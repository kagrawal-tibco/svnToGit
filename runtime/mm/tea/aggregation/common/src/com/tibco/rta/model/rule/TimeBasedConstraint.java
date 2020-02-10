package com.tibco.rta.model.rule;

import javax.xml.bind.annotation.XmlType;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

/**
 * 
 * 
 * A time based execution constraint for actions.
 *
 *
 */
public interface TimeBasedConstraint extends InvokeConstraint {
	
	/**
	 * An enumeration of possible constraints.
	 * 
	 *
	 */
	@XmlType(name=TIME_CONSTRAINT)
	public enum Constraint {
		/**
		 * Once set, perform the set action till the set condition evaluates to false
		 * not necessarily the clear condition evaluating to true.
		 */
		TILL_CONDITION_IMPROVES,
		/**
		 * Once set, perform the set action till the clear condition evaluates to true.
		 */
		TILL_CONDITION_CLEARS;
	}
	
	/**
	 * Time, in milliseconds indicating how often to invoke the set action.
	 * @return
	 */
	long getInvocationFrequency();
	
	/**
	 * Number of times the set action has to be invoked.
	 * @return the frequency.
	 */
	long getMaxInvocationCount();
	
	/**
	 * Get the associated time constraint enumeration.
	 * @return the constraint.
	 */
	Constraint getTimeConstraint();
	
}