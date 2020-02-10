package com.tibco.cep.loadbalancer.client.core;

import com.tibco.cep.loadbalancer.message.DistributableMessage;

/*
* Author: Ashwin Jayaprakash / Date: Jul 26, 2010 / Time: 1:16:57 PM
*/

public interface MessageCustomizer {
    /**
     * @param distributableMessage
     * @return The same message with custom/new headers or a new one.
     * @throws Exception
     */
    DistributableMessage customize(DistributableMessage distributableMessage) throws Exception;
}
