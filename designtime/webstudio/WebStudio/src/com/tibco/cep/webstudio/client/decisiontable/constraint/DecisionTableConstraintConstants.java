package com.tibco.cep.webstudio.client.decisiontable.constraint;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableConstraintConstants {

	public final static String[] OPERATORS = new String[]{"==", ">=", "<=", "<>", "!=", ">", "<", "="};

	public static final String DONT_CARE = "*";
	
	public static final String MIN_VALUE = "min.value";
	public static final String MAX_VALUE = "max.value";

	public static final String OVERLAPPING_RANGE_WARNING = "OverlappingRange";
	public static final String UNCOVERED_RANGE_WARNING = "UncoveredRange";
	public static final String POTENTIAL_COMBINATION_WARNING = "CombinationWarning";
	public static final String MARKER_ATTR_COLUMN_NAME = "COLUMN_NAME";
	public static final String MARKER_ATTR_DUPLICATE_RULEID = "RULE_DUPLICATE_ID";
	public static final String MARKER_ATTR_CONFLICTING_ACTIONS_RULEID = "RULE_CONFLICTS_ID";
	public static final String MARKER_ATTR_RULE_COMBINATION_PROBLEM = "RULE_COMBINATION_PROBLEM";
	public static final String MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY = "DOMAIN_ENTRY_MISSING";
	public static final String MARKER_ATTR_RANGE_MIN_VALUE = "RANGE_MIN";
	public static final String MARKER_ATTR_RANGE_MAX_VALUE = "RANGE_MAX";
	public static final String MARKER_ATTR_RANGE_PROBLEM = "RANGE_TYPE_PROBLEM";
	public static final String MARKER_ATTR_MISSING_EQUALS_CRITERION_PROBLEM = "MISSING_EQUALS_CRITERION_PROBLEM";
	
	
}
