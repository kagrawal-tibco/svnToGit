package com.tibco.cep.dashboard.integration.embedded;

import java.security.Principal;

import com.tibco.cep.dashboard.security.InvalidPrincipalException;
import com.tibco.cep.dashboard.security.InvalidTokenException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class ESecurityToken implements SecurityToken {
	
	private String userID;
	
	private String role;
	
	private Principal principal;
	
	ESecurityToken(String userID, final String role) {
		super();
		this.userID = userID;
		this.role = role;
		this.principal = new Principal(){

			@Override
			public String getName() {
				return role;
			}
			
		};
	}

	@Override
	public Principal[] getEffectivePrincipals() {
		return new Principal[]{principal};
	}

	@Override
	public Principal getPreferredPrincipal() {
		return principal;
	}

	@Override
	public Principal[] getPrincipals() {
		return getEffectivePrincipals();
	}

	@Override
	public String getUserID() {
		return userID;
	}

	@Override
	public boolean isSystem() {
		return false;
	}

	@Override
	public void setPreferredPrincipal(Principal principal) throws InvalidPrincipalException {
		
	}

	@Override
	public void touched() {
		
	}

	@Override
	public void verify() throws InvalidTokenException {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	@Override
	public boolean equals(SecurityToken obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ESecurityToken other = (ESecurityToken) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return userID;
	}

}
