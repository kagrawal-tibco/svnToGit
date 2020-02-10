/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class AccessConfigFileRefreshResponse implements IResponse {
	
	private String accessConfigFileContents;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	
	public Object getResponseObject() {
		return accessConfigFileContents;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof String)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.accessConfigFileContents = (String)responseObject;
	}

}
