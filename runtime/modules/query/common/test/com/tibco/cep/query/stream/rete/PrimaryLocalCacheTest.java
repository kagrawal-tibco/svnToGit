package com.tibco.cep.query.stream.rete;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.cache.PrimaryLocalCache;
import com.tibco.cep.query.stream.monitor.ResourceId;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.ref.Reference;

/*
* Author: Ashwin Jayaprakash Date: May 9, 2008 Time: 7:09:59 PM
*/
public class PrimaryLocalCacheTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        final int maxItems = 25;
        final long maxMillis = 5 * 60 * 1000;

        PrimaryLocalCache localCache =
                new PrimaryLocalCache(new ResourceId("Test"), "LocalCache", maxItems, maxMillis);

        try {
            localCache.start();
            try {
                for (int i = 0; i < maxItems; i++) {
                    localCache.put(i, i);
                }

                //----------

                long oldHits = localCache.getCacheStatistics().getCacheHits();
                localCache.get(0);
                long newHits = localCache.getCacheStatistics().getCacheHits();
                Reference<Cache.InternalEntry> ref = localCache.getInternalEntry(0);
                Cache.InternalEntry entry = ref.get();
                entry.getValue();
                long evenNewerHits = localCache.getCacheStatistics().getCacheHits();

                System.err.println(oldHits + ":" + newHits + ":" + evenNewerHits);

                Assert.assertEquals(oldHits, 0, "Old hits is wrong.");
                Assert.assertEquals(newHits, 1, "New hits is wrong.");
                Assert.assertEquals(evenNewerHits, 2, "Newest hits is wrong.");

                Object val = entry.getValue();
                long latestHits = localCache.getCacheStatistics().getCacheHits();
                System.err.println(val + "::" + latestHits);
                //This is one drawback of using the entry directly. The map stats don't get updated.
                Assert.assertEquals(latestHits, 2, "Latest hits changed.");

                localCache.put(100, 100);
                Assert.assertFalse(entry.isValid(), "Entry should've been invalidated.");
            }
            finally {
                localCache.stop();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
