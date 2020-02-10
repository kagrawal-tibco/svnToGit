/**
 * 
 */
package com.tibco.cep.studio.rms.model;

/**
 * @author aathalye
 *
 */
public class ArtifactAuditTrailRecord extends ArtifactReviewerMetadata {

	/**
	 * @param reviewerUsername
	 * @param reviewerRole
	 * @param newStatus
	 * @param oldStatus
	 * @param reviewerComments
	 */
	public ArtifactAuditTrailRecord(String reviewerUsername,
			                        String reviewerRole, 
			                        String newStatus, 
			                        String oldStatus,
			                        String reviewerComments) {
		super(reviewerUsername, reviewerRole, newStatus, oldStatus, reviewerComments);
	}
}
