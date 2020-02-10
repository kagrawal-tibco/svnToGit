package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.decision.table.utils.RuleIDGeneratorManager;

import ca.odell.glazedlists.EventList;

public class DecisionTableAddRowCommandListener implements ICommandCreationListener<AddCommand<TableRule>, TableRule> {
	
	private List<TableRule> affectedObjects;
	private EventList<TableRule> targetList; 
	private TableRuleSet targetRuleSet;
	private String project;
	private String tablePath;
	private Table table;
	private int insertionIndex;
	private InsertLocation insertLocation;
	
	public DecisionTableAddRowCommandListener(EventList<TableRule> targetList, TableRuleSet targetRuleSet, Table table, String path, int insertionIndex, InsertLocation loc) {
		this.targetList = targetList;
		this.targetRuleSet = targetRuleSet;
		affectedObjects = new ArrayList<TableRule>();
		this.project = table.getOwnerProjectName();
		this.tablePath = path;
		this.table = table;
		this.insertionIndex = insertionIndex;
		this.insertLocation = loc;
	}
	
	@Override
	public Object commandCreated(
			CommandEvent<AddCommand<TableRule>> commandEvent) {
		boolean resetRuleIDs = !DecisionTableUIPlugin.getDefault()
				.getPreferenceStore().getBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS);

		TableRule rule = DtmodelFactory.eINSTANCE.createTableRule();
		rule = DecisionTableUtil.createEModelFromRule(targetRuleSet, rule);
		RuleIDGeneratorManager.INSTANCE.setID(project, tablePath, rule);
		// don't adjust the rule IDs - either we always reset the IDs, or we always maintain the existing IDs
//		if (insertionIndex == -1) {
//			RuleIDGeneratorManager.INSTANCE.setID(project, tablePath, rule);
//		} else {
//			if (insertionIndex == 0) {
//				DecisionTableUtil.setID(rule, "1");
//			} else {
//				TableRule tableRule = targetList.get(insertionIndex-1);
//				String id = tableRule.getId();
//				id = String.valueOf(Integer.parseInt(id)+1);
//				DecisionTableUtil.setID(rule, id);
//			}
//		}
		EList<Column> cols = table.getDecisionTable().getColumns().getColumn();
		for(Column column: cols){
			if(column.getDefaultCellText()!=null){
				if(!column.getDefaultCellText().equalsIgnoreCase("")){
					TableRuleVariable ruleVariable = DtmodelFactory.eINSTANCE.createTableRuleVariable();
					ruleVariable.setColId(column.getId());
					ruleVariable.setId(rule.getId()+"_"+column.getId());
					ruleVariable.setExpr(column.getDefaultCellText());
					if(column.getColumnType().isAction()){
						rule.getAction().add(ruleVariable);
					}else{
						rule.getCondition().add(ruleVariable);
					}
				}
			}
		}
		targetList.getReadWriteLock().writeLock().lock();
		if (insertionIndex == -1) {
			targetRuleSet.getRule().add(rule);
			targetList.add(rule);
		} else {
//			if (!resetRuleIDs && reuseRuleIDs) {
//				DecisionTableUtil.adjustRuleIDs(targetList.get(insertionIndex).getId(), targetRuleSet);
//			}
			targetRuleSet.getRule().add(insertionIndex, rule);
			targetList.add(insertionIndex, rule);
			if (resetRuleIDs) {
				DecisionTableUtil.resetRuleIDs(targetRuleSet, table.getOwnerProjectName(), table.getPath());
			}
		}
		targetList.getReadWriteLock().writeLock().unlock();
		affectedObjects.add(rule);
		
		return rule.getId();
	}

	@Override
	public void commandUndone(CommandEvent<AddCommand<TableRule>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand) {
			ICreationCommand command = (ICreationCommand)source;
			Object createdObject = command.getCreatedObject();
			CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
			if (createdObject instanceof String) {
				Integer ruleIndex = Integer.valueOf((String) createdObject);
				TableRule targetRule = null;
				for(TableRule rule : targetList){
					if(rule.getId().equals(ruleIndex.toString())){
						targetRule = rule;
					}
				}
				targetList.getReadWriteLock().writeLock().lock();
				targetList.remove(targetRule);
				targetList.getReadWriteLock().writeLock().unlock();
				targetRuleSet.getRule().remove(targetRule);
				if (affectedObjects.size() > 0) {
					for (TableRule tableRule : affectedObjects) {
						DecisionTableUtil.decrementID(tableRule);
					}
				}
				//Restore id
				RuleIDGeneratorManager.INSTANCE.setPreviousID(project, tablePath);
			}
		}
	}

	@Override
	public List<TableRule> getAffectedObjects() {
		return affectedObjects;
	}
	
	
}
