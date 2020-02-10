package com.tibco.rta.common.service;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 19/4/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessageContext {

    /**
     * Acknowledges the message that was deserialized into the fact.
     *
     * @.category public-api
     * @since 2.0.0
     */
    void acknowledge() throws Exception;

    /**
     * Return true if message is already acknowledged.
     * @return
     */
    boolean isAcked();
}
