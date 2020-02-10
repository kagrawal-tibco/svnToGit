package com.tibco.cep.studio.util.logger.problems;

/**
 * 
 * @author smarathe
 *
 */
public class OverlappingRangeProblemEvent extends ProblemEvent {

	private int problemRuleId;

	public OverlappingRangeProblemEvent(String errorCode, String message,
			Object resource, int problemRuleId, int severity) {
		super(errorCode, message, resource, problemRuleId, severity);
		this.problemRuleId = problemRuleId;
	}

	public OverlappingRangeProblemEvent(String errorCode, String message,
			String details, Object resource, int severity) {
		super(errorCode, message, details, resource, severity);
		// TODO Auto-generated constructor stub
	}

	public OverlappingRangeProblemEvent(String projectName, String errorCode,
			String message, String details, Object resource, int location,
			int severity) {
		super(projectName, errorCode, message, details, resource, location, severity);
		// TODO Auto-generated constructor stub
	}

	public final int getProblemRuleId() {
		return problemRuleId;
	}
	
}
