/**
 * 
 */
package com.tibco.cep.decision.table.command;

/**
 * @author aathalye
 *
 */
public interface IMoveCommand extends IUndoableCommand {
	
	/**
	 * 
	 * @return
	 */
	Object getMovedObject();
}
