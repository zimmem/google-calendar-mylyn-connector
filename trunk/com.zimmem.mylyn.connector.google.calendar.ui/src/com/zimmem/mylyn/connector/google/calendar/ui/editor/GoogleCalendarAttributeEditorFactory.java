package com.zimmem.mylyn.connector.google.calendar.ui.editor;

import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskDataModel;
import org.eclipse.mylyn.tasks.ui.editors.AbstractAttributeEditor;
import org.eclipse.mylyn.tasks.ui.editors.AttributeEditorFactory;
import org.eclipse.ui.services.IServiceLocator;

public class GoogleCalendarAttributeEditorFactory extends AttributeEditorFactory {

    public GoogleCalendarAttributeEditorFactory(TaskDataModel model, TaskRepository taskRepository) {
        super(model, taskRepository, null);
    }

    public GoogleCalendarAttributeEditorFactory(TaskDataModel model, TaskRepository taskRepository,
                                                IServiceLocator serviceLocator) {
        super(model, taskRepository, serviceLocator);
    }

    // @Override
    // public AbstractAttributeEditor createEditor(String type,
    // TaskAttribute taskAttribute) {
    // if (TaskAttribute.SUMMARY.equals(type))
    // return new GoogleCalendarSummaryEditor();
    // return super.createEditor(type, taskAttribute);
    // }

}
