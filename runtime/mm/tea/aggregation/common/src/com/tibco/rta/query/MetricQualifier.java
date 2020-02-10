package com.tibco.rta.query;


/**
 * Qualifier for keys and values used in filter/query.
 * 
 * 
 */

public enum MetricQualifier {
	/**
	 * Specifies that a value provided in a query is to be treated as a dimension level.
	 */
	DIMENSION_LEVEL,
	
	DIMENSION_LEVEL_NO,
	
	CREATED_TIME,
	
	UPDATED_TIME,
	
	IS_PROCESSED;
}
