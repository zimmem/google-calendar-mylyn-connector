package com.zimmem.mylyn.connector.google.calendar.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.commons.net.AuthenticationCredentials;
import org.eclipse.mylyn.commons.net.AuthenticationType;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.zimmem.mylyn.connector.google.calendar.core.CalendarEventQueryAttribute;
import com.zimmem.mylyn.connector.google.calendar.core.GoogleCalendarCorePlugin;

public class GoogleCalendarClient {

    private CalendarService      calendarService;

    private final TaskRepository repository;

    private final String         userName;

    public final static String   CONNECTOR_APPLICATION_NAME = "google-calendar-mylyn-connector";

    public final static URL      GOOGLE_CALENDAR_FEEDS      = createURL("https://www.google.com/calendar/feeds/");

    public final static URL      OWNER_CALENDARS_FEED       = createURL("https://www.google.com/calendar/feeds/default/owncalendars/full");

    public final static URL      DEFAULT_CALEMDAR_FEED      = createURL("https://www.google.com/calendar/feeds/default/private/full");

    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public GoogleCalendarClient(TaskRepository repository) throws CoreException {
        this.repository = repository;
        userName = repository.getUserName();
        AuthenticationCredentials credentials = repository
                .getCredentials(AuthenticationType.REPOSITORY);
        if (repository != null) {
            calendarService = new CalendarService(CONNECTOR_APPLICATION_NAME);
            try {
                calendarService.setUserCredentials(credentials.getUserName(),
                        credentials.getPassword());
            } catch (AuthenticationException e) {
                Status status = new Status(IStatus.ERROR, GoogleCalendarCorePlugin.PLUGIN_ID,
                        "invalid credentials", e);
                throw new CoreException(status);
            }
        }
    }

    public List<CalendarEntry> getOwnerCalendars() throws CoreException {
        CalendarFeed resultFeed;
        try {
            resultFeed = calendarService.getFeed(OWNER_CALENDARS_FEED, CalendarFeed.class);
            List<CalendarEntry> entries = resultFeed.getEntries();
            return entries;
        } catch (Exception e) {
            Status status = new Status(IStatus.ERROR, GoogleCalendarCorePlugin.PLUGIN_ID,
                    "Error occurd while getOwnerCalendars", e);
            throw new CoreException(status);
        }

    }

    public CalendarEventEntry createTask(CalendarEventEntry event) throws CoreException {
        try {
            return calendarService.insert(DEFAULT_CALEMDAR_FEED, event);
        } catch (IOException e) {
            Status status = new Status(IStatus.ERROR, GoogleCalendarCorePlugin.PLUGIN_ID,
                    "error occured while create google calendar event", e);
            throw new CoreException(status);
        } catch (ServiceException e) {
            Status status = new Status(IStatus.ERROR, GoogleCalendarCorePlugin.PLUGIN_ID,
                    "error occured while create google calendar event", e);
            throw new CoreException(status);
        }
    }

    public CalendarEventEntry getCalendarEvent(String id) throws CoreException {
        try {
            return calendarService.getEntry(new URL(id), CalendarEventEntry.class);
        } catch (IOException e) {
            throw wrapCoreException(IStatus.ERROR,
                    "error occured while fetch google calendar event", e);
        } catch (ServiceException e) {
            throw wrapCoreException(IStatus.ERROR,
                    "error occured while fetch google calendar event", e);
        }
    }

    public CalendarEventEntry updateEvent(CalendarEventEntry event) throws CoreException {
        URL url;
        try {
            url = new URL(event.getEditLink().getHref());
            return calendarService.update(url, event);
        } catch (MalformedURLException e) {
            throw wrapCoreException(IStatus.ERROR,
                    "error occured while update google calendar event", e);
        } catch (IOException e) {
            throw wrapCoreException(IStatus.ERROR,
                    "error occured while update google calendar event", e);
        } catch (ServiceException e) {
            throw wrapCoreException(IStatus.ERROR,
                    "error occured while update google calendar event", e);
        }

    }

    public List<CalendarEventEntry> queryEvent(IRepositoryQuery query) throws CoreException {
        CalendarQuery calendarQuery = new CalendarQuery(DEFAULT_CALEMDAR_FEED);
        if (!query.getAttributes().isEmpty()) {
            String strMaxStart = query.getAttribute(CalendarEventQueryAttribute.MAXIMUM_START_TIME
                    .getKey());
            String strMinStart = query.getAttribute(CalendarEventQueryAttribute.MAXIMUM_START_TIME
                    .getKey());
            try {
                if (StringUtils.isNotBlank(strMaxStart)) {
                    calendarQuery.setMaximumStartTime(new DateTime(
                            GoogleCalendarCorePlugin.SIMPLE_DATE_FORMAT.parse(strMaxStart)));
                }

                if (StringUtils.isNotBlank(strMinStart)) {
                    calendarQuery.setMaximumStartTime(new DateTime(
                            GoogleCalendarCorePlugin.SIMPLE_DATE_FORMAT.parse(strMaxStart)));
                }
            } catch (ParseException e) {
                throw wrapCoreException(IStatus.ERROR,
                        "error occured while query google calendar events", e);
            }
        }
        try {
            CalendarEventFeed feed = calendarService.query(calendarQuery, CalendarEventFeed.class);
            return feed.getEntries();
        } catch (IOException e) {
            throw wrapCoreException(IStatus.ERROR,
                    "error occured while query google calendar events", e);
        } catch (ServiceException e) {
            throw wrapCoreException(IStatus.ERROR,
                    "error occured while query google calendar events", e);
        }
    }

    private CoreException wrapCoreException(int statusLevel, String message, Throwable e) {
        Status status = new Status(statusLevel, GoogleCalendarCorePlugin.PLUGIN_ID, message, e);
        return new CoreException(status);
    }
}
