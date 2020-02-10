package com.tibco.cep.studio.dashboard.core.model.SYN;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeUseOptional;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;

/**
 *
 */
public class SynOptionalProperty extends SynProperty implements ISynXSDAttributeUseOptional {

	/**
	 * @param name
	 * @param type
	 */
	public SynOptionalProperty(String name, ISynXSDSimpleTypeDefinition type) {
		super(name, type);
		type.setNullable(true);
	}

	/**
	 * @param name
	 * @param type
	 * @param defaultValue
	 */
	public SynOptionalProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue) {
		super(name, type, defaultValue);
		type.setNullable(true);
	}

	/**
	 * @param name
	 * @param type
	 * @param defaultValue
	 * @param isSystem
	 */
	public SynOptionalProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue, boolean isSystem) {
		super(name, type, defaultValue, isSystem);
		type.setNullable(true);
	}

	public Object cloneThis() throws Exception {
		return new SynOptionalProperty(getName(), (ISynXSDSimpleTypeDefinition) getTypeDefinition().cloneThis(), getDefault(), isSystem());
	}
}
