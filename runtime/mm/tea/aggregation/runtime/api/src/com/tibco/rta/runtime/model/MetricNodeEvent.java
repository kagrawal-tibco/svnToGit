package com.tibco.rta.runtime.model;

import java.io.Serializable;

import com.tibco.rta.query.MetricEventType;

/**
 * 
 * An interface used by the rules/actions services to access the metric changes.
 *
 */
public interface MetricNodeEvent extends Serializable {
	
	/**
	 * Get the metric node associated with this change event.
	 * @return the associated metric node.
	 */
	MetricNode getMetricNode();
	
	/**
	 * Type of change event.
	 * @return the change event type.
	 */
	MetricEventType getEventType();

}
