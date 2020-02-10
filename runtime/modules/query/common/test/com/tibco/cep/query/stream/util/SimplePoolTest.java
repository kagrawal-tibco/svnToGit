package com.tibco.cep.query.stream.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.util.SimplePool;

/*
 * Author: Ashwin Jayaprakash Date: Feb 7, 2008 Time: 11:17:12 AM
 */

public class SimplePoolTest extends AbstractTest {
    @Test(groups = { TestGroupNames.RUNTIME })
    public void test() {
        final int poolSize = 16;
        SimplePool<Integer> pool = new SimplePool<Integer>(poolSize);

        try {
            for (int trial = 0; trial < 2; trial++) {
                Assert.assertEquals(pool.getNumPooledElements(), 0, "Pool should've been empty.");

                for (int i = 0; i < poolSize; i++) {
                    boolean b = pool.returnOrAdd(new Integer(i));

                    Assert.assertEquals(b, true, "Object should've been pooled.");
                }

                Assert.assertEquals(pool.getNumPooledElements(), poolSize,
                        "Pool size is not correct.");

                for (int i = poolSize - 1; i >= 0; i--) {
                    Integer x = pool.fetch();

                    Assert.assertEquals(x.intValue(), i, "Internal pooling logic has changed.");
                }

                Assert.assertEquals(pool.getNumPooledElements(), 0, "Pool should've been empty.");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
