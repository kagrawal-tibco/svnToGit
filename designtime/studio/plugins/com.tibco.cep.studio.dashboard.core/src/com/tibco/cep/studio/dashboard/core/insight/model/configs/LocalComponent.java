package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalComponent extends LocalConfig {

	public LocalComponent(String configType) {
		super(configType);
	}

	public LocalComponent(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalComponent(LocalElement parentConfig, String configType, String name) {
		super(parentConfig, configType, name);
	}

}
