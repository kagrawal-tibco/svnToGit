package com.tibco.rta;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.impl.MetricKeyImpl;

import java.util.List;

/**
 * 
 * Each computed metric has a unique key identified by its dimension values.
 *
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MetricKeyImpl.class, name = "metricKey")})
public interface MetricKey extends Key {
	
	/**
	 * The associated schema name.
	 * @return the associated schema name.
	 */
	String getSchemaName();

	/**
	 * The associated cube name.
	 * @return he associated cube name.
	 */
	String getCubeName();

	/**
	 * The associated hierarchy name.
	 * @return the associated hierarchy name.
	 */
	String getDimensionHierarchyName();
	
	/**
	 * The associated dimension level name.
	 * @return the associated dimension level name.
	 */
	String getDimensionLevelName();

	/**
	 * Return the list of dimension names.
	 * @return the list of dimension names.
	 */
	List<String> getDimensionNames();
	
	/**
	 * Get the associated dimension value/
	 * @param dimensionName name for which value is desired.
	 * @return the associated dimension value.
	 */
	Object getDimensionValue(String dimensionName);
	
	/**
	 * Adds a dimension value to the key. Key comprises of key/value pairs.
	 * This method is used to build the entire key from its constitient key/values.
	 * @param dimensionName the dimension name.
	 * @param dimensionValue its associated value.
	 */
	void addDimensionValueToKey(String dimensionName, Object dimensionValue);

}