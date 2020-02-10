package com.tibco.cep.driver.hawk.internal;

import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;

public class HawkMessageContext extends AbstractEventContext {
	private Destination dest;
	private Object msg;

	/**
	 * 
	 * @param dest
	 * @param msg
	 */
	public HawkMessageContext(Destination dest, Object msg) {
		this.dest = dest;
		this.msg = msg;
	}

	@Override
	public boolean reply(SimpleEvent paramSimpleEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Destination getDestination() {
		return dest;
	}

	@Override
	public Object getMessage() {
		return msg;
	}

	@Override
	public String replyImmediate(SimpleEvent replyEvent) {
		// TODO Auto-generated method stub
		return null;
	}
}