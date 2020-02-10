package com.tibco.cep.dashboard.psvr.biz.security;

import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.security.SecurityToken;

public class SignOutAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		securityClient.logout(token);
		return handleSuccess(token+" signed out");
	}

}
