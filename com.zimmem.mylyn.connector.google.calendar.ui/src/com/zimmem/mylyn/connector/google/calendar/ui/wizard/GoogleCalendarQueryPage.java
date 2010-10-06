package com.zimmem.mylyn.connector.google.calendar.ui.wizard;

import java.util.Date;

import org.eclipse.mylyn.internal.provisional.commons.ui.DatePicker;
import org.eclipse.mylyn.internal.provisional.tasks.ui.wizards.AbstractRepositoryQueryPage2;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.zimmem.mylyn.connector.google.calendar.core.CalendarEventQueryAttribute;
import com.zimmem.mylyn.connector.google.calendar.core.GoogleCalendarCorePlugin;

@SuppressWarnings("restriction")
public class GoogleCalendarQueryPage extends AbstractRepositoryQueryPage2 {

    private DatePicker minimumStartTime;
    private DatePicker maximumStartTime;

    public GoogleCalendarQueryPage(TaskRepository repository, IRepositoryQuery query) {
        super("googleCalendarQueryDefinition", repository, query);
    }

    @Override
    public void applyTo(IRepositoryQuery query) {
        String queryTitle = this.getQueryTitle();
        if (queryTitle != null) {
            query.setSummary(queryTitle);
        }
        query.getAttributes().clear();
        if (minimumStartTime.getDate() != null) {
            setDateAttribute(query, CalendarEventQueryAttribute.MINIMUM_START_TTIME.getKey(),
                    minimumStartTime.getDate().getTime());
        }
        if (maximumStartTime.getDate() != null) {
            setDateAttribute(query, CalendarEventQueryAttribute.MINIMUM_START_TTIME.getKey(),
                    maximumStartTime.getDate().getTime());
        }

    }

    private void setDateAttribute(IRepositoryQuery query, String key, Date date) {
        query.setAttribute(key, GoogleCalendarCorePlugin.SIMPLE_DATE_FORMAT.format(date));
    }

    @Override
    protected void createPageContent(Composite parent) {
        Composite composite = new Composite(parent, SWT.BORDER);
        composite.setLayout(new GridLayout(2, false));

        Label label = new Label(composite, SWT.NONE);
        label.setText("minimumStartTime:");
        minimumStartTime = new DatePicker(composite, SWT.NONE, "select date", true, 8);
        minimumStartTime.setLayoutData(getTextGridData());

        label = new Label(composite, SWT.NONE);
        label.setText("maximumStartTime:");
        maximumStartTime = new DatePicker(composite, SWT.NONE, "select date", true, 8);
        maximumStartTime.setLayoutData(getTextGridData());

    }

    private GridData getTextGridData() {
        return new GridData(SWT.FILL, SWT.BEGINNING, true, false);
    }

    @Override
    protected boolean hasRepositoryConfiguration() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void doRefresh() {
        // nothing now

    }

    @Override
    protected boolean restoreState(IRepositoryQuery query) {
        // TODO Auto-generated method stub
        return false;
    }

}
