/**
 * 
 */
package com.tibco.cep.decision.table.command.memento;

import com.tibco.cep.decision.table.model.dtmodel.Column;

/**
 * @author aathalye
 *
 */
public class ColumnNameStateMemento extends ColumnStateMemento {
	
	/**
	 * The new name of the column.
	 */
	private String newColumnName;
	
	/**
	 * Need to maintain this state also.
	 */
	private String newPropertyPath;
	
	/**
	 * 
	 * @param monitored
	 * @param oldName
	 */
	public ColumnNameStateMemento(Column monitored, String oldName) {
		super(monitored, oldName);
	}

	/**
	 * @return the newColumnName
	 */
	public final String getNewColumnName() {
		return newColumnName;
	}

	/**
	 * @param newColumnName the newColumnName to set
	 */
	public final void setNewColumnName(String newColumnName) {
		this.newColumnName = newColumnName;
	}
	
	
	/**
	 * @return the newPropertyPath
	 */
	public final String getNewPropertyPath() {
		return newPropertyPath;
	}

	/**
	 * @param newPropertyPath the newPropertyPath to set
	 */
	public final void setNewPropertyPath(String newPropertyPath) {
		this.newPropertyPath = newPropertyPath;
	}
}
