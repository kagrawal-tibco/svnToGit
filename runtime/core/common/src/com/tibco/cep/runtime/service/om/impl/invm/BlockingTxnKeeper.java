package com.tibco.cep.runtime.service.om.impl.invm;

import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.om.api.invm.InProgressTxnKeeper;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.locks.SimpleConcurrentLocalLockManager;

/*
* Author: Ashwin Jayaprakash Date: Nov 17, 2008 Time: 4:18:47 PM
*/

public class BlockingTxnKeeper implements InProgressTxnKeeper {
    protected SimpleConcurrentLocalLockManager lockManager;

    public BlockingTxnKeeper() {
    }

    public String getId() {
        return getClass().getSimpleName() + ":" + System.identityHashCode(this);
    }

    /**
     * @param configuration
     * @param otherArgs
     * @throws Exception
     */
    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        lockManager = new SimpleConcurrentLocalLockManager();
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        lockManager.discard();
        lockManager = null;
    }

    //-----------

    /**
     * Blocks until the lock can be acquired.
     *
     * @param id
     */
    public void acquire(Long id) {
        lockManager.lock(id, Integer.MAX_VALUE, LockManager.LockLevel.LEVEL1);
    }

    /**
     * @param id
     */
    public void release(Long id) {
        lockManager.unlock(id);
    }
}
