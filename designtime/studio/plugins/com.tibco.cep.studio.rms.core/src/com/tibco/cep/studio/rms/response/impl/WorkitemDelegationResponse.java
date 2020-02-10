/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class WorkitemDelegationResponse implements IResponse {
	
	private DelegationStatus delegationStatus;
	
	public enum DelegationStatus {
		SUCCESS, FAILURE;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	
	public Object getResponseObject() {
		return delegationStatus;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof DelegationStatus)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.delegationStatus = (DelegationStatus)responseObject;
	}
}
