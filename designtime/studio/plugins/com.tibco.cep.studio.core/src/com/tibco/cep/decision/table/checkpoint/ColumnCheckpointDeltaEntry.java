/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint;

import java.util.List;

import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.memento.ColumnStateMemento;

/**
 * @author aathalye
 *
 */
public class ColumnCheckpointDeltaEntry<C extends IExecutableCommand> implements ICheckpointDeltaEntry<C> {
	
	private C command;
	
	private CheckpointEntryType entryType;
	
	/**
	 * @param command
	 */
	public ColumnCheckpointDeltaEntry(C command, CheckpointEntryType entryType) {
		this.command = command;
		this.entryType = entryType;
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
		List<?> otherAffectedObjects = otherCommand.getAffectedObjects();
		List<?> thisAffectedObjects = command.getAffectedObjects();
		
		//Get last one supposedly will be column
		Object thisAffectedObject = null;
		Object otherAffectedObject = null;
		if (!otherAffectedObjects.isEmpty()) {
			otherAffectedObject = otherAffectedObjects.get(otherAffectedObjects.size() - 1);
		}
		if (!thisAffectedObjects.isEmpty()) {
			thisAffectedObject = thisAffectedObjects.get(thisAffectedObjects.size() - 1);
		}
		if (!(thisAffectedObject instanceof ColumnStateMemento)
				&& (otherAffectedObject instanceof ColumnStateMemento)) {
			return false;
		}
		if (!(otherAffectedObject instanceof ColumnStateMemento)
				&& (thisAffectedObject instanceof ColumnStateMemento)) {
			return false;
		}
		
		ColumnStateMemento otherReciever = (ColumnStateMemento)otherAffectedObject;
		ColumnStateMemento thisReciever = (ColumnStateMemento)thisAffectedObject;
		//Check if classes are same
		if (!otherReciever.getClass().getName().equals(thisReciever.getClass().getName())) {
			return false;
		}
		//Check if ids are same
		if (otherReciever.getId().equals(thisReciever.getId())) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry#getCheckpointEntryType()
	 */
	public CheckpointEntryType getCheckpointEntryType() {
		return entryType;
	}

}
