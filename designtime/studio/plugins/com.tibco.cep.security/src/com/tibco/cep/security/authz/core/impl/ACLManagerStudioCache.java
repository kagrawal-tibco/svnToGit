package com.tibco.cep.security.authz.core.impl;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;

import com.tibco.cep.security.authz.core.ACLManager;
import com.tibco.cep.security.authz.utils.ACLUtils;

/**
* User: aathalye
* Date: May 14, 2010
* Time: 11:30:53 AM
*/

public class ACLManagerStudioCache extends ACLManagerCache implements IResourceChangeListener {
	
	private static final String ACL_FILE_EXTENSION = "ac";
	
	public static final ACLManagerStudioCache INSTANCE = new ACLManagerStudioCache();
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {

			public boolean visit(IResourceDelta delta) throws CoreException {
				IResource resource = delta.getResource();
				if (resource instanceof IWorkspaceRoot) {
					//Each child resource should be processed.
					IResourceDelta[] affectedChildren = delta.getAffectedChildren();
					for (IResourceDelta resourceDelta : affectedChildren) {
						processDelta(resourceDelta);
					}
				}
				return true;
			}
		};
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private ACLManagerStudioCache() {}
	
	
	
	/**
	 * 
	 * @param resourceDelta
	 */
	private void processDelta(IResourceDelta resourceDelta) {
		
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			public boolean visit(IResourceDelta delta) {
				int type = delta.getKind();
				
				IResource resource = delta.getResource();
				if (!(resource instanceof IFile)) {
					return true;
				}
				IFile file = (IFile)resource;
				//We are not interested in other notifications
				if (!ACL_FILE_EXTENSION.equals(file.getFileExtension())) {
					return true;
				}
				switch (type) {
				case IResourceDelta.ADDED :
					handleAccessConfigAddition(file);
					break;
				case IResourceDelta.REMOVED :
					handleAccessConfigRemoval(file);
					break;
				case IResourceDelta.CHANGED :
					handleAccessConfigModification(file);
					break;	
				}
				return true;
			}
		};
		try {
			resourceDelta.accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Handle .ac file creation.
	 * @param file
	 */
	private void handleAccessConfigAddition(IFile file) {
		IProject project = file.getProject();
		
		//Assuming there is no acl manager for it
		String projectName = project.getName();
		
		try {
			InputStream stream = file.getContents();
			stream = ACLUtils.wrapUnmarkableStream(stream);
			ACLManager aclManager = new ACLManagerImpl();
			aclManager.load(stream, true);
			if (aclManager != null) {
				putACLManager(projectName, aclManager);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Handle .ac file update.
	 * @param file
	 */
	private void handleAccessConfigModification(IFile file) {
		IProject project = file.getProject();
		//Same as add case
		String projectName = project.getName();
		
		try {
			InputStream stream = file.getContents();
			stream = ACLUtils.wrapUnmarkableStream(stream);
			ACLManager aclManager = getACLManager(projectName);
			if (aclManager != null) {
				aclManager.load(stream, true);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Handle .ac file removal.
	 * @param file
	 */
	private void handleAccessConfigRemoval(IFile file) {
		IProject project = file.getProject();
		//Same as add case
		String projectName = project.getName();
		removeACLManager(projectName);
	}
}
