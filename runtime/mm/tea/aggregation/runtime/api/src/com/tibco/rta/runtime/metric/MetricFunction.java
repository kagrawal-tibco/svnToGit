package com.tibco.rta.runtime.metric;

import com.tibco.rta.Fact;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.runtime.model.MetricNode;

/**
 * Providers of metric computations should implement this interface. The metric computation engine  
 * will call into this interface's compute method in order to acquire its metric value.
 *
 */
public interface MetricFunction<N> {
	
	/**
	 * Perform state initialization for the entire computation when a new {@code Fact} is asserted.
	 * 
	 * @param fact The newly asserted fact
	 * @param startNode Some cases framework passes startNode of the hierarchy chain.
	 * @param metricFunctionDescriptor The descriptor associated with this metric computation.
	 * @throws Exception The implementation may throw an exception
	 * @see MetricDescriptor
	 */
	void init(Fact fact, Measurement measurement, MetricNode startNode, DimensionHierarchy dh) throws Exception;

}