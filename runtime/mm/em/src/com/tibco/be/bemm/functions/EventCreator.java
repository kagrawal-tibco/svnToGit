package com.tibco.be.bemm.functions;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.loader.BEClassLoader;

class EventCreator {

	private Logger logger;
	private BEClassLoader classLoader;
	
	private String eventURI;
	
	EventCreator(Logger logger, BEClassLoader classLoader, String eventURI) {
		super();
		this.logger = logger;
		this.classLoader = classLoader;
		this.eventURI = eventURI;
	}

	SimpleEvent create() {
		try {
			return (SimpleEvent) classLoader.createEntity(eventURI);
		} catch (Exception e) {
			logger.log(Level.WARN, "could not create an instance of " + eventURI, e);
			return null;
		}
	}
}