package com.tibco.rta.runtime.model.rule;

import java.util.Properties;

import com.tibco.rta.model.rule.ActionDef;


/**
 * One time initialization of Action functions.
 * 
 * For example, an email action may need to establish a connection to the email server only once
 * or a webservice invocation may need a 1 time initialization of the service framework
 * 
 * The engine will initialize these during its startup sequence.
 * 
 *
 */
public interface ActionHandlerContext {
	
	/**
	 * Called by the engine during starup
	 * 
	 * @param configuration
	 */
	void init(Properties configuration);
	
	/**
	 * Called by the engine during shutdown.
	 * 
	 */
	void stop();
	
	/**
	 * Get name
	 * @return
	 */
	String getName();
	
	/**
	 * Implementors are free to call action.
	 * 
	 * @return
	 */
//	Action getAction();

	Action getAction(Rule rule, ActionDef actionDef);
	
}
