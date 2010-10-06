package com.zimmem.mylyn.connector.google.calendar.ui.editor;

import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.mylyn.tasks.ui.editors.AttributeEditorFactory;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;

import com.zimmem.mylyn.connector.google.calendar.core.GoogleCalendarCorePlugin;

public class GoogleCalendarTaskEditorPage extends AbstractTaskEditorPage {

    // @Override
    // protected Set<TaskEditorPartDescriptor> createPartDescriptors() {
    // Set<TaskEditorPartDescriptor> descriptors = new
    // LinkedHashSet<TaskEditorPartDescriptor>();
    // descriptors.add(new TaskEditorPartDescriptor(ID_PART_SUMMARY) {
    // @Override
    // public AbstractTaskEditorPart createPart() {
    // return new TaskEditorSummaryPart();
    // }
    // }.setPath(PATH_HEADER));
    // return descriptors;
    // }

    public GoogleCalendarTaskEditorPage(TaskEditor editor) {
        super(editor, "googleCalendarTaskEditorPage", "Google Calendar",
                GoogleCalendarCorePlugin.CONNECTOR_KIND);
        setNeedsPrivateSection(false);
        setNeedsSubmitButton(true);

    }

    @Override
    protected AttributeEditorFactory createAttributeEditorFactory() {
        return new GoogleCalendarAttributeEditorFactory(getModel(), getTaskRepository(),
                getEditorSite());

    }

}
