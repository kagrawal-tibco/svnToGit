package com.tibco.cep.studio.decision.table.ui.navigation;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableNavigatorLabelProvider extends AbstractResourceLabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		return getOverlayImage(getAdapterFactoryLabelProvider().getImage(element));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		return getAdapterFactoryLabelProvider().getText(element);
	}
}
