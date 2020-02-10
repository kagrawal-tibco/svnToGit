package com.tibco.be.util.calendar.Impl;
import java.util.Calendar;

import com.tibco.be.util.CalendarCalculator;
import com.tibco.be.util.calendar.CalendarException;
import com.tibco.be.util.calendar.RecurrenceMonthlyPattern;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 28, 2003
 * Time: 4:03:31 PM
 * To change this template use Options | File Templates.
 */
public class RecurrenceMonthlyPatternImpl extends RecurrencePatternImpl implements RecurrenceMonthlyPattern {

    int m_dateOfMonth;
    int m_everyXMonth;
    int m_recurrenceType;

    public boolean matchDatePattern(java.util.Calendar date) {
        if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
            int dateOfMonth = date.get(java.util.Calendar.DAY_OF_MONTH);
            if (dateOfMonth ==m_dateOfMonth ) {
                return true;
            }
            return false;
        }
        else {
            int todayDate =  date.get(java.util.Calendar.DAY_OF_MONTH);
            int occurenceDate = this.getCalcDateFirstOccurenceInThisMonth(date);
            if ( occurenceDate == todayDate) {
                return true;
            }
            else {
                return false;
            }
        }
    }


    public RecurrenceMonthlyPatternImpl() {
        m_dateOfMonth = (new java.util.GregorianCalendar()).get(Calendar.DAY_OF_MONTH);
        m_everyXMonth = 1;
        m_recurrenceType = RECURRENCE_EXACT_DATE;

        m_whichSeq = SEQ_FIRST;
        m_whichDay = DAY_DAY;
        m_whichXMonth = 1;

    }

    public void setRecurringOnDateOfMonth(int dateOfMonth, int recurrenceEveryXMonth) throws CalendarException {
        m_dateOfMonth = dateOfMonth;
        m_everyXMonth = recurrenceEveryXMonth;
        m_recurrenceType = RECURRENCE_EXACT_DATE;

    }

    public int getRecurringOnDateOfMonth() throws CalendarException {
        return m_dateOfMonth;
    }

    public int getRecurringEvery() {
        return m_everyXMonth;
    }

    public void validatePattern() throws CalendarException {
        if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
            if (m_everyXMonth < 1) {
                throw new CalendarException("Recurring month should be greater than 1");
            }
            if ((m_dateOfMonth < 1) || (m_dateOfMonth > 31)) {
                throw new CalendarException("Date of month should be greater than 0 and less than 32");
            }
        }
    }


    private int nextOccurenceInThisMonth(Calendar startDateTime) {
        if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
            int todayDateInMonth = startDateTime.get(Calendar.DAY_OF_MONTH);
            if (todayDateInMonth < m_dateOfMonth) {
                int lastDateOfMonth = CalendarCalculator.lastDateOfMonth(startDateTime);
                if (m_dateOfMonth > lastDateOfMonth) {
                    return lastDateOfMonth - todayDateInMonth;
                }
                else {
                    return m_dateOfMonth - todayDateInMonth;
                }
            }
            else {
                return -1;
            }
        }
        else { //calculated date
            int firstOccurenceInMonth = getCalcDateFirstOccurenceInThisMonth(startDateTime);
            if (firstOccurenceInMonth > startDateTime.get(Calendar.DAY_OF_MONTH)) {
                return firstOccurenceInMonth -  startDateTime.get(Calendar.DAY_OF_MONTH);
            }
            else {
                return -1;
            }
        }
    }

    private int getCalcDateFirstOccurenceInThisMonth(Calendar month) {
        Calendar first = (Calendar) month.clone();
        Calendar last = (Calendar) month.clone();
        first.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonthInYear = first.get(Calendar.DAY_OF_YEAR);
        int initialYear = first.get(Calendar.YEAR);

        last.set(Calendar.DAY_OF_MONTH, 1);
        last.add(Calendar.MONTH, 1);
        last.add(Calendar.DAY_OF_YEAR, -1);
        int lastDayOfMonthInYear = last.get(Calendar.DAY_OF_YEAR);

        int resultDay = 1;
        //find the first day
        for (int i = 0; i < this.m_whichSeq; i++) {
            int tempDay = 1;
            if (m_whichDay == DAY_DAY) {
                //empty
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
        return resultDay - firstDayOfMonthInYear + 1;
    }

    public Calendar getNextOccurence(Calendar startDateTime) throws CalendarException {
        Calendar cl = (Calendar) startDateTime.clone();

        int nextOccur = this.nextOccurenceInThisMonth(cl);
        if (nextOccur != -1) {
            cl.add(Calendar.DAY_OF_YEAR, nextOccur);
            return cl;
        }
        else {
            if (m_recurrenceType == RECURRENCE_EXACT_DATE) {
                cl.add(Calendar.MONTH, m_everyXMonth);
                cl.set(Calendar.DAY_OF_MONTH, 1);
                if (m_dateOfMonth ==1) {
                    return cl;
                }
            }
            else {  //calculated date
                cl.add(Calendar.MONTH, m_whichXMonth);
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
            nextOccur = this.nextOccurenceInThisMonth(cl);
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

    //exact date
    int m_whichSeq;
    int m_whichDay;
    int m_whichXMonth;
    public void setRecurringOnCalcDateOfMonth(int whichSeq, int whichDay, int recurrenceEveryXMonth) throws CalendarException {
        m_whichSeq = whichSeq;
        m_whichDay = whichDay;
        m_whichXMonth = recurrenceEveryXMonth;
        m_recurrenceType = RECURRENCE_CALCULATED_DATE;
    }

    public int getRecurringOnCalcDateWhichSeq() {
        return m_whichSeq;
    }

    public int getRecurringOnCalcDateWhichDay() {
        return m_whichDay;
    }

    public int getRecurringOnCalcDateWhichMonth() {
        return m_whichXMonth;
    }
}
