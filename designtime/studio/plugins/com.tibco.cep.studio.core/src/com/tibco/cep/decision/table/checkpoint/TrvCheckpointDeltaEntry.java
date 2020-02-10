/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint;

import java.util.List;

import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * 
 * @author aathalye
 *
 */
public class TrvCheckpointDeltaEntry<C extends IExecutableCommand> implements ICheckpointDeltaEntry<C> {
	
	private C command;
	
	private CheckpointEntryType entryType;
	
	/**
	 * @param command
	 */
	public TrvCheckpointDeltaEntry(C command, CheckpointEntryType entryType) {
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
		//TODO compare each element in the 2 lists
		//Get first one
		Object thisAffectedObject = null;
		Object otherAffectedObject = null;
		if (!otherAffectedObjects.isEmpty()) {
			otherAffectedObject = otherAffectedObjects.get(0);
		}
		if (!thisAffectedObjects.isEmpty()) {
			thisAffectedObject = thisAffectedObjects.get(0);
		}
		if (!(thisAffectedObject instanceof TableRuleVariable)
				&& (otherAffectedObject instanceof TableRuleVariable)) {
			return false;
		}
		if (!(otherAffectedObject instanceof TableRuleVariable)
				&& (thisAffectedObject instanceof TableRuleVariable)) {
			return false;
		}
		TableRuleVariable otherReciever = (TableRuleVariable)otherAffectedObject;
		TableRuleVariable thisReciever = (TableRuleVariable)thisAffectedObject;
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
