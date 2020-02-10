/**
 * 
 */
package com.tibco.cep.decision.table.command.memento;

import com.tibco.cep.decision.table.model.dtmodel.Column;

/**
 * Use this to store state of the monitored column
 * object to be used for updating index and such.
 * @author aathalye
 *
 */
public class ColumnPositionStateMemento extends ColumnStateMemento {
	
	/**
	 * Current index to be used for new addition or moving
	 * to new position after a move.
	 */
	private int currentOrderIndex;
	

	/**
	 * @param monitored
	 * @param value -> Previous index (-1 for new addition).
	 */
	public ColumnPositionStateMemento(Column column, Integer value) {
		super(column, value);
	}


	/**
	 * @return the currentOrderIndex
	 */
	public final int getCurrentOrderIndex() {
		return currentOrderIndex;
	}


	/**
	 * @param currentOrderIndex the currentOrderIndex to set
	 */
	public final void setCurrentOrderIndex(int currentOrderIndex) {
		this.currentOrderIndex = currentOrderIndex;
	}
}
