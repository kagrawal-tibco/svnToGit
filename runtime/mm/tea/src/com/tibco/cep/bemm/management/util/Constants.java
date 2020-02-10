package com.tibco.cep.bemm.management.util;

/**
 * This class has the constants used in the application
 * 
 * @author dijadhav
 *
 */
public class Constants {
	public static final String BE_APPLICATION = "BEApplication";
	public static final String BE_SITE_HOST_RESOURCE = "HostResource";
	public static final String BE_SERVICE_INSTANCE = "ServiceInstance";
	public static final String BE_PROCESSING_UNIT = "ProcessingUnit";
	public static final String BE_APPLICATION_HOST = "ApplicationHost";
	public static final String BE_AGENT = "Agent";
	public static final String BE_GLOBAL_VARIABLES_PROPERTY_FILE = "GlobalVariables";
	public static final String BE_SYSTEM_VARIABLES_PROPERTY_FILE = "SystemVariables";
	public static final String BIN = "bin";
	public static final String FORWARD_SLASH = "/";
	public static final String SPACE = " ";
	public static final String TAB = "\t";
	public static final String COLON = ":";
	public static final String BLANK = "";
	public static final String METASPACE_NAME = "metaspaceName";
	public static final String LISTEN_URL = "listenUrl";
	public static final String DISCOVERY_URL = "discoveryUrl";
	public static final String REMOTE_LISTEN_URL = "remoteListenUrl";

	public static final String CLUSTER = "cluster";
	public static final String MACHINE = "machine";
	public static final String PROCESS = "process";
	public static final String CACHE_ENGINE = "cache";
	public static final String INFERENCE_ENGINE = "inference";
	public static final String QUERY_ENGINE = "query";
	public static final String ENGINE = "engine";

	public static final String JMX_PREFIX = "service:jmx:rmi:///jndi/rmi://";
	public static final String JMX_SUFFIX = "/jmxrmi";
	public static final String BE_DAO_PROVIDER_TYPE = "be.dao.provider.type";
	public static final String COHERENCE = "coherence";
	public static final String TIBCO = "tibco";
	public static final String UNIX_COMMAND = "{0} --propFile {1} -n {2} -c {3} -u {4} {5} --propVar {6} --propVar {7}";
	public static final String WINDOWS_COMMAND = "{0} --propFile {1} -n {2} -c {3} -u {4} {5} --propVar {6} --propVar {7}";
	public static final String START_PU_COMMAND = "chmod -R u+rwx {0} ; cd {1} ; ./{2}.sh";
	public static final String WINDOWS_START_PU_COMMAND = "chmod -R u+rwx {0} ; cd {1} ; ./{2}.bat";
	public static final String ENGINE_EXEC_NAME = "be-engine";
	public static final String APPLICATION_NAME = "APPLICATION_NAME";
	public static final String BE_JVM_PROPERTY_FILE = "JVMProperties";
	public static final String BE_PROCESSING_UNIT_AGENT = "ProcessingUnitAgent";
	public static final String DEPLOYMENT_STATUS = "deploymentStatus";
	public static final String DEPLOYMENT = "deployment";
	public static final String HOT_DEPLOYABLE = "HotDeployable";
	public static final String DESCRIPTION = "description";
	public static final String CONNECT_TO_CLUSTER = "connectToCluster";
	public static final String TEA_AGENT_APP_CLUSTER_LISTEN_URL = "teaAgentClusterListenURL";
	public static final String CREATION_TIME = "creationTime";
	public static final String LAST_MODIFICATION_TIME = "lastModoficationTime";
	public static final String DOT = ".";
	public static final String ATR = "@";
	public static final String LAST_DEPLOYEMNT_TIME = "LastDeploymentTime";
	public static final String LOCALHOST = "localhost";
	public static final String INSTANCE_START_TIME = "InstanceStartTime";
	
	public static final String TRA_PROP_VAR_JMX_PORT = "jmx_port";
	public static final String TRA_PROP_VAR_JMX_HOST = "jmx_host";
	public static final String TRA_PROP_JAVA_HEAP_SIZE_INITIAL = "java.heap.size.initial";
	public static final String TRA_PROP_JAVA_HEAP_SIZE_MAX = "java.heap.size.max";
	
	public static final String IN_MEM_STANDALONE_CLUSTER_NAME = "standalone-cluster";
	
	public static final String BE_MASTER_HOST = "MasterHost";
	public static final String PATTERN = "pattern";
	public static final String LOGLEVEL = "level";
	public static final String INDEX = "index";
	public static final String GLOBAL_VARIABL_PREFIX = "tibco.clientVar.";
	public static final String SYSTEM_PROPERTY_PREFIX = "java.property.";
	public static final String TIBCO_ENV_VARIABLE_PREFIX = "tibco.env.";
	public static final String BE_TEA_AGENT_TEMP_DIR = "downloads";
	public static final String DEFAULT_PROFILE = "defaultProfile";
	public static final String IS_MONITORABLE_ONLY = "isMonitorableOnly";
	
}
