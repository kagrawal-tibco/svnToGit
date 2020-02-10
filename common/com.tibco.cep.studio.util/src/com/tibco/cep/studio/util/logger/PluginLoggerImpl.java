/**
 *
 */
package com.tibco.cep.studio.util.logger;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.service.logging.impl.LogManagerImpl;
import com.tibco.cep.runtime.service.logging.impl.LoggerImpl;

/**
 * @author aathalye
 * @author ssailapp
 *
 */
public class PluginLoggerImpl extends LoggerImpl {

	private String pluginId;
	
	private static boolean reset;
	
	public static final String CONSOLE_ID = "Tester Engine Console-";
	
	protected PluginLoggerImpl (final BEProperties properties,
			                    final String pluginId) throws Exception {
		super(pluginId);
		this.pluginId = pluginId;
		if (!reset) {
	        LogManager logManager = new LogManagerImpl(properties);
	        LogManagerFactory.setLogManager(logManager);
	        reset = true;
		}
    }


	public static PluginLoggerImpl createLogger(final BEProperties properties,
			                                    final String pluginId) throws Exception {
		return new PluginLoggerImpl(properties, pluginId);
	}

			
	public void logInfo(String className, String message, Object...args) {
		StringBuilder builder =
			new StringBuilder(" [ " + pluginId + " ]");
		if (className != null) {
			builder.append("[ class:" + className + " ]");
		}

		builder.append(message);
		log(Level.INFO, builder.toString(), args);
	}

	public void logInfo(String message, Object...args) {
		logInfo(null, message, args);
	}

	public void logDebug(String message, Object...args) {
		logDebug(null, message, args);
	}

	public void logDebug(String className, String message, Object...args) {
		StringBuilder builder =
			new StringBuilder(" [ " + pluginId + " ]");
		if (className != null) {
			builder.append("[ class:" + className + " ]");
		}
		if (message != null) {
			builder.append(message);
		}
		log(Level.DEBUG, builder.toString(), args);
	}
	
	public void logDebug(Throwable t, Object... args) {
		StringBuilder builder =
			new StringBuilder(" [ " + pluginId + " ]");
		String exMessage = t.getMessage() == null ? "" : t.getMessage();
		builder.append(exMessage);
		log(Level.DEBUG, builder.toString(), t, args);
	}
	
		
	public void logError(String className, String message) {
		logError(className, message, null);
	}
	
	public void logError(String className, String message, Throwable t, Object... args) {
		StringBuilder builder =
			new StringBuilder(" [ " + pluginId + " ]");
		if (className != null) {
			builder.append("[ class:" + className + " ]");
		}
		builder.append(message);
		if (t != null) {
			log(Level.ERROR, builder.toString(), t, args);
		} else {
			log(Level.ERROR, builder.toString(), args);
		}
	}

	public void logError(Throwable t, Object... args) {
		logError(null, null, t, args);
	}
	
	public boolean isDebugEnabled() {
		return isEnabledFor(Level.DEBUG);
	}
	
	public boolean isEnabledForError() {
		return isEnabledFor(Level.ERROR);
	}
}
