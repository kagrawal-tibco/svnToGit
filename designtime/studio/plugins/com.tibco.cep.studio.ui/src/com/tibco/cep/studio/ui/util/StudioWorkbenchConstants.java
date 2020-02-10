package com.tibco.cep.studio.ui.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 *@author sasahoo
 */

public class StudioWorkbenchConstants {

	public static final String _NAME_FOLDER_CONCEPTS = "Concepts";
	public static final String _NAME_FOLDER_PROCESSES = "Processes";
	public static final String _NAME_FOLDER_EVENTS = "Events";
	public static final String _NAME_FOLDER_CHANNELS = "Channels";
	public static final String _NAME_FOLDER_RULES = "Rules";
	public static final String _NAME_FOLDER_RULE_FUNCTIONS = "RuleFunctions";
	public static final String _NAME_FOLDER_DASHBOARDS = "Dashboards";
	public static final String _NAME_FOLDER_DEPLOYMENT = "Deployments";
	public static final String _NAME_FOLDER_SHARED_RESOURCE = "SharedResources";
	public static final String _NAME_FOLDER_DEFAULT_VARS = "defaultVars";
	public static final String _NAME_FOLDER_TEST_DATA = "TestData";
	public static final String _NAME_FOLDER_JAVA_SRC = "javaSrc";
	public static final String _NAME_FOLDER_JAVA_BIN = "bin";
	
	public static IProject _newProject;

	public static final String _WIZARD_TYPE_NAME_RULE_TEMPLATE_VIEW = "Rule Template View";
	public static final String _WIZARD_TYPE_NAME_RULE_TEMPLATE = "Rule Template";
	public static final String _WIZARD_TYPE_NAME_RULE = "Rule";
	public static final String _WIZARD_TYPE_NAME_RULE_FUNCTION = "Rule Function";
	public static final String _WIZARD_TYPE_NAME_CONCEPT = "Concept";
	public static final String _WIZARD_TYPE_NAME_STATEMACHINE = "State Model";
	public static final String _WIZARD_TYPE_NAME_SCORECARD = "Scorecard";
	public static final String _WIZARD_TYPE_NAME_CHANNEL = "Channel";
	public static final String _WIZARD_TYPE_NAME_EVENT = "Simple Event";
	public static final String _WIZARD_TYPE_NAME_TIME_EVENT = "Time Event";

	public static final String _WIZARD_TYPE_NAME_DT = "Decision Table";
	public static final String _WIZARD_TYPE_NAME_PROCESS= "Process";
	public static final String _WIZARD_TYPE_NAME_GRAPH= "Graph";
	public static final String _WIZARD_TYPE_NAME_BPMN= "Bpmn Process";
	public static final String _WIZARD_TYPE_NAME_QUERY= "Query";
	public static final String _WIZARD_TYPE_NAME_JAVA = "Java Source";

	public static final String _WIZARD_TYPE_NAME_DOMAIN = "Domain Model";
	
	public static final String _DT_EXTENSION = ".rulefunctionimpl";
	
	public static final String _WIZARD_TYPE_NAME_CLUSTER_TOPOLOGY= "Cluster Topology";
	
	public static final String CONCEPT_VIEW_EXTENSION  	= ".conceptview";
	public static final String EVENT_VIEW_EXTENSION  	= ".eventview";
	public static final String PROJECT_VIEW_EXTENSION  	= ".projectview";
	
	public static Map<String,String> extensionMap = new HashMap<String,String> ();
	static {
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE_TEMPLATE_VIEW, ".ruletemplateview");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE_TEMPLATE, ".ruletemplate");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE, ".rule");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_CONCEPT, ".concept");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_SCORECARD, ".scorecard");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_EVENT, ".event");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_DT, ".rulefunctionimpl");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_STATEMACHINE, ".statemachine");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_RULE_FUNCTION, ".rulefunction");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_PROCESS, ".process");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_GRAPH, ".graph");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_BPMN, ".beprocess");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_DOMAIN, ".domain");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_TIME_EVENT, "."+CommonIndexUtils.TIME_EXTENSION);
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_CHANNEL, ".channel");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_CLUSTER_TOPOLOGY, ".st");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_QUERY, ".bequery");
		extensionMap.put(StudioWorkbenchConstants._WIZARD_TYPE_NAME_JAVA, ".java");
	}
}
