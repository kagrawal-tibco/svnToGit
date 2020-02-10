/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.AbstractResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactLockResponse extends AbstractResponse {
	
	private Object response;

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#getResponseObject()
	 */
	public Object getResponseObject() {
		return response;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof String)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.response = responseObject;
	}
}
