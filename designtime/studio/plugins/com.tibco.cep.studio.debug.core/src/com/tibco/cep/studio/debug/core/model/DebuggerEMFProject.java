package com.tibco.cep.studio.debug.core.model;

import java.util.List;

import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.studio.core.repo.emf.EMFProject;

public class DebuggerEMFProject extends EMFProject {

	public DebuggerEMFProject(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	public DebuggerEMFProject(List<ResourceProvider> providers, String path) {
		super(providers, path);
	}
	
	

}
