package com.tibco.cep.query.stream.impl.rete;

/*
* Author: Ashwin Jayaprakash Date: Jul 29, 2008 Time: 4:57:02 PM
*/
public interface GenericReteEntityHandle {
    ReteEntityHandleType getType();

    boolean isWarm();

    void warmUp();

    void coolDown();
}
