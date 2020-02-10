package com.tibco.cep.dashboard.security;

import java.security.Principal;

import com.tibco.cep.dashboard.management.ServiceUnavailableException;

public interface SecurityToken {

	/**
	 * Check whether the logon session for the security token is still valid.
	 * @throws ServiceUnavailableException 
	 * @throws InvalidTokenException 
	 */
	public void verify() throws InvalidTokenException;

	/**
	 * Return the effective principals, either the preferred principal if it's
	 * set, or the original principal list.
	 */
	public Principal[] getEffectivePrincipals();

	/**
	 * Return the principal information. The list includes types of Principal
	 * and Group. Note that a Group is also a Principal.
	 */
	public Principal[] getPrincipals();

	/**
	 * Return the preferred principal.
	 */
	public Principal getPreferredPrincipal();

	/**
	 * Set a preferred principal to be used
	 */
	public void setPreferredPrincipal(Principal principal) throws InvalidPrincipalException;

//	/**
//	 * Return the list of groups.
//	 */
//	public Group[] getGroups() throws GeneralSecurityException;

	/**
	 * Return the userid of the subject
	 */
	public String getUserID();

	/**
	 * Externalize the security token into a string. The security token can be
	 * got back from the string via the AuthService.fromTokenString().
	 */
	public String toString();

	/**
	 * See if the tokens are the same. Two tokens are the same if their token Id
	 * are the same.
	 */
	public boolean equals(SecurityToken token);
	
	public void touched();
	
	public boolean isSystem();
	
}
