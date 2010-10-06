package com.zimmem.mylyn.connector.google.calendar.ui.editor;

import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.ui.ITasksUiConstants;
import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPageFactory;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditorInput;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.editor.IFormPage;

import com.zimmem.mylyn.connector.google.calendar.core.GoogleCalendarCorePlugin;

public class GoogleCalendarPageFactory extends AbstractTaskEditorPageFactory {

    @Override
    public boolean canCreatePageFor(TaskEditorInput input) {
        ITask task = input.getTask();
        // copy and pasted from other connectors
        return (task.getConnectorKind().equals(GoogleCalendarCorePlugin.CONNECTOR_KIND) || TasksUiUtil
                .isOutgoingNewTask(task, GoogleCalendarCorePlugin.CONNECTOR_KIND));
    }

    @Override
    public IFormPage createPage(TaskEditor parentEditor) {
        return new GoogleCalendarTaskEditorPage(parentEditor);
    }

    @Override
    public Image getPageImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPageText() {
        // TODO Auto-generated method stub
        return "Google Calendar";
    }

    @Override
    public int getPriority() {
        return PRIORITY_TASK;
    }

    @Override
    public String[] getConflictingIds(TaskEditorInput input) {
        // copy and pasted from other connectors
        if (!input.getTask().getConnectorKind().equals(GoogleCalendarCorePlugin.CONNECTOR_KIND)) {
            return new String[] { ITasksUiConstants.ID_PAGE_PLANNING };
        }
        return null;
    }

}
