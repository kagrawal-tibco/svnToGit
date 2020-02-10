/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import java.util.List;

import com.tibco.cep.studio.rms.model.ArtifactAuditTrailRecord;
import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactAuditTrailResponse implements IResponse {
	
	private List<ArtifactAuditTrailRecord> auditTrailRecord;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	
	public Object getResponseObject() {
		return auditTrailRecord;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof List)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.auditTrailRecord = (List<ArtifactAuditTrailRecord>)responseObject;
	}

}
