/**
 * 
 */
package com.tibco.cep.studio.rms.core.utils;

import java.text.SimpleDateFormat;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author aathalye
 *
 */
public class ArtifactsManagerConstants {
	
	public static final String ARTIFACT_NS = "www.tibco.com/be/ontology/Approval/Concepts/ArtifactsConcepts/AMS_C_Artifact";
	public static final String ARTIFACT_COMMIT_NS = "www.tibco.com/be/ontology/Approval/Concepts/ArtifactsConcepts/AMS_C_ArtifactCommited";
	public static final String ARTIFACT_REVIEWER_HISTORY_NS = "www.tibco.com/be/ontology/Approval/Concepts/ArtifactsConcepts/AMS_C_ReviewerHistory";
	public static final String ARTIFACT_REVIEW_SUMMARY_NS = "www.tibco.com/be/rms/ArtifactStatusChangePayload";
	public static final String ARTIFACT_COMMIT_COMPLETE_NS = "www.tibco.com/be/ontology/Approval/Concepts/ArtifactsConcepts/AMS_C_Checkin";
	public static final String PROJECTS_LIST_RESPONSE_NS = "www.tibco.com/be/ontology/Approval/Concepts/ArtifactsConcepts/AMS_C_ServedProjects";
	public static final String ARTIFACTS_TASKS_IDS_LIST_RESPONSE_NS = "www.tibco.com/be/rms/ArtifactsWorklist";
	public static final String ARTIFACTS_TASKS_DELEGATION_ROLES_NS = "www.tibco.com/be/ontology/AAA/Concepts/AMS_C_ConfiguredRoles";
	public static final String PROJECT_ARTIFACT_NAMES_COLL_RESPONSE_NS = "www.tibco.com/be/ontology/Approval/Concepts/ArtifactsConcepts/AMS_C_ApprovedArtifacts";
	public static final String PROJECT_ARTIFACT_NAMES_RESPONSE_NS = "www.tibco.com/be/ontology/Approval/Events/ArtifactEvents/Checkout/AMS_E_RetrieveArtifactNamesResponseEvent";
	public static final String ARTIFACT_AUDIT_TRAIL_RESPONSE_NS = "www.tibco.com/be/rms/AuditTrail";
	public static final String ARTIFACT_CHECKIN_HISTORY_NS = "www.tibco.com/be/rms/ArtifactCheckinHistory";
	public static final String ARTIFACT_REVISIONS_DIFF_NS = "www.tibco.com/be/rms/ArtifactsDiff";
	public static final String ERROR_NS = "www.tibco.com/be/ontology/Approval/Events/ArtifactEvents/AMS_E_ErrorEvent";
	
	public static final String PROJECT_ARTIFACT_NAMES_RECORD_NS = "www.tibco.com/be/ontology/WebStudio/Core/Concepts/DataSources/Records/WS_C_ProjectArtifactNamesDataSourceRecord";
	
	public static final String ELEM_ARTIFACT_APPLICABLE_STAGES_NS = "www.tibco.com/be/rms/RoleWorkflowStages";
	public static final String ELEM_ARTIFACT_APPLICABLE_STAGES = "AMS_C_ApplicableStages";
	public static final ExpandedName EX_ARTIFACT_APPLICABLE_STAGES = 
		ExpandedName.makeName(ELEM_ARTIFACT_APPLICABLE_STAGES_NS, ELEM_ARTIFACT_APPLICABLE_STAGES);
	
	public static final String ARTIFACT_WORKFLOW_STAGE_NS = ELEM_ARTIFACT_APPLICABLE_STAGES_NS;
	public static final String ELEM_ARTIFACT_APPLICABLE_STAGES_STAGE = "stage";
	public static final ExpandedName EX_ARTIFACT_APPLICABLE_STAGES_STAGE = 
		ExpandedName.makeName(ARTIFACT_WORKFLOW_STAGE_NS, ELEM_ARTIFACT_APPLICABLE_STAGES_STAGE);
	
	
	public static final String ATTR_ARTIFACT_APPLICABLE_STAGES_STAGE_NAME = "name";
	public static final ExpandedName EX_ATTR_ARTIFACT_APPLICABLE_STAGES_STAGE_NAME = 
		ExpandedName.makeName(ELEM_ARTIFACT_APPLICABLE_STAGES_NS, ATTR_ARTIFACT_APPLICABLE_STAGES_STAGE_NAME);
	
	public static final String ELEM_ARTIFACT_PATH = "artifactPath";
	public static final ExpandedName EX_ARTIFACT_PATH = ExpandedName.makeName(ELEM_ARTIFACT_PATH);
	
	public static final String ELEM_ARTIFACT_TYPE = "artifactType";
	public static final ExpandedName EX_ARTIFACT_TYPE = ExpandedName.makeName(ELEM_ARTIFACT_TYPE);
	
	public static final String ELEM_ARTIFACT_EXTENSION = "artifactFileExtension";
	public static final ExpandedName EX_ARTIFACT_EXTENSION = ExpandedName.makeName(ELEM_ARTIFACT_EXTENSION);
	
	public static final String ELEM_REVISION_ID = "revisionId";
	public static final ExpandedName EX_REVISION_ID = ExpandedName.makeName(ELEM_REVISION_ID);
	
	public static final String ELEM_ARTIFACT_STATUS = "status";
	public static final ExpandedName EX_ARTIFACT_STATUS = ExpandedName.makeName(ELEM_ARTIFACT_STATUS);
	
	public static final String ELEM_ARTIFACT_CONTENT = "artifactContent";
	public static final ExpandedName EX_ARTIFACT_CONTENT = ExpandedName.makeName(ELEM_ARTIFACT_CONTENT);

	public static final String ELEM_ARTIFACT_UPDATE_TIME = "artifactUpdateTime";
	public static final ExpandedName EX_ARTIFACT_UPDATE_TIME = ExpandedName.makeName(ELEM_ARTIFACT_UPDATE_TIME);

	public static final String ELEM_ARTIFACT_COMMIT_VERSION = "commitVersion";
	public static final ExpandedName EX_ARTIFACT_COMMIT_VERSION = ExpandedName.makeName(ELEM_ARTIFACT_COMMIT_VERSION);

	public static final String ELEM_EVENT = "event";
	public static final String ELEM_PAYLOAD = "payload";
	public static final ExpandedName EX_PAYLOAD = ExpandedName.makeName(ELEM_PAYLOAD);
	
	public static final String ELEM_AMS_C_Artifact = "AMS_C_Artifact";
	public static final ExpandedName EX_ARTIFACT_NAME = ExpandedName.makeName(ARTIFACT_NS, ELEM_AMS_C_Artifact);
	
	public static final String ELEM_AMS_C_ServedProjects = "AMS_C_ServedProjects";
	public static final ExpandedName EX_AMS_C_SERVEDPROJECTS_NAME = ExpandedName.makeName(PROJECTS_LIST_RESPONSE_NS, ELEM_AMS_C_ServedProjects);
	
	public static final String ELEM_APPROVED_ARTIFACTS_COLL = "approvedArtifacts";
	public static final ExpandedName EX_APPROVED_ARTIFACTS_COLL = ExpandedName.makeName(PROJECT_ARTIFACT_NAMES_COLL_RESPONSE_NS, ELEM_APPROVED_ARTIFACTS_COLL);
	
	public static final String ELEM_APPROVED_ARTIFACT_RECORD = "WS_C_ProjectArtifactNamesDataSourceRecord";
	public static final ExpandedName EX_APPROVED_ARTIFACT_RECORD  = ExpandedName.makeName(PROJECT_ARTIFACT_NAMES_RECORD_NS, ELEM_APPROVED_ARTIFACT_RECORD);
	
	public static final String ELEM_PROJECT_NAMES = "projectNames";
	public static final ExpandedName EX_PROJECT_NAMES = ExpandedName.makeName(ELEM_PROJECT_NAMES);
	
	public static final String ELEM_ERROR = "error";
	public static final ExpandedName EX_ERROR = ExpandedName.makeName(ERROR_NS, ELEM_ERROR);
	
	public static final String ELEM_ERROR_CODE = "errorCode";
	public static final ExpandedName EX_ERROR_CODE = ExpandedName.makeName(ELEM_ERROR_CODE);
	
	public static final String ELEM_ERROR_STRING = "errorString";
	public static final ExpandedName EX_ERROR_STRING = ExpandedName.makeName(ELEM_ERROR_STRING);
	
	public static final String ELEM_ERROR_DETAIL = "errorDetail";
	public static final ExpandedName EX_ERROR_DETAIL = ExpandedName.makeName(ELEM_ERROR_DETAIL);
	
	public static final String ELEM_COMMITTED_ARTIFACTS = "CommittedArtifacts";
	public static final ExpandedName EX_COMMITTED_ARTIFACTS = ExpandedName.makeName(ELEM_COMMITTED_ARTIFACTS);
	
	public static final String ELEM_COMMITTED_ARTIFACT = "CommittedArtifact";
	public static final ExpandedName EX_COMMITTED_ARTIFACT = ExpandedName.makeName(ARTIFACT_COMMIT_NS, ELEM_COMMITTED_ARTIFACT);
	
	public static final String ELEM_COMMITTED_ARTIFACT_OPERATION = "operation";
	public static final ExpandedName EX_COMMITTED_ARTIFACT_OPERATION = ExpandedName.makeName(ELEM_COMMITTED_ARTIFACT_OPERATION);
	
	public static final String ELEM_ACCESS_CONTENTS_REFRESH = "fileContents";
	public static final ExpandedName EX_ACCESS_CONTENTS_REFRESH = ExpandedName.makeName(ELEM_ACCESS_CONTENTS_REFRESH);
	
	public static final String ELEM_TASK = "Task";
	public static final ExpandedName EX_TASK = 
		ExpandedName.makeName(ARTIFACTS_TASKS_IDS_LIST_RESPONSE_NS, ELEM_TASK);
	
	
	public static final String ELEM_AUDIT_TRAIL = "AuditTrail";
	public static final ExpandedName EX_AUDIT_TRAIL = 
		ExpandedName.makeName(ARTIFACT_AUDIT_TRAIL_RESPONSE_NS, ELEM_AUDIT_TRAIL);
	
	public static final String ELEM_REVIEWER_HISTORY = "ReviewerHistory";
	public static final ExpandedName EX_REVIEWER_HISTORY = 
		ExpandedName.makeName(ARTIFACT_AUDIT_TRAIL_RESPONSE_NS, ELEM_REVIEWER_HISTORY);
	
	public static final String ELEM_ARTIFACT_CHECKIN_HISTORY = "ArtifactCheckinHistory";
	public static final ExpandedName EX_ARTIFACT_CHECKIN_HISTORY = 
		ExpandedName.makeName(ARTIFACT_CHECKIN_HISTORY_NS, ELEM_ARTIFACT_CHECKIN_HISTORY);
	
	public static final String ELEM_HISTORY_ENTRY = "HistoryEntry";
	public static final ExpandedName EX_HISTORY_ENTRY = 
		ExpandedName.makeName(ARTIFACT_CHECKIN_HISTORY_NS, ELEM_HISTORY_ENTRY);
	
	public static final String ELEM_DIFFED_ARTIFACTS = "DiffedArtifacts";
	public static final ExpandedName EX_DIFFED_ARTIFACTS = 
		ExpandedName.makeName(ARTIFACT_REVISIONS_DIFF_NS, ELEM_DIFFED_ARTIFACTS);
	
	public static final String ELEM_REMOTE_ARTIFACT_CONTENT = "RemoteArtifactContent";
	public static final ExpandedName EX_REMOTE_ARTIFACT_CONTENT = 
		ExpandedName.makeName(ARTIFACT_REVISIONS_DIFF_NS, ELEM_REMOTE_ARTIFACT_CONTENT);
	
	public static final String ELEM_MASTER_ARTIFACT_CONTENT = "MasterArtifactContent";
	public static final ExpandedName EX_MASTER_ARTIFACT_CONTENT = 
		ExpandedName.makeName(ARTIFACT_REVISIONS_DIFF_NS, ELEM_MASTER_ARTIFACT_CONTENT);
	
	public static final String ELEM_ARTIFACT_COMMITTED = "AMS_C_ArtifactCommited";
	public static final ExpandedName EX_ARTIFACT_COMMIT = 
		ExpandedName.makeName(ARTIFACT_COMMIT_NS, ELEM_ARTIFACT_COMMITTED);
	
	public static final String ELEM_ARTIFACT_COMMIT_COMPLETE = "AMS_C_Checkin";
	public static final ExpandedName EX_ARTIFACT_COMMIT_COMPLETE = 
		ExpandedName.makeName(ARTIFACT_COMMIT_COMPLETE_NS, ELEM_ARTIFACT_COMMIT_COMPLETE);
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
}
