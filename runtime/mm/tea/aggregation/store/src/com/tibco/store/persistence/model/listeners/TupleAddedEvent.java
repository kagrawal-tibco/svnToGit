package com.tibco.store.persistence.model.listeners;

import java.util.EventObject;

import com.tibco.store.persistence.model.MemoryTuple;

// TODO need to make sure the eventObject is immutable
public class TupleAddedEvent extends EventObject {
	
	public TupleAddedEvent(MemoryTuple tuple) {
		super(tuple);	
	}
	
}
