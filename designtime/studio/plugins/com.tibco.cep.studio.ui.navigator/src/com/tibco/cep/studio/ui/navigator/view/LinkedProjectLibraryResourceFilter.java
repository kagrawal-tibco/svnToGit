package com.tibco.cep.studio.ui.navigator.view;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Filters 'linked resource' project library entries from the Studio Explorer, so
 * that they can all be grouped in the "Project Dependencies" node.
 * 
 * @author rhollom
 *
 */
public class LinkedProjectLibraryResourceFilter extends ViewerFilter {

	private static final String PROJLIB_EXT = "projlib";
	
	public LinkedProjectLibraryResourceFilter() {
		super();
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IResource) {
			if (PROJLIB_EXT.equalsIgnoreCase(((IResource) element).getFileExtension())) {
				return !((IResource) element).isLinked(IResource.CHECK_ANCESTORS);
			}
		}
		return true;
	}

}
