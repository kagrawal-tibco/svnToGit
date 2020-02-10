/**
 * 
 */
package com.tibco.cep.studio.rms.model;

/**
 * @author aathalye
 *
 */
public class ArtifactDiffContent {
	
	private long revisionId;
	
	private byte[] artifactContents;
	
	private boolean isMaster;

	public ArtifactDiffContent(long revisionId, 
							   byte[] artifactContents,
			                   boolean isMaster) {
		super();
		this.revisionId = revisionId;
		this.artifactContents = artifactContents;
		this.isMaster = isMaster;
	}

	/**
	 * @return the revisionId
	 */
	public final long getRevisionId() {
		return revisionId;
	}

	/**
	 * @return the artifactContents
	 */
	public final byte[] getArtifactContents() {
		return artifactContents;
	}

	/**
	 * @return the isMaster
	 */
	public final boolean isMaster() {
		return isMaster;
	}
}
