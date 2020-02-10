package com.tibco.be.util.calendar.Impl;

import java.util.Date;

import com.tibco.be.util.CalendarCalculator;
import com.tibco.be.util.calendar.CalendarException;
import com.tibco.be.util.calendar.RecurrencePattern;
import com.tibco.be.util.calendar.RecurringCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 27, 2003
 * Time: 2:12:19 PM
 * To change this template use Options | File Templates.
 */
public class RecurringCalendarImpl extends CalendarImpl implements RecurringCalendar {
    int m_timeToStart;  //in minutes
    int m_duration;

    //always rounded
    Date m_startDate;
    Date m_endDate;
    int m_numOccurence;      // -1 = NO end, 0 = end by, n = num occurence
    RecurrencePattern m_pattern;

    public RecurringCalendarImpl() {
        super(true);
        m_timeToStart = 0;
        m_duration = 0;
        m_startDate = CalendarCalculator.roundDate(new Date());
        m_endDate = CalendarCalculator.addYear(m_startDate, 1);
        m_numOccurence = -1;
    }

    public boolean occursOnDate(Date date) throws CalendarException {
        //check startDate
        if(m_startDate.getTime() > date.getTime()) {
            return false;
        }
        //check end date
        long startDateTime = m_startDate.getTime() + m_timeToStart;
        return m_pattern.occursOn(RecurrencePattern.INTERVAL_DAY, date, new Date(startDateTime), m_endDate, m_numOccurence, m_timeToStart, m_duration);
    }

    //TimeOfDay - in minutes starting from 0 to 60 * 24
    public int getTimeToStart() {
        return m_timeToStart / 60000;
    }

    //return duration in minutes
    public int getDuration() {
        return m_duration / 60000 ;
    }

    public void setTimeToStart(int timeToStartInMins) {
        m_timeToStart = timeToStartInMins * 60000;
    }

    public void setDuration(int durationInMins) {
        m_duration = durationInMins * 60000;
    }

    public Date getStartOfRecurrence() {
        return m_startDate;
    }

    public Date getEndOfRecurrence() {
        return m_endDate;
    }

    public int getNumRecurrences() {
        return m_numOccurence;
    }

    //hour, minute, sec, and msec will be ignore
    public void setStartOfRecurrence(Date startDate) {
        m_startDate = CalendarCalculator.roundDate(startDate);
    }

    public void setEndOfRecurrence(Date endDate) throws CalendarException {
        Date tempDate = CalendarCalculator.roundDate(endDate);
        if (tempDate.getTime() < m_startDate.getTime()) {
            throw new CalendarException("Setting End Date prior to Start Date");
        }
        m_endDate = tempDate;
        m_numOccurence = 0;
    }

    public void setEndOfRecurrenceAfterOccurences(int times) throws CalendarException {
        m_numOccurence = times;
    }

    public void setNoEndOfRecurrence() {
        m_numOccurence = -1;
    }

    public RecurrencePattern getRecurrencePattern() {
        return m_pattern;
    }

    public void setRecurrencePattern(RecurrencePattern pattern) {
        m_pattern = pattern;
    }

    public boolean occursNow() {
        try {
            long startDateTime = m_startDate.getTime() + m_timeToStart;
            Date now = new Date();
            return m_pattern.occursOn(RecurrencePattern.INTERVAL_MINTUES, now, new Date(startDateTime), m_endDate, m_numOccurence, m_timeToStart, m_duration);
        }
        catch(CalendarException ex) {
            ex.printStackTrace(); //shouldn't happen
            return false;
        }
    }

    public Date getNextStartTime() {
        try {
            long startDateTime = m_startDate.getTime() + m_timeToStart;
            Date now = new Date();
            return m_pattern.getNextStartTime(now, new Date(startDateTime), m_endDate, m_numOccurence, m_duration);
        }
        catch(CalendarException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Date getNextEndTime() {
        try {
            long startDateTime = m_startDate.getTime() + m_timeToStart;
            Date now = new Date();
            return m_pattern.getNextEndTime(now, new Date(startDateTime), m_endDate, m_numOccurence, m_duration);
        }
        catch(CalendarException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //for testing only
    public boolean occursNow(Date now) {
        try {
            long startDateTime = m_startDate.getTime() + m_timeToStart;
            return m_pattern.occursOn(RecurrencePattern.INTERVAL_MINTUES, now, new Date(startDateTime), m_endDate, m_numOccurence, m_timeToStart, m_duration);
        }
        catch(CalendarException ex) {
            ex.printStackTrace(); //shouldn't happen
            return false;
        }
    }

    public Date getNextStartTime(Date now) {
        try {
            long startDateTime = m_startDate.getTime() + m_timeToStart;
            return m_pattern.getNextStartTime(now, new Date(startDateTime), m_endDate, m_numOccurence, m_duration);
        }
        catch(CalendarException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Date getNextEndTime(Date now) {
        try {
            long startDateTime = m_startDate.getTime() + m_timeToStart;
            return m_pattern.getNextEndTime(now, new Date(startDateTime), m_endDate, m_numOccurence, m_duration);
        }
        catch(CalendarException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
