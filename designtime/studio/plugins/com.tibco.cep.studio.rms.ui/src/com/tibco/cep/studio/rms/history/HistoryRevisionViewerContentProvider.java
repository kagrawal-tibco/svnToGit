package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author sasahoo
 *
 */
public class HistoryRevisionViewerContentProvider implements IStructuredContentProvider {
	
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput,
			Object newInput) {
	}
}
