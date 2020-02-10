/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.model.ArtifactReviewTaskSummary;
import com.tibco.cep.studio.rms.response.AbstractResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactsWorklistResponse extends AbstractResponse {
	
	private Object tasks;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#getResponseObject()
	 */
	public Object getResponseObject() {
		return tasks;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof ArtifactReviewTaskSummary)) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.tasks = responseObject;
	}

}
