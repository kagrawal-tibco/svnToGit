package com.tibco.cep.security.tokens.impl;

import com.tibco.cep.security.tokens.Role;

public class RoleImpl implements Role {

	private static final long serialVersionUID = 7427638348361539831L;
	
	private String name;
	
	protected RoleImpl(){
		
	}
	
	@Override
	public void setName(String value) {
		this.name = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isSemanticallyEquivalent(Role other) {
		if (other == null) {
			return false;
		}
		if (this == other) {
			return true;
		}
		if (this.getName().intern() == other.getName().intern()) {
			return true;
		}
		return false;		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleImpl other = (RoleImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
