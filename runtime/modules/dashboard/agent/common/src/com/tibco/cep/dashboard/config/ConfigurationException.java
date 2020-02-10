package com.tibco.cep.dashboard.config;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 1750811135240239111L;

	public ConfigurationException() {
	}

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
