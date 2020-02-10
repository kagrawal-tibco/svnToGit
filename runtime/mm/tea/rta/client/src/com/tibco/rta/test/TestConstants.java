package com.tibco.rta.test;

public class TestConstants {
	
	// Schema
    public final static String SCHEMA_NAME = "BE_MM";

    // Cube(s)
    public final static String CUBE_NAME = "BEMMCube";

    // Hierarchies
    public final static String DIM_HIERARCHY_INSTANCE_HEALTH = "instancehealthhierarchy";
    public final static String DIM_HIERARCHY_CLUSTER_HEALTH = "clusterhealthhierarchy";

    //Attributes
    public final static String ATTRIB_APPLICATION 					= "appName";
    public final static String ATTRIB_PU_NAME 						= "puName";
    public final static String ATTRIB_INSTANCE_NAME 				= "instanceName";
    public final static String ATTRIB_AGENT_TYPE 					= "agentType";
    public final static String ATTRIB_AGENT_NAME 					= "agentName";
    public final static String ATTRIB_SPACE			 				= "space";
    
    public final static String ATTRIB_HOST 							= "host";
    public final static String ATTRIB_INSTANCE_COUNT 				= "instancecount";
    public final static String ATTRIB_AGENT_COUNT 					= "agentcount";
    public final static String ATTRIB_ENGINE_COUNT 					= "enginecount";
    public final static String ATTRIB_INSTANCE_ACTIVE 				= "instanceisactive";
    public final static String ATTRIB_AGENT_ACTIVE 					= "agentisactive";
    public final static String ATTRIB_HOST_IS_PREDEFINED			= "hostispredefined";
    public final static String ATTRIB_INSTANCE_IS_PREDEFINED 		= "instanceispredefined";
    public final static String ATTRIB_TOTAL_INFERENCE_AGENT 		= "totalinferenceagents";
    public final static String ATTRIB_TIMESTAMP 					= "timestamp";    
    public final static String ATTRIB_JVM_UP_TIME 					= "jvmuptime";
    public final static String ATTRIB_CPU_COUNT 					= "cpucount";
    public final static String ATTRIB_CPU_TIME 						= "cputime";
    public final static String ATTRIB_CPU_USEAGE 					= "cpuusage";
    public final static String ATTRIB_INIT_MEM_SIZE 				= "initmemsize";
    public final static String ATTRIB_USED_MEM_SIZE 				= "usedmemsize";
    public final static String ATTRIB_COMMITTED_MEM_SIZE 			= "committedmemsize";
    public final static String ATTRIB_MAX_MEM_SIZE 					= "maxmemsize";    
    public final static String ATTRIB_THREAD_COUNT 					= "threadcount";
    public final static String ATTRIB_DEADLOCKED_THREAD_COUNT 		= "deadlockedthreadcount";
    public final static String ATTRIB_TOTAL_SUCCESS_TRANSACTION 	= "totalsuccessfultxn";
    public final static String ATTRIB_TOTAL_SUCCESS_DB_TRANSACTION 	= "totalsuccessfuldbtxn";
    public final static String ATTRIB_PUT_COUNT 					= "totalputcount";
    public final static String ATTRIB_NO_OF_RULES_FIRED 			= "noofrulesfired";
    public final static String ATTRIB_CHANNEL_URI 					= "channeluri";
    public final static String ATTRIB_NO_EVENTS_RECEIVED 			= "noeventsreceived";
    public final static String ATTRIB_NO_EVENTS_SENT 				= "noeventssent";
    public final static String ATTRIB_DEST_URI 						= "desturi";
    
    
    public final static String ATTRIB_APP_HEALTH			 		= "apphealth";
    public final static String ATTRIB_INSTANCE_HEALTH			 	= "instancehealth";
    public final static String ATTRIB_AGENT_HEALTH			 		= "agenthealth";
    public final static String ATTRIB_OK_COUNT			 			= "okcount";
    public final static String ATTRIB_WARNING_COUNT			 		= "warningcount";
    public final static String ATTRIB_CRITICAL_COUNT			 	= "criticalcount"; 
    
    
	
	//dimensions
	
	public final static String DIM_APP 								= "app";
	public final static String DIM_INSTANCE 						= "instance";
	public final static String DIM_PU 								= "pu";
	public final static String DIM_AGENT_TYPE						= "agentType";
	public final static String DIM_AGENT 							= "agent";
	public final static String DIM_SPACE 							= "space";
    
    
    
    
    // Computed Measurement
    public final static String MEASUREMENT_INSTANCE_COUNT 					=	"instancecount";
    public final static String MEASUREMENT_AGENT_COUNT						=	"agentcount";
    public final static String MEASUREMENT_TOTAL_NO_OF_RULES_FIRED 			=	"totalnoofrulesfired";
    public final static String MEASUREMENT_TOTAL_SUCCESS_DB_TRANSACTION		=	"totalsuccessfuldbtxn";
    public final static String MEASUREMENT_TOTAL_SUCCESS_TRANSACTION 		=	"totalsuccessfultxn";
    public final static String MEASUREMENT_CPU_USEAGE 						=	"cpuusage";
    public final static String MEASUREMENT_CPU_COUNT 						=	"cpucount";
    public final static String MEASUREMENT_CPU_TIME 						=	"cputime";
    public final static String MEASUREMENT_AVG_CPU_USEAGE 					=	"avgcpuusage";
    public final static String MEASUREMENT_USED_MEM 						=	"usedmem";
    public final static String MEASUREMENT_MAX_MEM 							=	"maxmem";
    public final static String MEASUREMENT_COMMITTED_MEM 					=	"committedmem";
    public final static String MEASUREMENT_INIT_MEM 						=	"initmem";
    public final static String MEASUREMENT_AVG_MEM_USEAGE 					=	"avgmemusage";
    public final static String MEASUREMENT_JVM_UP_TIME 						=	"jvmuptime";    
    public final static String MEASUREMENT_THREAD_COUNT  					=	"threadcount";
    public final static String MEASUREMENT_TOTAL_THREAD_COUNT  				=	"totalthreadcount";
    public final static String MEASUREMENT_DEADLOCKED_THREAD_COUNT 			=	"deadlockedthreadcount";
    public final static String MEASUREMENT_TIME 							=	"time";
    public final static String MEASUREMENT_AGENT_HEALTH 					=	"agenthealth";
    public final static String MEASUREMENT_INSTANCE_HEALTH 					=	"instancehealth";
    public final static String MEASUREMENT_INSTANCE_ACTIVE 					=	"instanceisactive";
    public final static String MEASUREMENT_AGENT_ACTIVE						=	"agentisactive";
    public final static String MEASUREMENT_APP_HEALTH 						=	"apphealth";
    public final static String MEASUREMENT_INSTANCE_PERCENT_OK_HEALTH 		=	"instancepercentokhealth";
    public final static String MEASUREMENT_INSTANCE_PERCENT_WARN_HEALTH 	=	"instancepercentwarnhealth";
    public final static String MEASUREMENT_INSTANCE_PERCENT_CRIT_HEALTH 	=	"instancepercentcrithealth";
    public final static String MEASUREMENT_AGENT_PERCENT_OK_HEALTH 			=	"agentpercentokhealth";
    public final static String MEASUREMENT_AGENT_PERCENT_WARN_HEALTH 		=	"agentpercentwarnhealth";
    public final static String MEASUREMENT_AGENT_PERCENT_CRIT_HEALTH 		=	"agentpercentcrithealth";
    public final static String MEASUREMENT_INF_AGENT_PERCENT_OK_HEALTH 		=	"infagentpercentokhealth";
    public final static String MEASUREMENT_INF_AGENT_PERCENT_WARN_HEALTH	=	"infagentpercentwarnhealth";
    public final static String MEASUREMENT_INF_AGENT_PERCENT_CRIT_HEALTH	=	"infagentpercentcrithealth";
    public final static String MEASUREMENT_INSTANCE_PERCENT_ACTIVE 			=	"instancepercentactive";
    public final static String MEASUREMENT_CHEAT_CLUSTER 					=	"cheatcluster";
    
    
    public final static String AGENT_TYPE_VALUE_INFERENCE						=		"inference";
    public final static String AGENT_TYPE_VALUE_CACHE							=		"cache";
   
    
    
    
    
    
   /* public final static String ATTRIB_ENVIRONMENT = "environment";
    
    public final static String ATTRIB_SERVICE = "contract_name";
    public final static String ATTRIB_SERVICETYPE = "contract_type";
    public final static String ATTRIB_OPERATION = "operation_name";
    public final static String ATTRIB_NODE = "node";
   
    public final static String ATTRIB_HIT = "hit";
    public final static String ATTRIB_SUCCESS = "success";
    public final static String ATTRIB_FAULT = "fault";
    public final static String ATTRIB_ASSET_STATUS = "asset_status";

    // DEMO Dimensions
    public final static String DIM_LEVEL_ENVIRONMENT = "environment";
    public final static String DIM_LEVEL_APPLICATION = "application_name";
    public final static String DIM_LEVEL_SERVICE = "service_name";
    public final static String DIM_LEVEL_SERVICETYPE = "service_type";
    public final static String DIM_LEVEL_OPERATION = "operation_name";
    public final static String DIM_LEVEL_HOST = "host";
    public final static String DIM_LEVEL_NODE = "node";

    // Time Dimensions
    public final static String DIM_LEVEL_WEEK = "weeks";
    public final static String DIM_LEVEL_DAY = "days";
    public final static String DIM_LEVEL_HOUR = "hours";
    public final static String DIM_LEVEL_MINUTE = "minutes";
*/
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
