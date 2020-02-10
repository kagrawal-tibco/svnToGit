/**
 * 
 */
package com.tibco.cep.decision.table.command;

/**
 * @author aathalye
 *
 */
public interface IRenameCommand extends IUndoableCommand {
	
	/**
	 * Hold reference to the renamed object
	 * @return
	 */
	Object getRenamedObject();
}
