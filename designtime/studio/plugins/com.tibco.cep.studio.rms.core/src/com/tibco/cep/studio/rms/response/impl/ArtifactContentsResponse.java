/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.response.AbstractResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactContentsResponse extends AbstractResponse {
	
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
		if (!(responseObject instanceof Artifact)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.artifactsHolder = responseObject;
	}

}
