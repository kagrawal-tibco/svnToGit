package com.tibco.cep.studio.ui.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.part.ViewPart;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractArgumentView extends ViewPart {

	protected TreeViewer viewer;
	

	/**
	 * @return the viewer associated with this
	 */
	public TreeViewer getViewer() {
		return this.viewer;
	}
}
