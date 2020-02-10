/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import java.util.List;

import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactReviewTaskDetailsResponse implements IResponse {

	private Object artifactsHolder;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#getResponseObject()
	 */
	public Object getResponseObject() {
		return artifactsHolder;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof List)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.artifactsHolder = responseObject;
	}
}
