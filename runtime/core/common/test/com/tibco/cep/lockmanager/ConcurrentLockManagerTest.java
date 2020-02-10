package com.tibco.cep.lockmanager;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.impl.locks.ConcurrentLockManager;
import com.tibco.cep.runtime.session.impl.locks.CoherenceLockKeeper;

import java.io.FileNotFoundException;

/*
* Author: Ashwin Jayaprakash Date: Jul 10, 2008 Time: 6:28:59 PM
*/
public class ConcurrentLockManagerTest extends AbstractClusterLockManagerTest {
    protected String cacheName;

    public static void main(String[] args) throws FileNotFoundException {
        String name = "dist-unlimited-nobs-ticket-booth";

        if (args.length > 1) {
            name = args[1];
        }

        System.err.println("Using Cache: " + name);

        new ConcurrentLockManagerTest(name).runTests(args);
    }

    public ConcurrentLockManagerTest(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    protected NamedCache createLockCache() {
        return CacheFactory.getCache(cacheName);
    }

    @Override
    protected void discardLockCache(NamedCache namedCache) {
        namedCache.clear();
        namedCache.destroy();
    }

    @Override
    protected LockManager createLockManager(NamedCache lockCache) {
        return new ConcurrentLockManager(new CoherenceLockKeeper(lockCache));
    }

    @Override
    protected void discardLockManager(LockManager lockManager) {
    }
}
