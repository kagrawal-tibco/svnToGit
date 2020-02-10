package com.tibco.cep.security.tokens.impl;

import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.tokens.Authen;
import com.tibco.cep.security.tokens.Authz;

public class AuthTokenImpl implements AuthToken {

	private static final long serialVersionUID = 4305050451451966741L;

	private Authen authen;
	private Authz authz;
	
	protected AuthTokenImpl() {
		
	}

	@Override
	public void setAuthen(Authen value) {
		this.authen = value;
	}

	@Override
	public Authen getAuthen() {
		return authen;
	}

	@Override
	public void setAuthz(Authz value) {
		this.authz = value;

	}

	@Override
	public Authz getAuthz() {
		return this.authz;

	}

	@Override
	public String getName() {
		if (authen != null && authen.getUser() != null) {
			authen.getUser().getUsername();
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthTokenImpl other = (AuthTokenImpl) obj;
		if (authen == null) {
			if (other.authen != null)
				return false;
		} else if (!authen.equals(other.authen))
			return false;
		if (authz == null) {
			if (other.authz != null)
				return false;
		} else if (!authz.equals(other.authz))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(AuthToken.class.getSimpleName());
		sb.append("[");
		sb.append("authen="+authen);
		sb.append(",authz="+authz);
		sb.append("]");
		return sb.toString();
	}

}