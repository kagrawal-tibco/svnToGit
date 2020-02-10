package com.tibco.cep.studio.core.converter;

import java.util.Iterator;
import java.util.Map;

import com.tibco.be.model.rdf.primitives.RDFBooleanTerm;
import com.tibco.be.model.rdf.primitives.RDFDateTimeTerm;
import com.tibco.be.model.rdf.primitives.RDFDoubleTerm;
import com.tibco.be.model.rdf.primitives.RDFIntegerTerm;
import com.tibco.be.model.rdf.primitives.RDFLongTerm;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFStringTerm;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.xml.ImportRegistry;
import com.tibco.xml.ImportRegistryEntry;
import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver.PrefixNotFoundException;

public abstract class EventConverter extends EntityConverter {

//	@Override
//	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity) {
//		Event newEntity = EventFactory.eINSTANCE.createEvent();
//		populateEntity(newEntity, entity);
//		return newEntity;
//	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		Event newEvent = (Event) newEntity;
		com.tibco.cep.designtime.model.event.Event event = (com.tibco.cep.designtime.model.event.Event) entity;
		newEvent.setPayloadString(event.getPayloadSchemaAsString()); // TODO : model conversion work
		newEvent.setTtl(event.getTTL());
		newEvent.setTtlUnits(TIMEOUT_UNITS.get(event.getTTLUnits()));
		newEvent.setSpecifiedTime(event.getSpecifiedTime());
		newEvent.setSerializationFormat(event.getSerializationFormat());
		newEvent.setRetryOnException(event.getRetryOnException());
		
		String channelURI = event.getChannelURI();

		String destinationName = event.getDestinationName();
		
		if (channelURI != null && channelURI.trim().length() > 0) {
			//Most likely a simple event
			SimpleEvent simpleEvent = (SimpleEvent)newEvent;
			if (channelURI.endsWith(IndexUtils.CHANNEL_EXTENSION)) {
				channelURI = channelURI.replace("." + IndexUtils.CHANNEL_EXTENSION, "");
			}
			simpleEvent.setChannelURI(channelURI);
			simpleEvent.setDestinationName(destinationName);
		}
		newEvent.setType(EVENT_TYPE.get(event.getType())); // time or simple
		//newEvent.setOwnerProjectName(projectName);
		
		processNamespaces(newEvent, event);
		com.tibco.cep.designtime.model.rule.Rule expiryAction = event.getExpiryAction(false);
		if (expiryAction != null) {
			Rule expiryRule = (Rule) new RuleConverter().convertEntity(expiryAction, projectName);
			newEvent.setExpiryAction(expiryRule);
		}
		
		// TODO : can be calculated.  Do we want this here?
		event.getSuperEvent(); // TODO : this can be computed from the path
		newEvent.setSuperEventPath(event.getSuperEventPath());
		event.getSubEventPaths(); // TODO : this can be computed
		event.getAncestorProperties(); // TODO : this can be computed
		Iterator<EventPropertyDefinition> iter = event.getUserProperties();
		while (iter.hasNext()) {
			EventPropertyDefinition userProp = (EventPropertyDefinition) iter.next();
			PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setOwnerPath(newEvent.getFullPath());
			newEvent.getProperties().add(userProperty);
			userProperty.setName(userProp.getPropertyName());
			userProperty.setOwnerProjectName(newEvent.getOwnerProjectName());
			userProperty.setType(getPropertyType(userProp.getType()));
			Map extendedProperties = userProp.getExtendedProperties();
			// convert extended properties
			if (extendedProperties.size() > 0) {
				PropertyMap extendedPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
				userProperty.setExtendedProperties(extendedPropertyMap);
				for (Object propKey : extendedProperties.keySet()) {
					String key = (String) propKey;
					Object val = extendedProperties.get(key);
					Entity extendedProperty = createProperty(key, val);
					extendedPropertyMap.getProperties().add(extendedProperty);
				}
				
			}
		}
//		event.getRuleSetPaths(); // TODO : are there one or more ruleSets per Event? These are the referring rule sets (?)
	}

	private void processNamespaces(Event newEvent,
			com.tibco.cep.designtime.model.event.Event event) {
		NamespaceImporter importer = event.getPayloadNamespaceImporter();
		Iterator prefixes = importer.getPrefixes();
		while (prefixes.hasNext()) {
			Object next = prefixes.next();
			try {
				String prefix = (String) next;
				String prefixForNamespaceURI = importer
						.getNamespaceURIForPrefix(prefix);
				NamespaceEntry entry = EventFactory.eINSTANCE
						.createNamespaceEntry();
				entry.setPrefix(prefix);
				entry.setNamespace(prefixForNamespaceURI);
				newEvent.getNamespaceEntries().add(entry);
			} catch (PrefixNotFoundException e) {
				e.printStackTrace();
			}
		}

		ImportRegistry payloadImportRegistry = event.getPayloadImportRegistry();
		ImportRegistryEntry[] imports = payloadImportRegistry.getImports();
		for (ImportRegistryEntry importRegistryEntry : imports) {
			String namespaceURI = importRegistryEntry.getNamespaceURI();
			String location = importRegistryEntry.getLocation();
			com.tibco.cep.designtime.core.model.event.ImportRegistryEntry regEntry = EventFactory.eINSTANCE
					.createImportRegistryEntry();
			regEntry.setNamespace(namespaceURI);
			regEntry.setLocation(location);
			newEvent.getRegistryImportEntries().add(regEntry);
		}
	}

	protected PROPERTY_TYPES getPropertyType(RDFPrimitiveTerm type) {
		if (type instanceof RDFBooleanTerm) {
			return PROPERTY_TYPES.BOOLEAN;
		} else if (type instanceof RDFDateTimeTerm) {
			return PROPERTY_TYPES.DATE_TIME;
		} else if (type instanceof RDFDoubleTerm) {
			return PROPERTY_TYPES.DOUBLE;
		} else if (type instanceof RDFIntegerTerm) {
			return PROPERTY_TYPES.INTEGER;
		} else if (type instanceof RDFLongTerm) {
			return PROPERTY_TYPES.LONG;
		} else if (type instanceof RDFStringTerm) {
			return PROPERTY_TYPES.STRING;
		}
		return null;
	}

	@Override
	public boolean canConvert(Object obj) {
		if (!super.canConvert(obj)) {
			return false;
		}
		com.tibco.cep.designtime.model.event.Event event = (com.tibco.cep.designtime.model.event.Event) obj;
		return event.getType() == com.tibco.cep.designtime.model.event.Event.SIMPLE_EVENT;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.event.Event.class;
	}

}
