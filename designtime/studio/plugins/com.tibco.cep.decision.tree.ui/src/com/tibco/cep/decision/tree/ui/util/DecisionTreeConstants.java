package com.tibco.cep.decision.tree.ui.util;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreeConstants {
	
	public enum NODE {
		ACTION, ASSIGNMENT, ASSOCIATION, BOOLEAN_CONDITION, BREAK, CALL_DECISION_TABLE, CALL_DECISION_TREE, CALL_RF, DESCRIPTION, END, LOOP, START, SWITCH_CONDITION, TRANSITION 
	}
	
	public static final String NODE_TYPE = "Type";
	public static final String UNIQUE_ACTION_NODE = "action_";
	public static final String UNIQUE_ASSOCIATION = "._";
	public static final String UNIQUE_ASSIGNMENT_NODE = "eq_";
	public static final String UNIQUE_BOOLEAN_CONDITION_NODE = "cond_";
	public static final String UNIQUE_BREAK_NODE = "break_";
	public static final String UNIQUE_CALL_RF_NODE = "rf_";
	public static final String UNIQUE_CALL_TABLE_NODE = "uri_";
	public static final String UNIQUE_CALL_TREE_NODE = "tree_";
	public static final String UNIQUE_DESCRIPTION_NODE = "desc_";
	public static final String UNIQUE_END_NODE = "end_";
	public static final String UNIQUE_LOOP_NODE = "loop_";
	public static final String UNIQUE_START_NODE = "start_";
	public static final String UNIQUE_SWITCH_CONDITION_NODE = "switch_";
	public static final String UNIQUE_TRANSITION = "edge_";
	
	public static final String isExpanded = "isExpanded";

	public static final String UNIQUE_NODE = "node_";

}
