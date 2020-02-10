/**
 * 
 */
package com.tibco.be.custom.channel.framework;

import java.util.Collection;
import java.util.HashMap;

import com.tibco.be.custom.channel.Event;

/**
 * @author vpatil
 */
public class CustomEvent implements Event {
	
	private String extId;
	private String destinationURI;
	private byte[] payload;
	private String eventUri;
	private HashMap<String, Object> eventProps = new HashMap<String, Object>();

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#getExtId()
	 */
	@Override
	public String getExtId() {
		return extId;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#setExtId(java.lang.String)
	 */
	@Override
	public void setExtId(String extId) {
		this.extId = extId;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#getDestinationURI()
	 */
	@Override
	public String getDestinationURI() {
		return destinationURI;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#setDestinationURI(java.lang.String)
	 */
	@Override
	public void setDestinationURI(String destinationURI) {
		this.destinationURI = destinationURI;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#getPayload()
	 */
	@Override
	public byte[] getPayload() {
		return payload;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#setPayload(byte[])
	 */
	@Override
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#getEventUri()
	 */
	@Override
	public String getEventUri() {
		return eventUri;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#setEventUri(java.lang.String)
	 */
	@Override
	public void setEventUri(String eventUri) {
		this.eventUri = eventUri;
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object value) {
		this.eventProps.put(name, value);
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#getPropertyValue(java.lang.String)
	 */
	@Override
	public Object getPropertyValue(String name) {
		return this.eventProps.get(name);
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.custom.channel.Event#getAllPropertyNames()
	 */
	@Override
	public Collection<String> getAllPropertyNames() {
		return eventProps.keySet();
	}

}
