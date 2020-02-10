/**
 * 
 */
package com.tibco.cep.diagramming.utils;

/**
 * @author aathalye
 *
 */
public enum GatewayType {
	XOR("XOR"),
	OR("OR"),
	FORK("FORK"),
	JOIN("JOIN"),
	AND("AND"),
	COMPLEX("COMPLEX"),
	FILTER("FILTER"),
	GROUP("GROUP"),
	CONDITION("CONDITION"),
	PIPE("PIPE");
	
	private final String name;
	
	GatewayType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
	
	
}
