package com.tibco.rta.runtime.model.rule;

import java.util.Collection;
import java.util.Map;

import com.tibco.rta.model.FunctionDescriptor.FunctionParamValue;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.runtime.model.MetricNodeEvent;

/**
 * 
 * A runtime rule action interface. Implementation should provide an instance of this interface
 * via the ActionHandlerContext.getAction interface.
 * 
 *
 */
public interface Action {
	
	/**
	 * Gets the associated context handler.
	 * @return the context handler.
	 */
	ActionHandlerContext getActionHandlerContext();
	
	/**
	 * Perform the action. It is called by the engine when thier associated rules filter criteria is passed.
	 * 
	 * @param rule the rule that triggered this action.
	 * @param node the node event that triggered this action.
	 * @throws Exception
	 */
	void performAction(Rule rule, MetricNodeEvent node) throws Exception;

	/**
	 * When the action class is initialized by the framework, this method is used to set the action function parameter.
	 */
	void addFunctionParamVal(FunctionParamValue val);

	/**
	 * Returns all the function parameter values.
	 * @return the associated function parameter value.
	 */
	Collection<FunctionParamValue> getFunctionParamValues();
	
	/**
	 * Returns the values for the given parameter
	 * @param paramName the parameter to use.
	 * @return the associated value.
	 */
	FunctionParamValue getFunctionParamValue(String paramName);

	/**
	 * Set the action definition
	 * @param actionDef the action definition to associate.
	 */
	void setActionDef(ActionDef actionDef);
	
	/**
	 * Gets the associated action definition
	 * @return
	 */
	ActionDef getActionDef();
	
	/**
	 * Gets the name of the action, if any.
	 * @return the action name.
	 */
	String getName();

    /**
   	 * Gets the type of the action, if any.
     * e.g : Email/Log/Custom
   	 * @return the action type.
   	 */
   	String getAlertType();

	/**
	 * Returns true if this is a Set action, false if it is a Clear action
	 * @return
	 */
	boolean isSetAction();

	/**
	 * Set to true if this is a Set action
	 * @param isSetAction
	 */
	void setSetAction(boolean isSetAction);
	

	/**
	 * Gets the associated Alert Text. This is used by the system to store as part of the Alert logs.
	 * @return
	 */
	String getAlertText();

	/**
	 * Sets the alert text. This should be called by overriding Action implementations.
	 * @param text
	 */
//	void setAlertText(String text);
	
	
	/**
	 * Gets the alert level from the underlying ActionDef
	 * @return the alert level for this action.
	 */
	String getAlertLevel();

	/**
	 * Gets the alert details such as other supplementary information such as email ids, etc. This is a string which is specific to each action.
	 * @return Gets the alert details
	 */
	String getAlertDetails();
}
