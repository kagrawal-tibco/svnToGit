package com.tibco.cep.kernel.core.base.cache;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.OperationList;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jul 27, 2006
 * Time: 5:07:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssertEntryCache {
    final static int MAX_CACHE_SIZE = Integer.parseInt(System.getProperty("com.tibco.cep.kernel.opList.cacheSize", "50"));
    AssertEntryImpl head;
    int size;
    int created;

    public AssertEntryCache() {
        head = new AssertEntryImpl(null);
        size = 0;
        created = 0;
    }

    public OperationList.AssertEntry get(BaseHandle handle) {
        if(size == 0) {
            if(created < MAX_CACHE_SIZE) {
                created++;
                return new AssertEntryImpl(handle);
            }
            else
                return new OperationList.AssertEntry(handle);
        }
        AssertEntryImpl ret = head.cacheNext;
        head.cacheNext = head.cacheNext.cacheNext;
        ret.reset(handle);
        size--;
        return ret;
    }

    public void put(AssertEntryImpl e) {
        e.cacheNext = head.cacheNext;
        head.cacheNext = e;
        size++;
    }

    class AssertEntryImpl extends OperationList.AssertEntry {
        AssertEntryImpl cacheNext;
        AssertEntryImpl(BaseHandle _handle) {
            super(_handle);
        }
        protected void reset(BaseHandle _handle) {
            handle = _handle;
            next = null;
        }
        protected void recycle(){
            next = null;
            handle = null;
            put(this);
        }
    }
}

