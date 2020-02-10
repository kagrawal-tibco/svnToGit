package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.SignalEndEventProperty;

/**
 * This method is used to hold the general properties of signal end event.
 * 
 * @author dijadhav
 * 
 */
public class SignalEndEventGeneralProperty extends EndEventGeneralProperty implements SignalEndEventProperty{

	/**
	 * Serail version UID.
	 */
	private static final long serialVersionUID = -5926502358240500817L;

	/**
	 * Variable for selected resource.
	 */
	private String resource;

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
