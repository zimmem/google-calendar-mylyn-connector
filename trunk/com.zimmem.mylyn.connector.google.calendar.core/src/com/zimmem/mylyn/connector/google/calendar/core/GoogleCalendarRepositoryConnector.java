package com.zimmem.mylyn.connector.google.calendar.core;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.tasks.core.AbstractRepositoryConnector;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskDataHandler;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;
import org.eclipse.mylyn.tasks.core.data.TaskMapper;
import org.eclipse.mylyn.tasks.core.sync.ISynchronizationSession;

import com.google.gdata.data.calendar.CalendarEventEntry;
import com.zimmem.mylyn.connector.google.calendar.client.GoogleCalendarClient;
import com.zimmem.mylyn.connector.google.calendar.client.GoogleCalendarClientManager;

public class GoogleCalendarRepositoryConnector extends AbstractRepositoryConnector {

    public GoogleCalendarRepositoryConnector() {
        taskDataHandler = new GoogleCalendarTaskDataHandler(this);
        googleCalendarClientManager = new GoogleCalendarClientManager();
        GoogleCalendarCorePlugin.getDefault().setRepositoryConnector(this);

    }

    private GoogleCalendarTaskDataHandler taskDataHandler;

    private GoogleCalendarClientManager   googleCalendarClientManager;

    @Override
    public AbstractTaskDataHandler getTaskDataHandler() {
        return taskDataHandler;
    }

    @Override
    public boolean canCreateNewTask(TaskRepository repository) {
        return true;
    }

    @Override
    public boolean canCreateTaskFromKey(TaskRepository repository) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getConnectorKind() {
        return GoogleCalendarCorePlugin.CONNECTOR_KIND;
    }

    @Override
    public String getLabel() {
        return "Google Calendar";
    }

    @Override
    public String getRepositoryUrlFromTaskUrl(String taskFullUrl) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TaskData getTaskData(TaskRepository taskRepository, String taskId,
                                IProgressMonitor monitor) throws CoreException {
        CalendarEventEntry event = getGoogleCalendarClient(taskRepository).getCalendarEvent(taskId);

        return taskDataHandler.updateEventTask(taskRepository, event, monitor);
    }

    @Override
    public String getTaskIdFromTaskUrl(String taskFullUrl) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTaskUrl(String repositoryUrl, String taskId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasTaskChanged(TaskRepository taskRepository, ITask task, TaskData taskData) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IStatus performQuery(TaskRepository repository, IRepositoryQuery query,
                                TaskDataCollector collector, ISynchronizationSession session,
                                IProgressMonitor monitor) {
        try {
            GoogleCalendarClient client = googleCalendarClientManager.getClient(repository);
            List<CalendarEventEntry> events = client.queryEvent(query);
            for (CalendarEventEntry event : events) {
                TaskData data = this.taskDataHandler.updateEventTask(repository, event, monitor);
                collector.accept(data);
            }
            return Status.OK_STATUS;
        } catch (CoreException e) {
            return e.getStatus();
        }

    }

    @Override
    public void updateRepositoryConfiguration(TaskRepository taskRepository,
                                              IProgressMonitor monitor) throws CoreException {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateTaskFromTaskData(TaskRepository taskRepository, ITask task, TaskData taskData) {
        TaskMapper mapper;
        mapper = new GoogleCalendarTaskMapper(taskData);
        mapper.applyTo(task);

    }

    public GoogleCalendarClient getGoogleCalendarClient(TaskRepository taskRepository)
            throws CoreException {
        return googleCalendarClientManager.getClient(taskRepository);
    }

    static class GoogleCalendarTaskMapper extends TaskMapper {

        public GoogleCalendarTaskMapper(TaskData taskData) {
            super(taskData);
        }

        // @Override
        // public PriorityLevel getPriorityLevel() {
        // String value = getPriority();
        // if (value != null) {
        // return LabelUtils.convertToMylyn(value);
        // } else {
        // return null;
        // }
        // }

    }
}
