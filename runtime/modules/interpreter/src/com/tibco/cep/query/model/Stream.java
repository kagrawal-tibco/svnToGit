package com.tibco.cep.query.model;


/**
 * Stream configuration applicable to an entity.
 */
public interface Stream
    extends QueryContext {


    /**
     *
     * @return AcceptType.
     */
    AcceptType getAcceptType();

    
    /**
     * @return StreamPolicy that governs the Stream behavior, if any.
     */
    StreamPolicy getPolicy();

    /**
     * @return EmitType that is listened to, if any.
     */
    EmitType getEmitType();

}
