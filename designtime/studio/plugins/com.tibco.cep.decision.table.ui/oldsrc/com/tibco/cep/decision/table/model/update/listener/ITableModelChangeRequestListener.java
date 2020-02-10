/**
 * 
 */
package com.tibco.cep.decision.table.model.update.listener;

import javax.swing.JTable;

/**
 * Listen for model change request notifications 
 * when UI model changes.
 * <p>
 * Keep implementations reentrant as far as possible.
 * i.e : Avoid any instance variables in the implementation classes.
 * </p>
 * @see JTable#tableChanged(javax.swing.event.TableModelEvent)
 * @author aathalye
 *
 */
public interface ITableModelChangeRequestListener {
	
	/**
	 * Change backend EMF model for the decision table
	 * when there is any change in the UI model.
	 * <p>
	 * Generally applies to cell modifications only.
	 * </p>
	 * @param tableModelChangeRequestEvent
	 */
	<T extends ITableModelChangeRequestEvent> void doChange(T tableModelChangeRequestEvent);
}
