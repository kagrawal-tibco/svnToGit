package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.ReceiveTaskProperty;

/**
 * This class is sued to hold the general property of receive task.
 * 
 * @author dijadhav
 * 
 */
public class ReceiveTaskGeneralProperty extends GeneralProperty implements ReceiveTaskProperty{

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5499329860649018519L;
	
	/**
	 * Boolean value which indicates the checkpoint is enabled or not.
	 */
	private boolean isCheckPoint;

	/**
	 * Variable for selected resource.
	 */
	private String resource;
	
	/**
	 * Variable for key expression.
	 */
	private String keyExpression;

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

	/**
	 * @return the keyExpression
	 */
	public String getKeyExpression() {
		return keyExpression;
	}

	/**
	 * @param keyExpression the keyExpression to set
	 */
	public void setKeyExpression(String keyExpression) {
		this.keyExpression = keyExpression;
	}

}
