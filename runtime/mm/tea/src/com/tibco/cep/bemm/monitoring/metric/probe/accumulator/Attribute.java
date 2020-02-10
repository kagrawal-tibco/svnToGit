package com.tibco.cep.bemm.monitoring.metric.probe.accumulator;

import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.TargetMapping;

public class Attribute {
	private String name;
	private Object value;
	private TargetMapping targetMapping;

	public Attribute() {

	}
	
	public Attribute(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public Attribute(String name, Object value,TargetMapping targetMapping) {
		super();
		this.name = name;
		this.value = value;
		this.targetMapping=targetMapping;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public TargetMapping getTargetMapping() {
		return targetMapping;
	}

	public void setTargetMapping(TargetMapping targetMapping) {
		this.targetMapping = targetMapping;
	}

	@Override
	public String toString() {
		return "Attribute [name=" + name + ", value=" + value + "]";
	}

}
