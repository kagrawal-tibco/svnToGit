package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.MessageEndEventProperty;

/**
 * This class is used to hold the general properties of message end event.
 * 
 * @author dijadhav
 * 
 */
public class MessageEndEventGeneralProperty extends EndEventGeneralProperty implements MessageEndEventProperty{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -3383661193617280847L;

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
