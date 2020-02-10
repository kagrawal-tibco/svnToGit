package com.tibco.cep.query.stream._retired_;

import com.tibco.cep.query.stream.cache.SharedObjectManager;
import com.tibco.cep.query.stream.impl.rete.Helper;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle.Type;
import com.tibco.cep.query.stream.impl.rete.input.QObjectManager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/*
 * Author: Ashwin Jayaprakash Date: Feb 15, 2008 Time: 2:53:25 PM
 */

/**
 * Starts feeding the objects to the {@link ReteQuery} when this Rule is
 * deployed and the {@link #trigger} is asserted.
 */

// todo Is this still needed?
public class SyncFindMatchesRule extends FindMatchesRule {
    public SyncFindMatchesRule(ReteQuery query, String name, String classOfEntitiesToBeFetched,
            Long trigger, SharedObjectManager som, QObjectManager qom)
            throws ClassNotFoundException {
        super(query, name, classOfEntitiesToBeFetched, trigger, som, qom);
    }

    @Override
    protected void handleEntity(Object reteObject) {
        try {
            Long id = Helper.extractId(reteObject);

            som.getCache().put(id, reteObject);

            som.shareObjectViaKey(id);

            query.enqueueInput(new ReteEntityHandle(reteObject.getClass().getName(), id, Type.NEW));
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void end() {
    }
}
