package com.tibco.cep.studio.rms.client.ui;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author sasahoo
 *
 */
public class ClientDetailsViewerContentProvider implements IStructuredContentProvider{
	
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput,
			Object newInput) {
	}
}
