package com.tibco.rta.engine;

import java.util.Properties;

import com.tibco.rta.common.ConfigProperty;

public class RtaEngineFactory {
	
	private static RtaEngineFactory instance;
	
	private RtaEngine engine;
	
	private RtaEngineFactory(){}
	
	synchronized public static RtaEngineFactory getInstance() {
		if (instance == null) {
			instance = new RtaEngineFactory();
		}
		return instance;
	}
	

	synchronized public RtaEngine getEngine(Properties configuration) {
		if (engine == null) {
			boolean isFT = Boolean
					.parseBoolean((String) ConfigProperty.RTA_FT_ENABLED
							.getValue(configuration));
			if (isFT) {
				boolean isGMP = Boolean
						.parseBoolean((String) ConfigProperty.RTA_FT_GMP_ENABLED
								.getValue(configuration));
				if (isGMP) {
					engine = new GMPFTEnabledRtaEngine();
				} else {
					engine = new FTEnabledRtaEngine();
				}
			} else {
				engine = new RtaEngine();
			}
		}
		return engine;
	}
	
	synchronized public RtaEngine getEngine() {
		return engine;
	}
}
