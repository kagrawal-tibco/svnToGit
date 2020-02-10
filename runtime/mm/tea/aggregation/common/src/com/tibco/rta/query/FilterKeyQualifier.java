package com.tibco.rta.query;

/**
 * 
 * Qualifier for filter keys and used in filter/query.
 * 
 */
public enum FilterKeyQualifier {
	
	/**
	 * Specifies that the key provided in a query is to be treated as the name of a metric computation.
	 */
	MEASUREMENT_NAME,
	
	/**
	 * Specifies that the key provided in a query is to be treated as the name of a dimension.
	 */
	DIMENSION_NAME,
	
}