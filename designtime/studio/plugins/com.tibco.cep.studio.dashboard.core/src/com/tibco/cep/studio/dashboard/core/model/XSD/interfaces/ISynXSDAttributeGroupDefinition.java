package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers.ISynXSDPropertyProvider;

/**
 * Attribute groups have attributes and also can reference to other attribute
 * groups
 *
 */
public interface ISynXSDAttributeGroupDefinition extends ISynXSDSchemaComponent, ISynXSDPropertyProvider {

	/**
	 * @return Returns the ref.
	 */
	public ISynXSDAttributeGroupDefinition getRef();

	/**
	 * @param ref The ref to set.
	 */
	public void setRef(ISynXSDAttributeGroupDefinition ref);

}
