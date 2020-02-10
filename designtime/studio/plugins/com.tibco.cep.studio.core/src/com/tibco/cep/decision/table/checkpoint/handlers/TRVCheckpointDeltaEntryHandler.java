/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint.handlers;

import java.util.List;

import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.impl.ModifyCellCommand;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.TableRuleInfo;

/**
 * Handle {@link ICheckpointDeltaEntry} which maps to a
 * {@link TableRuleVariable}.
 * <p>
 * Handle Index updates for cell modification.
 * </p>
 * 
 * @author aathalye
 * 
 */
public class TRVCheckpointDeltaEntryHandler implements ICheckpointDeltaEntryHandler {

	private Table tableEModel;

	/**
	 * The delta entry
	 */
	private ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry;
	
	private static final String CLASS = TRVCheckpointDeltaEntryHandler.class.getName();

	/**
	 * Required for removal command
	 */
	private FlushableEntry flushableEntry;

	public TRVCheckpointDeltaEntryHandler(final Table tableEModel,
			final ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry) {
		this.tableEModel = tableEModel;
		this.checkpointDeltaEntry = checkpointDeltaEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.decision.table.checkpoint.handlers.ICheckpointDeltaEntryHandler
	 * #handleEntry()
	 */

	public void handleEntry() {
		StudioCorePlugin.debug(CLASS, "Handling TRV entry");
		IExecutableCommand command = checkpointDeltaEntry.getCommand();
		if (command == null) {
			return;
		}
		List<?> affectedObjects = command.getAffectedObjects();
		TableTypes tableType = command.getTableType();
		// Create backend component
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		TableRuleVariable trv = (TableRuleVariable) affectedObjects.get(0);
		StudioCorePlugin.debug(CLASS, "Handling entry {0}", trv.getId());
		
		String ruleId = ModelUtils.DecisionTableUtils.getContainingRuleId(trv.getId());
		//Find TableRule for this
		TableRuleInfo tableRuleInfo = 
			ModelUtils.DecisionTableUtils.getTableRuleInfoFromId(ruleId, tableRuleSet);
		//TableRuleInfo could come out null because the rule was removed already
		//This is likely in the event modify was executed followed by removal in the stack
		//but the stack traversal happens such that modify comes before removal.
		//In index update case traversal happens backwards so modify command could come before
		//removal command.
		if (tableRuleInfo == null) {
			StudioCorePlugin.debug(CLASS, "Table Rule Info for rule id {0} not found. Possibly row deleted", ruleId);
			return;
		}
		TableRule tableRule = tableRuleInfo.getTableRule();
		
		Columns columns = tableRuleSet.getColumns();
		Column column = columns.search(trv.getColId());
		if (column == null) {
			return;
		}
		
		boolean found = false;
		switch (column.getColumnType()) {
		case CONDITION:
		case CUSTOM_CONDITION: {
			for (TableRuleVariable temp : tableRule.getCondition()) {
				if (temp.getId().equals(trv.getId())) {
					if (command instanceof ICreationCommand
							|| command instanceof ModifyCellCommand) {
						found = true;
						// Update this
						temp.setExpr(trv.getExpr());
					} else if (command instanceof IRemovalCommand) {
						// Clear it
						flushableEntry = new FlushableEntry(tableRule,
								temp, column.getColumnType());
					}
				}
			}
			if (!found) {
				//If not found add a new one
				tableRule.getCondition().add(trv);
			}
			}
			break;
		case ACTION:
		case CUSTOM_ACTION: {
			for (TableRuleVariable temp : tableRule.getAction()) {
				if (temp.getId().equals(trv.getId())) {
					if (command instanceof ICreationCommand
							|| command instanceof ModifyCellCommand) {
						found = true;
						// Update this
						temp.setExpr(trv.getExpr());
					} else if (command instanceof IRemovalCommand) {
						// Clear it
						flushableEntry = new FlushableEntry(tableRule,
								temp, column.getColumnType());
					}
				}
			}
			if (!found) {
				tableRule.getAction().add(trv);
			}
			}
			break;
		}
		removeFlushable();
	}

	private class FlushableEntry {

		private TableRule rule;

		private TableRuleVariable trvToFlush;

		private ColumnType columnType;

		/**
		 * @param rule
		 * @param trvToFlush
		 * @param columnType
		 */
		FlushableEntry(TableRule rule, TableRuleVariable trvToFlush,
				ColumnType columnType) {
			this.rule = rule;
			this.trvToFlush = trvToFlush;
			this.columnType = columnType;
		}
	}
	
	/**
	 * Remove any <code>FlushableEntry</code> in case
	 * any entry has to be removed
	 */
	void removeFlushable() {
		if (flushableEntry == null) {
			return;
		}
		switch (flushableEntry.columnType) {

		case CONDITION:
		case CUSTOM_CONDITION:
			flushableEntry.rule.getCondition()
					.remove(flushableEntry.trvToFlush);
			break;
		case ACTION:
		case CUSTOM_ACTION:
			flushableEntry.rule.getAction().remove(flushableEntry.trvToFlush);
			break;
		}
	}
}
