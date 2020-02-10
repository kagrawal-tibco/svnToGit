/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.transitions.impl;

import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactCommitState;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactSummaryStateManager;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;

/**
 * @author aathalye
 *
 */
public class ArtifactCommitStateTransition extends AbstractStateTransition {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#effectTransition(com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent)
	 */
	
	public void effectTransition(SummaryStateChangeEvent stateChangeEvent) {
		//Check event type
		int eventType = stateChangeEvent.getEventType();
		
		if (eventType != SummaryStateChangeEvent.ARTIFACT_COMMIT) {
			return;
		}
		
		//Check if it was not locally created
		String artifactPath = stateChangeEvent.getResourcePath();
		String project = stateChangeEvent.getProject();
		//Check if it exists
		ArtifactSummaryEntry summaryEntry = 
			stateManager.getArtifactEntry(project, artifactPath, ArtifactChanger.LOCAL);
		//Cant imagine why it could be null
		if (summaryEntry != null) {
			toState = new ArtifactCommitState();
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#executeActions(com.tibco.cep.rms.artifacts.state.StateContext)
	 */
	
	public void executeActions(StateContext stateContext) {
		toState.executeEntryAction(stateContext);
		toState = null;
	}

	/**
	 * @param stateManager
	 */
	public ArtifactCommitStateTransition(ArtifactSummaryStateManager stateManager) {
		super(stateManager);
	}
}
