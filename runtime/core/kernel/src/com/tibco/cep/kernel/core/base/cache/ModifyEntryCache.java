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
public class ModifyEntryCache {
    final static int MAX_CACHE_SIZE =  Integer.parseInt(System.getProperty("com.tibco.cep.kernel.opList.cacheSize", "50"));
    ModifyEntryImpl head;
    int size;
    int created;

    public ModifyEntryCache() {
        head = new ModifyEntryImpl(null);
        size = 0;
        created = 0;
    }

    public OperationList.ModifyEntry get(BaseHandle handle) {
        if(size == 0) {
            if(created < MAX_CACHE_SIZE) {
                created++;
                return new ModifyEntryImpl(handle);
            }
            else
                return new OperationList.ModifyEntry(handle);
        }
        ModifyEntryImpl ret = head.cacheNext;
        head.cacheNext = head.cacheNext.cacheNext;
        ret.reset(handle);
        size--;
        return ret;
    }

    public void put(ModifyEntryImpl e) {
        e.cacheNext = head.cacheNext;
        head.cacheNext = e;
        size++;
    }

    class ModifyEntryImpl extends OperationList.ModifyEntry {
        ModifyEntryImpl cacheNext;
        ModifyEntryImpl(BaseHandle _handle) {
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

