package com.tibco.rta.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.rta.Key;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricValueDescriptor;



/**
 * A base method implementation for a metric
 * 
 */
abstract public class BaseMetricImpl<N> implements Metric<N> {

	private static final long serialVersionUID = -6135308799356603440L;
	
	protected MetricValueDescriptor descriptor;

	protected Object dimensionValue;

    /**
     * Declared transient so as to avoid serialization.(JSON)
     */
	protected Key key;

	protected String metricName;
	
	protected long createdTime;
	
	protected long lastModifiedTime;

	public BaseMetricImpl() {

	}

    //Ignore this for explicit json serialization to avoid duplication
    @JsonIgnore
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	
	@Override
    @JsonIgnore
	public MetricValueDescriptor getDescriptor() {
		return descriptor;
	}
	
	@Override
	public void setDescriptor (MetricValueDescriptor descriptor) {
		this.descriptor = descriptor;
	}


	public Object getDimensionValue() {
		return dimensionValue;
	}
	
	public void setDimensionValue(Object dimensionValue) {
		this.dimensionValue = dimensionValue;
	}
	
	@Override
	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public long getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	
	public String toString() {
		return key + ", desc=" + descriptor;
	}

}