package com.tibco.cep.query.stream._retired_;

import com.tibco.cep.query.stream.cache.SharedObjectManager;
import com.tibco.cep.query.stream.impl.rete.Helper;
import com.tibco.cep.query.stream.impl.rete.ReteEntityBatchHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle.Type;
import com.tibco.cep.query.stream.impl.rete.input.QObjectManager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;

/*
 * Author: Ashwin Jayaprakash Date: Nov 16, 2007 Time: 4:14:18 PM
 */

/**
 * Keeps collecting the results and processes the entire list in the
 * {@link #end()}.
 */

// todo Is this still needed?
public class AsyncFindMatchesRule extends FindMatchesRule {
    protected final int batchProcessSize;

    protected AppendOnlyQueue<Long> collectedReteIds;

    public AsyncFindMatchesRule(ReteQuery query, String name, String classOfEntitiesToBeFetched,
            Long trigger, SharedObjectManager som, QObjectManager qom, int batchProcessSize)
            throws ClassNotFoundException {
        super(query, name, classOfEntitiesToBeFetched, trigger, som, qom);

        this.collectedReteIds = new AppendOnlyQueue<Long>();
        this.batchProcessSize = batchProcessSize;
    }

    @Override
    protected void handleEntity(Object reteObject) throws Exception {
        Long id = Helper.extractId(reteObject);

        som.getCache().put(id, reteObject);
        som.shareObjectViaKey(id);

        collectedReteIds.add(id);

        if (collectedReteIds.size() >= batchProcessSize) {
            sendBatch();
        }
    }

    private void sendBatch() throws Exception {
        int size = collectedReteIds.size();

        if (size == 0) {
            return;
        }

        Long[] ids = collectedReteIds.toArray(new Long[size]);

        ReteEntityBatchHandle batchHandle = new ReteEntityBatchHandle(classOfEntitiesToBeFetched,
                Type.NEW, ids);
        query.enqueueInput(batchHandle);

        collectedReteIds.clear();
    }

    @Override
    public void end() throws Exception {
        sendBatch();
    }
}