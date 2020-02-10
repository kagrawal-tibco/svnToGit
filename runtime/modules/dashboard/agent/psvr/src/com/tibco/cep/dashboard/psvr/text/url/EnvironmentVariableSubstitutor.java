package com.tibco.cep.dashboard.psvr.text.url;

import com.tibco.cep.dashboard.common.Once;
import com.tibco.cep.kernel.service.logging.Level;

class EnvironmentVariableSubstitutor implements Substitutor {

	private static final long serialVersionUID = 6583695568621288194L;

	// Keys are free-formed, can be anything from Environment
	private static final String[] KEYS = new String[] {};

	public String substitute(String key, SubstitutionContext context) throws Exception {
		if (key == null) {
			throw new Exception("Can't resolve environment context with null key.");
		} else {
			String envProperty = System.getProperty(key);
			if (envProperty == null) {
				if (Once.firstTime(key))
					context.getLogger().log(Level.WARN,"Java environment variable is not set for " + key);
			}
			return envProperty;
		}

	}

	@Override
	public String[] getSupportedKeys() {
		return KEYS;
	}


}