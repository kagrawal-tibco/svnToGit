package com.tibco.cep.query.stream.rete.perf;

import com.tibco.cep.query.exec.prebuilt.HeavyReteEntities;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.aggregate.NullsSmallestMaxAggregator;
import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.HeavyTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteCQAddEvtSlidWinAggrPerfTest extends AbstractCacheTest {
    protected final String[] lastNames = {"Clarke", "Baxter", "Asimov", "Brin", "Le Guin"};
    protected int personEventId;
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        personEventId = classToTypeMap.get("be.gen.PersonEvent");
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
            Manager.ManagerInput managerInput = (Manager.ManagerInput) master.getProperties()
                    .get(Manager.ManagerInput.KEY_INPUT);

            ClassLoader loader = getEntityClassLoader();
            Thread.currentThread().setContextClassLoader(loader);

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
        Class personEventClass = Class.forName("be.gen.PersonEvent");

        // ----------

        String[] columnNames = new String[]{"personEvent"};
        Class[] columnTypes = new Class[]{personEventClass};
        final ReteEntityInfo reteEntityInfo =
                new HeavyReteEntities.HeavyReteEntityInfo(columnNames, columnTypes, 1);

        ReteEntitySource source = new ReteEntitySourceImpl(new ResourceId("source"),
                reteEntityInfo, personEventClass);
        sources.put("source1", source);

        // ----------

        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                new LinkedHashMap<String, GroupAggregateItemInfo>();

        itemInfos.put("lastName", new GroupAggregateItemInfo(new GroupItemInfo(
                new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                          Tuple tuple) {
                        Object retVal = null;
                        try {
                            retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("lastName");
                        }
                        catch (NoSuchFieldException e) {
                            e.printStackTrace(System.err);
                        }
                        return retVal;
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
                Object retVal = null;
                try {
                    retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("age");
                }
                catch (NoSuchFieldException e) {
                    e.printStackTrace(System.err);
                }
                return retVal;
            }
        })));

        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

        columnNames = new String[]{"lastName", "count", "maxAge"};
        columnTypes = new Class[]{String.class, Integer.class, Integer.class};
        final TupleInfo outputTupleInfo =
                new AbstractTupleInfo(OutputTuple.class, columnNames, columnTypes) {
                    public Tuple createTuple(Number id) {
                        return new OutputTuple(id);
                    }
                };

        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey,
                                       AggregateInfo aggregateInfo,
                                       WindowInfo windowInfo, WindowOwner windowOwner,
                                       GroupAggregateTransformer aggregateTransformer) {
                SlidingWindow slidingWindow = new SlidingWindow(new ResourceId(parentResourceId,
                        "Sliding:" + id), reteEntityInfo, groupKey, windowOwner,
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

        ReteQueryImpl query = new ReteQueryImpl(master.getAgentService().getName(),
                new ResourceId("SlidingWindowAggregateQry"),
                new ReteEntitySource[]{source}, sink, false, null);

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            query.init(repo, null);
            query.start();

            sendAndMeasure(query, dispatcher, windowInfo.getSize());

            QueryMonitor monitor = Registry.getInstance().getComponent(QueryMonitor.class);
            Collection<String> redTrace =
                    monitor.fetchCacheProcessorTraceOutput(regionManager.getRegionName());
            for (String s : redTrace) {
                System.err.println(s);
                System.err.println();
            }
        }
        finally {
            dispatcher.unregisterAndStopQuery(query);
        }
    }

    private void sendAndMeasure(
            ReteQueryImpl query,
                                ReteEntityDispatcher dispatcher, int windowSize) throws Exception {
        final int totalEvents = 500000;

        System.err.println("Starting query..");
        long queryStartTime = System.currentTimeMillis();

        dispatcher.registerQuery(query);

        System.err.println("Starting to send..");
        eventHelper.addPersonEvents(1, totalEvents);

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

    public static class OutputTuple extends HeavyTuple {
        public OutputTuple(Number id) {
            super(id);
        }

        public OutputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }
}