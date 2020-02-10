package com.tibco.cep.dashboard.plugin.beviews.runtime;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class ComponentContentProvider {
	
	protected SecurityToken securityToken;
	
	protected Properties properties;
	
	protected Logger logger;
	
	protected ExceptionHandler exceptionHandler;
	
	protected MessageGenerator messageGenerator;
	
	public void setSecurityToken(SecurityToken securityToken) {
		this.securityToken = securityToken;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public void setMessageGenerator(MessageGenerator messageGenerator) {
		this.messageGenerator = messageGenerator;
	}

	public abstract String getComponentContent(BizSessionRequest request);

}
