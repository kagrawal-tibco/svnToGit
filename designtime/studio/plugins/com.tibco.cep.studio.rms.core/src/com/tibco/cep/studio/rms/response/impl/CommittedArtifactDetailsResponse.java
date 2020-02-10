/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class CommittedArtifactDetailsResponse implements IResponse {
	
	private CommittedArtifactDetails committedArtifactDetails;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	
	public Object getResponseObject() {
		return committedArtifactDetails;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	
	public void holdResponseObject(Object responseObject) {
		if (responseObject instanceof CommittedArtifactDetails) {
			this.committedArtifactDetails = (CommittedArtifactDetails)responseObject;
		}
	}
}
