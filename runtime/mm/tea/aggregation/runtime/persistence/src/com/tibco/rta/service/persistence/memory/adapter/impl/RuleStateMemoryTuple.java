package com.tibco.rta.service.persistence.memory.adapter.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.rta.MetricKey;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.store.persistence.model.MemoryKey;
import com.tibco.store.persistence.model.MemoryTuple;

public class RuleStateMemoryTuple implements MemoryTuple, RuleMetricNodeState {

	private RuleMetricNodeState wrapper;
	private MetricMemoryTuple metricNodeTuple;
	private RuleStateMemoryKey rKey;
	private Set<String> attrNames = new HashSet<String>();
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public RuleStateMemoryTuple(RuleMetricNodeState ruleNode, RuleStateMemoryKey rKey) {
		wrapper = ruleNode;
		metricNodeTuple = new MetricMemoryTuple(ruleNode.getMetricNode());
		this.rKey = rKey;
		attrNames.addAll(metricNodeTuple.getAttributeNames());
	}

	@Override
	public MemoryKey getMemoryKey() {
		return rKey;
	}

	@Override
	public void clearAttributes() {
		attributes.clear();
	}

	@Override
	public void setAttribute(String attrName, Object value) {
		attributes.put(attrName, value);
		attrNames.add(attrName);
	}

	@Override
	public Object getAttributeValue(String attrName) {
		if (attributes.get(attrName) != null) {
			return attributes.get(attrName);
		}
		return metricNodeTuple.getAttributeValue(attrName);
	}

	@Override
	public Set<String> getAttributeNames() {
		return attrNames;
	}

	@Override
	public RuleMetricNodeStateKey getKey() {
		return wrapper.getKey();
	}

	@Override
	public int getCount() {
		return wrapper.getCount();
	}

	@Override
	public void setCount(int setCount) {
		wrapper.setCount(setCount);
	}

	@Override
	public long getScheduledTime() {
		return wrapper.getScheduledTime();
	}

	@Override
	public void setScheduledTime(long actionTime) {
		wrapper.setScheduledTime(actionTime);
	}

	@Override
	public long getLastFireTime() {
		return wrapper.getLastFireTime();
	}

	@Override
	public void setLastFireTime(long timestamp) {
		wrapper.setLastFireTime(timestamp);
	}

	@Override
	public boolean isNew() {
		return wrapper.isNew();
	}

	@Override
	public void setIsNew(boolean isNew) {
		wrapper.setIsNew(isNew);
	}

	@Override
	public MetricKey getSetConditionKey() {
		return wrapper.getSetConditionKey();
	}

	@Override
	public void setSetConditionKey(MetricKey setConditionKey) {
		wrapper.setSetConditionKey(setConditionKey);
	}

	@Override
	public MetricKey getClearConditionKey() {
		return wrapper.getClearConditionKey();
	}

	@Override
	public void setClearConditionKey(MetricKey clearConditionKey) {
		wrapper.setClearConditionKey(clearConditionKey);
	}

	@Override
	public MetricNode getMetricNode() {
		return wrapper.getMetricNode();
	}

	@Override
	public void setMetricNode(MetricNode metricNode) {
		wrapper.setMetricNode(metricNode);
	}

	@Override
	public boolean isStored() {
		return wrapper.isStored();
	}

	@Override
	public void setStored(boolean stored) {
		wrapper.setStored(stored);
	}

	@Override
	public void setMemoryKey(MemoryKey key) throws Exception {
		if (key instanceof RuleStateMemoryKey) {
			rKey = (RuleStateMemoryKey) key;
		}
	}

	@Override
	public boolean equals(Object object) {
		// equals not implemented for ruleMetricNodeState
		if (!(object instanceof RuleStateMemoryTuple)) {
			return false;
		}
		RuleStateMemoryTuple otherNode = (RuleStateMemoryTuple) object;
		if ((otherNode.getMemoryKey() == null && this.getMemoryKey() != null) || this.getMemoryKey() == null && otherNode.getMemoryKey() != null) {
			return false;
		}
		return this.getMemoryKey().equals(otherNode.getMemoryKey());
	}

	@Override
	public boolean hasAttribute(String attributeName) {
		return attrNames.contains(attributeName);
	}

	@Override
	public int hashCode() {
		// hashCode not implemented for ruleMetricNodeState
		return getMemoryKey().hashCode();
	}

	@Override
	public Object getWrappedObject() {
		return wrapper;
	}

}
