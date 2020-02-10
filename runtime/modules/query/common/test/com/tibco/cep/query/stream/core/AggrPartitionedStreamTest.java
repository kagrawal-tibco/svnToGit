package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.aggregate.AverageAggregator;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.aggregate.DoubleSumAggregator;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 10, 2007 Time: 4:20:35 PM
 */

public class AggrPartitionedStreamTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() throws Exception {
        PublicContinuousQuery continuousQuery = null;

        try {
            final InputTupleInfo inputInfo = new InputTupleInfo(InputTuple.class, new String[]{
                    "columnA", "columnB"}, new Class[]{Integer.class, Double.class});

            // ----------

            LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                    new LinkedHashMap<String, GroupAggregateItemInfo>();

            itemInfos.put("columnA", new GroupAggregateItemInfo(new GroupItemInfo(
                    new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(GlobalContext globalContext,
                                              QueryContext queryContext, Tuple tuple) {
                            return tuple.getColumn(0);
                        }
                    })));
            itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                    CountAggregator.CREATOR, new DefaultTupleValueExtractor() {
                @Override
                public Object extract(GlobalContext globalContext,
                                      QueryContext queryContext, Tuple tuple) {
                    return tuple.getColumn(1);
                }
            })));
            itemInfos.put("sum", new GroupAggregateItemInfo(new AggregateItemInfo(
                    DoubleSumAggregator.CREATOR, new DefaultTupleValueExtractor() {
                @Override
                public Object extract(GlobalContext globalContext,
                                      QueryContext queryContext, Tuple tuple) {
                    return tuple.getColumn(1);
                }
            })));
            itemInfos.put("avg", new GroupAggregateItemInfo(new AggregateItemInfo(
                    AverageAggregator.CREATOR, new DefaultTupleValueExtractor() {
                @Override
                public Object extract(GlobalContext globalContext,
                                      QueryContext queryContext, Tuple tuple) {
                    return tuple.getColumn(1);
                }
            })));

            GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

            //todo Test min and max too.

            String[] columnNames = {"columnA", "count", "sum", "avg"};
            final TupleInfo outputTupleInfo = new OutputTupleInfo(OutputTuple.class, columnNames,
                    new Class[]{Long.class, Integer.class, Double.class, Double.class});

            // ----------

            final WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("source"),
                    inputInfo);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    Window window = new TrackedWindow(
                            new ResourceId(parentResourceId, "SimpleWindow:" + id), inputInfo,
                            groupKey, windowOwner);

                    AggregatedStream aggregatedStream = new AggregatedStream(window,
                            new ResourceId(parentResourceId, "Aggregate:" + id), aggregateInfo,
                            true);

                    window.init(aggregateTransformer, aggregatedStream, null);

                    return window;
                }
            };

            TrackedAPS partitionedStream = new TrackedAPS(source.getInternalStream(),
                    new ResourceId("aggregate-partition"), outputTupleInfo,
                    groupAggregateInfo, builder);

            sources.put("$source", source);
            sink = new StreamedSink(partitionedStream, new ResourceId("sink"));

            continuousQuery =
                    new PublicContinuousQuery(master.getAgentService().getName(), "AggregatedQry",
                            new Source[]{source}, sink);

            continuousQuery.init(null);
            continuousQuery.start();

            Context context = continuousQuery.getContext();

            // ----------

            LinkedList<Tuple> allTuples = new LinkedList<Tuple>();

            WrappedCustomCollection<Tuple> adds =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 1d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 1d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 2d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 3d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 3d}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1, 4d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1, 4d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1, 4d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1, 4d}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{2, 5d}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null, 7d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null, 7d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null, 8d}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null, 7d}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4, null}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4, null}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4, null}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4, null}));

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            continuousQuery.enqueueInput(new Object[]{adds, true});

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{null, 4, 29.0, 7.25});
            expectedResults.add(new Object[]{4, 4, 0.0, 0.0});
            expectedResults.add(new Object[]{2, 1, 5.0, 5.0});
            expectedResults.add(new Object[]{0, 5, 10.0, 2.0});
            expectedResults.add(new Object[]{1, 4, 16.0, 4.0});

            System.err.println();
            System.err.println("Pausing to receive..");
            Thread.sleep(1200);
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 1);
            verifyCollection(expectedResults, received);

            // ----------

            WrappedCustomCollection<Tuple> deletes =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            LinkedList<Object[]> answersOnDelete = new LinkedList<Object[]>();
            answersOnDelete.add(new Object[]{0, 4, 9.0, 2.25});
            answersOnDelete.add(new Object[]{0, 3, 8.0, 8.0 / 3.0});
            answersOnDelete.add(new Object[]{0, 2, 6.0, 3.0});
            answersOnDelete.add(new Object[]{0, 1, 3.0, 3.0});
            answersOnDelete.add(new Object[]{0, 0, 0.0, 0.0});

            answersOnDelete.add(new Object[]{1, 3, 12.0, 4.0});
            answersOnDelete.add(new Object[]{1, 2, 8.0, 4.0});
            answersOnDelete.add(new Object[]{1, 1, 4.0, 4.0});
            answersOnDelete.add(new Object[]{1, 0, 0.0, 0.0});

            answersOnDelete.add(new Object[]{2, 0, 0.0, 0.0});

            answersOnDelete.add(new Object[]{null, 3, 22.0, 22.0 / 3.0});
            answersOnDelete.add(new Object[]{null, 2, 15.0, 7.5});
            answersOnDelete.add(new Object[]{null, 1, 7.0, 7.0});
            answersOnDelete.add(new Object[]{null, 0, 0.0, 0.0});

            answersOnDelete.add(new Object[]{4, 3, 0.0, 0.0});
            answersOnDelete.add(new Object[]{4, 2, 0.0, 0.0});
            answersOnDelete.add(new Object[]{4, 1, 0.0, 0.0});
            answersOnDelete.add(new Object[]{4, 0, 0.0, 0.0});

            while (allTuples.isEmpty() == false) {
                deletes.clear();

                Tuple t = allTuples.remove();
                deletes.add(t);

                System.err.println();
                System.err.println("Sending..DELETES");
                for (Tuple delT : deletes) {
                    System.err.println(delT);
                }

                continuousQuery.enqueueInput(new Object[]{t, false});

                expectedResults.clear();
                expectedResults.add(answersOnDelete.remove());

                received = collectAndMatchStreamedSink(expectedResults, 1);
                verifyCollection(expectedResults, received);
            }

            commonTests();

            //-------------

            Map<GroupKey, TrackedWindow> windows = TrackedWindow.getCreatedWindows();
            Assert.assertEquals(windows.size(), 6, "Wrong number of windows.");

            for (GroupKey groupKey : windows.keySet()) {
                TrackedWindow window = windows.get(groupKey);

                Assert.assertTrue(window.isDiscardInvoked(),
                        "Window never got discarded: " + Arrays.asList(groupKey.getGroupColumns()));
            }

            Assert.assertEquals(partitionedStream.getDirtyWindows().size(), 0,
                    "Partition still has some dirty windows: " +
                            partitionedStream.getDirtyWindows());

            Assert.assertEquals(partitionedStream.getWindows().size(), 0,
                    "Partition still has some windows: " +
                            partitionedStream.getWindows());

            Assert.assertEquals(context.getQueryContext().getNextCycleSchedule().size(), 0,
                    "Future unscheduled streams are still present: " +
                            context.getQueryContext().getNextCycleSchedule());

            Assert.assertEquals(context.getQueryContext().getCurrentCycleSchedule().size(), 0,
                    "Current unscheduled streams are still present: " +
                            context.getQueryContext().getCurrentCycleSchedule());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (continuousQuery != null && continuousQuery.hasStopped() == false) {
                continuousQuery.stop();

                continuousQuery.discard();
            }
        }
    }

    public static class InputTupleInfo extends AbstractTupleInfo {
        public InputTupleInfo(Class<InputTuple> containerClass, String[] columnNames,
                              Class[] columnTypes) {
            super(containerClass, columnNames, columnTypes);
        }

        public Tuple createTuple(Number id) {
            return new InputTuple(id);
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

    public static class OutputTupleInfo extends AbstractTupleInfo {
        public OutputTupleInfo(Class<OutputTuple> containerClass, String[] columnNames,
                               Class[] columnTypes) {
            super(containerClass, columnNames, columnTypes);
        }

        public Tuple createTuple(Number id) {
            return new OutputTuple(id);
        }
    }

    public static class OutputTuple extends TrackedTuple {
        public OutputTuple(Number id) {
            super(id);
        }

        public OutputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class TrackedAPS extends AggregatedPartitionedStream {
        public TrackedAPS(Stream source, ResourceId id, TupleInfo outputInfo,
                          GroupAggregateInfo groupAggregateInfo,
                          WindowBuilder windowBuilder) {
            super(source, id, outputInfo, groupAggregateInfo, windowBuilder);
        }

        public CustomHashSet<Window> getDirtyWindows() {
            return dirtyWindows;
        }

        public HashMap<GroupKey, Window> getWindows() {
            return windows;
        }


    }

    public static class TrackedWindow extends SimpleAggregatedStreamWindow {
        static final HashMap<GroupKey, TrackedWindow> CREATED_WINDOWS =
                new HashMap<GroupKey, TrackedWindow>();

        protected boolean discardInvoked;

        public TrackedWindow(ResourceId id, TupleInfo outputInfo, GroupKey groupKey,
                             WindowOwner windowOwner) {
            super(id, outputInfo, groupKey, windowOwner);

            CREATED_WINDOWS.put(groupKey, this);
        }

        public static Map<GroupKey, TrackedWindow> getCreatedWindows() {
            return CREATED_WINDOWS;
        }

        public boolean isDiscardInvoked() {
            return discardInvoked;
        }

        @Override
        public void discard(Context context) {
            super.discard(context);

            discardInvoked = true;
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
            boolean isNew = (Boolean) sentData[1];

            // Only one source is present.
            if (isNew) {
                if (sentData[0] instanceof Collection) {
                    AppendOnlyQueue<Tuple> tuples = new AppendOnlyQueue<Tuple>();
                    tuples.addAll((Collection<Tuple>) sentData[0]);

                    sources[0].sendNewTuples(context, tuples);
                }
                else {
                    InputTuple inputTuple = (InputTuple) sentData[0];
                    sources[0].sendNewTuple(context, inputTuple);
                }
            }
            else {
                InputTuple inputTuple = (InputTuple) sentData[0];

                ((WithdrawableSource) sources[0]).sendDeadTuple(context, inputTuple);
            }
        }
    }
}