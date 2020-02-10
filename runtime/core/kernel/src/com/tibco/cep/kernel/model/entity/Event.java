package com.tibco.cep.kernel.model.entity;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: Apr 7, 2004
* Time: 3:04:47 PM
*
* Copyright (c) 2004  TIBCO Software Inc.
* Contact: Nick Leong (nleong@tibco.com)
*
*/

/**
 * Base for events, which are immutable entities.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
// TODO document the class
public interface Event extends Entity {


    /**
     * Sets the TTL (Time To Live) of the event, i.e.<!----> specifies how long an event should be valid.
     * When the event expires, it will be removed from the working memory.
     *
     * @param ttl TTL (Time To Live) of the event
     * @.category non-public-api
     * @since 2.0.0
     */
    void setTTL(long ttl);


    /**
     * Gets the TTL (Time To Live) of the event, i.e.<!----> how long an event should be valid.
     * When the event expires, it will be removed from the working memory.
     *
     * @return the TTL of the event
     * @.category public-api
     * @since 2.0.0
     */
    long getTTL();


    /**
     * Checks whether the event has an expiry action.
     *
     * @return true if the Event has an expiry action.
     * @.category public-api
     * @since 2.0.0
     */
    boolean hasExpiryAction();


    /**
     * Called when the TTL expires.
     *
     * @.category public-api
     * @since 2.0.0
     */
    void onExpiry();

    boolean getRetryOnException();
}
