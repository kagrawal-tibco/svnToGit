package com.tibco.be.util.calendar.Impl;

import java.util.Date;

import com.tibco.be.util.CalendarCalculator;
import com.tibco.be.util.calendar.CalendarException;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 27, 2003
 * Time: 12:31:28 PM
 * To change this template use Options | File Templates.
 */
public class NonRecurringCalendarImpl extends CalendarImpl {
    public static final long serialVersionUID=2413616562511050725L;

    Date m_startTime;
    Date m_endTime;

    public NonRecurringCalendarImpl() {
        super(false);
        m_startTime = null;
        m_endTime = null;
    }

    public Date getStartTime() {
        return m_startTime;
    }

    public Date getEndTime() {
        return m_endTime;
    }

    public void setStartTime(Date date) {
        if (m_startTime == null) {
            m_startTime = date;
            m_endTime = date;
        }
        else {
            m_startTime = date;
            if ( m_endTime.getTime() < m_startTime.getTime()) {
                m_endTime = date;
            }
        }
    }

    public void setEndTime(Date date) throws CalendarException {
        if (date.getTime() < m_startTime.getTime()) {
            throw new CalendarException("Setting End Time prior to Start Time");
        }
        m_endTime = date;
    }

    public boolean occursOnDate(Date date) {
        return CalendarCalculator.occursWithinDate(date, m_startTime, m_endTime);
    }

    public boolean occursNow() {
        Date now = new Date();
        if(m_startTime.getTime() <= now.getTime() && now.getTime() <= m_endTime.getTime()) {
            return true;
        }
        else {
            return false;
        }
    }

    public Date getNextStartTime() {
        Date now = new Date();
        if(m_startTime.getTime() > now.getTime()) {
            return m_startTime;
        }
        else {
            return null;
        }
    }

    public Date getNextEndTime() {
        Date now = new Date();
        if (m_endTime.getTime() > now.getTime()) {
            return m_endTime;
        }
        else {
            return null;
        }
    }
}
