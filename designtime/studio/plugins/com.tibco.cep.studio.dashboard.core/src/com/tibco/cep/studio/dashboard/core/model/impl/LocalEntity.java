package com.tibco.cep.studio.dashboard.core.model.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynRequiredProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynBooleanType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;

/**
 * @
 *
 */
public abstract class LocalEntity extends LocalElement {

	public static final String PROP_KEY_PREFIX = "";

	public static final String PROP_KEY_NAMESPACE = PROP_KEY_PREFIX + "Namespace";

	public static final String PROP_KEY_LAST_MODIFIED = PROP_KEY_PREFIX + "LastModified";

	public static final String PROP_KEY_IS_SYSTEM = PROP_KEY_PREFIX + "System";

	public static final String PROP_KEY_OWNER_PROJECT = PROP_KEY_PREFIX + "OwnerProject";

	public LocalEntity() {
		super();
	}

	protected LocalEntity(boolean setup) {
		super(setup);
	}

	public LocalEntity(LocalElement parentElement) {
		super(parentElement);
	}

	public LocalEntity(LocalElement parentElement, Entity entity) {
		super(parentElement, entity);
	}

	public LocalEntity(LocalParticle parentParticle) {
		super(parentParticle);
	}

	public LocalEntity(LocalElement parentElement, String name) {
		super(parentElement, name);
	}

	// ===================================================================
	// Initialization
	// ===================================================================

	/**
	 * Sets up the default properties
	 *
	 */
	protected void setupDefaultProperties() {
		/*
		 * Elements are initially given a random ID for referential use; elements that are synchronized with a real instance of Entity will override this property with the appropriate ID from the Entity
		 */
		addProperty(this, new SynRequiredProperty(PROP_KEY_NAMESPACE, new SynStringType(), "", true));

		/*
		 * System denotes that the element is not user-defined and is maintained by the system. The user may view it but nt modify it
		 */
		addProperty(this, new SynRequiredProperty(PROP_KEY_IS_SYSTEM, new SynBooleanType(), Boolean.FALSE.toString(), true));

		/*
		 * Name of the project which holds this element
		 */
		addProperty(this, new SynRequiredProperty(PROP_KEY_OWNER_PROJECT, new SynStringType(), "", true));

		super.setupDefaultProperties();
	}

	/**
	 * In a hierarchy properties at the super type levels tends to be called upon more frequently than those at the subtype level.
	 *
	 * This method is a convenience for subtypes to call from an overridden implementation of parseMDProperty(...) so that a quick delegation can be employed as opposed to iterating through all the local properties of the
	 * subtypes first and then defaulting to the su[per type.
	 *
	 * This method is only needed if this instance can have subclasses
	 *
	 * @param propertyName
	 * @return
	 */
	protected boolean isSuperProperty(String propertyName) {
		if (true == super.isSuperProperty(propertyName)) {
			return true;
		}
		if (true == PROP_KEY_NAMESPACE.equals(propertyName)) {
			return true;
		}
		if (true == PROP_KEY_LAST_MODIFIED.equals(propertyName)) {
			return true;
		}
		if (true == PROP_KEY_IS_SYSTEM.equals(propertyName)) {
			return true;
		}
		if (true == PROP_KEY_OWNER_PROJECT.equals(propertyName)) {
			return true;
		}
		return false;
	}

	@Override
	public Entity getEObject() {
		return (Entity) super.getEObject();
	}

	/**
	 * Parses the initial properties applicable for every LocalElement
	 *
	 * Every subclass of LocalElement that has an overriding implementation of this method should make sure to delegate to this method first... unless there is a real need to override the way these properties are handled.
	 *
	 * @param propertyName
	 */
	public void parseMDProperty(String propertyName) {
		if (true == super.isSuperProperty(propertyName)) {
			super.parseMDProperty(propertyName);
			return;
		}
		Entity entity = (Entity) getEObject();
		if (entity == null) {
			return;
		} else if (true == PROP_KEY_NAMESPACE.equals(propertyName)) {
			setNamespace(entity.getNamespace());
		} else if (true == PROP_KEY_LAST_MODIFIED.equals(propertyName)) {
			// setLastModified(entity.getLastModified());
		} else if (true == PROP_KEY_IS_SYSTEM.equals(propertyName)) {
			setSystem(Boolean.valueOf(getExtendedPropertyValue(entity, "system")));
		} else if (true == PROP_KEY_OWNER_PROJECT.equals(propertyName)) {
			setOwnerProject(entity.getOwnerProjectName());
		} else {
			throw new IllegalArgumentException("[ " + propertyName + " ] is not a recognized name for a property in this element");
		}
	}

	/**
	 * Parses a property that has been set already without marking the property as modified. This is useful for refreshing a property without having to force a save of the element
	 *
	 * @param propertyName
	 * @param resetAlreadySetFlag
	 */
	public void parseMDProperty(String propertyName, boolean resetAlreadySetFlag) {
		if (false == resetAlreadySetFlag) {
			parseMDProperty(propertyName);
		} else {
			SynProperty prop = (SynProperty) getProperty(propertyName);
			if (true == prop.isAlreadySet()) {
				prop.setAlreadySet(false);
				parseMDProperty(propertyName);
			}
		}
	}

	/*
	 * public String getLastModified() { return getPropertyValueWithNoException(PROP_KEY_LAST_MODIFIED); }
	 */

	/*
	 * public void setLastModified(String value) { setPropertyValueWithNoException(PROP_KEY_LAST_MODIFIED, value); }
	 */

	public boolean isSystem() {
		return getPropertyValueWithNoException(PROP_KEY_IS_SYSTEM).equalsIgnoreCase("true");
	}

	public void setSystem(boolean value) {
		setPropertyValueWithNoException(PROP_KEY_IS_SYSTEM, String.valueOf(value));
	}

	public String getOwnerProject() {
		return getPropertyValueWithNoException(PROP_KEY_OWNER_PROJECT);
	}

	public void setOwnerProject(String value) {
		setPropertyValueWithNoException(PROP_KEY_OWNER_PROJECT, value);
	}

	@Override
	protected void synchronizeElement(EObject object) {
		super.synchronizeElement(object);
		if (object instanceof Entity) {
			Entity entity = (Entity) object;
			entity.setNamespace(getNamespace());
			entity.setOwnerProjectName(getOwnerProject());
			if (isSystem() == true) {
				setExtendedPropertyValue(entity, "system", "true");
			} else {
				deleteExtendedPropertyValue(entity, "system");
			}
		}
	}

	public String getNamespace() {
		return getPropertyValueWithNoException(PROP_KEY_NAMESPACE);
	}

	public void setNamespace(String value) {
		setPropertyValueWithNoException(PROP_KEY_NAMESPACE, value);
	}

	public String getEObjectId(EObject eObject) {
		Entity entity = (Entity) eObject;
		return entity.getGUID();
	}

	public void setEObjectId(EObject eObject, String id) {
		Entity entity = (Entity) eObject;
		entity.setGUID(id);
	}

	protected String getEObjectName(EObject eObject) {
		Entity entity = (Entity) eObject;
		return entity.getName();
	}

	protected void setEObjectName(EObject eObject, String name) {
		Entity entity = (Entity) eObject;
		entity.setName(name);
	}

	protected String getEObjectFolder(EObject eObject) {
		Entity entity = (Entity) eObject;
		return entity.getFolder();
	}

	protected void setEObjectFolder(EObject eObject, String folder) {
		Entity entity = (Entity) eObject;
		entity.setFolder(folder);
	}

	protected String getEObjectDescription(EObject eObject) {
		Entity entity = (Entity) eObject;
		return entity.getDescription();
	}

	protected void setEObjectDescription(EObject eObject, String description) {
		Entity entity = (Entity) eObject;
		entity.setDescription(description);
	}

	protected Entity getExtendedProperty(Entity eObject, String propertyName) {
		if (eObject == null) {
			return null;
		}
		PropertyMap extendedPropertiesHolder = eObject.getExtendedProperties();
		if (extendedPropertiesHolder == null) {
			return null;
		}
		List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
		if (extendedPropertyList == null) {
			return null;
		}
		for (Entity extendedProperty : extendedPropertyList) {
			if (extendedProperty.getName().equals(propertyName) == true) {
				return extendedProperty;
			}
		}
		return null;
	}

	protected String getExtendedPropertyValue(Entity eObject, String propertyName) {
		Entity extendedProperty = getExtendedProperty(eObject, propertyName);
		if (extendedProperty != null && extendedProperty instanceof SimpleProperty) {
			return ((SimpleProperty) extendedProperty).getValue();
		}
		return null;
	}

	protected Map<String, String> getExtendedPropertyValuesMap(Entity eObject, String propertyNamePrefix) {
		if (eObject == null) {
			return null;
		}
		PropertyMap extendedPropertiesHolder = eObject.getExtendedProperties();
		if (extendedPropertiesHolder == null) {
			return null;
		}
		List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
		if (extendedPropertyList == null) {
			return null;
		}
		if (propertyNamePrefix.endsWith("*") == true) {
			propertyNamePrefix = propertyNamePrefix.substring(0, propertyNamePrefix.length() - 1);
		}
		Map<String, String> values = new LinkedHashMap<String, String>();
		for (Entity extendedProperty : extendedPropertyList) {
			String name = extendedProperty.getName();
			if (propertyNamePrefix.equals("") == true) {
				values.put(name, ((SimpleProperty) extendedProperty).getValue());
			} else if (name.startsWith(propertyNamePrefix) == true) {
				name = name.substring(propertyNamePrefix.length());
				values.put(name, ((SimpleProperty) extendedProperty).getValue());
			}
		}
		return values;
	}

	protected Entity createExtendedProperty(Entity eObject, String propertyName) {
		if (eObject == null) {
			throw new IllegalStateException("Persisted entity cannot be null");
		}
		PropertyMap extendedPropertiesHolder = eObject.getExtendedProperties();
		if (extendedPropertiesHolder == null) {
			extendedPropertiesHolder = ModelFactory.eINSTANCE.createPropertyMap();
			eObject.setExtendedProperties(extendedPropertiesHolder);
		}
		Entity propertyObject = null;
		List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
		for (Entity extendedProperty : extendedPropertyList) {
			if (extendedProperty.getName().equals(propertyName) == true) {
				propertyObject = extendedProperty;
				break;
			}
		}
		if (propertyObject == null) {
			propertyObject = ModelFactory.eINSTANCE.createSimpleProperty();
			propertyObject.setName(propertyName);
			extendedPropertyList.add(propertyObject);
		}
		return propertyObject;
	}

	protected void setExtendedPropertyValue(Entity eObject, String propertyName, String propertyValue) {
		if (eObject == null) {
			throw new IllegalStateException("Persisted entity cannot be null");
		}
		PropertyMap extendedPropertiesHolder = eObject.getExtendedProperties();
		if (extendedPropertiesHolder == null) {
			extendedPropertiesHolder = ModelFactory.eINSTANCE.createPropertyMap();
			eObject.setExtendedProperties(extendedPropertiesHolder);
		}
		SimpleProperty propertyObject = null;
		List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
		for (Entity extendedProperty : extendedPropertyList) {
			if (extendedProperty.getName().equals(propertyName) == true) {
				propertyObject = (SimpleProperty) extendedProperty;
				break;
			}
		}
		if (propertyObject == null) {
			propertyObject = ModelFactory.eINSTANCE.createSimpleProperty();
			propertyObject.setName(propertyName);
			extendedPropertyList.add(propertyObject);
		}
		if (propertyValue != null) {
			propertyObject.setValue(propertyValue);
		}
	}

	protected void deleteExtendedPropertyValue(Entity eObject, String propertyName) {
		if (eObject == null) {
			throw new IllegalStateException("Persisted entity cannot be null");
		}
		PropertyMap extendedPropertiesHolder = eObject.getExtendedProperties();
		if (extendedPropertiesHolder != null) {
			SimpleProperty propertyObject = null;
			List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
			for (Entity extendedProperty : extendedPropertyList) {
				if (extendedProperty.getName().equals(propertyName) == true) {
					propertyObject = (SimpleProperty) extendedProperty;
					break;
				}
			}
			if (propertyObject != null) {
				extendedPropertyList.remove(propertyObject);
			}
		}
	}

	protected void deleteExtendedPropertyValues(Entity eObject, String propertyNamePrefix) {
		if (eObject == null) {
			throw new IllegalStateException("Persisted entity cannot be null");
		}
		if (propertyNamePrefix.endsWith("*") == true) {
			propertyNamePrefix = propertyNamePrefix.substring(0, propertyNamePrefix.length());
		}
		PropertyMap extendedPropertiesHolder = eObject.getExtendedProperties();
		if (extendedPropertiesHolder != null) {
			List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
			List<Entity> extendedPropertyToBeDeleted = new LinkedList<Entity>();
			for (Entity extendedProperty : extendedPropertyList) {
				if (extendedProperty.getName().startsWith(propertyNamePrefix) == true) {
					extendedPropertyToBeDeleted.add(extendedProperty);
				}
			}
			if (extendedPropertyToBeDeleted.isEmpty() == false) {
				extendedPropertyList.removeAll(extendedPropertyToBeDeleted);
			}
		}
	}

}