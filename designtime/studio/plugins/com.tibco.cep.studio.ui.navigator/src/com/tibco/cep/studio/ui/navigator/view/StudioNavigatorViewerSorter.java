package com.tibco.cep.studio.ui.navigator.view;

import java.text.Collator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class StudioNavigatorViewerSorter extends ViewerSorter {

	public StudioNavigatorViewerSorter() {
	}

	public StudioNavigatorViewerSorter(Collator collator) {
		super(collator);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof IContainer && !(e2 instanceof IContainer)) {
			return -1;
		}
		if (e2 instanceof IContainer && !(e1 instanceof IContainer)) {
			return 1;
		}
		return super.compare(viewer, e1, e2);
	}
	
}
