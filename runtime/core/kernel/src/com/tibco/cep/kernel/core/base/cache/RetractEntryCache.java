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
public class RetractEntryCache {
    final static int MAX_CACHE_SIZE =  Integer.parseInt(System.getProperty("com.tibco.cep.kernel.opList.cacheSize", "50"));
    RetractEntryImpl head;
    int size;
    int created;

    public RetractEntryCache() {
        head = new RetractEntryImpl(null);
        size = 0;
        created = 0;
    }

    public OperationList.RetractEntry get(BaseHandle handle) {
        if(size == 0) {
            if(created < MAX_CACHE_SIZE) {
                created++;
                return new RetractEntryImpl(handle);
            }
            else
                return new OperationList.RetractEntry(handle);
        }
        RetractEntryImpl ret = head.cacheNext;
        head.cacheNext = head.cacheNext.cacheNext;
        ret.reset(handle);
        size--;
        return ret;
    }

    public void put(RetractEntryImpl e) {
        e.cacheNext = head.cacheNext;
        head.cacheNext = e;
        size++;
    }

    class RetractEntryImpl extends OperationList.RetractEntry {
        RetractEntryImpl cacheNext;
        RetractEntryImpl(BaseHandle _handle) {
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

