/**
 * 
 */
package com.tibco.cep.decision.table.command.memento;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.command.IMemento;

/**
 * @author aathalye
 *
 */
public class StateMemento<E extends EObject> implements IMemento {
	
	protected E monitored;
	
	/**
	 * Maintain previous value
	 */
	private Object value;
	
	public StateMemento(final E monitored, 
			            final Object value) {
		this.monitored = monitored;
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IMemento#getMonitored()
	 */
	
	public E getMonitored() {
		return monitored;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IMemento#getValue()
	 */
	
	public Object getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IMemento#setValue(java.lang.Object)
	 */
	
	public void setValue(Object value) {
		this.value = value;
	}
}
