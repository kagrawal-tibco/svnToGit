package com.tibco.rta.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.rta.MetricValueDescriptor;

/**
 * The Interface MetricValue.
 */
public class MetricValueDescriptorImpl implements MetricValueDescriptor {
	
	private static final long serialVersionUID = 8893469151645326544L;
	
	protected String schemaName;
	protected String cubeName;
	protected String measurementName;
	protected String dimHierarchyName;
	protected String dimensionName;

	public MetricValueDescriptorImpl() {
	}

	public MetricValueDescriptorImpl(String dimensionName, String dimHierarchyName,
			String cubeName, String schemaName, String measurementName) {
		this.dimensionName = dimensionName;
		this.dimHierarchyName = dimHierarchyName;
		this.cubeName = cubeName;
		this.schemaName = schemaName;
		this.measurementName = measurementName;
	}

	@Override
	public String getSchemaName() {
		return schemaName;
	}
	
	@Override
	public String getCubeName() {
		return cubeName;
	}

	@Override
    @JsonIgnore
	public String getMeasurementName() {
		return measurementName;
	}
	
	@Override
	public String getDimHierarchyName() {
		return dimHierarchyName;
	}
	
	@Override
	public String getDimensionName() {
		return dimensionName;
	}

//	@Override
//	public MetricFunctionDescriptor getMetricDescriptor() {
//		return metricFunctionDescriptor;
//	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	public void setCubeName(String cubeName) {
		this.cubeName = cubeName;
	}

	public void setMeasurementName(String measurementName) {
		this.measurementName = measurementName;
	}
	
	public void setDimHierarchyName(String dimHierarchyName) {
		this.dimHierarchyName = dimHierarchyName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	public String toString() {
		return "{" + schemaName + ", " + cubeName + ", " + dimHierarchyName + ", " + dimensionName + ", " + measurementName + "}";
	}

}