package com.tibco.rta.model;


/**
 * 
 * Describes a function used for computation
 *
 */
public interface MetricFunctionDescriptor extends FunctionDescriptor {

	/**
	 * Is this a multivalued metric
	 * @return return true if it is multivalued, false otherwise
	 */
	boolean isMultiValued();

	/**
	 * Get the metrics datatype
	 * @return
	 */
	DataType getMetricDataType();
	

}