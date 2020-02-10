/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import java.util.Date;
import java.util.List;

import com.tibco.cep.studio.rms.artifacts.Artifact;

/**
 * @author aathalye
 *
 */
public class ArtifactCommitCompletionMetaData {
	
	/**
	 * All artifacts information
	 */
	private List<Artifact> artifacts;
	
	private String username;
	
	private String checkinComments;
	
	private String patternId;
	
	private Date checkinTime;

	
	/**
	 * @return the artifact
	 */
	public final List<Artifact> getArtifacts() {
		return artifacts;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @return the checkinComments
	 */
	public final String getCheckinComments() {
		return checkinComments;
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
	 * @param username the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param checkinComments the checkinComments to set
	 */
	public final void setCheckinComments(String checkinComments) {
		this.checkinComments = checkinComments;
	}

	/**
	 * @param artifacts the artifacts to set
	 */
	public final void setArtifacts(List<Artifact> artifacts) {
		this.artifacts = artifacts;
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
