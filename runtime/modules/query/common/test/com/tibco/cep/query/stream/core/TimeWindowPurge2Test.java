package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.context.Context;
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
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Run;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Step;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.partition.purge.*;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Nov 12, 2007 Time: 6:03:33 PM
 */

public class TimeWindowPurge2Test extends AbstractTest {
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

            final WithdrawableSource source = new WithdrawableSourceImpl(
                    new ResourceId("MySource"), inputInfo);
            sources.put("source", source);

            TimeWindowInfo windowInfo = new TimeWindowInfo(50, 60 * 1000);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    TimeWindow timeWindow = new TimeWindow(new ResourceId(parentResourceId,
                            "Tumbling:" + id), inputInfo, groupKey, windowOwner,
                            (TimeWindowInfo) windowInfo);

                    WindowPurgeAdvisor advisor = new WindowPurgeAdvisor() {
                        public WindowPurgeAdvice advise(GlobalContext globalContext,
                                                        DefaultQueryContext queryContext,
                                                        WindowStats stats) {
                            Object o2 = stats.getStat("count");

                            if (o2 != null && ((Number) o2).intValue() > 0) {
                                long futureDated = Clock.getCurrentTimeMillis() + 10000;
                                ScheduledWindowPurgeAdvice scheduledAdvice =
                                        new ScheduledWindowPurgeAdvice(
                                                futureDated) {
                                            @Override
                                            public ImmediateWindowPurgeAdvice adviseNow(
                                                    WindowStats innerStats) {
                                                Object innerCount = innerStats.getStat("count");

                                                if (innerCount != null
                                                        && ((Number) innerCount).intValue() > 0) {
                                                    return new ImmediateWindowPurgeAdvice(
                                                            ((Number) innerCount).intValue());
                                                }
                                                return null;
                                            }
                                        };

                                return scheduledAdvice;
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
                    AggregateInfo purgeAggregateInfo = new AggregateInfo(map);

                    WindowPurgeManager purgeManager = new WindowPurgeManager(timeWindow,
                            windowOwner, advisor, purgeAggregateInfo);
                    timeWindow.init(aggregateTransformer, timeWindow, purgeManager);

                    return timeWindow;
                }
            };

            PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                    new ResourceId("Partitioner"), inputInfo, groupAggregateInfo, windowInfo,
                    builder);

            DStream deleteStream = new DStream(partitionedStream, new ResourceId("DStream"));

            sink = new StreamedSink(deleteStream, new ResourceId("MySink"));

            PublicContinuousQuery continuousQuery =
                    new PublicContinuousQuery(master.getAgentService().getName(), "TimeWindowQry",
                            new Source[]{source}, sink);

            continuousQuery.init(null);
            continuousQuery.start();

            // ----------

            List<Tuple> allTuples = new LinkedList<Tuple>();

            List<Tuple> adds = new LinkedList<Tuple>();
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 1l, 7}));
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
            DefaultQueryContext queryContext = context.getQueryContext();
            queryContext.getQueryConfig().setTracingEnabled(true);

            for (Tuple t : adds) {
                continuousQuery.enqueueInput(new Object[]{t, true});
            }

            List<Object[]> expectedResults = new LinkedList<Object[]>();

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 5);
            Assert.assertTrue(received.isEmpty(), "Should not have received any results.");

            // ----------

            System.err.println();
            System.err.println("Pausing to receive..");
            Thread.sleep(5000);

            expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"A", 1l, 7});
            expectedResults.add(new Object[]{"A", 4l, 6});
            expectedResults.add(new Object[]{"A", 3l, 6});
            expectedResults.add(new Object[]{"B", 2l, 6});
            expectedResults.add(new Object[]{"B", 5l, 6});

            received = collectAndMatchStreamedSink(expectedResults, 5);
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

            System.err.println();
            System.err.println("Pausing to receive..");
            Thread.sleep(5000);

            expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"A", 7l, 6});
            expectedResults.add(new Object[]{"A", 8l, 6});
            expectedResults.add(new Object[]{"A", 10l, 2});
            expectedResults.add(new Object[]{"A", 13l, 2});
            expectedResults.add(new Object[]{"B", 6l, 7});
            expectedResults.add(new Object[]{"B", 9l, 6});
            expectedResults.add(new Object[]{"B", 11l, 7});
            expectedResults.add(new Object[]{"B", 12l, 7});

            received = collectAndMatchStreamedSink(expectedResults, 6);
            verifyCollection(expectedResults, received);

            // ----------

            List<Tuple> deletes = new LinkedList<Tuple>();

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

            QueryWatcher watcher = context.getQueryContext().getQueryWatcher();
            System.out.println("Total: " + watcher.peekOldestRun().getTotalRuns() + ", Success: "
                    + watcher.peekOldestRun().getTotalSuccessfulRuns());

            for (Iterator<Run> iterator = watcher.getRecentRunsIterator(); iterator.hasNext();) {
                Run run = iterator.next();

                String message = "Run: " + run.getStatus() + ", " + run.getStartTime() + " to "
                        + run.getEndTime();
                System.err.println(message);

                Step step = run.getFirstStep();
                int i = 1;
                while (step != null) {
                    message = "  " + i + ")" + step.getResourceId();
                    System.err.println(message);

                    i++;

                    step = step.getNextStep();
                }

                Throwable error = run.getError();
                if (error != null) {
                    System.err.println(error);
                }
            }

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

    public static class InternalAdvisorAggregateTuple extends TrackedTuple {
        public InternalAdvisorAggregateTuple(Number id) {
            super(id);
        }

        public InternalAdvisorAggregateTuple(Number id, Object[] columns) {
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
