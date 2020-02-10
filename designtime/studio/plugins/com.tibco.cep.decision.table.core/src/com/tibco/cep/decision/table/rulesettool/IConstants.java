package com.tibco.cep.decision.table.rulesettool;

/**
 * Constants Interface
 * @author sasahoo
 */
public interface IConstants {
		
	//Error Messages
	public static String INVALID_RULE_FILENAME = "error.invalidFileName";
	public static String RULE_FILENAME_EXISTS = "error.ruleFileExists";
	public static String INVALID_RULEFILE_EXTN = "error.invalidFileExtn";
	
	public static String STANDARD_FUNCTIONS = "Standard";
	public static String CUSTOM_FUNCTIONS 	= "Custom";
	public static String ONTOLOGY_FUNCTIONS = "Ontology";
	
	public static String FUNCTIONS_LABEL = "Functions";

	public static final String STANDARD_FUNCTIONS_LABEL = IConstants.STANDARD_FUNCTIONS + " " + IConstants.FUNCTIONS_LABEL;
	public static final String CUSTOM_FUNCTIONS_LABEL   = IConstants.CUSTOM_FUNCTIONS + " " + IConstants.FUNCTIONS_LABEL;
	public static final String ONTOLOGY_FUNCTIONS_LABEL   = IConstants.ONTOLOGY_FUNCTIONS + " " + IConstants.FUNCTIONS_LABEL;
	
}
