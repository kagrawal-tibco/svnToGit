/**
 * 
 */
package com.tibco.cep.decision.table.command.memento;

import com.tibco.cep.decision.table.model.dtmodel.Column;

/**
 * @author yrajput
 *
 */
public class ColumnDefaultCellTextStateMemento extends ColumnStateMemento {
	
	private String newDefaultCellText;

	/**
	 * @param monitored
	 * @param oldDefaultCellText
	 */
	public ColumnDefaultCellTextStateMemento(Column monitored, String oldDefaultCellText) {
		super(monitored, oldDefaultCellText);
	}
	
	/**
	 * @return the newDefaultCellText
	 */
	public final String getNewColumnDefaultCellText() {
		return newDefaultCellText;
	}

	/**
	 * @param newDefaultCellText to set
	 */
	public final void setNewColumnDefaultCellText(String newDefaultCellText) {
		this.newDefaultCellText = newDefaultCellText;
	}
}
