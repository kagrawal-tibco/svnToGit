package com.tibco.be.util.calendar;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:26:55 PM
 * To change this template use Options | File Templates.
 */
public interface Calendar {
    boolean isRecurring();
    boolean occursOnDate(Date date) throws CalendarException ;

    boolean occursNow();
    Date getNextStartTime();
    Date getNextEndTime();
}
