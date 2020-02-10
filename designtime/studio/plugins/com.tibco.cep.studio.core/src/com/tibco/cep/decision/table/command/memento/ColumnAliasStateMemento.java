/**
 * 
 */
package com.tibco.cep.decision.table.command.memento;

import com.tibco.cep.decision.table.model.dtmodel.Column;

/**
 * @author aathalye
 *
 */
public class ColumnAliasStateMemento extends ColumnStateMemento {
	
	private String newColumnAlias;

	/**
	 * @param monitored
	 * @param oldAlias
	 */
	public ColumnAliasStateMemento(Column monitored, String oldAlias) {
		super(monitored, oldAlias);
	}
	
	/**
	 * @return the newColumnName
	 */
	public final String getNewColumnAlias() {
		return newColumnAlias;
	}

	/**
	 * @param newColumnName the newColumnName to set
	 */
	public final void setNewColumnAlias(String newColumnAlias) {
		this.newColumnAlias = newColumnAlias;
	}
}
