package com.tibco.cep.dashboard.management;

import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
public class ExceptionHandler {

	private Logger logger;

	private boolean forceDumpStackTrace;

	public ExceptionHandler(Logger logger) {
		this.logger = logger;
		Properties properties = ManagementService.getInstance().getProperties();
		String propertyNamePrefix = logger.getName().replace(LoggingService.getRootLogger().getName()+".", "");
		String propertyName = propertyNamePrefix+".force.stacktrace";
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Looking for " + propertyName);
		}
		String value = (String) properties.get(propertyName);
		while (StringUtil.isEmptyOrBlank(value) == true && "*".equals(propertyNamePrefix) == false){
			int i = propertyNamePrefix.lastIndexOf(".");
			if (i != -1){
				propertyNamePrefix = propertyNamePrefix.substring(0,i);
				propertyName = propertyNamePrefix+".force.stacktrace";
				value = (String) properties.get(propertyName);
			}
			else {
				propertyNamePrefix = "*";
				propertyName = "*.force.stacktrace";
				value = (String) properties.get(propertyName);
			}
			if (logger.isEnabledFor(Level.TRACE) == true) {
				logger.log(Level.TRACE, "Looking for " + propertyName);
			}
		}
		if (StringUtil.isEmptyOrBlank(value) == true){
			if (logger.isEnabledFor(Level.TRACE) == true) {
				logger.log(Level.TRACE, "Switching exception stacktracing off...");
			}
			forceDumpStackTrace = false;
		}
		else {
			forceDumpStackTrace = Boolean.valueOf(value);
			if (logger.isEnabledFor(Level.TRACE) == true) {
				logger.log(Level.TRACE, "Switching exception stacktracing "+(forceDumpStackTrace? "on" : "off")+" using "+propertyName);
			}
		}
	}

	public final boolean isForceDumpStackTrace() {
		return forceDumpStackTrace;
	}

	public final void setForceDumpStackTrace(boolean forceDumpStackTrace) {
		this.forceDumpStackTrace = forceDumpStackTrace;
	}

	public void handleException(Logger logger, String message, Throwable t, Level exMsgLogLevel, Level stackTraceLogLevel) {
		if (message == null && t != null) {
			message = getMessage(t);
		}
		if (logger.isEnabledFor(exMsgLogLevel) == true) {
			logger.log(exMsgLogLevel, message);
		}
		if (t != null) {
			if (logger.isEnabledFor(stackTraceLogLevel) == true) {
				logger.log(stackTraceLogLevel, t, t.getMessage());
			}
			else if (forceDumpStackTrace == true){
				logger.log(Level.ERROR, t, message);
			}
		}
	}

	public String getMessage(Throwable t) {
		String message = t.getLocalizedMessage();
		if (message == null || message.trim().length() == 0) {
			message = t.toString();
		}
		return message;
	}

	public void handleException(String message, Throwable t, Level exMsgLogLevel) {
		handleException(logger, message, t, exMsgLogLevel, Level.DEBUG);
	}

	public void handleException(String message, Throwable t) {
		handleException(logger, message, t, Level.ERROR, Level.DEBUG);
	}

	public void handleException(Throwable t) {
		handleException(logger, null, t, Level.ERROR, Level.DEBUG);
	}

	public void handleException(Throwable t, Level exMsgLogLevel) {
		handleException(logger, null, t, exMsgLogLevel, Level.DEBUG);
	}

	public void handleException(Throwable t, Level exMsgLogLevel, Level stackTraceLogLevel) {
		handleException(logger, null, t, exMsgLogLevel, stackTraceLogLevel);
	}

	public void handleException(String message, Throwable t, Level exMsgLogLevel, Level stackTraceLogLevel) {
		handleException(logger, message, t, exMsgLogLevel, stackTraceLogLevel);
	}

	public void handleExceptionWithNoSysErrTrace(String message, Throwable t, Level exMsgLogLevel, Level stackTraceLogLevel) {
		if (message == null && t != null) {
			message = getMessage(t);
		}
		if (logger.isEnabledFor(exMsgLogLevel) == true) {
			logger.log(exMsgLogLevel, message);
		}
		if (t != null && logger.isEnabledFor(stackTraceLogLevel) == true) {
			logger.log(stackTraceLogLevel, t.getMessage(), t);
		}
	}

	/*public void handleException(Logger logger, String message, Throwable t, Level exMsgLogLevel) {
		handleException(logger, message, t, exMsgLogLevel, Level.DEBUG);
	}

	public void handleException(Logger logger, String message, Throwable t) {
		handleException(logger, message, t, Level.ERROR, Level.DEBUG);
	}

	public void handleException(Logger logger, Throwable t) {
		handleException(logger, null, t, Level.ERROR, Level.DEBUG);
	}

	public void handleException(Logger logger, Throwable t, Level exMsgLogLevel) {
		handleException(logger, null, t, exMsgLogLevel, Level.DEBUG);
	}

	public void handleException(Logger logger, Throwable t, Level exMsgLogLevel, Level stackTraceLogLevel) {
		handleException(logger, null, t, exMsgLogLevel, stackTraceLogLevel);
	}*/

}