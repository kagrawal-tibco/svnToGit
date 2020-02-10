package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;

public interface ILocalConfigHelper {

	/**
	 * Returns the value for the given property of the config passed in.
	 * @param config
	 * @param property
	 * @return
	 * @throws Exception
	 */
	public abstract String getPropertyValue(LocalConfig config, LocalPropertyConfig propertyConfig);

	/**
	 * Sets the value in the MDS for config's property.
	 * @param localConfig
	 * @param propertyName
	 * @param propertyValue
	 * @throws Exception
	 */
	public abstract void setPropertyValue(LocalConfig localConfig, EObject eObject, LocalPropertyConfig propertyConfig, Object propertyValue);

	/**
	 * Returns the value for the given property of the config passed in.
	 * @param config
	 * @param property
	 * @return
	 * @throws Exception
	 */
	public abstract String getPropertyValue(LocalConfig config, String property);

	/**
	 * Sets the value in the MDS for config's property.
	 * @param localConfig
	 * @param propertyName
	 * @param propertyValue
	 * @throws Exception
	 */
	public abstract void setPropertyValue(LocalConfig localConfig, EObject eObject, String propertyName, Object propertyValue);

	/**
	 * Sets the value as null for primitive types and clears the list for reference values.
	 * @param localConfig
	 * @param propertyName
	 * @throws Exception
	 */
	public abstract void removePropertyValue(LocalConfig localConfig, EObject eObject, String propertyName);

	/**
	 * Sets the value as null for primitive types and clears the list for reference values.
	 * @param localConfig
	 * @param propertyName
	 * @throws Exception
	 */
	public abstract void removePropertyValue(LocalConfig localConfig, EObject eObject, LocalPropertyConfig propertyConfig);


	/**
	 * Creates MDConfig element as child element. Works with ConfigStructureValue.
	 * @param parentConfigType
	 * @param config
	 * @param particle
	 * @param childName
	 * @return
	 * @throws Exception
	 */
	public abstract EObject createChild(LocalConfig config, LocalParticleConfig lph, String childName);

	/**
	 * Removes the child MDConfig. Works with ConfigStructureValue.
	 * @param parent
	 * @param childParticle
	 * @param childName
	 * @throws Exception
	 */
	public abstract void deleteChild(LocalConfig parent, LocalParticleConfig lph, String childName);

	/**
	 * Used for setting the reference children. Works with ConfigInstanceRefValue.
	 * @param parent
	 * @param path
	 * @param maxOccurs
	 * @param elements
	 * @throws Exception
	 */
	public abstract void setConfigReferenceChild(LocalConfig parent, EObject eObject, String path, long maxOccurs, List<LocalElement> elements);

	/**
	 * Used to save the MDElement as references. Works with MdReferenceValue.
	 * @param parent
	 * @param childParticle
	 * @param elements
	 * @throws Exception
	 */
	public abstract void setEReferenceChild(LocalConfig parent, EObject eObject, LocalParticle childParticle, List<LocalElement> elements);

	/**
	 * Reorders the children under the LocalConfig in MDS. Works with ConfigStructureValue.
	 * @param config
	 * @param particle
	 * @throws Exception
	 */
	public abstract void setParticleChildrenOrder(LocalConfig config, LocalParticle particle);

	/**
	 * Get an object given its path. This method is called to retrieve MdObject for properties.
	 * There is intermediate type name for particle path. ASSUMPTION: All type in the intermediate
	 * path are not reference classes.
	 *
	 * @param startLocalObject
	 * @param propertyHelper
	 * @param create
	 * @return
	 * @throws Exception
	 */
	public EObject getEObject(LocalConfig config, EObject eObject, LocalPropertyConfig propertyHelper, boolean create);

	/**
	 * Return its own field or inherited field.
	 * @param configType
	 * @param name
	 * @return
	 * @throws Exception
	 */
	//public MDConfigFieldType getInheritedConfigFieldTypeByName(MDConfigType configType, String name);

	//public MDConfigType getCompatibleConfigType(MDConfigType configType, String expectedConfigType);

	public abstract EObject[] getChildren(LocalConfig localConfig, LocalParticleConfig lph);


}