/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

/**
 * Model to represent Artifacts on the Dashboard
 * 
 * @author Vikram Patil
 */
public class DashboardArtifact {
	
	private String projectName;
	private String artifactPath;
	
	public DashboardArtifact() {
	}
	
	public DashboardArtifact(String projectName, String artifactPath) {
		super();
		this.projectName = projectName;
		this.artifactPath = artifactPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getArtifactPath() {
		return artifactPath;
	}

	public void setArtifactPath(String artifactPath) {
		this.artifactPath = artifactPath;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}

		DashboardArtifact dashboardArtifact = (DashboardArtifact) obj;
		return (this.getProjectName().equals(dashboardArtifact.getProjectName()) && this.getArtifactPath().equals(dashboardArtifact.getArtifactPath()));
	}
}
