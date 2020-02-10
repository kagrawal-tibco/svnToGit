package com.tibco.cep.studio.cluster.topology.utils;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyConstants {

	public static final String NODE_TYPE = "node.type";
	public static final int UNKNOWN_NODE = -1;
	public static final int SITE_NODE = 0;
	public static final int CLUSTER_NODE = 1;
	public static final int HOST_NODE = 2;
	public static final int DEPLOYMENT_UNIT_NODE = 3;
	public static final int PU_NODE = 4;
	public static final int AGENT_NODE = 5;
	public static final int NOTE_NODE = 6;	

	public static final String SITE_NAME = "site.name";
	public static final String MODEL = "model.object";
//
	public static final String CLUSTER_NAME = "cluster.name";
	public static final String CLUSTER_BE_VERSION = "cluster.bever";
	public static final String CLUSTER_CDD = "cluster.cdd";
	public static final String CLUSTER_EAR = "cluster.ear";
//	
	public static final String DU_INFO_REF = "mu.idref";
	public static final String DU_PUIDS = "mu.puids";
	
	public static final String PU_ID = "pu.id";
	public static final String PU_PUID = "pu.puid";
//
	public static final String DEPLOYED_CDDFILE = "deployed.cddfile";	
	public static final String DEPLOYED_EARFILE = "deployed.earfile";	
//
	public static final String AGENT_TYPE_INF = "Inference";	
	public static final String AGENT_TYPE_QUERY = "Query";	
	public static final String AGENT_TYPE_CACHE = "Cache";	
	public static final String AGENT_TYPE_DASHBOARD = "Dashboard";
//	
	public static final String HOST_ID = "host.id";
	public static final String HOST_NAME = "host.name";
	public static final String HOST_IP = "host.ip";
	public static final String HOST_USERNAME = "host.username";
	public static final String HOST_PASSWORD = "host.password";
	public static final String HOST_OS = "host.os";
//
//	
//	
	public static final String INSTALLATION_BEVERSION = "installation.beversion";
	public static final String INSTALLATION_TRAFILE = "installation.trafile";
	public static final String INSTALLATION_BEHOME = "installation.behome";
//	
	public static final String HAWK_USETOSTART = "hawk.usetostart";	
	public static final String HAWK_DOMAIN = "hawk.domain";	
	public static final String HAWK_SERVICEPORT = "hawk.serviceport";	
	public static final String HAWK_NETWORK = "hawk.network";	
	public static final String HAWK_DAEMON = "hawk.daemon";		

}