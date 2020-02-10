/**
 * 
 */
package com.tibco.cep.security.authen;

import java.util.List;

import javax.security.auth.callback.Callback;

import com.tibco.cep.security.tokens.Role;

/**
 * @author aathalye
 * 
 */
public class RCPAuthCallback implements Callback {

	private String username;

	private String password;

	private List<Role> roles;

	public RCPAuthCallback() {
	}

	public RCPAuthCallback(String username, String password, List<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public final String getUsername() {
		return username;
	}

	public final String getPassword() {
		return password;
	}

	public final List<Role> getRoles() {
		return roles;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
