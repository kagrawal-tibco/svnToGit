package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.providers;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeGroupDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeUse;

/**
 * The provider interface for attributes which provide support for attributes, attribute references, and attribute groups
 *
 */
public interface ISynXSDPropertyProvider {

	/**
	 * Returns the resolved attributes by recursing through the refs and attribute groups (if any) and retrieving the concrete attributes
	 *
	 * @return List A List of ISynXSDAttributeDeclaration
	 */
	public List<ISynXSDAttributeDeclaration> getProperties();

	public ISynXSDAttributeDeclaration getProperty(String attributeName);

	public void addProperty(ISynXSDAttributeDeclaration attribute);

	public void removeProperty(String attributeName);

	/**
	 * Return a subset of getRefs() that are instances of ISynXSDAttributeGroupDefinition
	 *
	 * @return List A List of ISynXSDAttributeGroupDefinition.
	 */
	public List<ISynXSDAttributeGroupDefinition> getAttributeGroups();

	public ISynXSDAttributeGroupDefinition getAttributeGroup(String attributeGroupName);

	public void addAttributeGroup(ISynXSDAttributeGroupDefinition attributeGroup);

	public void removeAttributeGroup(String attributeGroupName);

	/**
	 * @return Returns the attributeUses.
	 */
	public List<ISynXSDAttributeUse> getPropertyUses();

	public void addPropertyUse(ISynXSDAttributeUse attributeUse);

	public void removePropertyUse(ISynXSDAttributeUse attributeUse);

}