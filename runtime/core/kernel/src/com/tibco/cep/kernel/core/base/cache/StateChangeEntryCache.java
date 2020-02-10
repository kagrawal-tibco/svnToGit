package com.tibco.cep.kernel.core.base.cache;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.OperationList;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 31, 2009
 * Time: 5:01:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateChangeEntryCache {
    final static int MAX_CACHE_SIZE =  Integer.parseInt(System.getProperty("com.tibco.cep.kernel.opList.cacheSize", "50"));
    StateChangeEntryImpl head;
    int size;
    int created;

    public StateChangeEntryCache() {
        head = new StateChangeEntryImpl(null,0);
        size = 0;
        created = 0;
    }

    public OperationList.StateChangeEntry get(BaseHandle handle, int index) {
        if(size == 0) {
            if(created < MAX_CACHE_SIZE) {
                created++;
                return new StateChangeEntryImpl(handle, index);
            }
            else {
                return new OperationList.StateChangeEntry(handle, index);
            }
        }
        StateChangeEntryImpl ret = head.cacheNext;
        head.cacheNext = head.cacheNext.cacheNext;
        ret.reset(handle, index);
        size--;
        return ret;
    }

    public void put(StateChangeEntryImpl e) {
        e.cacheNext = head.cacheNext;
        head.cacheNext = e;
        size++;
    }

    class StateChangeEntryImpl extends OperationList.StateChangeEntry{
        StateChangeEntryImpl cacheNext;
        StateChangeEntryImpl(BaseHandle _handle, int index) {
            super(_handle, index);
        }
        protected void reset(BaseHandle _handle, int index) {
            handle = _handle;
            this.index=index;
            next = null;
        }
        protected void recycle(){
            next = null;
            handle = null;
            index=0;
            put(this);
        }
    }
}


