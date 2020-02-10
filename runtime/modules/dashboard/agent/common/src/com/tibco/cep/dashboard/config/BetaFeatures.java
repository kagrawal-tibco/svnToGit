package com.tibco.cep.dashboard.config;

import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class BetaFeatures {

	private static final PropertyKey ENABLED_BETA_FEATURES = new PropertyKey(true, "enable.beta.features", "Enables beta features", PropertyKey.DATA_TYPE.Boolean, Boolean.FALSE);

	private static BetaFeatures instance;

	static final synchronized BetaFeatures getInstance() {
		if (instance == null) {
			instance = new BetaFeatures();
		}
		return instance;
	}

	private boolean enabled;

	private BetaFeatures() {

	}

	void init(Properties properties, Logger logger) {
		enabled = (Boolean) ENABLED_BETA_FEATURES.getValue(properties);
		if (enabled == true) {
			logger.log(Level.INFO, "Enabling Beta Features...");
		}
	}

	public static final boolean isEnabled(){
		return getInstance().enabled;
	}

}
