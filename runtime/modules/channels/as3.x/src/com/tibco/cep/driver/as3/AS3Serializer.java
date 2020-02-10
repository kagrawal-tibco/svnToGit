package com.tibco.cep.driver.as3;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.be.custom.channel.framework.CustomEvent;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.serializers.FieldType;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.store.as.ASContainer;
import com.tibco.cep.store.as.ASItem;

public class AS3Serializer extends BaseEventSerializer {
	private Logger logger;
	private ASContainer container;
	private com.tibco.cep.designtime.model.event.Event designTimeEvent;
	
	@Override
	public void initUserEventSerializer(String destinationName, Properties destinationProperties, Logger logger) {
		this.logger = logger;
	}

	@Override
	public Event deserializeUserEvent(Object message, Map<String, Object> properties) throws Exception {
		ASItem item = (ASItem) message;
		CustomEvent event = new CustomEvent();
		
		Object value = null;
		EventPropertyDefinition propDef = null;
		for (Object property : designTimeEvent.getAllUserProperties()) {
			if (property instanceof EventPropertyDefinition) {
				propDef = (EventPropertyDefinition) property;
				value = item.getValue(propDef.getPropertyName(), propDef.getType().getName());
				if (value != null) event.setProperty(propDef.getPropertyName(), value);
			}
		}
		
		value = item.getValue(AS3Properties.RESERVED_EVENT_PROP_PAYLOAD, FieldType.BLOB.name());
		if (value != null) {
			if (value instanceof byte[]) {
				event.setPayload((byte[]) value);
			} else {
				try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
					try (ObjectOutputStream o = new ObjectOutputStream(b)) {
						o.writeObject(value);
					}
					event.setPayload(b.toByteArray());
				}
			}
		}

		logger.log(Level.DEBUG, "Deserialized item to AS3Event");
		return event;
	}

	@Override
	public Object serializeUserEvent(EventWithId event, Map<String, Object> properties) throws Exception {
		ASItem item = new ASItem();
		item.setContainer(container);
		item.createItem();
		
		Object value = null; String type = null;
		for (String propertyName :event.getAllPropertyNames()) {
			type = designTimeEvent.getPropertyType(propertyName).getName();
			value = event.getPropertyValue(propertyName);
			if (value != null) {
				item.setValue(propertyName, type, value);
			}
		}
		
		value = event.getPayload();
		if (value != null) item.setValue(AS3Properties.RESERVED_EVENT_PROP_PAYLOAD, FieldType.BLOB.name(), value);
		
		return item;
	}

	public void setContainer(ASContainer container) {
		this.container = container;
	}
	
	public void setEvent(String eventURI) {
		if (eventURI != null && !eventURI.isEmpty()) {
			eventURI = eventURI.substring(eventURI.indexOf(TypeManager.DEFAULT_BE_NAMESPACE_URI) + TypeManager.DEFAULT_BE_NAMESPACE_URI.length(), eventURI.lastIndexOf('}'));
			try {
				RuleServiceProviderManager RSPM = RuleServiceProviderManager.getInstance();
				designTimeEvent = RSPM.getDefaultProvider().getProject().getOntology().getEvent(eventURI);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
