package com.tibco.cep.bpmn.ui.utils;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.core.SharedElementRootNode;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.ui.filter.FileInclusionFilter;


/**
 * 
 * @author sasahoo
 *
 */
public class JavaTaskResourceFilter extends FileInclusionFilter {

	/**
	 * @param inclusions
	 */
	@SuppressWarnings("rawtypes")
	public JavaTaskResourceFilter(Set inclusions) {
		super(inclusions);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				return isEntityFile(file) && isJavaTaskAnnotatedResource(file);
			}
			if (res instanceof IFolder) {
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		if (element instanceof SharedElementRootNode || element instanceof ElementContainer) {
			return true;
		} else {
			if(!(element instanceof IResource)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param file
	 * @return
	 */
	private boolean isJavaTaskAnnotatedResource(IFile file){
	   return StudioJavaUtil.isAnnotatedJavaTaskSource(file, CommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_TASK);
	}

	/**
	 * Ensures that the element contains a "virtual" rule function
	 * @param element
	 * @return 
	 */
	@Override
	protected boolean isVisible(Object element) {
		// TODO Auto-generated method stub

		Object[] object = CommonUtil.getResources((IFolder) element);
		
		for (Object obj : object) {
			if (obj instanceof IFolder) {
				isVisible(obj);
			}
			if (obj instanceof IFile){
				if (isEntityFile(obj) && isJavaTaskAnnotatedResource((IFile)obj)) {
					visible = true;
				}
			}
		}
		
		if (visible == true) {
			return true;
		}
		return false;
	}
	
	
}
