package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.query.continuous.ContinuousQuery;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Nov 12, 2007 Time: 3:49:40 PM
 */

public class SlidingWindowTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            final TupleInfo inputInfo =
                    new AbstractTupleInfo(InputTuple.class, new String[]{"columnA",
                            "columnB", "columnC"},
                            new Class[]{Integer.class, Long.class, Integer.class}) {
                        public Tuple createTuple(Number id) {
                            return new InputTuple(id);
                        }
                    };

            // ----------

            LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                    new LinkedHashMap<String, GroupAggregateItemInfo>();

            // Complex expression as a Group column.
            itemInfos.put("columnA", new GroupAggregateItemInfo(new GroupItemInfo(
                    new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(GlobalContext globalContext,
                                              QueryContext queryContext, Tuple tuple) {
                            return tuple.getColumn(0);
                        }
                    })));

            GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

            // ----------

            final WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("source"),
                    inputInfo);
            sources.put("Source", source);

            SlidingWindowInfo windowInfo = new SlidingWindowInfo(3);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    SlidingWindow slidingWindow = new SlidingWindow(new ResourceId(
                            parentResourceId, "Sliding:" + id), inputInfo, groupKey,
                            windowOwner, (SlidingWindowInfo) windowInfo);

                    slidingWindow.init(aggregateTransformer, slidingWindow, null);

                    return slidingWindow;
                }
            };

            PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                    new ResourceId("partition"), inputInfo, groupAggregateInfo, windowInfo,
                    builder);

            DStream dStream = new DStream(partitionedStream, new ResourceId("sink"));

            sink = new StreamedSink(dStream, new ResourceId(""));

            ContinuousQuery query =
                    new PublicContinuousQuery(master.getAgentService().getName(), "PurgeQuery",
                            new Source[]{source}, sink);
            query.init(null);
            query.start();

            // ----------

            Set<Tuple> exceptions = new HashSet<Tuple>();

            List<Tuple> allTuples = new LinkedList<Tuple>();

            WrappedCustomCollection<Tuple> adds =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            Tuple aTuple =
                    new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 1l, 7});
            adds.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 2l, 6});
            adds.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 3l, 6});
            adds.add(aTuple);
            exceptions.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 4l, 6});
            adds.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 5l, 6});
            adds.add(aTuple);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            query.enqueueInput(adds);

            List<Object[]> expectedResults = new LinkedList<Object[]>();

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 5);
            Assert.assertTrue(received.isEmpty(), "Should not have received any results.");

            allTuples.addAll(adds);

            // ----------

            adds.clear();

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 6l, 7});
            adds.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 7l, 6});
            adds.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 8l, 6});
            adds.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 9l, 6});
            adds.add(aTuple);
            exceptions.add(aTuple);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            query.enqueueInput(adds);

            expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"B", 4l, 6});
            expectedResults.add(new Object[]{"B", 5l, 6});
            expectedResults.add(new Object[]{"B", 6l, 7});

            received = collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            allTuples.addAll(adds);

            // ----------

            adds.clear();

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 10l, 2});
            adds.add(aTuple);
            exceptions.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 11l, 7});
            adds.add(aTuple);
            exceptions.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 12l, 7});
            adds.add(aTuple);
            exceptions.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 13l, 2});
            adds.add(aTuple);
            exceptions.add(aTuple);

            System.err.println();
            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }

            query.enqueueInput(adds);

            expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"A", 1l, 7});
            expectedResults.add(new Object[]{"B", 7l, 6});
            expectedResults.add(new Object[]{"B", 8l, 6});
            expectedResults.add(new Object[]{"A", 2l, 6});

            received = collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            allTuples.addAll(adds);

            // ----------

            for (Tuple t : allTuples) {
                query.enqueueInput(t);
            }

            expectedResults = new LinkedList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 5);

            // ----------

            commonTests(exceptions);

            query.stop();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class InputTuple extends TrackedTuple {
        public InputTuple(Number id) {
            super(id);
        }

        public InputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class PublicContinuousQuery extends ContinuousQueryImpl {
        public PublicContinuousQuery(String regionName, String name, Source[] sources, Sink sink) {
            super(regionName, new ResourceId(name), sources, sink, new AsyncProcessListener() {
                public void beforeStart() {
                }

                public void afterEnd() {
                }
            });
        }

        @Override
        protected void handleQueuedInput(Object object) throws Exception {
            if (object instanceof Collection) {
                WrappedCustomCollection<Tuple> sentData = (WrappedCustomCollection<Tuple>) object;

                // Only one source is present.
                sources[0].sendNewTuples(context, sentData);
            }
            else {
                ((WithdrawableSource) sources[0]).sendDeadTuple(context, (Tuple) object);
            }
        }
    }
}
