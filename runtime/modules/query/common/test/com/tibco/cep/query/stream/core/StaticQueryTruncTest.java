package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.SourceImpl;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.TruncatedBridge;
import com.tibco.cep.query.stream.truncate.TruncatedStream;
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

public class StaticQueryTruncTest extends AbstractTest {
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

        TruncatedBridge bridge = new TruncatedBridge(source.getInternalStream(), new ResourceId(
                "Bridge"));

        TruncatedStream truncatedStream =
                new TruncatedStream(new ResourceId("Truncater"), tupleInfo, 5, 2);

        sink = new StaticSink(truncatedStream, new ResourceId("Sink"));

        bridge.setup(truncatedStream);

        Query query = new Query("region-x", new ResourceId("LimitQry"), new Source[]{source}, sink);

        query.init(null);
        query.start();

        try {
            LinkedList<EmployeeEventTuple> allEmployees = new LinkedList<EmployeeEventTuple>();

            int total = 100;
            for (int i = 1; i <= total; i++) {
                if (bridge.canContinueStreamInput() == false) {
                    System.out.println("Stop request received. Stopping event pump.");
                    break;
                }

                String name = "A";
                int age = i;

                Employee e = new Employee(name, age);
                EmployeeEventTuple tuple = new EmployeeEventTuple(SimpleIdGenerator.generateNewId(),
                        new Object[]{e});
                query.enqueueInput(tuple);

                System.out.println("Sent: " + tuple);

                allEmployees.add(tuple);

                // Give sufficient time for tuple to get picked up.
                Thread.sleep(1000);
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

            Assert.assertEquals(allEmployees.size(), truncatedStream.getFirstX(query.getContext())
                    + truncatedStream.getFirstXOffset(query.getContext()),
                    "Truncated Bridge did not provide feedback correctly,");

            for (int i = 0; i < truncatedStream.getFirstXOffset(query.getContext()); i++) {
                allEmployees.removeFirst();
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
