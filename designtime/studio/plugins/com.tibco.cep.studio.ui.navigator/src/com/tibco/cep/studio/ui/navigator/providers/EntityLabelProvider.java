package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.navigator.IDescriptionProvider;

import com.tibco.cep.studio.ui.StudioNavigatorNode;

public class EntityLabelProvider implements ILabelProvider,IDescriptionProvider {

	public Image getImage(Object element) {
		return null;
	}

	public String getText(Object element) {
		if (element instanceof StudioNavigatorNode) {
			return ((StudioNavigatorNode)element).getName();
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {

	}

	public void dispose() {

	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof StudioNavigatorNode) {
			return ((StudioNavigatorNode)anElement).getName();
		}
		return null;
	}

}
