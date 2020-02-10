package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalChartComponent extends LocalPreviewableComponent {

	public LocalChartComponent() {
		super(BEViewsElementNames.CHART_COMPONENT);
	}

	public LocalChartComponent(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, BEViewsElementNames.CHART_COMPONENT, mdElement);
	}

	public LocalChartComponent(LocalElement parentConfig, String name) {
		super(parentConfig, BEViewsElementNames.CHART_COMPONENT, name);
	}

//	@Override
//	public void setupProperties() throws Exception {
//		super.setupProperties();
//	}

}