package com.zimmem.mylyn.connector.google.calendar.core;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.tasks.core.IRepositoryPerson;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.RepositoryResponse;
import org.eclipse.mylyn.tasks.core.RepositoryResponse.ResponseKind;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskDataHandler;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMetaData;
import org.eclipse.mylyn.tasks.core.data.TaskData;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.extensions.When;
import com.zimmem.mylyn.connector.google.calendar.client.GoogleCalendarAttribute;
import com.zimmem.mylyn.connector.google.calendar.client.GoogleCalendarClient;

public class GoogleCalendarTaskDataHandler extends AbstractTaskDataHandler {

    static final String GOOGLE_CALENDAR_KEY = "googleCalendarKey";

    public GoogleCalendarTaskDataHandler(GoogleCalendarRepositoryConnector connector) {
        this.connector = connector;
    }

    private GoogleCalendarRepositoryConnector connector;

    @Override
    public RepositoryResponse postTaskData(TaskRepository repository, TaskData taskData,
                                           Set<TaskAttribute> oldAttributes,
                                           IProgressMonitor monitor) throws CoreException {

        if (taskData.isNew()) {
            return insertIssue(repository, taskData, monitor);
        } else {
            return updateEvent(repository, taskData, oldAttributes, monitor);
        }
    }

    @Override
    public boolean initializeTaskData(TaskRepository repository, TaskData data,
                                      ITaskMapping initializationData, IProgressMonitor monitor)
            throws CoreException {
        createAttribute(data, GoogleCalendarAttribute.TASK_KEY);

        createAttribute(data, GoogleCalendarAttribute.SUMMARY);
        // createAttribute(data, GoogleCalendarAttribute.STATUS);
        createAttribute(data, GoogleCalendarAttribute.BEGIN_TIME);
        createAttribute(data, GoogleCalendarAttribute.FINISH_TIME);

        createAttribute(data, GoogleCalendarAttribute.DESCRIPTION);

        TaskAttribute priority = createAttribute(data, GoogleCalendarAttribute.PRIORITY);
        OptionsUtil.fillPriorityOptions(priority);

        createAttribute(data, GoogleCalendarAttribute.DATE_MODIFICATION);
        createAttribute(data, GoogleCalendarAttribute.DATE_CREATION);
        createAttribute(data, GoogleCalendarAttribute.USER_ASSIGNED);

        createAttribute(data, GoogleCalendarAttribute.DATE_COMPLETION);

        return true;

    }

    @Override
    public TaskAttributeMapper getAttributeMapper(TaskRepository taskRepository) {
        return new TaskAttributeMapper(taskRepository);
    }

    private TaskAttribute createAttribute(TaskData data, GoogleCalendarAttribute key,
                                          String attributeDefault) {
        TaskAttribute attribute = createAttribute(data, key);
        attribute.setValue(attributeDefault);
        return attribute;
    }

    private static TaskAttribute createAttribute(TaskData data, GoogleCalendarAttribute key) {
        return createAttribute(data.getRoot(), key);
    }

    private static TaskAttribute createAttribute(TaskAttribute parent,
                                                 GoogleCalendarAttribute GoogleCalendarAttribute) {
        TaskAttribute taskAttribute = parent.createAttribute(GoogleCalendarAttribute.getKey());
        TaskAttributeMetaData metaData = taskAttribute.getMetaData();
        metaData.defaults();
        metaData.setReadOnly(GoogleCalendarAttribute.isReadOnly());
        metaData.setKind(GoogleCalendarAttribute.getKind());
        metaData.setLabel(GoogleCalendarAttribute.getLabel());
        metaData.setType(GoogleCalendarAttribute.getType());
        metaData.putValue(GOOGLE_CALENDAR_KEY, GoogleCalendarAttribute.getGoogleCodeKey());
        return taskAttribute;
    }

    private RepositoryResponse insertIssue(TaskRepository repository, TaskData taskData,
                                           IProgressMonitor monitor) throws CoreException {
        CalendarEventEntry entry = new CalendarEventEntry();

        String summary = getStringValue(taskData, GoogleCalendarAttribute.SUMMARY);
        entry.setTitle(new PlainTextConstruct(summary));

        String description = getStringValue(taskData, GoogleCalendarAttribute.DESCRIPTION);
        entry.setContent(new PlainTextConstruct(description));

        Date dateBegin = getDateValue(taskData, GoogleCalendarAttribute.BEGIN_TIME);
        Date dateFinish = getDateValue(taskData, GoogleCalendarAttribute.FINISH_TIME);
        setEventWhen(entry, dateBegin, dateFinish);

        CalendarEventEntry created = connector.getGoogleCalendarClient(repository)
                .createTask(entry);

        return new RepositoryResponse(ResponseKind.TASK_CREATED, getID(created));

    }

    /**
     * update task to calendar
     * 
     * @param repository
     * @param taskData
     * @param oldAttributes
     * @param monitor
     * @return
     * @throws CoreException
     */
    private RepositoryResponse updateEvent(TaskRepository repository, TaskData taskData,
                                           Set<TaskAttribute> oldAttributes,
                                           IProgressMonitor monitor) throws CoreException {

        GoogleCalendarClient client = connector.getGoogleCalendarClient(repository);
        CalendarEventEntry entry = client.getCalendarEvent(taskData.getTaskId());

        String summary = getStringValue(taskData, GoogleCalendarAttribute.SUMMARY);
        entry.setTitle(new PlainTextConstruct(summary));

        String description = getStringValue(taskData, GoogleCalendarAttribute.DESCRIPTION);
        entry.setContent(new PlainTextConstruct(description));

        Date dateBegin = getDateValue(taskData, GoogleCalendarAttribute.BEGIN_TIME);
        Date dateFinish = getDateValue(taskData, GoogleCalendarAttribute.FINISH_TIME);
        setEventWhen(entry, dateBegin, dateFinish);

        CalendarEventEntry updated = client.updateEvent(entry);

        return new RepositoryResponse(ResponseKind.TASK_UPDATED, getID(updated));
    }

    private static String getStringValue(TaskData data, GoogleCalendarAttribute attribute) {
        return data.getAttributeMapper().getValue(getAttribute(data, attribute));
    }

    private Date getDateValue(TaskData data, GoogleCalendarAttribute attribute) {
        return data.getAttributeMapper().getDateValue(getAttribute(data, attribute));
    };

    private static void setAttributeValue(TaskData data, GoogleCalendarAttribute key, String value) {
        if (value != null) {
            TaskAttribute attribute = getAttribute(data, key);
            data.getAttributeMapper().setValue(attribute, value);
        }
    }

    private static void setDateAttributeValue(TaskData data, GoogleCalendarAttribute key, Date value) {
        if (value != null) {
            TaskAttribute attribute = getAttribute(data, key);
            data.getAttributeMapper().setDateValue(attribute, value);
        }
    }

    public static TaskAttribute getAttribute(TaskData taskData, GoogleCalendarAttribute key) {
        return taskData.getRoot().getAttribute(key.getKey());
    }

    private static void setAttributeValue(TaskData data, GoogleCalendarAttribute key,
                                          DateTime dateTime) {
        if (dateTime != null) {
            TaskAttribute attribute = getAttribute(data, key);
            data.getAttributeMapper().setDateValue(attribute, new Date(dateTime.getValue()));
        }
    }

    private static void setAttributeValue(TaskData data, GoogleCalendarAttribute key,
                                          List<String> list) {
        if (list != null) {
            TaskAttribute attribute = getAttribute(data, key);
            data.getAttributeMapper().setValues(attribute, list);
        }
    }

    private static void setAttributeValue(TaskData data, GoogleCalendarAttribute key,
                                          IRepositoryPerson person) {
        if (person != null) {
            TaskAttribute attribute = getAttribute(data, key);
            setAttributeValue(data, attribute, person);
        }
    }

    private static void setAttributeValue(TaskData data, TaskAttribute attribute,
                                          IRepositoryPerson person) {
        if (person != null) {
            data.getAttributeMapper().setRepositoryPerson(attribute, person);
        }
    }

    /**
     * update calendar event to local task
     * 
     * @param taskRepository
     * @param event
     * @param monitor
     * @return
     * @throws CoreException
     */
    public TaskData updateEventTask(TaskRepository taskRepository, CalendarEventEntry event,
                                    IProgressMonitor monitor) throws CoreException {
        String repositoryUrl = taskRepository.getRepositoryUrl();
        String eventId = getID(event);
        TaskData data = new TaskData(getAttributeMapper(taskRepository),
                taskRepository.getConnectorKind(), repositoryUrl, eventId);
        this.initializeTaskData(taskRepository, data, null, monitor);

        setAttributeValue(data, GoogleCalendarAttribute.TASK_KEY, eventId);
        String summay = event.getTitle().getPlainText();
        String description = event.getTextContent().getContent().getPlainText();
        setAttributeValue(data, GoogleCalendarAttribute.SUMMARY, summay);
        setAttributeValue(data, GoogleCalendarAttribute.DESCRIPTION, description);
        When when = event.getTimes().get(0);
        Date beginTime = new Date(when.getStartTime().getValue());
        Date finishTime = new Date(when.getEndTime().getValue());
        setDateAttributeValue(data, GoogleCalendarAttribute.BEGIN_TIME, beginTime);
        setDateAttributeValue(data, GoogleCalendarAttribute.FINISH_TIME, finishTime);
        return data;
    }

    private void setEventWhen(CalendarEventEntry event, Date begin, Date end) {
        When eventTime = new When();
        eventTime.setStartTime(new DateTime(begin, TimeZone.getDefault()));
        eventTime.setEndTime(new DateTime(end, TimeZone.getDefault()));
        event.getTimes().clear();
        event.addTime(eventTime);
    }

    private String getID(CalendarEventEntry entry) {
        String icalUID = entry.getIcalUID();
        int index = icalUID.indexOf('@');
        return icalUID.substring(0, index);
    }
}
