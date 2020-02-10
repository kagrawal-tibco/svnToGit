/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;

/**
 * Represent each dirty artifact inside a checkin whose status has to be changed. 
 * @author aathalye
 *
 */
public class ArtifactStatusChangeMetadata {
	
	/**
	 * The artifact whose status has to be changed
	 */
	private Artifact artifact;
	
	private ArtifactReviewerMetadata reviewerInfo;
	
	
	/**
	 * The operation performed on the artifact during checkin
	 */
	private ArtifactOperation artifactOperation;
	
	

	/**
	 * @param artifact
	 * @param reviewerInfo
	 */
	public ArtifactStatusChangeMetadata(Artifact artifact,
										ArtifactOperation artifactOperation,
			                            ArtifactReviewerMetadata reviewerInfo) {
		this.artifact = artifact;
		this.reviewerInfo = reviewerInfo;
		this.artifactOperation = artifactOperation;
	}


	/**
	 * @return the artifact
	 */
	public final Artifact getArtifact() {
		return artifact;
	}

	
	/**
	 * @return the reviewerInfo
	 */
	public final ArtifactReviewerMetadata getReviewerInfo() {
		return reviewerInfo;
	}



	/**
	 * @return the artifactOperation
	 */
	public final ArtifactOperation getArtifactOperation() {
		return artifactOperation;
	}
}
