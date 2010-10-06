package com.zimmem.mylyn.connector.google.calendar.core;

import java.text.SimpleDateFormat;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.mylyn.tasks.core.AbstractRepositoryConnector;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class GoogleCalendarCorePlugin extends Plugin {

    // The plug-in ID
    public static final String              PLUGIN_ID           = "com.zimmem.mylyn.connector.google.calendar.core"; //$NON-NLS-1$

    public static final String              GOOGLE_CALENDAR_URL = "http://www.google.com/calendar/b/0/render";

    public static final String              CONNECTOR_KIND      = "googlecalendar";

    public static final SimpleDateFormat    SIMPLE_DATE_FORMAT  = new SimpleDateFormat(
                                                                        "yyyy/MM/dd HH:mm:ss");

    // The shared instance
    private static GoogleCalendarCorePlugin plugin;

    private AbstractRepositoryConnector     repositoryConnector;

    /**
     * The constructor
     */
    public GoogleCalendarCorePlugin() {
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static GoogleCalendarCorePlugin getDefault() {
        return plugin;
    }

    public void setRepositoryConnector(AbstractRepositoryConnector repositoryConnector) {
        this.repositoryConnector = repositoryConnector;
    }

    public AbstractRepositoryConnector getRepositoryConnector() {
        return repositoryConnector;
    }

}
