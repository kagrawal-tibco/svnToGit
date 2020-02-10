package com.tibco.cep.kernel.core.base.tuple;

/*
* Author: Suresh Subramani / Date: 8/23/12 / Time: 2:53 PM
*/

import com.tibco.cep.kernel.model.knowledgebase.SetupException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is used by Process Orchestration.
 * These JoinTables are primarily used by Hawk
 */
public class NoOpJoinTableCollection implements JoinTableCollection {


    AtomicInteger ids = new AtomicInteger(0);

    @Override
    public short addTable(JoinTable joinTable) throws SetupException {
        return (short)ids.getAndIncrement();
    }

    @Override
    public JoinTable getJoinTable(short tableid) {
        return null;
    }

    @Override
    public void removeTable(JoinTable joinTable) {

    }

    @Override
    public JoinTable[] toArray() {
        return new JoinTable[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear() {
    }
}
