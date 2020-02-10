/**
 * 
 */
package com.tibco.cep.studio.ui.filter;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author aathalye
 *
 */
public class ExtensionOnlyBasedFileInclusionFilter extends FileInclusionFilter {
	
	/**
	 * @param inclusions
	 */
	public ExtensionOnlyBasedFileInclusionFilter(Set<String> inclusions) {
		super(inclusions);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param inclusions
	 * @param baseAbsolutePath
	 */
	public ExtensionOnlyBasedFileInclusionFilter(Set<String> inclusions,
			String baseAbsolutePath) {
		super(inclusions, baseAbsolutePath);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.components.FileInclusionFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
			if (res instanceof IFile) {
				IFile file = (IFile) res;
				String fileExtn = file.getFileExtension();
				return inclusions.contains(fileExtn);
			}
			if (res instanceof IFolder){
			    IFolder folder = (IFolder)res;
			    visible = false;
                return isVisible(folder);
			}
		}
		return true;
	}
}
