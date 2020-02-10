package com.tibco.rta.service.persistence.memory;

public class InMemoryConstant {
	
	public static final String METRIC_TABLE_PREFIX = "metric";
	public static final String FACT_TABLE_PREFIX = "fact";
	public static final String RULE_STATE_TABLE_PREFIX = "rule_state";	
	public static final String SEP = "_";
	
	public static final String SCHEMA_NAME_FIELD = "schema_name";
	public static final String CUBE_NAME_FIELD = "cube_name";
	public static final String DIMHR_NAME_FIELD = "dimhr_name";
	public static final String SCHEMA_FIELD = "schema";
	public static final String CUBE_FIELD = "cube";
	public static final String DIMHR_FIELD = "dimhr";
	//TODO change it to dim level name later
	public static final String DIMENSION_LEVEL_FIELD = "dim_level";
	public static final String DIMENSION_LEVEL_NO = "dim_level_no";
	
	public static final String ISNEW = "isNew";
	public static final String ISDELTED = "isDeleted";
	public static final String IS_PROCESSED = "is_processed";
	
	
	public static final String PROPERTY_ATTRIBUTE = "property";
	public static final String DIMENSION_ATTRIBUTE = "dimension";
	public static final String MEASUREMENT_ATTRIBUTE = "measurement";
	public static final String METRIC_ATTRIBUTE = "metric";
	public static final String CONTEXT_ATTRIBUTE = "context";
	
	public static final String METRIC_KEY_FIELD = "metric_key";
	public static final String RULE_NAME_FIELD = "rule_name";
	public static final String RULE_ACTION_NAME_FIELD = "action_name";
	public static final String RULE_SET_COUNT_FIELD = "set_count";
	public static final String RULE_SCHEDULED_TIME_FIELD = "scheduled_time";
	public static final String RULE_LAST_FIRED_TIME_FIELD = "last_fired_time";
	public static final String RULE_SET_CONDITION_KEY_FILED = "set_condition_key";
	public static final String RULE_CLEAR_CONDITION_KEY_FILED = "clear_condition_key";
	public static final String UPDATED_DATE_TIME_FIELD = "updated_time";
	public static final String CREATED_DATE_TIME_FIELD = "created_time";	
	
	public static String MEASUREMENT_ASSETSTATUS = "AssetStatus";
	
	public static final String PARTITION_ON_ATTRIBUTE = "parition_on";
	
}
