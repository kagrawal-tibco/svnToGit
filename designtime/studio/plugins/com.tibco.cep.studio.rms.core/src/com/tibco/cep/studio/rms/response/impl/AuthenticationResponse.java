/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.AbstractResponse;

/**
 * @author aathalye
 *
 */
public class AuthenticationResponse extends AbstractResponse {
	
	private Object token;
	
	
	public Object getResponseObject() {
		return token;
	}

	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof String)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.token = responseObject;
	}
	
}
