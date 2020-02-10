package com.tibco.cep.bpmn.core;

/**
 * @author pdhar
 *
 */
public interface BpmnCoreConstants {
	
	public static final String EXTENSION_POINT_INDEX_UPDATE = "com.tibco.cep.bpmn.core.bpmnIndexRefreshSync";//$NON-NLS-1$
	public static final String EXTENSION_POINT_ATTR_INDEX = "index"; //$NON-NLS-1$

	public static final String NODE_ATTR_NAME = "node.name"; //$NON-NLS-1$
	public static final String NODE_ATTR_TYPE = "node.type"; //$NON-NLS-1$
	public static final String NODE_ATTR_EXT_TYPE = "node.type.extended"; //$NON-NLS-1$
	public static final String NODE_ATTR_BE_RESOURCE_URL= "node.be.resource.url"; //$NON-NLS-1$
	public static final String NODE_ATTR_TOOL_ID = "node.tool.id"; //$NON-NLS-1$
	public static final String NODE_ATTR_COPY_ID = "node.copy.id"; //$NON-NLS-1$
	
	
	public static final String NODE_ATTR_TASK_ACTION = "task.action"; //$NON-NLS-1$
	public static final String NODE_ATTR_TASK_DESCRIPTION = "task.description"; //$NON-NLS-1$
	public static final String NODE_ATTR_TASK_MODE = "task.mode"; //$NON-NLS-1$
	public static final String NODE_ATTR_TASK_MODE_MULTIPLE = "Multi-Instance"; //$NON-NLS-1$
	public static final String NODE_ATTR_TASK_MODE_LOOP = "Standard"; //$NON-NLS-1$
	public static final String NODE_ATTR_TASK_MODE_COMPENSATION = "Compensation"; //$NON-NLS-1$
	public static final String NODE_ATTR_TASK_MODE_NONE = "None"; //$NON-NLS-1$
	public static final String NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_ALL = "ALL"; //$NON-NLS-1$
	public static final String NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_ONE = "ONE"; //$NON-NLS-1$
	public static final String NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_NONE = "NONE"; //$NON-NLS-1$
	public static final String NODE_ATTR_MULTI_INSTANCE_BEHAVIOUR_COMPLEX = "COMPLEX"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_FOLDER =BpmnCorePlugin.PLUGIN_ID + "."+ "codegen"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_PROCESS_PREFIX = BpmnCorePlugin.PLUGIN_ID+"codegen.process.prefix"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_RULE_PREFIX = BpmnCorePlugin.PLUGIN_ID+"codegen.rule.prefix"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_RULE_FUNCTION_PREFIX = BpmnCorePlugin.PLUGIN_ID+"codegen.rulefunction.prefix"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_CONCEPT_PREFIX = BpmnCorePlugin.PLUGIN_ID+"codegen.concept.prefix"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_EVENT_PREFIX = BpmnCorePlugin.PLUGIN_ID+"codegen.event.prefix"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_TIME_EVENT_PREFIX = BpmnCorePlugin.PLUGIN_ID+"codegen.timeevent.prefix"; //$NON-NLS-1$
	public static final String PREF_CODEGEN_SCORECARD_PREFIX = BpmnCorePlugin.PLUGIN_ID+"codegen.scorecard.prefix"; //$NON-NLS-1$

	public static final String PALETTE_FILE_EXTN = "beprocesspalette";
	public static final String DEFAULT_BEBPMNPALETTE = "default.beprocesspalette";
	
	
	public static final String BE_CODEGEN_ONTOLOGY = "ontology";//$NON-NLS-1$
	public static final String BPMN_CONFIG = "bpmn.config";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_ONTOLOGY = "ontology.bpmn";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_FOLDER = "codegen"; //$NON-NLS-1$
    public static final String BPMN_CODEGEN_EVENT_FOLDER = "events";//$NON-NLS-1$
    public static final String BPMN_CODEGEN_CONCEPT_FOLDER = "concepts";//$NON-NLS-1$
    public static final String BPMN_CODEGEN_SCORECARD_FOLDER = "scorecards";//$NON-NLS-1$
    public static final String BPMN_CODEGEN_RULE_FOLDER = "rules";//$NON-NLS-1$
    public static final String BPMN_CODEGEN_CONSTANTS_FOLDER = "constants";//$NON-NLS-1$
    public static final String BPMN_CODEGEN_RULEFUNCTION_FOLDER = "ruleFunctions";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_TIME_EVENT_FOLDER = "timeEvents";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_SELECTED_PROCESSES = "selectedProcesses";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_PROJECT = "project";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_ERROR_LIST = "errorList";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_PROJECT_SYMBOL_MAP = "genSymbolMap";//$NON-NLS-1$
	public static final String BPMN_CODEGEN_GEN_URI_MAP = "genURIMap";//$NON-NLS-1$
	
	public static final String BPMN_EXPRESSION_LANGUAGE_XPATH= "xpath";//$NON-NLS-1$
	public static final String BPMN_EXPRESSION_LANGUAGE_RULE= "Rule";//$NON-NLS-1$
	public static final String BPMN_EXPRESSION_LANGUAGE_JAVASCRIPT= "javascript";//$NON-NLS-1$

	public static final String PREF_LABEL_CODEGEN_FOLDER = "bpmn.core.codegen.folder"; //$NON-NLS-1$
	public static final String PREF_LABEL_CODEGEN_PROCESS_PREFIX = "bpmn.core.codegen.process.prefix"; //$NON-NLS-1$
	public static final String PREF_LABEL_CODEGEN_RULE_PREFIX = "bpmn.core.codegen.rule.prefix"; //$NON-NLS-1$
	public static final String PREF_LABEL_CODEGEN_RULE_FUNCTION_PREFIX = "bpmn.core.codegen.rulefunction.prefix"; //$NON-NLS-1$
	public static final String PREF_LABEL_CODEGEN_CONCEPT_PREFIX = "bpmn.core.codegen.concept.prefix"; //$NON-NLS-1$
	public static final String PREF_LABEL_CODEGEN_EVENT_PREFIX = "bpmn.core.codegen.event.prefix"; //$NON-NLS-1$
	public static final String PREF_LABEL_CODEGEN_TIME_EVENT_PREFIX = "bpmn.core.codegen.timeevent.prefix"; //$NON-NLS-1$
	public static final String PREF_LABEL_CODEGEN_SCORECARD_PREFIX = "bpmn.core.codegen.scorecard.prefix"; //$NON-NLS-1$
	public static final String BPMN_NAME_PREFIX = "NamePrefix";//$NON-NLS-1$
	
	public static final String SSL_HTTPS_CERT_FOLDER = "cert";
	public static final String SSL_HTTPS_IDENTITY = "identity";
	public static final String SSL_HTTPS_CIPHER_SUITE = "strongCipherSuitesOnly";
	public static final String SSL_HTTPS_VERIFY_HOST = "verifyHostName";
	
	
}
