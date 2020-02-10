package com.tibco.cep.dashboard.config;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.Service;

public class ConfigurationService extends Service {

	public ConfigurationService() throws ManagementException {
		super("configuration", "Configuration Service");
	}

	@Override
	protected void doInit() throws ManagementException {
		GlobalConfiguration.getInstance().init(properties, logger, exceptionHandler, messageGenerator);
		addDependent(FileChangeObserver.getInstance());
		//update the client configuration
		Object value = ConfigurationProperties.HOST_NAME.getValue(properties);
		if (value != null){
			ClientConfiguration.getInstance().addConfigurationValue(ConfigurationProperties.HOST_NAME.getName(),value.toString());
		}
		value = ConfigurationProperties.PULL_REQUEST_PORT.getValue(properties);
		if (value != null){
			ClientConfiguration.getInstance().addConfigurationValue(ConfigurationProperties.PULL_REQUEST_PORT.getName(),value.toString());
		}
		value = ConfigurationProperties.PULL_REQUEST_BASE_URL.getValue(properties);
		if (value != null){
			ClientConfiguration.getInstance().addConfigurationValue(ConfigurationProperties.PULL_REQUEST_BASE_URL.getName(),value.toString());
		}
		//initialize beta features flag
		BetaFeatures.getInstance().init(properties, logger);
	}

}
