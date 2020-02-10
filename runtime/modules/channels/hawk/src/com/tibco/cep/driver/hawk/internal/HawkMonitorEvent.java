package com.tibco.cep.driver.hawk.internal;

import java.util.EventObject;

public class HawkMonitorEvent {
	private String eventType;
	private EventObject event;

	public HawkMonitorEvent(String eventType, EventObject event) {
		this.eventType = eventType;
		this.event = event;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public EventObject getEvent() {
		return event;
	}

	public void setEvent(EventObject event) {
		this.event = event;
	}

}
