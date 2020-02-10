package com.tibco.cep.bemm.monitoring.metric;

import java.util.ArrayList;

public class MetricAttribute {
	
	public static final String CLUSTER = "appName";
	public static final String CLUSTER_HEALTH = "apphealth";
	public static final String APPLICATION = "applicationName";
	public static final String HOST = "host";
	public static final String HOST_IS_PREDEFINED = "hostispredefined";
	public static final String PU_INSTANCE = "instanceName";
	public static final String PU_NAME = "puName";
	public static final String PU_INSTANCE_IS_PREDEFINED = "instanceispredefined";
	public static final String PU_INSTANCE_COUNT = "instancecount";
	public static final String PU_AGENT_COUNT = "agentcount";
	public static final String PU_INSTANCE_HEALTH = "instancehealth";
	public static final String PU_INSTANCE_ISACTIVE = "instanceisactive";
	
	
	public static final String AGENT_TYPE = "agentType";
	public static final String AGENT_NAME = "agentName";
	public static final String AGENT_ISACTIVE = "agentisactive";
	public static final String AGENT_HEALTH = "agenthealth";
	public static final String AGENT_COUNT = "agentcount";
	public static final String TIMESTAMP = "timestamp";
	
	public static final String JVM_UPTIME = "jvmuptime";
	public static final String JVM_FORMATTED_UPTIME = "formatjvmuptime";
	
	//CPU Metric attributes
	public static final String CPU_COUNT = "cpucount";
	public static final String CPU_TIME = "cputime";
	public static final String CPU_USAGE = "cpuusage";
	
	//Memory Metric attributes
	public static final String MEMORY_INITIAL_SIZE = "initmemsize";
	public static final String MEMORY_USED_SIZE = "usedmemsize";
	public static final String MEMORY_COMMITTED_SIZE = "committedmemsize";
	public static final String MEMORY_MAX_SIZE = "maxmemsize";
	
	//Thread Metric attributes
	public static final String THREAD_COUNT = "threadcount";
	public static final String THREAD_DEADLOCKED_COUNT = "deadlockedthreadcount";
		
	//Garbage collector Metric attributes
	public static final String GC_POOL_NAME = "gcpoolname";
	public static final String GC_POOL_COLLECTION_COUNT = "gcpoolcollectioncount";
	public static final String GC_POOL_COLLECTION_TIME = "gcpoolcollectiontime";
	public static final String GC_FORMATTED_POOL_COLLECTION_TIME = "formatgcpoolcollectiontime";
	
	
	//Dimension Attributes
	public static final String DIM_APP = "app";
	public static final String DIM_PUN = "pu";
	public static final String DIM_INSTANCE = "instance";
	public static final String DIM_AGENT_TYPE = "agentType";
	public static final String DIM_AGENT = "agent";
	
	//Dimension Hierarchies
	public static final String HIERARCHY_INSTANCEHEALTH = "instancehealthhierarchy";
	public static final String HIERARCHY_CLUSTERHEALTH = "clusterhealthhierarchy";
	
	// Cube(s)
	public static final String CUBE_BEMM = "BEMMCube";
	
	public static final String CLUSTER_DUMMY = "dummyapp";  //This is a dummy attribute , which will differentiate our two hierarchies
	public static final String CLUSTER_DUMMY_VALUE = "dummyapp";  
	public static final String DUMMY_ATTR = "dummy";	   //Used by the SetHealthAction to send health for levels where lower dimensions are not available
	
	// Metrics
	public static final String METRIC_DUMMY_INST_HEALTH = "instancedummyhealth";
	public static final String METRIC_DUMMY_APP_HEALTH = "appdummyhealth";
	
	//Alerts metrics/dimensions/attributes
	public static final String ALERTS_SCHEMA="BETEA";
	public static final String ALERTS_CUBE="SystemAlerts";
	public static final String ALERTS_HIERARCHY="Alerts";
	
	public static final String ALERTS_DIM_ACTIONNAME="actionName";
	public static final String ALERTS_DIM_USERNAME="userName";
	public static final String ALERTS_DIM_RULENAME="ruleName";
	public static final String ALERTS_DIM_DIMLEVEL="actionName";
	public static final String ALERTS_DIM_ALERTID="alert_id";
	public static final String ALERTS_DIM_SCHEMANAME="schemaName";
	public static final String ALERTS_DIM_CUBE="cube";
	public static final String ALERTS_DIM_HIERARCHY="hierarchy";
	public static final String ALERTS_DIM_APP="app";
	
	public static final String ALERTS_MEASUREMENT_ALERTTEXT="AlertText";
	public static final String ALERTS_MEASUREMENT_ALERTLEVEL="AlertLevel";
	public static final String ALERTS_MEASUREMENT_ALERTTYPE="AlertType";
	public static final String ALERTS_MEASUREMENT_ALERTDETAILS="AlertDetails";
	public static final String ALERTS_MEASUREMENT_CURRENTCOUNT="CurrentCount";
	public static final String ALERTS_MEASUREMENT_TOTALCOUNT="TotalCount";
	public static final String ALERTS_MEASUREMENT_METRICNODE="MetricNode";
	public static final String ALERTS_MEASUREMENT_SETNODE="SetNode";
	public static final String ALERTS_MEASUREMENT_CLEARCONDITION="ClearCondition";
	public static final String ALERTS_MEASUREMENT_SETCONDITION="SetCondition";
	public static final String ALERTS_MEASUREMENT_CLEARNODE="ClearNode";
	public static final String ALERTS_MEASUREMENT_ISSETACTION="IsSetAction";
	public static final String ALERTS_MEASUREMENT_ISALERTCLEARED="IsAlertCleared";
	public static final String ALERTS_MEASUREMENT_ISADMIN="IsAdmin";
	public static final String ATTR_UPDATED_TIME = "updated_time";
	public static final String ATTR_CREATED_TIME = "created_time";
	
	public static final String ASSET_STATUS="asset_status";
	public static final String ASSET_NAME="asset_name";
	
	
	//BE TEA AGENT ACCUMULATOR PROPS
	public static final String BE_TEA_AGENT_NAME="agentName";
	public static final String BE_TEA_AGENT_STATUS="status";
	public static final String BE_TEA_AGENT_RULE_NAME="statRuleName";
	public static final String BE_TEA_AGENT_RULE_ENTITY="ruleEntity";
	public static final String BE_TEA_AGENT_RULE_APP="ruleApp";
	
	public static final ArrayList<String> ALERTS_QUERY_METRICS=new ArrayList<String>(){{
		add(ALERTS_MEASUREMENT_ALERTTEXT);
		add(ALERTS_MEASUREMENT_ALERTLEVEL);
		add(ALERTS_MEASUREMENT_ALERTTYPE);
		add(ALERTS_MEASUREMENT_ALERTDETAILS);
		add(ALERTS_MEASUREMENT_CURRENTCOUNT);
		add(ALERTS_MEASUREMENT_TOTALCOUNT);
		add(ALERTS_MEASUREMENT_METRICNODE);
		add(ALERTS_MEASUREMENT_SETNODE);
		add(ALERTS_MEASUREMENT_CLEARCONDITION);
		add(ALERTS_MEASUREMENT_CLEARNODE);
		add(ALERTS_MEASUREMENT_ISALERTCLEARED);
		add(ALERTS_MEASUREMENT_ISSETACTION);
		add(ALERTS_DIM_APP);
		add(ALERTS_DIM_ACTIONNAME);
		add(ALERTS_DIM_USERNAME);
		add(ALERTS_DIM_RULENAME);
		add(ALERTS_DIM_DIMLEVEL);
		add(ALERTS_DIM_ALERTID);
		add(ALERTS_DIM_SCHEMANAME);
		add(ALERTS_DIM_CUBE);
		add(ALERTS_DIM_HIERARCHY);
		add(ALERTS_MEASUREMENT_SETCONDITION);
		add(ALERTS_MEASUREMENT_ISADMIN);
	}};
	
	//Charting
	public static final String VIEW_ATTR_CUSTOM_TIME="time";
	
	public static final String ACTION_HEALTH_VALUE = "Health";
	public static final String ALERT_TEXT="Alert Text";
	
	
	//SELF MONITORING ATTRIBUTES
	public static final String DIM_SELF_AGENT="agent";
	public static final String DIM_SELF_RULE_ENTITY="ruleEntity";
	public static final String DIM_SELF_RULE_APP="ruleApp";
	public static final String DIM_SELF_RULE_NAME="rule";
	public static final String METRIC_SELF_AVGCPUUSAGE="avgmemusage";
	public static final String METRIC_SELF_AVGMEMUSAGE="avgcpuusage";
	public static final String METRIC_SELF_AVGRULETIME="avgTime";
	public static final String METRIC_SELF_TOTALRULETIME="totalTime";
	public static final String METRIC_SELF_RULEEVALCOUNT="evalCount";
	
		
}
