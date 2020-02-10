package com.tibco.cep.dashboard.plugin.beviews.drilldown.utils;

import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.psvr.context.Context;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.security.SecurityToken;

public class DrilldownRequestStore {

	private static final String SEND_EMPTY_RESPONSE = "sendemptyresponse";

	public static Context getContext(BizSessionRequest request) {
		return (Context) request.getAttribute(Context.class.getName());
	}

	public static SecurityToken getSecurityToken(BizSessionRequest request) {
		return (SecurityToken) request.getAttribute(SecurityToken.class.getName());
	}

	public static void setMALSession(BizSessionRequest request, MALSession session) {
		request.setAttribute(MALSession.class.getName(), session);
	}

	public static void setSecurityToken(BizSessionRequest request,
			SecurityToken token) {
		request.setAttribute(SecurityToken.class.getName(), token);
	}

	public static MALSession getSession(BizSessionRequest request) {
		return (MALSession) request.getAttribute(MALSession.class.getName());
	}

	public static boolean isSendEmptyResponse(BizSessionRequest request) {
		Object value = request.getAttribute(SEND_EMPTY_RESPONSE);
		return (value != null && Boolean.TRUE.equals(value));
	}

	public static void setSendEmptyResponse(BizSessionRequest request,
			Boolean value) {
		request.setAttribute(SEND_EMPTY_RESPONSE, value);
	}

	public static String getSessionId(BizSessionRequest request) {
		return (String) request.getAttribute(KnownParameterNames.SESSION_ID);
	}

	
}
