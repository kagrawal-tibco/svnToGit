package com.tibco.cep.dashboard.security;

import java.security.Principal;

class PrincipalImpl implements Principal{
	
	private String name;

	PrincipalImpl(String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Principal){
			return name.equals(((Principal)obj).getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
	
}