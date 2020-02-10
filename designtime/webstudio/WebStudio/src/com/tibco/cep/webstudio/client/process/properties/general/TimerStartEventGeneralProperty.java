package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.TimerStartEventProperty;

/**
 * This class is used to populate the general properties of timer start event.
 * 
 * @author dijadhav
 * 
 */
public class TimerStartEventGeneralProperty extends StartEventGeneralProperty implements TimerStartEventProperty{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -5824788278922548228L;

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
