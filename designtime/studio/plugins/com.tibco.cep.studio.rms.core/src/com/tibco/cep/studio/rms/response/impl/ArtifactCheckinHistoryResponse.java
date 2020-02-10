/**
 * 
 */
package com.tibco.cep.studio.rms.response.impl;

import java.util.List;

import com.tibco.cep.studio.rms.model.ArtifactCheckinHistoryEntry;
import com.tibco.cep.studio.rms.response.IResponse;

/**
 * @author aathalye
 *
 */
public class ArtifactCheckinHistoryResponse implements IResponse {
	
	private List<ArtifactCheckinHistoryEntry> checkinHistoryEntries;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.response.IResponse#getResponseObject()
	 */
	@Override
	public Object getResponseObject() {
		return checkinHistoryEntries;
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
		this.checkinHistoryEntries = (List<ArtifactCheckinHistoryEntry>)responseObject;
	}

}
