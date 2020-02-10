/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

/**
 * Model to represent worklist items
 * 
 * @author Vikram Patil
 */
public class WorklistItem {
	private String revisionId;
	private String artifactPath;
	private String reviewStatus;
	private String reviewComments;
	private String artifactType;
	private String projectName;
	private String deployEnvironments;

	public WorklistItem(String revisionId,
						String artifactPath,
						String artifactType,
						String reviewStatus,
						String reviewComments,
						String projectName) {
		super();
		this.revisionId = revisionId;
		this.artifactPath = artifactPath;
		this.reviewStatus = reviewStatus;
		this.reviewComments = reviewComments;
		this.artifactType = artifactType;
		this.projectName = projectName;
	}

	public String getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}

	public String getArtifactPath() {
		return artifactPath;
	}

	public void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

	public String getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getDeployEnvironments() {
		return deployEnvironments;
	}

	public void setDeployEnvironments(String deployEnvironments) {
		this.deployEnvironments = deployEnvironments;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}

		WorklistItem workList = (WorklistItem) obj;
		return (this.revisionId.equals(workList.getRevisionId())
				&& this.artifactPath.equals(workList.getArtifactPath()) && this.artifactType.equals(workList
				.getArtifactType()) && this.projectName.equals(workList.getProjectName()));
	}
}
