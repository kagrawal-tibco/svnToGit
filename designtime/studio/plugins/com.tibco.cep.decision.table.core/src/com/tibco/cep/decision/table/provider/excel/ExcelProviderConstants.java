package com.tibco.cep.decision.table.provider.excel;


public class ExcelProviderConstants {

	public final static String SHEET_NAME = "DecisionTable";
	public static final String SUBSTITUTION_SUFFIX = "$SUB";

	protected final static String HEADER_DECLARATIONS = "Declarations";
	protected final static String HEADER_DECISION_TABLE = "DecisionTable";
	protected final static String HEADER_EXCEPTION_TABLE = "ExceptionTable";
	protected final static String HEADER_DOMAIN_MODEL = "DomainModel";
	protected final static String HEADER_GENERAL_PROPERTIES = "General Properties";

	protected final static String HEADER_DECLARATION_PATH = "Path";
	protected final static String HEADER_DECLARATION_ALIAS = "Alias";
	protected final static String HEADER_DECLARATION_PROPERTY = "Property";
	protected final static String HEADER_DECLARATION_LEGEND = "Legend";
	protected final static String HEADER_DECLARATION_DOMAINTYPE = "DomainType";
	protected final static String HEADER_DECLARATION_DOMAINVALUES = "DomainValues";

	protected final static String HEADER_CONDITION_COLUMN = "Condition";
	protected final static String HEADER_ACTION_COLUMN = "Action";
	protected final static String HEADER_CUSTOM_ACTION_COLUMN = "CustomAction";
	protected final static String HEADER_CUSTOM_CONDITION_COLUMN = "CustomCondition";

	protected final static String HEADER_ID_COLUMN = "Id";
	protected final static String HEADER_DESCRIPTION_COLUMN = "Description";
	protected final static String HEADER_PRIORITY_COLUMN = "Priority";

	protected final static String DOMAINVALUE_UNDEFINED = "Undefined";

	protected final static short INDEX_PATH = 0;
	protected final static short INDEX_ALIAS = 1;
	protected final static short INDEX_PROPERTY = 2;
	protected final static short INDEX_GRAPHICAL_ALIAS_PATH = 3;
	protected final static short INDEX_DOMAIN_TYPE = 4;
	protected final static short INDEX_DOMAIN_VALUES = 5;

	protected final static String EXCEL_VERSION = "Version";
	protected final static String COLUMN_ALIAS_ID = "ALIAS:";
	
	//Values for texts that appear in exported xls under General Properties section.
	protected final static String GEN_PROP_EFFECTIVE_DATE_TEXT = "Effective Date";
	protected final static String GEN_PROP_EXPIRY_DATE_TEXT = "Expiry Date";
	protected final static String GEN_PROP_SINGLE_ROW_TEXT = "Single Row Execution";
	protected final static String GEN_PROP_TABLE_PRIORITY_TEXT = "Table Priority";
}
