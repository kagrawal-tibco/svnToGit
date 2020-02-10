package com.tibco.cep.studio.core.converter;

import java.util.Collection;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

public class PropertyDefinitionConverter extends EntityConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		PropertyDefinition newEntity = ElementFactory.eINSTANCE.createPropertyDefinition();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.PropertyDefinition.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		PropertyDefinition newProp = (PropertyDefinition) newEntity;
		com.tibco.cep.designtime.model.element.PropertyDefinition prop = (com.tibco.cep.designtime.model.element.PropertyDefinition) entity;
		
		populatePropDefProperties(newProp, prop, projectName);
		
		newProp.setType(getPropertyType(prop.getType()));
		if (prop.getConceptType() != null) {
//			newProp.setConceptType(prop.getConceptType());  // TODO : conversion work (partial)
			newProp.setConceptTypePath(prop.getConceptType().getFullPath());
		}
		newProp.setArray(prop.isArray());
		if (prop.getOwner() != null) {
//			newProp.setOwner(prop.getOwner());  // TODO : conversion work (partial)
			newProp.setOwnerPath(prop.getOwner().getFullPath());
		}
		
		newProp.setHistoryPolicy(prop.getHistoryPolicy());
		newProp.setHistorySize(prop.getHistorySize());
		newProp.setOrder(prop.getOrder());
		newProp.setDefaultValue(prop.getDefaultValue());
		newProp.getDisjointSet().addAll(prop.getDisjointSet());
		if (prop.getParent() != null) {
			newProp.setSuper(prop.getParent().getName()); // TODO : conversion work
		}
		if (newProp.eContainer() instanceof PropertyDefinitionConverter) {
			newProp.setParent((PropertyDefinition) newProp.eContainer());
		}
		newProp.setTransitive(prop.isTransitive());
	}
	
	private PROPERTY_TYPES getPropertyType(int type) {
		return PROPERTY_TYPES.get(type);
	}

	private void populatePropDefProperties(PropertyDefinition newEntity,
			com.tibco.cep.designtime.model.element.PropertyDefinition entity,
			String projectName) {
		
		PropertyDefinitionConverter converter = new PropertyDefinitionConverter();
		
		{
			Collection propertyDefinitions = entity.getEquivalentProperties();
			for (Object object : propertyDefinitions) {
				com.tibco.cep.designtime.model.element.PropertyDefinition propertyDef = (com.tibco.cep.designtime.model.element.PropertyDefinition) object;
				
				PropertyDefinition newPropertyDefinition = 
					(PropertyDefinition) converter.convertEntity(propertyDef, projectName);
				newEntity.getEquivalentProperties().add(newPropertyDefinition);
			}
		}
		{
			Collection propertyDefinitions = entity.getAttributeDefinitions();
			for (Object object : propertyDefinitions) {
				// this is a static reference and results in a loop if kept in
				com.tibco.cep.designtime.model.element.PropertyDefinition propertyDef = (com.tibco.cep.designtime.model.element.PropertyDefinition) object;
				
//				PropertyDefinition newPropertyDefinition = 
//					(PropertyDefinition) converter.convertEntity(propertyDef);
//				newEntity.getAttributeDefinitions().add(newPropertyDefinition);
			}
		}
	}
	
}
