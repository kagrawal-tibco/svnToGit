package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class SingleElementCollectionTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        final int x = 999;

        SingleElementCollection<Integer> collection = new SingleElementCollection<Integer>(x);
        Assert.assertEquals(collection.size(), 1, "Collection was not supposed to be empty.");
        Assert.assertFalse(collection.isEmpty(), "Queue was not supposed to be empty.");
        Assert.assertTrue(collection.contains(x), "Number was supposed to be present.");

        Object[] array = collection.toArray();
        Assert.assertEquals(array.length, 1, "Array was not supposed to be empty.");
        Assert.assertEquals(array[0], x, "Array element does not match expected value.");

        array = collection.toArray(new Integer[0]);
        Assert.assertEquals(array.length, 1, "Array was not supposed to be empty.");
        Assert.assertEquals(array[0], x, "Array element does not match expected value.");

        collection.clear();
        Assert.assertEquals(collection.size(), 0, "Collection was supposed to be empty.");
        Assert.assertTrue(collection.isEmpty(), "Queue was not supposed to be empty.");
        Assert.assertFalse(collection.contains(x), "Number was not supposed to be present.");

        collection.setElement(x);
        ReusableIterator<Integer> iterator = collection.iterator();
        for (; iterator.hasNext();) {
            Integer integer = iterator.next();
            Assert.assertEquals(integer.intValue(), x, "Iterator result is wrong.");
        }

        iterator.reset();
        for (; iterator.hasNext();) {
            Integer integer = iterator.next();

            iterator.remove();

            Assert.assertEquals(integer.intValue(), x, "Iterator result is wrong.");
        }
        Assert.assertEquals(collection.size(), 0, "Collection was supposed to be empty.");
        Assert.assertTrue(collection.isEmpty(), "Queue was supposed to be empty.");
    }
}
