package com.tibco.be.util.calendar.Impl;

import java.util.Calendar;

import com.tibco.be.util.CalendarCalculator;
import com.tibco.be.util.calendar.CalendarException;
import com.tibco.be.util.calendar.RecurrenceYearlyPattern;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 28, 2003
 * Time: 4:46:48 PM
 * To change this template use Options | File Templates.
 */
public class RecurrenceYearlyPatternImpl extends RecurrencePatternImpl implements RecurrenceYearlyPattern {

    int m_dateOfMonth;
    int m_monthOfYear;
    int m_everyXYear;

    int m_recurrenceType;

    public boolean matchDatePattern(java.util.Calendar date) {
        if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
            int dateOfMonth = date.get(java.util.Calendar.DAY_OF_MONTH);
            int monthOfYear = date.get(java.util.Calendar.MONTH);
            if (dateOfMonth ==m_dateOfMonth &&  monthOfYear == m_monthOfYear) {
                return true;
            }
            return false;
        }
        else {
            int todayDate =  date.get(java.util.Calendar.DAY_OF_YEAR);
            int occurenceDate = getCalcDateFirstOccurenceInThisYear(date);
            if (occurenceDate == todayDate) {
                return true;
            }
            else {
                return false;
            }
        }
    }


    public RecurrenceYearlyPatternImpl() {
        Calendar today = new java.util.GregorianCalendar();
        m_dateOfMonth = today.get(Calendar.DAY_OF_MONTH);
        m_monthOfYear = today.get(Calendar.MONTH);
        m_everyXYear = 1;
        m_recurrenceType = RECURRENCE_EXACT_DATE;

        m_whichSeq = SEQ_FIRST;
        m_whichDay = DAY_DAY;
        m_whichMonth = 0;
        m_whichXYear = 1;
    }

    public void validatePattern() throws CalendarException {
        if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
            if (m_monthOfYear < 0 || m_monthOfYear > 11) {
                throw new CalendarException("Invalid month of year, must be greater than or equal to 0 and less than 12");
            }
            if (m_dateOfMonth < 1 || m_dateOfMonth > 31) {
                throw new CalendarException("Invalid day of month, must be greater than 0 and less than 32");
            }
            Calendar today = new java.util.GregorianCalendar();
            today.set(Calendar.MONTH, m_monthOfYear);
            int lastDateOfMonth = CalendarCalculator.lastDateOfMonth(today);
            if (m_dateOfMonth == 1 && m_dateOfMonth > 29) { //feb
                throw new CalendarException("Invalid date for month February, must be greater than 0 and less than 30");
            }
            else if (lastDateOfMonth < m_dateOfMonth) {
                throw new CalendarException("Invalid date " + m_dateOfMonth + " for month " + m_monthOfYear);
            }
        }
    }


    //date start from 1 while month start from 0
    public void setRecurringOnDateInYear(int dateOfMonth, int monthOfYear, int recurrenceEveryXYear) throws CalendarException {
        m_dateOfMonth = dateOfMonth;
        m_monthOfYear = monthOfYear;
        m_everyXYear = recurrenceEveryXYear;
        m_recurrenceType = RECURRENCE_EXACT_DATE;
    }

    public int getRecurringOnDateOfMonth() throws CalendarException {
        return m_dateOfMonth;
    }

    public int getRecurringOnMonthOfYear() throws CalendarException {
        return m_monthOfYear;
    }

    public int getRecurringEvery() {
        return m_everyXYear;
    }

    private int nextOccurenceInThisYear(Calendar cl) {
        if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
            int clDayInYear = cl.get(Calendar.DAY_OF_YEAR);
            Calendar check = (Calendar) cl.clone();
            check.set(Calendar.MONTH, m_monthOfYear);
            check.set(Calendar.DAY_OF_MONTH, m_dateOfMonth);
            int dayOfYearTarget = check.get(Calendar.DAY_OF_YEAR);
            if (clDayInYear < dayOfYearTarget ) {
                return dayOfYearTarget - clDayInYear;
            }
            else {
                return -1;
            }
        }
        else {
            int occurDate = getCalcDateFirstOccurenceInThisYear(cl);
            int todayDate = cl.get(Calendar.DAY_OF_YEAR);
            if (occurDate > todayDate) {
                return occurDate -  todayDate;
            }
            else {
                return -1;
            }
        }
    }


    private int getCalcDateFirstOccurenceInThisYear(Calendar year) {
        Calendar first = (Calendar) year.clone();
        Calendar last = (Calendar) year.clone();
        first.set(Calendar.MONTH, m_whichMonth);
        first.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonthInYear = first.get(Calendar.DAY_OF_YEAR);
        int initialYear = first.get(Calendar.YEAR);

        last.set(Calendar.MONTH, m_whichMonth);
        last.set(Calendar.DAY_OF_MONTH, 1);
        last.add(Calendar.MONTH, 1);
        last.add(Calendar.DAY_OF_YEAR, -1);
        int lastDayOfMonthInYear = last.get(Calendar.DAY_OF_YEAR);

        int resultDay = 1;
        //find the first day
        for (int i = 0; i < this.m_whichSeq; i++) {
            int tempDay = 1;
            if (m_whichDay == DAY_DAY) {
//                if (i != 0) {
//                    first.add(Calendar.DAY_OF_YEAR, 1);
//                }
            }
            else if (m_whichDay == DAY_WEEKEND) {
                while(CalendarCalculator.isWeekDay(first)) {
                    first.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
            else if (m_whichDay == DAY_WEEKDAY) {
                while(!CalendarCalculator.isWeekDay(first)) {
                    first.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
            else { //date
                while(first.get(Calendar.DAY_OF_WEEK) != m_whichDay) {
                    first.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
            tempDay = first.get(Calendar.DAY_OF_YEAR);
            int finalYear = first.get(Calendar.YEAR);
            if (finalYear > initialYear) {
                break;
            }
            if (tempDay == lastDayOfMonthInYear) {
                resultDay = tempDay;
                break;
            }
            else if (tempDay > lastDayOfMonthInYear) {
                break;
            }
            resultDay = tempDay;
            first.add(Calendar.DAY_OF_YEAR, 1);
        }
        return resultDay;
    }

    public Calendar getNextOccurence(Calendar startDateTime) throws CalendarException {
        Calendar cl = (Calendar) startDateTime.clone();

        int nextOccur = nextOccurenceInThisYear(cl);
        if (nextOccur != -1) {
            cl.add(Calendar.DAY_OF_YEAR, nextOccur);
            return cl;
        }
        else {
            if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
                cl.add(Calendar.YEAR, m_everyXYear);
                cl.set(Calendar.MONTH, m_monthOfYear);
                cl.set(Calendar.DAY_OF_MONTH, 1);
                if (m_dateOfMonth == 1) {
                    return cl;
                }
            }
            else {
                //calculated date
                cl.add(Calendar.YEAR, this.m_whichXYear);
                cl.set(Calendar.MONTH, this.m_whichMonth);
                cl.set(Calendar.DAY_OF_MONTH, 1);
                if (m_whichSeq == SEQ_FIRST) {
                    if ( (m_whichDay == DAY_DAY) ||
                          (m_whichDay == DAY_WEEKDAY && CalendarCalculator.isWeekDay(cl)) ||
                          (m_whichDay == DAY_WEEKEND && !CalendarCalculator.isWeekDay(cl))) {
                        return cl;
                    }
                    else {
                        int dayOfWeek = cl.get(java.util.Calendar.DAY_OF_WEEK);
                        if (dayOfWeek == m_whichDay ) {
                            return cl;
                        }
                    }
                }
            }
            nextOccur = nextOccurenceInThisYear(cl);
            if (nextOccur != -1) {
                cl.add(Calendar.DAY_OF_YEAR, nextOccur);
                return cl;
            }
            else {
                throw new CalendarException("Calculation error, ask Nick to fix the bug");
            }
        }
    }

    public int getRecurrenceType() {
        return m_recurrenceType;
    }
    public void setRecurrenceType(int recurrenceType) {
        m_recurrenceType = recurrenceType;
    }

    int m_whichSeq;
    int m_whichDay;
    int m_whichMonth;
    int m_whichXYear;

    public void setRecurringOnCalcDateInYear(int whichSeq, int whichDay, int whichMonth, int recurrenceEveryXYear) throws CalendarException {
        m_whichSeq = whichSeq;
        m_whichDay = whichDay;
        m_whichMonth = whichMonth;
        m_whichXYear = recurrenceEveryXYear;
        m_recurrenceType = RECURRENCE_CALCULATED_DATE;
    }

    public int getRecurringOnCalcDateWhichSeq() {
        return m_whichSeq;
    }

    public int getRecurringOnCalcDateWhichDay() {
        return m_whichDay;
    }

    public int getRecurringOnCalcDateWhichMonth() {
        return m_whichMonth;
    }

    public int getRecurringOnCalcDateWhichYear() {
        return m_whichXYear;
    }


}
