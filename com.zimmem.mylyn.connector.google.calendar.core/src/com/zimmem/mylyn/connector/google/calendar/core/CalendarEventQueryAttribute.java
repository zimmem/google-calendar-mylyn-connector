package com.zimmem.mylyn.connector.google.calendar.core;

public enum CalendarEventQueryAttribute {

    /***/
    MINIMUM_START_TTIME("google.calendar.query.minimumStartTime"),
    /***/
    MAXIMUM_START_TIME("google.calendar.query.maximumStartTime");

    private CalendarEventQueryAttribute(String key) {
        this.key = key;
    }

    private String key;

    public String getKey() {
        return key;
    }

}
