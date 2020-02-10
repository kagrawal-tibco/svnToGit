package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalSeriesColor extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.SERIES_COLOR;

	public LocalSeriesColor() {
		super(THIS_TYPE);
	}

	public LocalSeriesColor(LocalElement parentElement,
			BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalSeriesColor(LocalElement parentElement,
			String name) {
		super(parentElement, THIS_TYPE, name);
	}
	
}
