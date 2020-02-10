/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.util.shared.ModelConstants;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.event.AdvisoryEvent;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.studio.core.annotations.AppliesTo;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.DefaultImportRegistry;
import com.tibco.xml.DefaultNamespaceMapper;
import com.tibco.xml.ImportRegistry;
import com.tibco.xml.ImportRegistryEntry;
import com.tibco.xml.ImportRegistrySupport;
import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.SmElement;

/**
 * @author aathalye
 *
 */
public class EventAdapter extends EntityAdapter<com.tibco.cep.designtime.core.model.event.Event> implements Event, ICacheableAdapter {
	
	public EventAdapter(com.tibco.cep.designtime.core.model.event.Event adapted, 
			            Ontology emfOntology) {
		super(adapted, emfOntology);
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getAllUserProperties()
	 */
	public List<EventPropertyDefinition> getAllUserProperties() {
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedUserProperties = 
						adapted.getAllUserProperties();
		List<EventPropertyDefinition> allUserProperties = 
			new ArrayList<EventPropertyDefinition>(adaptedUserProperties.size());
		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition p : adaptedUserProperties) {
			allUserProperties.add(new EventPropertyDefinitionAdapter(p, this, emfOntology));
		}
		return allUserProperties;
	}

	@Override
	protected com.tibco.cep.designtime.core.model.event.Event getAdapted() {
		return adapted;
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getAncestorForPropertyName(java.lang.String)
	 */
	public Event getAncestorForPropertyName(String propertyName) {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getAncestorProperties()
	 */
	public Iterator<PropertyDefinition> getAncestorProperties() {
		throw new UnsupportedOperationException("Not in Use");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getAttributeDefinition(java.lang.String)
	 */
	public EventPropertyDefinition getAttributeDefinition(String attributeName) {
		// these could all be combined, but for now I wanted to see which attributes are valid
		 if (Event.BASE_ATTRIBUTE_NAMES[0].equals(attributeName)) {
			com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName(attributeName);
			userProperty.setType(PROPERTY_TYPES.LONG);
			return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
		} else if (Event.BASE_ATTRIBUTE_NAMES[1].equals(attributeName)) {
			com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName(attributeName);
			userProperty.setType(PROPERTY_TYPES.STRING);
			return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
		} else if (Event.BASE_ATTRIBUTE_NAMES[2].equals(attributeName)) {
			com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName(attributeName);
			userProperty.setType(PROPERTY_TYPES.LONG);
			return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
		} else if (attributeName.equals("closure")) {
            if (this.getType() == EVENT_TYPE.TIME_EVENT.getValue()) {
            	com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
            	userProperty.setName(attributeName);
            	userProperty.setType(PROPERTY_TYPES.STRING);
            	return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
            }
        } else if (attributeName.equals("scheduledTime")) {
            if (this.getType() == EVENT_TYPE.TIME_EVENT.getValue()) {
            	com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
            	userProperty.setName(attributeName);
            	userProperty.setType(PROPERTY_TYPES.DATE_TIME);
            	return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
            }
        } else if (attributeName.equals("interval")) {
            if (this.getType() == EVENT_TYPE.TIME_EVENT.getValue()) {
            	com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
            	userProperty.setName(attributeName);
            	userProperty.setType(PROPERTY_TYPES.LONG);
            	return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
            }
        } else if ("payload".equals(attributeName)) {
			com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName(attributeName);
			userProperty.setType(PROPERTY_TYPES.STRING);
			return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
		} else if (attributeName.equals("soapAction")) {
            if (this.getType() == EVENT_TYPE.SIMPLE_EVENT.getValue() && RDFTypes.SOAP_EVENT.getName().equals(getSuperEventPath())) {
            	com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
            	userProperty.setName(attributeName);
            	userProperty.setType(PROPERTY_TYPES.STRING);
            	return new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
            }
        }
		throw new UnsupportedOperationException("To Be Done");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getAttributeDefinitions()
	 */
	public Collection<EventPropertyDefinition> getAttributeDefinitions() {
//		throw new UnsupportedOperationException("May not be needed");
		ArrayList<EventPropertyDefinition> retAttributes = new ArrayList<EventPropertyDefinition>();
        fillStaticAttributes(retAttributes);
        return retAttributes;
	}
	
	/**
	 * 
	 * @param list
	 */
	protected void fillStaticAttributes(ArrayList<EventPropertyDefinition> list) {
		int eventType = getType();
		EVENT_TYPE eventTypeEnum = EVENT_TYPE.get(eventType);
		//This is to avoid exceptions generated by client code
		switch (eventTypeEnum) {
		case SIMPLE_EVENT :
			fillStaticAttributes(list, getType(), "-1");
			break;
		case TIME_EVENT :
			fillStaticAttributes(list, getType(), getTimeEventCount());
			break;
		}
		

		// Add attribute soapAction if this inherits SOAPEvent type
		if (getType() == EVENT_TYPE.SIMPLE_EVENT.getValue()
				&& RDFTypes.SOAP_EVENT.getName().equals(getSuperEventPath())) {
			com.tibco.cep.designtime.core.model.element.PropertyDefinition pd = ElementFactory.eINSTANCE.createPropertyDefinition();
			pd.setName("soapAction");
			pd.setType(PROPERTY_TYPES.STRING);
			// do not use adapter factory here, create the EventPropertyDefinitionAdapter directly
//			EventPropertyDefinitionAdapter ad = AdapterFactory.createAdapter(pd, emfOntology);
			EventPropertyDefinition ad =  new EventPropertyDefinitionAdapter(pd, this, emfOntology);
			list.add(ad);
//			list.add(new DefaultMutableEventPropertyDefinition(null,
//					"soapAction", (RDFPrimitiveTerm) RDFTypes.STRING));
		}
	}
	/**
	 * 
	 * @param list
	 * @param type
	 * @param timeEventCount
	 */
	public  void fillStaticAttributes(List<EventPropertyDefinition> list, int type,
			String timeEventCount) {
		fillStaticAttributes(null, list, type, timeEventCount);
	}
	/**
	 * 
	 * @param event
	 * @param list
	 * @param type
	 * @param timeEventCount
	 */
	private void fillStaticAttributes(
			Event event, List<EventPropertyDefinition> list, int type, String timeEventCount) {
		com.tibco.cep.designtime.core.model.element.PropertyDefinition userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
		userProperty.setName("id");
		userProperty.setType(PROPERTY_TYPES.STRING);
		EventPropertyDefinition pd =  new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
		list.add(pd);
//		list.add(new DefaultMutableEventPropertyDefinition(event, "id",
//				(RDFPrimitiveTerm) RDFTypes.LONG));
		userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
		userProperty.setName("ttl");
		userProperty.setType(PROPERTY_TYPES.LONG);
		pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
		list.add(pd);
//		list.add(new DefaultMutableEventPropertyDefinition(event, "ttl",
//				(RDFPrimitiveTerm) RDFTypes.LONG));
		if (type == EVENT_TYPE.TIME_EVENT.getValue()) {
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName("closure");
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event,
//					"closure", (RDFPrimitiveTerm) RDFTypes.STRING));
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName("scheduledTime");
			userProperty.setType(PROPERTY_TYPES.DATE_TIME);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event,
//					"scheduledTime", (RDFPrimitiveTerm) RDFTypes.DATETIME));
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName("interval");
			userProperty.setType(PROPERTY_TYPES.LONG);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event,
//					"interval", (RDFPrimitiveTerm) RDFTypes.LONG));
		} else if (type == EVENT_TYPE.SIMPLE_EVENT.getValue()) {
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName("extId");
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event, "extId",
//					(RDFPrimitiveTerm) RDFTypes.STRING));
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName("payload");
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event,
//					"payload", (RDFPrimitiveTerm) RDFTypes.STRING));
		} else if (type == EVENT_TYPE.ADVISORY_EVENT.getValue()) {
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName("extId");
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event, "extId",
//					(RDFPrimitiveTerm) RDFTypes.STRING));
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName(AdvisoryEvent.ATTRIBUTE_CATEGORY);
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event,
//					AdvisoryEvent.ATTRIBUTE_CATEGORY,
//					(RDFPrimitiveTerm) RDFTypes.STRING));
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName(AdvisoryEvent.ATTRIBUTE_TYPE);
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event,
//					AdvisoryEvent.ATTRIBUTE_TYPE,
//					(RDFPrimitiveTerm) RDFTypes.STRING));
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName(AdvisoryEvent.ATTRIBUTE_MESSAGE);
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(event,
//					AdvisoryEvent.ATTRIBUTE_MESSAGE,
//					(RDFPrimitiveTerm) RDFTypes.STRING));
		}

		if ((String.valueOf(EVENT_TYPE.SIMPLE_EVENT.getValue())).equals(timeEventCount)) {
			userProperty = ElementFactory.eINSTANCE.createPropertyDefinition();
			userProperty.setName("payload");
			userProperty.setType(PROPERTY_TYPES.STRING);
			pd = new EventPropertyDefinitionAdapter(userProperty, this, emfOntology);
			list.add(pd);
//			list.add(new DefaultMutableEventPropertyDefinition(null, "payload",
//					(RDFPrimitiveTerm) RDFTypes.STRING));
		}
	}
	
	/**
	 * This will be required
	 */
	@AppliesTo(EVENT_TYPE.SIMPLE_EVENT)
	public String getChannelURI() {
		//Get the default destination's owner channel
		//Not applicable for time event
		if (adapted instanceof SimpleEvent) {
			return ((SimpleEvent)adapted).getChannelURI();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getDescendantForPropertyName(java.lang.String)
	 */
	public Event getDescendantForPropertyName(String propertyName) {
		throw new UnsupportedOperationException("May not be needed");
	}
	
	/**
	 * This will be required
	 */
	@AppliesTo(EVENT_TYPE.SIMPLE_EVENT)
	public String getDestinationName() {
		//This should be the name of the default destination
		//Not applicable for time event
		if (adapted instanceof SimpleEvent) {
			return ((SimpleEvent)adapted).getDestinationName();
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getExpiryAction(boolean)
	 */
	
	public Rule getExpiryAction(boolean create) {
		com.tibco.cep.designtime.core.model.rule.Rule expiryActionRule = 
							adapted.getExpiryAction();
		if (expiryActionRule != null) {
			try {
				RuleAdapter<com.tibco.cep.designtime.core.model.rule.Rule> expiryActionAdapter = 
					CoreAdapterFactory.INSTANCE.createAdapter(expiryActionRule, emfOntology);
				return expiryActionAdapter;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	@AppliesTo(EVENT_TYPE.TIME_EVENT)
	public String getInterval() {
		if (adapted instanceof TimeEvent) {
			return ((TimeEvent)adapted).getInterval();
		}
		throw new UnsupportedOperationException("Operation not supported for this event type");
	}
	
	@AppliesTo(EVENT_TYPE.TIME_EVENT)
	public int getIntervalUnit() {
		if (adapted instanceof TimeEvent) {
			return ((TimeEvent)adapted).getIntervalUnit().getValue();
		}
		throw new UnsupportedOperationException("Operation not supported for this event type");
	}

	public ImportRegistry getPayloadImportRegistry() {
		DefaultImportRegistry registry = new DefaultImportRegistry();
		List<ImportRegistryEntry> imports = new ArrayList<ImportRegistryEntry>();
		EList<com.tibco.cep.designtime.core.model.event.ImportRegistryEntry> importEntries = getAdapted().getRegistryImportEntries();
		for (com.tibco.cep.designtime.core.model.event.ImportRegistryEntry importRegistryEntry : importEntries) {
			imports.add(new ImportRegistryEntry(importRegistryEntry.getNamespace(),importRegistryEntry.getLocation()));
			ImportRegistrySupport.setFromList(registry, imports);
		}
		return registry;
	}

	public NamespaceImporter getPayloadNamespaceImporter() {
        NamespaceImporter importer = new DefaultNamespaceMapper("xsd");
        ((DefaultNamespaceMapper) importer).addXSDNamespace();
        EList<NamespaceEntry> namespaceEntries = getAdapted().getNamespaceEntries();
        for (NamespaceEntry namespaceEntry : namespaceEntries) {
        	importer.getOrAddPrefixForNamespaceURI(namespaceEntry.getNamespace(), namespaceEntry.getPrefix());
		}
//        Iterator prefixes = importer.getPrefixes();
//        while (prefixes.hasNext()) {
//        	String prefix = (String) prefixes.next();
//        	String namespace = importer.getNamespaceURIForPrefix(prefix);
//        	importer.getOrAddPrefixForNamespaceURI(namespace, prefix);
//        }
        return importer;
//		throw new UnsupportedOperationException("May not be needed");
	}

	public Element getPayloadSchema() {
		//TODO How are we going to store payloads? EObject? or namespace of the xsd, or an EClass itself
		throw new UnsupportedOperationException("TO BE DONE");
	}

	public String getPayloadSchemaAsString() {
		return adapted.getPayloadString();
	}

	public EventPropertyDefinition getPropertyDefinition(String propertyName,
			boolean all) {
		//Get Property from the adapted event
		com.tibco.cep.designtime.core.model.element.PropertyDefinition adaptedProperty = 
			adapted.getPropertyDefinition(propertyName, all);
		if (adaptedProperty == null) {
			return null;
		}
		return new EventPropertyDefinitionAdapter(adaptedProperty, this, emfOntology);
	}

	public RDFPrimitiveTerm getPropertyType(String propertyName) {
		//Get the event property definition
		EventPropertyDefinition eventPropertyDefinition = getPropertyDefinition(propertyName, true);
		if (eventPropertyDefinition != null) {
			return eventPropertyDefinition.getType();
		}
		return null;
	}
	
	@AppliesTo(EVENT_TYPE.TIME_EVENT)
	public int getSchedule() {
		if (!(adapted instanceof TimeEvent)) {
			throw new UnsupportedOperationException("Cannot invoke this operation on a non-time event");
		}
		return ((TimeEvent)adapted).getScheduleType().getValue();
	}

	public int getSerializationFormat() {
		return ModelConstants.SERIALIZATION_FORMAT_BE;
	}
	
	@AppliesTo(EVENT_TYPE.TIME_EVENT)
	public long getSpecifiedTime() {
		if (!(adapted instanceof TimeEvent)) {
			throw new UnsupportedOperationException("Cannot invoke this operation on a non-time event");
		}
		return ((TimeEvent)adapted).getSpecifiedTime();
	}

	public Collection<String> getSubEventPaths() {
		throw new UnsupportedOperationException("May not be needed"); 
	}

	public RDFPrimitiveTerm getSubPropertyType(String propertyName) {
		throw new UnsupportedOperationException("May not be needed");
	}

	public Event getSuperEvent() {
		com.tibco.cep.designtime.core.model.event.Event superEvent = adapted.getSuperEvent();
		if (superEvent != null) {
			return new EventAdapter(superEvent, emfOntology);
		}
		return null;
	}

	public String getSuperEventPath() {
		Event superEvent = getSuperEvent();
		String path = (superEvent != null) ? superEvent.getFullPath() : adapted.getSuperEventPath();
		return path;
	}

	public RDFPrimitiveTerm getSuperPropertyType(String propertyName) {
		throw new UnsupportedOperationException("May not be needed");
	}

	public String getTTL() {
		return adapted.getTtl();
	}

	public int getTTLUnits() {
		return adapted.getTtlUnits().getValue();
	}
	
	@AppliesTo(EVENT_TYPE.TIME_EVENT)
	public String getTimeEventCount() {
		if (!(adapted instanceof TimeEvent)) {
			throw new UnsupportedOperationException("Cannot invoke this operation on a non-time event");
		}
		return ((TimeEvent)adapted).getTimeEventCount();
	}
	
	/**
	 * @return the event type
	 * @see EVENT_TYPE
	 */
	public int getType() {
		if (adapted instanceof TimeEvent) {
			return EVENT_TYPE.TIME_EVENT_VALUE;
		} else if (adapted instanceof AdvisoryEvent) {
			return EVENT_TYPE.ADVISORY_EVENT_VALUE;
		}
		return adapted.getType().getValue();
	}
	
	/**
	 * Get a {@linkplain Iterator} over the local properties
	 */
	public Iterator<EventPropertyDefinition> getUserProperties() {
		//Get local properties only
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> adaptedProps = 
						adapted.getProperties();
		List<EventPropertyDefinition> userProps = 
			new ArrayList<EventPropertyDefinition>(adaptedProps.size());
		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition property : adaptedProps) {
			userProps.add(new EventPropertyDefinitionAdapter(property, this, emfOntology));
		}
		return userProps.iterator();
	}

	public boolean hasPayload() {
		return adapted.getPayload() != null;
	}

	public boolean isA(Event event) {
		//No harm in this cast
		EventAdapter other = (EventAdapter)event;
		return adapted.isA(other.getAdapted());
	}
	
	/** 
	 * We may need to implement this to know the schema type
	 */
	public boolean payloadIsAnyType() {
		throw new UnsupportedOperationException("TO BE DONE");
	}
	
	/**
	 * This method should have been defined on a {@linkplain MutableEvent}
	 */
	public void setPayloadSchema(Element schema) throws ModelException {
		throw new ModelException(new UnsupportedOperationException("This method not supported on a read-only event"));
	}

	public SmElement toSmElement() {
		return null;
	}

	

	public boolean getRetryOnException() {
		return getAdapted().isRetryOnException();
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.Event#getExpiryCodeBlock()
	 */
	@Override
	public CodeBlock getExpiryCodeBlock() {
		return CommonRulesParserManager.calculateOffset(RulesParser.THEN_BLOCK, ModelUtils.getRuleAsSource(adapted.getExpiryAction()));
	}
	
	public boolean isSoapEvent() {
		return adapted.isSoapEvent();
	}

}
