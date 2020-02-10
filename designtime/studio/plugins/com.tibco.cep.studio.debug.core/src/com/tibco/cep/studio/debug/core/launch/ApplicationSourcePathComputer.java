package com.tibco.cep.studio.debug.core.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;

/*
@author ssailapp
@date Jul 22, 2009 1:20:51 PM
 */

public class ApplicationSourcePathComputer implements ISourcePathComputerDelegate {
	
	public static final String ID = "com.tibco.cep.studio.debug.core.applicationSourceLocator";
	

	public String getId() {
		return ID;
	}
	
	public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException {
		
		return ApplicationRuntime.computeSourceContainers(configuration,monitor);
		
	}
    
	
}
