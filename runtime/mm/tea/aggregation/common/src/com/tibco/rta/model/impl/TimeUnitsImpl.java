package com.tibco.rta.model.impl;

/**
 * @author bgokhale
 * 
 * This class handles time related ranges.
 * A user can specify a time dimension. This can be in absolute time or a repeating sequence like
 * aggregation by month of year, quarter of year etc.
 * 
 * This class allows users to model thier time dimensions.
 * 
 */

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.tibco.rta.model.TimeUnits;

@XmlAccessorType(XmlAccessType.NONE)
public class TimeUnitsImpl implements TimeUnits {

	private static final long serialVersionUID = 2404612620025216649L;

	public static long SECONDS_IN_MILLIS = 1000;
	public static long MINUTES_IN_MILLIS = SECONDS_IN_MILLIS * 60;
	public static long HOURS_IN_MILLIS = MINUTES_IN_MILLIS * 60;
	public static long DAYS_IN_MILLIS = HOURS_IN_MILLIS * 24;
	public static long WEEK_IN_MILLIS = DAYS_IN_MILLIS * 7;

	ThreadLocal<Calendar> calenderThreadLocal = new ThreadLocal<Calendar>() {
		protected Calendar initialValue() {
			return Calendar.getInstance();
		}
	};

	// Base unit
	protected Unit unit;

	// how often (things like 5 day average, 3 weeks average this represents "5"
	// and "3")
	protected int multiplier = 1;

	// for quarter related, need to tell where a quarter starts
	protected int firstQtrStartMonth;

	public TimeUnitsImpl(Unit unit, int frequency) {
		this.unit = unit;
		this.multiplier = frequency;
	}

	public TimeUnitsImpl(Unit unit) {
		this.unit = unit;
	}
	
	public TimeUnitsImpl() {
		
	}

	public void setStartMonth(int startMonth) {
		this.firstQtrStartMonth = startMonth;
	}

	/**
	 * Given a timestamp, find the absolute time bucket that it fits into, based
	 * on this timeunit
	 * 
	 * @param timestamp
	 * @return
	 */
	@Override
	public long getTimeDimensionValue(long timestamp) {

		Calendar c= calenderThreadLocal.get();
		c.setTimeInMillis(timestamp);
		long period = 1;
		switch (unit) {
		case SECOND: {
			int seconds = c.get(Calendar.SECOND);
			seconds = multiplier * (seconds/multiplier);
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, seconds);
			return c.getTimeInMillis();
		}
		case MINUTE: {
			int minute = c.get(Calendar.MINUTE);
			minute = multiplier * (minute/multiplier);
			
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);

			c.set(Calendar.MINUTE, minute);
			return c.getTimeInMillis();
		}
		case HOUR: {
			int hour = c.get(Calendar.HOUR_OF_DAY);
			hour = multiplier * (hour/multiplier);
			
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);

			c.set(Calendar.HOUR_OF_DAY, hour);
			return c.getTimeInMillis();
		}
		case DAY: {
			int day = c.get(Calendar.DAY_OF_MONTH);
			day = multiplier * (day/multiplier);
			
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			
			c.set(Calendar.DAY_OF_MONTH, day);
			return c.getTimeInMillis();
		}
		case QUARTER: {
			int month = c.get(Calendar.MONTH);
			int qtr = 1;
			if (firstQtrStartMonth <= month) {
				qtr = firstQtrStartMonth + ((month - firstQtrStartMonth) / 3) * 3;
				long ts = getQtrStartTime(c, qtr);
				return ts;
			} else {
				qtr = firstQtrStartMonth + (((11 - firstQtrStartMonth) + month) / 3) * 3;
				c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
				long ts = getQtrStartTime(c, qtr);
				return ts;
			}
		}
		case MINUTE_OF_THE_HOUR: {
			return c.get(Calendar.MINUTE);
		}
		case HOUR_OF_THE_DAY: {
			return c.get(Calendar.HOUR_OF_DAY);
		}
		case DAY_OF_THE_WEEK: {
			return c.get(Calendar.DAY_OF_WEEK);
		}
		case QTR_OF_THE_YEAR: {
			int month = c.get(Calendar.MONTH);
			if (firstQtrStartMonth <= month) {
				return (month - firstQtrStartMonth) / 3 + 1;
			} else {
				return ((11 - firstQtrStartMonth) + month) / 3 + 1;
			}
		}
		case MONTH_OF_THE_YEAR: {
			return c.get(Calendar.MONTH);
		}
		case MONTH: {
			
			int month = c.get(Calendar.MONTH);
			month = multiplier * (month/multiplier);
			
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.MONTH, month);
			return c.getTimeInMillis();
		}
		case WEEK: {
			
			int week_of_month = c.get(Calendar.WEEK_OF_MONTH);
			week_of_month = multiplier * (week_of_month/multiplier);
			
			c.clear(Calendar.MILLISECOND);
			c.clear(Calendar.SECOND);
			c.clear(Calendar.MINUTE);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.DAY_OF_WEEK, 1);
			c.set(Calendar.WEEK_OF_MONTH, week_of_month);
			return c.getTimeInMillis();
		}
		case YEAR:
		// TODO: 1 year is easy, "n" years is tricky
		{
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.MONTH, 0);
			return c.getTimeInMillis();
		}
		default: {
			return timestamp;
		}
		}
	}
	
	@Deprecated
	private long getTimeDimensionValue_Old(long timestamp) {

		Calendar c= calenderThreadLocal.get();
		c.setTimeInMillis(timestamp);
		long period = 1;
		switch (unit) {
		case SECOND: {
			period = SECONDS_IN_MILLIS;
			return period = (multiplier * period) * (timestamp / (multiplier * period));
		}
		case MINUTE: {
			period = MINUTES_IN_MILLIS;
			return period = (multiplier * period) * (timestamp / (multiplier * period));
		}
		case HOUR: {
			period = HOURS_IN_MILLIS;
			return period = (multiplier * period) * ((long) (timestamp / (multiplier * period)));
		}
		case DAY: {
			period = DAYS_IN_MILLIS;
			return period = (multiplier * period) * (timestamp / (multiplier * period));
		}
		case QUARTER: {
			int month = c.get(Calendar.MONTH);
			int qtr = 1;
			if (firstQtrStartMonth <= month) {
				qtr = firstQtrStartMonth + ((month - firstQtrStartMonth) / 3) * 3;
				long ts = getQtrStartTime(c, qtr);
				return ts;
			} else {
				qtr = firstQtrStartMonth + (((11 - firstQtrStartMonth) + month) / 3) * 3;
				c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
				long ts = getQtrStartTime(c, qtr);
				return ts;
			}
		}
		case MINUTE_OF_THE_HOUR: {
			return c.get(Calendar.MINUTE);
		}
		case HOUR_OF_THE_DAY: {
			return c.get(Calendar.HOUR);
		}
		case DAY_OF_THE_WEEK: {
			return c.get(Calendar.DAY_OF_WEEK);
		}
		case QTR_OF_THE_YEAR: {
			int month = c.get(Calendar.MONTH);
			if (firstQtrStartMonth <= month) {
				return (month - firstQtrStartMonth) / 3 + 1;
			} else {
				return ((11 - firstQtrStartMonth) + month) / 3 + 1;
			}
		}
		case MONTH_OF_THE_YEAR: {
			return c.get(Calendar.MONTH);
		}
		case MONTH: {
			// TODO: 1 month is easy, "n" months is tricky.
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			return c.getTimeInMillis();
		}
		case WEEK: {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.clear(Calendar.MINUTE);
			c.clear(Calendar.SECOND);
			c.clear(Calendar.MILLISECOND);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
			return c.getTimeInMillis();
		}
		case YEAR:
		// TODO: 1 year is easy, "n" years is tricky
		{
			c.set(Calendar.MILLISECOND, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.MONTH, 0);
			return c.getTimeInMillis();
		}
		default: {
			return timestamp;
		}
		}
	}
	
	@Override
	public Unit getTimeUnit() {
		// TODO Auto-generated method stub
		return unit;
	}

	@Override
	public int getMultiplier() {
		// TODO Auto-generated method stub
		return multiplier;
	}

	private long getQtrStartTime(Calendar c, int stm) {
		c.set(Calendar.MONTH, stm);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		long ts = c.getTimeInMillis();
		return ts;
	}

	
	@Override
	public int getFirstQtrStartMonth() {
		return firstQtrStartMonth;
	}

	public static long getTimePeriod(TimeUnits.Unit timeunit, long multiplier) {

		long period = 0;
		switch (timeunit) {
		case SECOND: {
			period = SECONDS_IN_MILLIS;
			break;
		}
		case MINUTE: {
			period = MINUTES_IN_MILLIS;
			break;
		}
		case HOUR: {
			period = HOURS_IN_MILLIS;
			break;
		}
		case DAY: {
			period = DAYS_IN_MILLIS;
			break;
		}
		case WEEK: {
			period = WEEK_IN_MILLIS;
			break;
		}
		default: {
			break;
		}
		}
		return period * multiplier;
	}

//	public static void main(String[] args) {
//
//		TimeUnits tu = new TimeUnitsImpl(TimeUnits.Unit.WEEK, 1);
//		Calendar gmt = Calendar.getInstance();
//		System.out.println(gmt.getTime());
//		long t = gmt.getTimeInMillis();
//		long ms = tu.getTimeDimensionValue(t);
//
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(ms);
//		Date d = c.getTime();
//		System.out.println(d);
//
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of
//											// day !
//		cal.clear(Calendar.MINUTE);
//		cal.clear(Calendar.SECOND);
//		cal.clear(Calendar.MILLISECOND);
//
//		// get start of this week in milliseconds
//		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
//		System.out.println("Start of this week:       " + cal.getTime());
//
//		System.out.println(String.format("Batch %d received", 5));
//		
//		
//		cal.setTimeInMillis(1369089000000L);
//		System.out.println("Current hr bucket:       " + cal);
//		
//		long currTime = System.currentTimeMillis();
//		TimeUnitsImpl timeUnitImplHr = new TimeUnitsImpl(Unit.HOUR, 1);
//		long hrDimValue = timeUnitImplHr.getTimeDimensionValue(1369132200000L);
//		
//		
//		cal.setTimeInMillis(hrDimValue);
//		System.out.println("computed hr bucket:       " + cal.getTime());
//		
//		c.setTimeInMillis(1368946800000L);
//		
//		
//		
//		
//		System.out.println("SXXXXk:       " + c);
//		
////		System.out.println(1368946800000L-1368990000000L);
//	}
}
