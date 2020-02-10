package com.tibco.be.custom.channel.framework;

import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSession;

public class CustomEventContext extends AbstractEventContext{

	BaseDestination destination;
	SimpleEvent event;
	EventContext context;
	CustomDestination customDestination;

	public CustomEventContext(EventContext ctx,CustomDestination customDestination){
		this.context=ctx;
		this.customDestination=customDestination;
		this.destination=customDestination.getBaseDestination();
	}

	@Override
	public boolean reply(SimpleEvent replyEvent) {
		try {
			return context.reply((Event)destination.getSerializer().serialize(replyEvent, new DefaultSerializationContext((RuleSession) destination.getRuleSession(), customDestination)));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String replyImmediate(SimpleEvent replyEvent) {
		return null;
	}

	@Override
	public void acknowledge() {
		context.acknowledge();
	}

	@Override
	public void rollBack() {
		context.rollback();
	}

	@Override
	public CustomDestination getDestination() {
		return customDestination;
	}

	@Override
	public Object getMessage() {
		return null;
	}
}
