package com.tibco.cep.loadbalancer.impl.client;

import com.tibco.cep.loadbalancer.impl.CommonConstants;

/*
* Author: Ashwin Jayaprakash / Date: Jun 25, 2010 / Time: 4:14:43 PM
*/

public interface ClientConstants extends CommonConstants {
    /**
     * {@value}
     */
    String NAME_CLIENT = NAME_MODULE + ".client";

    /**
     * {@value}
     */
    String NAME_MEMBERSHIP_PUBLISHER = NAME_MODULE + ".membership.publisher";

    /**
     * {@value}
     */
    String NAME_MEMBER = "${service.resourceId}." + NAME_MODULE + ".member";

    /**
     * {@value}
     */
    String NAME_SINK = "${member.id}.destination.${destination.config.URI}.sink";

    //---------------

    /**
     * {@value}
     */
    String PROPERTY_MEMBERSHIP_PUBLISHER_CLASSNAME = NAME_MEMBERSHIP_PUBLISHER + ".classname";

    /**
     * {@value}
     */
    String PROPERTY_SINK_TRANSPORT = NAME_DESTINATION + ".sink.transport";

    /**
     * {@value}
     */
    String PROPERTY_TRANSPORT = "transport";
}
