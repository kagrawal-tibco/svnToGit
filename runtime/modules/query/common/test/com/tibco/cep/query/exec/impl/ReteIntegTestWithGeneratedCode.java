package com.tibco.cep.query.exec.impl;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.service.QueryServiceProvider;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.service.QueryStatement;
import com.tibco.cep.query.service.impl.QueryServiceProviderImpl;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.HeavyReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.HeavyTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteIntegTestWithGeneratedCode extends AbstractCacheTest {
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

    protected void runTest() throws Exception {

//
//        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos = new LinkedHashMap<String, GroupAggregateItemInfo>();
//
//        itemInfos.put("lastName", new GroupAggregateItemInfo(new GroupItemInfo(
//                new TupleValueExtractor() {
//                    @Override
//                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
//                            Tuple tuple) {
//                        SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
//                        Object result = null;
//                        try {
//                            result = event.getProperty("lastName");
//                        }
//                        catch (NoSuchFieldException e) {
//                            e.printStackTrace(System.err);
//                        }
//                        return result;
//                    }
//                })));
//        itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
//                CountAggregator.CREATOR, new TupleValueExtractor() {
//                    @Override
//                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
//                            Tuple tuple) {
//                        SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
//                        Object result = null;
//                        try {
//                            result = event.getProperty("age");
//                        }
//                        catch (NoSuchFieldException e) {
//                            e.printStackTrace(System.err);
//                        }
//                        return result;
//                    }
//                })));
//        itemInfos.put("sum", new GroupAggregateItemInfo(new AggregateItemInfo(
//                SumAggregator.CREATOR, new TupleValueExtractor() {
//                    @Override
//                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
//                            Tuple tuple) {
//                        SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
//                        Object result = null;
//                        try {
//                            result = event.getProperty("age");
//                        }
//                        catch (NoSuchFieldException e) {
//                            e.printStackTrace(System.err);
//                        }
//                        return result;
//                    }
//                })));
//        itemInfos.put("avg", new GroupAggregateItemInfo(new AggregateItemInfo(
//                AverageAggregator.CREATOR, new TupleValueExtractor() {
//                    @Override
//                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
//                            Tuple tuple) {
//                        SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
//                        Object result = null;
//                        try {
//                            result = event.getProperty("age");
//                        }
//                        catch (NoSuchFieldException e) {
//                            e.printStackTrace(System.err);
//                        }
//                        return result;
//                    }
//                })));
//        itemInfos.put("min", new GroupAggregateItemInfo(new AggregateItemInfo(
//                NullsLargestMinAggregator.CREATOR, new TupleValueExtractor() {
//                    @Override
//                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
//                            Tuple tuple) {
//                        SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
//                        Object result = null;
//                        try {
//                            result = event.getProperty("age");
//                        }
//                        catch (NoSuchFieldException e) {
//                            e.printStackTrace(System.err);
//                        }
//                        return result;
//                    }
//                })));
//        itemInfos.put("max", new GroupAggregateItemInfo(new AggregateItemInfo(
//                NullsSmallestMaxAggregator.CREATOR, new TupleValueExtractor() {
//                    @Override
//                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
//                            Tuple tuple) {
//                        SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
//                        Object result = null;
//                        try {
//                            result = event.getProperty("age");
//                        }
//                        catch (NoSuchFieldException e) {
//                            e.printStackTrace(System.err);
//                        }
//                        return result;
//                    }
//                })));
//
//        // ----------
//
//        TupleValueExtractor[] extractors = new TupleValueExtractor[1];
//        extractors[0] = new TupleValueExtractor() {
//            @Override
//            public Object extract(GlobalContext globalContext, QueryContext queryContext,
//                    Tuple tuple) {
//                // Count column.
//                return tuple.getColumn(1);
//            }
//        };
//
//        List<Comparator<Object>> comparators = new ArrayList<Comparator<Object>>(extractors.length);
//        comparators.add(new Comparator<Object>() {
//            public int compare(Object o1, Object o2) {
//                if (o1 == FixedKeys.NULL) {
//                    if (o2 == FixedKeys.NULL) {
//                        return 0;
//                    }
//
//                    // Nulls first.
//                    return -1;
//                }
//                else if (o2 == FixedKeys.NULL) {
//                    return 1;
//                }
//
//                int thisVal = (Integer) o1;
//                int anotherVal = (Integer) o2;
//
//                return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
//            }
//        });
//
//        // ----------
//
//        String[] columnNames = new String[] { "personEvent" };
//        Class[] columnTypes = new Class[] { personEventClass };
//        final ReteEntityInfo reteEntityInfo = new ReteEntityInfo(PersonEventTuple.class,
//                columnNames, columnTypes);
//        ReteEntityWithdrawableSource source = new ReteEntityWithdrawableSourceImpl(new ResourceId(
//                "source"), reteEntityInfo, personEventClass);
//        sources.put("source1", source);
//
//        // ----------
//
//        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);
//
//        columnNames = new String[] { "lastName", "count", "sum", "avg", "min", "max" };
//        columnTypes = new Class[] { String.class, Integer.class, Double.class, Double.class,
//                Integer.class, Integer.class };
//        final TupleInfo outputTupleInfo = new TupleInfo(OutputTuple.class, columnNames, columnTypes);
//
//        WindowBuilder builder = new WindowBuilder() {
//            public Window buildAndInit(ResourceId parentResourceId, String id, int groupHash,
//                    Object[] groupColumns, AggregateInfo aggregateInfo, WindowInfo windowInfo,
//                    WindowOwner windowOwner, GroupAggregateTransformer aggregateTransformer) {
//                Window window = new SimpleAggregatedStreamWindow(new ResourceId(parentResourceId,
//                        "SimpleWindow:" + id), reteEntityInfo, groupHash, groupColumns, windowOwner);
//
//                String[] names = new String[] { "count", "sum", "avg", "min", "max" };
//                Class[] types = new Class[] { Integer.class, Double.class, Double.class,
//                        Integer.class, Integer.class };
//                AbstractTupleInfo aggregateTupleInfo = new TupleInfo(InternalAggregateTuple.class,
//                        names, types);
//
//                AggregatedStream aggregatedStream = new AggregatedStream(window, new ResourceId(
//                        parentResourceId, "Aggregate:" + id), aggregateTupleInfo, aggregateInfo,
//                        true);
//
//                window.init(aggregateTransformer, aggregatedStream, null);
//
//                return window;
//            }
//        };
//
//        AggregatedPartitionedStream partitionedStream = new AggregatedPartitionedStream(source
//                .getInternalStream(), new ResourceId("aggregate-partition"), outputTupleInfo,
//                groupAggregateInfo, builder);
//
//        // ----------
//
//        SortInfo sortInfo = new SortInfo(new SortItemInfo[] { new SortItemInfo(true),
//                new SortItemInfo(true), new SortItemInfo(true) });
//        SortedStream sortedStream = new SortedStream(partitionedStream, new ResourceId("sort"),
//                sortInfo, extractors, comparators);
//
//        // ----------

//        sink = new StreamedSink(sortedStream, new ResourceId("Sink"));


        final String rspName = "test";
        final String ruleSessionName = "BAR1";
        final String repoUrl = "E:\\perforce\\dev\\be\\orion\\query\\testprojects\\CustomOM.ear";
        final String traFile =
                "E:\\perforce\\dev\\be\\orion\\query\\testprojects\\Model01\\be-engine.tra";

        final Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);
        final RuleServiceProvider rsp =
                RuleServiceProviderManager.getInstance().newProvider(rspName, env);
        rsp.configure(RuleServiceProvider.MODE_API);
        final QueryServiceProvider qsp = new QueryServiceProviderImpl(rsp);
        final QuerySession qs = qsp.getQuerySession(ruleSessionName);
        final Query q = qs.createQuery(
                "query",
                "select e.lastName, count(1), sum(e.age), avg(e.age), min(e.age), max(e.age) "
                        + "from \"/PersonEvent\" e group by e.lastName order by count(1)");
        final QueryStatement statement = q.createStatement();

        final QueryExecutionPlan ep = statement.getExecutionPlan();
        sink = ep.getSink();
        final ReteEntitySource source =
                (ReteEntitySource) ep.getSources().values().iterator().next();


        ReteQuery query =
                new ReteQueryImpl(master.getAgentService().getName(), new ResourceId("AgeCounterQry"),
                        new ReteEntitySource[]{source}, sink, true, null);

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            query.init(repo, null);

            dispatcher.registerQuery(query);
            query.start();

            Class personEventClass = Class.forName("be.gen.PersonEvent");
            List<Entity> entities = eventHelper.addPersonEvents(0, 50);

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults,
                            10);

            LinkedList<Tuple> lastSet = new LinkedList<Tuple>(received.keySet());
            while (lastSet.size() > 4) {
                lastSet.removeFirst();
            }

            Tuple tuple = lastSet.get(0);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Asimov", 12,
                    300.0, 25.0, 25, 25}), "Values do not match: " + tuple);

            tuple = lastSet.get(1);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Brin", 12,
                    300.0, 25.0, 25, 25}), "Values do not match: " + tuple);

            tuple = lastSet.get(2);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Clarke", 13,
                    325.0, 25.0, 25, 25}), "Values do not match: " + tuple);

            tuple = lastSet.get(3);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Baxter", 13,
                    325.0, 25.0, 25, 25}), "Values do not match: " + tuple);

            // ----------

            System.err.println("Sending deletes...");

            removeEntities(entities);

            expectedResults = new ArrayList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 10);

            lastSet = new LinkedList<Tuple>(received.keySet());
            while (lastSet.size() > 2) {
                lastSet.removeFirst();
            }

            tuple = lastSet.get(0);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Baxter", 1,
                    25.0, 25.0, 25, 25}), "Values do not match: " + tuple);

            tuple = lastSet.get(1);
            Assert.assertTrue(Arrays.equals(tuple.getRawColumns(), new Object[]{"Baxter", 0, 0.0,
                    0.0, null, null}), "Values do not match: " + tuple);
        }
        finally {
            query.stop();
        }
    }

    protected void removeEntities(List<Entity> entities) throws Exception {
        for (Entity entity : entities) {
            masterCache.remove(new Long(entity.getId()));
        }
    }

    public static class PersonEventTuple extends HeavyReteEntity {
        public PersonEventTuple(Long id) {
            super(id);
        }
    }

    public static class OutputTuple extends HeavyTuple {
        public OutputTuple(Long id) {
            super(id);
        }

        public OutputTuple(Long id, Object[] columns) {
            super(id, columns);
        }
    }
}