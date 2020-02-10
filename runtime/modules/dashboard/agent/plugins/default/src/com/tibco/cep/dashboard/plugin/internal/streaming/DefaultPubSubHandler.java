package com.tibco.cep.dashboard.plugin.internal.streaming;

import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;
import com.tibco.cep.dashboard.psvr.streaming.PubSubHandler;

//PORT the concrete handlers should not worry about shutdown, it should be controlled via the AbstractHandlerFactory
public class DefaultPubSubHandler extends PubSubHandler {

	@Override
	protected void init() {
	}

	@Override
	protected void subscribe(String subscriptionName, String contextName, MALSourceElement sourceElement, String condition) throws StreamingException {
	}

	@Override
	protected void unsubscribe(String subscriptionName) throws StreamingException {
	}

	@Override
	public void shutdown() throws NonFatalException {
	}

}
