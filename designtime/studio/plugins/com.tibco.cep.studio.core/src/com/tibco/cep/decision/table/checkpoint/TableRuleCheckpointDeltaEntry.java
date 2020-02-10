/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint;

import java.util.List;

import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;

/**
 * 
 * @author aathalye
 *
 * 
 */
public class TableRuleCheckpointDeltaEntry<C extends IExecutableCommand> implements ICheckpointDeltaEntry<C> {
	
	private C command;
	
	private CheckpointEntryType entryType;
	
	/**
	 * @param command
	 */
	public TableRuleCheckpointDeltaEntry(C command, CheckpointEntryType entryType) {
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
		
		//Get first one
		Object thisAffectedObject = null;
		Object otherAffectedObject = null;
		if (!otherAffectedObjects.isEmpty()) {
			otherAffectedObject = otherAffectedObjects.get(0);
		}
		if (!thisAffectedObjects.isEmpty()) {
			thisAffectedObject = thisAffectedObjects.get(0);
		}
		if (!(thisAffectedObject instanceof TableRule)
				&& (otherAffectedObject instanceof TableRule)) {
			return false;
		}
		if (!(otherAffectedObject instanceof TableRule)
				&& (thisAffectedObject instanceof TableRule)) {
			return false;
		}
		TableRule otherReciever = (TableRule)otherAffectedObject;
		TableRule thisReciever = (TableRule)thisAffectedObject;
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
