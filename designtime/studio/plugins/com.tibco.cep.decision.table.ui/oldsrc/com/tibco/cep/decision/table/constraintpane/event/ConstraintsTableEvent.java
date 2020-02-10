/**
 * 
 */
package com.tibco.cep.decision.table.constraintpane.event;

import java.util.EventObject;

import com.tibco.cep.decision.table.constraintpane.DecisionTable;

/**
 * @author aathalye
 *
 */
public class ConstraintsTableEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6755530442272349230L;
	
	private ConstraintsTableEventType eventType;
	
	/**
	 * To be used for updates
	 */
	private Object oldSourceObject;
	
	private DecisionTable constraintsTable;

	/**
	 * 
	 * @param source
	 * @param constraintsTable
	 * @param eventType
	 */
	public ConstraintsTableEvent(Object source, 
			                     DecisionTable constraintsTable,
			                     ConstraintsTableEventType eventType) {
		super(source);
		this.constraintsTable = constraintsTable;
		this.eventType = eventType;
	}
	
		
	public enum ConstraintsTableEventType {
		CELL_ADD,
		CELL_UPDATE,
		CELL_REMOVE,
		ALIAS_REMOVE,
		ALIAS_RENAME,
		CELL_PROPERTY_UPDATE;
	}

	/**
	 * @return the eventType
	 */
	public final ConstraintsTableEventType getEventType() {
		return eventType;
	}

	/**
	 * @return the constraintsTable
	 */
	public final DecisionTable getConstraintsTable() {
		return constraintsTable;
	}

	/**
	 * @return the oldSourceObject
	 */
	public final Object getOldSourceObject() {
		return oldSourceObject;
	}

	/**
	 * @param oldSourceObject the oldSourceObject to set
	 */
	public final void setOldSourceObject(Object oldSourceObject) {
		this.oldSourceObject = oldSourceObject;
	}
	
}
