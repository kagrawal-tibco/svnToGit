package com.tibco.cep.query.model;

/**
 * Possible types of stream emissions.
 */
public enum EmitType {

    /**
     * Expired entity.
     */
    DEAD,

    /**
     * New, modified, expired entities.
     */
    FULL,

    /**
     * New or modified entity.
     */
    NEW,
}
