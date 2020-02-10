package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.JavaTaskProperty;

/**
 * This class is used to hold the general properties of java task.
 * 
 * @author dijadhav
 * 
 */
public class JavaTaskGeneralProperty extends GeneralProperty implements JavaTaskProperty{
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 5431541111497721160L;
	
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
