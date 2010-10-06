package com.zimmem.mylyn.connector.google.calendar.core;

import org.eclipse.mylyn.tasks.core.ITask.PriorityLevel;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;

public class OptionsUtil {

    static final String PRIORITY_LOW      = "Low";
    static final String PRIORITY_MEDIUM   = "Medium";
    static final String PRIORITY_HIGH     = "High";
    static final String PRIORITY_CRITICAL = "Critical";

    public static void fillPriorityOptions(TaskAttribute priority) {
        priority.putOption(PriorityLevel.P1.toString(), OptionsUtil.PRIORITY_CRITICAL);
        priority.putOption(PriorityLevel.P2.toString(), OptionsUtil.PRIORITY_HIGH);
        priority.putOption(PriorityLevel.P3.toString(), OptionsUtil.PRIORITY_MEDIUM);
        priority.putOption(PriorityLevel.P4.toString(), OptionsUtil.PRIORITY_LOW);
    }

    /**
     * Converts a Google code priority String to a Mylyn priority level.
     * 
     * @param priority the Google code priority
     * @return the Mylyn priority level, may be {@literal null}
     */
    static PriorityLevel convertToMylyn(String priority) {
        if (PRIORITY_CRITICAL.equals(priority)) {
            return PriorityLevel.P1;
        } else if (PRIORITY_HIGH.equals(priority)) {
            return PriorityLevel.P2;
        } else if (PRIORITY_MEDIUM.equals(priority)) {
            return PriorityLevel.P3;
        } else if (PRIORITY_LOW.equals(priority)) {
            return PriorityLevel.P4;
        }
        return null;
    }

}
