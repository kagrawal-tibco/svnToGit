package com.tibco.tea.agent.be.util;

/**
 * This class is used to hold the properties of Business Events TEA agent.
 * 
 * @author dijadhav
 *
 */
public class BETeaAgentProps {
	//Prop names
	public static final String TEA_SERVER_URL = "tea.server.url";
	public static final String PORT = "tea.agent.be.port";
	public static final String AGENT_VERSION = "tea.agent.be.version";
	public static final String NAME = "tea.agent.be.name";
	public static final String CONTEXT = "tea.agent.be.context";
	public static final String RESOURCES_BASE = "tea.agent.be.resource.base";
	
	
	//Constants
	public static final String AGENT_TYPE_DESCRIPTION = "TIBCO's event driven solution allows you to capture and analyze data in real-time";
	
	
	// Schema
	public static String BEMM_FACT_SCHEMA = "BE_MM";
	public static String BEMM_SYSTEM_SCHEMA = "BETEA";
	public static String BEMM_ALERTS_SCHEMA="BE_MM_ALERTS";
	
	// Cube(s)
	public static String CUBE_BEMM = "BEMMCube";
	
	//Properties stored in schema/rule 
	public static final String BEMM_RULE_ENTITY_PROP = "entity";
	public static final String BEMM_RULE_IS_ADMIN = "isAdmin";
	public static final String BEMM_RULE_STORAGE_TYPE = "storage-datatype";
	
	public static final String BEMM_RULE_ALERT_TOKEN = "alert-token";
	
	//Properties to be sent to UI for a measurement/dimension
	public static final String BEMM_RULE_ATTR_NAME = "name";
	public static final String BEMM_RULE_ATTR_TYPE = "type";    //Dimension Name or Measurement name
	public static final String BEMM_RULE_DISPLAY_NAME = "display-name";
	public static final String BEMM_RULE_DISPLAY_TYPE = "display-storage-datatype";
	public static final String BEMM_RULE_DIMENSION_NAME = "DIMENSION_NAME";
	public static final String BEMM_RULE_MEASUREMENT_NAME = "MEASUREMENT_NAME";
	
	
	// Chart Filter Attributes
	public static String METRIC_VIEW_ATTR_TIME="time";
	
	// BE Rule Perisitence Properties
	public static final String BE_DATASTORE_RULE_FOLDER = "rules";
	public static final String BE_DATASTORE_RULE_EXTENSION = "xml";
	public static final String BE_RULE_APP_NAME_SEPERATOR = "$";
	public static final String BE_RULES_DATASTORE_PROP_NAME="BE_RULES_DATASTORE";
	public static final String BE_RULE_APP_NAME_SEPERATOR_PROP_NAME = "BE_RULE_APP_NAME_SEPERATOR";
	public static final String BE_DATASTORE_RULE_EXTENSION_PROP_NAME = "BE_DATASTORE_RULE_EXTENSION";
	
	
	//BEMM Set Health Action constants
	public static final String BEMM_HEALTH_ACTION_HEALTH_ENTITY= "entity";
	
	
	//Return success string
	public static final String BE_OPERTATION_SUCCESS = "succes";
	//Return fail string
	public static final String BE_OPERTATION_FAIL = "failure";
	
	public static final String PROP_CHART_MAX_SERIES="max-series";
	//Global lock constant
	public static final String GLOBAL_LOCK_KEY = "GLOBAL_LOCK";
	
}
