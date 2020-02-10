package com.tibco.rta.service.persistence.memory.adapter.impl;

import com.tibco.rta.MetricKey;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.store.persistence.model.MemoryKey;

public class RuleStateMemoryKey extends RuleMetricNodeStateKey implements MemoryKey {

	private static final long serialVersionUID = 1L;
	private RuleMetricNodeStateKey wrapper;

	public RuleStateMemoryKey(RuleMetricNodeStateKey key) {
		super(key.getRuleName(), key.getActionName(), key.getMetricKey());
		this.wrapper = key;
	}

	@Override
	public int hashCode() {
		return wrapper.hashCode();
	}

	@Override
	public boolean equals(Object keyObj) {
		if (!(keyObj instanceof RuleStateMemoryKey)) {
			return false;
		}
		RuleStateMemoryKey key = (RuleStateMemoryKey) keyObj;

		return wrapper.equals(key.getWrappedObject());
	}

	@Override
	public Object getWrappedObject() {		
		return wrapper;
	}

	@Override
	public String getActionName() {
		return wrapper.getActionName();
	}

	@Override
	public String getRuleName() {
		return wrapper.getRuleName();
	}

	@Override
	public MetricKey getMetricKey() {
		return wrapper.getMetricKey();
	}
	
	@Override
	public String toString() {
		return wrapper.toString();
	}

}
