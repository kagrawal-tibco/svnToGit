package com.tibco.cep.dashboard.psvr.biz.security;

import java.security.Principal;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.security.InvalidPrincipalException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class SetRoleAction extends BaseAuthenticatedAction {
	
	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
	}

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String preferredRole = request.getParameter("role");
		Principal[] principals = token.getPrincipals();
		for (Principal principal : principals) {
			if (principal.getName().equals(preferredRole) == true){
				try {
					token.setPreferredPrincipal(principal);
				} catch (InvalidPrincipalException e) {
					return handleError(getMessage("security.invalid.preferred.principal",getMessageGeneratorArgs(token,preferredRole)),e);
				}
			}
		}
		if (token.getPreferredPrincipal() == null){
			return handleError(getMessage("security.invalid.preferred.principal",getMessageGeneratorArgs(token)));
		}
		return handleSuccess("Preferred role set to "+preferredRole);
	}

}