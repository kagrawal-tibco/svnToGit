package com.tibco.be.custom.channel.kafka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.tibco.be.custom.channel.Event;

public class KafkaEvent implements Event {
	
	private String extId;
	private String destinationURI;
	private byte[] payload;
	private String eventUri;
	private HashMap<String, Object> eventProps = new HashMap<String, Object>();

	public KafkaEvent() {
	}

	public KafkaEvent(String extId, String destinationURI, byte[] payload, String expandedName) {
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
	}

	@Override
	public Collection<String> getAllPropertyNames() {
		return new ArrayList<String>(){{add("accountType");}};
	}
}
