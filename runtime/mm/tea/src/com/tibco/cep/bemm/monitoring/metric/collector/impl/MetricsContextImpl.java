package com.tibco.cep.bemm.monitoring.metric.collector.impl;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.bemm.monitoring.metric.collector.MetricsContext;

public class MetricsContextImpl implements MetricsContext {

	protected Map<String,Object> contextMap = new HashMap<String,Object>();
	
	@Override
	public Object getTupleValue(String name) {
		return contextMap.get(name);
	}

	@Override
	public void setTuple(String name, Object value) {
		contextMap.put(name, value);
	}

}
