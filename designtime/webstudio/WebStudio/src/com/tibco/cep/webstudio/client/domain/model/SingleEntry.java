package com.tibco.cep.webstudio.client.domain.model;

public class SingleEntry extends DomainEntry {
	private String value;

	public SingleEntry() {
		value = "";
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
