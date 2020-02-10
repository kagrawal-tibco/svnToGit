/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

/**
 * Model to represent Projects managed under the webstudio server
 * 
 * @author Vikram Patil
 */
public class ManagedProject {

	private String projectName;

	public ManagedProject(String projectName) {
		super();
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}

		ManagedProject managedProject = (ManagedProject) obj;
		return (this.getProjectName().equals(managedProject.getProjectName()));
	}
}
