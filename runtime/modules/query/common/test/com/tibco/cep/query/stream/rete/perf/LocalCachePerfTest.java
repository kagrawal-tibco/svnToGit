package com.tibco.cep.query.stream.rete.perf;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.cache.SharedPointer;
import com.tibco.cep.query.stream.cache.SharedPointerImpl;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.cache.PrimaryLocalCache;
import com.tibco.cep.query.stream.impl.cache.SimpleLocalCache;
import com.tibco.cep.query.stream.impl.rete.integ.AbstractSharedObjectSource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
* Author: Ashwin Jayaprakash Date: May 9, 2008 Time: 7:09:59 PM
*/
public class LocalCachePerfTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        final int maxItems = 500000;
        final long maxMillis = Integer.MAX_VALUE;
        final int loopCount = 500000;

        SimpleLocalCache sc =
                new SimpleLocalCache(new ResourceId("Cache"), "Simple", maxItems, maxMillis);
        PrimaryLocalCache pc =
                new PrimaryLocalCache(new ResourceId("Cache"), "Primary", maxItems, maxMillis);

        for (int i = 0; i < maxItems; i++) {
            Entity entity = new TestEntity(i);

            sc.put(i, entity);
            pc.put(i, entity);
        }

        //----------

        System.err.println("Warming up");

        Integer fixedKey = 14997;

        TestPointer simpleSharedPointer =
                new TestPointer(fixedKey, new TestSourceImpl(sc));

        SharedPointerImpl primarySharedPointer =
                new SharedPointerImpl(fixedKey, new TestSourceImpl(pc));

        runLoop(loopCount, simpleSharedPointer, primarySharedPointer);

        System.err.println("----------");

        //----------

        fixedKey = 23000;
        simpleSharedPointer = new TestPointer(fixedKey, new TestSourceImpl(sc));
        primarySharedPointer = new SharedPointerImpl(fixedKey, new TestSourceImpl(pc));
        runLoop(loopCount, simpleSharedPointer, primarySharedPointer);

        fixedKey = 49999;
        simpleSharedPointer = new TestPointer(fixedKey, new TestSourceImpl(sc));
        primarySharedPointer = new SharedPointerImpl(fixedKey, new TestSourceImpl(pc));
        runLoop(loopCount, simpleSharedPointer, primarySharedPointer);

        fixedKey = 1;
        simpleSharedPointer = new TestPointer(fixedKey, new TestSourceImpl(sc));
        primarySharedPointer = new SharedPointerImpl(fixedKey, new TestSourceImpl(pc));
        runLoop(loopCount, simpleSharedPointer, primarySharedPointer);
    }

    protected void runLoop(int loopCount, TestPointer testPointer,
                           SharedPointerImpl primarySharedPointer) {
        long start = System.currentTimeMillis();
        AppendOnlyQueue list = new AppendOnlyQueue();

        for (int i = 0; i < loopCount; i++) {
            Object o = testPointer.getObject();

            Entity e = (Entity) o;

            //Just to ensure HotSpot does not optimize this loop away.
            list.add(e);
        }

        long end = System.currentTimeMillis();
        System.err
                .println("Simple Pointer: " + loopCount + " iterations, Millis: " + (end - start));

        Assert.assertEquals(loopCount, list.size(), "HotSpot over-optimization!!");
        list.discard();

        System.gc();

        //----------

        start = System.currentTimeMillis();
        list = new AppendOnlyQueue();

        for (int i = 0; i < loopCount; i++) {
            Object o = primarySharedPointer.getObject();

            Entity e = (Entity) o;

            //Just to ensure HotSpot does not optimize this loop away.
            list.add(e);
        }

        end = System.currentTimeMillis();
        System.err.println("Smart Ref Pointer: " + loopCount + " iterations, Millis: " +
                (end - start));

        Assert.assertEquals(loopCount, list.size(), "HotSpot over-optimization!!");
        list.discard();

        System.gc();
    }

    //---------

    protected static class TestSourceImpl extends AbstractSharedObjectSource {
        protected TestSourceImpl(Cache cache) {
            super(null, "Cache:" + System.nanoTime(), cache, null, null);
        }

        public Entity handleDownloadedEntity(Object o, Long id) {
            return (Entity) o;
        }
    }

    protected static class TestPointer implements SharedPointer {
        protected Object key;

        protected SharedObjectSource source;

        public TestPointer(Object key, SharedObjectSource source) {
            this.key = key;
            this.source = source;
        }

        public SharedObjectSource getSource() {
            return source;
        }

        public Object getKey() {
            return key;
        }

        public Object getObject() {
            return source.fetch(key, false);
        }

        /**
         * Uses {@link Object#hashCode()}.
         */
        @Override
        public final int hashCode() {
            return super.hashCode();
        }

        /**
         * Uses {@link Object#equals(Object)}.
         */
        @Override
        public final boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public String toString() {
            return "Key: " + getKey() + ", Source: " + source;
        }
    }

    protected static class TestEntity implements Entity {
        protected final long id;

        public TestEntity(long id) {
            this.id = id;
        }

        public String getExtId() {
            return null;
        }

        public long getId() {
            return id;
        }

        public void start(Handle handle) {
        }

        public void setLoadedFromCache() {
        }

        public boolean isLoadedFromCache() {
            return false;
        }

        @Override
        public String getType() {
            //todo
            return null;
        }

        @Override
        public void setPropertyValue(String name, Object value) throws Exception {
            //todo

        }

        @Override
        public Object getPropertyValue(String name) throws NoSuchFieldException {
            //todo
            return null;
        }
    }
}