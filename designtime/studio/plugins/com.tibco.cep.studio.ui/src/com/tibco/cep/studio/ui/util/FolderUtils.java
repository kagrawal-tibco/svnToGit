package com.tibco.cep.studio.ui.util;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class FolderUtils {
	
	public static void createDerivedFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor) throws CoreException {
		if (!folder.exists()) {
			IContainer parent= folder.getParent();
			if (parent instanceof IFolder) {
				createDerivedFolder((IFolder)parent, force, local, null);
			}
			folder.create(force ? (IResource.FORCE | IResource.DERIVED) : IResource.DERIVED, local, monitor);
		}
	}

	
	public static void createFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor) throws CoreException {
		if (!folder.exists()) {
			IContainer parent= folder.getParent();
			if (parent instanceof IFolder) {
				createFolder((IFolder)parent, force, local, null);
			}
			folder.create(force, local, monitor);
		}
	}

}
