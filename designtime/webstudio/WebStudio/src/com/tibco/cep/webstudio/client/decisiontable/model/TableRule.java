package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author sasahoo
 *
 */
public class TableRule {

	private String id;
	private List<TableRuleVariable> conditions;
	private List<TableRuleVariable> actions;
	private List<MetaData> metadata;
	private boolean isEnabled = true;
	private boolean isNewRule = false;
	/**
	 * @param id
	 * @param conditions
	 * @param actions
	 * @param metadata
	 */
	public TableRule(String id) {
		this.id =id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TableRuleVariable> getConditions() {
		return conditions;
	}

	public void setConditions(List<TableRuleVariable> conditions) {
		this.conditions = conditions;
	}

	public List<TableRuleVariable> getActions() {
		return actions;
	}

	public void setActions(List<TableRuleVariable> actions) {
		this.actions = actions;
	}

	public List<MetaData> getMetaData() {
		return metadata;
	}

	public void setMetaData(List<MetaData> metaData) {
		this.metadata = metaData;
	}

    public void addAction(TableRuleVariable action) {
    	action.setTableRule(this);
    	actions.add(action);
    }

    public void addCondition(TableRuleVariable condition) {
    	condition.setTableRule(this);
    	conditions.add(condition);
    }
        
    public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled, boolean isAllEnabled) {
		if(isAllEnabled) { 
			for(TableRuleVariable ruleVar : conditions) {
				ruleVar.setEnabled(isEnabled);
			}
			for(TableRuleVariable ruleVar : actions) {
				ruleVar.setEnabled(isEnabled);
			}
		}
		this.isEnabled = isEnabled;		
	}

	public TableRule copy(String newId) {
		TableRule newRule = new TableRule(newId); 
		List<TableRuleVariable> newConditions = new ArrayList<TableRuleVariable>();
		List<TableRuleVariable> newActions = new ArrayList<TableRuleVariable>();
		newRule.setConditions(newConditions);
		newRule.setActions(newActions);
		
		List<TableRuleVariable> conditions = this.getConditions();
		for (TableRuleVariable condition : conditions) {
			TableRuleVariable newCondition = (TableRuleVariable) condition.copy();
			String tableRulevarId = newRule.getId() + "_" + newCondition.getColumnId();
			newCondition.setId(tableRulevarId);
			newRule.addCondition(newCondition);					
		}
		List<TableRuleVariable> actions = this.getActions();
		for (TableRuleVariable action : actions) {
			TableRuleVariable newAction = (TableRuleVariable) action.copy();
			String tableRulevarId = newRule.getId() + "_" + newAction.getColumnId();
			newAction.setId(tableRulevarId);
			newRule.addAction(newAction);
		}
		newRule.setEnabled(isEnabled, false);
		return newRule;
	}

	public boolean isNewRule() {
		return isNewRule;
	}

	public void setNewRule(boolean isNewRule) {
		this.isNewRule = isNewRule;
	}
	
}
