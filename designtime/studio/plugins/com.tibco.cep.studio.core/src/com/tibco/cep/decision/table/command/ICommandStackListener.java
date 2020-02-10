/**
 * 
 */
package com.tibco.cep.decision.table.command;

import com.tibco.cep.decision.table.command.impl.CommandEvent;

/**
 * @author aathalye
 *
 */
public interface ICommandStackListener<C extends IExecutableCommand> {
	
	void commandExecuted(CommandEvent<C> commandEvent);
	
	void commandUndone(CommandEvent<C> commandEvent);
}
