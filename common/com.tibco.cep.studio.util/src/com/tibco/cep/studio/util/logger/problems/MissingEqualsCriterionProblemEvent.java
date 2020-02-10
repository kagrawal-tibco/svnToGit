package com.tibco.cep.studio.util.logger.problems;

/**
 * @author aathalye
 *
 */
public class MissingEqualsCriterionProblemEvent extends ProblemEvent {
	
	private Object missingEqualsCriterion;

	public MissingEqualsCriterionProblemEvent(String errorCode, String message,
			Object resource, Object expression, String columnName, int severity) {
		super(errorCode, message, resource, -1, severity);
		this.missingEqualsCriterion = expression;
	}

	public MissingEqualsCriterionProblemEvent(String errorCode, String message,
			String details, Object resource, Object expression, String columnName, int severity) {
		super(errorCode, message, details, resource, severity);
		this.missingEqualsCriterion = expression;
	}

	public MissingEqualsCriterionProblemEvent(String projectName,
			String errorCode, String message, String details, Object resource, Object expression,
			String columnName, int severity) {
		super(projectName, errorCode, message, details, resource, -1, severity);
		this.missingEqualsCriterion = expression;
	}

	/**
	 * @return the missingEqualsCriterion
	 */
	public Object getMissingEqualsCriterion() {
		return missingEqualsCriterion;
	}
}
