package com.tibco.be.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:38:38 PM
 * To change this template use Options | File Templates.
 */
public class CalendarCalculator {

    public static Date roundMinute(Date indateTime) {
        java.util.Calendar checkDate = new java.util.GregorianCalendar();
        checkDate.setTime(indateTime);
        return new Date(roundMinute(checkDate).getTimeInMillis());
    }

    public static Date roundDate(Date indateTime) {
        java.util.Calendar checkDate = new java.util.GregorianCalendar();
        checkDate.setTime(indateTime);
        return new Date(roundDate(checkDate).getTimeInMillis());
    }

    public static Calendar roundMinute(Calendar indateTime) {
        Calendar checkDate = (Calendar) indateTime.clone();
        checkDate.set(java.util.Calendar.SECOND, 0);
        checkDate.set(java.util.Calendar.MILLISECOND, 0);
        return checkDate;
    }

    public static Calendar roundDate(Calendar indateTime) {
        Calendar checkDate = (Calendar) indateTime.clone();
        checkDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
        checkDate.set(java.util.Calendar.MINUTE, 0);
        checkDate.set(java.util.Calendar.SECOND, 0);
        checkDate.set(java.util.Calendar.MILLISECOND, 0);
        return checkDate;
    }

    public static Date addYear(Date indateTime, int numYear) {
        java.util.Calendar tempCl = new java.util.GregorianCalendar();
        tempCl.setTime(indateTime);
        tempCl.add(java.util.Calendar.YEAR, numYear);
        return new Date(tempCl.getTimeInMillis());
    }

    public static Calendar addYear(Calendar indateTime, int numYear) {
        java.util.Calendar tempCl = (Calendar) indateTime.clone();
        tempCl.add(java.util.Calendar.YEAR, numYear);
        return tempCl;
    }

    public static Date addMSec(Date indateTime, int msec) {
        java.util.Calendar tempCl = new java.util.GregorianCalendar();
        tempCl.setTime(indateTime);
        tempCl.add(java.util.Calendar.MILLISECOND, msec);
        return new Date(tempCl.getTimeInMillis());
    }

    public static Calendar addMSec(Calendar indateTime, int msec) {
        java.util.Calendar tempCl = (Calendar) indateTime.clone();
        tempCl.add(java.util.Calendar.MILLISECOND, msec);
        return tempCl;
    }

    public static boolean isWeekDay(Date checkDate) {
        Calendar cl = new java.util.GregorianCalendar();
        cl.setTime(checkDate);
        return isWeekDay(cl);
    }

    public static boolean isWeekDay(Calendar checkDate) {
        int date = checkDate.get(java.util.Calendar.DAY_OF_WEEK);
        if ((date == java.util.Calendar.MONDAY) ||
                (date == java.util.Calendar.TUESDAY) ||
                (date == java.util.Calendar.WEDNESDAY) ||
                (date == java.util.Calendar.THURSDAY) ||
                (date == java.util.Calendar.FRIDAY)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean occursWithinDate(Date checkTime, Date startTime, Date endTime) {
        return ((roundDate(startTime).getTime() <= roundDate(checkTime).getTime()) &&
                       (roundDate(checkTime).getTime() <= roundDate(endTime).getTime()));
    }

    public static boolean occursWithinDate(Calendar checkTime, Calendar startTime, Calendar endTime) {
        return ((roundDate(startTime).getTimeInMillis() <= roundDate(checkTime).getTimeInMillis()) &&
                       (roundDate(checkTime).getTimeInMillis() <= roundDate(endTime).getTimeInMillis()));
    }

    public static boolean occursWithinMinute(Date checkTime, Date startTime, Date endTime) {
        return ((roundMinute(startTime).getTime() <= roundMinute(checkTime).getTime()) &&
                       (roundMinute(checkTime).getTime() < roundMinute(endTime).getTime()));
    }

    public static boolean occursWithinMinute(Calendar checkTime, Calendar startTime, Calendar endTime) {
        return ((roundMinute(startTime).getTimeInMillis() <= roundMinute(checkTime).getTimeInMillis()) &&
                       (roundMinute(checkTime).getTimeInMillis() < roundMinute(endTime).getTimeInMillis()));
    }

    public static int lastDateOfMonth(Calendar indate) {
        Calendar cl = (Calendar) indate.clone();
        cl.add(Calendar.MONTH, 1);
        cl.getTimeInMillis();
        cl.set(Calendar.DAY_OF_MONTH, 1);
        cl.add(Calendar.DAY_OF_MONTH, -1);
        return cl.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean occurOnSameDay(Date d1, Date d2) {
        if(roundDate(d1).equals(roundDate(d2))) return true;
        return false;
    }

    public static boolean occurOnSameDay(Calendar d1, Calendar d2) {
        if(roundDate(d1).equals(roundDate(d2))) return true;
        return false;
    }

}
