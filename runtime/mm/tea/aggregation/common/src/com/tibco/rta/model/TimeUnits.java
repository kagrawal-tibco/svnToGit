package com.tibco.rta.model;

import java.io.Serializable;

/**
 * 
 * These are units of time on which aggregations are performed.
 * They may be absolute units of time (Aboslute hour) or repeating units of time (Month of the year).
 * 
 */
public interface TimeUnits extends Serializable {

	/**
	 * An enumeration of some basic time units.
	 *
	 */
	public enum Unit {
		// These are absolute in time. Example (Avg. Monthly sales: Jan 2012, Jan 2011 are different)
		// or, every 3 hour aggregates, etc.
		/**
		 * Millisecond.
		 */
		MILLISECOND,
		/**
		 * Second.
		 */
		SECOND, 
		/**
		 * Minute.
		 */
		MINUTE, 
		/**
		 * Hour.
		 */
		HOUR, 
		/**
		 * Day.
		 */
		DAY, 
		/**
		 * Week.
		 */
		WEEK,
		/**
		 * Month.
		 */
		MONTH, 
		/**
		 * Quarter.
		 */
		QUARTER, 
		/**
		 * Year.
		 */
		YEAR,

		// These are relative in time. Example: Average sales in Jan (any year)
		// or, categorization by month, day of year etc (independent of absolute year)
		/**
		 * Minute of the hour.
		 */
		MINUTE_OF_THE_HOUR, 
		/**
		 * Hour of the day.
		 */
		HOUR_OF_THE_DAY, 
		/**
		 * Day of the week.
		 */
		DAY_OF_THE_WEEK, 
		/**
		 * Month of the year.
		 */
		MONTH_OF_THE_YEAR,
		
		/**
		 * Quarter of the year.
		 */
		QTR_OF_THE_YEAR
	}


	/**
	 * Get the time unit
	 * 
	 * @return the timeunit.
	 */
	Unit getTimeUnit();
	
	/**
	 * Get the multiplier for the time unit specified. 
	 * 
	 * @return the multiplier
	 */
	int getMultiplier();

	/**
	 * For quarters, this represents the month corresponding to the 1st quarter.
	 * @return the month corresponding to the first quarter.
	 */
	int getFirstQtrStartMonth();	
	/**
	 * Given a timestamp, return its absolute time dimension value
	 * 
	 * @param timestamp the timestamp
	 * @return the absolute time interval used for aggregations, corresponding to the specified time.
	 */
	long getTimeDimensionValue(long timestamp);

}

