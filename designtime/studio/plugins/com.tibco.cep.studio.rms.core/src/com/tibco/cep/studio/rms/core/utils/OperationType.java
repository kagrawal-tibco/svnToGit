/**
 * 
 */
package com.tibco.cep.studio.rms.core.utils;

/**
 * @author aathalye
 *
 */
public enum OperationType {
	
	/**
	 * Operation indicating authentication process.
	 */
	LOGIN, 
	
	/**
	 * Operation indicating fetching list of RMS served projects.
	 */
	PROJECTS_LIST, 
	
	/**
	 * Operation indicating fetching artifacts belonging to certain project.
	 */
	PROJECT_ARTIFACTS_LIST,
	
	/**
	 * Operation indicating fetching contents of a certain artifact (master).
	 */
	ARTIFACT_CONTENTS,
	
	/**
	 * Operation indicating checking in certain artifact (individual artifact).
	 */
	CHECKIN_ARTIFACT,
	
	/**
	 * Operation indicating completion of a checkin request.
	 */
	CHECKIN_COMPLETE,

	/**
	 * Operation indicating fetching tasks list.
	 */
	WORKLISTS_IDS,
	
	/**
	 * Operation indicating fetching details of a review task.
	 */
	WORK_DETAILS,
	
	/**
	 * Operation indicating status changes upon review.
	 */
	STATUS_CHANGE,
	
	/**
	 * Operation indicating fetching audit trail of review for an artifact.
	 */
	AUDIT_TRAIL,
	LCK_REQUEST,
	
	/**
	 * Operation indicating refreshing details of committed artifact.
	 */
	COMMITTED_ARTIFACT_DETAILS,
	
	/**
	 * Operation indicating refreshing access config file for a project.
	 */
	ACCESS_CONFIG_REFRESH,
	
	/**
	 * Operation indicating generation of ear for a project managed by RMS.
	 */
	GENERATE_DEPLOYABLE,
	
	/**
	 * Operation indicating fetching checkin history for one/more artifacts.
	 */
	CHECKIN_HISTORY,
	
	/**
	 * Operation indicating comparison of revisions/artifacts.
	 */
	COMPARE_REVISIONS,
	
	/**
	 * Operation indicating fetching contents of an artifact at certain revision.
	 */
	REVISION_CONTENTS,
	
	/**
	 * Operation indicating delegation of workitem to one or more roles.
	 */
	WORKITEM_DELEGATION,
	
	/**
	 * Operation indicating logout process.
	 */
	LOGOUT;
}
