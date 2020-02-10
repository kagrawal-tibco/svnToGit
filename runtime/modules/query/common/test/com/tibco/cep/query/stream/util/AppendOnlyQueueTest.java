package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class AppendOnlyQueueTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        AppendOnlyQueue<Integer> queue = new AppendOnlyQueue<Integer>();

        Assert.assertEquals(queue.size(), 0, "Queue was supposed to be empty.");
        Assert.assertTrue(queue.isEmpty(), "Queue was supposed to be empty.");

        // ---------

        final int items1 = 10000;
        for (int i = 0; i < items1; i++) {
            queue.add(i);

            Assert.assertEquals(queue.size(), i + 1, "Queue size is wrong.");
        }
        Assert.assertEquals(queue.size(), items1, "Queue was not supposed to be empty.");
        Assert.assertFalse(queue.isEmpty(), "Queue was not supposed to be empty.");

        // ---------

        int x = 0;
        for (Integer integer : queue) {
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items1, "Queue Iterator did not retrieve all elements.");

        // ---------

        final int items2 = items1 * 2;
        for (int i = items1; i < items2; i++) {
            queue.add(i);

            Assert.assertEquals(queue.size(), i + 1, "Queue size is wrong.");
        }
        Assert.assertEquals(queue.size(), items2, "Queue was not supposed to be empty.");
        Assert.assertFalse(queue.isEmpty(), "Queue was not supposed to be empty.");

        // ---------

        x = 0;
        for (Integer integer : queue) {
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Queue Iterator did not retrieve all elements.");

        // ---------

        x = 0;
        ReusableIterator<Integer> iter = queue.iterator();
        for (; iter.hasNext();) {
            Integer integer = iter.next();
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Queue Iterator did not retrieve all elements.");

        x = 0;
        iter.reset();
        for (; iter.hasNext();) {
            Integer integer = iter.next();
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Queue Iterator did not retrieve all elements.");

        // ---------

        Object[] array = queue.toArray();
        x = 0;
        for (Object item : array) {
            Assert.assertEquals(((Integer) item).intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Queue Iterator did not retrieve all elements.");

        Integer[] ints = queue.toArray(new Integer[0]);
        x = 0;
        for (Integer integer : ints) {
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Queue Iterator did not retrieve all elements.");

        // ---------

        queue.clear();

        Assert.assertEquals(queue.size(), 0, "Queue was supposed to be empty.");
        Assert.assertTrue(queue.isEmpty(), "Queue was supposed to be empty.");

        x = 0;
        iter = queue.iterator();
        for (; iter.hasNext();) {
            Integer integer = iter.next();
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, 0, "Queue Iterator was supposed to be empty.");

        for (int i = 0; i < items1; i++) {
            queue.add(i);

            Assert.assertEquals(queue.size(), i + 1, "Queue size is wrong.");
        }
        Assert.assertEquals(queue.size(), items1, "Queue was not supposed to be empty.");
        Assert.assertFalse(queue.isEmpty(), "Queue was not supposed to be empty.");

        x = 0;
        for (Integer integer : queue) {
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items1, "Queue Iterator did not retrieve all elements.");

        // ---------

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = items1; i < items2; i++) {
            arrayList.add(i);
        }

        queue.addAll(arrayList);

        Assert.assertEquals(queue.size(), items2, "Queue was not supposed to be empty.");
        Assert.assertFalse(queue.isEmpty(), "Queue was not supposed to be empty.");

        x = 0;
        for (Integer integer : queue) {
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Queue Iterator did not retrieve all elements.");

        Assert.assertTrue(queue.contains(arrayList.get(0)),
                "Item should've been present in the Queue.");

        Assert.assertTrue(queue.containsAll(arrayList),
                "Items should've been present in the Queue.");

        // ---------

        AppendOnlyQueue<Integer> newQueue = new AppendOnlyQueue<Integer>();

        Assert.assertEquals(newQueue.size(), 0, "Queue was supposed to be empty.");
        Assert.assertTrue(newQueue.isEmpty(), "Queue was supposed to be empty.");

        newQueue.addAll(queue);

        Assert.assertEquals(newQueue.size(), items2, "Queue was not supposed to be empty.");
        Assert.assertFalse(newQueue.isEmpty(), "Queue was not supposed to be empty.");

        x = 0;
        for (Integer integer : newQueue) {
            Assert.assertEquals(integer.intValue(), x, "Queued element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Queue Iterator did not retrieve all elements.");
    }
}
