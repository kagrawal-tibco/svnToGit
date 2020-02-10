package com.tibco.cep.studio.util.logger.problems;

/**
 * Implementors of this interface can register with the
 * <code>ProblemEventManager</code> to receive notification
 * after all {@link IProblemEventListener}s have been
 * notified of the ProblemEvent<br>
 * Note: The use of the term "post" (as opposed to "pre") has
 * been avoided, so as not to confuse this "post" with the
 * act of posting problem events
 * 
 * @author rhollom
 *
 */
public interface IProblemEventFinishedListener {

	/**
	 * Implementors are notified of the problem event after all registered
	 * {@link IProblemEventListener}s have been notified
	 * 
	 * @param event the ProblemEvent that contains information about this event
	 */
	public void problemEventFinished(ProblemEvent event);
	
	/**
	 * Implementors are instructed that all problems have
	 * been cleared
	 */
	public void clearAllProblemsFinished();
	
	/**
	 * Implementors are instructed that all problems of the
	 * given problemCode have been cleared
	 * 
	 * @param problemCode
	 */
	public void clearProblemsFinished(String problemCode);
	
}
