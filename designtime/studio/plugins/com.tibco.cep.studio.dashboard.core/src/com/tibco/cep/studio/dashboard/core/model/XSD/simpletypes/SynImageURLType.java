package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

public class SynImageURLType extends SynStringType {

	private static final String[] ALLOWED_TYPES = new String[] { "jpg", "gif", "png" };

	public SynImageURLType() {
		super();
	}

	public Object cloneThis() throws Exception {
		SynImageURLType clone = new SynImageURLType();
		super.cloneThis(clone);
		return clone;
	}

	public String[] getAllowedImageTypes() {
		return ALLOWED_TYPES;
	}

}