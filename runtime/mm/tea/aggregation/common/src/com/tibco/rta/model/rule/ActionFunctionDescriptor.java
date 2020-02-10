package com.tibco.rta.model.rule;

import java.util.Collection;

import com.tibco.rta.model.FunctionDescriptor;

/**
 * 
 * Describes an action function to be used by design time for modeling a rule
 * 
 *
 */

public interface ActionFunctionDescriptor extends FunctionDescriptor {
	
	/**
	 * A list of function parameters and their values.
	 * @return a list of function parameter and their values
	 */
	Collection<FunctionParamValue> getFunctionParamValues();
	
	/**
	 * Add a function parameter and its value to the action.
	 * @param value the value to add.
	 */
	void addFunctionParamValue(FunctionParamValue value);
		
}
