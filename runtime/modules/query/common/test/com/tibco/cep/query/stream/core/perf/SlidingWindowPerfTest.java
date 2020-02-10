package com.tibco.cep.query.stream.core.perf;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.WithdrawableSource;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.CustomMaster;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Run;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Step;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.transform.TransformInfo;
import com.tibco.cep.query.stream.transform.TransformItemInfo;
import com.tibco.cep.query.stream.transform.TransformedStream;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/*
 * Author: Ashwin Jayaprakash Date: Nov 12, 2007 Time: 3:49:40 PM
 */

public class SlidingWindowPerfTest {
    public static void main(String[] args) throws Exception {
        final TupleInfo inputInfo = new AbstractTupleInfo(InputTuple.class, new String[]{"columnA",
                "columnB", "startTime"},
                new Class[]{Integer.class, Long.class, Timestamp.class}) {
            public Tuple createTuple(Number id) {
                return new InputTuple(id);
            }
        };

        final TupleInfo outputInfo =
                new AbstractTupleInfo(OutputTuple.class, new String[]{"columnA",
                        "columnB", "startTime", "endTime"}, new Class[]{Integer.class, Long.class,
                        Timestamp.class, Timestamp.class}) {
                    public Tuple createTuple(Number id) {
                        return new OutputTuple(id);
                    }
                };

        // ----------

        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                new LinkedHashMap<String, GroupAggregateItemInfo>();

        // Complex expression as a Group column.
        itemInfos.put("columnA", new GroupAggregateItemInfo(new GroupItemInfo(
                new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                          Tuple tuple) {
                        return tuple.getColumn(0);
                    }
                })));

        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

        // ----------

        final WithdrawableSource source = new WithdrawableSourceImpl(null, inputInfo);

        SlidingWindowInfo windowInfo = new SlidingWindowInfo(3);

        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey,
                                       AggregateInfo aggregateInfo,
                                       WindowInfo windowInfo, WindowOwner windowOwner,
                                       GroupAggregateTransformer aggregateTransformer) {
                SlidingWindow slidingWindow = new SlidingWindow(new ResourceId(parentResourceId,
                        "Sliding:" + id), outputInfo, groupKey, windowOwner,
                        (SlidingWindowInfo) windowInfo);

                slidingWindow.init(aggregateTransformer, slidingWindow, null);

                return slidingWindow;
            }
        };

        PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                null, inputInfo, groupAggregateInfo, windowInfo, builder);

        LinkedHashMap<String, TransformItemInfo> tranformItemInfos =
                new LinkedHashMap<String, TransformItemInfo>();
        TupleValueExtractor columnAExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(0);
            }
        };
        tranformItemInfos.put("columnA", new TransformItemInfo(columnAExtractor));

        TupleValueExtractor columnBExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(1);
            }
        };
        tranformItemInfos.put("columnB", new TransformItemInfo(columnBExtractor));

        TupleValueExtractor startTimeExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(2);
            }
        };
        tranformItemInfos.put("startTime", new TransformItemInfo(startTimeExtractor));

        TupleValueExtractor endTimeExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return new Timestamp(System.currentTimeMillis());
            }
        };
        tranformItemInfos.put("endTime", new TransformItemInfo(endTimeExtractor));

        TransformInfo transformInfo = new TransformInfo(tranformItemInfos);
        TransformedStream transformedStream = new TransformedStream(partitionedStream, null,
                outputInfo, transformInfo);

        StreamedSink streamedSink = new StreamedSink(transformedStream, null);

        PublicContinuousQuery continuousQuery =
                new PublicContinuousQuery("region-x", "SlidingWindowQry", new Source[]{source},
                        streamedSink);

        CustomMaster master = new CustomMaster();
        master.start(master.getProperties());

        Manager manager = Registry.getInstance().getComponent(Manager.class);
        continuousQuery.init(null);
        continuousQuery.start();

        Context context = continuousQuery.getContext();
        DefaultQueryContext queryContext = context.getQueryContext();
        queryContext.getQueryConfig().setTracingEnabled(true);

        // ----------

        for (int i = 0; i < 4; i++) {
            pumpTuples(source, streamedSink, continuousQuery, 25000);

            System.err.println();
            System.err.println("Next cycle...Press any key.");
            System.in.read();
        }

        // ----------

        continuousQuery.stop();
        master.stop();

        QueryWatcher watcher = queryContext.getQueryWatcher();
        System.out.println("Total: " + watcher.peekOldestRun().getTotalRuns() + ", Success: "
                + watcher.peekOldestRun().getTotalSuccessfulRuns());

        for (Iterator<Run> iterator = watcher.getRecentRunsIterator(); iterator.hasNext();) {
            Run run = iterator.next();

            String message = "Run: " + run.getStatus() + ", " + run.getStartTime() + " to "
                    + run.getEndTime();
            System.err.println(message);

            Step step = run.getFirstStep();
            int i = 1;
            while (step != null) {
                message = "  " + i + ")" + step.getResourceId();
                System.err.println(message);

                i++;

                step = step.getNextStep();
            }

            Throwable error = run.getError();
            if (error != null) {
                System.err.println(error);
            }
        }
    }

    private static void pumpTuples(Source source, StreamedSink streamedSink,
                                   PublicContinuousQuery continuousQuery, int howMany)
            throws InterruptedException,
            Exception, IOException {
        List<Tuple> allTuples = new LinkedList<Tuple>();

        List<InputTuple> adds = new LinkedList<InputTuple>();
        for (int i = 0; i < howMany; i++) {
            String a = (i % 2 == 0) ? "A" : "B";
            long l = i;
            Timestamp t = new Timestamp(System.currentTimeMillis());
            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{a, l, t}));
        }
        allTuples.addAll(adds);

        for (Tuple t : adds) {
            continuousQuery.enqueueInput(new Object[]{t, true});
        }

        System.err.println();
        System.err.println("Receiving...Press any key.");
        System.in.read();

        Tuple first = null;
        Tuple tuple = null;
        while ((tuple = streamedSink.poll(1, TimeUnit.SECONDS)) != null) {
            System.err.println(tuple.getId() + "::" + Arrays.asList(tuple.getRawColumns()));

            if (first == null) {
                first = tuple;
            }
        }

        if (first != null) {
            System.err.println();
            System.err.println("First tuple: " + first.getId() + "::"
                    + Arrays.asList(first.getRawColumns()));
        }
    }

    public static class InputTuple extends LiteTuple {
        public InputTuple(Number id) {
            super(id);
        }

        public InputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class OutputTuple extends LiteTuple {
        public OutputTuple(Number id) {
            super(id);
        }

        public OutputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class PublicContinuousQuery extends ContinuousQueryImpl {
        public PublicContinuousQuery(String regionName, String name, Source[] sources,
                                     StreamedSink streamedSink) {
            super(regionName, new ResourceId(name), sources, streamedSink,
                    new AsyncProcessListener() {
                        public void beforeStart() {
                        }

                        public void afterEnd() {
                        }
                    });
        }

        @Override
        protected void handleQueuedInput(Object object) throws Exception {
            Object[] sentData = (Object[]) object;
            InputTuple inputTuple = (InputTuple) sentData[0];
            boolean isNew = (Boolean) sentData[1];

            // Only one source is present.
            if (isNew) {
                sources[0].sendNewTuple(context, inputTuple);
            }
            else {
                ((WithdrawableSource) sources[0]).sendDeadTuple(context, inputTuple);
            }
        }
    }
}
