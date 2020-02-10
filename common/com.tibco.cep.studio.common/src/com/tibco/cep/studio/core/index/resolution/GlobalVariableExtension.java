package com.tibco.cep.studio.core.index.resolution;

import com.tibco.cep.studio.core.index.model.impl.GlobalVariableDefImpl;


public class GlobalVariableExtension extends GlobalVariableDefImpl {

	private String fProjectName;
	
	public GlobalVariableExtension(String projectName) {
		super();
		this.fProjectName = projectName;
	}

	public String getProjectName() {
		return fProjectName;
	}

	public void setProjectName(String projectName) {
		fProjectName = projectName;
	}

}
