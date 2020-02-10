package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashSet;

/*
* Author: Ashwin Jayaprakash Date: Apr 11, 2008 Time: 11:03:39 AM
*/
public class FixedKeyHashMapTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() throws ClassNotFoundException, IOException {
        final HashSet<String> refKeys = new HashSet<String>();
        for (int i = 0; i < 1000; i++) {
            refKeys.add("" + i);
        }

        FixedKeyHashMap<String, Integer> map = new FixedKeyHashMap<String, Integer>(refKeys);
        FixedKeyHashMap<String, Integer> copy = new FixedKeyHashMap<String, Integer>(refKeys);
        runTests(refKeys, map);

        FixedKeyHashMap<String, Integer> map2 = new FixedKeyHashMap<String, Integer>(copy);
        runTests(refKeys, map2);

        FixedKeyHashMap<String, Integer> map3 =
                new FixedKeyHashMap<String, Integer>("555");
        HashSet<String> names = new HashSet<String>();
        names.add("555");
        runTests(names, map3);

        //------------

        FixedKeyHashMap<String, Integer> serMain = new FixedKeyHashMap<String, Integer>(refKeys);
        for (String s : serMain.keySet()) {
            serMain.put(s, Integer.parseInt(s));
        }
        FixedKeyHashMap<String, Integer> map4 = serDeserTest(serMain);
        Assert.assertNotSame(map4, map3, "Serialization returned the same object!");
        runTests(refKeys, map4);

        //Just keys. No values. 
        map4 = new FixedKeyHashMap<String, Integer>(refKeys);
        FixedKeyHashMap<String, Integer> map5 = serDeserTest(map4);
        Assert.assertNotSame(map5, map4, "Serialization returned the same object!");
    }

    private void runTests(HashSet<String> refKeys, FixedKeyHashMap<String, Integer> map) {
        Assert.assertEquals(map.size(), refKeys.size(), "Wrong size.");
        Assert.assertFalse(map.isEmpty(), "Map should not've been empty.");

        for (String refKey : refKeys) {
            Assert.assertTrue(map.containsKey(refKey), "Key should've been present.");
        }

        for (int i = 5000; i < 6000; i++) {
            Assert.assertFalse(map.containsKey(i + ":junk"), "Key should not've been present.");
        }

        //------------

        for (String s : map.keySet()) {
            Assert.assertTrue(refKeys.contains(s), "Unexpected key in keyset.");

            map.put(s, Integer.parseInt(s));
        }

        Assert.assertEquals(map.size(), refKeys.size(), "Wrong size.");
        Assert.assertFalse(map.isEmpty(), "Map should not've been empty.");

        for (String s : map.keySet()) {
            Integer i = map.get(s);

            Assert.assertEquals(i.toString(), s, "Mapping was incorrect.");
        }

        for (String refKey : refKeys) {
            Integer i = map.remove(refKey);

            Assert.assertEquals(i.toString(), refKey, "Unexpected return value.");
        }

        Assert.assertEquals(map.size(), refKeys.size(), "Wrong size.");
        Assert.assertFalse(map.isEmpty(), "Map should not've been empty.");

        //------------

        for (String refKey : refKeys) {
            map.put(refKey, 777);
        }

        System.err.println("Map: " + map.toString());

        for (String s : map.keySet()) {
            Integer i = map.get(s);

            Assert.assertEquals(i.intValue(), 777, "Mapping was incorrect.");
        }

        map.clear();

        Assert.assertEquals(map.size(), refKeys.size(), "Wrong size.");
        Assert.assertFalse(map.isEmpty(), "Map should not've been empty.");

        for (String s : map.keySet()) {
            Integer i = map.get(s);

            Assert.assertNull(i, "Value should've been cleared.");
        }

        System.err.println("Capacity: " + map.getCapacity());

        map.discard();

        Assert.assertEquals(map.size(), 0, "Wrong size.");
        Assert.assertTrue(map.isEmpty(), "Map should've been empty.");
    }

    private FixedKeyHashMap<String, Integer> serDeserTest(FixedKeyHashMap<String, Integer> map)
            throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bos);
        oo.writeObject(map);
        oo.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bis);
        FixedKeyHashMap<String, Integer> retVal =
                (FixedKeyHashMap<String, Integer>) oi.readObject();
        oi.close();

        return retVal;
    }
}
