package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class CustomHashSetTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        CustomHashSet<String> collidingHash = new CustomHashSet<String>();
        collidingHash.add("orderStream");
        collidingHash.add("orderFulfilmentStream");
        collidingHash.add("priorityCustomerStream");

        Assert.assertEquals(collidingHash.size(), 3);

        Assert.assertTrue(collidingHash.contains("orderStream"), "Key should've been present.");
        Assert.assertTrue(collidingHash.contains("orderFulfilmentStream"),
                "Key should've been present.");
        Assert.assertTrue(collidingHash.contains("priorityCustomerStream"),
                "Key should've been present.");

        // ---------

        CustomHashSet<Integer> set = new CustomHashSet<Integer>();

        Assert.assertEquals(set.size(), 0, "Set was supposed to be empty.");
        Assert.assertTrue(set.isEmpty(), "Set was supposed to be empty.");

        // ---------

        final int items1 = 10000;
        for (int i = 0; i < items1; i++) {
            set.add(i);

            Assert.assertEquals(set.size(), i + 1, "Set size is wrong.");
        }
        Assert.assertEquals(set.size(), items1, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        // ---------

        HashSet<Integer> refSet = new HashSet<Integer>();
        for (int i = 0; i < items1; i++) {
            refSet.add(i);
        }

        for (Integer integer : set) {
            Assert.assertTrue(refSet.remove(integer), "Set element was not correct: " + integer);
        }
        Assert.assertTrue(refSet.isEmpty(), "Set Iterator did not retrieve all elements.");

        // ---------

        final int items2 = items1 * 2;
        for (int i = items1; i < items2; i++) {
            set.add(i);

            Assert.assertEquals(set.size(), i + 1, "Set size is wrong.");
        }
        Assert.assertEquals(set.size(), items2, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        // ---------

        refSet = new HashSet<Integer>();
        for (int i = 0; i < items2; i++) {
            refSet.add(i);
        }

        for (Integer integer : set) {
            Assert.assertTrue(refSet.remove(integer), "Set element was not correct.");
        }
        Assert.assertTrue(refSet.isEmpty(), "Set Iterator did not retrieve all elements.");

        // ---------

        refSet = new HashSet<Integer>();
        for (int i = 0; i < items2; i++) {
            refSet.add(i);
        }

        CustomHashSet<Integer> iterSet = new CustomHashSet<Integer>();
        iterSet.addAll(set);

        ReusableIterator<Integer> iter = iterSet.iterator();
        for (; iter.hasNext();) {
            Integer integer = iter.next();
            Assert.assertTrue(refSet.remove(integer), "Set element was not correct.");
        }
        Assert.assertTrue(refSet.isEmpty(), "Set Iterator did not retrieve all elements.");

        refSet = new HashSet<Integer>();
        for (int i = 0; i < items2; i++) {
            refSet.add(i);
        }

        iter.reset();
        for (; iter.hasNext();) {
            Integer integer = iter.next();

            iter.remove();

            Assert.assertTrue(refSet.remove(integer), "Set element was not correct.");
        }
        Assert.assertTrue(refSet.isEmpty(), "Set Iterator did not retrieve all elements.");
        Assert.assertTrue(iterSet.isEmpty(), "Iterator.remove() did not remove all elements.");

        // ---------

        Object[] array = set.toArray();
        Arrays.sort(array, 0, array.length);
        int x = 0;
        for (Object item : array) {
            Assert.assertEquals(((Integer) item).intValue(), x, "Set element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Set Iterator did not retrieve all elements.");

        Integer[] ints = set.toArray(new Integer[0]);
        Arrays.sort(ints, 0, ints.length);
        x = 0;
        for (Integer integer : ints) {
            Assert.assertEquals(integer.intValue(), x, "Set element was not correct.");
            x++;
        }
        Assert.assertEquals(x, items2, "Set Iterator did not retrieve all elements.");

        // ---------

        set.clear();

        Assert.assertEquals(set.size(), 0, "Set was supposed to be empty.");
        Assert.assertTrue(set.isEmpty(), "Set was supposed to be empty.");

        x = 0;
        iter = set.iterator();
        for (; iter.hasNext();) {
            Integer integer = iter.next();
            Assert.assertEquals(integer.intValue(), x, "Set element was not correct.");
            x++;
        }
        Assert.assertEquals(x, 0, "Set Iterator was supposed to be empty.");

        for (int i = 0; i < items1; i++) {
            set.add(i);

            Assert.assertEquals(set.size(), i + 1, "Set size is wrong.");
        }
        Assert.assertEquals(set.size(), items1, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        refSet = new HashSet<Integer>();
        for (int i = 0; i < items1; i++) {
            refSet.add(i);
        }

        for (Integer integer : set) {
            Assert.assertTrue(refSet.remove(integer), "Set element was not correct.");
        }
        Assert.assertTrue(refSet.isEmpty(), "Set Iterator did not retrieve all elements.");

        // ---------

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = items1; i < items2; i++) {
            arrayList.add(i);
        }

        set.addAll(arrayList);

        Assert.assertEquals(set.size(), items2, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        refSet = new HashSet<Integer>();
        for (int i = 0; i < items2; i++) {
            refSet.add(i);
        }

        for (Integer integer : set) {
            Assert.assertTrue(refSet.remove(integer), "Set element was not correct.");
        }
        Assert.assertTrue(refSet.isEmpty(), "Set Iterator did not retrieve all elements.");

        // ---------

        CustomHashSet<Integer> newSet = new CustomHashSet<Integer>();

        Assert.assertEquals(newSet.size(), 0, "Set was supposed to be empty.");
        Assert.assertTrue(newSet.isEmpty(), "Set was supposed to be empty.");

        newSet.addAll(set);

        Assert.assertEquals(newSet.size(), items2, "Set was not supposed to be empty.");
        Assert.assertFalse(newSet.isEmpty(), "Set was not supposed to be empty.");

        refSet = new HashSet<Integer>();
        for (int i = 0; i < items2; i++) {
            refSet.add(i);
        }

        for (Integer integer : set) {
            Assert.assertTrue(refSet.remove(integer), "Set element was not correct.");
        }
        Assert.assertTrue(refSet.isEmpty(), "Set Iterator did not retrieve all elements.");

        // ---------

        set = new CustomHashSet<Integer>();
        HashMap<Integer, Integer> identitySet = new HashMap<Integer, Integer>();


        for (int i = 0; i < items1; i++) {
            Integer ident = i;

            set.add(ident);
            identitySet.put(ident, ident);

            Assert.assertEquals(set.size(), i + 1, "Set size is wrong.");
        }
        Assert.assertEquals(set.size(), items1, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        for (int i = 0; i < items1; i++) {
            boolean b = set.add(i);

            Assert.assertFalse(b, "Duplicate add should not've been allowed.");
            Assert.assertEquals(set.size(), items1, "Set size is wrong.");
        }

        for (int i = 0; i < items1; i++) {
            boolean b = set.contains(i);

            Integer identNew = set.getOriginalIfPresent(i);
            Integer identOld = identitySet.get(i);

            Assert.assertSame(identNew, identOld, "Instances should've been the same.");

            Assert.assertTrue(b, "Search should've returned true.");
        }

        for (int i = 0; i < items1; i++) {
            boolean b = set.remove(i);

            Assert.assertTrue(b, "Search should've removed the element.");
        }
        Assert.assertEquals(set.size(), 0, "Set was supposed to be empty.");
        Assert.assertTrue(set.isEmpty(), "Set was supposed to be empty.");

        // ---------

        set = new CustomHashSet<Integer>();

        for (int i = 0; i < items1; i++) {
            set.add(i);

            Assert.assertEquals(set.size(), i + 1, "Set size is wrong.");
        }
        Assert.assertEquals(set.size(), items1, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        final int items3 = items1 / 2;
        arrayList = new ArrayList<Integer>();
        for (int i = 0; i < items3; i++) {
            arrayList.add(i);
        }

        set.retainAll(arrayList);
        Assert.assertEquals(set.size(), items3, "Set size is wrong.");
        Assert.assertFalse(set.isEmpty(), "Set size is wrong.");

        // ---------

        set = new CustomHashSet<Integer>();

        for (int i = 0; i < items1; i++) {
            set.add(i);

            Assert.assertEquals(set.size(), i + 1, "Set size is wrong.");
        }
        Assert.assertEquals(set.size(), items1, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        arrayList = new ArrayList<Integer>();
        for (int i = 0; i < items3; i++) {
            arrayList.add(i);
        }

        set.removeAll(arrayList);
        Assert.assertEquals(set.size(), items1 - items3, "Set size is wrong.");
        Assert.assertFalse(set.isEmpty(), "Set size is wrong.");

        // ---------

        set = new CustomHashSet<Integer>();

        for (int i = 0; i < items1; i++) {
            set.add(i);

            Assert.assertEquals(set.size(), i + 1, "Set size is wrong.");
        }
        Assert.assertEquals(set.size(), items1, "Set was not supposed to be empty.");
        Assert.assertFalse(set.isEmpty(), "Set was not supposed to be empty.");

        arrayList = new ArrayList<Integer>();
        for (int i = 0; i < items3; i++) {
            arrayList.add(i);
        }

        Assert.assertTrue(set.containsAll(arrayList), "All items should've been present.");

        // ---------

        LinkedList<DummyKey> sameHashKeys = new LinkedList<DummyKey>();
        for (int i = 0; i < 128; i++) {
            sameHashKeys.add(new DummyKey(i, 2));
        }

        CustomHashSet<DummyKey> testSet = new CustomHashSet<DummyKey>();
        for (DummyKey key : sameHashKeys) {
            testSet.add(key);
        }
        Assert.assertEquals(testSet.size(), sameHashKeys.size(),
                "Set was not supposed to be empty.");
        Assert.assertFalse(testSet.isEmpty(), "Set was not supposed to be empty.");

        for (DummyKey key : sameHashKeys) {
            Assert.assertTrue(testSet.contains(key), "Key was supposed to be present: "
                    + key.getId());
        }

        Collections.reverse(sameHashKeys);
        for (DummyKey key : sameHashKeys) {
            Assert
                    .assertTrue(testSet.remove(key), "Key was supposed to be present: "
                            + key.getId());
        }
        Assert.assertEquals(testSet.size(), 0, "Set was supposed to be empty.");
        Assert.assertTrue(testSet.isEmpty(), "Set was supposed to be empty.");
    }

    public static class DummyKey {
        protected int id;

        protected int hashCode;

        public DummyKey(int id, int hashCode) {
            this.id = id;
            this.hashCode = hashCode;
        }

        public int getId() {
            return id;
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj == this);
        }
    }
}
