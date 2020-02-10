/**
 * 
 */
package com.tibco.cep.rms.artifacts.state;

/**
 * @author aathalye
 *
 */
public interface IArtifactSummaryState {
	
	void executeEntryAction(StateContext context);
	
	void executeExitAction(StateContext context);
}
