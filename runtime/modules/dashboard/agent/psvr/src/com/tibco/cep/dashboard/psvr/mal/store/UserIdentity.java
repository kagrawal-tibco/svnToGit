package com.tibco.cep.dashboard.psvr.mal.store;

public class UserIdentity extends Identity {

	private String id;
	private final Identity parent;
	private String stringRepresentation;
	
	public UserIdentity(Identity parent, String id) {
		if (parent == null){
			throw new IllegalArgumentException("parent cannot be null");
		}
		this.parent = parent;
		this.id = id;
		stringRepresentation = id;
		if (parent != null) {
			stringRepresentation = stringRepresentation + "@" + parent.getId();
		}
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
		if (obj == this){
			return true;
		}
		if (obj instanceof UserIdentity){
			return stringRepresentation.equals(((UserIdentity)obj).stringRepresentation);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return stringRepresentation;
	}

}
