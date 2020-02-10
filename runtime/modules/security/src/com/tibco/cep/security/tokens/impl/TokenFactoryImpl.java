/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.security.tokens.impl;

import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Authen;
import com.tibco.cep.security.tokens.Authz;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.tokens.TokenFactory;
import com.tibco.cep.security.tokens.User;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 */
public class TokenFactoryImpl implements TokenFactory {
	
	private static TokenFactoryImpl instance;

	public static final synchronized TokenFactoryImpl getInstance() {
		if (instance == null) {
			instance = new TokenFactoryImpl();
		}
		return instance;
	}

	private TokenFactoryImpl() {
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AuthToken createAuthToken() {
		AuthTokenImpl authToken = new AuthTokenImpl();
		return authToken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Authen createAuthen() {
		AuthenImpl authen = new AuthenImpl();
		return authen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Authz createAuthz() {
		AuthzImpl authz = new AuthzImpl();
		return authz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public User createUser() {
		UserImpl user = new UserImpl();
		return user;
	}

}
