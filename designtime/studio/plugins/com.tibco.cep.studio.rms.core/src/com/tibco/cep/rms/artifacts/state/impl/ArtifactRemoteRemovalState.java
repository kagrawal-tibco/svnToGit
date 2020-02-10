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
public class ArtifactRemoteRemovalState implements IArtifactSummaryState {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeEntryAction()
	 */
	
	public void executeEntryAction(StateContext context) {
		RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
		
		String project = context.getProject();
		String artifactToRemovePath = context.getPathOfArtifact();
		
		// Remove the artifact irrespective of the artifact changer 
		summaryManager.removeArtifactEntry(project, 
                artifactToRemovePath, 
                ArtifactChanger.LOCAL,
                true,true);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeExitAction()
	 */
	
	public void executeExitAction(StateContext context) {
		// TODO Auto-generated method stub

	}

}
