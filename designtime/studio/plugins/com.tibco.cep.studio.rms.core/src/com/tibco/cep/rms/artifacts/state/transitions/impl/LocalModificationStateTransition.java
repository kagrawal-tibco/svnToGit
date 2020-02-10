/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.transitions.impl;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactLocalModificationState;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactSummaryStateManager;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;

/**
 * @author aathalye
 *
 */
public class LocalModificationStateTransition extends AbstractStateTransition {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#effectTransition(com.tibco.cep.rms.artifacts.state.IArtifactStateOwner, com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent)
	 */
	
	public void effectTransition(SummaryStateChangeEvent stateChangeEvent) {
		IFile resourceFile = stateChangeEvent.getResourceFile();
		if (resourceFile == null) {
			//This transition does not apply
			return;
		}
		//Check event type
		int eventType = stateChangeEvent.getEventType();
		
		if (eventType == SummaryStateChangeEvent.LOCAL_ARTIFACT_MODIFY) {
			//Check if it was not locally created
			String artifactPath = stateChangeEvent.getResourcePath();
			String project = stateChangeEvent.getProject();
			//Check if it exists
			
			//This document should not have been remotely added or should have 
			//been modified after successful commit
			ArtifactSummaryEntry summaryEntry = 
				stateManager.getArtifactEntry(project, artifactPath, ArtifactChanger.REMOTE);
			
			summaryEntry = (summaryEntry != null) ? summaryEntry 
					                              : stateManager.getArtifactEntry(project, 
					                            		                          artifactPath, 
					                            		                          ArtifactChanger.LOCAL, 
					                            		                          true);
			if (summaryEntry != null) {
				toState = new ArtifactLocalModificationState();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#executeActions()
	 */
	
	public void executeActions(StateContext stateContext) {
		toState.executeEntryAction(stateContext);
		toState = null;
	}

	/**
	 * @param stateManager
	 */
	public LocalModificationStateTransition(ArtifactSummaryStateManager stateManager) {
		super(stateManager);
	}
}
