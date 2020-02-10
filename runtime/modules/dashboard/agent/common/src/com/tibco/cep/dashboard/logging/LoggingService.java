package com.tibco.cep.dashboard.logging;

import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * @author anpatil
 * 
 */
public final class LoggingService {

	private static LoggingService instance;

	public static final synchronized LoggingService getInstance() {
		if (instance == null) {
			instance = new LoggingService();
		}
		return instance;
	}

	private RuleServiceProvider ruleServiceProvider;

	private Map<String, Logger> registeredLoggers;

	private LoggingService() {
		registeredLoggers = new LinkedHashMap<String, Logger>();
	}

	public void init(RuleServiceProvider ruleServiceProvider) {
		this.ruleServiceProvider = ruleServiceProvider;
	}

	private Logger internalGetLogger(String name) {
		Logger logger = ruleServiceProvider.getLogger(name);
		registeredLoggers.put(name, logger);
		return logger;
	}

	public static Logger getRootLogger() {
		return instance.internalGetLogger("dashboard");
	}

	public static Logger getChildLogger(Logger parentLogger, String name) {
		return instance.internalGetLogger(parentLogger.getName() + "." + name);
	}

	public static Logger getUtilLogger() {
		return getChildLogger(getRootLogger(), "utils");
	}

	public static Logger getRuntimeLogger() {
		return getChildLogger(getRootLogger(), "runtime");
	}

	public void dumpRegisteredLoggers() {
		if (getRootLogger().isEnabledFor(Level.DEBUG)) {
			getRootLogger().log(Level.DEBUG, "Registered Loggers" + registeredLoggers.keySet());
		}
	}

}