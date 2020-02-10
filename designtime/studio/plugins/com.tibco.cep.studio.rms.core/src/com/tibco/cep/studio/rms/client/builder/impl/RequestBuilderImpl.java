/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import java.util.List;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.client.IRequestBuilderInput;
import com.tibco.cep.studio.rms.client.RequestBuilder;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.model.ArtifactCommitCompletionMetaData;
import com.tibco.cep.studio.rms.model.ArtifactCommitMetaData;
import com.tibco.cep.studio.rms.model.ArtifactRevisionMetadata;
import com.tibco.cep.studio.rms.model.ArtifactStatusChangeDetails;

/**
 * @author aathalye
 *
 */
public class RequestBuilderImpl implements RequestBuilder {
	
	private OperationType operationType;
	
	public RequestBuilderImpl(final OperationType operationType) {
		this.operationType = operationType;
	}

	@SuppressWarnings("unchecked")
	public Object buildRequest(final Object input) throws Exception {
		IRequestBuilderInput builderInput = null;
		switch(operationType) {
		case CHECKIN_ARTIFACT:
			if (!(input instanceof ArtifactCommitMetaData)) {
				throw new IllegalArgumentException("Illegal Builder input");
			}
			builderInput = new ArtifactCommitRequestBuilderInput((ArtifactCommitMetaData)input);
			break;
		case CHECKIN_COMPLETE:
			if (!(input instanceof ArtifactCommitCompletionMetaData)) {
				throw new IllegalArgumentException("Illegal Builder input");
			}
			builderInput = new ArtifactsCommitCompletionRequestBuilderInput((ArtifactCommitCompletionMetaData)input);
			break;	
		case STATUS_CHANGE:
			if (!(input instanceof List)) {
				throw new IllegalArgumentException("Illegal Builder input");
			}
			builderInput = new ArtifactStatusChangeRequestBuilderInput((List<ArtifactStatusChangeDetails>)input);
			break;
		
		case ARTIFACT_CONTENTS:
			if (!(input instanceof Artifact)) {
				throw new IllegalArgumentException("Illegal Builder input");
			}
			builderInput = new ArtifactContentsRequestBuilderInput((Artifact)input);
			break;
			
		case REVISION_CONTENTS:
			if (!(input instanceof ArtifactRevisionMetadata)) {
				throw new IllegalArgumentException("Illegal Builder input");
			}
			builderInput = new ArtifactsRevisionContentRequestBuilderInput((ArtifactRevisionMetadata)input);
			break;
		}
		if (builderInput != null) {
			return builderInput.buildRequest();
		}
		return null;
	}
	
}
