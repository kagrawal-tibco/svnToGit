package com.tibco.cep.query.stream.core.perf;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.aggregate.NullsSmallestMaxAggregator;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.SourceImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class RawCQAddEvtSlidWinAggrPerfTest extends AbstractTest {
    protected final String[] lastNames = {"Clarke", "Baxter", "Asimov", "Brin", "Le Guin"};

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader loader = getEntityClassLoader();
            Thread.currentThread().setContextClassLoader(loader);

            runTest();

            System.err.println("----- Done warming up -----");
            System.gc();

            runTest();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldCL);
        }
    }

    protected void runTest() throws Exception {
        String[] columnNames = new String[]{"firstName", "lastName", "age"};
        Class[] columnTypes = new Class[]{String.class, String.class, Integer.class};
        final TupleInfo tupleInfo =
                new AbstractTupleInfo(LiteTuple.class, columnNames, columnTypes) {
                    public Tuple createTuple(Number id) {
                        return new LiteTuple(id);
                    }
                };

        Source source = new SourceImpl(new ResourceId("source"), tupleInfo);
        sources.put("source1", source);

        // ----------

        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                new LinkedHashMap<String, GroupAggregateItemInfo>();

        itemInfos.put("lastName", new GroupAggregateItemInfo(new GroupItemInfo(
                new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                          Tuple tuple) {
                        return tuple.getColumn(1);
                    }
                })));
        itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                CountAggregator.CREATOR, new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return "";
            }
        })));
        itemInfos.put("maxAge", new GroupAggregateItemInfo(new AggregateItemInfo(
                NullsSmallestMaxAggregator.CREATOR, new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(2);
            }
        })));

        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

        columnNames = new String[]{"lastName", "count", "maxAge"};
        columnTypes = new Class[]{String.class, Integer.class, Integer.class};
        final TupleInfo outputTupleInfo =
                new AbstractTupleInfo(LiteTuple.class, columnNames, columnTypes) {
                    public Tuple createTuple(Number id) {
                        return new LiteTuple(id);
                    }
                };

        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey,
                                       AggregateInfo aggregateInfo,
                                       WindowInfo windowInfo, WindowOwner windowOwner,
                                       GroupAggregateTransformer aggregateTransformer) {
                SlidingWindow slidingWindow = new SlidingWindow(new ResourceId(parentResourceId,
                        "Sliding:" + id), tupleInfo, groupKey, windowOwner,
                        (SlidingWindowInfo) windowInfo);

                String[] names = new String[]{"count", "maxAge"};
                Class[] types = new Class[]{Integer.class, Integer.class};

                AggregatedStream aggregatedStream = new AggregatedStream(slidingWindow,
                        new ResourceId(parentResourceId, "Aggregate:" + id), aggregateInfo, true);

                slidingWindow.init(aggregateTransformer, aggregatedStream, null);

                return slidingWindow;
            }
        };

        SlidingWindowInfo windowInfo = new SlidingWindowInfo(5);
        PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                new ResourceId("partition"), outputTupleInfo, groupAggregateInfo, windowInfo,
                builder);

        // ----------

        sink = new StreamedSink(partitionedStream, new ResourceId("Sink"));

        // ----------

        CQ query = new CQ(master.getAgentService().getName(),
                new ResourceId("SlidingWindowAggregateQry"), new Source[]{source}, sink);

        try {
            query.init(null);
            query.start();

            sendAndMeasure(query, windowInfo.getSize());
        }
        finally {
            query.stop();
        }
    }

    private void sendAndMeasure(CQ query, int windowSize)
            throws Exception {
        final int totalEvents = 500000;

        System.err.println("Starting query..");
        long queryStartTime = System.currentTimeMillis();

        System.err.println("Starting to send..");
        doAdds(query, 1, totalEvents);

        //----------

        StreamedSink streamedSink = (StreamedSink) sink;
        Tuple marker = streamedSink.getBatchEndMarker();

        long queryEndTime =
                pollAndCollect(lastNames.length, totalEvents, streamedSink, marker, windowSize);

        queryEndTime = queryEndTime - queryStartTime;
        System.err.println(
                "Received. Time: " + queryEndTime + "(millis) for " + totalEvents + " events.");
    }

    private long pollAndCollect(int totalEventTypes, int lastEvent, StreamedSink streamedSink,
                                Tuple marker, int windowSize)
            throws InterruptedException {
        Tuple tuple;
        long endTime = 0;
        HashMap<String, Integer> nameToCount = new HashMap<String, Integer>();

        HashSet<Integer> expectedAges = new HashSet<Integer>();
        for (int i = 0; i < totalEventTypes; i++) {
            expectedAges.add(lastEvent - i);
        }

        while ((tuple = streamedSink.poll(10, TimeUnit.SECONDS)) != null) {
            if (tuple == marker) {
                continue;
            }

            String name = (String) tuple.getColumn(0);
            Integer count = (Integer) tuple.getColumn(1);
            Integer age = (Integer) tuple.getColumn(2);

            if (expectedAges.remove(age)) {
                nameToCount.put(name, count);
            }

            if (expectedAges.size() == 0) {
                endTime = System.currentTimeMillis();
            }
        }

        for (String name : nameToCount.keySet()) {
            Integer i = nameToCount.get(name);

            Assert.assertEquals(i.intValue(), windowSize, "Incorrect window count for: " + name);
        }

        System.err.println("Results: " + nameToCount);
        Assert.assertEquals(expectedAges.size(), 0, "Not all results were received.");

        return endTime;
    }

    protected void doAdds(final CQ query, final int startId, final int lastId) throws Exception {
        Thread t = new Thread() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                for (int i = startId; i <= lastId; i++) {
                    Long id = SimpleIdGenerator.generateNewId();

                    Integer age = i;
                    String fn = "fn: " + i;
                    String ln = lastNames[i % lastNames.length];

                    LiteTuple event = new LiteTuple(id, new Object[]{fn, ln, age});

                    try {
                        query.enqueueInput(event);
                    }
                    catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                }

                long endTime = System.currentTimeMillis() - startTime;
                System.err.println(
                        "Sent. Time: " + endTime + "(millis) for " + (1 + lastId - startId) +
                                " events.");
            }
        };

        t.start();
    }

    //----------

    public static class CQ extends ContinuousQueryImpl {
        public CQ(String regionName, ResourceId resourceId, Source[] sources, Sink sink) {
            super(regionName, resourceId, sources, sink, new AsyncProcessListener() {
                public void beforeStart() {
                }

                public void afterEnd() {
                }
            });
        }

        protected void handleQueuedInput(Object object) throws Exception {
            sources[0].sendNewTuple(context, (Tuple) object);
        }
    }
}