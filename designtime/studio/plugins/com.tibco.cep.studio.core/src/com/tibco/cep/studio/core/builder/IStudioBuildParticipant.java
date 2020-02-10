package com.tibco.cep.studio.core.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IStudioBuildParticipant {
	
	public static final int TYPE_CLEAN 		= 0;
	public static final int TYPE_FULL_BUILD	= 1;
	public static final int TYPE_INC_BUILD		= 2;

	void cleanProject(IProject project,IProgressMonitor monitor)throws CoreException;
	void fullProjectBuild(IProject project,IProgressMonitor monitor) throws CoreException;
	void incrementalProjectBuild(IProject project, IResourceDelta delta,IProgressMonitor monitor) throws CoreException;
	
}