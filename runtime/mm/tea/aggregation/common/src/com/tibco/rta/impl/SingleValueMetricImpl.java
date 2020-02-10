package com.tibco.rta.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.rta.Metric;
import com.tibco.rta.SingleValueMetric;

/**
 * 
 * @author bgokhale
 *
 * @param <N>
 * 
 * Implemnentation of a SingleValueMetric
 */

public class SingleValueMetricImpl<N> extends BaseMetricImpl<N> implements SingleValueMetric<N> {

	private static final long serialVersionUID = 7735346396130407243L;

	protected N value;
	
	public SingleValueMetricImpl() {
	}
	
	@Override
	public N getValue() {
		return value;
	}

	public void setValue(N value) {
		this.value = value;
	}

	@Override
    @JsonIgnore
	public boolean isMultiValued() {
		return false;
	}
	
	public String toString() {
		return super.toString() + ", value=" + value;
	}

	@Override
	public Metric<N> deepCopy() {
		SingleValueMetricImpl<N> cloned = new SingleValueMetricImpl<N>();
		//descriptor is immutable
		cloned.descriptor = descriptor;
		//this is also immutable (mostly a primitive)
		cloned.dimensionValue = dimensionValue;
		//this is also immutable
		cloned.key = key;
		//this is also immutable
		cloned.metricName = metricName;
		//this is also immutable (mostly a primitive)
		cloned.value = value;

        //Set created and updated timestamps
        cloned.lastModifiedTime = lastModifiedTime;

        cloned.createdTime = lastModifiedTime;

		return cloned;
	}
}
