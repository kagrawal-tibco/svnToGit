package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalAlertAction extends LocalConfig {

	public static final String PROP_KEY_ENABLED = "Enabled";

	public LocalAlertAction(String configType) {
		super(configType);
	}

	public LocalAlertAction(LocalElement parentElement,
			String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalAlertAction(LocalElement parentConfig,
			String configType, String name) {
		super(parentConfig, configType, name);
	}
	
	public LocalAlertAction(LocalElement parentConfig,
			String configType) {
		super(parentConfig, configType);
	}
}
