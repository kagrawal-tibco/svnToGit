package com.tibco.cep.query.stream.core.perf;

import com.tibco.cep.query.stream.aggregate.*;
import com.tibco.cep.query.stream.context.*;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.WithdrawableSource;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.framework.CustomMaster;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.group.*;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.aggregate.IntegerSumAggregator;
import com.tibco.cep.query.stream.impl.query.ContinuousQueryImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.misc.Clock;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Run;
import com.tibco.cep.query.stream.monitor.QueryWatcher.Step;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.*;
import com.tibco.cep.query.stream.partition.purge.*;
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
 * Author: Ashwin Jayaprakash Date: Nov 30, 2007 Time: 4:24:57 PM
 */

public class VWAPTest {
    public static void main(String[] args) throws Exception {
        final TupleInfo inputInfo = new AbstractTupleInfo(InputTuple.class, new String[]{"symbol",
                "price", "volume"}, new Class[]{String.class, Double.class, Integer.class}) {
            public Tuple createTuple(Number id) {
                return new InputTuple(id);
            }
        };

        final TupleInfo partitionOutputInfo = new AbstractTupleInfo(OutputTuple.class, new String[]{
                "symbol", "vwap", "count", "totalVolume"}, new Class[]{String.class,
                Double.class, Integer.class, Double.class}) {
            public Tuple createTuple(Number id) {
                return new OutputTuple(id);
            }
        };

        final TupleInfo outputInfo = new AbstractTupleInfo(OutputTuple.class, new String[]{"symbol",
                "vwap", "count", "totalVolume", "endTime"}, new Class[]{String.class,
                Double.class, Integer.class, Double.class, Timestamp.class}) {
            public Tuple createTuple(Number id) {
                return new OutputTuple(id);
            }
        };

        // ----------

        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                new LinkedHashMap<String, GroupAggregateItemInfo>();

        // Complex expression as a Group column.
        itemInfos.put("symbol", new GroupAggregateItemInfo(new GroupItemInfo(
                new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                          Tuple tuple) {
                        return tuple.getColumn(0);
                    }
                })));
        itemInfos.put("vwap", new GroupAggregateItemInfo(new AggregateItemInfo(
                VWAPAggregator.CREATOR, null /* No explicit extractor needed. */)));
        itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                CountAggregator.CREATOR, new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return 1 /* Return anything. */;
            }
        })));
        itemInfos.put("totalVolume", new GroupAggregateItemInfo(new AggregateItemInfo(
                IntegerSumAggregator.CREATOR, new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(2);
            }
        })));

        GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(itemInfos);

        // ----------

        final WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("Source"),
                inputInfo);

        // VWAP over last 5 seconds.
        TimeWindowInfo timeWindowInfo = new TimeWindowInfo(Integer.MAX_VALUE, 60 * 2 * 1000);

        WindowBuilder builder = new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey,
                                       AggregateInfo aggregateInfo,
                                       WindowInfo windowInfo, WindowOwner windowOwner,
                                       GroupAggregateTransformer aggregateTransformer) {
                TimeWindow timeWindow = new TimeWindow(new ResourceId(parentResourceId,
                        "TimeWindow:" + id), inputInfo, groupKey, windowOwner,
                        (TimeWindowInfo) windowInfo);

                AggregatedStream aggregatedStream = new AggregatedStream(timeWindow,
                        new ResourceId(parentResourceId, "Aggregate:" + id), aggregateInfo, true);

                WindowPurgeAdvisor advisor = new WindowPurgeAdvisor() {
                    protected Long nextAdviceAt;

                    public WindowPurgeAdvice advise(GlobalContext globalContext,
                                                    DefaultQueryContext queryContext,
                                                    WindowStats stats) {
                        if (nextAdviceAt == null) {
                            long futureDate = Clock.getCurrentTimeMillis() + 5000;
                            nextAdviceAt = new Long(futureDate);

                            ScheduledWindowPurgeAdvice scheduledAdvice =
                                    new ScheduledWindowPurgeAdvice(
                                            nextAdviceAt) {
                                        @Override
                                        public ImmediateWindowPurgeAdvice adviseNow(
                                                WindowStats stats) {
                                            // Clear.
                                            nextAdviceAt = null;

                                            Object o2 = stats.getStat("count");

                                            if (o2 != null && ((Number) o2).intValue() > 0) {
                                                int x = ((Number) o2).intValue();
                                                x = x / 2;

                                                return x > 0 ? new ImmediateWindowPurgeAdvice(x) :
                                                        null;
                                            }

                                            return null;
                                        }
                                    };

                            return scheduledAdvice;
                        }

                        return null;
                    }
                };

                LinkedHashMap<String, AggregateItemInfo> map =
                        new LinkedHashMap<String, AggregateItemInfo>();
                TupleValueExtractor extractor = new DefaultTupleValueExtractor() {
                    @Override
                    public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                          Tuple tuple) {
                        // Can return anything for Count.
                        return 1;
                    }
                };
                map.put("count", new AggregateItemInfo(CountAggregator.CREATOR, extractor));
                TupleInfo aggregateOutputInfo = new AbstractTupleInfo(
                        InternalAdvisorAggregateTuple.class, new String[]{"count"},
                        new Class[]{Integer.class}) {
                    public Tuple createTuple(Number id) {
                        return new InternalAdvisorAggregateTuple(id);
                    }
                };
                AggregateInfo purgeAggregateInfo = new AggregateInfo(map);

                WindowPurgeManager purgeManager = new WindowPurgeManager(timeWindow, windowOwner,
                        advisor, purgeAggregateInfo);

                timeWindow.init(aggregateTransformer, aggregatedStream, purgeManager);

                return timeWindow;
            }
        };

        PartitionedStream partitionedStream = new PartitionedStream(source.getInternalStream(),
                new ResourceId("Partition"), outputInfo, groupAggregateInfo, timeWindowInfo,
                builder);

        LinkedHashMap<String, TransformItemInfo> tranformItemInfos =
                new LinkedHashMap<String, TransformItemInfo>();
        TupleValueExtractor symbolExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(0);
            }
        };
        tranformItemInfos.put("symbol", new TransformItemInfo(symbolExtractor));

        TupleValueExtractor vwapExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(1);
            }
        };
        tranformItemInfos.put("vwap", new TransformItemInfo(vwapExtractor));

        TupleValueExtractor totalPriceExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(2);
            }
        };
        tranformItemInfos.put("count", new TransformItemInfo(totalPriceExtractor));

        TupleValueExtractor totalVolumeExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return tuple.getColumn(3);
            }
        };
        tranformItemInfos.put("totalVolume", new TransformItemInfo(totalVolumeExtractor));

        TupleValueExtractor endTimeExtractor = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(GlobalContext globalContext, QueryContext queryContext,
                                  Tuple tuple) {
                return new Timestamp(System.currentTimeMillis());
            }
        };
        tranformItemInfos.put("endTime", new TransformItemInfo(endTimeExtractor));

        TransformInfo transformInfo = new TransformInfo(tranformItemInfos);
        TransformedStream transformedStream = new TransformedStream(partitionedStream,
                new ResourceId("Transformer"), outputInfo, transformInfo);

        StreamedSink streamedSink = new StreamedSink(transformedStream, new ResourceId("Sink"));

        PublicContinuousQuery continuousQuery = new PublicContinuousQuery("region-x", "VWAPQry",
                new Source[]{source}, streamedSink);

        CustomMaster master = new CustomMaster();
        master.start(master.getProperties());

        continuousQuery.init(null);
        continuousQuery.start();

        Context context = continuousQuery.getContext();
        DefaultQueryContext queryContext = context.getQueryContext();
        queryContext.getQueryConfig().setTracingEnabled(true);

        // ----------

        for (int i = 0; i < 2; i++) {
            pumpTuples(inputInfo, source, streamedSink, continuousQuery, 500);

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

    private static void pumpTuples(TupleInfo info, Source source,
                                   StreamedSink streamedSink, PublicContinuousQuery continuousQuery,
                                   int howMany)
            throws InterruptedException, Exception, IOException {
        List<Tuple> allTuples = new LinkedList<Tuple>();

        Random random = new Random();

        List<InputTuple> adds = new LinkedList<InputTuple>();
        for (int i = 0; i < howMany; i++) {
            String symbol = (i % 2 == 0) ? "BEAS" : "TIBX";
            double price = 9.0 + (2 * random.nextDouble());
            int volume = 10 + random.nextInt(990);

            adds.add(new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{symbol, price,
                    volume}));
        }
        allTuples.addAll(adds);

        for (Tuple t : adds) {
            continuousQuery.enqueueInput(new Object[]{t, true});
        }

        System.err.println();
        System.err.println("Receiving...Press any key.");
        System.in.read();

        final Tuple batchEndMarker = streamedSink.getBatchEndMarker();
        Tuple first = null;
        Tuple tuple = null;
        while ((tuple = streamedSink.poll(10, TimeUnit.SECONDS)) != null) {
            if (tuple == batchEndMarker) {
                continue;
            }

            System.err.println(tuple.getId() + "::" + Arrays.asList(tuple.getRawColumns()));

            if (first == null) {
                first = tuple;
                System.err.println("Columns::" + Arrays.asList(info.getColumnAliases()));
            }
        }

        if (first != null) {
            System.err.println();
            System.err.println("First tuple: " + first.getId() + "::"
                    + Arrays.asList(first.getRawColumns()));
            System.err.println("Columns::" + Arrays.asList(info.getColumnAliases()));
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

    public static class PartitionOutputTuple extends LiteTuple {
        public PartitionOutputTuple(Number id) {
            super(id);
        }

        public PartitionOutputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }

    public static class InternalAdvisorAggregateTuple extends TrackedTuple {
        public InternalAdvisorAggregateTuple(Number id) {
            super(id);
        }

        public InternalAdvisorAggregateTuple(Number id, Object[] columns) {
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

    public static class VWAPAggregator implements Aggregator {
        public static final AggregateCreator CREATOR = new Creator();

        protected final Map<Number, Double[]> tupleIdAndPVArray;

        protected double numerator;

        protected double denominator;

        public VWAPAggregator() {
            this.tupleIdAndPVArray = new HashMap<Number, Double[]>();
            this.numerator = 0.0;
            this.denominator = 0.0;
        }

        public void setExtractor(TupleValueExtractor extractor) {
        }

        public void setRecordAddsOnly(boolean recordAddsOnly) {
        }

        public void add(DefaultGlobalContext globalContext, DefaultQueryContext queryContext,
                        Tuple tuple) {
            double price = ((Number) tuple.getColumn(1)).doubleValue();
            double volume = ((Number) tuple.getColumn(2)).doubleValue();
            double pv = price * volume;

            numerator = numerator + price;
            denominator = denominator + volume;

            Double[] bigDecimals = new Double[]{pv, volume};
            tupleIdAndPVArray.put(tuple.getId(), bigDecimals);
        }

        public void remove(DefaultGlobalContext globalContext, DefaultQueryContext queryContext,
                           Tuple tuple) {
            Double[] pvAndV = tupleIdAndPVArray.remove(tuple.getId());

            numerator = numerator - pvAndV[0];
            denominator = denominator - pvAndV[1];
        }

        public Double calculateAggregate() throws Exception {
            return calculateAggregateAsDouble();
        }

        public int calculateAggregateAsInteger() throws Exception {
            throw new UnsupportedOperationException();
        }

        public long calculateAggregateAsLong() throws Exception {
            throw new UnsupportedOperationException();
        }

        public float calculateAggregateAsFloat() throws Exception {
            throw new UnsupportedOperationException();
        }

        public double calculateAggregateAsDouble() throws Exception {
            double d = denominator;
            if (d == 0.0) {
                return Double.NaN;
            }

            return numerator / d;
        }

        public static class Creator implements AggregateCreator {
            public VWAPAggregator createInstance() {
                return new VWAPAggregator();
            }
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
