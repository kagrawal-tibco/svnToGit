package com.tibco.cep.webstudio.client.i18n;

import java.util.Map;

/**
 * This class routes all RMSMessages properties. It enables the use of dynamic(created post war compile) properties files.<br/>
 * If the requested property is not found in custom properties it simply uses the property strings from locale property files.
 * 
 * @author moshaikh
 *
 */
public class RMSMessages implements I18nMessages {
	
	private Map<String, String> rmsMessages;
	
	public RMSMessages(Map<String, String> rmsMessages) {
		this.rmsMessages = rmsMessages;
	}
	
	private String getPropertyValue(String key, String... replaceValues) {
		return GlobalMessages.getPropertyValue(rmsMessages, key, replaceValues);
	}
	
	public String rmsCheckout_title() {
		return getPropertyValue("rmsCheckout_title");
	}
	
	public String rmsCheckout_mgmtServerUrl() {
		return getPropertyValue("rmsCheckout_mgmtServerUrl");
	}
	
	public String rmsCheckout_getArtifacts() {
		return getPropertyValue("rmsCheckout_getArtifacts");
	}
	
	public String rmsCheckout_projectList() {
		return getPropertyValue("rmsCheckout_projectList");
	}
	
	public String rmsCheckout_selectRecord_message() {
		return getPropertyValue("rmsCheckout_selectRecord_message");
	}
	
	public String rmsCheckout_alreadyCheckedOut_message(String projectName) {
		return getPropertyValue("rmsCheckout_alreadyCheckedOut_message", projectName);
	}
	
	public String rmsCommit_title() {
		return getPropertyValue("rmsCommit_title");
	}
	
	public String rmsCommit_selectRecord_message() {
		return getPropertyValue("rmsCommit_selectRecord_message");
	}
	
	public String rmsCommit_SCSCredentials_message() {
		return getPropertyValue("rmsCommit_SCSCredentials_message");
	}
	
	public String rmsCommit_RenameCheck_message() {
		return getPropertyValue("rmsCommit_RenameCheck_message");
	}
	
	public String rmsRevert_title() {
		return getPropertyValue("rmsRevert_title");
	}
	
	public String rmsRevert_selectRecord_message() {
		return getPropertyValue("rmsRevert_selectRecord_message");
	}
	
	public String rmsHistory_title() {
		return getPropertyValue("rmsHistory_title");
	}
	
	public String rmsHistory_from() {
		return getPropertyValue("rmsHistory_from");
	}
	
	public String rmsHistory_to() {
		return getPropertyValue("rmsHistory_to");
	}
	
	public String rmsHistory_search() {
		return getPropertyValue("rmsHistory_search");
	}
	
	public String rmsHistory_go() {
		return getPropertyValue("rmsHistory_go");
	}
	
	public String rmsHistory_revisionsTitle() {
		return getPropertyValue("rmsHistory_revisionsTitle");
	}
	
	public String rmsHistory_revisionDetailsTitle() {
		return getPropertyValue("rmsHistory_revisionDetailsTitle");
	}
	
	public String rmsArtifact_path() {
		return getPropertyValue("rmsArtifact_path");
	}
	
	public String rmsArtifact_fileType() {
		return getPropertyValue("rmsArtifact_fileType");
	}
	
	public String rmsArtifact_changeType() {
		return getPropertyValue("rmsArtifact_changeType");
	}
	
	public String rmsArtifact_type() {
		return getPropertyValue("rmsArtifact_type");
	}
	
	public String rmsArtifact_hasConflict() {
		return getPropertyValue("rmsArtifact_hasConflict");
	}
	
	public String rmsRevision_id() {
		return getPropertyValue("rmsRevision_id");
	}
	
	public String rmsRevision_action() {
		return getPropertyValue("rmsRevision_action");
	}
	
	public String rmsRevision_author() {
		return getPropertyValue("rmsRevision_author");
	}
	
	public String rmsRevision_date() {
		return getPropertyValue("rmsRevision_date");
	}
	
	public String rmsHistory_searchEmptyText() {
		return getPropertyValue("rmsHistory_searchEmptyText");
	}
	
	public String rmsHistory_gridHeader_revision() {
		return getPropertyValue("rmsHistory_gridHeader_revision");
	}
	
	public String rmsHistory_gridHeader_username() {
		return getPropertyValue("rmsHistory_gridHeader_username");
	}
	
	public String rmsRevisionDetails_status() {
		return getPropertyValue("rmsRevisionDetails_status");
	}
	
	public String rmsDeployable_title() {
		return getPropertyValue("rmsDeployable_title");
	}
	
	public String rmsDeployable_user() {
		return getPropertyValue("rmsDeployable_user");
	}
	
	public String rmsDeployable_version() {
		return getPropertyValue("rmsDeployable_version");
	}
	
	public String rmsDeployable_globalVariables() {
		return getPropertyValue("rmsDeployable_globalVariables");
	}
	
	public String rmsDeployable_classes() {
		return getPropertyValue("rmsDeployable_classes");
	}
	
	public String rmsDeployable_debugInfo() {
		return getPropertyValue("rmsDeployable_debug_info");
	}
	
	public String rmsDeployable_additionalInfoTitle() {
		return getPropertyValue("rmsDeployable_additional_info");
	}
	
	public String rmsDeployable_generateInProgress() {
		return getPropertyValue("rmsDeployable_generateInProgress");
	}
	
	public String rmsWorklist_approveRejectAllTitle() {
		return getPropertyValue("rmsWorklist_approveRejectAllTitle");
	}
	
	public String rmsWorklist_approveReject() {
		return getPropertyValue("rmsWorklist_approveReject");
	}
	
	public String rmsWorklist_optionApprove() {
		return getPropertyValue("rmsWorklist_optionApprove");
	}
	
	public String rmsWorklist_optionReject() {
		return getPropertyValue("rmsWorklist_optionReject");
	}
	
	public String rmsAuditTrail_reviewer() {
		return getPropertyValue("rmsAuditTrail_reviewer");
	}
	
	public String rmsAuditTrail_role() {
		return getPropertyValue("rmsAuditTrail_role");
	}
	
	public String rmsAuditTrail_oldStatus() {
		return getPropertyValue("rmsAuditTrail_oldStatus");
	}
	
	public String rmsAuditTrail_newStatus() {
		return getPropertyValue("rmsAuditTrail_newStatus");
	}
	
	public String rmsAuditTrail_time() {
		return getPropertyValue("rmsAuditTrail_time");
	}
	
	public String rmsWorklist_action() {
		return getPropertyValue("rmsWorklist_action");
	}
	
	public String rmsUpdate_title() {
		return getPropertyValue("rmsUpdate_title");
	}
	
	public String rmsWorklist_title() {
		return getPropertyValue("rmsWorklist_title");
	}
	
	public String rmsWorklist_delegateTo_text() {
		return getPropertyValue("rmsWorklist_delegateTo_text");
	}
	
	public String rmsWorklist_deleteItem() {
		return getPropertyValue("rmsWorklist_deleteItem");
	}
	
	public String rmsUpdate_overwriteNote() {
		return getPropertyValue("rmsUpdate_overwriteNote");
	}
	
	public String rmsUpdate_selectRecord_message() {
		return getPropertyValue("rmsUpdate_selectRecord_message");
	}
	
	public String rms_selectProjectToUpdate_message() {
		return getPropertyValue("rms_selectProjectToUpdate_message");
	}
	
	public String rms_selectProjectToCommit_message() {
		return getPropertyValue("rms_selectProjectToCommit_message");
	}
	
	public String rms_selectProjectToRevert_message() {
		return getPropertyValue("rms_selectProjectToRevert_message");
	}
	
	public String rms_selectArtifactToViewHistory_message() {
		return getPropertyValue("rms_selectArtifactToViewHistory_message");
	}
	
	public String rms_cannotDeployArtifact_message() {
		return getPropertyValue("rms_cannotDeployArtifact_message");
	}
	
	public String rmsWorklist_selectAction() {
		return getPropertyValue("rmsWorklist_selectAction");
	}
	
	public String rmsWorklist_delegateSelected() {
		return getPropertyValue("rmsWorklist_delegateSelected");
	}
	
	public String rmsWorklist_deleteSelected() {
		return getPropertyValue("rmsWorklist_deleteSelected");
	}
	
	public String rmsWorklist_buildAndDeployInProgress() {
		return getPropertyValue("rmsWorklist_buildAndDeployInProgress");
	}
	
	public String message_allArtifactsCheckedout() {
		return getPropertyValue("message_allArtifactsCheckedout");
	}
	
	public String rmsWorklist_gridHeader_revision() {
		return getPropertyValue("rmsWorklist_gridHeader_revision");
	}
	
	public String rmsWorklist_gridHeader_username() {
		return getPropertyValue("rmsWorklist_gridHeader_username");
	}
	
	public String rmsWorklist_gridHeader_project() {
		return getPropertyValue("rmsWorklist_gridHeader_project");
	}
	
	public String rmsWorklist_gridHeader_commitComment() {
		return getPropertyValue("rmsWorklist_gridHeader_commitComment");
	}
	
	public String rmsWorklist_gridHeader_commitTime() {
		return getPropertyValue("rmsWorklist_gridHeader_commitTime");
	}
	
	public String rmsWorklist_gridHeader_columnStatus() {
		return getPropertyValue("rmsWorklist_gridHeader_columnStatus");
	}
	
	public String rmsWorklist_gridHeader_comments() {
		return getPropertyValue("rmsWorklist_gridHeader_comments");
	}
	
	public String rmsWorklist_gridHeader_environments() {
		return getPropertyValue("rmsWorklist_gridHeader_environments");
	}
	
	public String servermessage_loginFailed() {
		return getPropertyValue("servermessage_loginFailed");
	}
	
	public String servermessage_alreadyLoggedIn(String user, String address) {
		return getPropertyValue("servermessage_alreadyLoggedIn", user == null ? "" : user, address == null ? "" : address);
	}
	
	public String servermessage_not_loggedin() {
		return getPropertyValue("servermessage_not_loggedin");
	}
	
	public String servermessage_resourceDelete_denied(String projectName) {
		return getPropertyValue("servermessage_resourceDelete_denied", projectName == null ? "" : projectName);
	}
	
	public String servermessage_DTadd_denied(String projectName) {
		return getPropertyValue("servermessage_DTadd_denied", projectName == null ? "" : projectName);
	}
	
	public String servermessage_RTIadd_denied(String projectName) {
		return getPropertyValue("servermessage_RTIadd_denied", projectName == null ? "" : projectName);
	}
	
	public String servermessage_generateDeployable_denied(String projectName) {
		return getPropertyValue("servermessage_generateDeployable_denied", projectName == null ? "" : projectName);
	}
	
	public String servermessage_generateDeployable_failedClasses(
			String projectName) {
		return getPropertyValue("servermessage_generateDeployable_failedClasses", projectName == null ? "" : projectName);
	}
	
	public String servermessage_generateDeployable_failedEar(String projectName) {
		return getPropertyValue("servermessage_generateDeployable_failedEar", projectName == null ? "" : projectName);
	}
	
	public String servermessage_checkin_successful(String revisionId) {
		return getPropertyValue("servermessage_checkin_successful", revisionId == null ? "" : revisionId);
	}
	
	public String servermessage_worklist_delegate_successful(String revisionIds, String roles) {
		return getPropertyValue("servermessage_worklist_delegate_successful", revisionIds == null ? "" : revisionIds, roles == null ? "" : roles);
	}
	
	public String servermessage_worklist_delete_successful(String revisionIds) {
		return getPropertyValue("servermessage_worklist_delete_successful", revisionIds == null ? "" : revisionIds);
	}
	
	public String servermessage_worklist_delete_notAllowed() {
		return getPropertyValue("servermessage_worklist_delete_notAllowed");
	}
	
	public String servermessage_revert_successful() {
		return getPropertyValue("servermessage_revert_successful");
	}
	
	public String servermessage_generateDeployable_noSupportedArtifacts() {
		return getPropertyValue("servermessage_generateDeployable_noSupportedArtifacts");
	}
	
	public String rms_artifactOperation_added() {
		return getPropertyValue("rms_artifactOperation_added");
	}
	
	public String rms_artifactOperation_modified() {
		return getPropertyValue("rms_artifactOperation_modified");
	}
	
	public String rms_artifactOperation_deleted() {
		return getPropertyValue("rms_artifactOperation_deleted");
	}
	
	public String rms_artifactStatus_committed() {
		return getPropertyValue("rms_artifactStatus_committed");
	}
	
	public String rms_artifactStatus_approve() {
		return getPropertyValue("rms_artifactStatus_approve");
	}
	
	public String rms_artifactStatus_reject() {
		return getPropertyValue("rms_artifactStatus_reject");
	}
	
	public String rms_artifactStatus_buildanddeploy() {
		return getPropertyValue("rms_artifactStatus_buildanddeploy");
	}
	
	public String message_noArtifactsLocked() {
		return getPropertyValue("message_noArtifactsLocked");
	}
	
	public String rmsManageLocks_title() {
		return getPropertyValue("rmsManageLocks_title");
	}
	
	public String rmsArtifact_lockOwner() {
		return getPropertyValue("rmsArtifact_lockOwner");
	}
	
	public String rmsArtifact_lockedTime() {
		return getPropertyValue("rmsArtifact_lockedTime");
	}
	
	public String button_UnLock() {
		return getPropertyValue("button_UnLock");
	}
}
