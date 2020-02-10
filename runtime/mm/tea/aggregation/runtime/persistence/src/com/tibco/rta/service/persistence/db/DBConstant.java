package com.tibco.rta.service.persistence.db;

public class DBConstant {
	public static final String SEP = "_";
	public static final String SPM_PRODUCT_NAME = "spm";
	public static final String FACT_KEY_FIELD = "fact_key";
	public static final String METRIC_KEY_FIELD = "metric_key";
	public static final String METRIC_ID_FIELD = "metric_id";
	public static final String METRIC_KEY_HASH_FIELD = "metric_key_hash";
	public static final String RULE_METRIC_ID_FIELD = "rule_metric_id";
	public static final String RULE_METRIC_KEY_HASH_FIELD = "rule_metric_key_hash";
	public static final String ALERT_ID = "alert_id";
	public static final String DIMHR_FIELD = "dimhr_name";
	public static final String CUBE_FIELD = "cube_name";
	public static final String METRIC_TABLE_PREFIX = "metric_";
	public static final String FACT_TABLE_PREFIX = "fact_";
	public static final String ASSET_TABLE_PREFIX = "asset_";
	public static final String PROCESSED_FACT_TABLE_PREFIX = "pf_";
	public static final String METRICS_FACT_TABLE_PREFIX = "mtx_ch_ft_";
	public static final String DIMENSION_LEVEL_NAME_FIELD = "dim_level_name";
	public static final String DIMENSION_LEVEL_FIELD = "dim_level";
	public static final String IS_PROCESSED = "is_processed";
	public static final String PROCESSED_FACTS_PREFIX = "pros_ft_";
	public static final String RULE_METRIC_TABLE_PREFIX = "rs_";
	public static final String ALERT_METRIC_TABLE_PREFIX = "as_";

	public static final String RULE_NAME_FIELD = "rule_name";
	public static final String RULE_CONTENT_FIELD = "rule_content";
	public static final String RULE_TABLE_NAME = "rule_info";
	public static final String RULE_ACTION_NAME_FIELD = "action_name";
	public static final String RULE_SET_COUNT_FIELD = "set_count";
	public static final String RULE_SCHEDULED_TIME_FIELD = "scheduled_time";
	public static final String RULE_LAST_FIRED_TIME_FIELD = "last_fired_time";
	public static final String RULE_SET_CONDITION_KEY_FILED = "set_condition_key";
	public static final String RULE_CLEAR_CONDITION_KEY_FILED = "clear_condition_key";
	public static final String OWNER_SCHEMA_FIELD = "owner_schema";
	public static final String ASSET_NAME_FIELD = "asset_name";
	public static final String ASSET_UID_FIELD = "asset_uid";
	public static final String ASSET_STATUS_FIELD = "asset_status";
	public static final String ASSET_IS_DELETED_FIELD = "is_deleted";
	public static final String UPDATED_DATE_TIME_FIELD = "updated_time";
	public static final String CREATED_DATE_TIME_FIELD = "created_time";
	public static final String ASSET_TIMESTAMP_FIELD = "asset_timestamp";
	// public static final String METRIC_VALUE_FILED_SUFFIX = "_value";
	// public static final String METRIC_CONTEXT_FIELD_SUFFIX = "_context";
	public static final String MULTIVALUED_METRIC_TABLE_VALUE_FIELD = "metric_value";
	public static final String MULTIVALUED_METRIC_TABLE_INDEX_FIELD = "value_index";

	public static final String ATTR_STORAGE_SCHEMA = "storage-schema";

	public static final String ATTR_STORAGE_TYPE = "storage-type";
	public static final String ATTR_STORAGE_SIZE = "storage-size";
	
	public static final String SESSION_TABLE_NAME = "session_info";
	public static final String SESSION_UID = "unique_id";
    public static final String SESSION_ID = "id";
    public static final String SESSION_NAME = "name";
    public static final String SESSION_QUERY_NAME = "Query_Name";
    public static final String SESSION_QUERY_DETAIL = "Query_Detail";
    public static final String SESSION_RULE_NAME = "Rule_Name";
    public static final String SESSION_RULE_DETAIL = "Rule_Detail";
    
    public static final int METRIC_KEY_MAX_SIZE = 500;
}
