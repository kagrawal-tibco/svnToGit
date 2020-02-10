package com.tibco.cep.studio.wizard.as.commons.utils;

public class AIdentifiable implements IIdentifiable {

	private String id;
	private String displayName;

	protected AIdentifiable(String id) {
		this.id = id;
	}

	protected AIdentifiable(String id, String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

}
