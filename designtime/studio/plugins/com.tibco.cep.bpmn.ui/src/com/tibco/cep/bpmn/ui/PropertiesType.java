package com.tibco.cep.bpmn.ui;

/**
 * 
 * @author majha
 *
 */
public enum PropertiesType {
	GENERAL_PROPERTIES("general"),
	MAPPING_PROPERTIES("mapping"),
	OUTPUT_PROPERTIES("output"),
	INPUT_PROPERTIES("input"),
	EXTENDED_PROPERTIES("extended"),
	SCRIPT_PROPERTIES("script");
	
	private String name;

	private PropertiesType(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
