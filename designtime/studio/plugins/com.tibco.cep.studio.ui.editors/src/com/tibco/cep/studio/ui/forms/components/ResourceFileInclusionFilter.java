package com.tibco.cep.studio.ui.forms.components;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class ResourceFileInclusionFilter extends ViewerFilter {
	
	@SuppressWarnings("unchecked")
	private Set inclusions;
	public static final Object[] NO_CHILDREN = new Object[0];
	private boolean visible = false;
	@SuppressWarnings("unchecked")
	public ResourceFileInclusionFilter(Set inclusions) {
		this.inclusions = inclusions;
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
				IFile file = (IFile) res;
				return isEntityFile(file);
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	private boolean isVisible(Object element) {
	
		Object[] object = CommonUtil.getResources((IFolder) element);
		
		for(Object obj : object){
			if(obj instanceof IFolder){
				isVisible(obj);
			}
			if(obj instanceof IFile){
				if(isEntityFile(obj)){
					visible = true;
				}
			}
		}
		
		if (visible == true) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unused")
	private  boolean exists(Object element) {
		if (element == null) {
			return false;
		}
		if (element instanceof IResource) {
			return ((IResource) element).exists();
		}
		
		return true;
	}
	
	private boolean isEntityFile(Object file){
		return !inclusions.contains(((IFile) file).getFileExtension());
	}
}
