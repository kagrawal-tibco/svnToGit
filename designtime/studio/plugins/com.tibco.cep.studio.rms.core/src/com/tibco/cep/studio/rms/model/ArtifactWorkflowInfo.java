/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import java.util.List;

/**
 * Workflow information for the role
 * @author aathalye
 *
 */
public class ArtifactWorkflowInfo {
	
	/**
	 * The role whose information is contained
	 */
	private String role;
	
	/**
	 * All the stages the role can view
	 */
	private List<String> stages;

	/**
	 * @return the role
	 */
	public final String getRole() {
		return role;
	}

	/**
	 * @return the stages
	 */
	public final List<String> getStages() {
		return stages;
	}

	/**
	 * @param role
	 * @param stages
	 */
	public ArtifactWorkflowInfo(String role, List<String> stages) {
		this.role = role;
		this.stages = stages;
	}

	/**
	 * 
	 */
	public ArtifactWorkflowInfo() {
		
	}

	/**
	 * @param role the role to set
	 */
	public final void setRole(String role) {
		this.role = role;
	}

	/**
	 * @param stages the stages to set
	 */
	public final void setStages(List<String> stages) {
		this.stages = stages;
	}
	
	
}
