/**
 * 
 */
package com.tibco.cep.rms.artifacts.state;

import java.util.Date;

/**
 * @author aathalye
 *
 */
public class StateContext {
	
	private String project;
	
	private String pathOfArtifact;
	
	/**
	 * The container path (folder, project etc.)
	 */
	private String artifactContainerPath;
	
	private String artifactExtension;

	private Date updateTime;
	
	private String committedVersion;
	/**
	 * @param pathOfArtifact
	 * @param artifactExtension
	 */
	public StateContext(String project, 
			            String pathOfArtifact,
			            String artifactContainerPath,
			            String artifactExtension,
			            Date updateTime,
			            String commitVersion) {
		this.project = project;
		this.pathOfArtifact = pathOfArtifact;
		this.artifactExtension = artifactExtension;
		this.artifactContainerPath = artifactContainerPath;
		if (updateTime != null)
			this.updateTime = new Date(updateTime.getTime());
		this.committedVersion = commitVersion;
	}

	/**
	 * @return the pathOfArtifact
	 */
	public final String getPathOfArtifact() {
		return pathOfArtifact;
	}

	/**
	 * @return the artifactExtension
	 */
	public final String getArtifactExtension() {
		return artifactExtension;
	}

	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}

	/**
	 * @return the artifactContainerPath
	 */
	public final String getArtifactContainerPath() {
		return artifactContainerPath;
	}

	public Date getUpdateTime() {
		if (updateTime != null)
			return new Date(updateTime.getTime());
		return null;
	}

	public String getCommittedVersion() {
		return committedVersion;
	}
	
	
}
