/**
 * 
 */
package com.tibco.cep.studio.rms.core;

import java.io.File;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.core.impl.ACLManagerCache;
import com.tibco.cep.security.authz.core.impl.ACLManagerImpl;
import com.tibco.cep.security.authz.utils.ACLUtils;
/**
 * @author aathalye
 *
 */
public class LoadACLManagersJob extends Job {

	/**
	 * @param name
	 */
	public LoadACLManagersJob(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		//Get all projects
		IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		
		for (IProject project : allProjects) {
			String projectName = project.getName();
			//Look for .ac file
			IResource accessConfigResource = project.findMember(projectName + ".ac");
			if (accessConfigResource instanceof IFile) {
				IFile accessConfigFile = (IFile)accessConfigResource;
				try {
					InputStream accessConfigContents = accessConfigFile.getContents();
					InputStream markableStream = ACLUtils.wrapUnmarkableStream(accessConfigContents);
					ACLManager aclManager = new ACLManagerImpl();
					aclManager.load(markableStream, true, null);
					ACLManagerCache.INSTANCE.putACLManager(projectName, aclManager);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
			}
		}
		return Status.OK_STATUS;
	}
}