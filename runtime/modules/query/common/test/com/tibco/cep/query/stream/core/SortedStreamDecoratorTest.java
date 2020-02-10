package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.*;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortItemInfo;
import com.tibco.cep.query.stream.sort.SortedStream;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeys;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 5, 2007 Time: 4:35:28 PM
 */

public class SortedStreamDecoratorTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            SortInfo info = new SortInfo(new SortItemInfo[]{new SortItemInfo(true),
                    new SortItemInfo(true), new SortItemInfo(true, 1, 0)});
            runTest(info);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void runTest(SortInfo info) throws Exception, InterruptedException {
        TupleValueExtractor[] extractors = new DefaultTupleValueExtractor[info.getItems().length];
        extractors[0] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(0);
            }
        };
        extractors[1] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(1);
            }
        };
        extractors[2] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(2);
            }
        };

        List<Comparator<Object>> comparators = new ArrayList<Comparator<Object>>(extractors.length);
        comparators.add(new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 == FixedKeys.NULL) {
                    if (o2 == FixedKeys.NULL) {
                        return 0;
                    }

                    // Nulls first.
                    return -1;
                }
                else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                return (Integer) o1 - (Integer) o2;
            }
        });
        comparators.add(new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 == FixedKeys.NULL) {
                    if (o2 == FixedKeys.NULL) {
                        return 0;
                    }

                    // Nulls first.
                    return -1;
                }
                else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                long thisVal = (Long) o1;
                long anotherVal = (Long) o2;

                return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
            }
        });
        comparators.add(new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 == FixedKeys.NULL) {
                    if (o2 == FixedKeys.NULL) {
                        return 0;
                    }

                    // Nulls first.
                    return -1;
                }
                else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                return (Integer) o1 - (Integer) o2;
            }
        });

        // ----------

        List<Tuple> allTuples = new LinkedList<Tuple>();

        WrappedCustomCollection<Tuple> adds = new WrappedCustomCollection(new LinkedList<Tuple>());
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{300, null, 7}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null, 2000l, 1}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null, null, null}));

        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{200, 5000l, 6}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{200, 5001l, 1}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{200, 5002l, 2}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{200, 5000l, 7}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{200, 5001l, 6}));

        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{300, 4000l, 6}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{300, 4001l, 1}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{300, 4002l, 2}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{300, 4000l, 7}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{300, 4001l, 6}));

        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6000l, 6}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6001l, 1}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6002l, 2}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6000l, 7}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6001l, 6}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6001l, 6}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6001l, 6}));

        allTuples.addAll(adds);

        System.err.println("Sending..");
        for (Tuple tuple : adds) {
            System.err.println(tuple);
        }

        // ----------

        WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("source"), null);
        sources.put("$Source", source);

        SortedStream sortedStream = new SortedStream(source.getInternalStream(),
                new ResourceId("sort"), info, extractors, comparators);

        sink = new StreamedSink(sortedStream, new ResourceId("sink"));

        // ----------

        Context context = new Context(new DefaultGlobalContext(),
                new DefaultQueryContext(master.getAgentService().getName(), "Sorter"));

        source.sendNewTuples(context, adds);

        List<Object[]> expectedResults = new LinkedList<Object[]>();
        expectedResults.add(new Object[]{null, null, null});
        expectedResults.add(new Object[]{null, 2000l, 1});
        expectedResults.add(new Object[]{200, 5000l, 6});
        expectedResults.add(new Object[]{200, 5001l, 1});
        expectedResults.add(new Object[]{200, 5002l, 2});
        expectedResults.add(new Object[]{300, null, 7});
        expectedResults.add(new Object[]{300, 4000l, 6});
        expectedResults.add(new Object[]{300, 4001l, 1});
        expectedResults.add(new Object[]{300, 4002l, 2});
        expectedResults.add(new Object[]{500, 6000l, 6});
        expectedResults.add(new Object[]{500, 6001l, 1});
        expectedResults.add(new Object[]{500, 6002l, 2});

        Set<Tuple> undeletedAndSuppressed = new HashSet<Tuple>();
        undeletedAndSuppressed.addAll(allTuples);

        LinkedIdentityHashMap<Tuple, Object[]> received =
                collectAndMatchStreamedSink(expectedResults, 5);
        undeletedAndSuppressed.removeAll(received.keySet());
        verifyCollection(expectedResults, received);

        // ----------

        adds.clear();
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{200, 5000l, 6}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{500, 6002l, 2}));

        allTuples.addAll(adds);
        undeletedAndSuppressed.addAll(allTuples);

        System.err.println();
        System.err.println("Sending..");
        for (Tuple t : adds) {
            System.err.println(t);
        }
        source.sendNewTuples(context, adds);

        /**
         * <pre>
         * X1 Y1 Z1
         * X1 Y1 Z2
         * X1 Y1 Z2
         * X1 Y1 Z3
         *
         * X1 Y2 Z1
         * X1 Y2 Z2
         * X1 Y2 Z2
         * X1 Y2 Z3
         *
         * X2 Y1 Z1
         * X2 Y1 Z2
         * X2 Y1 Z2
         * X2 Y1 Z3
         *
         * X2 Y2 Z1
         * X2 Y2 Z2
         * X2 Y2 Z2
         * X2 Y2 Z3
         *
         * X3 Y1 Z1
         * X3 Y1 Z2
         * X3 Y1 Z2
         * X3 Y1 Z3
         *
         * X3 Y2 Z1
         * X3 Y2 Z2
         * X3 Y2 Z2
         * X3 Y2 Z3
         *
         * Limit clause: [All], [All], [1,0]
         * </pre>
         *
         * Here, the limit-clause of the last column is for the last set of
         * columns - twice. Once for eliminating Tuples with different values
         * and another time again for eliminating multiple rows with the same
         * value.
         */

        expectedResults = new LinkedList<Object[]>();
        expectedResults.add(new Object[]{null, null, null});
        expectedResults.add(new Object[]{null, 2000l, 1});
        expectedResults.add(new Object[]{200, 5000l, 6});
        expectedResults.add(new Object[]{200, 5000l, 6});
        expectedResults.add(new Object[]{200, 5001l, 1});
        expectedResults.add(new Object[]{200, 5002l, 2});
        expectedResults.add(new Object[]{300, null, 7});
        expectedResults.add(new Object[]{300, 4000l, 6});
        expectedResults.add(new Object[]{300, 4001l, 1});
        expectedResults.add(new Object[]{300, 4002l, 2});
        expectedResults.add(new Object[]{500, 6000l, 6});
        expectedResults.add(new Object[]{500, 6001l, 1});
        expectedResults.add(new Object[]{500, 6002l, 2});
        expectedResults.add(new Object[]{500, 6002l, 2});

        received = collectAndMatchStreamedSink(expectedResults, 5);
        undeletedAndSuppressed.removeAll(received.keySet());
        verifyCollection(expectedResults, received);

        // ----------

        adds.clear();
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{400, 3000l, 6}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{400, 3500l, 3}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{400, 2000l, 1}));
        adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{550, 7002l, 2}));

        allTuples.addAll(adds);

        System.err.println();
        System.err.println("Sending..");
        for (Tuple t : adds) {
            System.err.println(t);
        }
        source.sendNewTuples(context, adds);

        expectedResults = new LinkedList<Object[]>();
        expectedResults.add(new Object[]{null, null, null});
        expectedResults.add(new Object[]{null, 2000l, 1});
        expectedResults.add(new Object[]{200, 5000l, 6});
        expectedResults.add(new Object[]{200, 5000l, 6});
        expectedResults.add(new Object[]{200, 5001l, 1});
        expectedResults.add(new Object[]{200, 5002l, 2});
        expectedResults.add(new Object[]{300, null, 7});
        expectedResults.add(new Object[]{300, 4000l, 6});
        expectedResults.add(new Object[]{300, 4001l, 1});
        expectedResults.add(new Object[]{300, 4002l, 2});
        expectedResults.add(new Object[]{400, 2000l, 1});
        expectedResults.add(new Object[]{400, 3000l, 6});
        expectedResults.add(new Object[]{400, 3500l, 3});
        expectedResults.add(new Object[]{500, 6000l, 6});
        expectedResults.add(new Object[]{500, 6001l, 1});
        expectedResults.add(new Object[]{500, 6002l, 2});
        expectedResults.add(new Object[]{500, 6002l, 2});
        expectedResults.add(new Object[]{550, 7002l, 2});

        received = collectAndMatchStreamedSink(expectedResults, 5);
        undeletedAndSuppressed.removeAll(received.keySet());
        verifyCollection(expectedResults, received);

        // ----------

        WrappedCustomCollection<Tuple> deletes =
                new WrappedCustomCollection(new LinkedList<Tuple>());

        expectedResults = new LinkedList<Object[]>();
        expectedResults.add(new Object[]{200, 5000l, 6});
        expectedResults.add(new Object[]{400, 2000l, 1});
        expectedResults.add(new Object[]{400, 3000l, 6});
        expectedResults.add(new Object[]{400, 3500l, 3});
        expectedResults.add(new Object[]{500, 6000l, 6});
        expectedResults.add(new Object[]{500, 6001l, 1});
        expectedResults.add(new Object[]{500, 6002l, 2});
        expectedResults.add(new Object[]{500, 6002l, 2});
        expectedResults.add(new Object[]{550, 7002l, 2});

        for (int x = allTuples.size() / 2; x > 0; x--) {
            deletes.clear();

            Tuple t = allTuples.remove(0);
            deletes.add(t);

            undeletedAndSuppressed.remove(t);

            System.err.println();
            System.err.println("Sending..DELETES");
            for (Tuple delT : deletes) {
                System.err.println(delT);
            }

            source.getInternalStream().getLocalContext().setNewTuples(null);
            source.getInternalStream().getLocalContext().setDeadTuples(deletes);
            source.getInternalStream().process(context);

            if (x == 1) {
                // Capture only the last set.
                received = collectAndMatchStreamedSink(expectedResults, 1);
                undeletedAndSuppressed.removeAll(received.keySet());
            }
            else {
                // Throw away.
                LinkedIdentityHashMap<Tuple, Object[]> throwAway = collectAndMatchStreamedSink(
                        new LinkedList<Object[]>(), 1);

                undeletedAndSuppressed.removeAll(throwAway.keySet());

                for (Object obj : throwAway.keySet()) {
                    Tuple tuple = (Tuple) obj;
                    tuple.decrementRefCount();
                }
            }
        }

        /*
         * These Tuples are still in the Stream, because the for-loop above
         * stopped just short of deleting them.
         */
        Set<Tuple> lastSet = new HashSet<Tuple>(received.keySet());
        for (Tuple tuple : lastSet) {
            Assert.assertEquals(((TrackedTuple) tuple).getRefCount(), 2, "Ref count of Tuple: "
                    + tuple + ", does not match expected the results.");
        }

        // Verify the last set.
        verifyCollection(expectedResults, received);

        // Exceptions for all undeleted Tuples - Suppressed and Observed.
        lastSet.addAll(undeletedAndSuppressed);
        commonTests(lastSet);
    }

    public static class InputTuple extends TrackedTuple {
        public InputTuple(Number id) {
            super(id);
        }

        public InputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }
}
