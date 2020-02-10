package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableNoKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableNoKeyTableImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableTable;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 12:56:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableNoKeyTableImpl_Mixed extends BaseObjectTableTable_Mixed<ObjectTableNoKeyTableImpl> implements ObjectTableNoKeyTable
{
    public ObjectTableNoKeyTableImpl_Mixed() {
        super();
    }
    
    protected ObjectTableNoKeyTableImpl newSimpleTable() {
        return new ObjectTableNoKeyTableImpl();
    }

    public boolean add(Handle objHandle) {
        if(isThreadLocal(objHandle)) {
            return getOrCreateThreadTable().add(objHandle);
        } else {
            try {
                lock();
                return getOrCreateSharedTable().add(objHandle);
            } finally {
                unlock();
            }
        }
    }

    public Handle[] getAllHandles() {
        ObjectTableTable threadTable = getThreadTable();
        int threadTableSize = 0;
        if (threadTable != null) threadTableSize = threadTable.size();
        Handle[] handles = null;
        int idx = 0;
        try {
            lock();
            if (sharedTable != null && sharedTable.size() > 0) {
                handles = new Handle[sharedTable.size() + threadTableSize];
                for (TableIterator it = sharedTable.iterator(); it.hasNext(); idx++) {
                    handles[idx] = it.next()[0];
                }
            }
        } finally {
            unlock();
        }
        if (handles == null) {
            if(threadTableSize <= 0) handles = NULL_ARR;
            else handles = new Handle[threadTableSize];
        }
        //will only be true if threadTable is not null
        if (threadTableSize > 0) {
            for (TableIterator it = threadTable.iterator(); it.hasNext(); idx++) {
                handles[idx] = it.next()[0];
            }
        }
        return handles;
    }

}
