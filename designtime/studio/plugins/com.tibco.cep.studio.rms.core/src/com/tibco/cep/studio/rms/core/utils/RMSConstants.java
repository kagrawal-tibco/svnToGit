/**
 * 
 */
package com.tibco.cep.studio.rms.core.utils;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author aathalye
 *
 */
public class RMSConstants {	
	
	public final static String MESSAGE_HEADER_NAMESPACE_PROPERTY="_ns_";
    public final static String MESSAGE_HEADER_NAME_PROPERTY="_nm_";
    public static final String USERNAME_HEADER = "username";
    public static final String PASSWORD_HEADER = "password";
    public static final String TOKENSTRING_HEADER = "tokenString";
    public static final String REQUEST_ID_HEADER = "requestid";
    public static final String REQUESTED_ROLE_HEADER = "role";
    public static final String SELECTED_PROJECT_HEADER = "projectname";
    public static final String REQ_RES_PATH_HEADER = "requestedresourcepath";
    public static final String LCK_REQ_RES_USER_HEADER = "requestor";//Lock/Unlock requestor
    public static final String IMPLEMENTED_RESOURCE_HEADER = "implementedresource";//Lock/Unlock requestor
    public static final String OPTIMIZED_HEADER = "optimized";//Whether checkin input is optimized
    public static final String EXT_ENTITY_PATH_HEADER = "extEntityFileName";
    public static final String CLEAR_APPROVED_TASKS_HEADER = "clearApprovedTasks";
    public static final String LOGIN_TOKEN_HEADER = "loginToken";
    
    public static final String LOGIN_REQ_EVENT = "AMS_E_LoginRequestEvent";
    public static final String CHK_IN_EXT_ENTS_REQ_EVENT = "CheckinExternalEntitiesEvent";
    public static final String TASKS_LISTS_REQ_EVENT = "TasksListRequestEvent";
    public static final String REVISIONS_REQ_EVENT = "RevisionsListRequestEvent";
    public static final String USER_REQ_EVENT = "UserRequestsEvent";
    public static final String CHECKOUT_EVENT = "CheckoutDecisionProjectRequestEvent";
    public static final String CHECKIN_EVENT = "CheckinProjectRequestEvent";
    public static final String ROUTER_EVENT = "RouterEvent";
    public static final String SHOW_REQUEST_DETAILS_EVENT = "ShowRequestDetailsEvent";
    public static final String FETCH_PROJECTS_LIST_EVENT = "AMS_E_ProjectsListRequestEvent";
    public static final String LCK_REQUEST_EVENT = "LockRequestEvent";
    public static final String UNLCK_REQUEST_EVENT = "UnlockRequestEvent";
    public static final String FETCH_IMPLEMENTATION_EVENT = "FetchParticularImplementationRequestEvent";
    public static final String FETCH_IMPLEMENTATION_REVIEW_EVENT = "FetchImplForReviewEvent";
    public static final String REFRESH_ACL_EVENT = "FetchACLEvent";
    
    public static final String LOGIN_DEST_PROPERTY = "rms.login.destination.name";
    public static final String WORKLIST_DEST_PROPERTY = "rms.worklists.destination.name";
    public static final String REVISIONS_DEST_PROPERTY = "rms.impl.revisions.destination.name";
    public static final String USER_REQ_DEST_PROPERTY = "rms.myrequests.destination.name";
    public static final String CHK_OUT_DEST_PROPERTY = "rms.checkout.destination.name";
    public static final String CHK_IN_DEST_PROPERTY = "rms.checkin.destination.name";
    public static final String REQUEST_DETAILS_DEST_PROPERTY = "rms.requestdetails.destination.name";
    public static final String PROJECTS_LIST_DEST_PROPERTY = "rms.fetchProjectsList.destination.name";
    public static final String ARTIFACT_CONTENTS_DEST_PROPERTY = "rms.fetchcontents.destination.name";
    public static final String ARTIFACT_CHECKIN_DEST_PROPERTY = "rms.checkin.destination.name";
    public static final String LCK_REQUEST_DEST_PROPERTY = "rms.lockresource.destination.name";
    public static final String UNLCK_REQUEST_DEST_PROPERTY = "rms.unlockresource.destination.name";
    public static final String FETCH_IMPL_DEST_PROPERTY = "rms.fetchimplementation.destination.name";
    public static final String FETCH_IMPL_REVIEW_DEST_PROPERTY = "rms.fetchimplementation.review.destination.name";
    public static final String REFRESH_ACL_DEST_PROPERTY = "rms.refreshacl.destination.name";
    public static final String CHKIN_EXT_ENTS_DEST_PROPERTY = "rms.checkin.extentities.destination.name";
    
    public static final String RET_ALL_ARTIFACTS_NAMES_DEST_PROPERTY = "rms.checkout.artifacts.names";
    
    public static enum MethodType {
    	GET, POST, PUT, DELETE;
    }
    
    public static final String LOGIN_EVENT_NS = "www.tibco.com/be/ontology/AAA/Events/AuthenEvents/AMS_E_LoginRequestEvent";
    public static final String TASKS_LIST_NS = "www.tibco.com/be/ontology/Approval/Events/TasksListsResponseEvent";
    public static final String USER_REQ_LIST_NS = "www.tibco.com/be/ontology/Approval/Events/UserRequestsResponseEvent";
    public static final String PROJS_LIST_EVENT_NS = "www.tibco.com/be/ontology/Approval/Events/FetchProjectsListRequestEvent";
    public static final String CHECKOUT_EVENT_NS = "www.tibco.com/be/ontology/Approval/Events/CheckoutDecisionProjectRequestEvent";
    
    public static final String REVS_LIST_NS = "www.tibco.com/be/ontology/Approval/Events/RevisionsListResponseEvent";
    public static final String DP_CONCEPT_NS = "www.tibco.com/be/ontology/Approval/Concepts/DecisionProject";
    public static final String DT_MODEL_NS = "http:///com/tibco/cep/decision/table/model/DecisionTable.ecore";
    public static final String NAMESPACE = "http://www.tibco.com/be/schemas/config";
	public static final String DP_NAMESPACE = "http://www.tibco.com/cep/decisionproject";
	public static final ExpandedName DP_EVENT_ELEMENT = ExpandedName.makeName("event");
	public static final ExpandedName DP_ELEMENT = ExpandedName.makeName("decisionProject");
	public static final ExpandedName EAR_ELEMENT = ExpandedName.makeName("ear");
	public static final ExpandedName ACL_ELEMENT = ExpandedName.makeName("acl");
	public static final ExpandedName DM_ELEMENT = ExpandedName.makeName("dm");
	public static final ExpandedName AUTH_TOKEN_ELEMENT = ExpandedName.makeName("token");
	
    public static final ExpandedName ARCHIVES_ELEMENT = ExpandedName.makeName(NAMESPACE, "archives");
    public static final ExpandedName CONCEPT_ELEMENT = ExpandedName.makeName(NAMESPACE, "concept");
    public static final ExpandedName RULESET_ELEMENT = ExpandedName.makeName(NAMESPACE, "ruleset");
    public static final ExpandedName RULEFUNCTION_ELEMENT = ExpandedName.makeName(NAMESPACE, "ruleFunction");
    public static final ExpandedName EVENT_ELEMENT = ExpandedName.makeName(NAMESPACE, "event");
    public static final ExpandedName RESOURCES_ELEMENT = ExpandedName.makeName(NAMESPACE, "resources");
    public static final ExpandedName LISTENDESTINATION_ELEMENT = ExpandedName.makeName(NAMESPACE, "listendestinations");
    public static final ExpandedName CHANNEL_ELEMENT = ExpandedName.makeName(NAMESPACE, "channel");
    public static final ExpandedName OM_ELEMENT = ExpandedName.makeName(NAMESPACE, "om-parameters");
    public static final ExpandedName OM_ENABLE = ExpandedName.makeName(NAMESPACE, "omEnable");
    public static final ExpandedName PREPROCESSOR_ELEMENT = ExpandedName.makeName(NAMESPACE, "Preprocessor");
    public static final ExpandedName WORKERS_ELEMENT = ExpandedName.makeName(NAMESPACE, "inputworkers");
    public static final ExpandedName QUEUE_SIZE_ELEMENT = ExpandedName.makeName(NAMESPACE, "inputqueuesize");
    public static final ExpandedName QUEUE_WEIGHT_ELEMENT = ExpandedName.makeName(NAMESPACE, "inputqueueweight");
    public static final ExpandedName DESIGNTIME_EDITION_XNAME = ExpandedName.makeName(NAMESPACE, "edition");
    
    public static final ExpandedName PROPERTY_DEFINITIONS_ELEMENT = ExpandedName.makeName("propertyDefinitions");
    public static final ExpandedName PROPERTY_DEFINITION_ELEMENT = ExpandedName.makeName("PropertyDefinition");
    public static final ExpandedName DESTINATIONS_ELEMENT = ExpandedName.makeName("destinations");
    public static final ExpandedName DESTINATION_ELEMENT = ExpandedName.makeName("destination");
    public static final ExpandedName USER_PROPERTIES_ELEMENT = ExpandedName.makeName("userProperties");
    public static final ExpandedName USER_PROPERTY_ELEMENT = ExpandedName.makeName("userProperty");
    public static final ExpandedName RULES_ELEMENT = ExpandedName.makeName("rules");
    public static final ExpandedName RULE_ELEMENT = ExpandedName.makeName("rule");
    public static final ExpandedName DRIVER_TYPE_ELEMENT = ExpandedName.makeName("type");
    public static final ExpandedName DRIVER_CONFIG_ELEMENT = ExpandedName.makeName("configMethod");
    public static final ExpandedName DRIVER_VERSION_ELEMENT = ExpandedName.makeName("version");
    public static final ExpandedName USER_PROPERTY_NAME_ELEMENT = ExpandedName.makeName("name");
    // for rule function
    public static final ExpandedName RULE_FUNCTION_PROPERTY_LASTMODIFIED = ExpandedName.makeName("lastModified");
    public static final ExpandedName RULE_FUNCTION_PROPERTY_EXTENDED = ExpandedName.makeName("extendedProperties");
    public static final ExpandedName RULE_FUNCTION_PROPERTY_HIDDEN = ExpandedName.makeName("hiddenProperties");
    public static final ExpandedName RULE_FUNCTION_PROPERTY_VALIDITY = ExpandedName.makeName("validity");
    public static final ExpandedName RULE_FUNCTION_PROPERTY_RETURN_TYPE = ExpandedName.makeName("returnType");
    public static final ExpandedName RULE_FUNCTION_ARGUMENTS = ExpandedName.makeName("arguments");
    public static final ExpandedName RULE_FUNCTION_ARGUMENT = ExpandedName.makeName("argument");

    public static class AttributeConstants {
    	public static final ExpandedName FOLDER_ATTR = ExpandedName.makeName("folder");
    	public static final ExpandedName NAME_ATTR = ExpandedName.makeName("name");
    	public static final ExpandedName SUPER_ATTR = ExpandedName.makeName("super");
    	public static final ExpandedName TYPE_ATTR = ExpandedName.makeName("type");
    	public static final ExpandedName CONCEPTTYPE_ATTR = ExpandedName.makeName("conceptType");
    	public static final ExpandedName ISARRAY_ATTR = ExpandedName.makeName("isArray");
    	public static final ExpandedName POLICY_ATTR = ExpandedName.makeName("policy");
    	public static final ExpandedName HISTORY_ATTR = ExpandedName.makeName("history");
    	public static final ExpandedName DEF_VALUE_ATTR = ExpandedName.makeName("value");
    	public static final ExpandedName IS_SCORECARD_ATTR = ExpandedName.makeName("isAScorecard");
    	public static final ExpandedName TTL_ATTR = ExpandedName.makeName("ttl");
    	public static final ExpandedName TTL_UNITS_ATTR = ExpandedName.makeName("units");
    	public static final ExpandedName IS_RULE_FUNC_ATTR = ExpandedName.makeName("isAFunction");
    	public static final ExpandedName IS_RULE_FUNC_VIRTUAL = ExpandedName.makeName("virtual");    	
    	public static final ExpandedName RULE_FUNCTION_ARGUMENT_IDENTIFIER = ExpandedName.makeName("identifier");
    	public static final ExpandedName RULE_FUNCTION_ARGUMENT_ENTITY = ExpandedName.makeName("entity");
    	public static final ExpandedName RULE_FUNCTION_ARGUMENT_ENTITY_TYPE = ExpandedName.makeName("entityType");
    	public static final ExpandedName DESCRIPTION_ATTR = ExpandedName.makeName("description");
    }
}
