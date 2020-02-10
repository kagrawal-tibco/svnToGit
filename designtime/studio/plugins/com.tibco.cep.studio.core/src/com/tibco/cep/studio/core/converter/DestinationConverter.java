package com.tibco.cep.studio.core.converter;


import java.util.Properties;
import java.util.Set;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.core.model.service.channel.SerializerInfo;
import com.tibco.cep.studio.core.StudioCorePlugin;


public class DestinationConverter extends EntityConverter {
	
	//Keep reference to driver type as we do not have DriverConfig yet
	private DRIVER_TYPE driverType;
	
	public DestinationConverter(DRIVER_TYPE driverType) {
		this.driverType = driverType;
	}

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		Destination newEntity = ChannelFactory.eINSTANCE.createDestination();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.service.channel.Destination.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		Destination newDestination = (Destination) newEntity;
		com.tibco.cep.designtime.model.service.channel.Destination destination = (com.tibco.cep.designtime.model.service.channel.Destination) entity;
		
		String eventURI = destination.getEventURI();
		if (eventURI.equalsIgnoreCase("")) {
			eventURI =  null;
		}
		
		newDestination.setEventURI(eventURI);
		newDestination.setDescription(destination.getDescription()== null ? "" : destination.getDescription());
		String serializerDeserializerClass = destination.getSerializerDeserializerClass();
		if (serializerDeserializerClass == null || serializerDeserializerClass.length() == 0) {
			//Get default serializer class

            //todo This is just a temporary fix! StudioCorePlugin.getDefault() is incompatible with use in command line import! Replace!
            DriverManager driverManager = StudioCorePlugin.getDefault().getDriverManager();
			SerializerInfo serializerInfo = driverManager.getDefaultSerializer(driverType);
			//This can be null for a channel like Local
			if (serializerInfo != null) {
				serializerDeserializerClass = serializerInfo.getSerializerClass();
			}
		}
		
		if (serializerDeserializerClass != null) {
			newDestination.setSerializerDeserializerClass(serializerDeserializerClass);
		}
		final Properties properties = destination.getProperties();
		PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		newDestination.setProperties(propertyMap);
		if (properties != null){
			Set<Object> keySet = properties.keySet();
			for (Object object : keySet) {
				String propName = (String) object;
				SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
				property.setName(propName);
				property.setValue(properties.getProperty(propName));
				propertyMap.getProperties().add(property);
			}
//todo: API has changed, must rewrite.
//			// create a new EMF properties
//			com.tibco.cep.designtime.core.model.element.Instance emfInstance = ElementFactory.eINSTANCE.createInstance();
//			newDestination.setProperties(emfInstance);
//			emfInstance.setName(properties.getName());
//			emfInstance.setDescription(properties.getDescription());
//			emfInstance.setFolder(properties.getFolderPath());
//			emfInstance.setLastModified(properties.getLastModified());
//			emfInstance.setOwnerProjectName(newDestination.getOwnerProjectName());
//			Concept concept = properties.getConcept();
//			if (concept != null){
//				// create new EMF Concept
//				com.tibco.cep.designtime.core.model.element.Concept emfConcept = ElementFactory.eINSTANCE.createConcept();
//				emfConcept.setName(concept.getName());
//				emfConcept.setFolder(concept.getFolderPath());
//				for (Object prop : concept.getPropertyDefinitions(true)){
//					if (prop instanceof PropertyDefinition){
//						PropertyDefinition propDef = (PropertyDefinition)prop;
//						// create new EMF Property Definition
//						com.tibco.cep.designtime.core.model.element.PropertyDefinition emfPropDef = ElementFactory.eINSTANCE.createPropertyDefinition();
//						emfConcept.getProperties().add(emfPropDef);
//						emfPropDef.setName(propDef.getName());
//						emfPropDef.setDefaultValue(propDef.getDefaultValue());
//						emfPropDef.setConceptTypePath(propDef.getConceptTypePath());
//						emfPropDef.setType(PROPERTY_TYPES.get(propDef.getType()));
//					}
//				}
//			}
//
//			Collection propertyCollection = properties.getAllPropertyInstances();
//			if (propertyCollection != null){
//				for (Object obj : propertyCollection){
//					if (obj instanceof Collection){
//						Collection propCollection = (Collection)obj;
//						for (Object ob : propCollection){
//							if (ob instanceof PropertyInstance){
//								// create new properties of Property Instance
//								com.tibco.cep.designtime.core.model.element.PropertyInstance emfPropInstance = ElementFactory.eINSTANCE.createPropertyInstance();
//								emfInstance.getProperties().add(emfPropInstance);
//								PropertyInstance propertyInstance = (PropertyInstance)ob;
//								String defName = propertyInstance.getPropertyDefinitionName();
//								String val = propertyInstance.getValue();
//								emfPropInstance.setDefinitionName(defName);
//								emfPropInstance.setName(propertyInstance.getName());
//								emfPropInstance.setValue(val);
//								emfPropInstance.setDescription(propertyInstance.getDescription());
//								//emfPropInstance.setInstance(emfInstance);
//							}
//						}
//					}
//				}
//			}
		}
		
		newDestination.setInputEnabled(destination.isInputEnabled());
		newDestination.setOutputEnabled(destination.isOutputEnabled());
	}
}