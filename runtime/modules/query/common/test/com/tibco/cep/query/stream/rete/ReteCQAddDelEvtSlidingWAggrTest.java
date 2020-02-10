package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.EventHelper;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.aggregate.NullsSmallestMaxAggregator;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSource;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSourceImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Mar 12, 2008 Time: 12:05:18 PM
 */

public class ReteCQAddDelEvtSlidingWAggrTest extends AbstractCacheTest {

    private int windowEventId;

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        windowEventId = classToTypeMap.get("be.gen.Events.WindowEvent1");
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader loader = getEntityClassLoader();
            Thread.currentThread().setContextClassLoader(loader);

            runTest();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldCL);
        }
    }

    protected void runTest() throws Exception {

        // ----------

        String[] columnNames = new String[]{"event"};
        Class[] columnTypes = new Class[]{windowEventClass};
        final ReteEntityInfo reteEntityInfo = new ReteEntityInfoImpl(EventHelper.EventTuple.class, columnNames,
                columnTypes);

        ReteEntityWithdrawableSource source = new ReteEntityWithdrawableSourceImpl(new ResourceId(
                "source"), reteEntityInfo, windowEventClass);
        sources.put("source1", source);

        // ----------

        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                new LinkedHashMap<String, GroupAggregateItemInfo>();

        itemInfos.put("prop1", new GroupAggregateItemInfo(new GroupItemInfo(
                new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(
                            GlobalContext globalContext, QueryContext queryContext,
                            Tuple tuple) {
                        Object retVal = null;
                        try {
                            retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("prop1");
                        }
                        catch(NoSuchFieldException e) {
                            e.printStackTrace(System.err);
                        }
                        return retVal;
                    }
                })));
        itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                CountAggregator.CREATOR, new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(
                            GlobalContext globalContext, QueryContext queryContext,
                            Tuple tuple) {
                        Object retVal = null;
                        try {
                            retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("prop2");
                        }
                        catch(NoSuchFieldException e) {
                            e.printStackTrace(System.err);
                        }
                        return retVal;
                    }
                })));
        itemInfos.put("max", new GroupAggregateItemInfo(new AggregateItemInfo(
                NullsSmallestMaxAggregator.CREATOR, new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(
                            GlobalContext globalContext, QueryContext queryContext,
                            Tuple tuple) {
                        Object retVal = null;
                        try {
                            retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("prop3");
                        }
                        catch(NoSuchFieldException e) {
                            e.printStackTrace(System.err);
                        }
                        return retVal;
                    }
                })));

        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

        columnNames = new String[]{"prop1", "count", "max"};
        columnTypes = new Class[]{String.class, Integer.class, Double.class};
        final TupleInfo outputTupleInfo =
                new AbstractTupleInfo(EventHelper.OutputTuple.class, columnNames, columnTypes) {
                    public Tuple createTuple(Number id) {
                        return new EventHelper.OutputTuple(id);
                    }
                };

        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(
                    ResourceId parentResourceId, String id, GroupKey groupKey,
                    AggregateInfo aggregateInfo,
                    WindowInfo windowInfo,
                    WindowOwner windowOwner,
                    GroupAggregateTransformer aggregateTransformer) {
                SlidingWindow slidingWindow = new SlidingWindow(new ResourceId(parentResourceId,
                        "Sliding:" + id), reteEntityInfo, groupKey, windowOwner,
                        (SlidingWindowInfo) windowInfo);

                String[] names = new String[]{"count", "max"};
                Class[] types = new Class[]{Integer.class, Double.class};

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

        ReteQuery query = getReteQuery("EventQry", new ReteEntitySource[]{source});

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            query.init(repo, null);

            dispatcher.registerQuery(query);
            query.start();

            List<Entity> entities = eventHelper.addWindowEvents();

            Thread.sleep(8 * 1000);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults,
                            10);

            LinkedList<Tuple> lastSet = new LinkedList<Tuple>(received.keySet());
            while(lastSet.size() > 5) {
                lastSet.removeFirst();
            }

            Tuple tuple = lastSet.get(0);
            Assert.assertTrue(Arrays
                    .equals(tuple.getRawColumns(), new Object[]{"Clarke", 5, 45}),
                    "Values do not match: " + tuple);

            tuple = lastSet.get(1);
            Assert.assertTrue(Arrays
                    .equals(tuple.getRawColumns(), new Object[]{"Baxter", 5, 46}),
                    "Values do not match: " + tuple);

            tuple = lastSet.get(2);
            Assert.assertTrue(Arrays
                    .equals(tuple.getRawColumns(), new Object[]{"Asimov", 5, 47}),
                    "Values do not match: " + tuple);

            tuple = lastSet.get(3);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Brin", 5, 48}),
                    "Values do not match: " + tuple);

            tuple = lastSet.get(4);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(),
                    new Object[]{"Le Guin", 5, 49}), "Values do not match: " + tuple);

            // ----------

            System.err.println("==============");
            System.err.println("Sending deletes and modifications...");

            eventHelper.deleteAndReinsertEvents(windowEventId, entities);

            Thread.sleep(8 * 1000);

            expectedResults = new ArrayList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 10);

            lastSet = new LinkedList<Tuple>(received.keySet());

            Assert.assertEquals(lastSet.size(), 1, "Wrong number of results");

            tuple = lastSet.get(0);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(),
                    new Object[]{"Le Guin", 5, 49}), "Values do not match: " + tuple);
        }
        finally {
            query.stop();
        }
    }

}