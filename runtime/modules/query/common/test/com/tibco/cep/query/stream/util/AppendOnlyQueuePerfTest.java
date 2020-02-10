package com.tibco.cep.query.stream.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class AppendOnlyQueuePerfTest extends AbstractTest {
    @Test(groups = { TestGroupNames.RUNTIME })
    public void test() {
        AppendOnlyQueue<Integer> queue = new AppendOnlyQueue<Integer>();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        final int num = 10000;

        addItems(queue, num);
        addItems(arrayList, num);
        addItems(linkedList, num);

        Assert.assertEquals(queue.size(), num, "Queue was not supposed to be empty.");
        Assert.assertFalse(queue.isEmpty(), "Queue was not supposed to be empty.");

        long queueTimes = 0;
        long arrayListTimes = 0;
        long linkedListTimes = 0;
        final int loops = 1000;
        for (int i = 0, k = 0; i < loops; i++, k = k % 3) {
            queue = new AppendOnlyQueue<Integer>();
            arrayList = new ArrayList<Integer>();
            linkedList = new LinkedList<Integer>();

            if (k == 0) {
                long start = System.nanoTime();
                addItems(arrayList, num);
                arrayListTimes += (System.nanoTime() - start);

                start = System.nanoTime();
                addItems(linkedList, num);
                linkedListTimes += (System.nanoTime() - start);

                start = System.nanoTime();
                addItems(queue, num);
                queueTimes += (System.nanoTime() - start);
            }
            else if (k == 1) {
                long start = System.nanoTime();
                addItems(linkedList, num);
                linkedListTimes += (System.nanoTime() - start);

                start = System.nanoTime();
                addItems(queue, num);
                queueTimes += (System.nanoTime() - start);

                start = System.nanoTime();
                addItems(arrayList, num);
                arrayListTimes += (System.nanoTime() - start);
            }
            else {
                long start = System.nanoTime();
                addItems(queue, num);
                queueTimes += (System.nanoTime() - start);

                start = System.nanoTime();
                addItems(arrayList, num);
                arrayListTimes += (System.nanoTime() - start);

                start = System.nanoTime();
                addItems(linkedList, num);
                linkedListTimes += (System.nanoTime() - start);
            }

            if (i > 0 && i % 100 == 0) {
                System.gc();
            }
        }

        System.out.println("Average over: " + loops + " tests.");

        long d = (long) (queueTimes / loops);
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.out.println(queue.getClass().getSimpleName() + ": add(..) time for: " + num
                + " items in microseconds: " + d);

        d = (long) (arrayListTimes / loops);
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.out.println(arrayList.getClass().getSimpleName() + ": add(..) time for: " + num
                + " items in microseconds: " + d);

        d = (long) (linkedListTimes / loops);
        d = TimeUnit.MICROSECONDS.convert(d, TimeUnit.NANOSECONDS);
        System.out.println(linkedList.getClass().getSimpleName() + ": add(..) time for: " + num
                + " items in microseconds: " + d);
    }

    protected void addItems(List<Integer> list, final int num) {
        for (int i = 0; i < num; i++) {
            list.add(i);
        }
    }

    protected void addItems(AppendOnlyQueue<Integer> queue, final int num) {
        for (int i = 0; i < num; i++) {
            queue.add(i);
        }
    }
}
