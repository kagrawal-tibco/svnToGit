package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
* Author: Ashwin Jayaprakash Date: Apr 18, 2008 Time: 4:40:17 PM
*/
public class LazyContainerTreeMapTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        LazyContainerTreeMap.ValueCollectionCreator<String, CustomHashSet<String>> creator =
                new LazyContainerTreeMap.ValueCollectionCreator<String, CustomHashSet<String>>() {
                    public CustomHashSet<String> createContainer() {
                        return new CustomHashSet<String>();
                    }
                };

        LazyContainerTreeMap<Integer, String, CustomHashSet<String>> map =
                new LazyContainerTreeMap<Integer, String, CustomHashSet<String>>(creator);

        final int loopCount = 50;
        final int setSize = 10;

        for (int i = 0; i < loopCount; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < setSize; j++) {
                    map.put(i, j + "");
                }
            }
            else {
                map.put(i, i + "");
            }
        }

        for (Integer integer : map.keySet()) {
            Object obj = map.get(integer);

            if (integer % 2 == 0) {
                Assert.assertTrue(obj instanceof CustomHashSet,
                        "Was expecting a collection under this key.");

                CustomHashSet<String> values = (CustomHashSet<String>) obj;

                Assert.assertEquals(values.size(), setSize, "Wrong size of collection.");

                for (int j = 0; j < setSize; j++) {
                    boolean b = values.contains(j + "");

                    Assert.assertTrue(b, "Element: " + j + " was expected.");
                }
            }
            else {
                Assert.assertEquals(obj, integer + "", "Value stored under key is wrong.");
            }
        }


        for (int i = 0; i < loopCount; i = i + 2) {
            for (int j = 0; j < setSize; j++) {
                boolean b = map.removeValueForKey(i, j + "");

                Assert.assertTrue(b, "Removal should've succeeded.");

                CustomHashSet<String> set = (CustomHashSet<String>) map.get(i);

                if (j + 1 != setSize) {
                    Assert.assertEquals(set.size(), setSize - j - 1,
                            "Multi-valued element did not get cleared correctly.");
                }
                else {
                    Assert.assertNull(set,
                            "Entire empty collection should've been removed by now.");
                }
            }

            Assert.assertFalse(map.containsKey(i), "Values");
        }

        for (int i = 1; i < loopCount; i = i + 2) {
            boolean b = map.removeValueForKey(i, i + "");

            Assert.assertTrue(b, "Removal should've succeeded.");
        }

        Assert.assertEquals(map.size(), 0, " Map should've been empty.");
    }

    public static void main(String[] args) {
        new LazyContainerTreeMapTest().test();
    }
}
