package com.tibco.tea.agent.be.util;

public enum BEEntityHealthStatus {
	
	ok("ok",1,"Normal"),

	warning("warning",2,"Warning"),

	critical("critical",3,"Critical");
	
	private String healthStatus;
	private int level;
	private String displayName;

	/**
	 * @param healthStatus
	 * @param level
	 */
	private BEEntityHealthStatus(String status,int level,String displayName) {
		this.healthStatus = status;
		this.level=level;
		this.displayName=displayName;
	}

	/**
	 * @return the healthStatus
	 */
	public String getHealthStatus() {
		return healthStatus;
	}
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
}
