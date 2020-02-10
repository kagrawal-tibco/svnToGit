package com.tibco.rta.runtime.model.rule;

/**
 * 
 * Lifecycle managed class that all action handlers must extend.
 */
abstract public class AbstractActionHandlerContext implements ActionHandlerContext {

	/**
	 * Name of the action
	 * 
	 */
	protected String name;

	/**
	 * Get the name of the action.
	 */
	@Override
	public String getName() {
		return name;
	}
	

}
