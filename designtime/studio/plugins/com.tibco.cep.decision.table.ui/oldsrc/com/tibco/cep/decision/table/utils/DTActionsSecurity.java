package com.tibco.cep.decision.table.utils;

import java.util.List;

import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;

public final class DTActionsSecurity {

	
	public static Permit verifyAccess(
			final String resourcePath,
			final com.tibco.cep.security.authz.utils.ResourceType resourceType,
			final String action) throws Exception {
		Permit permit = Permit.DENY;
		if (resourcePath == null || resourceType == null || action == null) {
			throw new IllegalArgumentException(
					"Invalid values of parameters");
		}
		// Fetch the Token
		AuthToken token = AuthTokenUtils.getToken();
		if (token != null) {
			// Get the roles
			List<Role> roles = token.getAuthz().getRoles();
			IAction rfAction = ActionsFactory.getAction(resourceType,
					action);
			permit = SecurityUtil.checkACL(resourcePath, resourceType,
					roles, rfAction);
		}
		return permit;
	}
}
