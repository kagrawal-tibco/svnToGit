package com.tibco.cep.query.stream.util;

import com.tibco.cep.query.stream.framework.TestGroupNames;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

/*
* Author: Ashwin Jayaprakash Date: Apr 11, 2008 Time: 11:03:39 AM
*/
public class FixedKeyHashMapPerfTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        final int avgLoopCount = 10;

        System.err.println("Warming up..");
        performTests(10, 20000, avgLoopCount);
        performTests(100, 20000, avgLoopCount);

        System.err.println();
        System.err.println("Testing..");
        performTests(1, 1000000, avgLoopCount);
        performTests(2, 1000000, avgLoopCount);
        performTests(5, 500000, avgLoopCount);
        performTests(10, 200000, avgLoopCount);
        performTests(15, 10000, avgLoopCount);
        performTests(50, 1000, avgLoopCount);
        performTests(100, 200, avgLoopCount);
        performTests(1000, 200, avgLoopCount);
    }

    private void performTests(int numKeys, int loopCount, int avgLoopCount) {
        HashSet<String> refKeys = makeKeys(numKeys);
        String[] refKeyArray = refKeys.toArray(new String[refKeys.size()]);

        //---------

        long mapTime = 0;
        long fixedKeyMapTime = 0;
        long fixedKeyMapTimeInternal = 0;

        for (int q = 0; q < avgLoopCount; q++) {
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>(refKeyArray.length);
            for (String key : refKeys) {
                hashMap.put(key, null);
            }
            mapTime += runLoop(refKeyArray, hashMap, loopCount);

            System.gc();

            //---------

            FixedKeyHashMap<String, Integer> fixedKeyHashMap =
                    new FixedKeyHashMap<String, Integer>(refKeys);
            fixedKeyMapTime += runLoop(refKeyArray, fixedKeyHashMap, loopCount);

            System.gc();

            fixedKeyMapTimeInternal +=
                    runFKHMWithInternalCalls(refKeyArray, fixedKeyHashMap, loopCount);

            System.gc();
        }

        //---------

        refKeys.clear();

        System.err.println("Num keys: " + numKeys + ", loopCount: " + loopCount
                + " : HashMap millis: " + mapTime / avgLoopCount
                + ", FixedKeyHashMap millis: " + fixedKeyMapTime / avgLoopCount
                + ", FixedKeyHashMap (internal) millis: " + fixedKeyMapTimeInternal / avgLoopCount);
    }

    private HashSet<String> makeKeys(int numKeys) {
        HashSet<String> refKeys = new HashSet<String>();
        Random random = new Random();

        for (int i = 0; i < numKeys; i++) {
            refKeys.add(random.nextInt() + "");
        }

        return refKeys;
    }

    private long runLoop(String[] keys, Map<String, Integer> map, int testLoopNum) {
        long start = System.currentTimeMillis();

        for (int m = 0; m < testLoopNum; m++) {
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];

                map.put(key, i);
            }

            for (int k = 0; k < 10; k++) {
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];

                    Integer x = map.get(key);
                }
            }

            map.clear();
        }

        return System.currentTimeMillis() - start;
    }

    private long runFKHMWithInternalCalls(String[] keys, FixedKeyHashMap<String, Integer> map,
                                          int testLoopNum) {
        long start = System.currentTimeMillis();

        int[] positions = new int[keys.length];

        for (int m = 0; m < testLoopNum; m++) {
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];

                //Short cut method to do both.
                positions[i] = map.setValueAndGetInternalPosition(key, i);
            }

            for (int k = 0; k < 10; k++) {
                for (int i = 0; i < positions.length; i++) {
                    int index = positions[i];

                    Integer x = map.getValueAtInternalPosition(index);

                    if (x == null) {
                        Assert.fail(keys[i] + "'s cached position returned null.");
                    }
                }
            }

            map.clear();
        }

        return System.currentTimeMillis() - start;
    }
}