package com.tibco.cep.loadbalancer.impl.client.transport.tcp;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash / Date: 10/4/11 / Time: 3:32 PM
*/
public interface MessageReceiver {
    Object unpackAsEventOrEventContext(Object message);

    void receive(RuleSession ruleSession, SimpleEvent event) throws Exception;

    void receive(RuleSession ruleSession, AbstractEventContext abstractEventContext, boolean ackExpected)
            throws Exception;

    void ackSent(AckHandle ackHandle);
}
