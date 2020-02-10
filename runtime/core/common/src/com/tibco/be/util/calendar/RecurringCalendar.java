package com.tibco.be.util.calendar;

import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:26:55 PM
 * To change this template use Options | File Templates.
 */
public interface RecurringCalendar extends Calendar{

    //TimeOfDay - in minutes starting from 0 to 60 * 24
    int getTimeToStart();
    void setTimeToStart(int timeToStartInMins);

    //return duration in minutes
    int getDuration();
    void setDuration(int durationInMins);

    Date getStartOfRecurrence();
    Date getEndOfRecurrence();
    int getNumRecurrences();

    //hour, minute, sec, and msec will be ignore
    void setStartOfRecurrence(Date startDate);
    void setEndOfRecurrence(Date endDate) throws CalendarException;
    void setEndOfRecurrenceAfterOccurences(int times) throws CalendarException;
    void setNoEndOfRecurrence();

    RecurrencePattern getRecurrencePattern();
    void setRecurrencePattern(RecurrencePattern pattern);
}
