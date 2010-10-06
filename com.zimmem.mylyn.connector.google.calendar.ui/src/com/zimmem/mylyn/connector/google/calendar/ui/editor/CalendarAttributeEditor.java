package com.zimmem.mylyn.connector.google.calendar.ui.editor;

import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskDataModel;
import org.eclipse.mylyn.tasks.ui.editors.AbstractAttributeEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class CalendarAttributeEditor extends AbstractAttributeEditor {

    public CalendarAttributeEditor(TaskDataModel manager, TaskAttribute taskAttribute) {
        super(manager, taskAttribute);
    }

    @Override
    public void createControl(Composite parent, FormToolkit toolkit) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        composite.setLayout(new GridLayout(1, false));

        Label label = new Label(composite, SWT.NONE);
        label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        label.setText(getTaskAttribute().getValue());

        toolkit.adapt(composite);
        setControl(composite);
    }
}
