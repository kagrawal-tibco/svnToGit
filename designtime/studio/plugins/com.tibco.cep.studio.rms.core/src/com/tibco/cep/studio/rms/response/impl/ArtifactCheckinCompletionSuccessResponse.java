/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactCheckinCompletionSuccessResponse implements IResponse {
	
	private String requestId;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	
	public Object getResponseObject() {
		return requestId;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof String)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.requestId = (String)responseObject;
	}

}
