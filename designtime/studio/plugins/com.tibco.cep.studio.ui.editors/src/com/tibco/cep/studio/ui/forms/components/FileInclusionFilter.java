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
 * Filter that will include only IFile from the list of provided file
 * extensions. Also it will exclude the base concept and empty folders as well. 
 * The filter will not filter objects that cannot be adapted into
 * IResource
 * 
 * @author sasahoo
 */
@SuppressWarnings("rawtypes")
public class FileInclusionFilter extends ViewerFilter {
	

	protected Set inclusions;
	public static final Object[] NO_CHILDREN = new Object[0];
	protected boolean visible = false;
	protected String baseAbsolutePath;
	
	/**
	 * @param inclusions
	 */
	public FileInclusionFilter(Set inclusions){
		this.inclusions = inclusions;
	}
	
	/**
	 * @param inclusions
	 * @param baseAbsolutePath
	 */
	public FileInclusionFilter(Set inclusions, String baseAbsolutePath) {
		this.inclusions = inclusions;
		this.baseAbsolutePath = baseAbsolutePath;
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
				String path = "/" + file.getProjectRelativePath().toString();
				return isEntityFile(file) && !isBaseEntityFile(baseAbsolutePath, path);
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
	protected boolean isVisible(Object element) {
	
		Object[] object = CommonUtil.getResources((IFolder) element);
		
		for(Object obj : object){
			if(obj instanceof IFolder){
				isVisible(obj);
			}
			if(obj instanceof IFile){
				if(isValidResource(obj)){
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
	 * @param obj
	 * @return
	 */
	protected boolean isValidResource(Object obj){
		return isEntityFile(obj);
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
	
	protected boolean isEntityFile(Object file){
		return inclusions.contains(((IFile) file).getFileExtension());
	}
	
	protected boolean isBaseEntityFile(String basepath, String path){
		if (basepath == null) {
			return false;
		}
		return basepath.equals(path);
	}
}
