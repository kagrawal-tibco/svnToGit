package com.tibco.cep.bpmn.runtime.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.bpmn.runtime.agent.Job.PendingEvent;
import com.tibco.cep.bpmn.runtime.agent.Job.PendingEventState;
import com.tibco.cep.bpmn.runtime.agent.PendingEventImpl;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;

public class PendingEventHelper {
	
	public static final String PENDING_EVENT_PROPERTY_SEPARATOR = "=";
	public static final String PENDING_EVENT_PROPERTY_FORMAT="%d=%s";
	public static final String PENDING_EVENT_VALUE_SEPARATOR=":";
	public static final String PENDING_EVENT_VALUE_FORMAT="%s:%s";

	
	public static void convertPropertyArrayToMap(PropertyArrayString peMapArr, Map<Long, String> peMap) {
		if(peMap == null)
			return;
		for(int i=0;i < peMapArr.length();i++) {
			String rec= peMapArr.get(i).getString();
			//<eventId>=<State>:<taskid>
			String [] kv = rec.split(PENDING_EVENT_PROPERTY_SEPARATOR);
			final Long eventId = Long.valueOf(kv[0]);
			peMap.put(eventId,kv[1]);
		}
		
	}

	public static void convertMapToPropertyArray(Map<Long, String> peMap, PropertyArrayString peMapArr) {
		if(peMapArr == null)
			return;
		peMapArr.clear();
		for(Entry<Long, String> entry:peMap.entrySet()) {
			//<eventId>=<State>:<taskid>
			String rec = String.format(PENDING_EVENT_PROPERTY_FORMAT,entry.getKey(),entry.getValue());
			peMapArr.add(rec);
		}
	}
	
	/**
	 * @param peMap
	 * @return
	 */
	public static Map<String, Set<PendingEvent>> convertMapToPendingEvents(Map<Long, String> peMap,DistributedCacheBasedStore objectStore) {
		Map<String, Set<PendingEvent>> pendingEvents = new HashMap<String, Set<PendingEvent>>();
		for(Entry<Long, String> peEntry:peMap.entrySet()){
			final long eventId = peEntry.getKey();
			final String[] values = peEntry.getValue().split(PENDING_EVENT_VALUE_SEPARATOR);
			//<eventId>=<State>:<taskid>
			final String state = values[0]; // state
			final String task = values[1]; // task id
			PendingEventState peState = PendingEventState.valueOf(state);
			
			if(!pendingEvents.containsKey(task)){
				pendingEvents.put(task, new HashSet<PendingEvent>());
			}
			Set<PendingEvent> peSet = pendingEvents.get(task);
			PendingEvent peEvent = null;
			if(objectStore.getEvent(eventId) != null){
				peEvent = new PendingEventImpl(eventId,peState, objectStore);
			}
			if(peEvent != null && !peSet.contains(peEvent)){
				peSet.add(peEvent);
			}
		}
		return pendingEvents;
	}

}
