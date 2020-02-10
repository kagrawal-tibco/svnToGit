package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.context.Context;
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
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Nov 12, 2007 Time: 6:03:33 PM
 */

public class TimeWindowTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            final TupleInfo inputInfo =
                    new AbstractTupleInfo(InputTuple.class, new String[]{"columnA",
                            "columnB", "columnC"},
                            new Class[]{String.class, Long.class, Integer.class}) {
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

            final WithdrawableSource source =
                    new WithdrawableSourceImpl(new ResourceId(""), inputInfo);
            sources.put("source", source);

            TimeWindowInfo windowInfo = new TimeWindowInfo(3, 5000);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    TimeWindow timeWindow = new TimeWindow(new ResourceId(parentResourceId, "Time:"
                            + id), inputInfo, groupKey, windowOwner,
                            (TimeWindowInfo) windowInfo);

                    timeWindow.init(aggregateTransformer, timeWindow, null);

                    return timeWindow;
                }
            };

            PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                    new ResourceId(""), inputInfo, groupAggregateInfo, windowInfo, builder);

            DStream deleteStream = new DStream(partitionedStream, new ResourceId(""));

            sink = new StreamedSink(deleteStream, new ResourceId(""));

            PublicContinuousQuery continuousQuery =
                    new PublicContinuousQuery(master.getAgentService().getName(), "TimeWindowQry",
                            new Source[]{source}, sink);

            continuousQuery.init(null);
            continuousQuery.start();

            // ----------

            List<Tuple> allTuples = new LinkedList<Tuple>();

            List<Tuple> adds = new LinkedList<Tuple>();
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 1l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 2l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 3l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 4l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 5l, 6}));

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            Context context = continuousQuery.getContext();

            for (Tuple t : adds) {
                continuousQuery.enqueueInput(new Object[]{t, true});
            }

            List<Object[]> expectedResults = new LinkedList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 4);
            Assert.assertTrue(received.isEmpty(), "Should not have received any results.");

            // ----------

            System.err.println();
            System.err.println("Pausing to receive..");
            Thread.sleep(windowInfo.getTimeMillis() + 500 /* Leeway. */);

            expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"A", 1l, 6});
            expectedResults.add(new Object[]{"A", 4l, 6});
            expectedResults.add(new Object[]{"A", 3l, 6});
            expectedResults.add(new Object[]{"B", 2l, 6});
            expectedResults.add(new Object[]{"B", 5l, 6});

            received = collectAndMatchStreamedSink(expectedResults, 6);
            verifyCollection(expectedResults, received);

            // ----------

            adds.clear();
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 6l, 7}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 7l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 8l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 9l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 10l, 2}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 11l, 7}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 12l, 7}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 13l, 2}));

            allTuples.addAll(adds);

            System.err.println();
            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }

            for (Tuple t : adds) {
                continuousQuery.enqueueInput(new Object[]{t, true});
            }

            expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"B", 6l, 7});
            expectedResults.add(new Object[]{"A", 7l, 6});
            expectedResults.add(new Object[]{"A", 8l, 6});
            expectedResults.add(new Object[]{"B", 9l, 6});

            expectedResults.add(new Object[]{"A", 10l, 2});
            expectedResults.add(new Object[]{"B", 11l, 7});
            expectedResults.add(new Object[]{"B", 12l, 7});
            expectedResults.add(new Object[]{"A", 13l, 2});

            received = collectAndMatchStreamedSink(expectedResults, 6);
            verifyCollection(expectedResults, received);

            // ----------

            List<Tuple> deletes = new LinkedList<Tuple>();
            expectedResults = new LinkedList<Object[]>();

            while (allTuples.isEmpty() == false) {
                deletes.clear();

                Tuple t = allTuples.remove(allTuples.size() - 1);
                deletes.add(t);

                System.err.println();
                System.err.println("Sending..DELETES");
                for (Tuple delT : deletes) {
                    System.err.println(delT);
                }

                continuousQuery.enqueueInput(new Object[]{t, false});

                received = collectAndMatchStreamedSink(expectedResults, 1);
                Assert.assertTrue(received.isEmpty(), "Should not have received any results.");
            }

            // ----------

            continuousQuery.stop();
            continuousQuery.discard();

            commonTests();
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
            Object[] sentData = (Object[]) object;
            InputTuple inputTuple = (InputTuple) sentData[0];
            boolean isNew = (Boolean) sentData[1];

            // Only one source is present.
            if (isNew) {
                sources[0].sendNewTuple(context, inputTuple);
            }
            else {
                ((WithdrawableSource) sources[0]).sendDeadTuple(context, inputTuple);
            }
        }
    }
}
