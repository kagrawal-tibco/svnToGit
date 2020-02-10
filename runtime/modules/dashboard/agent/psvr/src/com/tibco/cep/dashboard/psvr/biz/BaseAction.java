package com.tibco.cep.dashboard.psvr.biz;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementClient;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.STATE;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class BaseAction {

	protected Logger logger;
	protected ExceptionHandler exceptionHandler;
	protected MessageGenerator messageGenerator;

	protected ManagementClient managementClient;

	protected String command;
	protected Properties properties;
	protected Map<String, String> configuration;

	protected void init(String command,Properties properties, Map<String, String> configuration) throws Exception {
		this.command = command;
		this.properties = properties;
		this.configuration = configuration;
		managementClient = (ManagementClient) ManagementUtils.getContext().lookup("management");
	}

	public BizResponse execute(BizRequest request) {
		BizResponse response = null;
		if (managementClient.getStatus() != STATE.RUNNING) {
			response = handleError("Dashboard agent is not running");
		}
		try {
			response = doExecute(request);
		} catch (Throwable t) {
			String msg = getMessage("bizaction.runtime.failure", new MessageGeneratorArgs(t,request));
			exceptionHandler.handleException(msg,t, Level.ERROR, Level.ERROR);
			String message = t.getMessage();
			if (StringUtil.isEmptyOrBlank(message) == true) {
				message = "Request processing failed";
			}
			response = handleError(message);
		}
		if (response.getHeader("Content-Type") == null) {
			response.addHeader("Content-Type", "text/xml; charset=UTF-8");
		}
		return response;
	}

	protected abstract BizResponse doExecute(BizRequest request);

/*
	protected final XMLResponse handleException(Throwable t) {
		exceptionHandler.handleException(t);
		return XMLResponse.createErrorResponse(XMLResponse.EXCEPTION_STATUS, exceptionHandler.getMessage(t), t);
	}

	protected final XMLResponse handleException(String message, Throwable t) {
		if (message == null && t != null) {
			message = exceptionHandler.getMessage(t);
		}
		exceptionHandler.handleException(message, t);
		return XMLResponse.createErrorResponse(XMLResponse.EXCEPTION_STATUS, message, t);
	}

	protected final XMLResponse handleException(String message, Throwable t, Level exMsgLogLevel, Level stackTraceLogLevel) {
		exceptionHandler.handleException(message, t, exMsgLogLevel, stackTraceLogLevel);
		return XMLResponse.createErrorResponse(XMLResponse.EXCEPTION_STATUS, message, t);
	}
*/

	protected final BizResponse handleSuccess(String content) {
		XMLBizResponseImpl response = new XMLBizResponseImpl();
		response.addAttribute("content", content);
		return response;
	}

	protected final BizResponse handleSuccessWithCommandAck(){
		return handleSuccess(getCommandXML());
	}

	private final String getCommandXML() {
		StringBuilder sb = new StringBuilder("<command ");
		sb.append("name=\"");
		sb.append(command);
		sb.append("\"/>");
		return sb.toString();
	}

	//PORT add token to the handle error to log
	protected final BizResponse handleError(String message) {
		XMLBizResponseImpl response = new XMLBizResponseImpl(XMLBizResponseImpl.ERROR_STATUS, message);
		return response;
	}

	//PORT add token to the handle error to log
	protected final BizResponse handleError(String message,Throwable t) {
		exceptionHandler.handleException(message, t);
		return handleError(message);
	}

	protected final String getMessage(String key, Locale locale, MessageGeneratorArgs arguments) {
		return messageGenerator.getMessage(key, locale, arguments);
	}

	protected final String getMessage(String key, Locale locale) {
		return messageGenerator.getMessage(key, locale);
	}

	protected final String getMessage(String key, MessageGeneratorArgs arguments) {
		return messageGenerator.getMessage(key, arguments);
	}

	protected final String getMessage(String key) {
		return messageGenerator.getMessage(key);
	}

	protected void cleanup(){

	}

}
