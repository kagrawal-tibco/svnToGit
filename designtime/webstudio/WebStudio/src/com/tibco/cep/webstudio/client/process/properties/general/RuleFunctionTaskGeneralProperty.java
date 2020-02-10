package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.RuleFunctionTaskProperty;

/**
 * This class holds the general properties of Script task.
 * 
 * @author dijadhav
 * 
 */
public class RuleFunctionTaskGeneralProperty extends GeneralProperty implements RuleFunctionTaskProperty{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6391430673190067790L;

	/**
	 * Boolean value which indicates the checkpoint is enabled or not.
	 */
	private boolean isCheckPoint;

	/**
	 * Variable for selected resource.
	 */
	private String resource;

	/**
	 * @return the isCheckPoint
	 */
	public boolean isCheckPoint() {
		return isCheckPoint;
	}

	/**
	 * @param isCheckPoint
	 *            the isCheckPoint to set
	 */
	public void setCheckPoint(boolean isCheckPoint) {
		this.isCheckPoint = isCheckPoint;
	}

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource
	 *            the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
}
