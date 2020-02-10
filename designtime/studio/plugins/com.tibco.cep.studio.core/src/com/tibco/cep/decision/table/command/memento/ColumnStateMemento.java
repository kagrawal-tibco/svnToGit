/**
 * 
 */
package com.tibco.cep.decision.table.command.memento;

import com.tibco.cep.decision.table.model.dtmodel.Column;

/**
 * @author aathalye
 *
 */
public abstract class ColumnStateMemento extends StateMemento<Column> {

	/**
	 * @param monitored
	 * @param value
	 */
	public ColumnStateMemento(Column monitored, Object value) {
		super(monitored, value);
	}
	
	/**
	 * Get ID of the monitored object.
	 * @return
	 */
	public String getId() {
		return monitored.getId();
	}
}
