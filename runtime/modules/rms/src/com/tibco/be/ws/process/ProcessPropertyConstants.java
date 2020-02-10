/**
 * 
 */
package com.tibco.be.ws.process;

/**
 * Constants used to send data to and fro between the API's interacting with the EMF and the BE project
 * 
 * @author vpatil
 */
public interface ProcessPropertyConstants {
	public static final String PROP_TYPE_CONCEPT = "Concept";
	public static final String PROP_TYPE_CONCEPT_REFERENCE = "ConceptReference";
	public static final String PROP_TYPE_ONTOLOGY_PREFIX = "www.tibco.com/be/ontology";
	public static final String PROCESS_TYPE_PUBLIC = "Public";
	public static final String PROCESS_TYPE_PRIVATE = "Private";
	
	public static final String PROP_ID = "id";
	public static final String PROP_NAME = "name";
	public static final String PROP_TYPE = "type";
	public static final String PROP_ISARRAY = "isArray";
	public static final String PROP_PATH = "path";
	public static final String PROP_LABEL = "label";

	
	public static final String PROP_UNIQUEID = "uniqueId";
	public static final String PROP_LANEIDS = "laneIds";
	public static final String PROP_SEQUENCEIDS = "sequenceIds";
	public static final String PROP_REFERENCEIDS = "referenceIds";
	public static final String PROP_POINT_X = "X";
	public static final String PROP_POINT_Y = "Y";
	public static final String PROP_RESOURCE = "resource";
	public static final String PROP_ISIMMEDIATE = "isImmediate";
	
	public static final String PROP_TIMEOUT_EXPRESSION = "timeoutExpr";
	public static final String PROP_TIMEOUT_EVENT = "timeoutEvent";
	public static final String PROP_TIMEOUT_UNIT = "timeoutUnit";
	
	public static final String PROP_LC_TESTBEFORE = "testBefore";
	public static final String PROP_LC_LOOPCONDITION = "loopCondition";
	public static final String PROP_LC_LOOPCOUNT = "loopCount";
	public static final String PROP_LC_ISSEQUENTIAL = "isSequential";
	public static final String PROP_LC_CARDINALITYBODY = "cardinalityBody";
	public static final String PROP_LC_BEHAVIOR = "behavior";
	public static final String PROP_LC_CONDITIONBODY = "conditionBody";
	
	public static final String PROP_SERVICE = "service";
	public static final String PROP_PORT = "port";
	public static final String PROP_OPERATION = "operation";
	public static final String PROP_SOAPACTION = "soapAction";
	public static final String PROP_TIMEOUT = "timeout";
	public static final String PROP_ENDPOINTURL = "endPointUrl";
	public static final String PROP_JNDICONTEXTURL = "jndiContextURL";
	public static final String PROP_BINDINGTYPE = "bindingType";
	
	public static final String PROP_REPLYTO = "replyTo";
	public static final String PROP_CONSUME = "consume";
	
	public static final String PROP_TITLE = "title";
	public static final String PROP_TOOLTIP = "tooltip";
	public static final String PROP_ICON = "icon";
	public static final String PROP_VISIBLE = "visible";
	public static final String PROP_EMFTYPE = "emftype";
	public static final String PROP_EMF_EXTENDED_TYPE = "emfextendedtype";
	
	public static final String PROP_DEFAULT_SEQUENCEID = "defaultSequenceId";
	public static final String PROP_TRANSFORMATION = "transformation";
	public static final String PROP_JOIN_RULE_FUNCTION = "joinRuleFunction";
	public static final String PROP_FORK_RULE_FUNCTION = "forkRuleFunction";
	public static final String PROP_MERGE_EXPRESSION = "mergeExpression";
	
	public static final String PROP_DOC_ID = "docId";
	public static final String PROP_DOC_TEXT = "docText";
	public static final String PROP_EXPRESSION = "expression";
}
