package com.tibco.cep.dashboard.psvr.mal.store;

public class RoleIdentity extends Identity {
	
	private final String id;
	private final Identity parent;
	private String stringRepresentation;
	

	public RoleIdentity(Identity parent, String id) {
		this.parent = parent;
		this.id = id;
		stringRepresentation = id;
	}

	public RoleIdentity(String id) {
		this(null, id);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Identity getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		return stringRepresentation.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoleIdentity) {
			return id.equals(((RoleIdentity) obj).id);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return stringRepresentation;
	}	

}
