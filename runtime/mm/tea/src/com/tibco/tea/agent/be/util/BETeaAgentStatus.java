package com.tibco.tea.agent.be.util;

/**
 * This enum is used for different status of BusinessEvents .
 * 
 * @author dijadhav
 *
 */
public enum BETeaAgentStatus {
	/**
	 * Status if application,host, instance or agent is running
	 */
	RUNNING("Running"),

	STOPPED("Stopped"),

	UNREACHABLE("Unreachable"),

	DEPLOYED("Deployed"),

	NEEDsDEPLOYMENT("Needs Deployment"),

	NEEDREDEPLOYMENT("Needs Re-deployment"),

	UNDEPLOYED("Undepolyed"),

	STARTING("Starting"),

	DEPLOYING("Deploying"),

	UNDEPLOYING("UNDeploying"),

	STOPPING("Stopping"),

	OUTOFSYNC("Out of Sync"),

	REDEPLOYED("ReDeployed"),

	RESUMING("Resuming"),

	OTHER("Other"),

	NEED_DEPLOYMENT_OR_REDEPLOYMENT("Needs Deployment/Re-deployment"),
	
	REACHABLE("Reachable"), SUSPENDED("Suspended");

	private String status;

	/**
	 * @param status
	 */
	private BETeaAgentStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
}
