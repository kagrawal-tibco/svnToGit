package com.tibco.be.functions.java.util;

/*
* Author: Ashwin Jayaprakash / Date: 5/26/11 / Time: 4:11 PM
*/

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.runtime.session.impl.locks.AbstractConcurrentLockManager;
import com.tibco.cep.runtime.session.locks.LockManager;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Util.Concurrent",
        synopsis = "Concurrency Functions",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.util.concurrent", value=false))
public class ConcurrentHelper {
    private static InternalLocalLockManager LOCK_MANAGER;

    private static synchronized InternalLocalLockManager getIlm() {
        if (LOCK_MANAGER == null) {
            LOCK_MANAGER = new InternalLocalLockManager();
        }

        return LOCK_MANAGER;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "acquireLock",
        synopsis = "Acquires a lock using the specified String as the lock key and the caller blocks until the lock is\nacquired. These locks are not related in any way to the Cluster.DataGrid.Lock()/Unlock() functionality. These\nlocks also do not get unlocked automatically at the end of the RTC and hence require an explicit unlock call.",
        signature = "void acquireLock(String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key which will serve as the lock ley.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Acquires a lock using the specified String as the lock key and the caller blocks until the lock is\nacquired. These locks are not related in any way to the Cluster.DataGrid.Lock()/Unlock() functionality. These\nlocks also do not get unlocked automatically at the end of the RTC and hence require an explicit unlock call.",
        cautions = "Can cause deadlocks if not used correctly.",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )
    public static void acquireLock(String key) {
        getIlm().lock(key, Integer.MAX_VALUE, LockManager.LockLevel.LEVEL1);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "releaseLock",
        synopsis = "Releases the lock using the specified String as the lock key.",
        signature = "void releaseLock(String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The String key which will serve as the unlock ley.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Releases the lock using the specified String as the lock key.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )
    public static void releaseLock(String key) {
        getIlm().unlock(key);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "releaseAllOwnedLocks",
        synopsis = "Releases all locks that were acquired by this thread.",
        signature = "void releaseAllOwnedLocks()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "4.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Releases all locks that were acquired by this thread.",
        cautions = "none",
        fndomain = {ACTION, BUI},
        		reevaluate = true,
        example = ""
    )
    public static void releaseAllOwnedLocks() {
        getIlm().unlockAllLocksHeldByThread();
    }

    static class InternalLocalLockManager extends AbstractConcurrentLockManager<String, Object> {
        public InternalLocalLockManager() {
            super(false, new DummyLockKeeper<String, Object>());
        }

        @Override
        protected InvalidatableLock handleAbsentLock(String key) {
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
        protected LockData<String, Object> handleAbsentLockData(String key, TxnDataHeldByThread<String, Object> txn) {
            //Create a dummy instance.
            return new LockDataImpl<String, Object>(key, null);
        }
    }
}
