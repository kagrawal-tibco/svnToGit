package com.tibco.be.custom.channel;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;

/**
 * Extension to DefaultEventImpl that also keeps an handle to the original SimpleEvent.
 * 
 * @author moshaikh
 */
public class ExtendedDefaultEventImpl extends DefaultEventImpl {
	private SimpleEvent simpleEvent;
	
	ExtendedDefaultEventImpl(SimpleEvent simpleEvent) throws Exception {
		super(simpleEvent.getId(), simpleEvent.getExtId(), simpleEvent.getDestinationURI(), null, simpleEvent.getExpandedName().toString());
		if (simpleEvent.getPayload() instanceof XiNodePayload && ((XiNodePayload)simpleEvent.getPayload()).isJSONPayload()) {
			this.setPayload(((XiNodePayload)simpleEvent.getPayload()).toString().getBytes());
		} else {
			this.setPayload(simpleEvent.getPayload() != null ? simpleEvent.getPayload().toBytes() : null);
		}
		this.simpleEvent = simpleEvent;
	}
	
	public SimpleEvent getUnderlyingSimpleEvent() {
		return this.simpleEvent;
	}
}
