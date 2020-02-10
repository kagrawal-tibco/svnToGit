/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint.handlers;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.StudioCorePlugin;

/**
 * Handle Index updates for row(s) addition/removal
 * @author aathalye
 *
 */
public class TableRuleCheckpointDeltaEntryHandler implements
		ICheckpointDeltaEntryHandler {
	
	private Table tableEModel;
	
	private static final String CLASS = TableRuleCheckpointDeltaEntryHandler.class.getName();
	
	/**
	 * The delta entry
	 */
	private ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry;
	
	/**
	 * @param tableEModel
	 * @param tableRule
	 */
	public TableRuleCheckpointDeltaEntryHandler(Table tableEModel,
			ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry) {
		this.tableEModel = tableEModel;
		this.checkpointDeltaEntry = checkpointDeltaEntry;
	}
	
	/**
	 * Create a new {@link TableRule} object and add it to indexed {@link TableRuleSet}
	 * @param tableRule
	 * @param tableType
	 */
	private void createRule(TableRule tableRule, TableTypes tableType) {
		//Create backend component
		TableRuleSet trs = null;
		switch (tableType) {
		case DECISION_TABLE:
			trs = tableEModel.getDecisionTable();
			break;
		case EXCEPTION_TABLE:
			trs = tableEModel.getExceptionTable();
			break;
		}
		//Create a copy to avoid containment issues
		TableRule copyRule = (TableRule)EcoreUtil.copy(tableRule);
		//Add new rule to the indexed table
		if (!containsRule(trs.getRule(), tableRule)) {
			trs.getRule().add(copyRule);
		}
	}
	
	/**
	 * Remove a {@link TableRule} object from indexed {@link TableRuleSet}
	 * @param tableRule
	 * @param tableType
	 */
	private void removeRule(TableRule tableRule, TableTypes tableType) {
		TableRuleSet trs = null;
		switch (tableType) {
		case DECISION_TABLE:
			trs = tableEModel.getDecisionTable();
			break;
		case EXCEPTION_TABLE:
			trs = tableEModel.getDecisionTable();
			break;
		}
		TableRule existingTableRule = getExistingRule(trs.getRule(), tableRule);
		if (existingTableRule != null) {
			StudioCorePlugin.debug(CLASS, MessageFormat.format("Removing Rule %1$s" , existingTableRule));		
			trs.getRule().remove(existingTableRule);
		}	
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.handlers.ICheckpointDeltaEntryHandler#handleEntry()
	 */
	public void handleEntry() {
		IExecutableCommand command = checkpointDeltaEntry.getCommand();
		if (command == null) {
			return;
		}
		List<?> affectedObjects = command.getAffectedObjects();
		for (Object affectedObject : affectedObjects) {
			if (!(affectedObject instanceof TableRule)) {
				return;
			}
			TableRule tableRule = (TableRule)affectedObject;
			if (command instanceof ICreationCommand) {
				createRule(tableRule, command.getTableType());
			}
			if (command instanceof IRemovalCommand) {
				removeRule(tableRule, command.getTableType());
			}
		}
	}
	
	private boolean containsRule(List<TableRule> allRules, TableRule tableRule) {
		//Check if one with same id exists
		for (TableRule rule : allRules) {
			if (rule.getId().equals(tableRule.getId())) {
				return true;
			}
		}
		return false;
	}
	
	private TableRule getExistingRule(List<TableRule> allRules, TableRule tableRule) {
		//Get one with the same id
		TableRule existingRule = null;
		for (TableRule rule : allRules) {
			if (rule.getId().equals(tableRule.getId())) {
				existingRule = rule;
				break;
			}
		}
		return existingRule;
	}
}
