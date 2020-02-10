/**
 * 
 */
package com.tibco.cep.studio.util.logger.problems;

/**
 * @author aathalye
 *
 */
public class DuplicateRuleProblemEvent extends ProblemEvent implements IMarkableProblemEvent, IAutofixableProblemEvent {
	
	private int duplicateRuleID;
	/**
	 * @param errorCode
	 * @param message
	 * @param resource
	 * @param location
	 * @param severity
	 */
	public DuplicateRuleProblemEvent(String errorCode, String message,
			Object resource, int duplicateRuleID, int location, int severity) {
		super(errorCode, message, resource, -1, severity);
		this.duplicateRuleID = duplicateRuleID;
	}
	
	/**
	 * @return the duplicateRuleID
	 */
	public final int getDuplicateRuleID() {
		return duplicateRuleID;
	}
	
	
}
