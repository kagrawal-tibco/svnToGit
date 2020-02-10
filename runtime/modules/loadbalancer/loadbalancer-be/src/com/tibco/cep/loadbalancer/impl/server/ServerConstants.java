package com.tibco.cep.loadbalancer.impl.server;

import com.tibco.cep.loadbalancer.impl.CommonConstants;

/*
* Author: Ashwin Jayaprakash / Date: Jun 3, 2010 / Time: 4:54:09 PM
*/

public interface ServerConstants extends CommonConstants {
    /**
     * {@value}
     */
    String NAME_SERVER = NAME_MODULE + ".server";

    /**
     * {@value}
     */
    String NAME_KERNEL = NAME_MODULE + ".kernel";

    /**
     * {@value}
     */
    String NAME_MEMBERSHIP_CHANGE_PROVIDER = NAME_MODULE + ".membership.changeprovider";

    /**
     * {@value}
     */
    String NAME_LOADBALANCER = NAME_DESTINATION + ".actual";

    //---------------

    /**
     * {@value}
     */
    String PROPERTY_MEMBERSHIP_CHANGE_PROVIDER_CLASSNAME = NAME_MEMBERSHIP_CHANGE_PROVIDER + ".classname";

    /**
     * {@value}
     */
    String PROPERTY_DESTINATION_ROUTING_KEY = NAME_DESTINATION + ".routing.key";
}
