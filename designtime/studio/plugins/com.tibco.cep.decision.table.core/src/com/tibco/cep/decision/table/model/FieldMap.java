package com.tibco.cep.decision.table.model;

public class FieldMap {
	private String key;
	private String value;
	
	FieldMap(String key, String value)
	{
		this.key = key;
		this.value = value;
	}
	
	public String getKey()
	{
		return key;
	}
	
	public String getValue()
	{
		return value;
	}
}
