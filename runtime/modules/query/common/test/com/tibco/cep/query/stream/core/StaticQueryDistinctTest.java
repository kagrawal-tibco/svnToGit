package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.distinct.DistinctFilteredStream;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.SourceImpl;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.query.snapshot.SimpleBridge;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Nov 19, 2007 Time: 11:16:23 AM
 */

public class StaticQueryDistinctTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() throws Exception {
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

        Bridge bridge = new SimpleBridge(source.getInternalStream(), new ResourceId("Bridge"));

        DistinctFilteredStream distinctStream =
                new DistinctFilteredStream(new ResourceId("Distinct"), tupleInfo);

        sink = new StaticSink(distinctStream, new ResourceId("Sink"));

        bridge.setup(distinctStream);

        Query query = new Query(master.getAgentService().getName(),
                new ResourceId("DistinctNameQry"), new Source[]{source}, sink);

        query.init(null);
        query.start();

        try {
            LinkedList<EmployeeEventTuple> allEmployees = new LinkedList<EmployeeEventTuple>();

            int total = 12;
            // See Employee's hashCode method.
            String[] names = {"A", "B", "C", "D"};
            for (int i = 1; i <= total; i++) {
                String name = names[i % names.length];
                int age = i;

                Employee e = new Employee(name, age);
                EmployeeEventTuple tuple = new EmployeeEventTuple(SimpleIdGenerator.generateNewId(),
                        new Object[]{e});
                query.enqueueInput(tuple);

                System.out.println("Sent: " + tuple);

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

            while (allEmployees.size() > names.length) {
                allEmployees.removeLast();
            }

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            for (EmployeeEventTuple eventTuple : allEmployees) {
                expectedResults.add(eventTuple.getRawColumns());
            }

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

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Age: " + age;
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
