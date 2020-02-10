package com.tibco.cep.studio.wizard.hawk.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.ElementFactoryImpl;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class EntityCreator {

	public static boolean checkEventExist(String projectName, SimpleEvent event) {
		Entity existingEntity = CommonIndexUtils.getSimpleEvent(projectName, event.getFullPath());
		if (existingEntity != null) {
			return true;
		}
		return false;
	}

	public static boolean checkDestExist(Channel channel, String destinationName) {
		DriverConfig config = channel.getDriver();
		EList<Destination> dests = config.getDestinations();
		for (int i = 0; i < dests.size(); i++) {
			Destination temp = dests.get(i);
			if (temp.getName().equals(destinationName)) {
				return true;
			}
		}
		return false;
	}

	public static void persistEntities(List<Entity> entities, IProject prj) throws Exception {
		String baseURI = prj.getLocation().toString();
		System.out.println("baseURI:" + baseURI);
		Map<?, ?> options = ModelUtils.getPersistenceOptions();
		for (Entity entity : entities) {

			if (entity instanceof RuleSet) {
				continue;
			}
			// validate/recreate the entity name,namespace,return type, folder
			checkValidCharacters(entity);

			String folder = entity.getFolder();
			String extension = IndexUtils.getFileExtension(entity);
			URI uri = URI.createFileURI(baseURI + "/" + folder + "/" + entity.getName() + "." + extension);
			if (entity instanceof Rule) {
				try {
					ModelUtilsCore.persistRule((Rule) entity, uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}

			if (entity instanceof RuleFunction) {
				try {
					ModelUtilsCore.persistRuleFunction((RuleFunction) entity, uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}

			// using XMI
			ResourceSet resourceSet = new ResourceSetImpl();
			if (resourceSet.getResourceFactoryRegistry().getFactory(uri) == null) {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
						.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			}
			Resource resource = resourceSet.createResource(uri);
			if (resource == null)
				continue;

			resource.getContents().add(entity);
			try {
				resource.save(options);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static SimpleEvent createEvent(String projectName, String eventFolder, String eventName,
			Properties eventProps) {
		SimpleEvent event = EventFactory.eINSTANCE.createSimpleEvent();
		populateEntity(projectName, eventName, "/" + eventFolder + "/", event);
		event.setSuperEventPath("");
		event.setTtl("0");
		event.setTtlUnits(TIMEOUT_UNITS.SECONDS);
		event.setType(EVENT_TYPE.SIMPLE_EVENT);

		// set event properties
		for (int i = 0; i < eventProps.keySet().size(); i++) {
			PropertyDefinition pd = ElementFactoryImpl.eINSTANCE.createPropertyDefinition();
			String propName = (String) eventProps.keySet().toArray()[i];
			pd.setName(propName);
			String type = eventProps.getProperty(propName);
			if (type.contains(".")) {
				type = type.substring(type.lastIndexOf(".") + 1);
			}
			if (type.equals("Integer")) {
				type = "int";
			}
			pd.setType(PROPERTY_TYPES.get(type));
			pd.setOwnerPath("/" + eventFolder + "/"+eventName);
			pd.setOwnerProjectName(projectName);
			event.getProperties().add(pd);
		}
		return event;
	}

	public static void addDestination(Channel channel, String destinationName, SimpleEvent event, Properties destProps) {
		Destination destination = ChannelFactory.eINSTANCE.createDestination();
		DriverConfig config = channel.getDriver();
		destination.setName(destinationName);
		destination.setDescription("");
		destination.setDriverConfig(config);
		destination.setSerializerDeserializerClass(HawkConstants.HAWK_SERIALIZER);
		destination.setOwnerProjectName(channel.getOwnerProjectName());
		// add destination's properties
		PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		destination.setProperties(propertyMap);
		for (int i = 0; i < destProps.keySet().size(); i++) {
			String propName = (String) destProps.keySet().toArray()[i];
			SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
			sp.setName(propName);
			sp.setValue(destProps.getProperty(propName));
			destination.getProperties().getProperties().add(sp);
		}

		if (event != null) {
			destination.setEventURI(event.getFullPath());
		}
		// remove the original destination with the same name
		EList<Destination> dests = config.getDestinations();
		for (int i = 0; i < dests.size(); i++) {
			Destination temp = dests.get(i);
			if (temp.getName().equals(destinationName)) {
				dests.remove(i);
				i--;
			}
		}
		config.getDestinations().add(destination);
	}

	protected static void populateEntity(String projectName, String filename, String folder, Entity entity) {
		entity.setName(filename);
		entity.setDescription("");
		entity.setFolder(folder);
		entity.setNamespace(folder);
		entity.setGUID(GUIDGenerator.getGUID());
		entity.setOwnerProjectName(projectName);
	}

	private static void checkValidCharacters(Entity entity) {
		// validate/recreate the entity
		// name,folder,namespace,return type,

		// name
		String entity_name = entity.getName();
		entity_name = removeInvalidChars(entity_name);
		entity.setName(entity_name);

		// folder
		String entity_folder = entity.getFolder();
		entity_folder = removeInvalidChars(entity_folder);
		entity.setFolder(entity_folder);

		// namespace
		String entity_namespace = entity.getNamespace();
		entity_namespace = removeInvalidChars(entity_namespace);
		entity.setNamespace(entity_namespace);

		// return type
		if (entity instanceof RuleFunction) {
			// return type
			String entity_returntype = ((RuleFunction) entity).getReturnType();
			if (entity_returntype != null) {
				entity_returntype = removeInvalidChars(entity_returntype);
			}
			((RuleFunction) entity).setReturnType(entity_returntype);
			// ConditionText
			String entity_conditionText = ((RuleFunction) entity).getConditionText();
			if (entity_conditionText != null) {
				entity_conditionText = removeInvalidChars(entity_conditionText);
			}
			((RuleFunction) entity).setConditionText(entity_conditionText);
			// Symbol
			for (Symbol symbol : ((RuleFunction) entity).getSymbols().getSymbolList()) {
				String symbol_type = symbol.getType();
				if (symbol_type != null) {
					symbol_type = removeInvalidChars(symbol_type);
				}
				symbol.setType(symbol_type);
			}
		}

		if (entity instanceof Rule) {
			for (Symbol symbol : ((Rule) entity).getSymbols().getSymbolList()) {
				String symbol_type = symbol.getType();
				if (symbol_type != null) {
					symbol_type = removeInvalidChars(symbol_type);
				}
				symbol.setType(symbol_type);
			}
		}

		if (entity instanceof Channel) {

			List<Destination> destinationList = ((Channel) entity).getDriver().getDestinations();
			for (Destination destination : destinationList) {
				String entity_eventURI = destination.getEventURI();
				if (entity_eventURI != null) {
					entity_eventURI = removeInvalidChars(entity_eventURI);
					destination.setEventURI(entity_eventURI);
				}
			}
		}

		if (entity instanceof SimpleEvent) {
			String entity_ChannelURI = ((SimpleEvent) entity).getChannelURI();
			if (entity_ChannelURI != null) { // abstract wsdl case #BE-10394
				entity_ChannelURI = removeInvalidChars(entity_ChannelURI);
				((SimpleEvent) entity).setChannelURI(entity_ChannelURI);
			}

			String entity_destinationName = ((SimpleEvent) entity).getDestinationName();
			if (entity_destinationName != null) {
				entity_destinationName = removeInvalidChars(entity_destinationName);
			}
			((SimpleEvent) entity).setDestinationName(entity_destinationName);
		}
	}

	private static String removeInvalidChars(String replaceString) {
		if (replaceString.contains(" ")) {
			replaceString = replaceString.replace(" ", "_");
		}
		if (replaceString.contains(".")) {
			replaceString = replaceString.replace(".", "_");
		}
		if (replaceString.contains("$")) {
			replaceString = replaceString.replace("$", "_");
		}
		if (replaceString.contains("~")) {
			replaceString = replaceString.replace("~", "_");
		}
		if (replaceString.contains("^")) {
			replaceString = replaceString.replace("^", "_");
		}
		return replaceString;
	}

}
