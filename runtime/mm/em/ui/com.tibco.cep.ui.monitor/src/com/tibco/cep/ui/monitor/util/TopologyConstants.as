package com.tibco.cep.ui.monitor.util
{
	public class TopologyConstants
	{
		//****************************** General Attribute names *******************************
		public static const NAME_ATTR:String = "name";
		
		//************************ topology tree XML element (node) names ************************
		public static const SITES_NODE:String = "sites";
		public static const SITE_NODE:String = "site";
		public static const CLUSTER_NODE:String = "cluster";
		public static const MACHINE_NODE:String = "machine";
		public static const PROCESS_NODE:String = "process";
		public static const AGENT_NODE:String = "agent";
		public static const METHODS_NODE:String = "methods";
		public static const METHOD_GROUP_NODE:String = "methodgroup";
		public static const METHOD_NODE:String = "method";
		public static const CACHED_OBJECTS_NODE:String = "mcacheobjects";
		
		//************************ topology tree Agent types string ************************
		public static const AGENT_TYPE_INFERENCE:String = "inference";
		public static const AGENT_TYPE_CACHESERVER:String = "cache";
		public static const AGENT_TYPE_QUERY:String = "query";
		public static const AGENT_TYPE_DASHBOARD:String = "dashboard";
		
		//************************ Topology.xml file XML element names *************************
		//IN THE TOPOLOGY.XML FILE THE NAME OF EVERY ELEMENT AND EVERY ATTRIBUTE MUST HAVE "-" REPLACED BY "_".
		//Adobe Flex's XML parser doesn't work with the names with "-", so replace "-" by "_"
		public static const TOP_FILE_SITE_NODE:String = "site";
		public static const TOP_FILE_CLUSTERS_NODE:String = "clusters";
		public static const TOP_FILE_CLUSTER_NODE:String = "cluster";
		public static const TOP_FILE_CLUSTER_NODE_NAME_ATTR:String = "name";
		
		//Topology.xml: deployment_mappings node and its children: Attributes and XML elements
		public static const TOP_FILE_DEPLOY_MAPPINGS_NODE:String = "deployment_mappings";
		public static const TOP_FILE_DEPLOY_MAPPING_NODE:String = "deployment_mapping";
		public static const TOP_FILE_DEPLOY_MAPPING_NODE_HOST_REF_ATTR:String = "host_ref";
		public static const TOP_FILE_DEPLOY_MAPPING_NODE_DEPLOY_UNIT_REF_ATTR:String = "deployment_unit_ref";
		
		//Topology.xml: deployment_units node and its children: Attributes and XML elements
		public static const TOP_FILE_DEPLOY_UNITS_NODE:String = "deployment_units";
		public static const TOP_FILE_DEPLOY_UNIT_NODE:String = "deployment_unit";
		public static const TOP_FILE_DEPLOY_UNIT_NODE_ID_ATTR:String = "id";
		public static const TOP_FILE_DEPLOY_UNIT_NODE_NAME_ATTR:String = "name";
		public static const TOP_FILE_DEPLOY_FILES_NODE:String = "deployed_files";
		public static const TOP_FILE_CDD_DEPLOY_NODE:String = "cdd_deployed";
		public static const TOP_FILE_EAR_DEPLOY_NODE:String = "ear_deployed";


		//Topology.xml: host_resources node and its children: Attributes and XML elements
		public static const TOP_FILE_HOST_RESOURCES_NODE:String = "host_resources";
		public static const TOP_FILE_HOST_RESOURCE_NODE:String = "host_resource";
		public static const TOP_FILE_HOST_RESOURCE_NODE_ID_ATTR:String = "id";
		public static const TOP_FILE_HOST_NAME_NODE:String = "hostname";
		public static const TOP_FILE_IP_NODE:String = "ip";
		public static const TOP_FILE_USER_CREDENTIALS_NODE:String = "user_credentials";
		public static const TOP_FILE_USER_CREDENTIALS_NODE_PASSWD_ATTR:String = "password";
		public static const TOP_FILE_USER_CREDENTIALS_NODE_USERNAME_ATTR:String = "username";
		
//		public static const TOP_FILE_ _NODE:String = "";
//		public static const TOP_FILE_ _NODE:String = "";
//		public static const TOP_FILE_ _NODE:String = "";
//		public static const TOP_FILE_ _NODE:String = "";
//		public static const TOP_FILE_ _NODE:String = "";

	}
}