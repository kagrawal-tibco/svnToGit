package com.tibco.cep.studio.core;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.common.configuration.XPATH_VERSION;

public interface IStudioProjectContributor {

	public List<String> getNauturesList();
	
	public List<String> getBuildersList();
	
	public void createProject(IProject project, boolean isImport, XPATH_VERSION xpathVersion);
	
	public void setContainsJavaNatureOnImport(boolean containsJavaNature);
}
