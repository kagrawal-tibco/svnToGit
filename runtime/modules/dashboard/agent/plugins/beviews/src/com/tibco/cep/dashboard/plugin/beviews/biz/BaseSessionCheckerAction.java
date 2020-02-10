package com.tibco.cep.dashboard.plugin.beviews.biz;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseSessionedAction;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.security.SecurityToken;

public abstract class BaseSessionCheckerAction extends BaseSessionedAction {

	@Override
	protected final BizResponse doSessionedExecute(SecurityToken token, BizSessionRequest request) {
		String sessionId = request.getParameter(KnownParameterNames.SESSION_ID);
		if (StringUtil.isEmptyOrBlank(sessionId) == false){
			BizSession session = request.getSession();
			if (session == null){
				//session is not present, we are dealing with invalidated session
				return handleInvalidSession(token,sessionId,request);
			}
		}
		return doExecute(token,request);
	}

	protected abstract BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request);
	
	protected abstract BizResponse doExecute(SecurityToken token, BizSessionRequest request);

}
