package com.tibco.cep.studio.ui.wizards;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class OnlyFileInclusionFilter extends ViewerFilter {
	
	protected Set<String> inclusions;
	public static final Object[] NO_CHILDREN = new Object[0];
	protected boolean visible = false;
	
	/**
	 * @param inclusions
	 * @param showSubNode
	 */
	public OnlyFileInclusionFilter(Set<String> inclusions) {
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
		if(element instanceof SharedElementRootNode){
			return false;
		}else if(element instanceof DesignerElement){
			return true;
		}else{
			return showSubNode(element);
		}
	}
	
	/**
	 * @param element
	 * @return
	 */
	protected boolean showSubNode(Object element){
		if(!(element instanceof IResource)){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	protected boolean isVisible(Object element) {
	
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
	protected  boolean exists(Object element) {
		if (element == null) {
			return false;
		}
		if (element instanceof IResource) {
			return ((IResource) element).exists();
		}
		
		return true;
	}
	
	protected boolean isEntityFile(Object file){
		return inclusions.contains(((IFile) file).getFileExtension());
		
	}
}
