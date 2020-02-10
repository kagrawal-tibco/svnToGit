package com.tibco.rta.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.Metric;
import com.tibco.rta.query.impl.MetricResultTupleImpl;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * A ResultSet object to hold a metric values by name in query results.
 *
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MetricResultTupleImpl.class, name = "metricResultTuple")})
public interface MetricResultTuple extends Serializable {
	
	/**
	 * Returns a metric for a given metric name.
	 * 
	 * @param name Name of the metric.
	 * @return the corresponding Metric.
	 */
	Metric getMetric(String name);
	
	/**
	 * A list of metric names that this result set holds.
	 * 
	 * @return A list of metric names that this result set holds.
	 */
	String[] getMetricNames();
	
	/**
	 * The state of the metric node.
	 * 
	 * @return the state of the metric node. 
	 */
	MetricEventType getMetricEventType();


	/**
	 * Returns all the metrics in the result set
	 * 
	 * @return list of all the metrics
	 */
    Map<String, Metric> getMetrics();
}
