package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.util.List;

import com.tibco.rta.MetricKey;
import com.tibco.store.persistence.model.MemoryKey;

public class MetricMemoryKey implements MemoryKey, MetricKey {

	private static final long serialVersionUID = 1L;
	private MetricKey wrapper;

	public MetricMemoryKey(MetricKey mKey) {
		this.wrapper = mKey;
	}

	@Override
	public int hashCode() {
		return wrapper.hashCode();
	}

	@Override
	public boolean equals(Object keyObj) {
		if (!(keyObj instanceof MetricMemoryKey)) {
			return false;
		}

		MetricMemoryKey key = (MetricMemoryKey) keyObj;

		return wrapper.equals(key.getWrappedObject());
	}

	boolean strEql(String str1, String str2) {
		if (str1 == str2) {
			return true;
		} else if (str1 != null && str1.equals(str2)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return wrapper.toString();
	}

	public MetricKey getMetricKey() {
		return wrapper;
	}

	@Override
	public String getSchemaName() {
		return wrapper.getSchemaName();
	}

	@Override
	public String getCubeName() {
		return wrapper.getCubeName();
	}

	@Override
	public String getDimensionHierarchyName() {
		return wrapper.getDimensionHierarchyName();
	}

	@Override
	public String getDimensionLevelName() {
		return wrapper.getDimensionLevelName();
	}

	@Override
	public List<String> getDimensionNames() {
		return wrapper.getDimensionNames();
	}

	@Override
	public Object getDimensionValue(String dimensionName) {
		return wrapper.getDimensionValue(dimensionName);
	}

	@Override
	public void addDimensionValueToKey(String dimensionName, Object dimensionValue) {
		wrapper.addDimensionValueToKey(dimensionName, dimensionValue);
	}

	@Override
	public Object getWrappedObject() {
		return wrapper;
	}

	@Override
	public int compareTo(Object other) {
		return toString().compareTo(other.toString());
	}
}
