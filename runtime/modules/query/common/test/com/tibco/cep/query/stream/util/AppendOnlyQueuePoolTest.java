package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class AppendOnlyQueuePoolTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        // Warm up.
        System.err.println("Warming up..");
        testPooledQueue(250, 200000);
        testUnPooledQueue(250, 200000);
        testArrayList(250, 200000);
        testLinkedList(250, 200000);

        System.err.println("-------------");
        performTests(1000, 20);
        System.err.println("-------------");
        performTests(1000, 200);
        System.err.println("-------------");
        performTests(1000, 2000);
        System.err.println("-------------");
        performTests(500, 20000);
        System.err.println("-------------");
        performTests(250, 200000);
    }

    protected void performTests(final int loops, final int items1) {
        System.err.println("Average over " + loops + " operations.");

        long d = testArrayList(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("ArrayList for " + items1 + " items in microseconds " + d);

        d = testPooledQueue(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("Pooled-Queue for " + items1 + " items in microseconds " + d);

        d = testLinkedList(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("LinkedList for " + items1 + " items in microseconds " + d);

        d = testUnPooledQueue(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("UnPooled-Queue for " + items1 + " items in microseconds " + d);
    }

    protected long testLinkedList(final int loops, final int items1) {
        long linkedListTime = 0;

        for (int m = 0; m < loops; m++) {
            LinkedList<Integer> list = new LinkedList<Integer>();

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                list.add(i);
            }
            linkedListTime += (System.nanoTime() - start);

            list.clear();
        }
        System.gc();

        return linkedListTime;
    }

    protected long testArrayList(final int loops, final int items1) {
        long arrayListTime = 0;

        for (int m = 0; m < loops; m++) {
            ArrayList<Integer> list = new ArrayList<Integer>();

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                list.add(i);
            }
            arrayListTime += (System.nanoTime() - start);

            list.clear();
        }
        System.gc();

        return arrayListTime;
    }

    protected long testPooledQueue(final int loops, final int items1) {
        long queueTime = 0;

        ArrayPool pool = null;

        int fragmentStartSize = ArrayPool.DEFAULT_FRAGMENT_START_SIZE;
        if (items1 > 10000) {
            // One size for all.
            fragmentStartSize = AppendOnlyQueue.MAX_FRAGMENT_SIZE;

            pool = new ArrayPool(1, fragmentStartSize, new ArrayPool.DefaultCustomizer(100));
        }
        else {
            pool = new ArrayPool();
        }

        for (int m = 0; m < loops; m++) {
            AppendOnlyQueue<Integer> queue = new AppendOnlyQueue<Integer>(fragmentStartSize, pool);

            Assert.assertEquals(queue.size(), 0, "Queue was supposed to be empty.");
            Assert.assertTrue(queue.isEmpty(), "Queue was supposed to be empty.");

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                queue.add(i);
            }
            queueTime += (System.nanoTime() - start);

            Assert.assertEquals(queue.size(), items1, "Queue was not supposed to be empty.");
            Assert.assertFalse(queue.isEmpty(), "Queue was not supposed to be empty.");

            queue.clear();
        }
        pool.discard();
        System.gc();

        return queueTime;
    }

    protected long testUnPooledQueue(final int loops, final int items1) {
        long queueTime = 0;

        for (int m = 0; m < loops; m++) {
            AppendOnlyQueue<Integer> queue = new AppendOnlyQueue<Integer>();

            Assert.assertEquals(queue.size(), 0, "Queue was supposed to be empty.");
            Assert.assertTrue(queue.isEmpty(), "Queue was supposed to be empty.");

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                queue.add(i);
            }
            queueTime += (System.nanoTime() - start);

            Assert.assertEquals(queue.size(), items1, "Queue was not supposed to be empty.");
            Assert.assertFalse(queue.isEmpty(), "Queue was not supposed to be empty.");

            queue.clear();
        }
        System.gc();

        return queueTime;
    }
}
