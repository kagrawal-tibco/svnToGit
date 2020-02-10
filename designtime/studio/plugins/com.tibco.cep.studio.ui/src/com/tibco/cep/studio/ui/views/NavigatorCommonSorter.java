package com.tibco.cep.studio.ui.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * 
 * @author sasahoo
 *
 */
public class NavigatorCommonSorter extends ViewerSorter {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
	 */
	@Override
	public int category(Object element) {
		return 2;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return 1;
	}
}
