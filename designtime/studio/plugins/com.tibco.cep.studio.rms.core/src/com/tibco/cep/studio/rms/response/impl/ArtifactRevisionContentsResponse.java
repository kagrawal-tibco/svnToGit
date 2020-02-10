/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.model.ArtifactRevisionMetadata;
import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactRevisionContentsResponse implements IResponse {
	
	private ArtifactRevisionMetadata artifactRevisionMetadata;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	@Override
	public Object getResponseObject() {
		return artifactRevisionMetadata;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	@Override
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof ArtifactRevisionMetadata)) {
			throw new IllegalArgumentException("Illegal contents for this response");
		}
		artifactRevisionMetadata = (ArtifactRevisionMetadata)responseObject;
	}

}
