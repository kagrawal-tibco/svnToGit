package com.tibco.cep.dashboard.psvr.biz;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.config.ConfigurationProperties;
import com.tibco.cep.dashboard.security.SecurityToken;

public abstract class BaseSessionedAction extends BaseAuthenticatedAction {
	
	private String contextPath;
	
	private String baseURL;
	
	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		contextPath = "";
		baseURL = (String) ConfigurationProperties.PULL_REQUEST_BASE_URL.getValue(properties);
	}

	@Override
	protected final BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		BizSessionRequest sessionedXMLRequest = new XMLBizSessionRequestImpl(request,contextPath);
		sessionedXMLRequest.setAttribute(ConfigurationProperties.PULL_REQUEST_BASE_URL.getName(), baseURL);
		return doSessionedExecute(token, sessionedXMLRequest);
	}

	protected abstract BizResponse doSessionedExecute(SecurityToken token, BizSessionRequest request);
}
