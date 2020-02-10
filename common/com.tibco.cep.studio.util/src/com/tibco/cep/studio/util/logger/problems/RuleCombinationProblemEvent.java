/**
 * 
 */
package com.tibco.cep.studio.util.logger.problems;

/**
 * @author aathalye
 *
 */
public class RuleCombinationProblemEvent extends ProblemEvent implements IMarkableProblemEvent {

	/**
	 * @param errorCode
	 * @param message
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public RuleCombinationProblemEvent(String errorCode, String message,
			Object resource, int location, int severity) {
		super(errorCode, message, resource, location, severity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param details
	 * @param resource
	 * @param severity
	 */
	public RuleCombinationProblemEvent(String errorCode, String message,
			String details, Object resource, int severity) {
		super(errorCode, message, details, resource, severity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param projectName
	 * @param errorCode
	 * @param message
	 * @param details
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public RuleCombinationProblemEvent(String projectName, String errorCode,
			String message, String details, Object resource, int location,
			int severity) {
		super(projectName, errorCode, message, details, resource, location, severity);
		// TODO Auto-generated constructor stub
	}
	
	
}
