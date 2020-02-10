package com.tibco.be.util.calendar;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:26:55 PM
 * To change this template use Options | File Templates.
 */
public interface RecurrenceWeeklyPattern extends RecurrencePattern {
    void setRecurringOnDate(int dateOfWeek, boolean on) throws CalendarException;
    boolean getRecurringOnDate(int dateOfWeek) throws CalendarException;

    void setRecurringEvery(int everyXWeek);
    int getRecurringEvery();
}
