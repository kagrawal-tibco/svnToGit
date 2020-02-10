package com.tibco.cep.webstudio.client.model.ruletemplate;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model for representing Domain Models
 * 
 * @author Vikram Patil
 */
public class DomainInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	private Map<String, String> valuesMap = new LinkedHashMap<String, String>();

	public DomainInfo() {
		super();
	}

	public DomainInfo(String type) {
		this.setType(type);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void addValue(String id, String value) {
		this.valuesMap.put(id, value);
	}

	public Map<String, String> getValues() {
		return this.valuesMap;
	}
}
