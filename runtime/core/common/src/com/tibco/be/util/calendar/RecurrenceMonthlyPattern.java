package com.tibco.be.util.calendar;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:26:55 PM
 * To change this template use Options | File Templates.
 */
public interface RecurrenceMonthlyPattern extends RecurrencePattern{

    final static public int RECURRENCE_EXACT_DATE = 0;
    final static public int RECURRENCE_CALCULATED_DATE= 1;

    int getRecurrenceType();
    void setRecurrenceType(int recurrenceType);

    //exact date
    void setRecurringOnDateOfMonth(int dateOfMonth, int everyXMonth) throws CalendarException;
    int getRecurringOnDateOfMonth() throws CalendarException;
    int getRecurringEvery();

    //calculated date
    final static public int SEQ_FIRST = 1;
    final static public int SEQ_SECOND = 2;
    final static public int SEQ_THIRD = 3;
    final static public int SEQ_FORTH = 4;
    final static public int SEQ_LAST = 99;

    final static public int DAY_MONDAY = java.util.Calendar.MONDAY;
    final static public int DAY_TUESDAY = java.util.Calendar.TUESDAY;
    final static public int DAY_WEDNESDAY = java.util.Calendar.WEDNESDAY;
    final static public int DAY_THURSDAY = java.util.Calendar.THURSDAY;
    final static public int DAY_FRIDAY = java.util.Calendar.FRIDAY;
    final static public int DAY_SATURADAY = java.util.Calendar.SATURDAY;
    final static public int DAY_SUNDAY = java.util.Calendar.SUNDAY;
    final static public int DAY_DAY = 10;
    final static public int DAY_WEEKDAY = 11;
    final static public int DAY_WEEKEND = 12;

    void setRecurringOnCalcDateOfMonth(int whichSeq, int whichDay, int recurrenceEveryXMonth) throws CalendarException;
    int getRecurringOnCalcDateWhichSeq();
    int getRecurringOnCalcDateWhichDay();
    int getRecurringOnCalcDateWhichMonth();
}
