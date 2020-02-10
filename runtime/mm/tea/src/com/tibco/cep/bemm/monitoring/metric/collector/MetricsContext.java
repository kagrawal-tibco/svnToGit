package com.tibco.cep.bemm.monitoring.metric.collector;


public interface MetricsContext {

	Object getTupleValue(String key);
	
	void setTuple(String name, Object value);
}
