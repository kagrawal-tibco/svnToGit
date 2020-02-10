package com.tibco.be.util.calendar.Impl;

import java.util.Calendar;

import com.tibco.be.util.CalendarCalculator;
import com.tibco.be.util.calendar.CalendarException;
import com.tibco.be.util.calendar.RecurrenceDailyPattern;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 28, 2003
 * Time: 12:27:10 AM
 * To change this template use Options | File Templates.
 */
public class RecurrenceDailyPatternImpl extends RecurrencePatternImpl implements RecurrenceDailyPattern  {

    int m_recurrenceType;
    int m_everyXDay;

    public boolean matchDatePattern(java.util.Calendar date) {
        if ( m_recurrenceType == RecurrenceDailyPattern.RECURRENCE_EVERYDAY) {
            if ( m_everyXDay == 1) {
                return true;
            }
            else {
                return true;       //???
                //throw new RuntimeException ("not implemented yet");
            }
        }
        return CalendarCalculator.isWeekDay(date);
    }

    public RecurrenceDailyPatternImpl() {
        m_recurrenceType = RecurrenceDailyPattern.RECURRENCE_EVERYDAY;
        m_everyXDay = 1;
    }

    public int getRecurrenceType() {
        return m_recurrenceType;
    }

    public void setRecurrenceType(int recurrenceType) {
        m_recurrenceType = recurrenceType;
    }

    public void validatePattern() throws CalendarException {
        if (m_recurrenceType > RecurrenceDailyPattern.RECURRENCE_EVERYWEEKDAY) {
            throw new CalendarException("Invalid recurrenceType");
        }
    }


    public java.util.Calendar getNextOccurence(java.util.Calendar startDateTime) throws CalendarException {
        java.util.Calendar cl = (Calendar) startDateTime.clone();
        if (m_recurrenceType == RecurrenceDailyPattern.RECURRENCE_EVERYDAY) {
            cl.add(java.util.Calendar.DAY_OF_YEAR, m_everyXDay);
            return  cl;
        }
        else if (m_recurrenceType == RecurrenceDailyPattern.RECURRENCE_EVERYWEEKDAY) {
            cl.add(java.util.Calendar.DAY_OF_YEAR, 1);
            while(!CalendarCalculator.isWeekDay(cl)) {
                cl.add(java.util.Calendar.DAY_OF_YEAR, 1);
            }
            return cl;
        }
        throw new CalendarException("Invalid recurrenceType");
    }


    public void setRecurringEvery(int everyXDay) {
        m_everyXDay = everyXDay;
    }

    public int getRecurringEvery() {
        return m_everyXDay;
    }
}
