package com.tibco.be.custom.channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A convenience Pojo, a default implementation of the <code>Event</code> Users can extend this class or implement <code>Event</code> directly.
 * @.category public-api
 */

public class DefaultEventImpl implements EventWithId {

	protected long id;
	protected String extId;
	protected String destinationURI;
	protected byte[] payload;
	protected String eventUri;
	protected HashMap<String, Object> eventProps = new LinkedHashMap<String, Object>();

	public DefaultEventImpl() {
	}
	
	public DefaultEventImpl(long id, String extId, String destinationURI, byte[] payload, String expandedName) {
		this.id = id;
		this.extId = extId;
		this.payload = payload;
		this.eventUri = expandedName;
	}
	
	@Override
	public String getExtId() {
		return extId;
	}

	@Override
	public void setExtId(String extId) {
		this.extId = extId;
	}

	@Override
	public String getDestinationURI() {
		return destinationURI;
	}

	@Override
	public void setDestinationURI(String destinationURI) {
		this.destinationURI = destinationURI;
	}

	@Override
	public byte[] getPayload() {
		return payload;
	}

	@Override
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	@Override
	public String getEventUri() {
		return eventUri;
	}

	@Override
	public void setEventUri(String expandedName) {
		this.eventUri = expandedName;
	}

	@Override
	public void setProperty(String name, Object value) {
		eventProps.put(name, value);
	}

	@Override
	public Object getPropertyValue(String name) {
		return eventProps.get(name);
	};

	@Override
	public Collection<String> getAllPropertyNames() {
		return eventProps.keySet();
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId (long id) {
		this.id = id;
	}
}
