package com.tibco.rta.runtime.model.rule.impl;

import com.tibco.rta.MetricKey;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;

public class AlertNodeStateImpl extends RuleMetricNodeStateImpl implements AlertNodeState {
	
	//the action that caused this alert. 
	//TODO Use DataObject as much as possible.
	protected Action action;
	
	protected String alertLevel;

	protected String alertDetails;

    protected String alertType;
	
	protected String alertText;
	
	protected String setCondition;
	
	protected String clearCondition;

	protected int totalCount;

	protected String ruleUserName;

	protected String actionName;
	

	public AlertNodeStateImpl (String ruleName, String actionName, MetricKey metricKey) {
		super(ruleName, actionName, metricKey);
	}

	public AlertNodeStateImpl(String ruleName, String actionName,
			MetricKey key2, boolean stored) {
		super(ruleName, actionName, key2);
	}

	public AlertNodeStateImpl (Rule rule, Action action, RuleMetricNodeState rmNode) {

		// shallow clone the incoming.
		this.key = new AlertNodeStateKey(rmNode.getKey().getRuleName(), rmNode.getKey().getActionName(),
				rmNode.getKey().getMetricKey());
		this.setCount = rmNode.getCount();
		this.scheduledTime = rmNode.getScheduledTime(); 
		this.lastFireTime = rmNode.getLastFireTime();
		this.isNew = true;
		this.metricNode = rmNode.getMetricNode();
		this.setConditionKey = rmNode.getSetConditionKey(); 
		this.clearConditionKey = rmNode.getClearConditionKey(); 
		
		this.action = action;
		
		this.alertLevel = action.getActionDef().getAlertLevel();
		
		this.alertText = action.getAlertText();

        this.alertType = action.getAlertType();
		
		this.alertDetails = action.getAlertDetails();
		
		this.setCondition = action.getActionDef().getRuleDef().getSetCondition().toString();
		
		if (action.getActionDef().getRuleDef().getClearCondition() != null) {
			this.clearCondition = action.getActionDef().getRuleDef().getClearCondition().toString();
		}
		
		this.ruleUserName = action.getActionDef().getRuleDef().getUserName();
		
		this.actionName = action.getName();
	}

	@Override
	public String toString() {
		String pattern = "AlertNodeStateImpl [key=[%s], setCount=[%d], scheduledTime=[%d], lastFireTime=[%d], metricNode=[%s], setConditionKey=[%s], clearConditionKey=[%s], stored=[%s]]";
        return String.format(pattern, key, setCount, scheduledTime, lastFireTime, metricNode, setConditionKey, clearConditionKey, stored);
	}

	@Override
	public Action getAction() {
		return action;
	}

	@Override
	public String getAlertLevel() {
		return alertLevel;
	}

	@Override
	public String getAlertDetails() {
		return alertDetails;
	}

    public String getAlertType() {
        return alertType;
    }

	@Override
	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}

    @Override
    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    @Override
	public void setAlertDetails(String alertDetails) {
		this.alertDetails = alertDetails;
	}

	@Override
	public String getRuleUserName() {
		return ruleUserName;
	}

	@Override
	public String getActionName() {
		return actionName;
	}
	@Override
	public void setAlertText(String alertText){
		this.alertText=alertText;
	}
	
	@Override
	public String getAlertText(){
		return alertText;
	}

}
