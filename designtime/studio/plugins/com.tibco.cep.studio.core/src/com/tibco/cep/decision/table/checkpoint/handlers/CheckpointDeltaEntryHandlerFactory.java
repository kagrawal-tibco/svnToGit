/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint.handlers;

import com.tibco.cep.decision.table.checkpoint.ColumnCheckpointDeltaEntry;
import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.checkpoint.TableRuleCheckpointDeltaEntry;
import com.tibco.cep.decision.table.checkpoint.TrvCheckpointDeltaEntry;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;

/**
 * @author aathalye
 *
 */
public class CheckpointDeltaEntryHandlerFactory {
	
	public static final CheckpointDeltaEntryHandlerFactory INSTANCE = new CheckpointDeltaEntryHandlerFactory();
	
	private CheckpointDeltaEntryHandlerFactory() {}
	
	/**
	 * 
	 * @param checkpointDeltaEntry
	 * @param tableEModel
	 * @return
	 */
	public ICheckpointDeltaEntryHandler getDeltaEntryHandler(final ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry, final Table tableEModel) {
		if (checkpointDeltaEntry instanceof TrvCheckpointDeltaEntry) {
			TrvCheckpointDeltaEntry<IExecutableCommand> trvEntry = 
				(TrvCheckpointDeltaEntry<IExecutableCommand>)checkpointDeltaEntry;
			return new TRVCheckpointDeltaEntryHandler(tableEModel, trvEntry);
		}
		if (checkpointDeltaEntry instanceof TableRuleCheckpointDeltaEntry) {
			TableRuleCheckpointDeltaEntry<IExecutableCommand> trEntry = 
				(TableRuleCheckpointDeltaEntry<IExecutableCommand>)checkpointDeltaEntry;
			return new TableRuleCheckpointDeltaEntryHandler(tableEModel, trEntry);
		}
		if (checkpointDeltaEntry instanceof ColumnCheckpointDeltaEntry) {
			ColumnCheckpointDeltaEntry<IExecutableCommand> trEntry = 
				(ColumnCheckpointDeltaEntry<IExecutableCommand>)checkpointDeltaEntry;
			return new ColumnCheckpointDeltaEntryHandler(tableEModel, trEntry);
		}
		//TODO Handle other types of entries
		return null;
	}
}
