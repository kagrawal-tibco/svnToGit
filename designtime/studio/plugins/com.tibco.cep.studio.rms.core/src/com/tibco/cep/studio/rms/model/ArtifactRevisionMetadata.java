/**
 * 
 */
package com.tibco.cep.studio.rms.model;

/**
 * @author aathalye
 *
 */
public class ArtifactRevisionMetadata extends ArtifactCommitMetaData {
	
	/**
	 * The revision id we are interested in
	 */
	private String revisionId;
	
	private String projectName;

	/**
	 * @return the revisionId
	 */
	public final String getRevisionId() {
		return revisionId;
	}

	/**
	 * @param revisionId the revisionId to set
	 */
	public final void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
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
}
