package com.zimmem.mylyn.connector.google.calendar.client;

import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.mylyn.tasks.core.TaskRepository;

import com.google.common.collect.Maps;

public class GoogleCalendarClientManager {

    public GoogleCalendarClientManager() {
        clientsByUsername = Maps.newHashMap();
    }

    private final Map<String, GoogleCalendarClient> clientsByUsername;

    public GoogleCalendarClient getClient(TaskRepository repository) throws CoreException {
        Assert.isNotNull(repository);
        GoogleCalendarClient client = clientsByUsername.get(repository.getUserName());
        if (client == null) {
            client = new GoogleCalendarClient(repository);
            clientsByUsername.put(repository.getRepositoryUrl(), client);
        }
        return client;
    }

}
