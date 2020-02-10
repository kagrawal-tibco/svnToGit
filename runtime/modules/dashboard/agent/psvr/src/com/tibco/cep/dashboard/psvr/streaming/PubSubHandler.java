package com.tibco.cep.dashboard.psvr.streaming;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;

public abstract class PubSubHandler extends AbstractHandler {

	protected abstract void subscribe(String subscriptionName, String contextName, MALSourceElement sourceElement, String condition) throws StreamingException;
	
	protected abstract void unsubscribe(String subscriptionName) throws StreamingException;
	
	@Override
	public abstract void shutdown() throws NonFatalException;
}
