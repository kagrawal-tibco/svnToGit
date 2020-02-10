package com.tibco.cep.query.stream._retired_;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream._retired_.LinkedTupleSet;
import com.tibco.cep.query.stream._retired_.MultiLinkedTupleSet;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;

/*
* Author: Ashwin Jayaprakash Date: Mar 23, 2008 Time: 12:28:48 AM
*/
public class MultiLinkedTupleSetTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            doTest1();
            System.err.println("=============");
            doTest2();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doTest1() {
        ArrayList<LiteTuple> tuples = new ArrayList<LiteTuple>();
        tuples.add(new LiteTuple(1L, new Object[]{"1"}));
        LinkedTupleSet<LiteTuple> tupleSet = new LinkedTupleSet<LiteTuple>(tuples);

        tuples = new ArrayList<LiteTuple>();
        tuples.add(new LiteTuple(2L, new Object[]{"2"}));
        LinkedTupleSet<LiteTuple> tupleSet2 = new LinkedTupleSet<LiteTuple>(tuples);
        tupleSet.setNext(tupleSet2);

        tuples = new ArrayList<LiteTuple>();
        tuples.add(new LiteTuple(3L, new Object[]{"3"}));
        LinkedTupleSet<LiteTuple> tupleSet4 = new LinkedTupleSet<LiteTuple>(tuples);
        tupleSet2.setNext(tupleSet4);

        long count = 1;
        MultiLinkedTupleSet<LiteTuple> mlts = new MultiLinkedTupleSet<LiteTuple>(tupleSet);
        for (Iterator<LiteTuple> iterator = mlts.iterator(); iterator.hasNext();) {
            LiteTuple liteTuple = iterator.next();

            System.err.println(liteTuple);

            Assert.assertEquals(liteTuple.getId().longValue(), count,
                    "Element does not match.");
            count++;
        }
        Assert.assertEquals(4, count, "Last element does not match.");

        Assert.assertEquals(mlts.size(), 3, "size() returned wrong value.");

        Assert.assertFalse(mlts.isEmpty(), "isEmpty() returned wrong value.");
    }

    protected void doTest2() {
        ArrayList<LiteTuple> tuples = new ArrayList<LiteTuple>();
        tuples.add(new LiteTuple(1L, new Object[]{"1"}));
        LinkedTupleSet<LiteTuple> tupleSet4 = new LinkedTupleSet<LiteTuple>(tuples);

        long count = 1;
        MultiLinkedTupleSet<LiteTuple> mlts = new MultiLinkedTupleSet<LiteTuple>(tupleSet4);
        for (Iterator<LiteTuple> iterator = mlts.iterator(); iterator.hasNext();) {
            LiteTuple liteTuple = iterator.next();

            System.err.println(liteTuple);

            Assert.assertEquals(liteTuple.getId().longValue(), count,
                    "Element does not match.");
            count++;
        }
        Assert.assertEquals(2, count, "Last element does not match.");
    }
}
