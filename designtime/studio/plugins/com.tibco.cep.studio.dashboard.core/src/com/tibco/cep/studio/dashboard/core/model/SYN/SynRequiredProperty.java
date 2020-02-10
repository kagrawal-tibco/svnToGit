package com.tibco.cep.studio.dashboard.core.model.SYN;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeUseRequired;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;

/**
 * @ *
 */
public class SynRequiredProperty extends SynProperty implements ISynXSDAttributeUseRequired {

	/**
	 * @param name
	 * @param type
	 */
	public SynRequiredProperty(String name, ISynXSDSimpleTypeDefinition type) {
		super(name, type);
	}

	/**
	 * @param name
	 * @param type
	 * @param defaultValue
	 */
	public SynRequiredProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue) {
		super(name, type, defaultValue);
	}

	/**
	 * @param name
	 * @param type
	 * @param defaultValue
	 * @param isSystem
	 */
	public SynRequiredProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue, boolean isSystem) {
		super(name, type, defaultValue, isSystem);
	}

	public Object cloneThis() throws Exception {
		return new SynRequiredProperty(getName(), (ISynXSDSimpleTypeDefinition) getTypeDefinition().cloneThis(), getDefault(), isSystem());
	}

}
