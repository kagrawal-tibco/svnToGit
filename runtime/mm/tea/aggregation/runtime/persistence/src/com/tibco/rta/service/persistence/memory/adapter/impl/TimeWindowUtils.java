package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.util.Calendar;
import java.util.Date;

public class TimeWindowUtils {
    
	public static long getStartOfWeek(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        
        int week_of_month = c.get(Calendar.WEEK_OF_MONTH);
        c.clear(Calendar.MILLISECOND);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MINUTE);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.DAY_OF_WEEK, 1);
        c.set(Calendar.WEEK_OF_MONTH, week_of_month);
        return c.getTimeInMillis();
    }
	
	public static long getEndOfWeek(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        
        int week_of_month = c.get(Calendar.WEEK_OF_MONTH);
        c.set(Calendar.WEEK_OF_MONTH, week_of_month);
        c.set(Calendar.DAY_OF_WEEK, 7);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        
        return c.getTimeInMillis();
    }
	
    public static long getStartOfDay(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTimeInMillis();
    }

    public static long getEndOfDay(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.MILLISECOND, 999);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTimeInMillis();
    }
    
    public static long getStartOfHour(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);

        int hour = c.get(Calendar.HOUR_OF_DAY);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, hour);
        return c.getTimeInMillis();
    }

    public static long getEndOfHour(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        
        int hour = c.get(Calendar.HOUR_OF_DAY);
        c.set(Calendar.MILLISECOND, 999);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.HOUR_OF_DAY, hour);
        return c.getTimeInMillis();
    }
	
    public static long getStartOfMinute(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);

        int minute = c.get(Calendar.MINUTE);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);

        c.set(Calendar.MINUTE, minute);
        return c.getTimeInMillis();
    }

    public static long getEndOfMinute(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);

        int minute = c.get(Calendar.MINUTE);
        c.set(Calendar.MILLISECOND, 999);
        c.set(Calendar.SECOND, 59);

        c.set(Calendar.MINUTE, minute);
        return c.getTimeInMillis();
    }
    
    public static void main(String [] args) {
    	long currentTimeInMillis = System.currentTimeMillis();
    	System.out.println("Current time: "+ new Date(currentTimeInMillis));
    	System.out.println("Start of week: "+ new Date(getStartOfWeek(currentTimeInMillis)));
    	System.out.println("End of week: "+ new Date(getEndOfWeek(currentTimeInMillis)));
    	System.out.println("Start of day: "+ new Date(getStartOfDay(currentTimeInMillis)));
    	System.out.println("End of day: "+ new Date(getEndOfDay(currentTimeInMillis)));
    	System.out.println("Start of hour: "+ new Date(getStartOfHour(currentTimeInMillis)));
    	System.out.println("End of hour: "+ new Date(getEndOfHour(currentTimeInMillis)));
    }
	
}
