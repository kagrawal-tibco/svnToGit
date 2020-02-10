/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionRule;
import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;

/**
 * @author aathalye
 *
 */
public class DecisionTableRowRemovalCommandListener implements
		ICommandCreationListener<RemoveCommand<TableRule>, TableRule> {
	
	/**
	 * The UI model to operate on
	 */
	private DecisionDataModel decisionDataModel;
	
	private Table tableEModel;
	/**
	 * The rules to remove
	 */
	private DecisionRule[] rulesToRemove;
	
	private List<TableRule> affectedObjects;


	/**
	 * @param decisionDataModel
	 * @param rulesToRemove
	 */
	public DecisionTableRowRemovalCommandListener(
			DecisionDataModel decisionDataModel, Table tableEModel, DecisionRule[] rulesToRemove) {
		this.decisionDataModel = decisionDataModel;
		this.rulesToRemove = rulesToRemove;
		this.tableEModel = tableEModel;
		affectedObjects = new ArrayList<TableRule>();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public Object commandCreated(CommandEvent<RemoveCommand<TableRule>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IRemovalCommand) {
			IRemovalCommand command = (IRemovalCommand)source;
			TableTypes tableType = command.getTableType();
			for (DecisionRule rule : rulesToRemove) {
				//Find its position in index
				int index = decisionDataModel.getRowIndex(rule.getId());
				decisionDataModel.removeRule(rule);
				removeRule(index, tableType);
			}
			//Return reference to the same array
			return rulesToRemove;
		}
		return null;
	}
	
	private void removeRule(int ruleIndex, TableTypes tableType) {
		TableRuleSet trs = null;
		switch (tableType) {
		case DECISION_TABLE:
			trs = tableEModel.getDecisionTable();
			break;
		case EXCEPTION_TABLE:
			trs = tableEModel.getExceptionTable();
			break;
		}
		TableRule toRemoveRule = trs.getRule().get(ruleIndex);
		trs.getRule().remove(toRemoveRule);
		affectedObjects.add(toRemoveRule);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	
	@SuppressWarnings("unchecked")
	public void commandUndone(CommandEvent<RemoveCommand<TableRule>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IRemovalCommand
				&& source instanceof AbstractExecutableCommand) {
			AbstractExecutableCommand<TableRule> removalCommand = 
						(AbstractExecutableCommand<TableRule>)source;
			if (removalCommand.isDefunct()) {
				return;
			}
			Object removedObject = ((IRemovalCommand)removalCommand).getRemovedObject();
			TableTypes tableType = removalCommand.getTableType();
			if (removedObject instanceof DecisionRule[]) {
				DecisionRule[] removedRules = (DecisionRule[])removedObject;
				for (DecisionRule decisionRule : removedRules) {
					//Get id of removed rule
					int removedRuleId = decisionRule.getId();
					//Find its position in index
					int index = decisionDataModel.getRowIndex(removedRuleId);
					decisionDataModel.addRule(index, decisionRule);
					createRule(tableType, index, decisionRule);
				}
			}
		}
	}
	
	private void createRule(TableTypes tableType, int index, DecisionRule decisionRule) {
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
		TableRule newRule = 
			DecisionTableUtil.createEModelFromRule(decisionDataModel, 
					trs, 
					decisionRule);
		trs.getRule().add(index, newRule);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	public List<TableRule> getAffectedObjects() {
		return affectedObjects;
	}
}
