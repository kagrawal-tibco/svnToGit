package com.tibco.cep.bpmn.ui;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;

public interface BpmnUIConstants extends BpmnCoreConstants {

	public static final String BPMN_MODEL_NEW_PROCESS = "bpmn.model.new.process"; //$NON-NLS-1$
	public static final String BPMN_MODEL_OPEN_FAIL = "bpmn.model.open.error"; //$NON-NLS-1$
	public static final String BPMN_MODEL_CREATE_INITIAL = "bpmn.model.create.initial"; //$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_INITIAL = "bpmn.model.load.initial"; //$NON-NLS-1$
	public static final String BPMN_MODEL_INIT_GRAPH = "bpm.model.init.graph"; //$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_PROCESS = "bpmn.model.load.process";//$NON-NLS-1$
	public static final String BPMN_MODEL_CREATE_PROCESS = "bpmn.model.create.process";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_POOL_LANES = "bpmn.model.load.poolLanes";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_POOL = "bpmn.model.load.pool";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_FLOW_NODES = "bpmn.model.load.flownodes";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_ARTIFACT_NODES = "bpmn.model.load.artifactnodes";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_FLOW_NODE = "bpmn.model.load.flownode";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_ARTIFACT_NODE = "bpmn.model.load.artifactnode";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_SEQUENCE_FLOWS = "bpmn.model.load.sequenceflows";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_ASSOCIATIONS = "bpmn.model.load.associationedges";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_SEQUENCE_FLOW = "bpmn.model.load.sequenceflow";//$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_ASSOCIATION = "bpmn.model.load.associationedge";//$NON-NLS-1$
	public static final String BPMN_MODEL_SAVE_ERROR = "bpmn.model.save.error"; //$NON-NLS-1$
	public static final String BPMN_MODEL_LOAD_POOL_NAME = "bpmn.model.load.pool.name";//$NON-NLS-1$
	
	public static final String NODE_TYPE_CHANGE = "bpmn.model.load.pool.name";
	
	public static final String CONNECTION_NAME = "connection.name";
	public static final String EVENT_DEFINITION_TYPE = "event.definition.type";

	
	public static final String NODE_START_EVENT = "Start Event";
	public static final String NODE_END_EVENT = "End Event";
	
	public static final String NODE_SCRIPT_TASK = "Script";
	public static final String NODE_WS_TASK = "Web Service";
	public static final String NODE_DECISION_TABLE_TASK = "Decision Table";
	public static final String NODE_SEND_EVENT_TASK = "Send Event";
	public static final String NODE_RECEIVE_EVENT_TASK = "Receive Event";
	public static final String NODE_MANUAL_TASK = "Manual";
	public static final String NODE_RULE_FUNCTION_TASK = "RuleFunction";
	public static final String NODE_RULE_TASK = "Rule";
	public static final String NODE_EVENT = "Event";
	public static final String NODE_TIMER_EVENT = "Timer Event";
	public static final String NODE_ONMESSAGE_EVENT = "On Message Event";
	public static final String NODE_SUBPROCESS_ACTIVITY = "SubProcess";	
	
	
	// Colors
	public static TSEColor LANE_FILL_COLOR = new TSEColor(255,220,81);
	public static TSEColor BADGE_GRADIENT_START_COLOR = new TSEColor(255,221,82);
	public static TSEColor BADGE_GRADIENT_END_COLOR = new TSEColor(255,250,231);
	public static TSEColor SUB_PROCESS_FILL_COLOR = new TSEColor(255,220,81);
	
	
	// Palette
	public static final String PALETTE_EVENTS = "Events";
	
	
	public static final String PALETTE_EVENTS_TOOLTIP = "Events tools";
	public static final String PALETTE_START_EVENT = "Start Event";
	public static final String PALETTE_START_EVENT_TOOLTIP = "Add a Start Event";
	public static final String PALETTE_END_EVENT = "End Event";
	public static final String PALETTE_END_EVENT_TOOLTIP = "Add an End Event";
	public static final String PALETTE_TIMER_EVENT = "Timer Event";
	public static final String PALETTE_TIMER_EVENT_TOOLTIP = "Add a Timer Event";
	public static final String PALETTE_ONMESSAGE_EVENT = "On Message Event";	
	public static final String PALETTE_ONMESSAGE_EVENT_TOOLTIP = "Add an On Message Event";	
	
	// Start Event Group
	public static final String PALETTE_START_EVENTS = "Start Events";
	public static final String PALETTE_START_EVENTS_TOOLTIP = "Start Event Tools";
	
	public static final String PALETTE_START_EVENT_NONE = "Start Event";
	public static final String PALETTE_START_EVENT_NONE_TOOLTIP = "Add a Start Event";
	public static final String PALETTE_START_EVENT_MESSAGE = "Message Start Event";
	public static final String PALETTE_START_EVENT_MESSAGE_TOOLTIP = "Add a Message Start Event";
	public static final String PALETTE_START_EVENT_TIMER = "Timer Start Event";
	public static final String PALETTE_START_EVENT_TIMER_TOOLTIP = "Add a Timer Start Event";
	
	public static final String NODE_START_EVENT_NONE = "Start";
	public static final String NODE_START_EVENT_MESSAGE = "Message Start";
	public static final String NODE_START_EVENT_TIMER = "Timer Start";
	public static final String NODE_START_PRIORITY = "Priority";
	
	// End Event Group
	
	public static final String PALETTE_END_EVENTS = "End Events";
	public static final String PALETTE_END_EVENTS_TOOLTIP = "End Event Tools";
	
	public static final String PALETTE_END_EVENT_NONE = "End Event";
	public static final String PALETTE_END_EVENT_NONE_TOOLTIP = "Add an End Event";
	public static final String PALETTE_END_EVENT_MESSAGE = "Message End Event";
	public static final String PALETTE_END_EVENT_MESSAGE_TOOLTIP = "Add an Message End Event";
	public static final String PALETTE_END_EVENT_ERROR = "Error End Event";
	public static final String PALETTE_END_EVENT_ERROR_TOOLTIP = "Add an Error End Event";
	
	public static final String NODE_END_EVENT_NONE = "End";
	public static final String NODE_END_EVENT_MESSAGE = "Message End";
	public static final String NODE_END_EVENT_ERROR = "Error End";
	
	// Intermediate Event Group
	
	public static final String PALETTE_INTERMEDIATE_EVENTS = "Intermediate Events";
	public static final String PALETTE_INTERMEDIATE_EVENTS_TOOLTIP = "Intermediate Event Tools";
	// intermediate catch
	public static final String PALETTE_CATCH_INTERMEDIATE_EVENT_MESSAGE = "Catch Message Intermediate Event";
	public static final String PALETTE_CATCH_INTERMEDIATE_EVENT_MESSAGE_TOOLTIP = "Add a Catch Message Intermediate Event";
	public static final String PALETTE_CATCH_INTERMEDIATE_EVENT_TIMER = "Catch Timer Intermediate Event";
	public static final String PALETTE_CATCH_INTERMEDIATE_EVENT_TIMER_TOOLTIP = "Add a Catch Timer Intermediate Event";
	public static final String PALETTE_CATCH_INTERMEDIATE_EVENT_ERROR = "Catch Error Intermediate Event";
	public static final String PALETTE_CATCH_INTERMEDIATE_EVENT_ERROR_TOOLTIP = "Add a Catch Error Intermediate Event";

	public static final String NODE_INTERMEDIATE_CATCH_MESSAGE = "Catch Message Intermediate";
	public static final String NODE_INTERMEDIATE_CATCH_TIMER = "Catch Timer Intermediate";
	public static final String NODE_INTERMEDIATE_CATCH_ERROR = "Catch Error Intermediate";
	// intermediate throw
	public static final String PALETTE_THROW_INTERMEDIATE_EVENT_NONE = "Throw Intermediate Event";
	public static final String PALETTE_THROW_INTERMEDIATE_EVENT_NONE_TOOLTIP = "Add a Throw Intermediate Event";
	public static final String PALETTE_THROW_INTERMEDIATE_EVENT_MESSAGE = "Throw Message Intermediate Event";
	public static final String PALETTE_THROW_INTERMEDIATE_EVENT_MESSAGE_TOOLTIP = "Add a Throw Message Intermediate Event";

	public static final String NODE_INTERMEDIATE_THROW_NONE = "Throw Intermediate";
	public static final String NODE_INTERMEDIATE_THROW_MESSAGE = "Throw Message Intermediate";
	
	
	// Gateways
	public static final String PALETTE_GATEWAYS = "Gateways";
	public static final String PALETTE_GATEWAYS_TOOLTIP = "Gateway Tools";
	
	public static final String PALETTE_INCLUSIVE_GATEWAY = "Inclusive";
	public static final String PALETTE_INCLUSIVE_GATEWAY_TOOLTIP = "Inclusive Gateway";
	public static final String PALETTE_EXCLUSIVE_GATEWAY = "Exclusive";
	public static final String PALETTE_EXCLUSIVE_GATEWAY_TOOLTIP = "Exclusive Gateway";
	public static final String PALETTE_COMPLEX_GATEWAY = "Complex";
	public static final String PALETTE_COMPLEX_GATEWAY_TOOLTIP = "Complex Gateway";
	public static final String PALETTE_PARALLEL_GATEWAY = "Parallel";
	public static final String PALETTE_PARALLEL_GATEWAY_TOOLTIP = "Parallel Gateway";
	public static final String PALETTE_EVENT_BASED_GATEWAY = "Event Based";
	public static final String PALETTE_EVENT_BASED_GATEWAY_TOOLTIP = "Event Based Gateway";
	
	
	public static final String NODE_INCLUSIVE_GATEWAY = "Inclusive";
	public static final String NODE_EXCLUSIVE_GATEWAY = "Exclusive";
	public static final String NODE_PARALLEL_GATEWAY = "Parallel";
	public static final String NODE_COMPLEX_GATEWAY = "Complex";
	public static final String NODE_EVENT_BASED_GATEWAY = "Event Based";
	
	
	// Tasks
	
	public static final String PALETTE_TASKS = "Tasks";
	public static final String PALETTE_TASKS_TOOLTIP = "Task Tools";
	
	public static final String PALETTE_TASK_RULE_FUNCTION = "Rulefunction Script Task";
	public static final String PALETTE_TASK_RULE_FUNCTION_TOOLTIP = "Add a Rulefunction Script Task";
	public static final String PALETTE_TASK_DECISION_TABLE = "Decision Table Task";
	public static final String PALETTE_TASK_DECISION_TABLE_TOOLTIP = "Add a Decision Table Task";
	public static final String PALETTE_TASK_SEND_EVENT = "Send Event Task";
	public static final String PALETTE_TASK_SEND_EVENT_TOOLTIP = "Add a Send Event Task";
	public static final String PALETTE_TASK_RECIEVE_EVENT = "Recieve Event Task";
	public static final String PALETTE_TASK_RECIEVE_EVENT_TOOLTIP = "Add a Recieve Event Task";
	public static final String PALETTE_TASK_MANUAL = "Manual Task";
	public static final String PALETTE_TASK_MANUAL_TOOLTIP = "Add a Manual Task";
	public static final String PALETTE_TASK_WEB_SERVICE = "Web Service Task";
	public static final String PALETTE_TASK_WEB_SERVICE_TOOLTIP = "Add a Web Service Task";
	public static final String PALETTE_TASK_INFERENCE = "Business Rule Task";
	public static final String PALETTE_TASK_INFERENCE_TOOLTIP = "Add a Business Rule Task";
	
	// Sub Process
	public static final String PALETTE_ACTIVITY_SUB_PROCESS = "Sub Process";
	public static final String PALETTE_ACTIVITY_SUB_PROCESS_TOOLTIP = "Add a Sub Process Activity";
	
	// Sequence Flow
	public static final String PALETTE_COMMON_SEQUENCE_FLOW = "Sequence Flow";
	public static final String PALETTE_COMMON_SEQUENCE_FLOW_TOOLTIP = "Connect Flow Nodes";
	
	public static final String NODE_SEQUENCE_FLOW = "Sequence";
	
	// Text Annotation
	public static final String PALETTE_COMMON_TEXT_ANNOTATION= "Note";
	public static final String PALETTE_COMMON_TEXT_ANNOTATION_TOOLTIP="Add a Note";
	public static final String ATTR_ANOOTATION_TEXT ="Add a Note";
	
	public static final String NODE_COMMON_TEXT_ANNOTATION = "Text Annotation";
	
	// Process 
	public static final String NODE_PROCESS = "Process";
	
	
	// Lanes and Pools
	public static final String PALETTE_SWIM_LANES = "Swimlanes";
	public static final String PALETTE_SWIMLANES_TOOLTIP = "Swimlanes tools";
	
	public static final String PALETTE_POOL = "Pool";
	public static final String PALETTE_POOL_TOOLTIP = "Pool";
	public static final String PALETTE_LANE = "Lane";	
	public static final String PALETTE_LANE_TOOLTIP = "Lane";
	
	public static final String NODE_LANE = "Lane";	
	public static final String NODE_POOL = "Pool";
	public static final String DEFAULT_LANE_NAME = "Default Lane"; //$NON-NLS-1$
	public static final String DEFAULT_LANESET_NAME = "Default Laneset";//$NON-NLS-1$
	
	// Process Types
	public static final String PROCESS_TYPE_PRIVATE = "Private";
	public static final String PROCESS_TYPE_PUBLIC = "Public";
	
	
	// Menus 
	public static final String GENERATE_ACTION = "com.tibco.cep.bpmn.ui.action.generateProcessCode";//$NON-NLS-1$
	
	// help 
	public static final String GENERAL_HELP_SECTION ="General";
	public static final String lOOP_CHAR_HELP_SECTION ="LoopCharacteristics";
	public static final String REPLY_EVENT_HELP_SECTION ="ReplyEvent";
	public static final String TIMEOUT_HELP_SECTION ="Timeout";
	public static final String LOOP_CHAR_TYPE_ITERATOR = "iterator";
	public static final String LOOP_CHAR_TYPE_COUNTER = "count";
	public static final String LOOP_CHAR_XSLT_STRREPLACE = "ReplaceWithInteger";
//	public static final String LOOP_CHAR_XPATH_TEMPLATE ="xpath://&lt;?xml version=\\&quot;1.0\\&quot; encoding=\\&quot;UTF-8\\&quot;?>\\n&lt;xpath>\\n    &lt;expr>"+LOOP_CHAR_XSLT_STRREPLACE
//														 +"&lt;/expr>\\n    &lt;namespaces>\\n        &lt;namespace URI=\\&quot;http://www.w3.org/1999/XSL/Transform\\&quot; pfx"	
//														 +"=\\&quot;xsl\\&quot;/>\\n        &lt;namespace URI=\\&quot;http://www.w3.org/2001/XMLSchema\\&quot;"
//														 +"pfx=\\&quot;xsd\\&quot;/>\\n    &lt;/namespaces>\\n    &lt;variables>\\n        &lt;variable>job&lt;/variable>\\n    &lt;/variables>\\n&lt;/xpath>";
	public static final String LOOP_CHAR_XPATH_TEMPLATE = "xpath://<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xpath>\n    <expr>"+LOOP_CHAR_XSLT_STRREPLACE+"</expr>\n    " +
															"<namespaces>\n        <namespace URI=\"http://www.w3.org/1999/XSL/Transform\" pfx=\"xsl\"/>\n        " +
															"<namespace URI=\"http://www.w3.org/2001/XMLSchema\" pfx=\"xsd\"/>\n    </namespaces>\n    <variables/>\n</xpath>";
}
