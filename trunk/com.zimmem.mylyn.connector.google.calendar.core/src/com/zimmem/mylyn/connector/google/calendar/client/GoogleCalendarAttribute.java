package com.zimmem.mylyn.connector.google.calendar.client;

import org.eclipse.mylyn.tasks.core.data.TaskAttribute;

public enum GoogleCalendarAttribute {

    /**
     * The sequence number of the issue.
     */
    TASK_KEY(TaskAttribute.TASK_KEY, TaskAttribute.TYPE_SHORT_TEXT, "Key:", true, true),

    /**
     * The short summary.
     */
    SUMMARY(TaskAttribute.SUMMARY, TaskAttribute.TYPE_SHORT_RICH_TEXT, "Summary:", true, false),

    /**
     * The person who reported the issue.
     */
    USER_REPORTER(TaskAttribute.USER_REPORTER, TaskAttribute.TYPE_PERSON, "Reporter:",
            TaskAttribute.KIND_PEOPLE, false, true),

    /**
     * The long description for new issues, editable. Don't use together with
     * {@link #DESCRIPTION_EXISTING}.
     */
    DESCRIPTION(TaskAttribute.DESCRIPTION, TaskAttribute.TYPE_LONG_RICH_TEXT, "Description:", true,
            false),

    /**
     * The status of the issue.
     */
    STATUS(TaskAttribute.STATUS, TaskAttribute.TYPE_SINGLE_SELECT, "Status:", false, false),

    /**
     * The class of an issue.
     */
    TYPE(TaskAttribute.TASK_KIND, TaskAttribute.TYPE_SINGLE_SELECT, "Type:", false, false),

    /**
     * The priority.
     */
    PRIORITY(TaskAttribute.PRIORITY, TaskAttribute.TYPE_SINGLE_SELECT, "Priority:", true, false),

    /**
     * The completion date, if this is set the issue is considered closed by
     * Mylyn.
     */
    DATE_COMPLETION(TaskAttribute.DATE_COMPLETION, TaskAttribute.TYPE_DATE, "Completed:", true,
            true),

    /**
     * The milestone.
     */
    MILESTONE(TaskAttribute.VERSION, TaskAttribute.TYPE_SHORT_TEXT, "Milestone:", false, false),

    /**
     * A new comment.
     */
    COMMENT_NEW(TaskAttribute.COMMENT_NEW, TaskAttribute.TYPE_LONG_RICH_TEXT, "New Comment:", true,
            false),

    /**
     * The creation date.
     */
    DATE_CREATION(TaskAttribute.DATE_CREATION, TaskAttribute.TYPE_DATETIME, "Created:", true, true),

    /**
     * The modification date.
     */
    DATE_MODIFICATION(TaskAttribute.DATE_MODIFICATION, TaskAttribute.TYPE_DATETIME, "Modified:",
            true, true),

    /**
     * The person who is currently responsible for the issue.
     */
    USER_ASSIGNED(TaskAttribute.USER_ASSIGNED, TaskAttribute.TYPE_PERSON, "Owner:",
            TaskAttribute.KIND_PEOPLE, true, false),

    /**
     * The URL pointing to the issue.
     */
    URL(TaskAttribute.TASK_URL, TaskAttribute.TYPE_URL, "Url:", true, true),

    BEGIN_TIME("google.calendar.date.begin", TaskAttribute.TYPE_DATETIME, "Begin time", false,
            false),

    FINISH_TIME("google.calendar.date.end", TaskAttribute.TYPE_DATETIME, "Finish time", false,
            false);

    private final String  key;
    private final String  type;
    private final String  label;
    private final String  kind;
    private final boolean hidden;
    private final boolean readOnly;

    private GoogleCalendarAttribute(String key, String type, String label, boolean isHidden,
                                    boolean isReadOnly) {
        this(key, type, label, isHidden ? null : TaskAttribute.KIND_DEFAULT, isHidden, isReadOnly);
    }

    private GoogleCalendarAttribute(String key, String type, String label, String kind,
                                    boolean isHidden, boolean isReadOnly) {
        this.key = key;
        this.type = type;
        this.label = label;
        this.kind = kind;
        this.hidden = isHidden;
        this.readOnly = isReadOnly;
    }

    /**
     * Returns the key of the attribute in an
     * {@link org.eclipse.mylyn.tasks.core.data.TaskData} object. This is a key
     * that ideally Mylyn understands.
     * 
     * @return the key of the attribute in a task data object
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns the Google Code specific key of the attribute in an
     * {@link org.eclipse.mylyn.tasks.core.data.TaskData} object.
     * 
     * @return the Google Code specific key of the attribute in a task data
     *         object
     */
    public String getGoogleCodeKey() {
        return this.key;
    }

    /**
     * Returns the type of the attribute for Mylyn. Eg. whether is a string or a
     * choice.
     * 
     * @see TaskAttribute
     * @return the type of the attribute
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns whether the attribute should be hidden. Hidden attributes don't
     * show up in the "Attributes" section of the task editor, they might show
     * up somewhere else though.
     * 
     * @return {@literal true} the attribute is hidden, {@literal false}
     *         otherwise
     */
    public boolean isHidden() {
        return hidden;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Returns the kind of the attribute for Mylyn. Eg. whether is a normal
     * attribute or a person or an action.
     * 
     * @see TaskAttribute
     * @return the kind of the attribute
     */
    public String getKind() {
        return this.kind;
    }

    public String getLabel() {
        return this.label;
    }

}
