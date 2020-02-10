package com.tibco.cep.query.stream.impl.rete.query;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.impl.rete.GenericReteEntityHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityBatchHandle;
import com.tibco.cep.query.stream.impl.rete.ReteEntityHandle;

/*
* Author: Ashwin Jayaprakash Date: May 17, 2008 Time: 12:27:53 PM
*/
public class HealthReporter {
    ReteQuery query;

    public HealthReporter(ReteQuery query) {
        this.query = query;
    }

    /**
     * @return The approximate number of entity notifications that are pending processing by the
     *         query.
     */
    public int getPendingEntityCount() {
        ConcurrentLinkedQueue<GenericReteEntityHandle> queuedInput = query.getQueuedInput();

        int count = 0;

        for (GenericReteEntityHandle handle : queuedInput) {
            if (handle instanceof ReteEntityHandle) {
                count++;
            }
            else if (handle instanceof ReteEntityBatchHandle) {
                ReteEntityBatchHandle batchHandle = (ReteEntityBatchHandle) handle;

                Long[] ids = batchHandle.getReteIds();
                SharedPointer[] pointers = batchHandle.getSharedPointers();

                if (ids != null) {
                    count = count + ids.length;
                }
                else {
                    count = count + pointers.length;
                }
            }
        }

        return count;
    }

    /**
     * @return If the query was created with "SS-then-CQ" mode then, if the query is still
     *         processing the Snapshot items, then the notifications that arrive from the live
     *         changes are accumulated. This returns the count of those pending/accumulated items.
     */
    public int getAccumulatedEntityCountDuringSS() {
        LinkedBlockingQueue<GenericReteEntityHandle> items = query.getAccumulatedItemsDuringSSA();

        if (items != null) {
            return items.size();
        }

        return 0;
    }
}
