package com.tibco.rta.impl;

/**
 * @author bgokhale
 * 
 * Implementation of a multivalued metric
 * 
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.rta.Metric;
import com.tibco.rta.MultiValueMetric;

public class MultiValueMetricImpl<N> extends BaseMetricImpl<N> implements MultiValueMetric<N> {

	private static final long serialVersionUID = 1319938374775520372L;

	protected List<N> values = new ArrayList<N>();
	
	public MultiValueMetricImpl() {
		values = new ArrayList<N>(0);
	}

	@Override
	public List<N> getValues() {
		return values;
	}

	@Override
	public boolean isMultiValued() {
		return true;
	}
	
	public String toString() {
		String s = "[";
		for (N n : values) {
			s += n + ", ";
		}
		s += ']';
		return super.toString() + s;
	}

//	@Override
	public void addValue(N value) {
		values.add(value);
	}

//	@Override
	public void setValues(Collection<N> values) {
		this.values = new ArrayList(values);
	}

	@Override
	public Metric<N> deepCopy() {
		MultiValueMetricImpl<N> cloned = new MultiValueMetricImpl<N>();
		//descriptor is immutable
		cloned.descriptor = descriptor;
		//this is also immutable (mostly a primitive)
		cloned.dimensionValue = dimensionValue;
		//this is also immutable
		cloned.key = key;
		//this is also immutable
		cloned.metricName = metricName;
		//list contains immutables (mostly a primitive)
		cloned.values = new ArrayList<N>(values);

        //Set created and updated timestamps
        cloned.lastModifiedTime = lastModifiedTime;

        cloned.createdTime = lastModifiedTime;

		return cloned;
	}
}
