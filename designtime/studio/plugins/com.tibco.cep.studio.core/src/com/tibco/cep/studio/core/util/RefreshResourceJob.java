package com.tibco.cep.studio.core.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class RefreshResourceJob extends WorkspaceJob {

	public static final String REFRESH_JOB = "_refresh_resource_job";
	
	private IResource fResource;
	private int fDepth;

	public RefreshResourceJob(IResource resource, int depth) {
		super("Refreshing "+resource.getName());
		this.fResource = resource;
		this.fDepth = depth;
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Refreshing "+fResource.getName(), 1);
		fResource.refreshLocal(fDepth, monitor);
		monitor.done();
		return Status.OK_STATUS;
	}

}
