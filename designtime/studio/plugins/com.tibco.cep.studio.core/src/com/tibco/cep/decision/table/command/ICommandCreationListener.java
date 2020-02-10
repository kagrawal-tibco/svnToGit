/**
 * 
 */
package com.tibco.cep.decision.table.command;

import java.util.List;

import com.tibco.cep.decision.table.command.impl.CommandEvent;

/**
 * Introduced this listener as a means of indirection purely to resolve
 * plugin dependency problems.
 * @author aathalye
 *
 */
public interface ICommandCreationListener<I extends IUndoableCommand, T> {
	
	/**
	 * Fired when an {@link ICreationCommand} is added on stack
	 * @param command
	 * @return the object created as a result of the command execution
	 */
	Object commandCreated(CommandEvent<I> commandEvent);
	
	/**
	 * Execute undo operation on the command
	 * @param command
	 */
	void commandUndone(CommandEvent<I> commandEvent);
	
	/**
	 * Return a <code>Collection</code> of objects affected by the
	 * execution of this command
	 * @return
	 */
	List<T> getAffectedObjects();
}
