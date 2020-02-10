/**
 * 
 */
package com.tibco.cep.rms.artifacts.state.transitions;

import com.tibco.cep.rms.artifacts.state.IArtifactSummaryState;
import com.tibco.cep.rms.artifacts.state.StateContext;
import com.tibco.cep.rms.artifacts.state.SummaryStateChangeEvent;

/**
 * @author aathalye
 *
 */
public interface IArtifactStateTransition {
	
	/**
	 * Execute exit action of from state and entry action of to state
	 */
	void executeActions(StateContext stateContext);
	
	IArtifactSummaryState getFromState();
	
	IArtifactSummaryState getToState();
	
	void effectTransition(SummaryStateChangeEvent stateChangeEvent);
}
