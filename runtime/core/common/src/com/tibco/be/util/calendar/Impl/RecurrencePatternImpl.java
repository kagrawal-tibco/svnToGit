package com.tibco.be.util.calendar.Impl;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.tibco.be.util.CalendarCalculator;
import com.tibco.be.util.calendar.CalendarException;
import com.tibco.be.util.calendar.RecurrencePattern;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 28, 2003
 * Time: 10:20:57 AM
 * To change this template use Options | File Templates.
 */
public abstract class RecurrencePatternImpl implements RecurrencePattern, Serializable {
    public static final long serialVersionUID=2413616562511050725L;

    public abstract boolean matchDatePattern(Calendar date);

    /*
    public Date startOfLastOccurence(Date startDateTime, Date endDate, int numOccurence) throws CalendarException{
        validatePattern();

        if (numOccurence == -1) {
            throw new CalendarException("No end date for this pattern");
        }
        boolean checkEndDate = false;
        long endTime = 0L;
        int remainOccurence = numOccurence;
        if (numOccurence == 0) { //Last Occurence determined by the end date
            checkEndDate = true;
            endTime = endDate.getTime();
            remainOccurence = Integer.MAX_VALUE;
        }
        java.util.Calendar cl = new java.util.GregorianCalendar();
        cl.setTime(startDateTime);
        while (remainOccurence > 0) {
             java.util.Calendar temp = this.getNextOccurence(cl);
            if (checkEndDate == true) {
                if (temp.getTimeInMillis() > endTime) {
                    return cl.getTime();
                }
            }
            cl = temp;
            remainOccurence--;
        }
        return cl.getTime();
    } */


    public  boolean occursOn(int intervalCalutation, Date checkDate, Date startDateTime, Date endDate, int numOccurence, int startMin, int duration) throws CalendarException {
        java.util.Calendar check = new java.util.GregorianCalendar();
        check.setTime(checkDate);
        java.util.Calendar start = new java.util.GregorianCalendar();
        start.setTime(startDateTime);
        java.util.Calendar end = null;
        if (endDate != null) {
            end = new java.util.GregorianCalendar();
            end.setTime(endDate);
        }
        return occursOn(intervalCalutation, check, start, end, numOccurence, startMin, duration);
    }


    public boolean matchTime(Calendar startDateTime, int startMin, int duration) {
        int here = (startDateTime.get(Calendar.HOUR_OF_DAY) * 60 + startDateTime.get(Calendar.MINUTE)) * 60000;
        if ((startMin <= here) && (here < (startMin + duration))) {
            return true;
        }
        else {
            return false;
        }

    }

    public  boolean occursOn(int intervalCalutation, java.util.Calendar checkDate, java.util.Calendar startDateTime, java.util.Calendar endDate, int numOccurence, int startMin, int duration) throws CalendarException {
        validatePattern();

        boolean checkEndDate = false;
        int remainOccurence;

        long dateRoundedCheckTime = CalendarCalculator.roundDate(checkDate).getTimeInMillis();
        long dateRoundedEndTime = 0L;

        long minRoundedCheckTime = CalendarCalculator.roundMinute(checkDate).getTimeInMillis();

        if (numOccurence == -1) {    //no end date
            checkEndDate = false;
            remainOccurence = Integer.MAX_VALUE;
        }
        else if (numOccurence == 0) { //Last Occurence determined by the end date
            checkEndDate = true;
            dateRoundedEndTime = CalendarCalculator.roundDate(endDate).getTimeInMillis();
            remainOccurence = Integer.MAX_VALUE;
        }
        else { //num occurence
            checkEndDate = false;
            remainOccurence = numOccurence;
            if (matchDatePattern(startDateTime)) {
                remainOccurence--;
            }
            /*
            if (intervalCalutation == RecurrencePattern.INTERVAL_DAY && matchDatePattern(startDateTime)) {
                remainOccurence--;
            }
            else if (intervalCalutation == RecurrencePattern.INTERVAL_MINTUES && matchDatePattern(startDateTime)) {
//                if (checkDate.getTimeInMillis() < startDateTime.getTimeInMillis()) {
                    remainOccurence--;
//                }
            } */
        }

        //check beginning first
        if (intervalCalutation == RecurrencePattern.INTERVAL_DAY) {
            //check if checkTime < start
            if (dateRoundedCheckTime < CalendarCalculator.roundDate(startDateTime).getTimeInMillis()) {
                return false;
            }
            if (matchDatePattern(checkDate) && CalendarCalculator.occursWithinDate(checkDate, startDateTime, CalendarCalculator.addMSec(startDateTime, duration))) {
                return true;
            }
        }
        else if (intervalCalutation == RecurrencePattern.INTERVAL_MINTUES) {
            //check if checkTime < start
            if (minRoundedCheckTime < CalendarCalculator.roundMinute(startDateTime).getTimeInMillis()) {
                return false;
            }
            if (matchDatePattern(checkDate) && CalendarCalculator.occursWithinMinute(checkDate, startDateTime, CalendarCalculator.addMSec(startDateTime, duration))) {
                return true;
            }
        }

        java.util.Calendar temp = startDateTime;
        while (remainOccurence > 0) {
            temp = this.getNextOccurence(temp);  //should get the exact time
            if (intervalCalutation == RecurrencePattern.INTERVAL_DAY) {
                long dateRoundedNextOccurenceTime = CalendarCalculator.roundDate(temp).getTimeInMillis();
                if (dateRoundedCheckTime < dateRoundedNextOccurenceTime) {
                    return false;
                }
                if (checkEndDate == true) {
                    if (dateRoundedNextOccurenceTime > dateRoundedEndTime) {
                        return false;
                    }
                }
                if (matchDatePattern(checkDate) && CalendarCalculator.occursWithinDate(checkDate, temp, CalendarCalculator.addMSec(temp, duration))) {
                    return true;
                }
            }
            else if (intervalCalutation == RecurrencePattern.INTERVAL_MINTUES) {
                long minRoundedNextOccurenceTime = CalendarCalculator.roundMinute(temp).getTimeInMillis();
                if (minRoundedCheckTime < minRoundedNextOccurenceTime) {
                    return false;
                }
                if (checkEndDate == true) {
                    long dateRoundedNextOccurenceTime = CalendarCalculator.roundDate(temp).getTimeInMillis();
                    if (dateRoundedNextOccurenceTime > dateRoundedEndTime) {
                        return false;
                    }
                }
                if (matchDatePattern(checkDate) && CalendarCalculator.occursWithinMinute(checkDate, temp, CalendarCalculator.addMSec(temp, duration))) {
                    return true;
                }
            }
            remainOccurence--;
        }
        return false;
    }

    public Date getNextStartTime(Date fromTime, Date startDateTime, Date endDate, int numOccurence, int duration) throws CalendarException {
        java.util.Calendar from = new java.util.GregorianCalendar();
        from.setTime(fromTime);
        java.util.Calendar start = new java.util.GregorianCalendar();
        start.setTime(startDateTime);
        java.util.Calendar end = null;
        if (endDate != null) {
            end = new java.util.GregorianCalendar();
            end.setTime(endDate);
        }
        return getNextStartTime(from, start, end, numOccurence, duration);
    }

    public Date getNextStartTime(java.util.Calendar fromDateTime, java.util.Calendar startDateTime, java.util.Calendar endDate, int numOccurence, int duration) throws CalendarException {
        boolean checkEndDate = false;
        int remainOccurence;

        long lastDate = 0L;

        long fromTime = CalendarCalculator.roundMinute(fromDateTime).getTimeInMillis();

        if (numOccurence == -1) {    //no end date
            checkEndDate = false;
            remainOccurence = Integer.MAX_VALUE;
        }
        else if (numOccurence == 0) { //Last Occurence determined by the end date
            checkEndDate = true;
            lastDate = CalendarCalculator.roundDate(endDate).getTimeInMillis();
            remainOccurence = Integer.MAX_VALUE;
        }
        else { //num occurence
            checkEndDate = false;
            remainOccurence = numOccurence;
            if (matchDatePattern(startDateTime)) {
                remainOccurence--;
            }
        }

        java.util.Calendar nextStartTime = startDateTime;
        if (matchDatePattern(startDateTime) && fromTime < startDateTime.getTimeInMillis()) {
            return startDateTime.getTime();
        }
        while (remainOccurence > 0) {
            nextStartTime = this.getNextOccurence(nextStartTime);  //should get the exact time
            if (checkEndDate == true) {
                if (CalendarCalculator.roundDate(nextStartTime).getTimeInMillis() == lastDate) {
                    //same as the end date
                    if (fromTime < nextStartTime.getTimeInMillis()) {
                        return nextStartTime.getTime();
                    }
                    else {
                        return null;
                    }
                }
                else if (CalendarCalculator.roundDate(nextStartTime).getTimeInMillis() > lastDate) {
                    //not on same day, check if pass the end date
                    return null;
                }
                else {
                    if (fromTime < nextStartTime.getTimeInMillis()) {
                        return nextStartTime.getTime();
                    }
                }
            }
            else if(fromTime < nextStartTime.getTimeInMillis()) {
                return nextStartTime.getTime();
            }
            remainOccurence--;
        }
        return null;
    }

    public Date getNextEndTime(Date fromTime, Date startDateTime, Date endDate, int numOccurence, int duration) throws CalendarException {
        java.util.Calendar from = new java.util.GregorianCalendar();
        from.setTime(fromTime);
        java.util.Calendar start = new java.util.GregorianCalendar();
        start.setTime(startDateTime);
        java.util.Calendar end = null;
        if (endDate != null) {
            end = new java.util.GregorianCalendar();
            end.setTime(endDate);
        }
        return getNextEndTime(from, start, end, numOccurence, duration);
    }

    public Date getNextEndTime(Calendar fromDateTime, Calendar startTime, Calendar endDate, int numOccurence, int duration) throws CalendarException {
        boolean checkEndDate = false;
        int remainOccurence;

        long lastDate = 0L;

        long fromTime = CalendarCalculator.roundMinute(fromDateTime).getTimeInMillis();

        if (numOccurence == -1) {    //no end date
            checkEndDate = false;
            remainOccurence = Integer.MAX_VALUE;
        }
        else if (numOccurence == 0) { //Last Occurence determined by the end date
            checkEndDate = true;
            lastDate = CalendarCalculator.roundDate(endDate).getTimeInMillis();
            remainOccurence = Integer.MAX_VALUE;
        }
        else { //num occurence
            checkEndDate = false;
            remainOccurence = numOccurence;
            if (matchDatePattern(startTime)) {
                remainOccurence--;
            }
        }

        java.util.Calendar nextStartTime = startTime;
        if (matchDatePattern(startTime) && fromTime < (startTime.getTimeInMillis() + duration)) {
            return new Date(startTime.getTimeInMillis() + duration);
        }
        while (remainOccurence > 0) {
            nextStartTime = this.getNextOccurence(nextStartTime);  //should get the exact time
            long endTime = nextStartTime.getTimeInMillis()+ duration;
            if (checkEndDate == true) {
                if (CalendarCalculator.roundDate(nextStartTime).getTimeInMillis() == lastDate) {
                    //same as the end date
                    if (fromTime < (endTime)) {
                        return new Date(endTime);
                    }
                    else {
                        return null;
                    }
                }
                else if (CalendarCalculator.roundDate(nextStartTime).getTimeInMillis() > lastDate) {
                    //not on same day, check if pass the end date
                    return null;
                }
                else {
                    if (fromTime < endTime) {
                        return new Date(endTime);
                    }
                }
            }
            else if(fromTime < endTime) {
                return new Date(endTime);
            }
            remainOccurence--;
        }
        return null;
    }

}
