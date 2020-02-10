package com.tibco.cep.kernel.core.base.tuple.tabletable.concurrentwm;

import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MigrationIterator;
import com.tibco.cep.kernel.core.base.tuple.tabletable.MixedIteratorLock;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableWithKeyTable;
import com.tibco.cep.kernel.core.base.tuple.tabletable.ObjectTableWithKeyTableImpl;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 25, 2009
 * Time: 12:56:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTableWithKeyTableImpl_Mixed extends BaseObjectTableTable_Mixed<ObjectTableWithKeyTableImpl> implements ObjectTableWithKeyTable
{
    public ObjectTableWithKeyTableImpl_Mixed() {
        super();
    }
    
    protected ObjectTableWithKeyTableImpl newSimpleTable() {
        return new ObjectTableWithKeyTableImpl();
    }

    public boolean add(Handle objHandle, int key) {
        if(isThreadLocal(objHandle)) {
            return getOrCreateThreadTable().add(objHandle, key);
        } else {
            try {
                lock();
                return getOrCreateSharedTable().add(objHandle, key);
            } finally {
                unlock();
            }
        }
    }

    public Handle[] getAllHandles(int key) {
        Handle[] ret = NULL_ARR;
        int ii = 0;
        try {
            lock();
            if (sharedTable != null && sharedTable.size() > 0) {
                ret = new Handle[64];
                for(TableIterator it = sharedTable.keyIterator(key); it.hasNext(); ii++) {
                    if(ii == ret.length) { //resize
                        Handle[] newRet = new Handle[ret.length * 2];
                        System.arraycopy(ret, 0, newRet, 0, ret.length);
                        ret = newRet;
                    }
                    ret[ii] = it.next()[0];
                }
            }
        } finally {
            unlock();
        }
        
        ObjectTableWithKeyTable threadTable = getThreadTable();
        if (threadTable != null && threadTable.size() > 0) {
            if(ret == NULL_ARR) ret = new Handle[64];
            for(TableIterator it = threadTable.keyIterator(key); it.hasNext(); ii++) {
                if(ii == ret.length) { //resize
                    Handle[] newRet = new Handle[ret.length * 2];
                    System.arraycopy(ret, 0, newRet, 0, ret.length);
                    ret = newRet;
                }
                ret[ii] = it.next()[0];
            }
        }
        
        //make sure it ends with a null value
        if(ii < ret.length) ret[ii] = null;
        return ret;
    }

    //locked by caller
    //TODO search for key matches here and if none exist then return NULL_ITER
    public TableIterator keyIterator(int key) {
        ObjectTableWithKeyTableImpl threadTable = threadTables.get();
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

    public MigrationIterator<KeyTuple> migrationIterator() {
        //cache only tables should be empty during hot deploy
        return sharedTable.migrationIterator();
    }

    protected static class MixedKeyIterator extends ObjectTableWithKeyTableImpl.ObjectKeyEntryIterator
    {
        protected ObjectTableWithKeyTableImpl nextTable;
        protected MixedIteratorLock lock;

        private MixedKeyIterator(int key, ObjectTableWithKeyTableImpl.ObjectKeyEntry startCursor, ObjectTableWithKeyTableImpl thread, MixedIteratorLock iteratorLock) {
            super(key, startCursor);
            nextTable = thread;
            lock = iteratorLock;
        }

//        protected MixedKeyIterator(int key, ObjectTableWithKeyTableImpl shared, ObjectTableWithKeyTableImpl thread, MixedIteratorLock iteratorLock) {
//            super(key, shared);
//            assert(shared != null && thread != null && shared != thread);
//            nextTable = thread;
//            lock = iteratorLock;
//            //in case first table is has no matches, it will move to the second table
//            hasNext();
//        }
        
        protected static TableIterator newInstance(int key, ObjectTableWithKeyTableImpl shared, ObjectTableWithKeyTableImpl thread, MixedIteratorLock iteratorLock) {
            assert(shared != null && thread != null && shared != thread);
            ObjectTableWithKeyTableImpl.ObjectKeyEntry cursor = findStart(key, shared);
            if(cursor == null) {
                iteratorLock.unlock();
                return ObjectTableWithKeyTableImpl.ObjectKeyEntryIterator.newInstance(key, thread);
            } else {
                return new MixedKeyIterator(key, cursor, thread, iteratorLock);
            }
        }

        public boolean hasNext() {
            boolean result = super.hasNext();
            if(!result && nextTable != null) {
                lock.unlock();
                m_cursor = findStart(m_key, nextTable);
                nextTable = null;
                return super.hasNext();
            }
            return result;
        }
    }
}