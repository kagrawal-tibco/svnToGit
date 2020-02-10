package com.tibco.cep.studio.util.logger.problems;

/**
 * Implementors of this interface can register with the
 * <code>ProblemEventManager</code> to receive notification
 * when a problem event occurs (for instance, syntax validation fails)
 * 
 * @author rhollom
 *
 */
public interface IProblemEventListener {

	/**
	 * Implementors are notified of the problem events and given the following information
	 * 
	 * @param event the ProblemEvent that contains information about this event
	 */
	public void handleProblemEvent(ProblemEvent event);
	
	/**
	 * Implementors are instructed to clear all problems (i.e. in a view)
	 */
	public void clearAllProblems();
	
	/**
	 * Implementors are instructed to clear all problems with the given problem code (i.e. in a view)
	 * @param problemCode
	 */
	public void clearProblems(String problemCode);
	
}
