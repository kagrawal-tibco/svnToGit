package com.tibco.cep.kernel.core.base.cache;

import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.OperationList;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jul 27, 2006
 * Time: 5:07:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZeroTTLEventEntryCache {
    final static int MAX_CACHE_SIZE =  Integer.parseInt(System.getProperty("com.tibco.cep.kernel.opList.cacheSize", "50"));
    ZeroTTLEntryImpl head;
    int size;
    int created;

    public ZeroTTLEventEntryCache() {
        head = new ZeroTTLEntryImpl(null);
        size = 0;
        created = 0;
    }

    public OperationList.ZeroTTLEventEntry get(AbstractEventHandle handle) {
        if(size == 0) {
            if(created < MAX_CACHE_SIZE) {
                created++;
                return new ZeroTTLEntryImpl(handle);
            }
            else
                return new OperationList.ZeroTTLEventEntry(handle);
        }
        ZeroTTLEntryImpl ret = head.cacheNext;
        head.cacheNext = head.cacheNext.cacheNext;
        ret.reset(handle);
        size--;
        return ret;
    }

    public void put(ZeroTTLEntryImpl e) {
        e.cacheNext = head.cacheNext;
        head.cacheNext = e;
        size++;
    }

    class ZeroTTLEntryImpl extends OperationList.ZeroTTLEventEntry {
        ZeroTTLEntryImpl cacheNext;
        ZeroTTLEntryImpl(AbstractEventHandle _handle) {
            super( _handle);
        }
        protected void reset(AbstractEventHandle _handle) {
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

