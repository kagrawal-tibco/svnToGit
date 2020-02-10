package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.MessageStartEventProperty;

/**
 * This class is used to hold the general properties of message start event.
 * 
 * @author dijadhav
 * 
 */
public class MessageStartEventGeneralProperty extends StartEventGeneralProperty implements MessageStartEventProperty{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 4309698647991935966L;

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
