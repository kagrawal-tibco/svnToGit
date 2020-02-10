package com.tibco.cep.dashboard.psvr.variables;

import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.variables.Variable;

class USRVariableValueProvider implements VariableValueProvider {

	public static final String USR_TOKEN = "TOKEN"; // Logged in user token
	public static final String USR_NAME = "NAME"; // Logged in user name
	public static final String USR_ROLE = "ROLE"; // Logged in user role

	@Override
	public String getIdentifier() {
		return "USR";
	}

	@Override
	public String getValue(VariableContext context, Variable variable) {
		SecurityToken userToken = context.getToken();
		if (userToken == null) {
			context.getLogger().log(Level.WARN, "No token specified for "+variable);
			return "";
		}
		if (variable.getArgument().equalsIgnoreCase(USR_TOKEN) == true) {
			return userToken.toString();
		}
		if (variable.getArgument().equalsIgnoreCase(USR_NAME) == true) {
			return userToken.getUserID();
		}
		if (variable.getArgument().equalsIgnoreCase(USR_ROLE) == true) {
			if (userToken.getPreferredPrincipal() != null) {
				return userToken.getPreferredPrincipal().getName();
			} else {
				return userToken.getPrincipals()[0].getName();
			}
		}
		context.getLogger().log(Level.WARN, "Unsupported argument in " + variable);
		return "";
	}

}