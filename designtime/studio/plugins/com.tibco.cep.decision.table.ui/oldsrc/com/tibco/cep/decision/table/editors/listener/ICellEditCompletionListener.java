/**
 * 
 */
package com.tibco.cep.decision.table.editors.listener;

/**
 * Listen for cell edit completion events
 * @author aathalye
 *
 */
public interface ICellEditCompletionListener {
	
	/**
	 * Notification of cell editing completion.
	 * @param editCompletionEvent
	 */
	public void editingComplete(EditCompletionEvent editCompletionEvent);
}
