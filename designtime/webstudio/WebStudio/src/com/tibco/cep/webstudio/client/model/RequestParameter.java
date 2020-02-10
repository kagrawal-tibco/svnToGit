/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enclose Parameters that need to be sent to the server
 * 
 * @author Vikram Patil
 */
public class RequestParameter {

	private Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();

	public static final String REQUEST_PROJECT_NAME = "projectName";
	public static final String REQUEST_LOCALE_CODE = "localeCode";
	public static final String REQUEST_PARAM_PATH = "artifactPath";
	public static final String REQUEST_PARAM_TYPE = "artifactType";
	public static final String REQUEST_PARAM_FILE_EXTN = "artifactExtension";
	public static final String REQUEST_PARAM_CHANGE_TYPE = "artifactChangeType";
	public static final String REQUEST_PARAM_BASE_ARTIFACT_PATH = "baseArtifactPath";
	public static final String REQUEST_PARAM_ARTIFACTLIST = "artifactList";
	public static final String REQUEST_PARAM_POST_DATA = "data";
	public static final String REQUEST_USER_NAME = "username";
	public static final String REQUEST_USER_PASSWORD = "password";
	public static final String REQUEST_USER_FORCELOGIN = "forceLogin";
	public static final String REQUEST_ARTIFACT_DATA_ITEM = "artifactDataItem";
	public static final String REQUEST_PARAM_ARTIFACT_RENAMETO_PATH = "artifactRenameToPath";
	public static final String REQUEST_PARAM_IMPLEMENTSPATH = "implementsPath";
	public static final String REQUEST_OPERATION_NAME = "operationName";
	
	public static final String REQUEST_PARAM_REVISION_ID = "revisionId";
	public static final String REQUEST_PARAM_APPROVAL_STATUS = "approvalStatus";
	public static final String REQUEST_PARAM_APPROVAL_COMMENTS = "approvalComments";
	public static final String REQUEST_PARAM_APPROVAL_ENVIRONMENTS = "deployEnvironments";

	public static final String REQUEST_PARAM_COMMIT_COMMENTS = "commitComments";
	
	public static final String REQUEST_GROUP_NAME = "groupName";
	public static final String REQUEST_GROUP_FILE_TYPE = "groupType";
	public static final String REQUEST_GROUP_OPERATION_TYPE = "operationType";
	
	public static final String REQUEST_ARGUMENT_PATH = "argumentPath";
	public static final String REQUEST_ARGUMENT_TYPE = "argumentType";
	public static final String REQUEST_ARGUMENT_PROPERTY_NAME = "argumentPropertyName";
	public static final String REQUEST_ARGUMENTS = "ruleFunctionImplArgumentsPath";
	public static final String REQUEST_ARGUMENT_PROPERTIES = "entityArgumentPropertiesPath";
	
	public static final String REQUEST_PARAM_GEN_DEBUG_INFO = "generateDebugInfo";
	public static final String REQUEST_PARAM_GEN_GENERATE_CLASS_ONLY = "buildClassesOnly";
	public static final String REQUEST_PARAM_INCLUDE_SERVICE_GLOBAL_VAR = "includeAllServiceLevelGlobalVar";
	
	public static final String REQUEST_PARAM_DIFFMODE = "diffMode";

	public RequestParameter(String key, Object value) {
		parameterMap.put(key, value);
	}

	/**
	 * Set additional parameters
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		parameterMap.put(key, value);
	}

	/**
	 * Get parameter map
	 * 
	 * @return
	 */
	public Map<String, Object> getParameters() {
		return parameterMap;
	}
}
