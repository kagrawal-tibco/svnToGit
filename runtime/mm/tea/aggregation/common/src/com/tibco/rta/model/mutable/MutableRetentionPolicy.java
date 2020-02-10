package com.tibco.rta.model.mutable;

import com.tibco.rta.model.RetentionPolicy;
import com.tibco.rta.model.TimeUnits;

/**
 * 
 * This represents a retention policy that can be modified via the API.
 *
 */
public interface MutableRetentionPolicy extends RetentionPolicy {

	/**
	 * Defines the qualifier of the retention policy.
	 * @param qualifier Whether this policy is for facts of for hierarchies.
	 */
	void setQualifier(Qualifier qualifier);
	
	/**
	 * Sets a retention policy unit multiplier.
	 * @param unitCount unit multiplier
	 */

	void setRetentionUnitCount(long unitCount);

	/**
	 * Sets the unit of measurement for the unit multiplier.
	 * @param timeUnit the unit of measurement to use.
	 */
	void setRetentionUnit(TimeUnits.Unit timeUnit);

	/**
	 * Sets the purge time of the day in HHMI format, where HH is the hours of the day and MI is the minutes of the hour.
	 * @param purgeTimeOfDay the purge time of the day.
	 */
	void setPurgeTimeOfDay(String purgeTimeOfDay);
	
	/**
	 * Sets the name of the hierarchy for which this policy is being defined.
	 * @param hierarchyName the name of the hierarchy for which this policy is being defined.
	 */
	void setHierarchyName(String hierarchyName);

}
