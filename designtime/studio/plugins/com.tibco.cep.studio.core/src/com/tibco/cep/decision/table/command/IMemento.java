/**
 * 
 */
package com.tibco.cep.decision.table.command;

import org.eclipse.emf.ecore.EObject;

/**
 * Maintain an object to be monitored for state changes, and its value
 * @author aathalye
 *
 */
public interface IMemento {
	
	/**
	 * Return the {@link EObject} monitored by this memento
	 * @return
	 */
	EObject getMonitored();
	
	/**
	 * The actual value.
	 * <p>
	 * This can be value of cell for a TRV, or
	 * index of a new/deleted rule etc.
	 * @return
	 */
	Object getValue();
	
	/**
	 * Set the value of the memento
	 * @param value
	 */
	void setValue(Object value);
}
