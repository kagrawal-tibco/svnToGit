package com.tibco.cep.loadbalancer.message;

/*
* Author: Ashwin Jayaprakash / Date: Jul 27, 2010 / Time: 2:53:08 PM
*/

public interface DistributionResponse {
    /**
     * @return
     * @see DistributableMessage#getUniqueId()
     */
    Object getUniqueIdOfMessage();

    boolean isPositiveAck();
}
