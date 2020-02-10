package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.TupleRowNoKey;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MixedIteratorLock;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableNoKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableNoKeyTableImpl;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableTable;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 12:56:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TupleTableNoKeyTableImpl_Mixed  extends BaseTupleTableTable_Mixed<TupleTableNoKeyTableImpl> implements TupleTableNoKeyTable
{
    public TupleTableNoKeyTableImpl_Mixed() {
        super();
    }

    protected TupleTableNoKeyTableImpl newSimpleTable() {
        return new TupleTableNoKeyTableImpl();
    }

    public MigrationIterator<TupleRowNoKey> migrationIterator() {
        //cache only tables should be empty during hot deploy
        return sharedTable.migrationIterator();
    }

    public Handle[][] getAllRows() {
        Handle[][] rows = null;
        TupleTableTable threadTable = getThreadTable();
        int threadTableSize = 0;
        int idx = 0;
        if (threadTable != null) threadTableSize = threadTable.size();
        try {
            lock();
            if (sharedTable != null && sharedTable.size() > 0) {
                rows = new Handle[sharedTable.size() + threadTableSize][];
                for (TableIterator it = sharedTable.iterator(); it.hasNext(); idx++) {
                    rows[idx] = it.next();
                }
            }
        } finally {
            unlock();
        }
        if (rows == null) {
            if(threadTableSize <= 0) rows = NULL_ARR;
            else rows = new Handle[threadTableSize][];
        }
        //will only be true if threadTable is not null
        if (threadTableSize > 0) {
            for (TableIterator it = threadTable.iterator(); it.hasNext(); idx++) {
                rows[idx] = it.next();
            }
        }
        return rows;
    }

    //locked by caller
    public TableIterator iterator() {
        TupleTableNoKeyTableImpl threadTable = threadTables.get();
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
    
    /*
        Unlocks this table after it is done iterating on the shared table
     */
    protected static class MixedIterator extends TupleTableNoKeyTableImpl.RowIterator
    {
        protected TupleTableNoKeyTableImpl nextTable;
        protected MixedIteratorLock lock;

        protected MixedIterator(TupleTableNoKeyTableImpl shared, TupleTableNoKeyTableImpl thread, MixedIteratorLock iteratorLock) {
            super(shared);
            nextTable = thread;
            lock = iteratorLock;
            assert(shared != null && thread != null && shared != thread);
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