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
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decision.table.utils.RuleIDGeneratorManager;

/**
 * @author aathalye
 *
 */
public class DecisionTableAddRowCommandListener implements
		ICommandCreationListener<AddCommand<TableRule>, TableRule> {
	
	/**
	 * The UI model to operate on
	 */
	private DecisionDataModel decisionDataModel;
	
	private Table tableEModel;
	
	private List<TableRule> affectedObjects;
	
	private TableTypes tableType;
	
	public DecisionTableAddRowCommandListener(final DecisionDataModel decisionDataModel,
			                                  final Table tableEModel,
			                                  final TableTypes tableType) {
		this.decisionDataModel = decisionDataModel;
		this.tableEModel = tableEModel;
		this.tableType = tableType;
		affectedObjects = new ArrayList<TableRule>();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@SuppressWarnings("unchecked")
	public Object commandCreated(CommandEvent<AddCommand<TableRule>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand 
				&& source instanceof AbstractExecutableCommand) {
			AbstractExecutableCommand<TableRule> command = (AbstractExecutableCommand<TableRule>)source;
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			String project = commandStack.getProject();
			//Get parent table
			Table table = command.getParent();
			String tablePath = table.getFolder() + table.getName();
			DecisionRule decisionRule = decisionDataModel.createDecisionRule();
			//Set id
			RuleIDGeneratorManager.INSTANCE.setID(project, tablePath, decisionRule);
			int ruleIndex = decisionDataModel.addRule(decisionRule);
			//Create a mapping
			//decisionDataModel.addRowIDIndexEntry(decisionRule.getId(), ruleIndex);
//			System.out.println("Executing " + decisionDataModel.getRuleCount());
			createRule(decisionRule);
			return ruleIndex;
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void commandUndone(CommandEvent<AddCommand<TableRule>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand) {
			ICreationCommand command = (ICreationCommand)source;
			Object createdObject = command.getCreatedObject();
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			if (createdObject instanceof Integer) {
				Integer ruleIndex = (Integer)createdObject;
				//Remove the rule
				decisionDataModel.removeRule(ruleIndex);
				String project = commandStack.getProject();
				//Get parent table
				Table table = command.getParent();
				String tablePath = table.getFolder() + table.getName();
				//System.out.println("Undoing " + decisionDataModel.getRuleCount());
				//Restore id
				RuleIDGeneratorManager.INSTANCE.setPreviousID(project, tablePath);
				removeRule(ruleIndex);
			}
		}
	}


	private void createRule(DecisionRule decisionRule) {
		//Create backend component
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		TableRule newRule = 
			DecisionTableUtil.createEModelFromRule(decisionDataModel, 
					tableRuleSet, 
					decisionRule);
		//This is an affected object and needs to be recorded
		affectedObjects.add(newRule);
		tableRuleSet.getRule().add(newRule);
		
		decisionRule.setValue(newRule);
	}
	
	private void removeRule(int ruleIndex) {
		TableRuleSet trs = null;
		switch (tableType) {
		case DECISION_TABLE:
			trs = tableEModel.getDecisionTable();
			break;
		case EXCEPTION_TABLE:
			trs = tableEModel.getExceptionTable();
			break;
		}
		trs.getRule().remove(ruleIndex);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	public List<TableRule> getAffectedObjects() {
		return affectedObjects;
	}
}
