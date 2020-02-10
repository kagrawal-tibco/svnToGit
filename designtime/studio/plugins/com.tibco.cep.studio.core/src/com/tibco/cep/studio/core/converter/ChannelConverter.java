package com.tibco.cep.studio.core.converter;


import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;

public class ChannelConverter extends EntityConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		Channel newEntity = ChannelFactory.eINSTANCE.createChannel();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.service.channel.Channel.class;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		com.tibco.cep.designtime.model.service.channel.Channel channel = 
			(com.tibco.cep.designtime.model.service.channel.Channel) entity;
		super.populateEntity(newEntity, entity, projectName);
		Channel newChannel = (Channel) newEntity;
		DriverConfig driver = channel.getDriver();
		newChannel.setFolder(channel.getFolderPath());
		DRIVER_TYPE driverType = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driverType.setName(driver.getDriverType());
		com.tibco.cep.designtime.core.model.service.channel.DriverConfig newDriverConfig = 
			(DriverManagerConstants.DRIVER_HTTP == driverType.getName().intern()) ? 
					ChannelFactory.eINSTANCE.createHttpChannelDriverConfig() : ChannelFactory.eINSTANCE.createDriverConfig();
		newChannel.setDriver(newDriverConfig);
		
		newDriverConfig.setDriverType(driverType);
		newDriverConfig.setConfigMethod(CONFIG_METHOD.get(driver.getConfigMethod().toUpperCase()));
		newDriverConfig.setReference(driver.getReference());
		newDriverConfig.setLabel(driver.getLabel());
		newDriverConfig.setChannel(newChannel);

//		com.tibco.cep.designtime.model.service.channel.Channel channel2 = driver.getChannel(); // TODO : this is the container (?)
		final Properties properties = driver.getChannelProperties();
		PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		newDriverConfig.setProperties(propertyMap);
		if (properties != null){
			Set<Object> keySet = properties.keySet();
			for (Object object : keySet) {
				String propName = (String) object;
				SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
				property.setName(propName);
				property.setValue(properties.getProperty(propName));
				propertyMap.getProperties().add(property);
			}
//todo: API has changed, must rewrite            
//			// create new Instance for EMF Model
//			com.tibco.cep.designtime.core.model.element.Instance emfInstance = ElementFactory.eINSTANCE.createInstance();
//			newDriverConfig.setProperties(emfInstance);
//			emfInstance.setName(properties.getName());
//			emfInstance.setFolder(properties.getFolderPath());
//			emfInstance.setLastModified(properties.getLastModified());
//			com.tibco.cep.designtime.model.element.Concept oldConcept = properties.getConcept();
//			// create new EMF Concept
//			//This is not needed as this will come from the driver manager
//			Concept emfConcept = ElementFactory.eINSTANCE.createConcept();
//			emfInstance.setConcept(emfConcept);
//			if (oldConcept != null){
//				emfConcept.setName(oldConcept.getName());
//				emfConcept.setFolder(oldConcept.getFolderPath());
//				Iterator it = oldConcept.getPropertyDefinitions(true).iterator();
//				while (it.hasNext()){
//					Object next = it.next();
//					if (!(next instanceof PropertyDefinition)) continue;
//					PropertyDefinition propDef = (PropertyDefinition)next;
//					// create a new EMF Property Definition
//					com.tibco.cep.designtime.core.model.element.PropertyDefinition emfPropDef = ElementFactory.eINSTANCE.createPropertyDefinition();
//					emfConcept.getProperties().add(emfPropDef);
//					emfPropDef.setName(propDef.getName());
//					emfPropDef.setType(PROPERTY_TYPES.get(propDef.getType()));
//					emfPropDef.setDefaultValue(propDef.getDefaultValue());
//				}
//
//
//			}
//			for (Object obj : properties.getAllPropertyInstances()){
//				if (obj instanceof List){
//					List list = (List)obj;
//					for (Object ob : list){
//						if (ob instanceof PropertyInstance){
//							// Create EMF Property Instance
//							com.tibco.cep.designtime.core.model.element.PropertyInstance emfPropInstance = ElementFactory.eINSTANCE.createPropertyInstance();
//							emfInstance.getProperties().add(emfPropInstance);
//							PropertyInstance propInstance = (PropertyInstance)ob;
//							String propName = propInstance.getName();
//							String propValue = propInstance.getValue();
//							emfPropInstance.setName(propName);
//							emfPropInstance.setDefinitionName(propInstance.getPropertyDefinitionName());// test whether definition name is same as name
//							emfPropInstance.setValue(propValue);
//							emfPropInstance.setInstance(emfInstance);
//
//							PropertyDefinition propDef = propInstance.getPropertyDefinition();
//							if(propDef != null){
//								// create EMF PropertyDefinition
//								com.tibco.cep.designtime.core.model.element.PropertyDefinition emfPropDef = ElementFactory.eINSTANCE.createPropertyDefinition();
//								// check if prop definition is really needed for PropertyInstance because data is getting duplicated , same info PropertyInstance has
//								int type = propDef.getType();
//							}
//						}
//					}
//				}
//
//			}
//
//			// populate extended properties of Instance
//			/*
//			Map extMap = properties.getExtendedProperties();
//			if (extMap != null){
//				Set entrySet = extMap.entrySet();
//				Iterator it = entrySet.iterator();
//				while (it.hasNext()){
//					Object next = it.next();
//					if (next instanceof Map.Entry){
//						Map.Entry entry = (Map.Entry)next;
//						Object key = entry.getKey();
//						Object val = entry.getValue();
//						System.out.println("");
//						if (val instanceof Map){
//							Map valMap = (Map)val;
//							Set valSet = valMap.entrySet();
//							Iterator valIt = valSet.iterator();
//							while (valIt.hasNext()){
//								Object valNext = it.next();
//								if (valNext instanceof Map.Entry){
//									Map.Entry valEntry = (Map.Entry)next;
//									Object valKey = valEntry.getKey();
//									Object valVal = valEntry.getValue();
//									System.out.println("");
//								}
//							}
//						}
//
//					}
//				}
//				if (entrySet != null){
//
//				}
//			}
//			*/
		}
		Iterator<Destination> destinations = driver.getDestinations();
		//No harm in doing this
		DestinationConverter destConverter = new DestinationConverter(driverType);
		while (destinations.hasNext()) {
			Destination destination = (Destination) destinations.next();
			com.tibco.cep.designtime.core.model.service.channel.Destination newDestination = (com.tibco.cep.designtime.core.model.service.channel.Destination) destConverter.convertEntity(destination, projectName);
			newDriverConfig.getDestinations().add(newDestination);
			newDestination.setDriverConfig(newDriverConfig);
		}
	}
}
