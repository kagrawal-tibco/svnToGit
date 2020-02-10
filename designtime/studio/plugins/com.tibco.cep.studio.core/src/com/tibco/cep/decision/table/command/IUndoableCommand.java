/**
 * 
 */
package com.tibco.cep.decision.table.command;


/**
 * @author aathalye
 *
 */
public interface IUndoableCommand extends IExecutableCommand {
	
	/**
	 * Execute undo operation
	 */
	void undo();
	
	/**
	 * Save the state of the monitored object in the memento
	 * @param memento
	 */
	void saveMemento(IMemento memento);
	
	/**
	 * Get the stored state from the memento
	 * @return
	 */
	Object getValue();
}
