/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint;

import com.tibco.cep.decision.table.command.IExecutableCommand;

/**
 * @author aathalye
 *
 */
public interface ICheckpointDeltaEntry<C extends IExecutableCommand> {
	
	/**
	 * 
	 * @return
	 */
	C getCommand();
	
	/**
	 * Comapre 2 {@link ICheckpointDeltaEntry} objects for likeness.
	 * @param checkpointDeltaEntry
	 * @return
	 */
	boolean like(ICheckpointDeltaEntry<C> checkpointDeltaEntry);
	
	CheckpointEntryType getCheckpointEntryType();

}
