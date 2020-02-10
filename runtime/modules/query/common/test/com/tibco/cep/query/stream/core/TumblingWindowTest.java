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
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Nov 12, 2007 Time: 6:03:33 PM
 */

public class TumblingWindowTest extends AbstractTest {
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

            final WithdrawableSource source =
                    new WithdrawableSourceImpl(new ResourceId(""), inputInfo);
            sources.put("source", source);

            TumblingWindowInfo windowInfo = new TumblingWindowInfo(3);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    TumblingWindow tumblingWindow = new TumblingWindow(new ResourceId(
                            parentResourceId, "Tumbling:" + id), inputInfo, groupKey, windowOwner,
                            (TumblingWindowInfo) windowInfo);

                    tumblingWindow.init(aggregateTransformer, tumblingWindow, null);

                    return tumblingWindow;
                }
            };

            PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                    new ResourceId(""), inputInfo, groupAggregateInfo, windowInfo, builder);

            sink = new StreamedSink(partitionedStream, new ResourceId(""));

            // ----------

            List<Tuple> allTuples = new LinkedList<Tuple>();

            WrappedCustomCollection<Tuple> adds =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 1l, 7}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 2l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 3l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"A", 4l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 5l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 6l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 7l, 6}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"B", 8l, 6}));

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), "Tumbling"));

            source.sendNewTuples(context, adds);

            List<Object[]> expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"A", 1l, 7});
            expectedResults.add(new Object[]{"A", 3l, 6});
            expectedResults.add(new Object[]{"A", 4l, 6});
            expectedResults.add(new Object[]{"B", 2l, 6});
            expectedResults.add(new Object[]{"B", 5l, 6});
            expectedResults.add(new Object[]{"B", 6l, 6});

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 1);
            verifyCollection(expectedResults, received);

            // ----------

            adds.clear();
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{"C", 9l, 6}));

            allTuples.addAll(adds);

            System.err.println();
            System.err.println("Sending..");
            for (Tuple t : adds) {
                System.err.println(t);
            }

            source.sendNewTuples(context, adds);

            expectedResults = new LinkedList<Object[]>();
            expectedResults.add(new Object[]{"C", 9l, 6});
            expectedResults.add(new Object[]{"B", 7l, 6});
            expectedResults.add(new Object[]{"B", 8l, 6});

            received = collectAndMatchStreamedSink(expectedResults, 1);
            verifyCollection(expectedResults, received);

            // ----------

            for (Tuple t : allTuples) {
                source.sendDeadTuple(context, t);
            }

            expectedResults = new LinkedList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 5);

            // ----------

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
}
