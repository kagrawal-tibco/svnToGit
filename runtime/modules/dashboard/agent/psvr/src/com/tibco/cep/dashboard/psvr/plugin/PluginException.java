package com.tibco.cep.dashboard.psvr.plugin;

/**
 * @author anpatil
 *
 */
public class PluginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8984947084961006306L;

	/**
	 * 
	 */
	public PluginException() {
	}

	/**
	 * @param message
	 */
	public PluginException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PluginException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PluginException(String message, Throwable cause) {
		super(message, cause);
	}

}
