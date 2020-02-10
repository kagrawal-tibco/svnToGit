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
public class ArtifactLocalModificationState implements IArtifactSummaryState {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeEntryAction()
	 */
	
	public void executeEntryAction(StateContext context) {
		RMSArtifactsSummaryManager summaryManager = RMSArtifactsSummaryManager.getInstance();
		String project = context.getProject();
		String artifactPath = context.getPathOfArtifact();
		String containerPath = context.getArtifactContainerPath();
		String artifactExtension = context.getArtifactExtension();
		
		ArtifactSummaryEntry summaryEntry = summaryManager.getSummaryEntry(project, artifactPath, null, true, true, true);
		Date updateTime = summaryEntry.getArtifact().getUpdateTime();
		String commitVersion = summaryEntry.getArtifact().getCommittedVersion();
		
		//Only remove remote changer entry
		summaryManager.removeArtifactEntry(project, 
				                           artifactPath, 
				                           ArtifactChanger.REMOTE, 
				                           true);
		
		/**
		 * Check for existing modified entry successfully committed and reverse its status
		 */
		summaryEntry = summaryManager.getSummaryEntry(project, 
				                       artifactPath, 
				                       ArtifactChanger.LOCAL,
				                       true);
		if (summaryEntry != null) {
			summaryManager.reverseCommitStatus(project, summaryEntry, true, commitVersion);
			//Remove entry from added section
			summaryManager.removeArtifactEntry(project, summaryEntry, false);
		} 
		//Create new entry in modified list
		summaryManager.createArtifactEntry(project, 
					                       artifactPath, 
					                       containerPath,
					                       artifactExtension,
					                       updateTime,
					                       commitVersion,
					                       ArtifactOperation.MODIFY,
					                       ArtifactChanger.LOCAL);
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.IArtifactSummaryState#executeExitAction()
	 */
	
	public void executeExitAction(StateContext context) {
		// TODO Auto-generated method stub

	}

}
