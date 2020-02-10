package com.tibco.rta.model;

/**
 * An extension of {@code Dimension} that deals with time based aggregations.
 * 
 */
public interface TimeDimension extends Dimension {
	
	/**
	 * Get the time unit
	 * 
	 * @return
	 */
	TimeUnits getTimeUnit();

    /**
     * Return the multiplier for this time unit. (Example: 3 days, 4 seconds etc)
     * @return
     */
    int getMultiplier();
}