/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IRemovalCommand;


/**
 * @author aathalye
 *
 */
public class UndoableCommandCheckpointEntry<C extends IExecutableCommand> implements ICheckpointDeltaEntry<C> {
	
	private C command;
	
	/**
	 * Children checkpoint entries based on objects affected by a command
	 */
	private List<ICheckpointDeltaEntry<C>> children;
	
	/**
	 * Certain entries may require full re-indexing of the table.
	 */
	private boolean needsReIndex;
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry#getCheckpointEntryType()
	 */
	
	/**
	 * @param command
	 * @param entryType
	 */
	public UndoableCommandCheckpointEntry(C command, boolean needsReIndex) {
		this.command = command;
		
		this.needsReIndex = needsReIndex;
		
		children = new ArrayList<ICheckpointDeltaEntry<C>>();
		List<?> affectedObjects = command.getAffectedObjects();
		for (Object affectedObject : affectedObjects) {
			addChild(affectedObject);
		}
	}

	public CheckpointEntryType getCheckpointEntryType() {
		if (command instanceof ICreationCommand) {
			return CheckpointEntryType.ADD;
		}
		if (command instanceof IRemovalCommand) {
			return CheckpointEntryType.DELETE;
		}
		return CheckpointEntryType.CHANGE;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry#getCommand()
	 */
	
	public C getCommand() {
		return command;
	}
	
	public List<ICheckpointDeltaEntry<C>> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	public void addChild(ICheckpointDeltaEntry<C> childEntry) {
		children.add(childEntry);
	}
	
	public void addChild(Object affectedObject) {
		ICheckpointDeltaEntry<C> childEntry = 
			CheckpointDeltaEntryFactory.INSTANCE.getDeltaEntry(command, affectedObject);
		if (childEntry != null) {
			children.add(childEntry);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry#like(com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry)
	 */
	
	public boolean like(ICheckpointDeltaEntry<C> checkpointDeltaEntry) {
		if (!(checkpointDeltaEntry instanceof UndoableCommandCheckpointEntry)) {
			return false;
		}
		UndoableCommandCheckpointEntry<C> otherEntry = (UndoableCommandCheckpointEntry<C>)checkpointDeltaEntry;
		if (children.size() > 0 && otherEntry.getChildren().size() == 0) {
			return false;
		}
		for (int i = 0; i < children.size(); i++) {
			ICheckpointDeltaEntry<C> childEntry = children.get(i);
			//Compare with same entry in other
			ICheckpointDeltaEntry<C> otherChildEntry = otherEntry.getChildren().get(i);
			if (!childEntry.like(otherChildEntry)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean needsReIndex() {
		return needsReIndex;
	}
}
