/**
 * 
 */
package com.tibco.be.ws.scs;

/**
 * SVN Entry used to store all the details of a single artifact in a give SVN commit transaction
 * 
 * @author vpatil
 */
public class SCSCommitEntry {
	
	private String projectName;
	private String artifactPath;
	private String artifactExtn;
	private String comments;
	private byte[] artifactContents;
	private boolean deleted;
	
	public SCSCommitEntry(String projectName, String artifactPath,
			String artifactExtn, String comments, byte[] artifactContents, boolean deleted) {
		this.projectName = projectName;
		this.artifactPath = artifactPath;
		this.artifactExtn = artifactExtn;
		this.comments = comments;
		this.artifactContents = artifactContents;
		this.deleted = deleted;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getArtifactPath() {
		return artifactPath;
	}

	public String getArtifactExtn() {
		return artifactExtn;
	}

	public String getComments() {
		return comments;
	}

	public byte[] getArtifactContents() {
		return artifactContents;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
