package com.tibco.cep.studio.core.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class DefaultStudioBuildParticipant implements IStudioBuildParticipant {

	@Override
	public void cleanProject(IProject project,IProgressMonitor monitor) throws CoreException{
		// default implementation does nothing
	}

	@Override
	public void fullProjectBuild(IProject project,IProgressMonitor monitor) throws CoreException {
		// default implementation does nothing
	}

	@Override
	public void incrementalProjectBuild(IProject project, IResourceDelta delta,IProgressMonitor monitor) throws CoreException {
		// default implementation does nothing
	}

}