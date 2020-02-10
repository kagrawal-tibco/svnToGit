package com.tibco.rta.runtime.model.rule.impl;

import java.util.UUID;

import com.tibco.rta.MetricKey;

public class AlertNodeStateKey extends RuleMetricNodeStateKey {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5458092804316748099L;
	
	//All alerts are new and unique.
	protected String id;

	public AlertNodeStateKey(String ruleName, String actionName, MetricKey metricKey) {
		super(ruleName, actionName, metricKey);
		this.id = UUID.randomUUID().toString();
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof AlertNodeStateKey)) {
			return false;
		}
		AlertNodeStateKey key2 = (AlertNodeStateKey) object;

//		return key2.ruleName.equals(ruleName)
//			&& key2.metricKey.equals(metricKey)
//			&& key2.actionName.equals(actionName);
		
		if (id == null && key2.id == null) {
			return true;
		} else if (id != null) {
			return id.equals(key2.id);
		} else if (key2.id != null) {
			return key2.id.equals(id);
		}
		return false;
	}
	
	public int hashcode() {
		if (id != null) {
			return id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	public String getId() {
		return id;
	}

}
