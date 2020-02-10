package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.studio.dashboard.ui.images.ImageContributor;

public class ChartComponentImageContributor implements ImageContributor {

	@Override
	public ImageDescriptor getDescriptor(String key) {
		return DashboardChartPlugin.getDefault().getImageRegistry().getDescriptor(key);
	}

}
