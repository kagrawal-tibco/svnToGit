package com.tibco.cep.dashboard.security;

import java.security.GeneralSecurityException;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

import com.tibco.cep.dashboard.management.Client;
import com.tibco.cep.dashboard.management.ServiceUnavailableException;

public interface SecurityClient extends Client {

	public SecurityToken login(String username, String password) throws AccountNotFoundException, LoginException, GeneralSecurityException;

	public void logout(SecurityToken token) throws ServiceUnavailableException;

	public void addSecurityTokenListener(SecurityTokenListener listener) throws ServiceUnavailableException;

	public void removeSecurityTokenListener(SecurityTokenListener listener) throws ServiceUnavailableException;

	public SecurityToken convert(String token) throws InvalidTokenException;

	public void verify(SecurityToken securityToken) throws InvalidTokenException;

}