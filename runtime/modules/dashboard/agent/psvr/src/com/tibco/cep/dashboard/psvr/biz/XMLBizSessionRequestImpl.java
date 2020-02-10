package com.tibco.cep.dashboard.psvr.biz;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;

public class XMLBizSessionRequestImpl extends XMLBizRequestImpl implements BizSessionRequest {
	
	private String contextPath;
	
	private Map<String,Object> attributes;

	public XMLBizSessionRequestImpl(String requestXML, String contextPath) {
		super(requestXML);
		this.contextPath = contextPath; 
		attributes = new HashMap<String, Object>();
	}
	
	public XMLBizSessionRequestImpl(BizRequest requestXML, String contextPath) {
		super(null);
		this.contextPath = contextPath;
		Iterator<String> parameterNames = requestXML.getParameterNames();
		while (parameterNames.hasNext()) {
			String parameterName = (String) parameterNames.next();
			String[] parameterValues = requestXML.getParameterValues(parameterName);
			for (String parameterValue : parameterValues) {
				addParameter(parameterName, parameterValue);
			}
		}
		attributes = new HashMap<String, Object>();
		getSession(true);
	}
	
	
	@Override
	public void setAttribute(String key, Object value) {
		if (value == null){
			attributes.remove(key);
			return;
		}
		attributes.put(key, value);
	}	

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public Iterator<String> getAttributeNames() {
		return attributes.keySet().iterator();
	}
	
	@Override
	public BizSession getSession() {
		return getSession(false);
	}	

	@Override
	public BizSession getSession(boolean create) {
		BizSession session = null;
		String sessionid = getParameter(KnownParameterNames.SESSION_ID);
		if (StringUtil.isEmptyOrBlank(sessionid) == false){
			session = BizSessionProvider.getInstance().getSession(sessionid);
		}
		else if (create == true){
			String token = getParameter(KnownParameterNames.TOKEN);
			session = BizSessionProvider.getInstance().createSession(token);
			addParameter(KnownParameterNames.SESSION_ID, session.getId());
		}
		return session;
	}

	@Override
	public String getContextPath() {
		return this.contextPath;
	}

	@Override
	public Map getParameterMap() {
		return null;
	}



}
