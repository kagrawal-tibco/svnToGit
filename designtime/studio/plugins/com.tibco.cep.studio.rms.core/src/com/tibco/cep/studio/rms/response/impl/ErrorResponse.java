/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class ErrorResponse implements IResponse {
	
	private com.tibco.cep.studio.rms.model.Error errorObject;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	
	public Object getResponseObject() {
		return errorObject;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof com.tibco.cep.studio.rms.model.Error)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.errorObject = (com.tibco.cep.studio.rms.model.Error)responseObject;
	}
}
