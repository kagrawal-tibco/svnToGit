package com.tibco.cep.studio.ui.navigator.view;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedElement;

/**
 * This class Hide Global Variables from project library heirarchy in Project Explorer
 */
public class ProjectLibraryGVFilter extends ViewerFilter {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof SharedElement) {
			String fileName = ((SharedElement) element).getFileName();
			if (fileName.endsWith(".substvar")) {
				return false;
			}
		}
		if (element instanceof ElementContainer) {
			String folderName = ((ElementContainer) element).getName();
			if (folderName.equals("defaultVars")){
				return false;
			}
		}
		return true;
	}

}
