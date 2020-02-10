package com.tibco.cep.studio.core.dependency;

import java.util.List;

import org.eclipse.core.resources.IResource;

public interface IDependencyCalculator {

	public List<IResource> calculateDependencies(IResource resource);
	
	public void setValidExtensions(String extensions);
	
	public String getValidExtensions();
	
}
