package com.tibco.cep.bemm.common.enums;

/**
 * Actions performed in BE Tea agent
 * 
 * @author dijadhav
 *
 */
public enum BETEAAgentAction {
	CREATE_INSTANCE("Create Instance"), DEPLOY_INSTANCE("Deploy Instance"), UNDEPLOY_INSTANCE("Undeploy Instance"), REDEPLOY_INSTANCE(
			"Redeploy Instance"), STOP_INSTANCE("Stop Instance"), START_INSTANCE("Start Instance"), UPDATE_INSTANCE(
			"Update Instance");

	/**
	 * @param action
	 * @param description
	 */
	private BETEAAgentAction(String action) {
		this.action = action;
	}

	private String action;

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

}
