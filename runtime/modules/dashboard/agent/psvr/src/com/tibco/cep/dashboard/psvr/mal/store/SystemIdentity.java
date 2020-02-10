package com.tibco.cep.dashboard.psvr.mal.store;

public class SystemIdentity extends Identity {

	private static final String ID = "system";

	private static final int HASHCODE = ID.hashCode();

	private static SystemIdentity instance;

	public static final synchronized SystemIdentity getInstance() {
		if (instance == null) {
			instance = new SystemIdentity();
		}
		return instance;
	}

	private SystemIdentity() {

	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Identity getParent() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return HASHCODE;
	}

	@Override
	public String toString() {
		return ID;
	}
}