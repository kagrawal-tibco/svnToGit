/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author aathalye
 *
 */
public class ArtifactReviewTask {
	
	private ArtifactReviewTaskSummary parent;
	/**
	 * Same as revision id of checkin
	 */
	private String taskId;
	
	private String checkinComments;
	
	/**
	 * Hidden patternid
	 */
	private String patternId;
	
	/**
	 * The username for the user making the checkin
	 */
	private String username;
	
	private boolean changed;
	
	/**
	 * The project name
	 */
	private String projectName;
	
	private Date checkinTime;
	
	/**
	 * All artifacts committed in this revision
	 */
	private List<CommittedArtifactDetails> allCommittedArtifacts;

	/**
	 * @return the taskId
	 */
	public final String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public final void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the allCommittedArtifacts
	 */
	public final List<CommittedArtifactDetails> getAllCommittedArtifacts() {
		if (allCommittedArtifacts == null) {
			allCommittedArtifacts = new ArrayList<CommittedArtifactDetails>();
		}
		return allCommittedArtifacts;
	}

	/**
	 * @param allCommittedArtifacts the allCommittedArtifacts to set
	 */
	public final void setAllCommittedArtifacts(List<CommittedArtifactDetails> allCommittedArtifacts) {
		this.allCommittedArtifacts = allCommittedArtifacts;
	}

	public ArtifactReviewTaskSummary getParent() {
		return parent;
	}

	public void setParent(ArtifactReviewTaskSummary parent) {
		this.parent = parent;
	}

	/**
	 * @return the checkinComments
	 */
	public final String getCheckinComments() {
		return checkinComments;
	}

	/**
	 * @param checkinComments the checkinComments to set
	 */
	public final void setCheckinComments(String checkinComments) {
		this.checkinComments = checkinComments;
	}

	/**
	 * @return the patternId
	 */
	public final String getPatternId() {
		return patternId;
	}

	/**
	 * @param patternId the patternId to set
	 */
	public final void setPatternId(String patternId) {
		this.patternId = patternId;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return the projectName
	 */
	public final String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public final void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the checkinTime
	 */
	public final Date getCheckinTime() {
		return checkinTime;
	}

	/**
	 * @param checkinTime the checkinTime to set
	 */
	public final void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}
	
	
	
}