package com.tibco.cep.webstudio.client.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.webstudio.client.WebStudio;

/**
 * This is wrapper class on java.util.Logger.<br/>
 * It adds additional functionality of showing userName in log statements and hides
 * stackTrace logging.
 * 
 * @author moshaikh
 * 
 */
public class WebStudioClientLogger {
	
	private Logger logger;
	/**
	 * This instance is used when logging is disabled and no log messages should be submitted to server.
	 */
	private static WebStudioClientLogger dummyInstance = new WebStudioClientLogger(null);

	private WebStudioClientLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Creates and returns an instance of WebStudioClientLogger.
	 * @param loggerName
	 * @return
	 */
	public static synchronized WebStudioClientLogger getLogger(String loggerName) {
		if (WebStudio.isLoggingEnabled()) {
			String userName = null;
			Logger logger = null;
			if (WebStudio.get() != null && WebStudio.get().getUser() != null) {
				userName = WebStudio.get().getUser().getUserName();
			}
			if (userName != null && !userName.trim().isEmpty()) {
				logger = Logger.getLogger(loggerName + " (user-" + userName + ")");
			}
			else {
				logger = Logger.getLogger(loggerName);
			}
			WebStudioClientLogger gwtClientLogger = new WebStudioClientLogger(logger);
			return gwtClientLogger;
		} else {
			return dummyInstance;
		}
	}

	public void info(String msg) {
		if (logger != null) {
			logger.info(msg);
		}
	}

	public void debug(String msg) {
		if (logger != null) {
			logger.fine(msg);
		}
	}

	public void warn(String msg) {
		if (logger != null) {
			logger.warning(msg);
		}
	}

	public void error(String msg) {
		if (logger != null) {
			logger.severe(msg);
		}
	}
	
	public void error(Throwable t) {
		if (logger != null) {
			logger.log(Level.SEVERE, t.getMessage(), t);
		}
	}
}
