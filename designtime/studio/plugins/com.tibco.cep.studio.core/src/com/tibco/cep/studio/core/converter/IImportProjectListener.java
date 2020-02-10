package com.tibco.cep.studio.core.converter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IImportProjectListener {

	public void projectImported(IProject project, IProgressMonitor monitor);
	
}
