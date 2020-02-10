package com.tibco.rta.model;

/**
 * 
 * Defines a retention policy for facts and hierarchies. This is used by the purge
 * service that deletes data from the system as defined by the retention policy.
 * 
 */

public interface RetentionPolicy {
	
	/**
	 * 
	 * Qualifier to tell if the retention policy applies to a fact or to a hierarchy.
	 *
	 */
	enum Qualifier {
		FACT, HIERARCHY
	}
	
	/**
	 * Returns the associated qualifier
	 * @return the associated qualifier.
	 */
	Qualifier getQualifier();
	
	/**
	 * Returns the hierarchy name for which this retention policy applies.
	 * @return the hierarchy name for which this retention policy applies.
	 */
	String getHierarchyName();	
	
	/**
	 * Returns the unit multiplier of the retention unit. 
	 * @return the unit multiplier of the retention unit. 
	 */
	long getRetentionUnitCount();
	
	/**
	 * Returns the unit of measurement of the retention count.
	 * @return the unit of measurement of the retention count.
	 */
	TimeUnits.Unit getRetentionUnit();
	
	/**
	 * Returns the period in milliseconds based on retention count and retention unit.
	 * @return the period in milliseconds based on retention count and retention unit.
	 */
	
	long getRetentionPeriod();
	
	/**
	 * The time of day when the purge timer should trigger for this hierarchy.
	 * HHMI format HH denotes the hour of the day and MI denotes the minutes of the hour.
	 * @return the time of day when the purge timer should trigger for this hierarchy.
	 */

	String getPurgeTimeOfDay();
	
	/**
	 * Returns how often to run the purge job for this hierarchy.
	 * @return the purge frequency for this hierarchy.
	 */
	long getPurgeFrequencyPeriod();

}
