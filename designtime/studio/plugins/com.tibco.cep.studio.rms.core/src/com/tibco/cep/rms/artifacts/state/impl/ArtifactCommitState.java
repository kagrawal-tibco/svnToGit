/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.impl;

import com.tibco.cep.rms.artifacts.state.IArtifactSummaryState;
import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;

/**
 * @author aathalye
 *
 */
public class ArtifactCommitState implements IArtifactSummaryState {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeEntryAction()
	 */
	
	public void executeEntryAction(StateContext context) {
		RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
		String project = context.getProject();
		String artifactPath = context.getPathOfArtifact();
		String commitVersion = context.getCommittedVersion();
		
		//Update commit status
		summaryManager.reverseCommitStatus(project, artifactPath, commitVersion, ArtifactChanger.LOCAL, false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeExitAction()
	 */
	
	public void executeExitAction(StateContext context) {
		// TODO Auto-generated method stub

	}

}
