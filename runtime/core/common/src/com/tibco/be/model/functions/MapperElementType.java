package com.tibco.be.model.functions;


/**
 *
 * @.category public-api
 */
public enum MapperElementType {
	XSLT("xslt"),
	XPATH("xpath"),
	UNKNOWN("unknown");
	
	private String typeValue;
	private MapperElementType(String type) {
		this.typeValue = type;
	}
	
	/**
	 *
	 * @.category public-api
	 */	
	public String getTypeValue() { return typeValue; }
	
	public String toString() { return typeValue; }
}
