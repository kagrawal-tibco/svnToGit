/**
 * 
 */
package com.tibco.cep.decisionproject.util;

/**
 * @author rmishra
 *
 */
public class DTConstants {

	public static final String DT_OPERATION = "DTOperation";
	public static final String DT_ADD = "Add";
	public static final String DT_DELETE = "Delete";
	public static final String DT_MODIFY = "Modify";
	public static final String STRING_TYPE = "String";
	public static final int DECISION_TABLE = 1;
	public static final int EXCEPTION_TABLE = 2;
	public static final int MODEL_SOURCE_EXCEL = 1;
	public static final int MODEL_SOURCE_NEW = 2;
	// Table Model Listener Constants 
	public static final String MODIFIED_RULE_INDEX_LIST = "modifiedRuleIndexList";
	public static final String MODIFIED_CONDITION_INDEX_LIST = "modifiedConditionIndexList";
	public static final String MODIFIED_ACTION_INDEX_LIST = "modifiedActionIndexList";
	// property type  type constants
	public static final String PROPERTY_TYPE_STRING = "String";
	public static final String PROPERTY_TYPE_LONG = "long";
	public static final String PROPERTY_TYPE_DOUBLE = "double";
	public static final String PROPERTY_TYPE_BOOLEAN = "boolean";
	public static final String PROPERTY_TYPE_INT = "int";
	public static final String PROPERTY_TYPE_INTEGER = "Integer";
	public static final String PROPERTY_TYPE_DATE_TIME = "datetime";
	public static final String PROPERTY_TYPE_CONCEPT = "concept";
	public static final String PROPERTY_TYPE_CONCEPT_REFERENCE = "conceptreference";
	
	//Metadata property names
	public static final String TABLE_RULE_PRIORITY = "Priority";
	public static final int TABLE_RULE_PRIORITY_DEF = 5;

	public static final String TABLE_PRIORITY = "Priority";
	public static final int TABLE_PRIORITY_DEF = 5;
	public static final String TABLE_EFFECTIVE_DATE = "EffectiveDate";
	public static final String TABLE_EXPIRY_DATE = "ExpiryDate";
	public static final String TABLE_SINGLE_ROW_EXECUTION = "SingleRowExecution";
	public static final boolean TABLE_SINGLE_ROW_EXECUTION_DEF = false;
	
	public static final String DECISION_PROJECT_EAR_FILE_PATH = "earFilePath";
	// constants for Sheet Names
	public static final String SHEET_DECISION_TABLE = "DecisionTable";
	public static final String SHEET_DOMAIN_MODEL = "DomainModel";
	
	//public static final int DECISION_TABLE = 1;
	//public static final int EXCEPTION_TABLE = 2;
	//public static final int MODEL_SOURCE_EXCEL = 1;
	//public static final int MODEL_SOURCE_NEW = 2;
	public static final String SINCE_BE_40 = "BE 4.0";
	public static final String PREFIX_IN = "(in)";
	public static final String PREFIX_OUT = "(out)";
	public static final String CUSTOM_CONDITION_PREFIX = "Custom Condition ";
	public static final String CUSTOM_ACTION_PREFIX = "Custom Action ";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"; //$NON-NLS-1$

	public static final String ASTERISK = "*";	
}
