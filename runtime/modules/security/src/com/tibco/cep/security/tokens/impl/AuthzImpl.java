package com.tibco.cep.security.tokens.impl;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.security.tokens.Authz;
import com.tibco.cep.security.tokens.Role;

public class AuthzImpl implements Authz {
	
	private static final long serialVersionUID = -8140604091698765625L;
	
	private List<Role> roles;
	
	protected AuthzImpl(){
		roles = new LinkedList<Role>();
	}

	@Override
	public List<Role> getRoles() {
		return roles;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthzImpl other = (AuthzImpl) obj;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Authz.class.getSimpleName());
		sb.append("[");
		sb.append("roles="+roles);
		sb.append("]");
		return sb.toString();
	}
}
