package com.tibco.be.custom.channel;

import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.tibco.be.model.rdf.primitives.RDFLongTerm;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.ChannelProperties;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Handles serialization/de-serialization of BE events to/from a transport.
 * <p>
 * Each BE channel type has its own implementation of
 * <code>BaseEventSerializer</code>, which is used by the channel's destinations
 * to transform outgoing events into messages and incoming messages into events.
 * </p>
 * 
 * @.category public-api
 * @see BaseChannel
 * @see BaseDestination
 * @since 5.4
 */

public abstract class BaseEventSerializer implements EventSerializer {

	private static String ENTITY_NS = "www.tibco.com/be/ontology";
	private com.tibco.cep.designtime.model.event.Event designtimeEvent;

	/**
	 * Initializes the serializer.It is called at the time of engine startup
	 *
	 * @param destinationName       name of the destination
	 * @param destinationProperties the properties of the destination
	 * @param logger                the logger instance
	 * @.category public-api
	 * @since 5.4
	 */
	public abstract void initUserEventSerializer(String destinationName, Properties destinationProperties,
			Logger logger);

	/**
	 * Transforms a message received by the Destination of the given context into an
	 * <code>Event</code>
	 *
	 * @param message    an Object representing an incoming message in the
	 *                   Destination's transport.
	 * @param properties de-serialization properties
	 * @return the <code>Event</code> that represents the incoming message
	 * @throws Exception if an <code>Event</code> could not be created.
	 * @.category public-api
	 * @since 5.4
	 */
	public abstract Event deserializeUserEvent(Object message, Map<String, Object> properties) throws Exception;

	/**
	 * Transforms an <code>Event</code> into a message that can be sent by the
	 * Destination of that context.
	 *
	 * @param event      an <code>Event</code>
	 * @param properties serialization properties
	 * @return the message Object to be sent on the underlying transport of the
	 *         Destination.
	 * @throws Exception if the message Object could not be created.
	 * @.category public-api
	 * @since 5.4
	 */
	public abstract Object serializeUserEvent(EventWithId event, Map<String, Object> properties) throws Exception;

	/**
	 * for internal use
	 */
	@Override
	public final void init(ChannelManager channelManager, DestinationConfig config) {
		Properties props = new Properties();
		for (Entry<Object, Object> entry : config.getProperties().entrySet()) {
			String newVal = (String) entry.getValue();
			if (entry.getValue() != null && GvCommonUtils.isGlobalVar((String) entry.getValue())) {
				GlobalVariableDescriptor variable = channelManager.getRuleServiceProvider().getGlobalVariables()
						.getVariable(GvCommonUtils.stripGvMarkers((String) entry.getValue()));
				newVal = variable.getValueAsString();
			}
			props.put(entry.getKey(), newVal);
		}

		
		if (config.getDefaultEventURI() != null && !config.getDefaultEventURI().isEmpty()) {
			String eventUri = config.getDefaultEventURI().namespaceURI;
			props.put(ChannelProperties.DEFAULT_EVENT_URI, config.getDefaultEventURI().namespaceURI);
			String eventPath = eventUri.substring(ChannelProperties.ENTITY_NS.length());
			setDesignTimeEvent(eventPath);	
		}

		this.initUserEventSerializer(config.getName(), props,
				channelManager.getRuleServiceProvider().getLogger(this.getClass()));
	}

	/**
	 * for internal use
	 */
	@Override
	public final SimpleEvent deserialize(Object eventObject, SerializationContext context) throws Exception {
		if (eventObject != null && (eventObject instanceof Event)) {
			Event userEvent = (Event) eventObject;
			SimpleEvent event = null;
			EventPayload payload = null;
			if (userEvent.getEventUri() != null && userEvent.getEventUri().length() > 1) {// EventType found in received
																							// event
				ExpandedName enUser = null;
				if (userEvent.getEventUri().contains(ENTITY_NS)) {
					String name = userEvent.getEventUri().substring(userEvent.getEventUri().lastIndexOf("/") + 1,
							userEvent.getEventUri().length());
					enUser = ExpandedName.makeName(userEvent.getEventUri(), name);
				} else {
					String eventUri = ENTITY_NS + userEvent.getEventUri();
					String name = eventUri.substring(eventUri.lastIndexOf("/") + 1, eventUri.length());
					enUser = ExpandedName.makeName(eventUri, name);
				}
				event = (SimpleEvent) context.getRuleSession().getRuleServiceProvider().getTypeManager()
						.createEntity(enUser);
				if (userEvent.getPayload() != null) {
					payload = context.getRuleSession().getRuleServiceProvider().getTypeManager().getPayloadFactory()
							.createPayload(enUser, userEvent.getPayload());
				}
			} else {// EventType NOT found in received event
				if (context.getDestination().getConfig().getDefaultEventURI() == null) {// Default event type NOT
																						// configured in dest
					throw new Exception(
							"EventType not found in received event, nor a default Event is configured in destination - "
									+ context.getDestination().getURI());
				}
				event = (SimpleEvent) context.getRuleSession().getRuleServiceProvider().getTypeManager()
						.createEntity(context.getDestination().getConfig().getDefaultEventURI());
				if (userEvent.getPayload() != null) {
					payload = context.getRuleSession().getRuleServiceProvider().getTypeManager().getPayloadFactory()
							.createPayload(context.getDestination().getConfig().getDefaultEventURI(),
									userEvent.getPayload());
				}
			}
			event.setExtId(userEvent.getExtId());
			event.setPayload(payload);

			for (String name : event.getPropertyNames()) {
				Object val = userEvent.getPropertyValue(name);
				if (val instanceof String) {
					event.setProperty(name, (String) val);
				} else if (val != null) {
					event.setProperty(name, val);
				}
			}
			return event;
		}
		return null;
	}

	/**
	 * for internal use
	 */
	@Override
	public final Object serialize(SimpleEvent event, SerializationContext context) throws Exception {
		DefaultEventImpl config = new ExtendedDefaultEventImpl(event);

		for (String name : event.getPropertyNames()) {
			try {
				config.setProperty(name, event.getPropertyValue(name));
			} catch (NoSuchFieldException e) {
			}
		}
		return config;
	}

	/**
	 * for internal use
	 */
	public boolean isJSONPayload() {
		return false;
	}

	/**
	 * for internal use
	 */
	protected boolean isLongType(String name) {
		if (designtimeEvent != null && designtimeEvent.getPropertyType(name) instanceof RDFLongTerm) {
			return true;
		}
		return false;
	}
			
	/**
	 * for internal use
	 * 
	 * @param dtEvent
	 */
	protected void setDesignTimeEvent(String eventUri) {
		RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
		this.designtimeEvent = RSP.getProject().getOntology().getEvent(eventUri);
	}
			
	/**
	 * for internal use
	 * 
	 * @return
	 */
	protected String getDesignTimeEventUri() {
		return (designtimeEvent != null) ? designtimeEvent.getFolderPath() + "/" + designtimeEvent.getName() : null;
	}

}
