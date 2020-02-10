package com.tibco.rta.model.serialize.impl;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/10/12
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModelSerializationConstants {

    public static final String ELEM_SCHEMA_NAME = "schema";
    public static final String ELEM_SCHEMAS_NAME = "schemas";
    public static final String ELEM_CUBES_NAME = "cubes";
    public static final String ELEM_CUBE_NAME = "cube";
    public static final String ELEM_FACT_NAME = "fact";
    public static final String ELEM_FACTS_NAME = "facts";
    public static final String FIELD_KEY = "key";
    public static final String ELEM_HIERARCHYS_NAME = "hierarchies";
    public static final String ELEM_HIERARCHY_NAME = "hierarchy";
    public static final String ELEM_MEASUREMENTS_NAME = "measurements";
    public static final String ELEM_MEASUREMENT_NAME = "measurement";
    public static final String ELEM_DIMENSION_NAME = "dimension";
    public static final String ELEM_TIME_DIMENSION_NAME = "time-dimension";
    public static final String ELEM_DIM_NAME = "dimName";
    public static final String ELEM_DIMENSION_REF_NAME = "dim";
    public static final String ELEM_ATTRIBUTE_REF_NAME = "attr";
    public static final String ELEM_DIMENSIONS_NAME = "dimensions";
    public static final String ELEM_METRIC_DESCS_NAME = "metricDescriptors";
    public static final String ELEM_RULE_PRIORITY = "priority";
    public static final String ELEM_FUNCTION_CONTEXT = "function-context";
    
    public static final String ELEM_METRIC_FN_BINDINGS_NAME = "metric-function-bindings";
    public static final String ELEM_METRIC_FNS_NAME = "metric-functions";
    public static final String ELEM_METRIC_FN_NAME = "metric-function";

    public static final String ELEM_MEASUREMENT_BINDINGS_NAME = "measurement-bindings";
    public static final String ELEM_MEASUREMENT_BINDING_NAME = "measurement-binding";
    public static final String ELEM_MEASUREMENT_REFS_NAME = "measurement-refs";

    public static final String ELEM_METRIC_DESC_NAME = "md";

    public static final String ELEM_ATTRIBUTES_NAME = "attributes";
    public static final String ELEM_ATTRIBUTE_NAME = "attribute";

    public static final String ELEM_VALUE_NAME = "value";
    public static final String ELEM_VALUES_NAME = "values";
    
    public static final String ELEM_URL = "url";
    public static final String ELEM_QUERY_NAME = "query";
    public static final String ELEM_MODEL_NAME = "model";
    public static final String ELEM_SCOPE_NAME = "scope";
    
    public static final String ELEM_RULE_NAME = "rule";
    public static final String ELEM_RULES_NAME = "rules";
    public static final String ELEM_MAXCOUNT_NAME = "max-count";
    public static final String ELEM_SCHEDULE_NAME = "schedule";
    public static final String ELEM_USER_NAME = "user-name";
    public static final String ELEM_OWNER_NAME = "owner-name";
    public static final String ELEM_VERSION_NAME = "version";    
    public static final String ELEM_RULEINFO_NAME = "rule-info";
    public static final String ELEM_CREATED_DATE = "created-date";
    public static final String ELEM_MODIFIED_DATE = "modified-date";    
    public static final String ELEM_SETACTIONS_NAME = "set-actions";
    public static final String ELEM_CLEARACTIONS_NAME = "clear-actions";
    public static final String ELEM_ACTION_NAME = "action";
    public static final String ELEM_ACTIONS_NAME = "actions";
    public static final String ELEM_CONDITION_NAME = "condition";
    public static final String ELEM_SET_CONDITION = "set-condition";
    public static final String ELEM_CLEAR_CONDITION = "clear-condition";
    
    public static final String ELEM_FILTERS_NAME = "filters";
    public static final String ELEM_EQFILTER_NAME = "eqfilter";
    public static final String ELEM_NEQFILTER_NAME = "neqfilter";
    public static final String ELEM_GEFILTER_NAME = "gefilter";
    public static final String ELEM_GTFILTER_NAME = "gtfilter";
    public static final String ELEM_LEFILTER_NAME = "lefilter";
    public static final String ELEM_LTFILTER_NAME = "ltfilter";
    public static final String ELEM_AND_FILTER = "andfilter";
    public static final String ELEM_OR_FILTER = "orfilter";
    public static final String ELEM_NOT_FILTER = "notfilter";
    public static final String ELEM_LIKEFILTER_NAME = "likefilter";
    public static final String ELEM_INFILTER_NAME = "infilter";
    
    public static final String ELEM_FILTER_TYPE = "type";
    public static final String ELEM_FILTER_VALUE = "value";

    public static final String ELEM_METRIC_NAME = "metric";
    public static final String ELEM_KEY_NAME = "key";
    public static final String ELEM_DESC_NAME = "desc";
    public static final String ELEM_METRICFUNCTION_DESCRIPTOR= "metric-function-descriptor";
    public static final String ELEM_METRICFUNCTION_DESCRIPTOR_NAME="metric-function-desc-name";
    public static final String ELEM_METRICFUNCTION_DESCRIPTOR_CATEGORY="metric-function-category";
    public static final String ELEM_METRICFUNCTION_DESCRIPTOR_MULTIVALUED="metric-function-multivaled";
    public static final String ELEM_METRICFUNCTION_DESCRIPTOR_CLASS="metric-function-class";
    public static final String ELEM_METRICFUNCTION_DESCRIPTOR_DATATYPE="metric-function-datatype";
    public static final String ELEM_METRICFUNCTION_DESCRIPTOR_DESC="metric-function-desc";
    public static final String ELEM_COMPUTATION_VALUE="computed-value";
    public static final String ELEM_VALUE_DATATYPE="value-data-type";
    
    public static final String ELEM_TUPLE_NAME = "tuple";
    public static final String ELEM_RESULTS_NAME = "results";
    public static final String ELEM_FILTER_NAME = "name";
    public static final String ELEM_METRIC_QUALIFIER = "metricqualifier";
    public static final String ELEM_FILTER_QUALIFIER = "filterqualifier";

    public static final String ELEM_METADATA_NAME = "metadata";
    public static final String ELEM_METADATA_BATCH = "batch";

    public static final String ELEM_FUNCTION_DESCRIPTORS = "function-descriptors";
    public static final String ELEM_FUNCTION_DESCRIPTOR = "function-descriptor";
    public static final String ELEM_FUNCTION_PARAMS = "function-params";
    public static final String ELEM_FUNCTION_PARAM = "function-param";


    public static final String ELEM_ACTION_DESCRIPTORS = "action-descriptors";
    public static final String ELEM_ACTION_DESCRIPTOR = "action-descriptor";
    public static final String ELEM_ACTION_PARAMS = "action-params";
    public static final String ELEM_ACTION_PARAM = "action-param";    
    
    public static final String ELEM_FUNCTIONPARAM_BINDINGS="function-param-bindings";
    public static final String ELEM_FUNCTIONPARAM_BINDING="func-param-binding";
    
    public static final String ELEM_ORDERBY_TUPLE="order-by-tuple";
    public static final String ELEM_NAME_NAME = "name";
    public static final String ELEM_REF_NAME = "ref-name";
    public static final String ELEM_TUPLENAME_NAME = "tupleName";
    public static final String ELEM_RETENTION_POLICIES = "retention-policies";
    public static final String ELEM_RETENTION_POLICY = "retention-policy";
    					
    public static final String ELEM_ASSETS_NAME = "assets";
    public static final String ELEM_ASSET_NAME = "asset";
    public static final String ELEM_PROPERTY_NAME = "property";
    public static final String ELEM_PROPERTIES_NAME = "properties";
    public static final String ELEM_DIMENSION_MAP = "dimensions";
    
    public static final String ELEM_RULE_ENABLED = "enabled";
    public static final String ELEM_RULE_SET_ONCE = "isSetOnce";
    public static final String ELEM_STREAMING_QUERY = "isStreaming";
    public static final String ELEM_DIMENSION_LEVEL = "dim-level";
    public static final String ELEM_FUNCTION_PARAM_VALUE = "param-value";
    
    public static final String ATTR_SYSTEM_SCHEMA = "system-schema";
    public static final String ATTR_ASSET_REF = "asset-ref";
    public static final String ATTR_VALIDATE_ASSET = "validate-asset";
    public static final String ATTR_IS_MANDATORY = "mandatory";
    public static final String ATTR_DISPLAY_NAME = "display-name";
    public static final String ATTR_ID_NAME = "id";
    public static final String ATTR_REF_NAME = "ref";
    public static final String ATTR_REF_ACTION_NAME = "ref-action";
    public static final String ATTR_REF_NAME_NAME = "ref-name";
    public static final String ATTR_ATTR_REF_NAME = "attribute-ref";
    public static final String ATTR_EXCLUDE_NAME = "excludes";
    public static final String ATTR_DEPENDS_NAME = "depends";
    
    public static final String ATTR_PARENT_NAME = "parent";
    public static final String ATTR_NAME_NAME = "name";
    public static final String ATTR_USERNAME = "username";
    public static final String ATTR_VALUE_NAME="value";
    public static final String ATTR_ATTR_NAME= "attribute-name";
    public static final String ATTR_PASSWORD = "password";
    public static final String ATTR_DATATYPE_NAME = "datatype";
    public static final String ATTR_STORAGE_DATATYPE_NAME = "storage-datatype";
    public static final String ATTR_IMPL_CLASS = "implclass";    
    public static final String ATTR_MEASUREMENT_NAME = ELEM_MEASUREMENT_NAME;
    public static final String ATTR_SCHEMA_NAME = ELEM_SCHEMA_NAME;
    public static final String ATTR_QUERY_NAME = ELEM_QUERY_NAME;
    public static final String ATTR_TYPE_NAME = "type";
    public static final String ATTR_QUERYDEF_TYPE = "querydef-type";
    public static final String ATTR_QUERYDEFBY_FILTER="QueryByFilter";
    public static final String ATTR_QUERYDEFBY_KEY="QueryByKey";
    public static final String ATTR_LEVEL_NAME = "level";    
    public static final String ATTR_CATEGORY = "category";    
    public static final String ATTR_DESCRIPTION = "description";    
    public static final String ATTR_ORDINAL = "ordinal";    
    public static final String ATTR_DEFAULT_VALUE = "defaultvalue";   
    public static final String ATTR_ACTION_PARAM_ID ="ref-action-param-id";
    public static final String ATTR_FREQUENCY_NAME = "frequency";
    public static final String ATTR_MAXCOUNT_NAME = "maxCount";
    public static final String ATTR_TIME_CONSTRAINT = "timeconstraint";
    public static final String TIME_CONSTRAINT = "time-Constraint";
    public static final String ATTR_COMPUTE_NAME = "compute";    
    public static final String ATTR_SORT_NAME="sort-order";

    public static final String ATTR_INDEX_NAME= "index";
    public static final String ATTR_DIMENSION_TYPE = "type";
    public static final String ATTR_TIME_DIMENSION_UNIT = "unit";
    public static final String ATTR_TIME_DIMENSION_FREQUENCY = "frequency";
    public static final String ATTR_TIME_DIMENSION_QTR_OFFSET = "qtroffset";
    public static final String ATTR_PERSIST_NAME="persist";
    public static final String ATTR_PURGE_FREQUENCY_PERIOD = "purge-frequency-period";
    public static final String ATTR_PURGE_TIME_DAY = "purge-time-of-day";
    public static final String ATTR_RETENTION_COUNT = "retention-count";
    public static final String ATTR_PERIOD_NAME = "period";
    public static final String ATTR_UNIT_NAME = "unit";
    public static final String ATTR_ALERT_LEVEL = "alert-level";
    
//    public static final String ATTR_PING_SERVICE = "ping-service";
//    public static final String ATTR_STORAGE_SCHEMA = "storage-schema";
    public static final String PARAM_BINDING = "parambinding";

    public static final String IS_MULTI_VALUED = "multivalued";
    public static final String ATTR_HIERARCHY_ENABLED = "enabled";
    
    public static final String ATTR_IS_ALERT_HIERARCHY = "alert-hierarchy";
}
