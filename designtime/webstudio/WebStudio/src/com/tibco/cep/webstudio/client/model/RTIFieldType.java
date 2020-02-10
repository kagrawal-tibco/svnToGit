package com.tibco.cep.webstudio.client.model;

/**
 * Enum for field type.  
 * @author apsharma
 *
 */
public enum RTIFieldType {

	STRING("text","String"),
	
	INTEGER("integer","integer"),
	
	LONG("integer","long"),
	
	DOUBLE("float","double"),
	
	BOOLEAN("boolean","boolean"),
	
	DATETIME("datetime","DateTime"),
	
	CONCEPT_EVENT(null,"Concept/Event");
	
	private String value;
	private String displayText;
	
	private RTIFieldType(String value, String displayText) {
		this.value = value;
		this.displayText = displayText;
	}

	public String getDisplayText() {
		return displayText;
	}

	public String getValue() {
		return value;
	}
}
