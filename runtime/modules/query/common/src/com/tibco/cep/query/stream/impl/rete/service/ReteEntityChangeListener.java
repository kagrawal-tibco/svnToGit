package com.tibco.cep.query.stream.impl.rete.service;

import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 12:13:47 PM
 */

public interface ReteEntityChangeListener {
    boolean hasListeners(Class entityClass);

    void onNew(Class entityClass, Long id, int version);

    void onModify(Class entityClass, Long id, int version);

    void onDelete(Class entityClass, Long id, int version);

    void onEntity(ReteEntityHandle entityHandle);
}