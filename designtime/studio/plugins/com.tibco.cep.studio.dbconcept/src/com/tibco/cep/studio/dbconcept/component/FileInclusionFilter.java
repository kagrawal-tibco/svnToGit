package com.tibco.cep.studio.dbconcept.component;

import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


/**
 * 
 * @author mjha
 *
 */
public class FileInclusionFilter extends ViewerFilter {
	
	protected Set<?> inclusions;
	public static final Object[] NO_CHILDREN = new Object[0];
	protected boolean visible = false;
	private boolean showSubNode;
	
	public FileInclusionFilter(Set<?> inclusions, boolean showSubNode) {
		this.inclusions = inclusions;
		this.showSubNode = showSubNode;
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
				if(!this.showSubNode)
					return false;
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
	protected boolean isVisible(Object element) {
	
		Object[] object = getResources((IFolder) element);
		
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
	
	private Object[] getResources(IContainer container) {
		try {
			IResource[] members = container.members();
			Object[] returnRes = new Object[members.length];
			System.arraycopy(members, 0, returnRes, 0, returnRes.length);
			return returnRes;
		}
		catch (CoreException e) {
			return new Object[0];
		}
	}
}
