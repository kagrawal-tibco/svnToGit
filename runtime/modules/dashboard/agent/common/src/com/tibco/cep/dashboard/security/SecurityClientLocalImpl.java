package com.tibco.cep.dashboard.security;

import java.security.GeneralSecurityException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

import com.tibco.cep.dashboard.management.AbstractLocalClientImpl;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ServiceUnavailableException;

public final class SecurityClientLocalImpl extends AbstractLocalClientImpl implements SecurityClient {
	
	private SecurityService securityService;

	public SecurityClientLocalImpl() {
		super(SecurityService.NAME);
		securityService = (SecurityService) service;
	}

	public SecurityToken login(String username, String password) throws ServiceUnavailableException, AccountNotFoundException, LoginException, GeneralSecurityException {
		return securityService.login(username, password);
	}

	public void logout(SecurityToken token) throws ServiceUnavailableException {
		securityService.logout(token);
	}

	public void addSecurityTokenListener(SecurityTokenListener listener) throws ServiceUnavailableException {
		securityService.addSecurityTokenListener(listener);
	}

	public void removeSecurityTokenListener(SecurityTokenListener listener) throws ServiceUnavailableException {
		securityService.removeSecurityTokenListener(listener);
	}

	public SecurityToken convert(String token) throws ServiceUnavailableException, InvalidTokenException {
		return securityService.convert(token);
	}

	public void verify(SecurityToken securityToken) throws ServiceUnavailableException, InvalidTokenException {
		securityService.verify(securityToken);
	}

	@Override
	public void cleanup() throws ManagementException {
		//do nothing
	}

}