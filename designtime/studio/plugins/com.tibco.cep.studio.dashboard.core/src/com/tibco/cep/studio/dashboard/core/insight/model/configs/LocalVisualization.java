package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalVisualization extends LocalConfig {

	public LocalVisualization(String configType) {
		super(configType);
	}

	public LocalVisualization(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalVisualization(LocalElement parentConfig, String configType, String name) {
		super(parentConfig, configType, name);
	}

	public LocalVisualization(LocalElement parentConfig, String configType) {
		super(parentConfig, configType);
	}
}
