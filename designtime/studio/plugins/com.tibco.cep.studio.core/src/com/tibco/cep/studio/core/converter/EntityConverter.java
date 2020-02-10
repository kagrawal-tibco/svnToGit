package com.tibco.cep.studio.core.converter;

import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;

public abstract class EntityConverter {

	protected EntityConverter fInstance;
	protected List<Entity> fConvertedEntities;
	
	public abstract Entity convertEntity(
			com.tibco.cep.designtime.model.Entity entity, String projectName);
	
	public boolean canConvert(Object obj) {
		return getInstance().getConverterClass().isAssignableFrom(obj.getClass());
	}
	
	public EntityConverter getInstance() {
		if (fInstance == null) {
			fInstance = this;
		}
		return fInstance;
	}

	public abstract Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass();
	
	public void setEntityList(List<Entity> entityList) {
		this.fConvertedEntities = entityList;
	}
	
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		newEntity.setName(entity.getName());
		newEntity.setOwnerProjectName(projectName);
		if (entity.getFolder() != null) {
			newEntity.setFolder(entity.getFolder().getFullPath());
		}
		newEntity.setLastModified(entity.getLastModified());
		newEntity.setDescription(entity.getDescription());
		newEntity.setNamespace(entity.getNamespace());
		newEntity.setGUID(entity.getGUID());
		populateProperties(newEntity, entity);
		return;
	}
	
	
	private void populateProperties(Entity newEntity,
			com.tibco.cep.designtime.model.Entity entity) {

		// convert hidden properties
		if (entity.getHiddenProperties() != null && entity.getHiddenProperties().size() > 0) {
			PropertyMap hiddenPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			newEntity.setHiddenProperties(hiddenPropertyMap);
			Map hiddenProperties = entity.getHiddenProperties();
			for (Object propKey : hiddenProperties.keySet()) {
				String key = (String) propKey;
				Object val = hiddenProperties.get(key);
				Entity hiddenProperty = createProperty(key, val);
				hiddenPropertyMap.getProperties().add(hiddenProperty);
			}
		}
		
		// convert extended properties
		if (entity.getExtendedProperties() != null && entity.getExtendedProperties().size() > 0) {
			PropertyMap extendedPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			newEntity.setExtendedProperties(extendedPropertyMap);
			Map extendedProperties = entity.getExtendedProperties();
			for (Object propKey : extendedProperties.keySet()) {
				String key = (String) propKey;
				Object val = extendedProperties.get(key);
				Entity extendedProperty = createProperty(key, val);
				extendedPropertyMap.getProperties().add(extendedProperty);
			}
			
		}
		
		// I assume the transient properties are not persisted and therefore
		// do not need to be converted
		if (entity.getTransientProperties() != null && entity.getTransientProperties().size() > 0) {
			System.out.println("found transient properties during conversion");
		}
	}

	protected Entity createProperty(String key, Object val) {
		if (val instanceof String) {
			SimpleProperty simpleProperty = ModelFactory.eINSTANCE.createSimpleProperty();
			simpleProperty.setName(key);
			simpleProperty.setValue((String) val);
			return simpleProperty;
		} else if (val instanceof Map) {
			Map propMap = (Map) val;
			PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			
			for (Object prop : propMap.keySet()) {
				String propKey = (String) prop;
				Object propObj = propMap.get(propKey);
				Entity subProperty = createProperty(propKey, propObj);
				propertyMap.getProperties().add(subProperty);
			}

			ObjectProperty objProperty = ModelFactory.eINSTANCE.createObjectProperty();
			objProperty.setName(key);
			objProperty.setValue(propertyMap);

			return objProperty;
		} else {
			SimpleProperty simpleProperty = ModelFactory.eINSTANCE.createSimpleProperty();
			simpleProperty.setName(key);
			simpleProperty.setValue(String.valueOf(val));
			return simpleProperty;
		}
	}
	
	
}
