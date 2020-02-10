/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.impl;

import com.tibco.cep.rms.artifacts.state.IArtifactSummaryState;
import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;

/**
 * @author aathalye
 *
 */
public class ArtifactRemoteAdditionState implements IArtifactSummaryState {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeEntryAction()
	 */
	
	public void executeEntryAction(StateContext context) {
		RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
		String project = context.getProject();
		String artifactPath = context.getPathOfArtifact();
		String containerPath = context.getArtifactContainerPath();
		String artifactExtension = context.getArtifactExtension();
		summaryManager.createArtifactEntry(project, 
				                           artifactPath, 
				                           containerPath,
				                           artifactExtension,
				                           context.getUpdateTime(),
				                           context.getCommittedVersion(),
				                           ArtifactOperation.ADD,
				                           ArtifactChanger.REMOTE);
		//Remove any entry for same path which was local delete
		summaryManager.removeArtifactEntry(project, artifactPath, ArtifactChanger.LOCAL, false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeExitAction()
	 */
	
	public void executeExitAction(StateContext context) {
		// TODO Auto-generated method stub

	}

}
