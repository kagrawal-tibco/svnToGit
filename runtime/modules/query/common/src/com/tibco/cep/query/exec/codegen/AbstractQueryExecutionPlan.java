package com.tibco.cep.query.exec.codegen;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.core.DStream;
import com.tibco.cep.query.stream.core.DSubStream;
import com.tibco.cep.query.stream.core.IStream;
import com.tibco.cep.query.stream.core.ISubStream;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Sink;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.distinct.DistinctFilteredStream;
import com.tibco.cep.query.stream.expression.EvaluatableExpression;
import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.impl.filter.FilteredStreamImpl;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSourceImpl;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.join.ProxyStream;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.AggregatedPartitionedStream;
import com.tibco.cep.query.stream.partition.PartitionedStream;
import com.tibco.cep.query.stream.partition.SimpleAggregatedStreamWindow;
import com.tibco.cep.query.stream.partition.SlidingWindow;
import com.tibco.cep.query.stream.partition.SlidingWindowInfo;
import com.tibco.cep.query.stream.partition.TimeWindow;
import com.tibco.cep.query.stream.partition.TimeWindowInfo;
import com.tibco.cep.query.stream.partition.TumblingWindow;
import com.tibco.cep.query.stream.partition.TumblingWindowInfo;
import com.tibco.cep.query.stream.partition.Window;
import com.tibco.cep.query.stream.partition.WindowBuilder;
import com.tibco.cep.query.stream.partition.WindowInfo;
import com.tibco.cep.query.stream.partition.WindowOwner;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.query.snapshot.PassThruBridge;
import com.tibco.cep.query.stream.query.snapshot.SimpleBridge;
import com.tibco.cep.query.stream.query.snapshot.SortedBridge;
import com.tibco.cep.query.stream.query.snapshot.TruncatedBridge;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortedStream;
import com.tibco.cep.query.stream.transform.TransformInfo;
import com.tibco.cep.query.stream.transform.TransformedStream;
import com.tibco.cep.query.stream.truncate.TruncatedStream;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 5, 2007
 * Time: 7:02:28 PM
 */


/**
 * Extended by all generated QueryExecutionPlan implementations.
 */
public abstract class AbstractQueryExecutionPlan implements QueryExecutionPlan {


    protected final Logger logger;
    protected final String regionName;
    protected final String name;
    protected Sink sink;
    protected Bridge bridge;
    protected final LinkedHashMap<Class, Set<Source>> tupleClassToSourceSet;
    protected final Map<String, Source> aliasToSource;
    protected final String queryText;


    protected AbstractQueryExecutionPlan(String regionName, String name, String text) throws Exception {
        this.regionName = regionName;
        this.logger = Registry.getInstance().getComponent(Logger.class);
        this.name = name;
        this.queryText = text;
        this.tupleClassToSourceSet = new LinkedHashMap<Class, Set<Source>>();
        this.aliasToSource = new LinkedHashMap<String, Source>();
    }


    protected Stream buildAggregation(String id, Stream stream, TupleInfo tupleInfo, boolean emitFull,
                                      GroupAggregateInfo groupAggregateInfo, WindowBuilder windowBuilder) {
        if (null == windowBuilder) {
            windowBuilder = this.getDefaultWindowBuilder(stream.getOutputInfo(), tupleInfo);
        }
//        if (emitFull) {
//            return new AggregatedPartitionedStreamDecorator(stream, new ResourceId(stream.getResourceId(), id),
//                    tupleInfo, groupAggregateInfo, windowBuilder);
//        } else {
            return new AggregatedPartitionedStream(stream, new ResourceId(stream.getResourceId(), id),
                    tupleInfo, groupAggregateInfo, windowBuilder);
//        }
    }


    protected Stream buildDeleted(String id, Stream stream) {
        return new DStream(stream, new ResourceId(stream.getResourceId(), id));
    }


    protected Stream buildDistinct(String id, TupleInfo tupleInfo) {
        return new DistinctFilteredStream(new ResourceId(id), tupleInfo);
    }


    protected Stream buildDistinct(String id, Stream stream) {
        return new DistinctFilteredStream(stream, new ResourceId(stream.getResourceId(), id));
    }


    protected Stream buildFilter(String id, Stream stream, EvaluatableExpression expr) {
        return new FilteredStreamImpl(stream, new ResourceId(stream.getResourceId(), id), expr);
    }


    protected Stream buildInsert(String id, Stream stream) {
        return new IStream(stream, new ResourceId(stream.getResourceId(), id));
    }


    protected Stream buildJoin(String id, Stream[] streams, Expression expr, JoinedTupleInfo tupleInfo) throws Exception {
        final Map<String, Stream> m = new HashMap<String, Stream>();
        for (Stream stream : streams) {
            m.put(stream.getResourceId().getId(), stream);
        }
        return new JoinedStreamImpl(this.regionName, this.name, new ResourceId(id), m, null, expr, tupleInfo);
    }


    protected Stream buildLimit(String id, TupleInfo tupleInfo, int limitFirst, int limitOffset) {
        return new TruncatedStream(new ResourceId(id), tupleInfo, limitFirst, limitOffset);
    }


    protected Stream buildLimit(String id, Stream stream, int limitFirst, int limitOffset) {
        return new TruncatedStream(stream, new ResourceId(id), limitFirst, limitOffset);
    }


    protected Stream buildLimit(
            String id, TupleInfo tupleInfo, ExpressionEvaluator limitFirst, ExpressionEvaluator limitOffset) {
        return new TruncatedStream(new ResourceId(id), tupleInfo, limitFirst, limitOffset);
    }


    protected Stream buildLimit(
            String id, Stream stream, ExpressionEvaluator limitFirst, ExpressionEvaluator limitOffset) {
        return new TruncatedStream(stream, new ResourceId(id), limitFirst, limitOffset);
    }


    protected Stream buildPartition(String id, Stream stream, TupleInfo tupleInfo,
                                    GroupAggregateInfo groupAggregateInfo, WindowInfo windowInfo,
                                    WindowBuilder windowBuilder) {
        return new PartitionedStream(stream, new ResourceId(stream.getResourceId(), id),
                tupleInfo, groupAggregateInfo, windowInfo, windowBuilder);
    }


    protected Bridge buildPassThroughBridge(String id, Stream stream, Stream setupStream) {
        this.bridge = new PassThruBridge(stream, new ResourceId(stream.getResourceId(), id));
        this.bridge.setup(setupStream);

        return this.bridge;
    }


    protected Stream buildProxy(String id, Stream stream) {
        return new ProxyStream(new ResourceId(stream.getResourceId(), id), stream);
    }


    protected Bridge buildSimpleBridge(String id, Stream stream, Stream setupStream) {
        this.bridge = new SimpleBridge(stream, new ResourceId(stream.getResourceId(), id));
        this.bridge.setup(setupStream);
        return this.bridge;
    }


    protected Sink buildSink(String id, Stream stream, TupleInfo tupleInfo) {
        if (null == stream) {
            return new StreamedSink(new ResourceId(id), tupleInfo);
        } else {
            return new StreamedSink(stream, new ResourceId(stream.getResourceId(), id));
        }
    }


    protected WindowBuilder buildSlidingWindowBuilder(final TupleInfo inputTupleInfo,
                                                                                     final TupleInfo outputTupleInfo,
                                                                                     final Emit emit,
                                                                                     final boolean includesAggregate) {
        return new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey, AggregateInfo aggregateInfo, WindowInfo windowInfo,
                                       WindowOwner windowOwner, GroupAggregateTransformer aggregateTransformer) {

                final Window window = new SlidingWindow(
                        new ResourceId(parentResourceId, "SlidingWindow:" + id), inputTupleInfo,
                        groupKey, windowOwner, (SlidingWindowInfo) windowInfo);

                SubStream stream = window;

                switch (emit) {
                    case NEW: {
                        stream = new ISubStream(window, new ResourceId(window.getResourceId(), "Insert:" + id));
                    }
                    break;
                    case DEAD: {
                        stream = new DSubStream(window, new ResourceId(window.getResourceId(), "Delete:" + id));
                    }
                    break;
                }

                if (includesAggregate && (null != aggregateInfo)) {
                    stream = new AggregatedStream(stream,
                            new ResourceId(parentResourceId, "Aggregate:" + id), aggregateInfo, true);
                }

                window.init(aggregateTransformer, stream, null);

                return window;
            }//buildAndInit
        };
    }


    protected Stream buildSort(String id, TupleInfo tupleInfo, SortInfo sortInfo,
                               TupleValueExtractor[] extractors, List<Comparator<Object>> comparators) {
        return new SortedStream(new ResourceId(id), tupleInfo, sortInfo, extractors, comparators);
    }

    
    protected Stream buildSort(String id, Stream stream, SortInfo sortInfo, TupleValueExtractor[] extractors,
                               List<Comparator<Object>> comparators) {
        return new SortedStream(stream, new ResourceId(stream.getResourceId(), id), sortInfo, extractors, comparators);
    }


    protected Bridge buildSortedBridge(String id, Stream stream, Stream setupStream, SortInfo sortInfo,
                                       TupleValueExtractor[] extractors, List<Comparator<Object>> comparators) {
        this.bridge = new SortedBridge(stream, new ResourceId(stream.getResourceId(), id),
                sortInfo, extractors, comparators);
        this.bridge.setup(setupStream);

        return this.bridge;
    }


    protected Bridge buildSortedBridge(String id, Stream stream, Sink setupSink, SortInfo sortInfo,
                                       TupleValueExtractor[] extractors, List<Comparator<Object>> comparators) {
        this.bridge = new SortedBridge(stream, new ResourceId(stream.getResourceId(), id),
                sortInfo, extractors, comparators);
        this.bridge.setup(setupSink.getInternalStream());

        return this.bridge;
    }


    protected Stream buildSource(String id, ReteEntityInfo tupleInfo, String alias) {
        final Source source = new ReteEntitySourceImpl(new ResourceId(id), tupleInfo, tupleInfo.getColumnTypes()[0]);

        final Class containerClass = tupleInfo.getContainerClass();
        this.aliasToSource.put(alias, source);
        Set<Source> sourceSet = this.tupleClassToSourceSet.get(containerClass);
        if (null == sourceSet) {
            sourceSet = new HashSet<Source>();
            this.tupleClassToSourceSet.put(containerClass, sourceSet);
        }
        sourceSet.add(source);

        return source.getInternalStream();
    }


    protected StaticSink buildStaticSink(String id, Stream stream, TupleInfo tupleInfo) {
        if (null == stream) {
            return new StaticSink(new ResourceId(id), tupleInfo);
        } else {
            return new StaticSink(stream, new ResourceId(stream.getResourceId(), id));
        }
    }


    protected WindowBuilder buildTimeWindowBuilder(final TupleInfo inputTupleInfo,
                                                                                  final TupleInfo outputTupleInfo,
                                                                                  final Emit emit,
                                                                                  final boolean includesAggregate) {
        return new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId,
                                       String id,
                                       GroupKey groupKey,
                                       AggregateInfo aggregateInfo,
                                       WindowInfo windowInfo,
                                       WindowOwner windowOwner,
                                       GroupAggregateTransformer aggregateTransformer) {

                final Window window = new TimeWindow(
                        new ResourceId(parentResourceId, "TimeWindow:" + id), inputTupleInfo,
                        groupKey, windowOwner, (TimeWindowInfo) windowInfo);

                SubStream stream = window;

                switch (emit) {
                    case NEW: {
                        stream = new ISubStream(window, new ResourceId(window.getResourceId(), "Insert:" + id));
                    }
                    break;
                    case DEAD: {
                        stream = new DSubStream(window, new ResourceId(window.getResourceId(), "Delete:" + id));
                    }
                    break;
                }

                if (includesAggregate && (null != aggregateInfo)) {
                    stream = new AggregatedStream(stream,
                            new ResourceId(parentResourceId, "Aggregate:" + id), aggregateInfo, true);
                }

                window.init(aggregateTransformer, stream, null);

                return window;
            }//buildAndInit
        };
    }


    protected Stream buildTransform(String id, Stream stream, TransformInfo transformInfo, TupleInfo tupleInfo) {
        return new TransformedStream(stream, new ResourceId(stream.getResourceId(), id), tupleInfo, transformInfo);
    }


    protected Bridge buildTruncatedBridge(String id, Stream stream, Stream setupStream) {
        this.bridge = new TruncatedBridge(stream, new ResourceId(stream.getResourceId(), id));
        this.bridge.setup(setupStream);
        return this.bridge;
    }


    protected WindowBuilder buildTumblingWindowBuilder(final TupleInfo inputTupleInfo,
                                                                                      final TupleInfo outputTupleInfo,
                                                                                      final Emit emit,
                                                                                      final boolean includesAggregate) {
        return new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey, AggregateInfo aggregateInfo, WindowInfo windowInfo,
                                       WindowOwner windowOwner, GroupAggregateTransformer aggregateTransformer) {

                final Window window = new TumblingWindow(
                        new ResourceId(parentResourceId, "TumblingWindow:" + id), inputTupleInfo,
                        groupKey, windowOwner, (TumblingWindowInfo) windowInfo);

                SubStream stream = window;

                switch (emit) {
                    case NEW: {
                        stream = new ISubStream(window, new ResourceId(window.getResourceId(), "Insert:" + id));
                    }
                    break;
                    case DEAD: {
                        stream = new DSubStream(window, new ResourceId(window.getResourceId(), "Delete:" + id));
                    }
                    break;
                }

                if (includesAggregate && (null != aggregateInfo)) {
                    stream = new AggregatedStream(stream,
                            new ResourceId(parentResourceId, "Aggregate:" + id), aggregateInfo, true);
                }

                window.init(aggregateTransformer, stream, null);

                return window;
            }//buildAndInit
        };
    }


    protected Stream buildWithdrawableSource(String id, ReteEntityInfo tupleInfo, String alias) {
        final Source source = new ReteEntityWithdrawableSourceImpl(new ResourceId(id),
                tupleInfo, tupleInfo.getColumnTypes()[0]);

        this.aliasToSource.put(alias, source);
        final Class containerClass = tupleInfo.getContainerClass();
        Set<Source> sourceSet = this.tupleClassToSourceSet.get(containerClass);
        if (null == sourceSet) {
            sourceSet = new HashSet<Source>();
            this.tupleClassToSourceSet.put(containerClass, sourceSet);
        }
        sourceSet.add(source);

        return source.getInternalStream();
    }


    public Bridge getBridge() {
        return this.bridge;
    }


    protected WindowBuilder getDefaultWindowBuilder(final TupleInfo inputTupleInfo,
                                                    final TupleInfo outputTupleInfo) {
        return new WindowBuilder() {
            public Window buildAndInit(ResourceId parentResourceId, String id, GroupKey groupKey, AggregateInfo aggregateInfo, WindowInfo windowInfo,
                                       WindowOwner windowOwner, GroupAggregateTransformer aggregateTransformer) {

                final Window window = new SimpleAggregatedStreamWindow(
                        new ResourceId(parentResourceId, "SimpleWindow:" + id), inputTupleInfo,
                        groupKey, windowOwner);

                final AggregatedStream aggregatedStream = (null == aggregateInfo) ? null
                        : new AggregatedStream(window, new ResourceId(parentResourceId, "Aggregate:" + id),
                        aggregateInfo, true);

                window.init(aggregateTransformer, aggregatedStream, null);

                return window;
            }//buildAndInit
        };
    }


    public static Logger getLogger() {
        return Registry.getInstance().getComponent(Logger.class);
    }

    
    public String getRegionName() {
        return regionName;
    }

    /**
     * @return String
     */
    public String getName() {
        return this.name;
    }


    /**
     * @return String text of the query.
     */
    public String getQueryText() {
        return this.queryText;
    }



    public Sink getSink() {
        return this.sink;
    }


    public Map<String, Source> getSources() {
        return this.aliasToSource;
    }


    public Set<Source> getSources(Class tupleClass) {
        return this.tupleClassToSourceSet.get(tupleClass);
    }


    public Set<Class> getSourceTupleClasses() {
        return this.tupleClassToSourceSet.keySet();
    }


    public enum Emit {
        DEAD,
        FULL,
        NEW,;
    }


}
