package com.tibco.rta.model.rule;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import com.tibco.rta.model.rule.impl.ActionDefImpl;


/**
 * 
 * An action function with execution constraints.
 *
 */

@JsonDeserialize(as=ActionDefImpl.class)
public interface ActionDef extends Serializable{

	/**
	 * Get the associated execution constraint.
	 * @return the associated constraint.
	 */
	InvokeConstraint getConstraint();
	
	/**
	 * Get the associated function descriptor
	 * @return the associated function descriptor.
	 */
	ActionFunctionDescriptor getActionFunctionDescriptor();
	
	/**
	 * Get the action name.
	 * @return the name.
	 */
	String getName();
	
	
	String getAlertLevel();
	
	RuleDef getRuleDef();

}