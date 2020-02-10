package com.tibco.rta.query;

import java.util.List;

/**
 * An interface that allows to build a query.
 */
public interface QueryDef extends BaseQueryDef {
	
	/**
	 * Get the cube name.
	 * @return
	 */
	String getCubeName();

	/**
	 * Get the hierarchy name.
	 * @return
	 */
	String getHierarchyName();


	/**
	 * Get the order by clause.
	 * @return an ordered list representing the sort order.
	 */
	List<MetricFieldTuple> getOrderByTuples();

	

	/**
	 * Add an order by clause to the query.
	 * @param qualifier the qualifier indicating sort order.
	 */
	void addOrderByTuple(MetricQualifier qualifier);
	
	/**
	 * Add an order by clause to the query
	 * @param qualifier the qualifier to use.
	 * @param value the value of the qualifier.
	 */
	void addOrderByTuple(FilterKeyQualifier qualifier, String value);
	
	/**
	 * The result details.
	 * @param measurementName measurement to return.
	 * @param metricName the metric to return.
	 */
	void addMetricName(String measurementName, String metricName);
	
}