/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import java.util.List;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class ProjectArtifactsNamesResponse implements IResponse {
	
	private List<Artifact> projectArtifactsList;

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	@Override
	public Object getResponseObject() {
		return projectArtifactsList;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof List)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.projectArtifactsList = (List<Artifact>)responseObject;
	}

}
