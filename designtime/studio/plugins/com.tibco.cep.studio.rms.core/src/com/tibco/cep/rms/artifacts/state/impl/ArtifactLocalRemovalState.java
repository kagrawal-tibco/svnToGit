/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.impl;

import java.util.Date;

import com.tibco.cep.rms.artifacts.state.IArtifactSummaryState;
import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;

/**
 * @author aathalye
 *
 */
public class ArtifactLocalRemovalState implements IArtifactSummaryState {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeEntryAction()
	 */
	
	public void executeEntryAction(StateContext context) {
		
		RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
		
		String project = context.getProject();
		String artifactToRemovePath = context.getPathOfArtifact();
		String containerPath = context.getArtifactContainerPath();
		
		// Retrieve this artifact irrespective of commit status/artifact changer
		ArtifactSummaryEntry summaryEntry = 
				summaryManager.getSummaryEntry(context.getProject(), 
						context.getPathOfArtifact(), null,true,true,true);
		
		// To remove an artifact, it must be present
		if (summaryEntry != null){
			// If locally added simply remove it
			if (summaryEntry.getArtifactChanger() == ArtifactChanger.LOCAL && summaryEntry.getOperationType() == ArtifactOperation.ADD){
				summaryManager.removeArtifactEntry(project, 
                        artifactToRemovePath, 
                        ArtifactChanger.LOCAL,
                        true);
			} else {
				String artifactExtension = context.getArtifactExtension();
				Date updateTime = summaryEntry.getArtifact().getUpdateTime();
				String commitVersion = summaryEntry.getArtifact().getCommittedVersion();
				//Create a new entry in removed artifacts as this
				//could be a potential commit entry.
				summaryManager.createArtifactEntry(project, 
						                           artifactToRemovePath, 
						                           containerPath,
	                                               artifactExtension,
	                                               updateTime,
	                                               commitVersion,
	                                               ArtifactOperation.DELETE,
	                                               ArtifactChanger.LOCAL);
				// Remove the existing entry
				summaryManager.removeArtifactEntry(project, summaryEntry, true);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeExitAction()
	 */
	
	public void executeExitAction(StateContext context) {
		// TODO Auto-generated method stub

	}

}
