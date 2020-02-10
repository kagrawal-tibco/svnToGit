package com.tibco.rta;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.model.impl.MetricValueDescriptorImpl;

import java.io.Serializable;

/**
 * This holds the context information of a {@code MetricNode}
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MetricValueDescriptorImpl.class, name = "metricValueDescriptor")})
public interface MetricValueDescriptor extends Serializable {

	/**
	 * Gets the schema name.
	 *
	 * @return the schema name
	 */
	String getSchemaName();
	
	/**
	 * Gets the cube name.
	 *
	 * @return the cube name
	 */
	String getCubeName();
	
	/**
	 * Gets the measurement name.
	 *
	 * @return the measurement name
	 */
	String getMeasurementName();
	
	/**
	 * Gets the dimension hierarchy name.
	 *
	 * @return the dimension hierarchy name
	 */
	String getDimHierarchyName();
	
	/**
	 * Gets the dimension name.
	 *
	 * @return the dimension name
	 */
	String getDimensionName();
}