/**
 * 
 */
package com.tibco.cep.decision.table.command;

/**
 * @author aathalye
 *
 */
public interface IModificationCommand extends IUndoableCommand {
	
	/**
	 * Object modified as a result of this command's execution.
	 * <p>
	 * Example added row
	 * </p>
	 * @return
	 */
	Object getModifiedObject();
}
