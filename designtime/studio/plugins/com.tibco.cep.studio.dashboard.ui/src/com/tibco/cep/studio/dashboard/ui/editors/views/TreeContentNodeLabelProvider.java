package com.tibco.cep.studio.dashboard.ui.editors.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;

public class TreeContentNodeLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		TreeContentNode node = (TreeContentNode) element;
		LocalElement data = (LocalElement) node.getData();
		return ((ContributableImageRegistry) DashboardUIPlugin.getInstance().getImageRegistry()).get(data);
	}

	@Override
	public String getText(Object element) {
		return ((TreeContentNode) element).getName();
	}

}