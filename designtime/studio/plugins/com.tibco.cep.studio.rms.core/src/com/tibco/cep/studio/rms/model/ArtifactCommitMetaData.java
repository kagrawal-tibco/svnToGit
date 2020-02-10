package com.tibco.cep.studio.rms.model;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;

/**
 * @author aathalye
 *
 */
public class ArtifactCommitMetaData {
	
	private Artifact artifactToCommit;
	
	private ArtifactOperation artifactOperation;
	
	private String patternId;

	/**
	 * @return the artifactToCommit
	 */
	public final Artifact getArtifact() {
		return artifactToCommit;
	}

	/**
	 * @param artifactToCommit the artifactToCommit to set
	 */
	public final void setArtifact(Artifact artifactToCommit) {
		this.artifactToCommit = artifactToCommit;
	}

	/**
	 * @return the artifactOperation
	 */
	public final ArtifactOperation getArtifactOperation() {
		return artifactOperation;
	}

	/**
	 * @param artifactOperation the artifactOperation to set
	 */
	public final void setArtifactOperation(ArtifactOperation artifactOperation) {
		this.artifactOperation = artifactOperation;
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
}
