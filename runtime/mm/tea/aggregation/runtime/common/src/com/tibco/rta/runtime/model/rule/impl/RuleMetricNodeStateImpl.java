package com.tibco.rta.runtime.model.rule.impl;

import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;

public class RuleMetricNodeStateImpl implements RuleMetricNodeState {
	
	protected Key key;
	protected int setCount;
	protected long scheduledTime;
	protected long lastFireTime;
	transient protected boolean isNew;
	protected MetricNode metricNode;
	protected MetricKey setConditionKey;
	protected MetricKey clearConditionKey;
	
	protected boolean stored = true;
	
	
	public RuleMetricNodeStateImpl() {
		//for use with sub-class
	}
	
	public RuleMetricNodeStateImpl (String ruleName, String actionName, MetricKey metricKey) {
		key = new RuleMetricNodeStateKey(ruleName, actionName, metricKey);
	}

	public RuleMetricNodeStateImpl(String ruleName, String actionName,
			MetricKey key2, boolean stored) {
		this(ruleName, actionName, key2);
		this.stored = stored;
	}

	@Override
	public RuleMetricNodeStateKey getKey() {
		return (RuleMetricNodeStateKey) key;
	}

	@Override
	public int getCount() {
		return setCount;
	}

	@Override
	public void setCount(int setCount) {
		this.setCount = setCount;
	}

	@Override
	public long getScheduledTime() {
		return scheduledTime;
	}

	@Override
	public void setScheduledTime(long actionTime) {
		this.scheduledTime = actionTime;
	}

	@Override
	public long getLastFireTime() {
		return lastFireTime;
	}
	
	@Override 
	public void setLastFireTime(long timestamp) {
		this.lastFireTime = timestamp;
	}
	
	@Override
	public boolean isNew() {
		return isNew;
	}
	
	@Override
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Override
	public MetricKey getSetConditionKey() {
		return setConditionKey;
	}

	@Override
	public void setSetConditionKey(MetricKey setConditionKey) {
		this.setConditionKey = setConditionKey;
	}

	@Override
	public MetricKey getClearConditionKey() {
		return clearConditionKey;
	}

	@Override
	public void setClearConditionKey(MetricKey clearConditionKey) {
		this.clearConditionKey = clearConditionKey;
	}

	@Override
	public MetricNode getMetricNode() {
		return metricNode;
	}

	@Override
	public void setMetricNode(MetricNode metricNode) {
		this.metricNode = metricNode;
	}
	
	@Override
	public void setStored (boolean stored) {
		this.stored = stored;
	}
	
	@Override
	public boolean isStored() {
		return this.stored;
	}

	@Override
	public String toString() {
		return "RuleMetricNodeStateImpl [key=" + key + ", setCount=" + setCount + ", scheduledTime=" + scheduledTime
				+ ", lastFireTime=" + lastFireTime + ", metricNode=" + metricNode + ", setConditionKey="
				+ setConditionKey + ", clearConditionKey=" + clearConditionKey + ", stored=" + stored + "]";
	}

}
