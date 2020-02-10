package com.tibco.cep.studio.util.logger.problems;

import java.util.ArrayList;
import java.util.List;

public class ProblemEventManager {
	
	private List<IProblemEventListener> problemEventListeners = new ArrayList<IProblemEventListener>();
	private List<IProblemEventFinishedListener> problemEventFinishedListeners = new ArrayList<IProblemEventFinishedListener>();
	// a cache of all posted problems, so that interested clients can get previously posted problems
//	private List<ProblemEvent> 			problems = new ArrayList<ProblemEvent>();

	private static ProblemEventManager instance;
	
	public static ProblemEventManager getInstance() {
		if (instance == null) {
			instance = new ProblemEventManager();
		}
		return instance;
	}

	/**
	 * Registers a new <code>IProblemEventListener</code>.  Has no effect
	 * if the eventListener has already been registered.
	 * 
	 * @param eventListener an instance of IProblemEventListener
	 */
	public void addProblemEventListener(IProblemEventListener eventListener) {
		if (!problemEventListeners.contains(eventListener)) {
			problemEventListeners.add(eventListener);
		}
	}
	
	/**
	 * Registers a new <code>IProblemEventListener</code>.  Has no effect
	 * if the eventListener has already been registered.
	 * 
	 * @param eventListener an instance of IProblemEventListener
	 */
	public void removeProblemEventListener(IProblemEventListener eventListener) {
		if (problemEventListeners.contains(eventListener)) {
			problemEventListeners.remove(eventListener);
		}
	}
	
	/**
	 * Registers a new <code>IProblemEventFinishedListener</code>.  Has no effect
	 * if the eventListener has already been registered.
	 * 
	 * @param eventListener an instance of IProblemEventFinishedListener
	 */
	public void addProblemEventFinishedListener(IProblemEventFinishedListener eventListener) {
		if (!problemEventFinishedListeners.contains(eventListener)) {
			problemEventFinishedListeners.add(eventListener);
		}
	}
	
	/**
	 * Registers a new <code>IProblemEventFinishedListener</code>.  Has no effect
	 * if the eventListener has already been registered.
	 * 
	 * @param eventListener an instance of IProblemEventFinishedListener
	 */
	public void removeProblemEventFinishedListener(IProblemEventFinishedListener eventListener) {
		if (problemEventFinishedListeners.contains(eventListener)) {
			problemEventFinishedListeners.remove(eventListener);
		}
	}
	
	/**
	 * Post a problem event to the event manager.  The event
	 * manager then notifies all registered {@link IProblemEventListener}
	 * objects
	 *  
	 * @param problemCode the error code of the problem
	 * @param message the description of the problem
	 * @param details additional information about this problem
	 * @param resource the resource on which this problem occurs
	 * @param location the location/line number of the problem in the given resource
	 * @param severity the severity of the problem.  One of ProblemEvent.INFO, ProblemEvent.WARN, ProblemEvent.ERROR
	 */
	public void postProblem(String projectName, String problemCode, String message, String details, Object resource, int location, int severity) {
		fireProblemEvent(projectName, problemCode, message, details, resource, location, severity);
	}
	
	 /**
	 * Post a problem event to the event manager.  The event
	 * manager then notifies all registered {@link IProblemEventListener}
	 * objects
	 *  
	 * @param problemCode the error code of the problem
	 * @param message the description of the problem
	 * @param details additional information about this problem
	 * @param resource the resource on which this problem occurs
	 * @param severity the severity of the problem.  One of ProblemEvent.INFO, ProblemEvent.WARN, ProblemEvent.ERROR
	  */
	public void postProblem(String projectName, String problemCode, String message, String details, Object resource, int severity) {
		fireProblemEvent(projectName, problemCode, message, details, resource, -1, severity);
	}

	/**
	 * Post a problem event to the event manager.  The event
	 * manager then notifies all registered {@link IProblemEventListener}
	 * objects
	 *  
	 * @param problemCode the error code of the problem
	 * @param message the description of the problem
	 * @param resource the resource on which this problem occurs
	 * @param severity the severity of the problem.  One of ProblemEvent.INFO, ProblemEvent.WARN, ProblemEvent.ERROR
	 */
	public void postProblem(String projectName, String problemCode, String message, Object resource, int severity) {
		fireProblemEvent(projectName, problemCode, message, null, resource, -1, severity);
	}
	
	/**
	 * Post a problem event to the event manager.  The event
	 * manager then notifies all registered {@link IProblemEventListener}
	 * objects
	 *  
	 * @param problemCode the error code of the problem
	 * @param message the description of the problem
	 * @param resource the resource on which this problem occurs
	 * @param severity the severity of the problem.  One of ProblemEvent.INFO, ProblemEvent.WARN, ProblemEvent.ERROR
	 */
	public void postProblem(String projectName, String problemCode, String message, Object resource, int location, int severity) {
		fireProblemEvent(makeProblemEvent(projectName, problemCode, message, resource, location, severity));
	}
	
	/**
	 * Make a problem event identical to the one the postProblem method 
	 * (with same signature)would have posted to the event manager.to the event manager.  The event
	 * manager then notifies all registered {@link IProblemEventListener}
	 * objects
	 *  
	 * @param problemCode the error code of the problem
	 * @param message the description of the problem
	 * @param resource the resource on which this problem occurs
	 * @param severity the severity of the problem.  One of ProblemEvent.INFO, ProblemEvent.WARN, ProblemEvent.ERROR
	 */
	public static ProblemEvent makeProblemEvent(String projectName, String problemCode, String message, Object resource, int location, int severity) {
		return new ProblemEvent(projectName, 
				                problemCode, 
				                message, 
				                null, 
				                resource, 
				                location, 
				                severity);
	}
	
	public static ProblemEvent makeProblemEvent(String projectName, String problemCode, String message, String details, Object resource, int location, int severity) {
		return new ProblemEvent(projectName, 
				                problemCode,
				                message,
				                details,
				                resource,
				                location,
				                severity);
	}
	
	/**
	 * Post a problem event to the event manager.  The event
	 * manager then notifies all registered {@link IProblemEventListener}
	 * objects
	 *  
	 * @param event the event to post
	 */
	public void postProblem(ProblemEvent event) {
		fireProblemEvent(event);
	}
	
	
	/**
	 * Notify all {@link IProblemEventListener}s to clear any
	 * cached {@link ProblemEvent}s
	 */
	public void clearAllProblems() {
//		problems.clear();
		
		fireClearEvent(null);
	}

	/**
	 * Notify all {@link IProblemEventListener}s to clear any
	 * cached {@link ProblemEvent}s with a problemCode equal
	 * to problemCode
	 * 
	 * @param problemCode the problemCode to be cleared
	 */
	public void clearProblems(String problemCode) {
//		problems.clear();
		
		fireClearEvent(problemCode);
	}
	
	private synchronized void fireClearEvent(String problemCode) {
		
		for (IProblemEventListener problemEventListener : problemEventListeners) {
			if (problemCode == null) {
				problemEventListener.clearAllProblems();
			} else {
				problemEventListener.clearProblems(problemCode);
			}
		}
		fireClearEventFinished(problemCode);
		
	}

	private synchronized void fireClearEventFinished(String problemCode) {
		for (IProblemEventFinishedListener problemEventListener : problemEventFinishedListeners) {
			if (problemCode == null) {
				problemEventListener.clearAllProblemsFinished();
			} else {
				problemEventListener.clearProblemsFinished(problemCode);
			}
		}
	}

	private synchronized void fireProblemEvent(String projectName, String problemCode, String message,
			String details, Object resource, int location, int severity) {
		
		ProblemEvent event = new ProblemEvent(projectName, problemCode, message, details, resource, location, severity);
		fireProblemEvent(event);
	}
	
	private synchronized void fireProblemEvent(ProblemEvent event) {
//		problems.add(event);
		
		for (IProblemEventListener problemEventListener : problemEventListeners) {
			problemEventListener.handleProblemEvent(event);
		}
		fireProblemEventFinished(event);
	}

	private synchronized void fireProblemEventFinished(ProblemEvent event) {
		for (int i=0; i<problemEventFinishedListeners.size(); i++) {
			IProblemEventFinishedListener problemEventListener = problemEventFinishedListeners.get(i);
			problemEventListener.problemEventFinished(event);
		}
	}

//	public List<ProblemEvent> getProblems() {
//		return problems;
//	}
	
}
