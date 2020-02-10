/**
 * 
 */
package com.tibco.cep.studio.util.logger.problems;

/**
 * @author aathalye
 *
 */
public class ConflictingActionsProblemEvent extends ProblemEvent implements IMarkableProblemEvent, IAutofixableProblemEvent {
	
	private int problemRuleId;

	/**
	 * @param errorCode
	 * @param message
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public ConflictingActionsProblemEvent(String errorCode, String message,
			Object resource, int problemRuleId, int severity) {
		super(errorCode, message, resource, problemRuleId, severity);
		this.problemRuleId = problemRuleId;
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param details
	 * @param resource
	 * @param severity
	 */
	public ConflictingActionsProblemEvent(String errorCode, String message,
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
	public ConflictingActionsProblemEvent(String projectName, String errorCode,
			String message, String details, Object resource, int location,
			int severity) {
		super(projectName, errorCode, message, details, resource, location, severity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the problemRuleId
	 */
	public final int getProblemRuleId() {
		return problemRuleId;
	}
	
	
}
