package com.tibco.cep.runtime.model.event.impl;

public class EventPropertyModificationException extends RuntimeException
{
	public EventPropertyModificationException(SimpleEventImpl ev, String propName) {
		super(String.format("Modified event property after assertion.  Property name: %s Event: %s", propName, ev));
	}

}
