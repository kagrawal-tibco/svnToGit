package com.tibco.cep.loadbalancer.impl;

/*
* Author: Ashwin Jayaprakash / Date: Jun 25, 2010 / Time: 4:15:14 PM
*/

public interface CommonConstants {
    /**
     * {@value}
     */
    String NAME_SERVICE = "machine.${machineid}.process.${processid}";

    /**
     * {@value}
     */
    String NAME_MODULE = "agent.${rulesession.name}.loadbalancer";

    /**
     * {@value}
     */
    String NAME_DESTINATION = NAME_MODULE + ".destination.${destination.config.URI}";

    /**
     * {@value}
     */
    String NAME_SOURCE = "${destination.config.URI}.source";

    /**
     * {@value}
     */
    String NAME_SOURCE_OVERRIDE = NAME_SOURCE + ".override";

    /**
     * {@value}
     */
    String DEF_ROUTER_DESTINATION = "com.tibco.cep.loadbalancer.impl.server.integ.ForwardingJmsDestination";

    /**
     * {@value}
     */
    String DEF_RECEIVER_DESTINATION = "com.tibco.cep.loadbalancer.impl.client.integ.PseudoJmsDestination";

    /**
     * {@value}
     */
    String DEF_MEMBERSHIP_PROVIDER = "com.tibco.cep.loadbalancer.impl.server.membership.grid.DataGridMembershipProvider";

    /**
     * {@value}
     */
    String DEF_MEMBERSHIP_PUBLISHER = "com.tibco.cep.loadbalancer.impl.client.membership.grid.DataGridMembershipPublisher";
}
