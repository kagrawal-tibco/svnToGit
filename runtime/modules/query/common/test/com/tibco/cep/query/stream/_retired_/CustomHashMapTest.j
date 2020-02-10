package com.tibco.cep.query.stream._retired_;

import java.util.HashSet;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream._retired_.CustomHashMap5;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class CustomHashMapTest extends AbstractTest {
    //todo Complete this.
    @Test(groups = { TestGroupNames.RUNTIME })
    public void test() {
        CustomHashMap5<Integer, String> map = new CustomHashMap5<Integer, String>();

        Assert.assertEquals(map.size(), 0, "Map was supposed to be empty.");
        Assert.assertTrue(map.isEmpty(), "Map was supposed to be empty.");

        // ---------

        final int items1 = 10000;
        for (int i = 0; i < items1; i++) {
            map.put(i, i + "");

            Assert.assertEquals(map.size(), i + 1, "Map size is wrong.");
        }
        Assert.assertEquals(map.size(), items1, "Map was not supposed to be empty.");
        Assert.assertFalse(map.isEmpty(), "Map was not supposed to be empty.");

        // ---------

        HashSet<Integer> refSet = new HashSet<Integer>();
        for (int i = 0; i < items1; i++) {
            refSet.add(i);
        }

        // for (Integer integer : map.keySet()) {
        // Assert.assertTrue(refSet.remove(integer), "Map element was not
        // correct: " + integer);
        // }
        // Assert.assertTrue(refSet.isEmpty(), "Map Iterator did not retrieve
        // all elements.");

        // ---------

        final int items2 = items1 * 2;
        for (int i = items1; i < items2; i++) {
            map.put(i, i + "");

            Assert.assertEquals(map.size(), i + 1, "Map size is wrong.");
        }
        Assert.assertEquals(map.size(), items2, "Map was not supposed to be empty.");
        Assert.assertFalse(map.isEmpty(), "Map was not supposed to be empty.");

        // ---------

        for (int i = 0; i < items2; i++) {
            Assert.assertTrue(map.containsKey(i), "Map should've contained: " + i);
        }

        // ---------

        int origSize = map.size();
        for (int i = items1; i < items2; i++) {
            String old = map.put(i, i + "--" + i);

            Assert.assertEquals(old, i + "", "Replaced value is incorrect.");
            Assert.assertEquals(map.size(), origSize, "Map size is wrong.");
        }
        Assert.assertEquals(map.size(), items2, "Map was not supposed to be empty.");
        Assert.assertFalse(map.isEmpty(), "Map was not supposed to be empty.");

        // ---------

        origSize = map.size();
        for (int i = 0; i < items2; i++) {
            // if ((i & 1) == 0) {
            // continue;
            // }
            //
            // String old = map.remove(i);
            // origSize--;
            //
            // Assert.assertNotNull(old, "Removed value is incorrect.");
            // Assert.assertEquals(map.size(), origSize, "Map size is wrong.");
        }

        // ---------

        for (int i = 0; i < items2; i++) {
            map.put(i, (items2 - i) + "");
        }
        Assert.assertEquals(map.size(), items2, "Map was not supposed to be empty.");
        Assert.assertFalse(map.isEmpty(), "Map was not supposed to be empty.");

        map.clear();
        Assert.assertTrue(map.isEmpty(), "Map should've been empty.");
        Assert.assertEquals(map.size(), 0, "Map should've been empty.");

        for (int i = 0; i < items2; i++) {
            map.put(i, (items2 - i) + "");

            Assert.assertEquals(map.size(), i + 1, "Map size is wrong.");
        }
        Assert.assertEquals(map.size(), items2, "Map was not supposed to be empty.");
        Assert.assertFalse(map.isEmpty(), "Map was not supposed to be empty.");

        // ---------

        for (int i = items2 - 1; i >= 0; i--) {
            String s = map.get(i);

            Assert.assertEquals(s, (items2 - i) + "", "Returned value is incorrect.");
        }
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
