package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MixedIteratorLock;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableTableImpl;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 6:52:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseObjectTableTable_Mixed<TABLE extends ObjectTableTableImpl> extends BaseMixedTable<TABLE> implements ObjectTableTable
{
    protected ThreadLocal<TABLE> threadTables;

    protected BaseObjectTableTable_Mixed() {
        super();
        _reset();
    }

    private void _reset() {
        threadTables = new ThreadLocal();
    }
    
    //reset is synchronized by the caller
    public void reset() {
        super.reset();
        _reset();
    }

    
    protected TABLE getThreadTable() {
        return threadTables.get();
    }

    protected TABLE getOrCreateThreadTable() {
        TABLE ret = threadTables.get();
        if (ret == null) {
            ret = newSimpleTable();
            threadTables.set(ret);
        }
        return ret;
    }
    
    public boolean remove(Handle objHandle) {
        if (isThreadLocal(objHandle)) {
            return getOrCreateThreadTable().remove(objHandle);
        } else {
            try {
                lock();
                return getOrCreateSharedTable().remove(objHandle);
            } finally {
                unlock();
            }
        }
    }

    protected boolean isThreadLocal(Handle handle) {
        return handle.getSharingLevel() == EntitySharingLevel.UNSHARED;
    }
    
    //locked by caller
    public TableIterator iterator() {
        ObjectTableTableImpl threadTable = threadTables.get();
        if(sharedTable == null || sharedTable.size() == 0) {
            //only sharedTable needs to be protected by the lock 
            iteratorLock.unlock();
            if(threadTable == null || threadTable.size() == 0) return NULL_ITER;
            else return threadTable.iterator();
        } else if (threadTable == null || threadTable.size() == 0) {
            return sharedTable.iterator();
        } else {
            return new MixedIterator(sharedTable, threadTable, iteratorLock);
        }
    }

    /**
     * Unlocks this table after finished iterating over shared table
    */
    protected static class MixedIterator extends ObjectTableTableImpl.ObjectEntryIterator
    {
        protected ObjectTableTableImpl nextTable;
        protected MixedIteratorLock lock;
                
        protected MixedIterator(ObjectTableTableImpl shared, ObjectTableTableImpl thread, MixedIteratorLock iteratorLock) {
            super(shared);
            assert(shared != null && thread != null && shared != thread);
            nextTable = thread;
            lock = iteratorLock;
        }
    
        public boolean hasNext() {
            boolean result = super.hasNext();
            if(!result && nextTable != null) {
                lock.unlock();
                init(nextTable);
                nextTable = null;
                return super.hasNext();
            }
            return result; 
        }
    }
}
