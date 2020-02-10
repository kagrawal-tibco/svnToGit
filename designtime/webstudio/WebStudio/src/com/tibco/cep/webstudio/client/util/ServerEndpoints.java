package com.tibco.cep.webstudio.client.util;

import com.google.gwt.core.client.GWT;
import com.tibco.cep.webstudio.client.WebStudio;


/**
 * All the Server API end points which the UI widgets will communicate with.
 * 
 * @author Vikram Patil
 */
public enum ServerEndpoints {

	// RMS Server End Points
	RMS_GET_MANAGED_PROJECTS("/projects"),
	RMS_GET_ALL_PROJECTS("/allProjects"),
	RMS_GET_WORKSPACE_CONTENTS("/workspace"),
	
	RMS_GET_PROJECT_ARTIFACTS("/artifacts"),
	RMS_POST_PROJECT_CHECKOUT("/checkout"),
	
	RMS_POST_FETCH_ARTIFACTS_TO_COMMIT("/committables"),
	RMS_POST_PROJECT_COMMITTED_ARTIFACTS("/commit"),
	RMS_POST_PROJECT_REVERT_ARTIFACTS("/revert"),
	RMS_GET_PROJECT_ARTIFACT_LOCKS("/artifactLocks"),
	
	RMS_GET_ARTIFACT_CONTENTS("/artifact"),
	RMS_GET_ARTIFACT_REVISIONS("/artifact/history"),
	RMS_POST_ARTIFACT_SAVE("/artifact/save"),
	RMS_DELETE_ARTIFACT("/artifact/delete"),
	RMS_GET_ARTIFACT_EXPORT("/artifact/export"),
	RMS_PUT_ARTIFACT_RENAME("/artifact/rename"),
	RMS_LOCK_ARTIFACT("/artifact/lock"),
	RMS_UNLOCK_ARTIFACT("/artifact/unlock"),
	RMS_GET_ARTIFACT_VERSION_DIFF("/artifact/compare"),
	RMS_VALIDATE("/artifact/validate"),
	
	RMS_POST_FETCH_ARTIFACTS_TO_UPDATE("/synchronizables"),
	RMS_POST_SYNCRONIZE_PROJECT_ARTIFACTS("/synchronize"),
	
	RMS_DECISION_TABLE_SHOW_ANALYZER_PANE("/decisiontable/analyzerValues"),
	RMS_DECISION_TABLE_ANALYZE_ACTION("/decisiontable/analyze"),
	RMS_DECISION_TABLE_SHOW_COVERAGE("/decisiontable/coverage"),
	
	RMS_GET_WORKLIST("/worklist"),	
	RMS_POST_WORKLIST_ITEMS("/worklist/statusChange"),
	RMS_GET_WORKLIST_ITEM_REVIEW("/worklist/review"),
	
	RMS_GET_WORKLIST_ITEMS("/details"),
	RMS_PUT_DELEGATE_WORKLIST_ITEM("/worklist/delegate"),
	RMS_GET_DELEGATE_WORKLIST_ROLES("/delegateRoles"),
	RMS_DELETE_WORKLIST_ITEM("/worklist/delete"),
	
	LOGIN("/login"),
	LOGOUT("/logout"),
	
	RMS_GET_MY_GROUPS("/groups"),
	RMS_DELETE_GROUP("/delete"),
	RMS_ADD_GROUP("/add"),
	RMS_UPDATE_GROUP("/update"),
	RMS_GET_GROUP_ARTIFACTS("/artifacts"),
	
	RMS_GET_RECENTLYOPENED_ARTIFACTS("/recentlyOpened"),
	RMS_PUT_RECENTLYOPENED_ARTIFACTS("/recentlyOpened/add"),
	
	RMS_GET_FAVORITE_ARTIFACTS("/favorites"),
	RMS_ADD_TO_FAVORITE_ARTIFACTS("/favorites/add"),
	
	RMS_POST_FETCH_ARTIFACTS_TO_DEPLOY("/showDeployables"),
	RMS_DEPLOY_ARTIFACT("/deploy"),
	RMS_GENERATE_DEPLOYABLE("/generateDeployable"),
	
	RMS_CHECK_DEPLOYABLE("/checkDeployable"),
	RMS_GET_USER_DATA("/userData"),
	RMS_GET_ACL_DATA("/aclData"),
	RMS_UPDATE_USER_PERMISSIONS("/userPermission/update"),
	RMS_UPDATE_USER_DATA("/userData/update"),
	RMS_UPDATE_ACL_DATA("/aclData/update"),
	RMS_GET_USER_PREFERENCES("/preferences"),
	RMS_UPDATE_USER_PREFERENCES("/preferences/update"),
	
	RMS_SHOW_DT_ARGUMENTS("/decisiontable/arguments"),
	
	RMS_DT_IMPORT("/decisiontable/import"),
	
	RMS_GET_DOMAINS("/domains"),
	RMS_GET_DOMAINS_BY_DATATYPE("/domains/values"),
	
	RMS_GET_DISPLAY_PROPERTIES("/displayProperties"),
	
	RMS_GET_ABOUT_DETAILS("/about"),
	
	RMS_POST_PROCESS_VALIDATE("/processValidate"),
	RMS_PROCESS_POPULATE_PALETTE_PANE("/processPalette"),
	
	RMS_GET_APPLICATION_PREFERENCES("/appPreferences"),
	RMS_GET_NOTIFICATION_PREFERENCES("/notifyPreferences"),
	RMS_CHECK_APPLICATION_PREFERENCES_PROTLET_PERMISSION("/appPreferences/permission"),
	RMS_UPDATE_APPLICATION_PREFERENCES("/appPreferences/update"),
	RMS_UPDATE_NOTIFICATION_PREFERENCES("/notifyPreferences/update"),

	RMS_GET_PROJECTS_FOR_LOCK_MGMT("/projectsLockMgmt"),
	
	RMS_GET_LOAD_AND_PARSE_WSDL("/loadWSDL"),
	
	RMS_GET_SYNC_TO_REPOSITORY_ARTIFACTS("/repositorySyncArtifacts"),
	RMS_POST_PROJECT_REPOSITORY_SYNC("/repositorySync");
	
	private String url;
	
	private static final String API_BASE_CONTEXT_PATH = "ws/api";
	
	private ServerEndpoints(String url) {
		this.url = url;
	}
	
	public String getURL(String... additionalPath) {
		String basePath = GWT.getHostPageBaseURL();
		// trim the last separation
		basePath = basePath.substring(0, basePath.length() - 1);
		// remove the module name from the context path
		basePath = basePath.substring(0, basePath.lastIndexOf("/") + 1);
		
		// form the API Path
		StringBuffer apiPathBuffer = new StringBuffer(basePath);
		apiPathBuffer.append(API_BASE_CONTEXT_PATH);
		if (this != LOGIN) apiPathBuffer.append("/" + WebStudio.get().getApiToken());
		
		if (additionalPath != null && additionalPath.length > 0) {
			for (String pathParam : additionalPath) {
				apiPathBuffer.append("/" + pathParam);
			}
		}
		
		apiPathBuffer.append(this.url);
		apiPathBuffer.append(".xml");
		
		return apiPathBuffer.toString();
	}
}
