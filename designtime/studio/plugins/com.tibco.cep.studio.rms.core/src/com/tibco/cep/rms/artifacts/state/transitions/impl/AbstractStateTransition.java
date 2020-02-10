/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.transitions.impl;

import com.tibco.cep.rms.artifacts.state.IArtifactSummaryState;
import com.tibco.cep.rms.artifacts.state.impl.ArtifactSummaryStateManager;
import com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition;

/**
 * @author aathalye
 *
 */
public abstract class AbstractStateTransition implements IArtifactStateTransition {

	protected IArtifactSummaryState fromState;
	
	protected IArtifactSummaryState toState;
	
	protected ArtifactSummaryStateManager stateManager;
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#getFromState()
	 */
	
	/**
	 * @param stateManager
	 */
	protected AbstractStateTransition(ArtifactSummaryStateManager stateManager) {
		this.stateManager = stateManager;
	}

	public IArtifactSummaryState getFromState() {
		return fromState;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.rms.artifacts.state.transitions.IArtifactStateTransition#getToState()
	 */

	public IArtifactSummaryState getToState() {
		return toState;
	}

}
