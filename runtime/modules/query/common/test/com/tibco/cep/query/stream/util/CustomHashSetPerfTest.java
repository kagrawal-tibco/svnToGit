package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class CustomHashSetPerfTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        // Warm up.
        System.err.println("Warming up..");
        testCustomHashSetAdd(250, 20000);
        testTreeSetAdd(250, 20000);
        testHashSetAdd(250, 20000);

        testTreeSetRemove(250, 20000);
        testHashSetRemove(250, 20000);
        testCustomHashSetRemove(250, 20000);

        System.err.println("-------------");
        performTests(1000, 20);
        System.err.println("-------------");
        performTests(1000, 200);
        System.err.println("-------------");
        performTests(1000, 100);
        System.err.println("-------------");
        performTests(1000, 2000);
        System.err.println("-------------");
        performTests(500, 10000);
        System.err.println("-------------");
        performTests(500, 20000);
        System.err.println("-------------");
        performTests(250, 200000);
    }

    protected void performTests(final int loops, final int items1) {
        System.err.println("Average over " + loops + " operations.");

        long d = testTreeSetAdd(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("TreeSet add() for " + items1 + " items in microseconds " + d);

        d = testHashSetAdd(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("HashSet add() for " + items1 + " items in microseconds " + d);

        d = testCustomHashSetAdd(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("CustomSet add() for " + items1 + " items in microseconds " + d);

        // ---------

        d = testTreeSetRemove(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("TreeSet remove() for " + items1 / 2 + " items in microseconds " + d);

        d = testCustomHashSetRemove(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("CustomSet remove() for " + items1 / 2 + " items in microseconds " + d);

        d = testHashSetRemove(loops, items1);
        d = d / loops;
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.err.println("HashSet remove() for " + items1 / 2 + " items in microseconds " + d);
    }

    protected long testHashSetAdd(final int loops, final int items1) {
        long hashSetTime = 0;

        for (int m = 0; m < loops; m++) {
            HashSet<Integer> set = new HashSet<Integer>();

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                set.add(i);
            }
            hashSetTime += (System.nanoTime() - start);

            set.clear();
        }
        System.gc();

        return hashSetTime;
    }

    protected long testTreeSetAdd(final int loops, final int items1) {
        long treeSetTime = 0;

        for (int m = 0; m < loops; m++) {
            TreeSet<Integer> set = new TreeSet<Integer>();

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                set.add(i);
            }
            treeSetTime += (System.nanoTime() - start);

            set.clear();
        }
        System.gc();

        return treeSetTime;
    }

    protected long testCustomHashSetAdd(final int loops, final int items1) {
        long setTime = 0;

        for (int m = 0; m < loops; m++) {
            CustomHashSet<Integer> set = new CustomHashSet<Integer>();

            Assert.assertEquals(set.size(), 0, "Set was supposed to be empty.");
            Assert.assertTrue(set.isEmpty(), "Set was supposed to be empty.");

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                set.add(i);
            }
            setTime += (System.nanoTime() - start);

            Assert.assertEquals(set.size(), items1, "Set was not supposed to be empty.");
            Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

            set.clear();
        }
        System.gc();

        return setTime;
    }

    protected long testTreeSetRemove(final int loops, final int items1) {
        long treeSetTime = 0;

        for (int m = 0; m < loops; m++) {
            TreeSet<Integer> set = new TreeSet<Integer>();

            for (int i = 0; i < items1; i++) {
                set.add(i);
            }

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                if ((i & 1) == 0) {
                    set.remove(i);
                }
            }
            treeSetTime += (System.nanoTime() - start);

            set.clear();
        }
        System.gc();

        return treeSetTime;
    }

    protected long testHashSetRemove(final int loops, final int items1) {
        long hashSetTime = 0;

        for (int m = 0; m < loops; m++) {
            HashSet<Integer> set = new HashSet<Integer>();

            for (int i = 0; i < items1; i++) {
                set.add(i);
            }

            long start = System.nanoTime();
            for (int i = 0; i < items1; i++) {
                if ((i & 1) == 0) {
                    set.remove(i);
                }
            }
            hashSetTime += (System.nanoTime() - start);

            set.clear();
        }
        System.gc();

        return hashSetTime;
    }

    protected long testCustomHashSetRemove(final int loops, final int items1) {
        long setTime = 0;

        for (int m = 0; m < loops; m++) {
            CustomHashSet<Integer> set = new CustomHashSet<Integer>();

            Assert.assertEquals(set.size(), 0, "Set was supposed to be empty.");
            Assert.assertTrue(set.isEmpty(), "Set was supposed to be empty.");

            for (int i = 0; i < items1; i++) {
                set.add(i);
            }

            long start = System.nanoTime();
            int c = 0;
            for (int i = 0; i < items1; i++) {
                if ((i & 1) == 0) {
                    set.remove(i);
                    c++;
                }
            }
            setTime += (System.nanoTime() - start);

            Assert.assertEquals(set.size(), items1 - c, "Set was not supposed to be empty.");
            Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

            set.clear();
        }
        System.gc();

        return setTime;
    }
}
