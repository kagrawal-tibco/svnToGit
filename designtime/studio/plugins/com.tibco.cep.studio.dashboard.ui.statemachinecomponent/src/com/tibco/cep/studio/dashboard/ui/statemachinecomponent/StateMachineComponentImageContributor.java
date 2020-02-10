package com.tibco.cep.studio.dashboard.ui.statemachinecomponent;

import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.studio.dashboard.ui.images.ImageContributor;

public class StateMachineComponentImageContributor implements ImageContributor {

	@Override
	public ImageDescriptor getDescriptor(String key) {
		return DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor(key);
	}

}
