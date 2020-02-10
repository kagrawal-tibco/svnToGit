package com.tibco.rta.model.rule;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

/**
 * 
 * An execution constraint for actions.
 *
 */
public interface InvokeConstraint extends Serializable{
	
	/**
	 * 
	 * Enumaration of action execution constraints.
	 *
	 */
	@XmlType
	public enum Constraint {
		/**
		 * Perform the action only once, even if the action condition evaluated to true multiple times.
		 */
		ONCE_ONLY,
		/**
		 * Perform the action each time the rule condition evaluates to true.
		 */
		ALWAYS,
		/**
		 * Upon the first time, start a timer based execution. See TimeBasedConstraint
		 */
		TIMED;
	}

	/**
	 * Get the associated Constraint
	 * @return the associated constraint enumeration
	 */
	Constraint getConstraint();
	
}