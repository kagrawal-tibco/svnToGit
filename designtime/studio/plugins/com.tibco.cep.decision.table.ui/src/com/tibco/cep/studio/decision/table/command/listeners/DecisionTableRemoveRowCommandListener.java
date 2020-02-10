package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.decision.table.command.AbstractExecutableCommand;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.studio.decision.table.generator.IDGenerator;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.decision.table.utils.RuleIDGeneratorManager;

import ca.odell.glazedlists.EventList;

public class DecisionTableRemoveRowCommandListener implements ICommandCreationListener<RemoveCommand<TableRule>, TableRule>{

	private List<TableRule> affectedObjects;
	private EventList<TableRule> targetList; 
	private TableRuleSet targetRuleSet;
	
	public DecisionTableRemoveRowCommandListener(EventList<TableRule> targetList, 
			TableRuleSet targetRuleSet, List<TableRule> selectedRules){
		this.targetList = targetList;
		this.targetRuleSet = targetRuleSet;
		this.affectedObjects = selectedRules;
	}
	
	@Override
	public Object commandCreated(CommandEvent<RemoveCommand<TableRule>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IRemovalCommand) {
			boolean resetRuleIDs = !DecisionTableUIPlugin.getDefault()
					.getPreferenceStore().getBoolean(PreferenceConstants.ALWAYS_MAINTAIN_RULE_IDS);
			boolean reuseRuleIDs = DecisionTableUIPlugin.getDefault()
					.getPreferenceStore().getBoolean(PreferenceConstants.REUSE_DELETED_RULE_IDS);
			boolean removedLastRow = false;
			EList<TableRule> rules = targetRuleSet.getRule();
			int rulesSize = rules.size();
			for (TableRule tableRule : affectedObjects) {
				if (rules.indexOf(tableRule) == rulesSize-1) {
					removedLastRow = true;
					break;
				}
			}
			targetList.getReadWriteLock().writeLock().lock();
			targetRuleSet.getRule().removeAll(affectedObjects);
			targetList.removeAll(affectedObjects);
			targetList.getReadWriteLock().writeLock().unlock();
			if (resetRuleIDs) {
				Table parentTable = (Table) targetRuleSet.eContainer();
				DecisionTableUtil.resetRuleIDs(targetRuleSet, parentTable.getOwnerProjectName(), parentTable.getPath());
			} else if (reuseRuleIDs) {
				DecisionTableUtil.resetRuleIDGenerator(targetRuleSet);
			}

			return affectedObjects;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
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
			if (removedObject instanceof ArrayList<?>) {
				List<TableRule> removedRules = (ArrayList<TableRule>)removedObject;
				List<TableRule> newRules = new ArrayList<TableRule>();
				int maxId = 0;
				for (TableRule tableRule : removedRules) {
					TableRule copy = EcoreUtil.copy(tableRule);
					copy.setId(String.valueOf(tableRule.getId()));
					maxId = Math.max(maxId, Integer.valueOf(copy.getId()));
					newRules.add(copy);
				}
				targetList.getReadWriteLock().writeLock().lock();
				targetRuleSet.getRule().addAll(newRules);
				targetList.addAll(newRules);
				targetList.getReadWriteLock().writeLock().unlock();
				Table table = (Table) targetRuleSet.eContainer();
				IDGenerator<String> idGenerator = RuleIDGeneratorManager.getIDGenerator(table.getOwnerProjectName(), table.getPath());
				if (Integer.valueOf(idGenerator.getCurrentID()) < maxId + 1) {
					idGenerator.setID(Integer.toString(maxId+1));
				}
			}
		}		
	}

	@Override
	public List<TableRule> getAffectedObjects() {
		return affectedObjects;
	}

}
