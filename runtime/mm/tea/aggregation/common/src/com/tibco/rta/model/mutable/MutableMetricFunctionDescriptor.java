package com.tibco.rta.model.mutable;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.MetricFunctionDescriptor;

/**
 * 
 * A mutable interface to model a metric function descriptor.
 *
 */
public interface MutableMetricFunctionDescriptor extends MutableFunctionDescriptor, MetricFunctionDescriptor {
	
	/**
	 * Set to true if this is a multivalued computation.
	 * @param multiValued
	 */
	void setMultiValued(boolean multiValued);
	
	/**
	 * Set the data type of the metric computation.
	 * @param dataType
	 */
	void setMetricDataType(DataType dataType);

}