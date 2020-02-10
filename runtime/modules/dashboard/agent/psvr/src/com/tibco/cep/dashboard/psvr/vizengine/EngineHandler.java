package com.tibco.cep.dashboard.psvr.vizengine;

import java.util.Locale;

import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 * 
 */
public abstract class EngineHandler extends AbstractHandler {

	protected final Object[] EMPTY_OBJ_ARRAY = new Object[0];
	
	protected String getMessage(String key, Locale locale, MessageGeneratorArgs arguments) {
		return messageGenerator.getMessage(key, locale, arguments);
	}
	
	protected String getMessage(String key, Locale locale){
		return messageGenerator.getMessage(key, locale);
	}
	
	protected String getMessage(String key) {
		return messageGenerator.getMessage(key);
	}

	protected String getMessage(Exception ex) {
		return exceptionHandler.getMessage(ex);
	}
	
	protected final String getMessage(String key, MessageGeneratorArgs arguments) {
		return messageGenerator.getMessage(key, arguments);
	}

	protected void handleException(Exception e) {
		exceptionHandler.handleException(e);
	}

	protected void handleException(String message, Exception e) {
		exceptionHandler.handleException(message, e);
	}

	protected void handleException(String message, Exception e, Level exMsgLogLevel) {
		exceptionHandler.handleException(message, e, exMsgLogLevel);
	}

	protected void handleException(String message, Exception ex, Level exMsgLogLevel, Level stackTraceLogLevel) {
		exceptionHandler.handleException(message, ex, exMsgLogLevel, stackTraceLogLevel);
	}

}