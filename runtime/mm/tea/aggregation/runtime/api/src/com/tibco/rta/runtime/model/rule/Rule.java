package com.tibco.rta.runtime.model.rule;

import java.util.Collection;

import com.tibco.rta.model.rule.RuleDef;

/**
 * 
 * Runtime representation of a rule.
 *
 */

public interface Rule {
	
	/**
	 * Get the rule name.
	 * @return the rule name.
	 */
	String getName();

	/**
	 * Return the corresponding design time artefact RuleDef
	 * 
	 * @return
	 */
	RuleDef getRuleDef();
	
	Action getSetAction(String actionName);
	
	Action getClearAction(String actionName);
	
	Collection<Action> getSetActions();
	
	Collection<Action> getClearActions();
}