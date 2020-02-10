package com.tibco.cep.runtime.session.locks;

import com.tibco.cep.runtime.session.impl.locks.AbstractConcurrentLockManager;

/*
* Author: Ashwin Jayaprakash Date: Sep 2, 2010 Time: 2:42:12 PM
*/
public class SimpleConcurrentLocalLockManager extends AbstractConcurrentLockManager<Long, Object> {
    public SimpleConcurrentLocalLockManager() {
        super(false, new DummyLockKeeper<Long, Object>());
    }

    @Override
    protected InvalidatableLock handleAbsentLock(Long key) {
        //Create a dummy lock.
        InvalidatableLock lock = new InvalidatableLock(LockLevel.LEVEL1);

        //Pretend that it is locked.
        try {
            lock.acquire();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return lock;
    }

    @Override
    protected LockData<Long, Object> handleAbsentLockData(Long key,
                                                          TxnDataHeldByThread<Long, Object> txn) {
        //Create a dummy instance.
        return new LockDataImpl<Long, Object>(key, null);
    }
}
