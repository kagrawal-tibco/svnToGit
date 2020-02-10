/**
 * 
 */
package com.tibco.cep.decision.table.command;

/**
 * @author aathalye
 *
 */
public interface ICreationCommand extends IUndoableCommand {
	
	/**
	 * Object created as a result of this command's execution.
	 * <p>
	 * Example added row
	 * </p>
	 * @return
	 */
	Object getCreatedObject();
}
