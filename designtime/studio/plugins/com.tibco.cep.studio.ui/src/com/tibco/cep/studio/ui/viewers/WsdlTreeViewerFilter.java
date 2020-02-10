package com.tibco.cep.studio.ui.viewers;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * @author pdhar
 *
 */
public class WsdlTreeViewerFilter extends ViewerFilter {
	IProject project;
	
	public WsdlTreeViewerFilter(IProject project) {
		setProject(project);
	}
	

	/**
	 * @return the project
	 */
	public IProject getProject() {
		return project;
	}


	/**
	 * @param project the project to set
	 */
	public void setProject(IProject project) {
		this.project = project;
	}

	
	private boolean hasChildren(Object parent) throws CoreException {
		boolean hasChildren = false;
		if(parent instanceof IProject){
			IResource[] members = ((IProject) parent).members();
			for (IResource resource : members) {
				hasChildren = hasChildren || hasChildren(resource);
			}
		} else if (parent instanceof IContainer) {
			IResource[] members = ((IContainer)parent).members();
			for (IResource resource : members) {
				hasChildren = hasChildren || hasChildren(resource);
			}
		} else if(parent instanceof IFile) {
			IFile file = (IFile)parent;
			if(file.getFileExtension().equals("wsdl"))
				hasChildren = true;

		} 
		return hasChildren;
	}



	@Override
	public boolean select(Viewer viewer, Object parentElement,
			Object element) {
		if(element instanceof IResource) {
			try {
				if (element instanceof IContainer || element instanceof IFile) {
					return hasChildren(element);
				}
				
			} catch (CoreException e) {
				return false;
			}
		} 
		return false;
	}
	

	
}