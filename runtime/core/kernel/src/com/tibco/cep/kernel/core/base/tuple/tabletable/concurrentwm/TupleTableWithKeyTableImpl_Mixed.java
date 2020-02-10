package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.TupleRowWithKey;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MixedIteratorLock;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableWithKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.TupleTableWithKeyTableImpl;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 12:56:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TupleTableWithKeyTableImpl_Mixed extends BaseTupleTableTable_Mixed<TupleTableWithKeyTableImpl> implements TupleTableWithKeyTable
{
    public TupleTableWithKeyTableImpl_Mixed() {
        super();
    }

    protected TupleTableWithKeyTableImpl newSimpleTable() {
        return new TupleTableWithKeyTableImpl();
    }

    public Handle[][] getAllRows(int key) {
        Handle[][] ret = NULL_ARR;
        int ii = 0;
        try {
            lock();
            if (sharedTable != null && sharedTable.size() > 0) {
                ret = new Handle[64][];
                for(TableIterator it = sharedTable.keyIterator(key); it.hasNext(); ii++) {
                    if(ii == ret.length) { //resize
                        Handle[][] newRet = new Handle[ret.length * 2][];
                        System.arraycopy(ret, 0, newRet, 0, ret.length);
                        ret = newRet;
                    }
                    ret[ii] = it.next();
                }
            }
        } finally {
            unlock();
        }
        
        TupleTableWithKeyTable threadTable = getThreadTable();
        if (threadTable != null && threadTable.size() > 0) {
            if(ret == NULL_ARR) ret = new Handle[64][];
            for(TableIterator it = threadTable.keyIterator(key); it.hasNext(); ii++) {
                if(ii == ret.length) { //resize
                    Handle[][] newRet = new Handle[ret.length * 2][];
                    System.arraycopy(ret, 0, newRet, 0, ret.length);
                    ret = newRet;
                }
                ret[ii] = it.next();
            }
        }
        //make sure it ends with a null value
        if(ii < ret.length) ret[ii] = null;
        return ret;
    }

    public MigrationIterator<TupleRowWithKey> migrationIterator() {
        //cache only tables should be empty during hot deploy
        return sharedTable.migrationIterator();
    }

    public TableIterator iterator() {
        TupleTableWithKeyTableImpl threadTable = threadTables.get();
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

    public TableIterator keyIterator(int key) {
        TupleTableWithKeyTableImpl threadTable = threadTables.get();
        if(sharedTable == null || sharedTable.size() == 0) {
            //only sharedTable needs to be protected by the lock 
            iteratorLock.unlock();
            if(threadTable == null || threadTable.size() == 0) return NULL_ITER;
            else return threadTable.keyIterator(key);
        } else if (threadTable == null || threadTable.size() == 0) {
            return sharedTable.keyIterator(key);
        } else {
            return MixedKeyIterator.newInstance(key, sharedTable, threadTable, iteratorLock);
        }
    }

    /*
       Unlocks this table after it is done iterating on the shared table
    */
  protected static class MixedIterator extends TupleTableWithKeyTableImpl.RowIterator
    {
        protected TupleTableWithKeyTableImpl nextTable;
        protected MixedIteratorLock lock;

        protected MixedIterator(TupleTableWithKeyTableImpl shared, TupleTableWithKeyTableImpl thread, MixedIteratorLock iteratorLock) {
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

    /*
       Unlocks this table after it is done iterating on the shared table
    */
    protected static class MixedKeyIterator extends TupleTableWithKeyTableImpl.KeyIterator
    {
        protected TupleTableWithKeyTableImpl nextTable;
        protected MixedIteratorLock lock;

        protected MixedKeyIterator(int key, TupleTableWithKeyTableImpl.RowEntry start, TupleTableWithKeyTableImpl thread, MixedIteratorLock iteratorLock) {
            super(key, start);
            nextTable = thread;
            lock = iteratorLock;
        }

        protected static TableIterator newInstance(int key, TupleTableWithKeyTableImpl shared, TupleTableWithKeyTableImpl thread, MixedIteratorLock iteratorLock) {
            assert(shared != null && thread != null && shared != thread);
            TupleTableWithKeyTableImpl.RowEntry start = findStart(key, shared);
            if(start == null) {
                iteratorLock.unlock();
                return TupleTableWithKeyTableImpl.KeyIterator.newInstance(key, thread);
            } else {
                return new MixedKeyIterator(key, start, thread, iteratorLock);
            }
        }

        public boolean hasNext() {
            boolean result = super.hasNext();
            if(!result && nextTable != null) {
                lock.unlock();
                m_entry = findStart(m_key, nextTable);
                nextTable = null;
                return super.hasNext();
            }
            return result;
        }
    }
}