package com.tibco.store.persistence.model.listeners;

import java.util.EventObject;

import com.tibco.store.persistence.model.MemoryTuple;

// TODO need to make sure the eventObject is immutable
public class TupleRemovedEvent extends EventObject {
	public TupleRemovedEvent(MemoryTuple tuple) {
		super(tuple);
	}
}
