package com.tibco.cep.bpmn.runtime.agent;

import com.tibco.cep.bpmn.runtime.agent.Job.PendingEvent;
import com.tibco.cep.bpmn.runtime.agent.Job.PendingEventState;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;

public class PendingEventImpl implements PendingEvent {

	Event event;
	long eventId = -1L; // lazy loading
	PendingEventState state; // 0x00 - ASSERTED, 0x01 Acknowledged/Reloaded,
	private DistributedCacheBasedStore objectManager;
								// 0xFF consumed

	PendingEventImpl(Event event) {
		this.event = event;
		this.eventId = event.getId();
		state = PendingEventState.ASSERT;
	}
	
	public PendingEventImpl(long eventId, PendingEventState state2, DistributedCacheBasedStore objectManager) {
		this.eventId = eventId;
		this.state = state2;
		this.objectManager = objectManager;
	}
	
	public boolean isInitialized() {
		return event != null;
	}
	
	/**
	 * lazy load the event during recovery
	 */
	private void loadEvent() {
		if(event == null){
			if(this.eventId == -1L)
				throw new RuntimeException("Invalid event id specified.");
			event = objectManager.getEvent(eventId);
		}
	}

	public long getId() {
		return eventId;
	}

	@Override
	public Event getEvent() {
		loadEvent();
		return event;
	}

	@Override
	public boolean consume() {
		state = PendingEventState.CONSUMED;
		return true;
	}

	@Override
	public boolean acknowledge() {
		state = PendingEventState.ACKNOWLEDGED;
		return true;
	}

	@Override
	public boolean reload() {
		state = PendingEventState.RELOAD;
		return true;

	}

	@Override
	public PendingEventState getState() {
		return state;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (eventId ^ (eventId >>> 32));
		return result;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PendingEventImpl other = (PendingEventImpl) obj;
		if (eventId != other.eventId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PendingEventImpl [eventId=" + eventId + ", state=" + state + "]";
	}

	
	
	
	
	

}