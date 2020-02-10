/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import java.util.Date;

/**
 * A value Object containing information about each checkin history entry.
 * @author aathalye
 *
 */
public class ArtifactCheckinHistoryEntry {
	
	private CommittedArtifactDetails[] checkedinArtifactInfos;
	
	private String author;
	
	private long revisionId;
	
	private String checkinComments;
	
	private Date checkinTime;

	/**
	 * 
	 * @param artifactPaths
	 * @param username
	 * @param revisionId
	 * @param checkinComments
	 * @param checkinTime
	 */
	public ArtifactCheckinHistoryEntry(CommittedArtifactDetails[] checkedinArtifactInfos, 
			                           String username,
			                           long revisionId, 
			                           String checkinComments,
			                           Date checkinTime) {
		this.checkedinArtifactInfos = checkedinArtifactInfos;
		this.author = username;
		this.revisionId = revisionId;
		this.checkinComments = checkinComments;
		this.checkinTime = checkinTime;
	}

	/**
	 * @param artifactPaths
	 * @param author
	 * @param revisionId
	 */
	public ArtifactCheckinHistoryEntry(CommittedArtifactDetails[] checkedinArtifactInfos, 
			                           String username,
			                           long revisionId,
			                           Date checkinTime) {
		this(checkedinArtifactInfos, username, revisionId, null, checkinTime);
	}

	/**
	 * @return the artifactPaths
	 */
	public final CommittedArtifactDetails[] getArtifactsCommitted() {
		return checkedinArtifactInfos;
	}

	/**
	 * @return the author
	 */
	public final String getUsername() {
		return author;
	}

	/**
	 * @return the revisionId
	 */
	public final long getRevisionId() {
		return revisionId;
	}

	/**
	 * @return the checkinComments
	 */
	public final String getCheckinComments() {
		return checkinComments;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getCheckinTime() {
		return checkinTime;
	}
}
