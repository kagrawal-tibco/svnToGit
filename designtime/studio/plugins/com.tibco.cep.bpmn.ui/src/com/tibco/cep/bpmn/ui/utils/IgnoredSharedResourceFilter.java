package com.tibco.cep.bpmn.ui.utils;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.core.StudioCore;

public class IgnoredSharedResourceFilter extends ViewerFilter{

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof IResource) {
			IResource res = (IResource) element;
			return !StudioCore.isIgnoredHint(res);
		}
		return false;
	}

}
