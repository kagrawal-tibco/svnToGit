package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalAlert extends LocalConfig {

	public static final String PROP_KEY_ENABLED = "Enabled";

	public static final String ELEMENT_KEY_ACTION = "Action";

	public LocalAlert(String configType) {
		super(configType);
	}

	public LocalAlert(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalAlert(LocalElement parentConfig, String configType, String name) {
		super(parentConfig, configType, name);
	}

	public LocalAlert(LocalElement parentConfig, String configType) {
		super(parentConfig, configType);
	}
}
