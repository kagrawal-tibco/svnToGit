package com.tibco.be.util.calendar.Impl;

import java.util.Calendar;

import com.tibco.be.util.calendar.CalendarException;
import com.tibco.be.util.calendar.RecurrenceWeeklyPattern;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 28, 2003
 * Time: 2:25:04 PM
 * To change this template use Options | File Templates.
 */
public class RecurrenceWeeklyPatternImpl extends RecurrencePatternImpl implements RecurrenceWeeklyPattern {

    boolean[] m_daysInWeek;
    int m_everyXWeek;

    public static int SUNDAY = 0;
    public static int MONDAY = 1;
    public static int TUESDAY = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY = 4;
    public static int FRIDAY = 5;
    public static int SATURDAY = 6;

    public boolean matchDatePattern(java.util.Calendar date) {
        int calDate = date.get(java.util.Calendar.DAY_OF_WEEK);
        if (m_daysInWeek[this.toDateOfWeek(calDate)]) {
            return true;
        }
        return false;
    }


    public RecurrenceWeeklyPatternImpl() {
        m_daysInWeek = new boolean[7];
        for(int i=0; i < 7; i++) {
            m_daysInWeek[i] = false;
        }

        Calendar cl = new java.util.GregorianCalendar();
        if ( cl.getFirstDayOfWeek() != Calendar.SUNDAY) {
            MONDAY = 0;
            TUESDAY = 1;
            WEDNESDAY = 2;
            THURSDAY = 3;
            FRIDAY = 4;
            SATURDAY = 5;
            SUNDAY = 6;
        }
        m_everyXWeek = 1;
    }

    public void validatePattern() throws CalendarException {
        if (m_everyXWeek < 1) {
            throw new CalendarException("Recurring week should be greater than 1");
        }
        for (int i=0; i < 7; i++) {
            if (this.m_daysInWeek[i] == true) {
                return;
            }
        }
        throw new CalendarException("No date of week are set for recurring on");
    }

    public void setRecurringOnDate(int dayOfWeek, boolean on) throws CalendarException {
        m_daysInWeek[toDateOfWeek(dayOfWeek)] = on;
    }

    public boolean getRecurringOnDate(int dayOfWeek) throws CalendarException {
        return m_daysInWeek[toDateOfWeek(dayOfWeek)];
    }

    public Calendar getNextOccurence(Calendar startDateTime) throws CalendarException {
        Calendar cl = (Calendar) startDateTime.clone();

        int nextOccur = this.nextOccurenceInThisWeek(cl);
        if (nextOccur != -1) {
            cl.add(Calendar.DAY_OF_YEAR, nextOccur);
            return cl;
        }
        else {
            cl.add(Calendar.WEEK_OF_YEAR, m_everyXWeek);
            cl = roundToStartOfWeek(cl);
            if (m_daysInWeek[0] == true) {
                return cl;
            }
            else {
                nextOccur = this.nextOccurenceInThisWeek(cl);
                if (nextOccur != -1) {
                    cl.add(Calendar.DAY_OF_YEAR, nextOccur);
                    return cl;
                }
                else {
                    throw new CalendarException("Calculation error, ask Nick to fix the bug");
                }
            }
        }
    }

    private Calendar roundToStartOfWeek(Calendar indate) {
        Calendar cl = (Calendar) indate.clone();
        int todayDate = toDateOfWeek(indate.get(Calendar.DAY_OF_WEEK));
        cl.add(Calendar.DAY_OF_YEAR, -todayDate);
        return cl;
    }

    private int nextOccurenceInThisWeek(Calendar startDateTime) {
        int todayDate = toDateOfWeek(startDateTime.get(Calendar.DAY_OF_WEEK));

        // patch: Ishaan Shastri
        for(int i = todayDate+1; i < 7; i++) {
            if (m_daysInWeek[i]) {
                return ( i- todayDate);
            }
        }
//        for(int i =1; i < 7; i++) {
//            if (m_daysInWeek[todayDate + i] == true) {
//                return i;
//            }
//        }
        return -1;
    }

    private int toDateOfWeek(int calendarDayOfWeek) {
        switch(calendarDayOfWeek) {
            case Calendar.MONDAY:
                return MONDAY;
            case Calendar.TUESDAY:
                return TUESDAY;
            case Calendar.WEDNESDAY:
                return WEDNESDAY;
            case Calendar.THURSDAY:
                return THURSDAY;
            case Calendar.FRIDAY:
                return FRIDAY;
            case Calendar.SATURDAY:
                return SATURDAY;
            case Calendar.SUNDAY:
                return SUNDAY;
            default:
                return -1;
        }
    }

    public int getRecurringEvery() {
        return m_everyXWeek;
    }

    public void setRecurringEvery(int everyXWeek) {
        m_everyXWeek = everyXWeek;
    }
}
