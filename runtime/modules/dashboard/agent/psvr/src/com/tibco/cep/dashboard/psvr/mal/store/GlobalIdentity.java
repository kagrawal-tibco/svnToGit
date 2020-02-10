package com.tibco.cep.dashboard.psvr.mal.store;

public class GlobalIdentity extends Identity {
	
	private static final String ID = "global";
	
	private static final int HASHCODE = ID.hashCode();
	
	private static GlobalIdentity instance;

	public static final synchronized GlobalIdentity getInstance() {
		if (instance == null) {
			instance = new GlobalIdentity();
		}
		return instance;
	}

	private GlobalIdentity() {
		
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