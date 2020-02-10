/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.transitions.impl;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactLocalRemovalState;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactSummaryStateManager;

/**
 * @author aathalye
 *
 */
public class LocalRemovalStateTransition extends AbstractStateTransition {

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
		
		if (eventType == SummaryStateChangeEvent.LOCAL_ARTIFACT_REMOVE) {
			toState = new ArtifactLocalRemovalState();
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
	public LocalRemovalStateTransition(ArtifactSummaryStateManager stateManager) {
		super(stateManager);
		// TODO Auto-generated constructor stub
	}
	

}
