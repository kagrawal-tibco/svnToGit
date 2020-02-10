package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

/*
@author ssailapp
@date Feb 2, 2011
 */

public class ClusterConfigImages {

	private static String CLUSTER_PATH = "icons/cluster/";
	
	public static String IMG_CLUSTER_ACTION_CONFIG = "actionconfig.gif";
	public static String IMG_CLUSTER_AGENT = "agents_16x16.png";
	public static String IMG_CLUSTER_ALERT_CONFIG = "alertconfig.gif";
	public static String IMG_CLUSTER_BACKING_STORE = "backingstore_16x16.gif";
	public static String IMG_CLUSTER_CACHE_AGENT = "cacheagent_16x16.gif";
	public static String IMG_CLUSTER_CLUSTER = "cluster_16x16.png";
	public static String IMG_CLUSTER_CLUSTER_MEMBER = "clustermember.gif";
	public static String IMG_CLUSTER_CONCEPT = "concept.png";
	public static String IMG_CLUSTER_CONNECTION = "connection_16x16.gif";
	public static String IMG_CLUSTER_DASHBOARD_AGENT = "dashboardagent_16x16.png";
	public static String IMG_CLUSTER_DBCONCEPTS = "DB.gif";
	public static String IMG_CLUSTER_DBCONCEPTS_DISABLED = "DB-disabled.gif";
	public static String IMG_CLUSTER_DESTINATION = "destination_16x16.png";
	public static String IMG_CLUSTER_DOMAIN_OBJECT = "domainobj_16x16.gif";
	public static String IMG_CLUSTER_DOMAIN_OBJECT_OVERRIDES = "domainobjoverrides_16x16.png";
	public static String IMG_CLUSTER_DOMAIN_OBJECTS = "domainobjs_16x16.gif";
	public static String IMG_CLUSTER_FUNCTION = "rule_function.png";
	public static String IMG_CLUSTER_GENERAL = "general.gif";
	public static String IMG_CLUSTER_GROUP = "group.gif";
	public static String IMG_CLUSTER_GROUP_REFERENCE = "group_reference.gif";		
	public static String IMG_CLUSTER_INF_AGENT = "inferenceagent_16x16.png";
	public static String IMG_CLUSTER_LOG_CONFIG = "logconfig_16x16.gif";
	public static String IMG_CLUSTER_METRIC = "metric_16x16.gif";
	public static String IMG_CLUSTER_MM_AGENT = "monitormanagement_16x16.gif";
	public static String IMG_CLUSTER_OBJECT_MGMT = "objectmgmt_16x16.gif";
	public static String IMG_CLUSTER_PROCESS = "process_16x16.gif";
	public static String IMG_CLUSTER_PROCUNIT = "processingunits_16x16.png";
	public static String IMG_CLUSTER_PROJECT = "studio_project.gif";
	public static String IMG_CLUSTER_PROPERTY = "property_16x16.png";
	public static String IMG_CLUSTER_PROPERTY_GROUP = "propertygroup_16x16.png";
	public static String IMG_CLUSTER_QUERY_AGENT = "queryagent_16x16.png";
	public static String IMG_CLUSTER_REFERENCE = "reference.gif";
	public static String IMG_CLUSTER_RULES = "rules.png";
	public static String IMG_CLUSTER_RULE_TEMPLATE = "rules_template.png";
	public static String IMG_CLUSTER_SCORECARD = "scorecard.png";
	public static String IMG_CLUSTER_SET_PROPERTY = "clustermember.gif";
	public static String IMG_CLUSTER_SIMPLEEVENT = "event.png";

	public static Image getImage(String imageId) {
		return (EditorsUIPlugin.getDefault().getImage(CLUSTER_PATH + imageId));
	}
	
	public static ImageDescriptor getImageDescriptor(String imageId) {
		return (EditorsUIPlugin.getImageDescriptor(CLUSTER_PATH + imageId));
	}
	
}
