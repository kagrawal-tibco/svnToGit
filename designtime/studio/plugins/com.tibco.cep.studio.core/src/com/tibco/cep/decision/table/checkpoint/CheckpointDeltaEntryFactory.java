/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint;

import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IMoveCommand;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.IRenameCommand;
import com.tibco.cep.decision.table.command.memento.ColumnStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;


/**
 * @author aathalye
 *
 */
public class CheckpointDeltaEntryFactory {
	
	public static CheckpointDeltaEntryFactory INSTANCE = new CheckpointDeltaEntryFactory();
	
	private CheckpointDeltaEntryFactory() {}
	
	/**
	 * Get an instance of an {@link ICheckpointDeltaEntry} for the given
	 * {@link IExecutableCommand} and the object affected as a result
	 * of execution of the command.	
	 * @param <C>
	 * @param command
	 * @param affectedObject
	 * @return
	 */
	public <C extends IExecutableCommand> ICheckpointDeltaEntry<C> getDeltaEntry(C command, Object affectedObject) {
		if (affectedObject instanceof TableRuleVariable) {
			if (command instanceof ICreationCommand) {
				return new TrvCheckpointDeltaEntry<C>(command, CheckpointEntryType.ADD);
			}
			return new TrvCheckpointDeltaEntry<C>(command, CheckpointEntryType.CHANGE);
		} else if (affectedObject instanceof TableRule) {
			if (command instanceof IRemovalCommand) {
				return new TableRuleCheckpointDeltaEntry<C>(command, CheckpointEntryType.DELETE);
			}
			if (command instanceof ICreationCommand) {
				return new TableRuleCheckpointDeltaEntry<C>(command, CheckpointEntryType.ADD);
			}
		} else if (affectedObject instanceof ColumnStateMemento) {
			CheckpointEntryType checkpointEntryType = null;
			if (command instanceof IRemovalCommand) {
				checkpointEntryType = CheckpointEntryType.DELETE;
			}
			if (command instanceof ICreationCommand) {
				checkpointEntryType = CheckpointEntryType.ADD;
			}
			if (command instanceof IMoveCommand) {
				checkpointEntryType = CheckpointEntryType.CHANGE;
			} 
			if (command instanceof IRenameCommand) {
				checkpointEntryType = CheckpointEntryType.CHANGE;
			}
			return new ColumnCheckpointDeltaEntry<C>(command, checkpointEntryType);
		} 
		return null;
	}
}
