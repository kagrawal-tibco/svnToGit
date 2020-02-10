package com.tibco.cep.security.tokens.impl;

import com.tibco.cep.security.tokens.User;

public class UserImpl implements User {
	
	private static final long serialVersionUID = 6949010914704630099L;
	
	private String username;
	private String password;

	protected UserImpl() {

	}

	@Override
	public void setUsername(String value) {
		this.username = value;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String value) {
		this.password = value;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserImpl other = (UserImpl) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return username;
	}	
}
