package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalTextComponentColorSet extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.TEXT_COLOR_SET;

	public LocalTextComponentColorSet() {
		super(THIS_TYPE);
	}

	public LocalTextComponentColorSet(LocalElement parentElement,
			BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalTextComponentColorSet(LocalElement parentElement,
			String name) {
		super(parentElement, THIS_TYPE, name);
	}
	
}
