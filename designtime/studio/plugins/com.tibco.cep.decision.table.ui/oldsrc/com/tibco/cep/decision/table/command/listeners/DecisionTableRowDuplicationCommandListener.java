/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionEntry;
import com.jidesoft.decision.DecisionRule;
import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.command.impl.DuplicateCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decision.table.utils.RuleIDGeneratorManager;

/**
 * Listener class for duplicating rules when duplication command
 * is invoked.
 * @author aathalye
 *
 */
public class DecisionTableRowDuplicationCommandListener implements
		ICommandCreationListener<DuplicateCommand, TableRule> {
	
	/**
	 * The UI model to operate on
	 */
	private DecisionDataModel decisionDataModel;
	
	/**
	 * The rules to duplicate
	 */
	private DecisionRule[] rulesToDuplicate;
	
	private Table tableEModel;
	
	private List<TableRule> affectedObjects;
	
	private TableTypes tableType;
	

	/**
	 * @param decisionDataModel
	 * @param rulesToDuplicate
	 */
	public DecisionTableRowDuplicationCommandListener(
			DecisionDataModel decisionDataModel, DecisionRule[] rulesToDuplicate,
			Table tableEModel,
            final TableTypes tableType) {
		this.decisionDataModel = decisionDataModel;
		this.rulesToDuplicate = rulesToDuplicate;
		this.tableEModel = tableEModel;
		this.tableType = tableType;
		affectedObjects = new ArrayList<TableRule>();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.IAddCommandListener#addCommandCreated(com.tibco.cep.decision.table.command.ICreationCommand)
	 */
	@SuppressWarnings("unchecked")
	private Object addCommandCreated(CommandEvent<DuplicateCommand> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand 
				&& source instanceof AbstractExecutableCommand) {
			AbstractExecutableCommand<TableRule> command = (AbstractExecutableCommand<TableRule>)source;
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			String project = commandStack.getProject();
			//Get parent table
			Table table = command.getParent();
			String tablePath = table.getFolder() + table.getName();
			int ruleCount = decisionDataModel.getRuleCount();
			List<Integer> createdIndexes = new ArrayList<Integer>(ruleCount);
			if (rulesToDuplicate.length == 1) {
	        	DecisionRule duplicatedRule = rulesToDuplicate[0].duplicateRule();
	        	//Set id
				RuleIDGeneratorManager.INSTANCE.setID(project, tablePath, duplicatedRule);
	        	int ruleIndex = 
	            	decisionDataModel.addRule(ruleCount, duplicatedRule);
	            /*decisionDataModel.addRowIDIndexEntry(duplicatedRule.getId(), 
	            		                             ruleIndex);*/
	            createRule(duplicatedRule);
	            createdIndexes.add(ruleIndex);
	        }
	        else {
	            for (DecisionRule rule : rulesToDuplicate) {
	                try {
	                	DecisionRule duplicatedRule = rule.clone();
	                	//Set id
	    				RuleIDGeneratorManager.INSTANCE.setID(project, tablePath, duplicatedRule);
	    				
	    				List<DecisionEntry> conditions = rule.getConditions();
	    				for (DecisionEntry condition : conditions) {
	    					duplicatedRule.addCondition(condition.clone());
	    				}
	    				List<DecisionEntry> actions = rule.getActions();
	    				for (DecisionEntry action : actions) {
	    					duplicatedRule.addAction(action.clone());
	    				}

	                	int ruleIndex = 
	                		decisionDataModel.addRule(duplicatedRule);
	                	/*decisionDataModel.addRowIDIndexEntry(duplicatedRule.getId(), 
	                                                         ruleIndex);*/
	                	createRule(duplicatedRule);
	                	createdIndexes.add(ruleIndex);
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
			return createdIndexes;
		}
		return null;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public Object commandCreated(CommandEvent<DuplicateCommand> commandEvent) {
		return addCommandCreated(commandEvent);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void commandUndone(CommandEvent<DuplicateCommand> commandEvent) {
		undoCommandCreation(commandEvent);
	}
	
	private void createRule(DecisionRule decisionRule) {
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
		DecisionTableUIPlugin.debug(this.getClass().getName(), "Duplicated Decision Rule id {0}", decisionRule.getId());
		TableRule newRule = 
			DecisionTableUtil.createEModelFromRule(decisionDataModel, 
					trs, 
					decisionRule);
		//This is an affected object and needs to be recorded
		affectedObjects.add(newRule);
		trs.getRule().add(newRule);
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

	@SuppressWarnings("unchecked")
	private void undoCommandCreation(CommandEvent<DuplicateCommand> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand) {
			ICreationCommand command = (ICreationCommand)source;
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			Object createdObject = command.getCreatedObject();
			if (createdObject instanceof List) {
				List<Integer> createdIndexes = (List<Integer>)createdObject;
				for (int loop = createdIndexes.size() - 1; loop >= 0; loop--) {
					int ruleIndex = createdIndexes.get(loop);
					decisionDataModel.removeRule(ruleIndex);
					String project = commandStack.getProject();
					//Get parent table
					Table table = command.getParent();
					String tablePath = table.getFolder() + table.getName();
					//Restore id
					RuleIDGeneratorManager.INSTANCE.setPreviousID(project, tablePath);
					removeRule(ruleIndex);
				}
			}
		}
	}
}
