package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.context.*;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 10, 2007 Time: 4:20:35 PM
 */

public class PartitionedStreamTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            final InputTupleInfo inputInfo = new InputTupleInfo(InputTuple.class, new String[]{
                    "columnA", "columnB", "columnC"}, new Class[]{Integer.class, Long.class,
                    Integer.class});

            // ----------

            LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                    new LinkedHashMap<String, GroupAggregateItemInfo>();

            itemInfos.put("columnB", new GroupAggregateItemInfo(new GroupItemInfo(
                    new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(GlobalContext globalContext,
                                              QueryContext queryContext, Tuple tuple) {
                            return tuple.getColumn(1);
                        }
                    })));

            GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

            String[] columnNames = {"columnB"};
            final TupleInfo outputTupleInfo = new OutputTupleInfo(OutputTuple.class, columnNames,
                    new Class[]{Long.class});

            // ----------

            final WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("source"),
                    inputInfo);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo,
                                           WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    Window window = new SimpleAggregatedStreamWindow(new ResourceId(
                            parentResourceId, "SimpleWindow:" + id), inputInfo, groupKey,
                            windowOwner);

                    window.init(aggregateTransformer, null, null);

                    return window;
                }
            };

            AggregatedPartitionedStream partitionedStream = new AggregatedPartitionedStream(source
                    .getInternalStream(), new ResourceId("aggregate-partition"), outputTupleInfo,
                    groupAggregateInfo, builder);

            sources.put("$source", source);
            sink = new StreamedSink(partitionedStream, new ResourceId("sink"));

            // ----------

            List<Tuple> allTuples = new LinkedList<Tuple>();

            WrappedCustomCollection<Tuple> adds =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, null, 7}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 5001l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 6001l, 6}));

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), "GroupedAggregator"));

            source.sendNewTuples(context, adds);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{6001l});
            expectedResults.add(new Object[]{5001l});
            expectedResults.add(new Object[]{null});

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 1);
            verifyCollection(expectedResults, received);

            // ----------

            adds.clear();
            adds.add(
                    new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null, null, 7}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 5001l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 6001l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 5000l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, 6002l, 2}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0, null, 2}));

            allTuples.addAll(adds);

            System.err.println();
            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }
            source.sendNewTuples(context, adds);

            expectedResults.clear();
            expectedResults.add(new Object[]{6002l});
            expectedResults.add(new Object[]{5000l});

            received = collectAndMatchStreamedSink(expectedResults, 1);
            verifyCollection(expectedResults, received);

            // ----------

            WrappedCustomCollection<Tuple> deletes =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            Collections.shuffle(allTuples);

            while (allTuples.isEmpty() == false) {
                deletes.clear();

                Tuple t = allTuples.remove(0);
                deletes.add(t);

                System.err.println();
                System.err.println("Sending..DELETES");
                for (Tuple delT : deletes) {
                    System.err.println(delT);
                }

                source.getInternalStream().getLocalContext().setNewTuples(null);
                source.getInternalStream().getLocalContext().setDeadTuples(deletes);
                source.getInternalStream().process(context);

                expectedResults = new LinkedList<Object[]>();
                received = collectAndMatchStreamedSink(expectedResults, 1);
                Assert.assertEquals(received.size(), 0, "No results were expected.");
            }

            source.getInternalStream().getLocalContext().setNewTuples(null);
            source.getInternalStream().getLocalContext().setDeadTuples(null);
            source.getInternalStream().process(context);

            expectedResults = new LinkedList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 1);
            Assert.assertEquals(received.size(), 0, "No results were expected.");

            commonTests();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
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
}
