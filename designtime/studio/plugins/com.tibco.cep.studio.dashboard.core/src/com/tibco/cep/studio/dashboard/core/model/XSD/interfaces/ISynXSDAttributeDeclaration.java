package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDAttributeDeclaration;

/**
 * An attribute declaration is an association between a name and a simple type definition, together with occurrence information and (optionally) a default value
 *
 */
public interface ISynXSDAttributeDeclaration extends ISynXSDSchemaComponent {

	/**
	 *
	 * @return ISynXSDSimpleType The simple type for this attribute
	 */
	public ISynXSDSimpleTypeDefinition getTypeDefinition();

	/**
	 * @return Returns the ref.
	 */
	public SynXSDAttributeDeclaration getRef();

	/**
	 * @param ref The ref to set.
	 */
	public void setRef(SynXSDAttributeDeclaration ref);

}
