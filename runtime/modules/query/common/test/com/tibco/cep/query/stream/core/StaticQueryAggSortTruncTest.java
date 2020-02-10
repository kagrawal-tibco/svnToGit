package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.aggregate.AverageAggregator;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.SourceImpl;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.query.snapshot.SortedBridge;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortItemInfo;
import com.tibco.cep.query.stream.truncate.TruncatedStream;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.FixedKeys;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Nov 19, 2007 Time: 11:16:23 AM
 */

public class StaticQueryAggSortTruncTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() throws Exception {
        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                new LinkedHashMap<String, GroupAggregateItemInfo>();

        itemInfos.put("name", new GroupAggregateItemInfo(new GroupItemInfo(
                new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                          Tuple tuple) {
                        Employee event = (Employee) tuple.getColumn(0);
                        Object result = null;
                        return event.getName();
                    }
                })));
        itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                CountAggregator.CREATOR, new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                Employee event = (Employee) tuple.getColumn(0);
                return event.getName();
            }
        })));
        itemInfos.put("avg", new GroupAggregateItemInfo(new AggregateItemInfo(
                AverageAggregator.CREATOR, new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                Employee event = (Employee) tuple.getColumn(0);
                return event.getAge();
            }
        })));

        // ----------

        TupleValueExtractor[] extractors = new DefaultTupleValueExtractor[1];
        extractors[0] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                // Name.
                return tuple.getColumn(0);
            }
        };

        List<Comparator<Object>> comparators = new ArrayList<Comparator<Object>>(extractors.length);
        comparators.add(new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 == FixedKeys.NULL) {
                    if (o2 == FixedKeys.NULL) {
                        return 0;
                    }

                    // Nulls first.
                    return -1;
                }
                else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                String thisVal = (String) o1;
                String anotherVal = (String) o2;

                return thisVal.compareTo(anotherVal);
            }
        });

        // ----------

        String[] columnNames = new String[]{"employeeEvent"};
        Class[] columnTypes = new Class[]{Employee.class};
        final TupleInfo tupleInfo = new AbstractTupleInfo(EmployeeEventTuple.class, columnNames,
                columnTypes) {
            public Tuple createTuple(Number id) {
                return new EmployeeEventTuple(id);
            }
        };
        Source source = new SourceImpl(new ResourceId("Employees"), tupleInfo);
        sources.put("source", source);

        // ----------

        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

        columnNames = new String[]{"name", "count", "avg"};
        columnTypes = new Class[]{String.class, Integer.class, Double.class};
        final TupleInfo outputTupleInfo =
                new AbstractTupleInfo(OutputTuple.class, columnNames, columnTypes) {
                    public Tuple createTuple(Number id) {
                        return new OutputTuple(id);
                    }
                };

        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey,
                                       AggregateInfo aggregateInfo,
                                       WindowInfo windowInfo,
                                       WindowOwner windowOwner,
                                       GroupAggregateTransformer aggregateTransformer) {
                Window window = new SimpleAggregatedStreamWindow(new ResourceId(parentResourceId,
                        "SimpleWindow:" + id), tupleInfo, groupKey, windowOwner);

                AggregatedStream aggregatedStream = new AggregatedStream(window, new ResourceId(
                        parentResourceId, "Aggregate:" + id), aggregateInfo, true);

                window.init(aggregateTransformer, aggregatedStream, null);

                return window;
            }
        };

        AggregatedPartitionedStream partitionedStream = new AggregatedPartitionedStream(source
                .getInternalStream(), new ResourceId("EmployeeWatcher"), outputTupleInfo,
                groupAggregateInfo, builder);

        // ----------

        SortInfo info = new SortInfo(new SortItemInfo[]{new SortItemInfo(false)});
        Bridge bridge = new SortedBridge(partitionedStream, new ResourceId("Bridge"), info,
                extractors, comparators);

        TruncatedStream truncatedStream = new TruncatedStream(new ResourceId("Truncate"),
                partitionedStream.getOutputInfo(), 2, 2);

        bridge.setup(truncatedStream);

        sink = new StaticSink(truncatedStream, new ResourceId("Sink"));

        Query query = new Query(master.getAgentService().getName(), new ResourceId("AgeCounterQry"),
                new Source[]{source}, sink);

        query.init(null);
        query.start();

        try {
            Set<EmployeeEventTuple> allEmployees = new HashSet<EmployeeEventTuple>();

            int total = 1000;
            String[] names = {"A", "B", "C", "D"};
            for (int i = 0; i < total; i++) {
                String name = names[i % names.length];
                int age = 25 + i;

                Employee e = new Employee(name, age);
                EmployeeEventTuple tuple = new EmployeeEventTuple(SimpleIdGenerator.generateNewId(),
                        new Object[]{e});
                query.enqueueInput(tuple);

                allEmployees.add(tuple);
            }

            waitForQueryToComplete(query);

            query.pause();
            //Schedule Flusher here.
            Stream flusher = bridge.getFlusher();
            query.getContext().getQueryContext().addStreamForNextCycle(flusher);
            query.resume();
            query.ping();
            //Let Flusher get invoked.
            waitForQueryToComplete(query);

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{"B", 250, 524.0});
            expectedResults.add(new Object[]{"A", 250, 523.0});

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStaticSink(expectedResults, true, 10);
            verifyCollection(expectedResults, received);

            // ----------

            for (Object tuple : received.keySet()) {
                if (tuple instanceof TrackedTuple) {
                    TrackedTuple trackedTuple = (TrackedTuple) tuple;

                    Assert.assertEquals(trackedTuple.getRefCount(), 1, "Ref count wrong: " + tuple);
                }
            }

            for (Iterator<? extends Tuple> iter = OutputTuple.tuples.iterator(); iter.hasNext();) {
                Tuple tuple = iter.next();

                if (tuple.getRawColumns() == null) {
                    iter.remove();
                }
            }

            Assert.assertEquals(OutputTuple.tuples.size(), 4,
                    "Some output Tuples are still non-zero.");

            Set<Tuple> exceptions = new HashSet<Tuple>(received.keySet());
            exceptions.addAll(OutputTuple.tuples);

            exceptions.addAll(allEmployees);

            commonTests(exceptions);
        }
        finally {
            query.stop();
        }
    }

    // ----------

    public static class Employee {
        protected final String name;

        protected final int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

    public static class EmployeeEventTuple extends TrackedTuple {
        public EmployeeEventTuple(Number id) {
            super(id);
        }

        public EmployeeEventTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class OutputTuple extends TrackedTuple {
        static final LinkedList<OutputTuple> tuples = new LinkedList<OutputTuple>();

        public OutputTuple(Number id) {
            super(id);

            tuples.addLast(this);
        }

        public OutputTuple(Number id, Object[] columns) {
            super(id, columns);

            tuples.addLast(this);
        }
    }

    public static class Query extends ContinuousQueryImpl {
        public Query(String regionName, ResourceId resourceId, Source[] sources, Sink sink) {
            super(regionName, resourceId, sources, sink, new AsyncProcessListener() {
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
