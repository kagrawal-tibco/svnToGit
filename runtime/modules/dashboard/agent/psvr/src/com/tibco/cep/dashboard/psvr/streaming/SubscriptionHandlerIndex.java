package com.tibco.cep.dashboard.psvr.streaming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class SubscriptionHandlerIndex {
	
	private List<SubscriptionHandler> handlers;
	private Map<String,List<SubscriptionHandler>> subscriptionNameToSubscriptionHandlersMap;
	
	SubscriptionHandlerIndex(){
		handlers = new LinkedList<SubscriptionHandler>();
		subscriptionNameToSubscriptionHandlersMap = new HashMap<String, List<SubscriptionHandler>>();
	}
	
	
	void addHandler(SubscriptionHandler handler){
		handlers.add(handler);
		DataSourceUpdateHandler[] updateHandlers = handler.getUpdateHandlers();
		for (DataSourceUpdateHandler dataSourceUpdateHandler : updateHandlers) {
			String subscriptionName = dataSourceUpdateHandler.getSubscriptionName();
			List<SubscriptionHandler> handlers = subscriptionNameToSubscriptionHandlersMap.get(subscriptionName);
			if (handlers == null){
				handlers = new LinkedList<SubscriptionHandler>();
				subscriptionNameToSubscriptionHandlersMap.put(subscriptionName, handlers);
			}
			//we don't need to synchronize subscriptionNameToSubscriptionHandlersMap since  
			//the caller synchronizes the calls to addHandler/removeHandler
			//we need to synchronize the handlers list, since getHandlers(..) 
			//could be called in-between the manipulations
			synchronized (handlers) {
				handlers.add(handler);	
			}
		}
	}
	
	void removeHandler(SubscriptionHandler handler){
		handlers.remove(handler);
		DataSourceUpdateHandler[] updateHandlers = handler.getUpdateHandlers();
		for (DataSourceUpdateHandler dataSourceUpdateHandler : updateHandlers) {
			String subscriptionName = dataSourceUpdateHandler.getSubscriptionName();
			List<SubscriptionHandler> handlers = subscriptionNameToSubscriptionHandlersMap.get(subscriptionName);
			if (handlers != null){
				//we don't need to synchronize subscriptionNameToSubscriptionHandlersMap since  
				//the caller synchronizes the calls to addHandler/removeHandler
				//we need to synchronize the handlers list, since getHandlers(..) 
				//could be called in-between the manipulations				
				synchronized (handlers) {
					handlers.remove(handler);
					if (handlers.isEmpty() == true) {
						subscriptionNameToSubscriptionHandlersMap.remove(subscriptionName);
					}
				}
			}
		}
	}
	
	Collection<SubscriptionHandler> getHandlers(String subscriptionName){
		List<SubscriptionHandler> handlers = subscriptionNameToSubscriptionHandlersMap.get(subscriptionName);
		//no handlers found, return empty list
		if (handlers == null){
			return Collections.emptyList();
		}
		//we found handlers, synchronize on it to prevent modifications to it via addHandler/removeHandler 
		synchronized (handlers) {
			//we should check if the list is still valid, since removeHandler can remove the list
			if (subscriptionNameToSubscriptionHandlersMap.containsKey(subscriptionName) == false){
				//the list is non-existing, we return empty list  
				return Collections.emptyList();	
			}
			//we create a new list , we are taking a gamble here where-in the list of handlers can get
			//out of sync during the iteration over the handlers by the caller of getHandlers
			return new ArrayList<SubscriptionHandler>(handlers);	
		}
	}

	Collection<String> getSubscriptionNames(){
		return subscriptionNameToSubscriptionHandlersMap.keySet();
	}
	
	Collection<SubscriptionHandler> getHandlers(){
		return handlers;
	}	
}
