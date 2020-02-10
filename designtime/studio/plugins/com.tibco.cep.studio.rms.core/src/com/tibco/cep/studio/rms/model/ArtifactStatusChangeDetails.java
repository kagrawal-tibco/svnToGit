/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import java.util.List;

/**
 * Wrapper for multiple state changes to be done in one shot
 * @author aathalye
 *
 */
public class ArtifactStatusChangeDetails {
	
	/**
	 * The revision id in which the checkin was made
	 */
	private long revisionId;
	
	/**
	 * Project name
	 */
	private String projectName;
	
	/**
	 * PatternId required for exId
	 */
	private String patternId;
	
	/**
	 * List of one or more dirty artifacts
	 */
	private List<ArtifactStatusChangeMetadata> artifactsStatusCangeMetadata;

	/**
	 * @param revisionId
	 * @param projectName
	 */
	public ArtifactStatusChangeDetails(long revisionId,
			                           String patternId,
			                           String projectName,
			                           List<ArtifactStatusChangeMetadata> artifactsStatusCangeMetadata) {
		this.revisionId = revisionId;
		this.patternId = patternId;
		this.projectName = projectName;
		this.artifactsStatusCangeMetadata = artifactsStatusCangeMetadata;
	}

	/**
	 * @return the revisionId
	 */
	public final long getRevisionId() {
		return revisionId;
	}

	/**
	 * @return the projectName
	 */
	public final String getProjectName() {
		return projectName;
	}
	

	/**
	 * @return the patternId
	 */
	public final String getPatternId() {
		return patternId;
	}

	/**
	 * @return the artifactsStatusCangeMetadata
	 */
	public final List<ArtifactStatusChangeMetadata> getArtifactsStatusCangeMetadata() {
		return artifactsStatusCangeMetadata;
	}
}
