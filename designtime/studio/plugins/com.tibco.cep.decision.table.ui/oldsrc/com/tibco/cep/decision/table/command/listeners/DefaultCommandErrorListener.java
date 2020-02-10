/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import com.tibco.cep.decision.table.command.ICommandErrorListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;

/**
 * @author aathalye
 *
 */
public class DefaultCommandErrorListener<C extends IExecutableCommand> implements ICommandErrorListener<C> {

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandErrorListener#takeAction(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void takeAction(CommandEvent<C> errorEvent) {
		//Get source
		Object source = errorEvent.getSource();
		if (source instanceof IExecutableCommand) {
			IExecutableCommand command = (IExecutableCommand)source;
			/*CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			//Remove the command from the stack
			commandStack.removeLast();*/
			command.setDefunct(true);
		}
	}
}
