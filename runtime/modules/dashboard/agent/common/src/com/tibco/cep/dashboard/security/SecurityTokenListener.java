package com.tibco.cep.dashboard.security;

public interface SecurityTokenListener {
	
	public void tokenCreated(SecurityToken token);
	
	public void tokenDeleted(SecurityToken token);
	
	public void tokenExpired(SecurityToken token);

}
