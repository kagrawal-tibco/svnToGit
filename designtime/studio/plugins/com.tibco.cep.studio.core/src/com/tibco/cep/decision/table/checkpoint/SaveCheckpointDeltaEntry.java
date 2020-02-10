/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint;

import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.impl.CheckPointCommand;

/**
 * @author aathalye
 *
 */

public class SaveCheckpointDeltaEntry<C extends IExecutableCommand> implements ICheckpointDeltaEntry<C> {
	

	private C command;
	
	/**
	 * @param command
	 */
	public SaveCheckpointDeltaEntry(C command) {
		this.command = command;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICheckpointDeltaEntry#getCommand()
	 */
	public C getCommand() {
		return command;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICheckpointDeltaEntry#like(com.tibco.cep.decision.table.command.ICheckpointDeltaEntry)
	 */
	public boolean like(ICheckpointDeltaEntry<C> checkpointDeltaEntry) {
		C otherCommand = checkpointDeltaEntry.getCommand();
		if (command instanceof CheckPointCommand 
				&& otherCommand instanceof CheckPointCommand) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry#getCheckpointEntryType()
	 */
	public CheckpointEntryType getCheckpointEntryType() {
		return CheckpointEntryType.NOT_ASSIGNED;
	}
	
}