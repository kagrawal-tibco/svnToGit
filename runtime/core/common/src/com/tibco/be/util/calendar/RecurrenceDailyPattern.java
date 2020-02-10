package com.tibco.be.util.calendar;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:26:55 PM
 * To change this template use Options | File Templates.
 */
public interface RecurrenceDailyPattern extends RecurrencePattern {

    final static public int RECURRENCE_EVERYDAY = 0;
    final static public int RECURRENCE_EVERYWEEKDAY= 1;

    int getRecurrenceType();
    void setRecurrenceType(int recurrenceType);

    void setRecurringEvery(int everyXDay);
    int getRecurringEvery();
}
