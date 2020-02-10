package com.tibco.rta.model.rule;

/**
 * Rule transitions. Help in deciding when to perform actions.
 * 
 * 
 *
 */
public enum RuleTransition {
	/**
	 * false to false transition.
	 */
	FALSE_TRUE,
	
	/**
	 * true to false transition.
	 */
	TRUE_FALSE,
	
	/**
	 * true to true transition.
	 */
	TRUE_TRUE,
	
	/**
	 * false to false transition.
	 */
	FALSE_FALSE,
}
