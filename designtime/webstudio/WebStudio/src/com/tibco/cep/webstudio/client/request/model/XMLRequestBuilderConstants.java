package com.tibco.cep.webstudio.client.request.model;

public class XMLRequestBuilderConstants {
	
	public static final String REQUEST_ELEMENT = "request";
	public static final String DATA_ELEMENT = "data";
	public static final String PROJECT_ELEMENT = "project";
	public static final String NAME_ELEMENT = "name";
	public static final String OPERATION_ELEMENT = "operation";
	public static final String ITEM_ELEMENT = "item";
	public static final String PARAMETER_ELEMENT = "parameter";
	public static final String LIST_ELEMENT = "list";
	public static final String PARAMETER_ATTRIBUTE_NAME = "name";

	public static final String ARTIFACT_ITEM_ELEMENT = "artifactItem";
	public static final String ARTIFACT_PATH_ELEMENT = "artifactPath";
	public static final String ARTIFACT_TYPE_ELEMENT = "artifactType";
	public static final String ARTIFACT_FILE_EXTN_ELEMENT = "fileExtension";
	public static final String ARTIFACT_CONTENTS_ELEMENT = "artifactContent";
	public static final String ARTIFACT_CHANGE_TYPE = "changeType";
	public static final String ARTIFACT_BASE_ARTIFACT_PATH = "baseArtifactPath";
	public static final String ARTIFACT_IS_SYNC_MERGE = "isSyncMerge";
	
	public static final String COMMIT_COMMENTS_ELEMENT = "commitComments";
	
	public static final String RTI_PATH_ELEMENT = "artifactPath";
	public static final String RTI_IMPLEMENTS_PATH_ELEMENT = "implementsPath";
	public static final String RTI_DESCRIPTION_ELEMENT = "description";
	public static final String RTI_PRIORITY_ELEMENT = "rulePriority";
	public static final String RTI_VIEW_ELEMENT = "view";
	public static final String RTI_BUILDER_ELEMENT = "builder";
	public static final String RTI_BINDINGINFO_ELEMENT = "bindingInfo";
	public static final String RTI_BINDINGINFO_ID_ELEMENT = "bindingId";
	public static final String RTI_BINDINGINFO_TYPE_ELEMENT = "type";
	public static final String RTI_BINDINGINFO_VALUE_ELEMENT = "value";
	public static final String RTI_CONDITION_ELEMENT = "conditions";
	public static final String RTI_COMMAND_ELEMENT = "commands";
	public static final String RTI_COMMANDINFO_ELEMENT = "commandInfo";
	public static final String RTI_COMMANDINFO_ALIAS_ELEMENT = "commandAlias";
	public static final String RTI_COMMANDINFO_TYPE_ELEMENT = "type";
	public static final String RTI_COMMANDINFO_ACTIONTYPE_ELEMENT = "actionType";
		
	public static final String RTI_FILTER_ELEMENT = "filter";
	public static final String RTI_FILTER_ID_ELEMENT = "filterId";
	public static final String RTI_FILTER_MATCHTYPE_ELEMENT = "matchType";
	public static final String RTI_FILTER_OPERATOR_ELEMENT = "operator";
	public static final String RTI_FILTER_VALUE_ELEMENT = "value";
	public static final String RTI_FILTER_VALUE_SIMPLE_ELEMENT = "simple";
	public static final String RTI_FILTER_VALUE_MIN_ELEMENT = "minValue";
	public static final String RTI_FILTER_VALUE_MAX_ELEMENT = "maxValue";
	public static final String RTI_FILTER_LINK_ELEMENT = "link";
	public static final String RTI_FILTER_LINK_NAME_ELEMENT = "name";
	public static final String RTI_FILTER_LINK_TYPE_ELEMENT = "type";
	
	public static final String GROUP_ELEMENT = "userGroup";
	public static final String GROUP_TYPE = "type";
	public static final String GROUP_ITEM_ELEMENT = "groupItem";
	public static final String GROUP_CONTENT_ELEMENT = "groupContent";
	public static final String GROUP_ARTIFACT_ELEMENT = "artifact";
	
	public static final String USER_PREFERENCE_ELEMENT = "userPreference";
	public static final String USER_PREFERENCE_ITEM_ELEMENT = "userPreferenceItem";
	public static final String USER_PREFERENCE_PORTAL_COLUMN_ELEMENT = "portalColumns";
	public static final String USER_PREFERENCE_RECENTLY_OPENED_LIMIT_ELEMENT = "recentlyOpenedArtifactLimit";
	public static final String USER_PREFERENCE_FAVORITE_LIMIT_ELEMENT = "favoriteArtifactLimit";
	public static final String USER_PREFERENCE_CUSTOM_URL_ELEMENT = "customURL";
	public static final String USER_PREFERENCE_ITEM_VIEW = "itemView";
	public static final String USER_PREFERENCE_DT_PAGE_SIZE = "decisionTablePageSize";
	public static final String USER_PREFERENCE_AUTO_UNLOCK_ON_REVIEW = "autoUnLockOnReview";
	public static final String USER_PREFERENCE_GROUP_RELATED_ARTIFACTS = "groupRelatedArtifacts";
	public static final String USER_PREFERENCE_ALLOW_CUSTOM_DOMAIN_VALUES = "allowCustomDomainValues";
	public static final String USER_PREFERENCE_SHOW_COLUMN_ALIAS_IFPRESENT = "showColumnAliasIfPresent";
	public static final String USER_PREFERENCE_AUTO_FIT_COLUMNS_APPROCH = "autoFitColumnsApproch";
	public static final String USER_PREFERENCE_SCS_USER_NAME_ELEMENT = "scsUserName";
	public static final String USER_PREFERENCE_SCS_USER_PASSWORD_ELEMENT = "scsUserPassword";
	public static final String USER_PREFERENCE_DEFAULT_RTI_FILTER_TYPE = "defaultRTIFilterType";
	public static final String USER_PREFERENCE_DASHBOARD_PORTLET_ELEMENT = "dashboardPortlets";
	public static final String USER_PREFERENCE_DASHBOARD_USER_PORTLET_ELEMENT = "portlet";
	public static final String USER_PREFERENCE_DASHBOARD_PORTLET_ID_ELEMENT = "portletId";
	public static final String USER_PREFERENCE_DASHBOARD_PORTLET_COLUMN_ELEMENT = "col";
	public static final String USER_PREFERENCE_DASHBOARD_PORTLET_ROW_ELEMENT = "rw";
	public static final String USER_PREFERENCE_DASHBOARD_PORTLET_HEIGHT_ELEMENT = "height";
	public static final String USER_PREFERENCE_DASHBOARD_PORTLET_COLSPAN_ELEMENT = "colSpan";
	
	public static final String APPLICATION_PREFERENCE_ELEMENT = "applicationPreference";
	public static final String APPLICATION_PREFERENCE_ITEM_ELEMENT = "applicationPreferenceItem";
	public static final String APPLICATION_PREFERENCE_OPERATOR_PREFERNCES_ELEMENT = "operatorPreferences";
	public static final String APPLICATION_PREFERENCE_OPERATOR_PREFERNCE_ELEMENT = "operatorPreferences";
	public static final String APPLICATION_PREFERENCE_FIELD_TYPE_ELEMENT = "fieldType";
	public static final String APPLICATION_PREFERENCE_FILTER_OPERATORS_ELEMENT = "filterOperators";
	public static final String APPLICATION_PREFERENCE_COMMAND_OPERATORS_ELEMENT = "commandOperators";
	public static final String APPLICATION_PREFERENCE_OPERATOR_ELEMENT = "operator";

	public static final String WORKLIST_ELEMENT = "worklist";
	public static final String WORKLIST_ITEM_ELEMENT = "worklistItem";
	public static final String WORKLIST_REVISIONID_ELEMENT = "revisionId";
	public static final String WORKLIST_ARTIFACT_PATH_ELEMENT = "artifactPath";
	public static final String WORKLIST_ARTIFACT_TYPE_ELEMENT = "artifactType";
	public static final String WORKLIST_REVIEWER_STATUS_ELEMENT = "reviewStatus";
	public static final String WORKLIST_REVIEWER_COMMENTS_ELEMENT = "reviewComments";
	public static final String WORKLIST_DEPLOY_ENVIRONMENTS = "deployEnvironments";
	
	public static final String WORKLIST_DELEGATE_REVISIONS_ELEMENT = "revisions";
	public static final String WORKLIST_DELEGATE_ROLES_ELEMENT = "roles";
	public static final String WORKLIST_DELEGATE_ROLE_ELEMENT = "role";
	
	/**
	 * The name of the managed project which could be same as local project name.
	 */
	public static final String WORKLIST_PROJECT_NAME_ELEMENT = "managedProjectName";
	
	public static final String DT_PAGES_SAVE_ELEMENT = "decisionTableSave";
}
