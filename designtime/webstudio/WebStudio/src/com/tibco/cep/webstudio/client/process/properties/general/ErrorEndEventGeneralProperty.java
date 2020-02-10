package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.ErrorEndEventProperty;

/**
 * This class is used to hold the general properties of error end event.
 * 
 * @author dijadhav
 * 
 */
public class ErrorEndEventGeneralProperty extends EndEventGeneralProperty implements ErrorEndEventProperty{
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 4222907710681309645L;
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
