package com.tibco.tea.agent.be.util;

/**
 * Operating Systems supported to deploy the service instance
 * 
 * @author dijadhav
 *
 */
public enum SupportedOS {
	Windows("Windows Based"), Unix("OS/X,Unix/Linux Based");
	private String description;

	/**
	 * @param description
	 */
	private SupportedOS(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
