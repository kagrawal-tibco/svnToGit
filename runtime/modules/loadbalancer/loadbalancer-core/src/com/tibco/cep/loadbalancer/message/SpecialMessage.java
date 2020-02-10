package com.tibco.cep.loadbalancer.message;

/*
* Author: Ashwin Jayaprakash / Date: Aug 3, 2010 / Time: 1:23:33 PM
*/

/**
 * For communicating and coordinating apart from the actual messages.
 */
public interface SpecialMessage {
    Object getUniqueId();

    Object getSenderId();
}
