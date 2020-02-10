package com.tibco.cep.loadbalancer.impl.server.integ;

import java.util.Map;

import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;

/*
* Author: Ashwin Jayaprakash / Date: 4/21/11 / Time: 11:18 AM
*/
public class DummyDestination extends AbstractDestination<DummyChannel> {
    public DummyDestination(DummyChannel channel, String name) {
        super(channel, new DummyDestinationConfig(name));
    }

    @Override
    protected void destroy(RuleSession session) throws Exception {
    }

    @Override
    public void connect() throws Exception {
    }

	@Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData)
			throws Exception {
		throw new Exception("requestEvent() is not supported with this Channel");
	}
}
