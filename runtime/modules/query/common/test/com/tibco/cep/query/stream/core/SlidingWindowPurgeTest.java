package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.partition.purge.ImmediateWindowPurgeAdvice;
import com.tibco.cep.query.stream.partition.purge.WindowPurgeAdvisor;
import com.tibco.cep.query.stream.partition.purge.WindowPurgeManager;
import com.tibco.cep.query.stream.partition.purge.WindowStats;
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

public class SlidingWindowPurgeTest extends AbstractTest {
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

            final WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("Source"),
                    inputInfo);
            sources.put("source", source);

            SlidingWindowInfo windowInfo = new SlidingWindowInfo(3);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    SlidingWindow slidingWindow = new SlidingWindow(new ResourceId(
                            parentResourceId, "Sliding:" + id), inputInfo, groupKey,
                            windowOwner, (SlidingWindowInfo) windowInfo);

                    WindowPurgeAdvisor advisor = new WindowPurgeAdvisor() {
                        public ImmediateWindowPurgeAdvice advise(GlobalContext globalContext,
                                                                 DefaultQueryContext queryContext,
                                                                 WindowStats stats) {
                            Object o1 = stats.getStat(WindowStats.ALIAS_PENDING_COUNT);
                            Object o2 = stats.getStat("count");

                            if (o2 != null) {
                                Number number = (Number) o2;

                                if (number.intValue() >= 3) {
                                    ImmediateWindowPurgeAdvice advice =
                                            new ImmediateWindowPurgeAdvice(
                                                    3);
                                    return advice;
                                }
                            }

                            return null;
                        }
                    };

                    LinkedHashMap<String, AggregateItemInfo> map =
                            new LinkedHashMap<String, AggregateItemInfo>();
                    TupleValueExtractor extractor = new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(GlobalContext globalContext,
                                              QueryContext queryContext, Tuple tuple) {
                            // Can return anything for Count.
                            return 1;
                        }
                    };
                    map.put("count", new AggregateItemInfo(CountAggregator.CREATOR, extractor));
                    AbstractTupleInfo aggregateOutputInfo = new AbstractTupleInfo(
                            InternalAdvisorAggregateTuple.class, new String[]{"count"},
                            new Class[]{Integer.class}) {
                        public Tuple createTuple(Number id) {
                            return new InternalAdvisorAggregateTuple(id);
                        }
                    };
                    AggregateInfo purgeAggregateInfo = new AggregateInfo(map);

                    WindowPurgeManager purgeManager = new WindowPurgeManager(slidingWindow,
                            windowOwner, advisor, purgeAggregateInfo);

                    slidingWindow.init(aggregateTransformer, slidingWindow, purgeManager);

                    return slidingWindow;
                }
            };

            PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                    new ResourceId("Partition"), inputInfo, groupAggregateInfo, windowInfo,
                    builder);

            DStream dStream = new DStream(partitionedStream, new ResourceId("DStream"));

            sink = new StreamedSink(dStream, new ResourceId(""));

            ContinuousQuery query =
                    new PublicContinuousQuery(master.getAgentService().getName(), "PurgeQuery",
                            new Source[]{source}, sink);
            query.init(null);
            query.start();

            // ----------

            Map<Integer, List<Object[]>> expectedTupleSets = new HashMap<Integer, List<Object[]>>();

            List<Tuple> allTuples = new LinkedList<Tuple>();
            WrappedCustomCollection<Tuple> adds =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            expectedTupleSets.put(1, new LinkedList<Object[]>());
            InputTuple aTuple =
                    new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 1l,
                            7});
            adds.add(aTuple);
            expectedTupleSets.get(1).add(aTuple.getRawColumns());

            expectedTupleSets.put(2, new LinkedList<Object[]>());
            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 2l, 6});
            adds.add(aTuple);
            expectedTupleSets.get(2).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 3l, 6});
            adds.add(aTuple);
            expectedTupleSets.get(1).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 4l, 6});
            adds.add(aTuple);
            expectedTupleSets.get(1).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 5l, 6});
            adds.add(aTuple);
            expectedTupleSets.get(2).add(aTuple.getRawColumns());

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            query.enqueueInput(adds);

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedTupleSets.get(1), 5);
            verifyCollection(expectedTupleSets.get(1), received);

            // ----------

            adds.clear();

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 6l, 7});
            adds.add(aTuple);
            expectedTupleSets.get(2).add(aTuple.getRawColumns());

            expectedTupleSets.put(3, new LinkedList<Object[]>());
            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 7l, 6});
            adds.add(aTuple);
            expectedTupleSets.get(3).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 8l, 6});
            adds.add(aTuple);
            expectedTupleSets.get(3).add(aTuple.getRawColumns());

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }

            query.enqueueInput(adds);

            received = collectAndMatchStreamedSink(expectedTupleSets.get(2), 8);
            verifyCollection(expectedTupleSets.get(2), received);

            // ----------

            adds.clear();

            expectedTupleSets.put(4, new LinkedList<Object[]>());
            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 9l, 6});
            adds.add(aTuple);
            expectedTupleSets.get(4).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 10l, 2});
            adds.add(aTuple);
            expectedTupleSets.get(3).add(aTuple.getRawColumns());

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }

            query.enqueueInput(adds);

            received = collectAndMatchStreamedSink(expectedTupleSets.get(3), 5);
            verifyCollection(expectedTupleSets.get(3), received);

            // ----------

            adds.clear();

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 11l, 7});
            adds.add(aTuple);
            expectedTupleSets.get(4).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 12l, 7});
            adds.add(aTuple);
            expectedTupleSets.get(4).add(aTuple.getRawColumns());

            expectedTupleSets.put(5, new LinkedList<Object[]>());
            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 13l, 2});
            adds.add(aTuple);
            expectedTupleSets.get(5).add(aTuple.getRawColumns());

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }

            query.enqueueInput(adds);

            received = collectAndMatchStreamedSink(expectedTupleSets.get(4), 5);
            verifyCollection(expectedTupleSets.get(4), received);

            // ----------

            adds.clear();

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 14l, 2});
            adds.add(aTuple);
            expectedTupleSets.get(5).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 15l, 2});
            adds.add(aTuple);
            expectedTupleSets.get(5).add(aTuple.getRawColumns());

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 16l, 2});
            adds.add(aTuple);
            expectedTupleSets.get(5).add(aTuple.getRawColumns());

            Set<Tuple> exceptions = new HashSet<Tuple>();

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 17l, 2});
            adds.add(aTuple);
            exceptions.add(aTuple);

            aTuple = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 18l, 2});
            adds.add(aTuple);
            exceptions.add(aTuple);

            allTuples.addAll(adds);

            System.err.println();
            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }

            query.enqueueInput(adds);

            received = collectAndMatchStreamedSink(expectedTupleSets.get(5), 5);
            verifyCollection(expectedTupleSets.get(5), received);

            // ---------

            for (Tuple tuple : allTuples) {
                query.enqueueInput(tuple);
            }

            received = collectAndMatchStreamedSink(new LinkedList<Object[]>(), 5);

            // ---------

            InternalAdvisorAggregateTuple lastCount = InternalAdvisorAggregateTuple
                    .getLastInstanceCreated();
            Assert.assertEquals(lastCount.getRawColumns(), new Object[]{exceptions.size()},
                    "Last internal purge-count does not have the expected value.");

            exceptions.add(lastCount);

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

    public static class InternalAdvisorAggregateTuple extends TrackedTuple {
        protected static InternalAdvisorAggregateTuple lastInstanceCreated;

        public InternalAdvisorAggregateTuple(Number id) {
            super(id);

            lastInstanceCreated = this;
        }

        public InternalAdvisorAggregateTuple(Number id, Object[] columns) {
            super(id, columns);

            lastInstanceCreated = this;
        }

        public static InternalAdvisorAggregateTuple getLastInstanceCreated() {
            return lastInstanceCreated;
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
