package com.tibco.cep.driver.jms;

import java.util.Properties;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * A temporary JMS destination configuration used in {@link JMSTemporaryDestination}
 * @author moshaikh
 *
 */
public class JMSTempDestinationConfig implements DestinationConfig {
	private String uri;
	private ExpandedName defaultEventURI;
	private EventSerializer eventSerializer;
	private Properties properties;

	public JMSTempDestinationConfig(ExpandedName responseEventURI, String eventSerializer) throws Exception {
		this.uri = "DUMMY_URI";
		this.defaultEventURI = responseEventURI;
		this.eventSerializer = (EventSerializer) Class.forName(eventSerializer).newInstance();
		properties = new Properties();
		properties.put("Queue", "DUMMY_QUEUE");
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getURI() {
		return uri;
	}

	@Override
	public ExpandedName getDefaultEventURI() {
		return defaultEventURI;
	}

	@Override
	public EventSerializer getEventSerializer() {
		return eventSerializer;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public ChannelConfig getChannelConfig() {
		return null;
	}

	@Override
	public void setFilter(Event event) {
	}

	@Override
	public Event getFilter() {
		return null;
	}
}
