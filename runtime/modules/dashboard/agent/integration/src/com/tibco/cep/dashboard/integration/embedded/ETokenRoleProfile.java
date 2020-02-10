package com.tibco.cep.dashboard.integration.embedded;

import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ETokenRoleProfile extends TokenRoleProfile {

	public ETokenRoleProfile(EViewsConfigHelper helper, SecurityToken token) {
		super();
		alertEvalutorsIndex = helper.getAlertEvaluatorIndex();
		viewsConfigHelper = helper;
	}

}
