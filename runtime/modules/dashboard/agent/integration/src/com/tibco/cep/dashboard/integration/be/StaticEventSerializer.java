package com.tibco.cep.dashboard.integration.be;

import com.tibco.cep.driver.http.serializer.RESTMessageSerializer;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.session.RuleSession;

public class StaticEventSerializer extends RESTMessageSerializer {
	
	private Class<? extends SimpleEvent> eventClass;

	@Override
	protected SimpleEvent createEvent(String ns, String nm, RuleSession session, DestinationConfig config) throws Exception {
		if (eventClass == null){
			synchronized (this) {
				if (eventClass == null){
					String className = config.getDefaultEventURI().getLocalName();
					eventClass = Class.forName(className).asSubclass(SimpleEvent.class);
				}
			}
		}
		return eventClass.newInstance();
	}

	@Override
	protected void deserializePayload(SimpleEvent event, RuleSession session, Object message) throws Exception {
		if (event instanceof SimpleDBEvent){
			event.setPayload(new ObjectPayload(message));
			return;
		}
		super.deserializePayload(event, session, message);
	}
}