package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.distinct.DistinctFilteredStream;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Oct 10, 2007 Time: 4:20:35 PM
 */

public class DistinctFilteredStreamTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() throws Exception {
        PublicContinuousQuery continuousQuery = null;

        try {
            InputTupleInfo inputInfo = new InputTupleInfo(InputTuple.class, new String[]{
                    "columnA"}, new Class[]{Integer.class});

            WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("source"),
                    inputInfo);

            DistinctFilteredStream distinctStream = new DistinctFilteredStream(
                    source.getInternalStream(), new ResourceId("distinct"));

            sources.put("$source", source);
            sink = new StreamedSink(distinctStream, new ResourceId("sink"));

            continuousQuery =
                    new PublicContinuousQuery(master.getAgentService().getName(), "DistinctQry",
                            new Source[]{source}, sink);

            continuousQuery.init(null);
            continuousQuery.start();

            Context context = continuousQuery.getContext();

            // ----------

            LinkedList<Tuple> allTuples = new LinkedList<Tuple>();

            WrappedCustomCollection<Tuple> adds =
                    new WrappedCustomCollection(new LinkedList<Tuple>());
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{0}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{1}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{2}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4}));

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            continuousQuery.enqueueInput(new Object[]{adds, true});

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{null});
            expectedResults.add(new Object[]{4});
            expectedResults.add(new Object[]{2});
            expectedResults.add(new Object[]{0});
            expectedResults.add(new Object[]{1});

            System.err.println();
            System.err.println("Pausing to receive..");
            Thread.sleep(1200);
            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 1);
            verifyCollection(expectedResults, received);

            // ----------

            adds.clear();

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{8}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{8}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{null}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{2}));

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4}));
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{4}));

            allTuples.addAll(adds);

            System.err.println("Sending..");
            for (Tuple tuple : adds) {
                System.err.println(tuple);
            }

            continuousQuery.enqueueInput(new Object[]{adds, true});

            expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{null});
            expectedResults.add(new Object[]{4});
            expectedResults.add(new Object[]{2});
            expectedResults.add(new Object[]{8});

            System.err.println();
            System.err.println("Pausing to receive..");
            Thread.sleep(1200);
            received = collectAndMatchStreamedSink(expectedResults, 1);
            verifyCollection(expectedResults, received);

            // ----------

            WrappedCustomCollection<Tuple> deletes =
                    new WrappedCustomCollection(new LinkedList<Tuple>());

            while (allTuples.isEmpty() == false) {
                deletes.clear();

                Tuple t = allTuples.remove();
                deletes.add(t);

                System.err.println();
                System.err.println("Sending..DELETES");
                for (Tuple delT : deletes) {
                    System.err.println(delT);
                }

                continuousQuery.enqueueInput(new Object[]{t, false});

                expectedResults.clear();

                received = collectAndMatchStreamedSink(expectedResults, 1);
                Assert.assertEquals(received.size(), 0, "No results were expected.");
            }

            commonTests();

            //-------------

            Assert.assertEquals(context.getQueryContext().getNextCycleSchedule().size(), 0,
                    "Future unscheduled streams are still present: " +
                            context.getQueryContext().getNextCycleSchedule());

            Assert.assertEquals(context.getQueryContext().getCurrentCycleSchedule().size(), 0,
                    "Current unscheduled streams are still present: " +
                            context.getQueryContext().getCurrentCycleSchedule());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (continuousQuery != null && continuousQuery.hasStopped() == false) {
                continuousQuery.stop();

                continuousQuery.discard();
            }
        }
    }

    public static class InputTupleInfo extends AbstractTupleInfo {
        public InputTupleInfo(Class<InputTuple> containerClass, String[] columnNames,
                              Class[] columnTypes) {
            super(containerClass, columnNames, columnTypes);
        }

        public Tuple createTuple(Number id) {
            return new InputTuple(id);
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

    public static class OutputTupleInfo extends AbstractTupleInfo {
        public OutputTupleInfo(Class<OutputTuple> containerClass, String[] columnNames,
                               Class[] columnTypes) {
            super(containerClass, columnNames, columnTypes);
        }

        public Tuple createTuple(Number id) {
            return new OutputTuple(id);
        }
    }

    public static class OutputTuple extends TrackedTuple {
        public OutputTuple(Number id) {
            super(id);
        }

        public OutputTuple(Number id, Object[] columns) {
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
            Object[] sentData = (Object[]) object;
            boolean isNew = (Boolean) sentData[1];

            // Only one source is present.
            if (isNew) {
                if (sentData[0] instanceof Collection) {
                    AppendOnlyQueue<Tuple> tuples = new AppendOnlyQueue<Tuple>();
                    tuples.addAll((Collection<Tuple>) sentData[0]);

                    sources[0].sendNewTuples(context, tuples);
                }
                else {
                    InputTuple inputTuple = (InputTuple) sentData[0];
                    sources[0].sendNewTuple(context, inputTuple);
                }
            }
            else {
                InputTuple inputTuple = (InputTuple) sentData[0];

                ((WithdrawableSource) sources[0]).sendDeadTuple(context, inputTuple);
            }
        }
    }
}