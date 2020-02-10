package com.tibco.cep.loadbalancer.impl.transport;

import com.tibco.cep.loadbalancer.impl.message.SimpleSpecialMessage;

/*
* Author: Ashwin Jayaprakash / Date: Aug 3, 2010 / Time: 3:41:48 PM
*/
public class HelloMessage extends SimpleSpecialMessage {
    public HelloMessage() {
    }

    public HelloMessage(Object uniqueId, Object senderId) {
        super(uniqueId, senderId);
    }
}
