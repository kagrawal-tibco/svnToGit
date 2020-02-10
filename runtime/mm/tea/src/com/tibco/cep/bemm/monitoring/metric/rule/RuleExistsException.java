package com.tibco.cep.bemm.monitoring.metric.rule;

/**
 * This exception is used to indicate exception while creating a new rule
 * when a rule with same name already exists
 */

public class RuleExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 909307660228007614L;

	/**
	 * Instantiates a new RuleExistsException.
	 *
	 * @param message the message
	 */
	public RuleExistsException(String message) {
		super(message);
	}

	public RuleExistsException(Exception e) {
		super(e);
	}

	public RuleExistsException(Throwable e) {
		super(e);
	}

}
