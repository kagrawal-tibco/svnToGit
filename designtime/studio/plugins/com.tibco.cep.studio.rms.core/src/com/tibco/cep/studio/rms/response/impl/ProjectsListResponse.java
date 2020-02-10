/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.AbstractResponse;

/**
 * @author aathalye
 *
 */
public class ProjectsListResponse extends AbstractResponse {
	
	private Object projectsList;

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#getResponseObject()
	 */
	public Object getResponseObject() {
		return projectsList;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.response.IResponse#holdResponseObject(java.lang.Object)
	 */
	public void holdResponseObject(Object responseObject) {
		if (!(responseObject instanceof String[])) {
			throw new IllegalArgumentException("Illegal argument for this response");
		}
		this.projectsList = responseObject;
	}

}
