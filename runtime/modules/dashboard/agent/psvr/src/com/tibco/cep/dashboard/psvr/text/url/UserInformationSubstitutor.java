package com.tibco.cep.dashboard.psvr.text.url;

import com.tibco.cep.kernel.service.logging.Level;

import com.tibco.cep.dashboard.security.SecurityToken;

class UserInformationSubstitutor implements Substitutor {

	private static final long serialVersionUID = 847004493205619445L;

	public static final String USR_NAME = "NAME"; // Logged in user name
	public static final String USR_ROLE = "ROLE"; // Logged in user role
	public static final String USR_PASS = "PASS"; // Logged in user password

	private static final String[] KEYS = new String[] { USR_NAME, USR_ROLE, USR_PASS };

	
	public String substitute(String substitutionKey, SubstitutionContext context) throws Exception {
		SecurityToken userToken = context.getToken();
		if ((userToken == null) || (substitutionKey == null)) {
			context.getLogger().log(Level.WARN, "Can't resolve user context with null token=" + userToken + " or key=" + substitutionKey);
			throw new Exception("Can't resolve user context with null token or key.");
		} else if (substitutionKey.equals(UserInformationSubstitutor.USR_NAME)) {
			return userToken.getUserID();
		} else if (substitutionKey.equals(UserInformationSubstitutor.USR_ROLE)) {
			if (userToken.getPreferredPrincipal() != null) {
				return userToken.getPreferredPrincipal().getName();
			} else {
				return userToken.getPrincipals()[0].getName();
			}
		} else if (substitutionKey.equals(UserInformationSubstitutor.USR_PASS)) {
			context.getLogger().log(Level.WARN, "User password is not available through context.");
			throw new Exception("User password is not available through context.");
		} else {
			context.getLogger().log(Level.WARN, "Unsupported substitution key for user context:" + substitutionKey);
			throw new Exception("Unsupported substitution key for user context:" + substitutionKey);
		}

	}

	@Override
	public String[] getSupportedKeys() {
		return KEYS;
	}

}
