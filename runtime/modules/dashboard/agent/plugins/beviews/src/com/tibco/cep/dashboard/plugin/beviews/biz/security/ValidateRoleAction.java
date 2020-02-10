package com.tibco.cep.dashboard.plugin.beviews.biz.security;

import java.security.Principal;

import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.security.InvalidPrincipalException;
import com.tibco.cep.dashboard.security.InvalidTokenException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ValidateRoleAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String role = request.getParameter("role");
		MALSession session = null;
		FakeSecurityToken fakeToken = null;
		try {
			Principal existingRole = getExistingRole(token.getPrincipals(), role);
			if (existingRole == null) {
				return handleError(getMessage("validaterole.nonexistent.principal", getMessageGeneratorArgs(token, role)));
			}
			// create a fake token
			fakeToken = new FakeSecurityToken(token);
			// set the role on the fake token
			fakeToken.setPreferredPrincipal(existingRole);
			// create a mal session
			session = new MALSession(fakeToken);
			return handleSuccess(getMessage("validaterole.valid.principal", getMessageGeneratorArgs(token, role)));
		} catch (InvalidPrincipalException e) {
			return handleError(getMessage("validaterole.invalid.principal", getMessageGeneratorArgs(token, role)));
		} catch (MALException e) {
			exceptionHandler.handleException(e);
			return handleError(getMessage("validaterole.general.failure", getMessageGeneratorArgs(token, e, role)));
		} catch (ElementNotFoundException e) {
			exceptionHandler.handleException(e);
			return handleError(getMessage("validaterole.element.notfound.failure", getMessageGeneratorArgs(token, e, role)));
		} finally {
			if (session != null) {
				session.close();
			}
			fakeToken = null;
		}
	}

	protected Principal getExistingRole(Principal[] principals, String role) {
		for (Principal principal : principals) {
			if (principal.getName().equals(role) == true) {
				return principal;
			}
		}
		return null;
	}

	private class FakeSecurityToken implements SecurityToken {

		private SecurityToken token;

		private Principal role;

		FakeSecurityToken(SecurityToken token) {
			this.token = token;
		}

		public boolean equals(SecurityToken token) {
			return token.equals(token);
		}

		public Principal[] getEffectivePrincipals() {
			if (role != null) {
				return new Principal[] { role };
			}
			return token.getEffectivePrincipals();
		}

		public Principal getPreferredPrincipal() {
			return role;
		}

		public Principal[] getPrincipals() {
			return token.getPrincipals();
		}

		public String getUserID() {
			return token.getUserID();
		}

		public boolean isSystem() {
			return token.isSystem();
		}

		public void setPreferredPrincipal(Principal principal) throws InvalidPrincipalException {
			for (Principal existingPrincipal : getPrincipals()) {
				if (existingPrincipal.equals(principal) == true) {
					role = principal;
					return;
				}
			}
			throw new InvalidPrincipalException(principal.getName() + " is invalid");
		}

		public String toString() {
			return token.toString();
		}

		public void touched() {
			token.touched();
		}

		public void verify() throws InvalidTokenException {
			token.verify();
		}

	}
}
