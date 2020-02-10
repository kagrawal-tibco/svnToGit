package com.tibco.rta.runtime.model;

import java.util.Collection;
import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;

/**
 * A wrapper over a {@link Metric} instance. It provides convenience methods to
 * iterate over its associated facts. This might by required by the metric computation function.
 */
public interface MetricNode extends RtaNode {
	
	/**
	 * 
	 * @return The encapsulated {@code Metric} object
	 */
	<N> Metric<N> getMetric(String metricName);
	
	
	/**
	 * A list of metric names that this mode stores.
	 * @return The list.
	 */
	Collection<String> getMetricNames();

	
	/**
	 * Returns the context associated with the given metric name.
	 * @param name the metric name for which context is desired.
	 * @return the context associated with the name.
	 */
	
	RtaNodeContext getContext(String metricName);
	
	/**
	 * 
	 * @return The dimension hierarchy to which this node belongs to
	 */
	String getDimensionHierarchyName();
	
	
	/**
	 * 
	 * @param orderByList 
	 * @return get all associated facts for this metric node 
	 */
	<T extends Fact> Browser<T> getChildFactsBrowser(List<MetricFieldTuple> orderByList);

	
	boolean isDeleted();
	
	MetricNode deepCopy();


	boolean isProcessed();


}