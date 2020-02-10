package com.tibco.rta;

import java.io.Serializable;

/**
 *
 * This represents the computed metric.
 * @param <N> The data type of the metric.
 *  
 */

public interface Metric<N> extends Serializable {
	
	
	/**
	 * Gets its primary key
	 * @return the {@link Key}
	 */
	Key getKey();
	
	/**
	 * Sets its primary key
	 * @param key
	 */
	void setKey (Key key);
	
	/**
	 * Gets the value of the dimension that this metric represents.
	 * @return
	 */
	Object getDimensionValue();
	
	/**
	 * Get the associated descriptor
	 * @return
	 */
	MetricValueDescriptor getDescriptor();
	
	/**
	 * A descriptor associated with this metric like schema, cube, etc.
	 * @param descriptor
	 */
	
	void setDescriptor(MetricValueDescriptor descriptor);
	
	/**
	 * 
	 * @return return true if its a multivalue metric, false otherwise
	 */
	boolean isMultiValued();

	/**
	 * Deep copy this metric node.
	 * @return a new deep copied metric node.
	 */
	Metric<N> deepCopy();
	
	/**
	 * Timestamp in milliseconds when this metric was created.
	 * @return creation time in milliseconds
	 */
	long getCreatedTime();
	
	/**
	 * Timestamp in milliseconds when this metric was updated.
	 * @return modification time in milliseconds.
	 */
	long getLastModifiedTime();

	
}
