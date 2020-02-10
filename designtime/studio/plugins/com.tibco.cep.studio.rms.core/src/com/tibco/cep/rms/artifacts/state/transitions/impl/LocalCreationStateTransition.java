/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.transitions.impl;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactLocalAdditionState;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactSummaryStateManager;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;

/**
 * @author aathalye
 *
 */
public class LocalCreationStateTransition extends AbstractStateTransition {
	
	
	/**
	 * @param stateManager
	 */
	public LocalCreationStateTransition(ArtifactSummaryStateManager stateManager) {
		super(stateManager);
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#effectTransition(com.tibco.cep.rms.artifacts.state.IArtifactStateOwner, com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent)
	 */
	public void effectTransition(SummaryStateChangeEvent stateChangeEvent) {
		//Check event type
		int eventType = stateChangeEvent.getEventType();
		
		if (eventType != SummaryStateChangeEvent.LOCAL_ARTIFACT_CREATE) {
			return;
		}
		//Check what is the contained resource
		IFile resourceFile = stateChangeEvent.getResourceFile();
		if (resourceFile == null) {
			//This transition does not apply
			return;
		}
		//Check that there is no entry already for this artifact with a remote changer
		ArtifactSummaryEntry summaryEntry = 
			stateManager.getArtifactEntry(stateChangeEvent.getProject(), 
					stateChangeEvent.getResourcePath(), ArtifactChanger.REMOTE);
		
		if (summaryEntry == null) {
			toState = new ArtifactLocalAdditionState();
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#executeActions()
	 */
	
	public void executeActions(StateContext stateContext) {
		toState.executeEntryAction(stateContext);
		toState = null;
	}
}
