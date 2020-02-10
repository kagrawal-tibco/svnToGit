/**
 * 
 */
package com.tibco.cep.decision.table.command;

/**
 * @author aathalye
 *
 */
public interface IRemovalCommand extends IUndoableCommand {
	
	/**
	 * Hold reference to the removed object
	 * @return
	 */
	Object getRemovedObject();
}
