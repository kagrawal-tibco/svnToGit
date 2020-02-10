/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint.handlers;

import java.util.List;

import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.checkpoint.UndoableCommandCheckpointEntry;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;

/**
 * @author aathalye
 *
 */
public class UndoableCommandCheckpointEntryHandler implements
		ICheckpointDeltaEntryHandler {
	
	private Table tableEModel;
	
	/**
	 * The delta entry
	 */
	private ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.handlers.ICheckpointDeltaEntryHandler#handleEntry()
	 */
	
	public void handleEntry() {
		if (checkpointDeltaEntry instanceof UndoableCommandCheckpointEntry) {
			UndoableCommandCheckpointEntry<IExecutableCommand> undoableCommandCheckpointEntry = 
				(UndoableCommandCheckpointEntry<IExecutableCommand>)checkpointDeltaEntry;
			IExecutableCommand command = checkpointDeltaEntry.getCommand();
			if (command == null) {
				return;
			}
			List<ICheckpointDeltaEntry<IExecutableCommand>> children = undoableCommandCheckpointEntry.getChildren();
			for (ICheckpointDeltaEntry<IExecutableCommand> child : children) {
				ICheckpointDeltaEntryHandler handler = 
					CheckpointDeltaEntryHandlerFactory.INSTANCE.getDeltaEntryHandler(child, tableEModel);
				handler.handleEntry();
			}
		}
	}

	/**
	 * @param tableEModel
	 * @param checkpointDeltaEntry
	 */
	public UndoableCommandCheckpointEntryHandler(ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry,
			                                     Table tableEModel) {
		this.tableEModel = tableEModel;
		this.checkpointDeltaEntry = checkpointDeltaEntry;
	}
}
