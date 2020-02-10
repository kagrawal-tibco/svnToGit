package com.tibco.cep.query.stream.rete;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateItemInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.query.snapshot.SortedBridge;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortItemInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteSSAddEvtAggrTest extends AbstractCacheTest {

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
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

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[])sources, sink, true, null);
    }

    protected void runTest() throws Exception {

        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos = eventHelper.getItemInfos(personEventClass);

        // ----------

        TupleValueExtractor[] extractors = eventHelper.getPersonEventTupleValueExtractors();
        List<Comparator<Object>> comparators = eventHelper.getPersonEventComparators();

        // ----------

        final ReteEntityInfo reteEntityInfo = eventHelper.getReteEntityInfo(personEventClass);
        ReteEntitySource source = new ReteEntitySourceImpl(new ResourceId("source"),
                reteEntityInfo, personEventClass);
        sources.put("source1", source);

        // ----------
        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);
        final TupleInfo outputTupleInfo = eventHelper.getTupleInfo(personEventClass);
        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(
                    ResourceId parentResourceId, String id, GroupKey groupKey,
                    AggregateInfo aggregateInfo,
                    WindowInfo windowInfo, WindowOwner windowOwner,
                    GroupAggregateTransformer aggregateTransformer) {
                Window window = new SimpleAggregatedStreamWindow(new ResourceId(parentResourceId,
                        "SimpleWindow:" + id), reteEntityInfo, groupKey,
                        windowOwner);

                String[] names = new String[]{"count", "sum", "avg", "min", "max"};
                Class[] types = new Class[]{Integer.class, Integer.class, Double.class,
                        Integer.class, Integer.class};

                AggregatedStream aggregatedStream = new AggregatedStream(window, new ResourceId(
                        parentResourceId, "Aggregate:" + id), aggregateInfo, true);

                window.init(aggregateTransformer, aggregatedStream, null);

                return window;
            }
        };

        AggregatedPartitionedStream partitionedStream = new AggregatedPartitionedStream(source
                .getInternalStream(), new ResourceId("aggregate-partition"), outputTupleInfo,
                groupAggregateInfo, builder);

        // ----------
        SortInfo sortInfo = new SortInfo(new SortItemInfo[]{new SortItemInfo(true),
                new SortItemInfo(false)});
        Bridge bridge = new SortedBridge(partitionedStream, new ResourceId("bridge"), sortInfo,
                extractors, comparators);
        sink = new StaticSink(new ResourceId("Sink"), partitionedStream.getOutputInfo());
        bridge.setup(sink.getInternalStream());
        // ----------

        ReteQuery query = getReteQuery("AgeCounterQry", new ReteEntitySource[]{source});
        SnapshotQueryManager ssQueryManager = regionManager.getSSQueryManager();
        try {
            query.init(repo, null);
            query.start();

            List<Entity> entities = eventHelper.addPersonEvents(new String[]{
                    "Clarke", "Baxter", "Asimov", "Brin", "Le Guin"}, 43, 0, 50777);

            ssQueryManager.registerAndStartFeeding(query, bridge);

            waitForQueryToComplete(query);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStaticSink(expectedResults, 10);

            LinkedList<Tuple> lastSet = new LinkedList<Tuple>(received.keySet());
            while (lastSet.size() > 5) {
                lastSet.removeFirst();
            }

            Tuple tuple = lastSet.get(0);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Le Guin", 10155,
                    436665, 43.0, 43, 43}), "Values do not match: " + tuple);

            tuple = lastSet.get(1);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Brin", 10155,
                    436665, 43.0, 43, 43}), "Values do not match: " + tuple);

            tuple = lastSet.get(2);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Asimov", 10155,
                    436665, 43.0, 43, 43}), "Values do not match: " + tuple);

            tuple = lastSet.get(3);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Clarke", 10156,
                    436708, 43.0, 43, 43}), "Values do not match: " + tuple);

            tuple = lastSet.get(4);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Baxter", 10156,
                    436708, 43.0, 43, 43}), "Values do not match: " + tuple);
        }
        finally {
            query.stop();
        }
    }
}
