package com.tibco.rta.query;

import com.tibco.rta.query.filter.Filter;

/**
 * Query definition using filters
 */
public interface QueryByFilterDef extends QueryDef {

    /**
     * Get the root level filter
     * @return
     */
	Filter getFilter();

    /**
     * Set root filter.
     * @param filter
     */
	void setFilter(Filter filter);
	
	/**
	 * Sets the schema name.
	 * @param schemaName
	 */
	void setSchemaName(String schemaName);

	/**
	 * Set the cube name.
	 * @param cubeName
	 */
	void setCubeName(String cubeName);

	/**
	 * Set the hierarchy name.
	 * @param hierarchyName
	 */
	void setHierarchyName(String hierarchyName);
	
	/**
	 * Set the measurement name.
	 * @param measurementName
	 */
	void setMeasurementName(String measurementName);
}
