package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

/**
 * String properties are free form text string and so do not need additional validation aside from the default null validation of the
 *
 */
public class SynCharacterType extends SynPrimitiveType {

	public SynCharacterType() {
		super(char.class);
	}

	public SynCharacterType(String[] enumValues) {
		super(char.class, enumValues);
	}

	public Object cloneThis() throws Exception {
		SynCharacterType clone = new SynCharacterType();
		super.cloneThis(clone);
		return clone;
	}
}