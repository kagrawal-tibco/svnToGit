package com.tibco.rta.runtime.model.rule;

import com.tibco.rta.MetricKey;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;

public interface RuleMetricNodeState {
	
	RuleMetricNodeStateKey getKey();
	
	int getCount();
	
	void setCount(int setCount);
	
	long getScheduledTime();
	
	void setScheduledTime(long actionTime);

	long getLastFireTime();

	void setLastFireTime(long timestamp);
	
	boolean isNew();
	
	void setIsNew (boolean isNew);

	MetricKey getSetConditionKey();

	void setSetConditionKey(MetricKey setConditionKey);

	MetricKey getClearConditionKey();

	void setClearConditionKey(MetricKey clearConditionKey);

	MetricNode getMetricNode();

	void setMetricNode(MetricNode metricNode);
	
	boolean isStored();
	
	void setStored(boolean stored);
	
}
