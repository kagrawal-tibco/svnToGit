package com.tibco.rta.query;

/**
 * 
 * Type of the metric change event.
 *
 */
public enum MetricEventType {
	/**
	 * Indicates new metric node.
	 */
	NEW, 
	/**
	 * Indicates an updated metric node.
	 */
	UPDATE, 
	/**
	 * Indicates a deleted metric node.
	 */
	DELETE,
}
