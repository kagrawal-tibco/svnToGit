/**
 * 
 */
package com.tibco.cep.studio.rms.model;

/**
 * @author aathalye
 *
 */
public class ArtifactReviewerMetadata {
	
	/**
	 * Username reviewing this
	 */
	private String reviewerUsername;
	
	/**
	 * Status changed by the reviewer
	 */
	private String newStatus;
	
	/**
	 * Old status of the artifact
	 */
	private String oldStatus;
	
	private String reviewerComments;
	
	/**
	 * Role of the reviewer
	 */
	private String reviewerRole;

	/**
	 * @return the reviewerUsername
	 */
	public final String getReviewerUsername() {
		return reviewerUsername;
	}
	

	/**
	 * @return the reviewerRole
	 */
	public final String getReviewerRole() {
		return reviewerRole;
	}


	/**
	 * @return the newStatus
	 */
	public final String getNewStatus() {
		return newStatus;
	}

	/**
	 * @return the oldStatus
	 */
	public final String getOldStatus() {
		return oldStatus;
	}

	/**
	 * Do not return null here.
	 * @return the reviewerComments
	 */
	public final String getReviewerComments() {
		return reviewerComments = (reviewerComments == null) ? "" : reviewerComments;
	}

	/**
	 * @param reviewerUsername
	 * @param newStatus
	 * @param oldStatus
	 * @param reviewerComments
	 */
	public ArtifactReviewerMetadata(String reviewerUsername, 
			                        String reviewerRole,
			                        String newStatus,
			                        String oldStatus, 
			                        String reviewerComments) {
		this.reviewerUsername = reviewerUsername;
		this.reviewerRole = reviewerRole;
		this.newStatus = newStatus;
		this.oldStatus = oldStatus;
		this.reviewerComments = reviewerComments;
	}
}
