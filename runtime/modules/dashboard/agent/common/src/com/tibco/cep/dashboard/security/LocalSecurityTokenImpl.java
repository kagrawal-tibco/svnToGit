package com.tibco.cep.dashboard.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocalSecurityTokenImpl implements SecurityToken {

	private String token = UUID.randomUUID().toString();
	
	private long creationTime = System.currentTimeMillis();
	
	private boolean system;
	
	private transient String userName;

	private transient List<Principal> principals;

	private transient Principal preferredPrincipal;

	private Principal[] principalsAsArray;

	private long lastTouchedTime;
	
	SecurityService securityService;

	LocalSecurityTokenImpl(String userName, List<?> roles, boolean system) {
		super();
		this.userName = userName;
		this.system = system;
		principals = new ArrayList<Principal>(roles.size());
		for (Object role : roles) {
			principals.add(new PrincipalImpl(((Principal)role).getName()));
		}
		principalsAsArray = principals.toArray(new Principal[principals.size()]);
		lastTouchedTime = System.currentTimeMillis();
	}

	@Override
	public Principal[] getEffectivePrincipals() {
		if (preferredPrincipal != null) {
			return new Principal[] { preferredPrincipal };
		}
		return getPrincipals();
	}

	@Override
	public Principal getPreferredPrincipal() {
		return preferredPrincipal;
	}

	@Override
	public Principal[] getPrincipals() {
		return principalsAsArray;
	}

	@Override
	public String getUserID() {
		return userName;
	}

	@Override
	public void setPreferredPrincipal(Principal principal) throws InvalidPrincipalException {
		if (principals.contains(principal) == false) {
			throw new InvalidPrincipalException(principal.getName() + " is invalid");
		}
		preferredPrincipal = principal;
	}

	@Override
	public void verify() throws InvalidTokenException {
		securityService.verify(this);
	}

	@Override
	public int hashCode() {
		return token.hashCode();
	}

	@Override
	public String toString() {
		return token;
	}

	@Override
	public boolean equals(SecurityToken token) {
		return token.toString().equals(token);
	}
	
	@Override
	public void touched() {
		this.lastTouchedTime = System.currentTimeMillis();
	}
	
	long getLastTouchedTime(){
		return this.lastTouchedTime;
	}

	@Override
	public boolean isSystem() {
		return system;
	}
	
	long getCreationTime(){
		return creationTime;
	}
}