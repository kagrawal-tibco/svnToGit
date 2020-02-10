package com.tibco.cep.studio.ui.forms.components;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachinesFileInclusionFilter extends FileInclusionFilter {
	
	/**
	 * @param inclusions
	 */
	@SuppressWarnings("unchecked")
	public StateMachinesFileInclusionFilter(Set inclusions) {
		super(inclusions);
	}
	
	/**
	 * @param viewer
	 * @param parentElement
	 * @param element
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return isEntityFile(file);
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if(element instanceof StateEntityNode){
			return false;
		}
		return true;
	}
}
