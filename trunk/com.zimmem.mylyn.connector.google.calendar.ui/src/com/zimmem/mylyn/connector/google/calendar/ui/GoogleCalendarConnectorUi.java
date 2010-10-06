package com.zimmem.mylyn.connector.google.calendar.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.AbstractRepositoryConnectorUi;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskRepositoryPage;
import org.eclipse.mylyn.tasks.ui.wizards.NewTaskWizard;
import org.eclipse.mylyn.tasks.ui.wizards.RepositoryQueryWizard;

import com.zimmem.mylyn.connector.google.calendar.core.GoogleCalendarCorePlugin;
import com.zimmem.mylyn.connector.google.calendar.ui.wizard.GoogleCalendarQueryPage;
import com.zimmem.mylyn.connector.google.calendar.ui.wizard.GoogleCalendarRepositorySettingsPage;

public class GoogleCalendarConnectorUi extends AbstractRepositoryConnectorUi {

    @Override
    public String getConnectorKind() {
        return GoogleCalendarCorePlugin.CONNECTOR_KIND;
    }

    @Override
    public ITaskRepositoryPage getSettingsPage(TaskRepository taskRepository) {
        return new GoogleCalendarRepositorySettingsPage(taskRepository);
    }

    @Override
    public IWizard getQueryWizard(TaskRepository taskRepository, IRepositoryQuery queryToEdit) {
        RepositoryQueryWizard wizard = new RepositoryQueryWizard(taskRepository);
        wizard.addPage(new GoogleCalendarQueryPage(taskRepository, queryToEdit));
        return wizard;
    }

    @Override
    public IWizard getNewTaskWizard(TaskRepository taskRepository, ITaskMapping selection) {
        return new NewTaskWizard(taskRepository, selection);
    }

    @Override
    public boolean hasSearchPage() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getAccountCreationUrl(TaskRepository taskRepository) {
        return "https://www.google.com/accounts/NewAccount";
    }

    @Override
    public String getAccountManagementUrl(TaskRepository taskRepository) {
        return "https://www.google.com/accounts/ManageAccount";
    }

    @Override
    public String getTaskKindLabel(ITask task) {
        // This prefix is used in the task editor title bar "Issue 23"
        // could be changed to ITask#getTaskKind()
        return task.getTaskKind();
    }

}
