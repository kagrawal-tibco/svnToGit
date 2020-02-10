package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 4:39:57 PM
 */

public class ArrayPoolTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        int classes = ArrayPool.DEFAULT_NUM_CLASSES;
        int fragmentStartSizeLogBase2 = Helper.floorLogBase2(ArrayPool.DEFAULT_FRAGMENT_START_SIZE);
        int maxPoolFactor = ArrayPool.DefaultCustomizer.DEFAULT_MAX_ITEMS_PER_CLASS_FACTOR;

        ArrayPool pool = new ArrayPool();

        Map<Integer, Set<Object[]>> pooledArrays = new HashMap<Integer, Set<Object[]>>();
        for (int i = 0; i < classes; i++) {
            pooledArrays.put(i + 1, new HashSet<Object[]>());
        }
        Set<Object[]> unPooledArrays = new HashSet<Object[]>();

        // ----------

        for (int i = 0; i < classes; i++) {
            int arraySize = 1 << (fragmentStartSizeLogBase2 + i);
            int maxItemsInClass = (classes - i) * maxPoolFactor;

            Set<Object[]> storage = pooledArrays.get(i + 1);

            for (int m = 0; m < maxItemsInClass; m++) {
                Object[] array = pool.createArray(arraySize);

                Assert.assertEquals(array.length, arraySize,
                        "Array size created by the pool does not match the request.");

                storage.add(array);
            }

            // Add one more that crosses the max pool limit.
            Object[] array = pool.createArray(arraySize);
            unPooledArrays.add(array);
        }

        // ----------

        for (int i = 0; i < classes; i++) {
            Set<Object[]> list = pooledArrays.get(i + 1);
            for (Object[] objects : list) {
                pool.returnArray(objects, true);
            }
        }

        // These won't get pooled.
        for (Object[] objects : unPooledArrays) {
            pool.returnArray(objects, true);
        }

        // ----------

        int unPooledArrayNum = unPooledArrays.size();

        for (int i = 0; i < classes; i++) {
            int arraySize = 1 << (fragmentStartSizeLogBase2 + i);
            int maxItemsInClass = (classes - i) * maxPoolFactor;

            Set<Object[]> storage = pooledArrays.get(i + 1);

            for (int m = 0; m < maxItemsInClass; m++) {
                Object[] array = pool.createArray(arraySize);

                Assert.assertTrue(storage.remove(array), "Array should've been pooled. Class: "
                        + (i + 1));

                Assert.assertEquals(array.length, arraySize,
                        "Array size created by the pool does not match the request.");
            }

            Object[] array = pool.createArray(arraySize);
            Assert.assertFalse(unPooledArrays.remove(array),
                    "Array should not've been pooled. Class: " + (i + 1));
            Assert.assertEquals(array.length, arraySize,
                    "Array size created by the pool does not match the request.");

            Assert.assertEquals(storage.size(), 0, "Some arrays were not returned by the pool.");
        }

        Assert.assertEquals(unPooledArrays.size(), unPooledArrayNum, "Some arrays that were not"
                + " supposed to be pooled were actually pooled.");

        pool.discard();
    }
}
