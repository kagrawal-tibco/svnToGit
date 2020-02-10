/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class LogoutResponse implements IResponse {
	
	private String loggedoutUsername;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	@Override
	public Object getResponseObject() {
		return loggedoutUsername;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	@Override
	public void holdResponseObject(Object responseObject) {
		this.loggedoutUsername = (String)responseObject;
	}
}
