package com.tibco.be.util.calendar;


import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:26:55 PM
 * To change this template use Options | File Templates.
 */
public interface RecurrencePattern {
     public final static int INTERVAL_DAY = 0;
     public final static int INTERVAL_MINTUES= 1;

    void validatePattern() throws CalendarException;

    //Date startOfLastOccurence(Date startDateTime, Date endDate, int numOccurence) throws CalendarException;
    java.util.Calendar getNextOccurence(java.util.Calendar startDateTime) throws CalendarException;

    Date getNextStartTime(Date fromTime, Date startDateTime, Date endDate, int numOccurence, int duration) throws CalendarException;
    Date getNextStartTime(java.util.Calendar fromTime, java.util.Calendar startDateTime, java.util.Calendar endDate, int numOccurence, int duration) throws CalendarException;

    Date getNextEndTime(Date fromTime, Date startDateTime, Date endDate, int numOccurence, int duration) throws CalendarException;
    Date getNextEndTime(java.util.Calendar fromTime, java.util.Calendar startDateTime, java.util.Calendar endDate, int numOccurence, int duration) throws CalendarException;

    boolean occursOn(int intervalCalutation, Date checkDateTime, Date startDateTime, Date endDate, int numOccurence, int startMin, int duration) throws CalendarException;
    boolean occursOn(int intervalCalutation, java.util.Calendar checkDateTime, java.util.Calendar startDateTime, java.util.Calendar endDate,
                                                 int numOccurence, int startMin, int duration) throws CalendarException;
}
