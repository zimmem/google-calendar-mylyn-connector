package com.zimmem.mylyn.connector.google.calendar.ui.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositorySettingsPage;
import org.eclipse.swt.widgets.Composite;

import com.zimmem.mylyn.connector.google.calendar.core.GoogleCalendarCorePlugin;

public class GoogleCalendarRepositorySettingsPage extends AbstractRepositorySettingsPage {

    public static final String GOOGLE_CALENDAR_URL = "https://www.google.com/calendar/b/0/render";

    //	@Override
    //	public String getRepositoryUrl() {
    //		return "";
    //	}

    @Override
    protected void createSettingControls(Composite parent) {
        super.createSettingControls(parent);
        serverUrlCombo.setText(GOOGLE_CALENDAR_URL);
        serverUrlCombo.setEnabled(false);
        savePasswordButton.setSelection(true);
        repositoryUserNameEditor.setLabelText("Google Account:");
        //		for (Control control : parent.getChildren()) {
        //			if (control instanceof Label) {
        //				Label label = (Label) control;
        //				if (label.getText().equals(LABEL_SERVER)) {
        //					label.dispose();
        //					return;
        //				}
        //			}
        //		}
    }

    private static final String TITLE       = "Google Calendar Repository Settings";

    private static final String DESCRIPTION = "Enter your google account and password.";

    public GoogleCalendarRepositorySettingsPage(TaskRepository taskRepository) {
        super(TITLE, DESCRIPTION, taskRepository);
        setNeedsAdvanced(false);
        setNeedsEncoding(false);
        setNeedsTimeZone(false);
        setNeedsValidation(true);
    }

    @Override
    public String getConnectorKind() {
        return GoogleCalendarCorePlugin.CONNECTOR_KIND;
    }

    @Override
    protected boolean isValidUrl(String url) {
        return true;
    }

    @Override
    protected Validator getValidator(TaskRepository repository) {
        return new Validator() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                this.setStatus(Status.OK_STATUS);
                // TODO Check connection for username / password validation
            }
        };
    }

    @Override
    protected void createAdditionalControls(Composite parent) {
        // do nothing

    }

}
