package com.tibco.be.functions.trax.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * Utility class to convert Calendar to TIBCO date format and vice versa.
 */
public class CalendarUtil {
	
	
	public static ThreadLocal<SimpleDateFormat> tibcoDateFormat = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		}
	};
	
	/**
	 * Takes a date in TIBCO date format and return a java calendar object. 
	 * @throws ParseException 
	 */
	public static Calendar parseDate(String date) {
		SimpleDateFormat dateFormat = tibcoDateFormat.get();
		try {
			Date dt = dateFormat.parse(date);
			Calendar cal = new GregorianCalendar();
			cal.setTime(dt);
			return cal;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Takes a java Calendar object as input 
	 * and returns the date as string in TIBCO date format i.e. yyyy-MM-ddTHH:mm:ss.SSSZ
	 */
	public static String toString(Calendar cal){
		SimpleDateFormat dateFormat = tibcoDateFormat.get();
		String date = dateFormat.format(cal.getTime());
		return date;
	}
	
	/**
	 * Takes a java Calendar object as input 
	 * and returns the date as string in given format
	 */
	public static String toString(Calendar cal, String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String date = dateFormat.format(cal.getTime());
		return date;
	}
	
	public static void main(String[] args) {
		Calendar cal = new GregorianCalendar();
		System.out.println(tibcoDateFormat.get().format(cal.getTime()));
	}
}
