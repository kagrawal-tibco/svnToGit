package com.tibco.rta.runtime.model.rule;

import com.tibco.rta.MetricKey;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;


public interface AlertNodeState {
	
	
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
	/**
	 * Get the Action associated with this alert.
	 * @return
	 */
	Action getAction();
	
	void setAction(Action action);
	
	String getAlertLevel();

	void setAlertLevel(String alertLevel);

	String getAlertDetails();
	
	void setAlertDetails(String alertDetails);

    String getAlertType();

    void setAlertType(String alertType);

	int getTotalCount();
	
	String getRuleUserName();
	
	String getActionName();
	
	void setAlertText(String alertText);

	String getAlertText();
	
}
