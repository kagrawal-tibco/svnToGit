/**
 * 
 */
package com.tibco.cep.decision.table.command;

import com.tibco.cep.decision.table.command.impl.CommandEvent;

/**
 * Respond to errors created during command execution
 * @author aathalye
 *
 */
public interface ICommandErrorListener<C extends IExecutableCommand> {
	
	void takeAction(CommandEvent<C> errorEvent);
}
