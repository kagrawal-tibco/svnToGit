package com.tibco.cep.bemm.model.impl;

/**
 * Enum for different types of agent.
 * 
 * @author dijadhav
 *
 */
public enum AgentType {
	CACHE("cache"), INFERENCE("inference"), QUERY("query"), PROCESS("process"), DASHBOARD("dashboard");

	/**
	 * @param type
	 */
	private AgentType(String type) {
		this.type = type;
	}

	private String type;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
