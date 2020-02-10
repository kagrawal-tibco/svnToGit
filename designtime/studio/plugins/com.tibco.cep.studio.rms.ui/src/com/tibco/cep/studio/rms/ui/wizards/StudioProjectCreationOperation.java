package com.tibco.cep.studio.rms.ui.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.ui.actions.CreateStudioProjectOperation;

public class StudioProjectCreationOperation extends CreateStudioProjectOperation {

	public StudioProjectCreationOperation(IProject newProj,
			                              File targetLocation, 
			                              boolean useDefaults) {
		super(newProj, targetLocation, useDefaults);
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.actions.CreateStudioProjectOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException,
			InvocationTargetException, InterruptedException {
		try {
			doProjectCreation(monitor);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
