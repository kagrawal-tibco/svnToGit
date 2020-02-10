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

public class TimeWindowLoopTest extends AbstractTest {
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

            final int windowTimeoutSec = 5;

            TimeWindowInfo windowInfo = new TimeWindowInfo(Integer.MAX_VALUE,
                    windowTimeoutSec * 1000);

            WindowBuilder builder = new WindowBuilder() {
                public Window buildAndInit(ResourceId parentResourceId, String id,
                                           GroupKey groupKey, AggregateInfo aggregateInfo,
                                           WindowInfo windowInfo, WindowOwner windowOwner,
                                           GroupAggregateTransformer aggregateTransformer) {
                    TimeWindow timeWindow = new TimeWindow(new ResourceId(parentResourceId, "Time:"
                            + id), inputInfo, groupKey, windowOwner, (TimeWindowInfo) windowInfo);

                    DSubStream dSubStream = new DSubStream(timeWindow,
                            new ResourceId(parentResourceId, "DStream:" + id));

                    timeWindow.init(aggregateTransformer, dSubStream, null);

                    return timeWindow;
                }
            };

            PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                    new ResourceId(""), inputInfo, groupAggregateInfo, windowInfo, builder);

            sink = new StreamedSink(partitionedStream, new ResourceId(""));

            PublicContinuousQuery continuousQuery =
                    new PublicContinuousQuery(master.getAgentService().getName(), "TimeWindowQry",
                            new Source[]{source}, sink);

            continuousQuery.init(null);
            continuousQuery.start();

            // ----------

            for (int k = 0; k < 10; k++) {
                List<Tuple> allTuples = new LinkedList<Tuple>();

                System.err.println("Sending..");
                for (long l = 0; l < 1000; l++) {
                    Tuple t = new InputTuple(SimpleIdGenerator.generateNewId(),
                            new Object[]{"A", l, -1});

                    continuousQuery.enqueueInput(t);
                    allTuples.add(t);
                }
                System.err.println("Sent.");

                Thread.sleep(windowTimeoutSec * 1000);

                LinkedList<Object[]> expectedResults = new LinkedList<Object[]>();
                for (Tuple t : allTuples) {
                    expectedResults.add(t.getRawColumns());
                }

                LinkedIdentityHashMap<Tuple, Object[]> received =
                        collectAndMatchStreamedSink(expectedResults,
                                /* Just 1 sec leeway. */1);
                verifyCollection(expectedResults, received);

                Assert.assertEquals(received.size(), allTuples.size(),
                        "Not all tuples were received.");
            }

            // ----------

            continuousQuery.stop();
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
            sources[0].sendNewTuple(context, (Tuple) object);
        }
    }
}
