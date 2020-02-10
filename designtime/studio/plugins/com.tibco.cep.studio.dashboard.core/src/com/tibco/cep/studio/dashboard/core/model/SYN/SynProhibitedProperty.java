package com.tibco.cep.studio.dashboard.core.model.SYN;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeUseProhibited;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;

/**
 * @ *
 */
public class SynProhibitedProperty extends SynProperty implements ISynXSDAttributeUseProhibited {

	/**
	 * @param name
	 * @param type
	 */
	public SynProhibitedProperty(String name, ISynXSDSimpleTypeDefinition type) {
		super(name, type);
	}

	/**
	 * @param name
	 * @param type
	 * @param defaultValue
	 */
	public SynProhibitedProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue) {
		super(name, type, defaultValue);
	}

	/**
	 * @param name
	 * @param type
	 * @param defaultValue
	 * @param isSystem
	 */
	public SynProhibitedProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue, boolean isSystem) {
		super(name, type, defaultValue, isSystem);
	}

	public Object cloneThis() throws Exception {
		return new SynProhibitedProperty(getName(), (ISynXSDSimpleTypeDefinition) getTypeDefinition().cloneThis(), getDefault(), isSystem());
	}

}
