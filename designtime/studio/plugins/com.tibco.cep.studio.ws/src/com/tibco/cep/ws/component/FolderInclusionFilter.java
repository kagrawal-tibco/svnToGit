package com.tibco.cep.ws.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


/**
 * 
 * @author mjha
 *
 */
public class FolderInclusionFilter extends ViewerFilter {
	
	public FolderInclusionFilter() {

	}
	
	/**
	 * @param viewer
	 * @param parentElement
	 * @param element
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element)
					.getAdapter(IResource.class);
			if (res instanceof IFile) {
				return false;
			}

			if (res instanceof IFolder){
                return true;
			}
		}
		
		return true;
	}
	
	

}
