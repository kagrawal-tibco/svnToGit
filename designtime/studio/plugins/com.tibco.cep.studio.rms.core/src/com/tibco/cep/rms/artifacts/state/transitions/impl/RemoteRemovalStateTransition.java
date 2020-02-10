/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.transitions.impl;

import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactRemoteRemovalState;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactSummaryStateManager;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;

/**
 * @author aathalye
 *
 */
public class RemoteRemovalStateTransition extends AbstractStateTransition {

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#effectTransition(com.tibco.cep.rms.artifacts.state.IArtifactStateOwner, com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent)
	 */
	public void effectTransition(SummaryStateChangeEvent stateChangeEvent) {
		//Check event type
		int eventType = stateChangeEvent.getEventType();

		if (eventType != SummaryStateChangeEvent.REMOTE_ARTIFACT_REMOVE) {
			return;
		}
		//Check what is the contained resource
		Artifact artifact = stateChangeEvent.getArtifact();
		if (artifact == null) {
			//This transition does not apply
			return;
		}
		String project = stateChangeEvent.getProject();
		String artifactPath = artifact.getArtifactPath();
		
		//Check if artifact exists, irrespective of commit status/artifact changer
		ArtifactSummaryEntry summaryEntry = 
				RMSArtifactsSummaryManager.getInstance().getSummaryEntry(project, artifactPath, null, false, true, true);

		if (summaryEntry != null) {
			toState = new ArtifactRemoteRemovalState();
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
	public RemoteRemovalStateTransition(ArtifactSummaryStateManager stateManager) {
		super(stateManager);
		// TODO Auto-generated constructor stub
	}
	
}
