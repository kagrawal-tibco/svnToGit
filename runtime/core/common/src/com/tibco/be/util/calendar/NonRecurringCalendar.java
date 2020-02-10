package com.tibco.be.util.calendar;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:26:55 PM
 * To change this template use Options | File Templates.
 */
public interface NonRecurringCalendar {
    Date getStartTime();
    Date getEndTime();

    void setStartTime(Date date);
    void setEndTime(Date date) throws CalendarException;
}
