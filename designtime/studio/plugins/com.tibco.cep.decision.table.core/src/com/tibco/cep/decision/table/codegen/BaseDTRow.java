package com.tibco.cep.decision.table.codegen;

import java.util.ArrayList;
import java.util.Arrays;

import com.tibco.cep.decisionproject.util.DTConstants;

public class BaseDTRow<COND, ACT> {
	private ArrayList<COND> conditions;
	private ArrayList<ACT> actions;
	private int priority = DTConstants.TABLE_RULE_PRIORITY_DEF;
	private int rowId;
	
	public BaseDTRow(ArrayList<COND> conditions, ArrayList<ACT> actions, int priority, String rowId) {
		this.conditions = conditions;
		this.actions = actions;
		this.priority = priority;
		this.rowId = Integer.parseInt(rowId);
	}
	
	public BaseDTRow(COND[] conditions, ACT[] actions, int priority, String rowId) {
		if(conditions == null) {
			this.conditions = null;
		} else {
			this.conditions = new ArrayList<COND>(Arrays.asList(conditions));
		}
		if(actions == null) {
			this.actions = null;
		} else {
			this.actions = new ArrayList<ACT>(Arrays.asList(actions));
		}
	}

	public ArrayList<COND> getConditions() {
		return conditions;
	}

	public ArrayList<ACT> getActions() {
		return actions;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public int getRowId() {
		return rowId;
	}
}
