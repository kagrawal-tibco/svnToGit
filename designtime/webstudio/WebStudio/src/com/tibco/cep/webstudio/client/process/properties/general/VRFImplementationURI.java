package com.tibco.cep.webstudio.client.process.properties.general;

import java.io.Serializable;

/**
 * This class holds the properties of implementation uri.
 * 
 * @author dijadhav
 * 
 */
public class VRFImplementationURI implements Serializable{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2100869091808968435L;

	/**
	 * Variable for implementation uri.
	 */
	private String uri;

	/**
	 * Boolean value which indicates implementation uri is selected or not.
	 */
	private boolean isDeployed;

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the isDeployed
	 */
	public boolean isDeployed() {
		return isDeployed;
	}

	/**
	 * @param isDeployed
	 *            the isDeployed to set
	 */
	public void setDeployed(boolean isDeployed) {
		this.isDeployed = isDeployed;
	}
}
