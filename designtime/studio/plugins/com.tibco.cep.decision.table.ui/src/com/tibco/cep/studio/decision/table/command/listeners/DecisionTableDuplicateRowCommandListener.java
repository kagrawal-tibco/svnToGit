package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import ca.odell.glazedlists.EventList;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.DuplicateCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.decision.table.utils.RuleIDGeneratorManager;

public class DecisionTableDuplicateRowCommandListener implements ICommandCreationListener<DuplicateCommand, TableRule>{

	private List<TableRule> affectedObjects;
	private EventList<TableRule> targetList; 
	private TableRuleSet targetRuleSet;
	private String project;
	private String tablePath;
	
	public DecisionTableDuplicateRowCommandListener(EventList<TableRule> targetList, 
			TableRuleSet targetRuleSet, List<TableRule> selectedRules, String project, String path){
		this.targetList = targetList;
		this.targetRuleSet = targetRuleSet;
		this.affectedObjects = selectedRules;
		this.project = project;
		this.tablePath = path;
	}
	
	@Override
	public Object commandCreated(CommandEvent<DuplicateCommand> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand 
				&& source instanceof AbstractExecutableCommand) {
			List<Integer> createdIndices = new ArrayList<Integer>();
			List<TableRule> newRules = new ArrayList<TableRule>();
			for (TableRule tableRule : affectedObjects) {
				TableRule copy = EcoreUtil.copy(tableRule);
				RuleIDGeneratorManager.INSTANCE.setID(project, tablePath, copy);
				createdIndices.add(Integer.parseInt(copy.getId()));
				newRules.add(copy);
			}
			targetList.getReadWriteLock().writeLock().lock();
			targetRuleSet.getRule().addAll(newRules);
			targetList.addAll(newRules);
			targetList.getReadWriteLock().writeLock().unlock();
			affectedObjects.addAll(newRules);
			boolean resetRuleIDs = !DecisionTableUIPlugin.getDefault()
					.getPreferenceStore().getBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS);
			boolean reuseRuleIDs = !DecisionTableUIPlugin.getDefault()
					.getPreferenceStore().getBoolean(PreferenceConstants.REUSE_DELETED_RULE_IDS);
			if (resetRuleIDs) {
				Table parentTable = (Table) targetRuleSet.eContainer();
				DecisionTableUtil.resetRuleIDs(targetRuleSet, parentTable.getOwnerProjectName(), parentTable.getPath());
			} else if (reuseRuleIDs) {
				DecisionTableUtil.resetRuleIDGenerator(targetRuleSet);
			}
			
			return createdIndices;
		}
		return null;
	}

	@Override
	public void commandUndone(CommandEvent<DuplicateCommand> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof ICreationCommand) {
			ICreationCommand command = (ICreationCommand)source;
			Object createdObject = command.getCreatedObject();
			if (createdObject instanceof List<?>) {
				List<Integer> createdIndexes = (List<Integer>)createdObject;
				List<TableRule> duplicatedRules= new ArrayList<TableRule>();
				for (int loop = createdIndexes.size() - 1; loop >= 0; loop--) {
					int ruleIndex = createdIndexes.get(loop);
					EList<TableRule> rules = targetRuleSet.getRule();
					for (TableRule tableRule : rules) {
						int currId = Integer.valueOf(tableRule.getId());
						if (ruleIndex == currId){
							duplicatedRules.add(tableRule);
							//restore ID
							RuleIDGeneratorManager.INSTANCE.setPreviousID(project, tablePath);
						}
					}
				}
				targetList.getReadWriteLock().writeLock().lock();
				targetRuleSet.getRule().removeAll(duplicatedRules);
				targetList.removeAll(duplicatedRules);
				targetList.getReadWriteLock().writeLock().unlock();
			}
		}
	}

	@Override
	public List<TableRule> getAffectedObjects() {
		return affectedObjects;
	}

}
