package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MultiValueMetric;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.RtaNodeContext;
import com.tibco.rta.service.persistence.memory.InMemoryConstant;
import com.tibco.rta.util.IOUtils;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;

public class MetricMemoryTuple implements MemoryTuple, MutableMetricNode {

	private static final long serialVersionUID = 1L;
	private MetricNode wrapper;
	private MetricKey metricKey;
	private MemoryKey mKey;
	private Set<String> attrNames = new HashSet<String>();
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public MetricMemoryTuple(MetricNode mNode) {
		wrapper = mNode;
		mKey = RuntimeModelConvertor.getMemoryKey(mNode.getKey());
		metricKey = (MetricKey) mNode.getKey();
		attrNames.add(InMemoryConstant.DIMENSION_LEVEL_FIELD);
		attrNames.add(InMemoryConstant.DIMENSION_LEVEL_NO);
		attrNames.addAll(mNode.getMetricNames());
		attrNames.addAll(metricKey.getDimensionNames());
	}

	@Override
	public MemoryKey getMemoryKey() {
		return mKey;
	}

	@Override
	public void setAttribute(String attrName, Object value) {
		attributes.put(attrName, value);
		attrNames.add(attrName);
	}

	@Override
	public Object getAttributeValue(String attrName) {
		try {
			if (attrName.equals(InMemoryConstant.DIMENSION_LEVEL_FIELD)) {
				return metricKey.getDimensionLevelName();
			} else if (attrName.equals(InMemoryConstant.DIMENSION_LEVEL_NO)) {
				DimensionHierarchy dh = ModelRegistry.INSTANCE.getRegistryEntry(metricKey.getSchemaName()).getCube(metricKey.getCubeName()).
						getDimensionHierarchy(metricKey.getDimensionHierarchyName());
				return dh.getLevel(metricKey.getDimensionLevelName());
			} else if (metricKey.getDimensionValue(attrName) != null) {
				return metricKey.getDimensionValue(attrName);
			} else if (wrapper.getMetric(attrName) != null) {
				Metric metric = wrapper.getMetric(attrName);
				Object value = null;
				if (!metric.isMultiValued()) {
					SingleValueMetric svm = (SingleValueMetric) metric;
					value = svm.getValue();
				} else {
					MultiValueMetric mvm = (MultiValueMetric) metric;
					List<Object> vals = mvm.getValues();
					value = IOUtils.serialize((ArrayList) vals);
				}
				return value;
			}
		} catch (Exception e) {
			// log exception
		}
		return attributes.get(attrName);
	}

	@Override
	public Set<String> getAttributeNames() {
		return attrNames;
	}

	@Override
	public void clearAttributes() {
		attributes.clear();
	}

	@Override
	public RtaNode getParent() {
		return wrapper.getParent();
	}

	@Override
	public Key getParentKey() {
		return wrapper.getParentKey();
	}

	@Override
	public Browser<Fact> getFactBrowser() {
		return wrapper.getFactBrowser();
	}

	@Override
	public Browser<? extends RtaNode> getChildNodeBrowser() {
		return wrapper.getChildNodeBrowser();
	}

	@Override
	public boolean isNew() {
		return wrapper.isNew();
	}

	@Override
	public <N> Metric<N> getMetric(String metricName) {
		return wrapper.getMetric(metricName);
	}

	@Override
	public Collection<String> getMetricNames() {
		return wrapper.getMetricNames();
	}

	@Override
	public RtaNodeContext getContext(String metricName) {
		return wrapper.getContext(metricName);
	}

	@Override
	public String getDimensionHierarchyName() {
		return wrapper.getDimensionHierarchyName();
	}

	@Override
	public <T extends Fact> Browser<T> getChildFactsBrowser(List<MetricFieldTuple> orderByList) {
		return wrapper.getChildFactsBrowser(orderByList);
	}

	@Override
	public boolean isDeleted() {
		return wrapper.isDeleted();
	}

    @Override
    public boolean hasAttribute(String attributeName) {
        return attrNames.contains(attributeName);
    }

    @Override
	public MetricNode deepCopy() {
		return wrapper.deepCopy();
	}

	@Override
	public Key getKey() {
		return wrapper.getKey();
	}

	@Override
	public void setMemoryKey(MemoryKey key) throws Exception {
		this.mKey = key;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof MetricMemoryTuple)) {
			return false;
		}
		MetricMemoryTuple otherNode = (MetricMemoryTuple) object;
		return wrapper.equals(otherNode.getWrappedObject());
	}

	@Override
	public int hashCode() {
		return wrapper.hashCode();
	}

	@Override
	public MetricNode getWrappedObject() {
		return wrapper;
	}
	
	@Override
	public String toString() {
		return wrapper.getKey().toString();
	}

	@Override
	public long getCreatedTime() {
		return wrapper.getCreatedTime();
	}

	@Override
	public long getLastModifiedTime() {
		return wrapper.getLastModifiedTime();
	}
	
	public void setCreatedTime(long createdTime) {
		((MutableMetricNode)wrapper).setCreatedTime(createdTime);
	}

	public void setLastModifiedTime(long lastModifiedTime) {
		((MutableMetricNode)wrapper).setLastModifiedTime(lastModifiedTime);
	}

	@Override
	public <N> void setMetric(String name, Metric<N> metric) {
		((MutableMetricNode)wrapper).setMetric(name, metric);
	}

	@Override
	public void addFact(Fact fact) throws Exception {
		((MutableMetricNode)wrapper).addFact(fact);		
	}

	@Override
	public void setContext(String metricName, RtaNodeContext context) {
		((MutableMetricNode)wrapper).setContext(metricName, context);
	}

	@Override
	public void setKey(Key key) {
		((MutableMetricNode)wrapper).setKey(key);
	}

	@Override
	public void setParentKey(Key parentKey) {
		((MutableMetricNode)wrapper).setParentKey(parentKey);
	}

	@Override
	public void setIsNew(boolean isNew) {
		((MutableMetricNode)wrapper).setIsNew(isNew);
	}

	@Override
	public void setDeleted(boolean isDeleted) {
		((MutableMetricNode)wrapper).setDeleted(isDeleted);
	}

	@Override
	public boolean isProcessed() {
		return wrapper.isProcessed();
	}

	@Override
	public void setProcessed(boolean isProcessed) {
		((MutableMetricNode)wrapper).setProcessed(isProcessed);
		
	}
}
