package com.tibco.cep.loadbalancer.impl.transport;

import com.tibco.cep.loadbalancer.impl.CommonConstants;

/*
* Author: Ashwin Jayaprakash / Date: Jul 6, 2010 / Time: 6:54:01 PM
*/

public interface TransportConstants {
    /**
     * {@value}
     */
    String NAME_TRANSPORT = "transport";

    /**
     * {@value}
     */
    String PROPERTY_PREFIX_ACTUAL_SINK =
            CommonConstants.NAME_DESTINATION + ".sink.${transportconfig.name}";
}
