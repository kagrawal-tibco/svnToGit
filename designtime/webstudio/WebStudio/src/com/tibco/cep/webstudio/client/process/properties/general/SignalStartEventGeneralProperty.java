package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.SignalStartEventProperty;

/**
 * This class is used to hold the general properties of signal start event.
 * 
 * @author dijadhav
 * 
 */
public class SignalStartEventGeneralProperty extends
		StartEventGeneralProperty implements SignalStartEventProperty{

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 7389897633714682355L;

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
