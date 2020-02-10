package com.tibco.cep.query.exec.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.exec.QueryExecutionPlanFactory;
import com.tibco.cep.query.exec.descriptors.QueryExecutionPlanDescriptor;
import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.WindowBuilderDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.AggregationDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.BridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.DistinctFilteredStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.EvaluatorDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.ExpressionDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.ExtractorDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.FilterDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.JoinDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.PartitionDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.PassThroughBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.ProxyDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SimpleBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SinkDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SortedBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SortedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SourceDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TransformedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TruncatedBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TruncatedStreamDescriptor;
import com.tibco.cep.query.model.AcceptType;
import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.core.DSubStream;
import com.tibco.cep.query.stream.core.ISubStream;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.distinct.DistinctFilteredStream;
import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateTransformer;
import com.tibco.cep.query.stream.group.GroupKey;
import com.tibco.cep.query.stream.impl.expression.EvaluatorToExtractorAdapter;
import com.tibco.cep.query.stream.impl.expression.ExtractorToEvaluatorAdapter;
import com.tibco.cep.query.stream.impl.filter.FilteredStreamImpl;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySourceImpl;
import com.tibco.cep.query.stream.impl.rete.ReteEntityWithdrawableSourceImpl;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.join.JoinedStreamImpl;
import com.tibco.cep.query.stream.io.StaticSink;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.join.ProxyStream;
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
import com.tibco.cep.query.stream.partition.purge.WindowPurgeManager;
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

public class QueryExecutionPlanFactoryImpl
        implements QueryExecutionPlanFactory {


    protected QueryExecutionPlanDescriptor qepDescriptor;

    public QueryExecutionPlanDescriptor getQepDescriptor() {
        return qepDescriptor;
    }

    public void setQepDescriptor(QueryExecutionPlanDescriptor qepDescriptor) {
        this.qepDescriptor = qepDescriptor;
    }

    private Stream buildAggregation(
            QueryExecutionPlanFactoryContext context,
            Stream input)
            throws Exception {

        final AggregationDescriptor aggregationDescriptor = this.qepDescriptor.getAggregationDescriptor();
        if (null == aggregationDescriptor) {
            return input;
        }

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final TupleInfo inputTupleInfo = (TupleInfo) nameToObject.get(
                aggregationDescriptor.getInputStreamDescriptors()[0].getTupleInfoFieldName());

        final TupleInfo outputTupleInfo = (TupleInfo) nameToObject.get(
                aggregationDescriptor.getTupleInfoFieldName());

        final WindowBuilderDescriptor windowBuilderDescriptor = (WindowBuilderDescriptor)
                nameToObject.get(aggregationDescriptor.getWindowBuilderName());
        final WindowBuilder windowBuilder;
        if (null == windowBuilderDescriptor) {
            windowBuilder = this.buildDefaultWindowBuilder(inputTupleInfo, outputTupleInfo); //todo build before

        } else {
            switch (windowBuilderDescriptor.getType()) {
                case SLIDING:
                    windowBuilder = this.buildSlidingWindowBuilder( //todo build before
                            windowBuilderDescriptor, inputTupleInfo, outputTupleInfo);
                    break;

                case TIME:
                    windowBuilder = this.buildTimeWindowBuilder( //todo build before
                            windowBuilderDescriptor, inputTupleInfo, outputTupleInfo);
                    break;

                case TUMBLING:
                    windowBuilder = this.buildTumblingWindowBuilder( //todo build before
                            windowBuilderDescriptor, inputTupleInfo, outputTupleInfo);
                    break;

                default:
                    windowBuilder =
                            this.buildDefaultWindowBuilder(inputTupleInfo, outputTupleInfo); //todo build before
            }
        }

        final GroupAggregateInfo groupAggregateInfo = (GroupAggregateInfo)
                nameToObject.get(aggregationDescriptor.getGroupAggregateInfoName());

        final Stream aggrStream = new AggregatedPartitionedStream(input,
                new ResourceId(input.getResourceId(), aggregationDescriptor.getName()),
                outputTupleInfo, groupAggregateInfo, windowBuilder);

        return this.buildHavingFilters(aggrStream);
    }


    private void buildBridge(
            QueryExecutionPlanFactoryContext context) {

        if (context.isContinuous()) {
            return;
        }

        final BridgeDescriptor bridgeDescriptor = this.qepDescriptor.getBridgeDescriptor();

        final QueryExecutionPlanFactoryContext.BridgeInfo bridgeInfo = context.getBridgeInfo();

        if (bridgeDescriptor instanceof PassThroughBridgeDescriptor) {
            bridgeInfo.bridge = new PassThruBridge(bridgeInfo.inputStream,
                    new ResourceId(bridgeInfo.inputStream.getResourceId(), bridgeDescriptor.getName()));
        } else if (bridgeDescriptor instanceof SimpleBridgeDescriptor) {
            bridgeInfo.bridge = new SimpleBridge(bridgeInfo.inputStream,
                    new ResourceId(bridgeInfo.inputStream.getResourceId(), bridgeDescriptor.getName()));
        } else if (bridgeDescriptor instanceof SortedBridgeDescriptor) {
            final SortedStreamDescriptor sortDescriptor = this.qepDescriptor.getSortDescriptor();
            final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
            bridgeInfo.bridge = new SortedBridge(bridgeInfo.inputStream,
                    new ResourceId(bridgeInfo.inputStream.getResourceId(), sortDescriptor.getName()),
                    (SortInfo) nameToObject.get(sortDescriptor.getSortInfoName()),
                    (TupleValueExtractor[]) nameToObject.get(sortDescriptor.getExtractorsName()),
                    (List<Comparator<Object>>) nameToObject.get(sortDescriptor.getComparatorsName()));
        } else if (bridgeDescriptor instanceof TruncatedBridgeDescriptor) {
            bridgeInfo.bridge = new TruncatedBridge(bridgeInfo.inputStream,
                    new ResourceId(bridgeInfo.inputStream.getResourceId(), bridgeDescriptor.getName()));
        }

        bridgeInfo.bridge.setup(bridgeInfo.setupStream);
    }


    private WindowBuilder buildDefaultWindowBuilder(
            final TupleInfo inputTupleInfo,
            final TupleInfo outputTupleInfo)
            throws Exception {

        return new WindowBuilder() {
            public Window buildAndInit(
                    ResourceId parentResourceId,
                    String id,
                    GroupKey groupKey,
                    AggregateInfo aggregateInfo,
                    WindowInfo windowInfo,
                    WindowOwner windowOwner,
                    GroupAggregateTransformer aggregateTransformer) {

                final Window window = new SimpleAggregatedStreamWindow(
                        new ResourceId(parentResourceId, "SimpleWindow:" + id), inputTupleInfo, groupKey,
                        windowOwner);

                final AggregatedStream aggregatedStream =
                        (null == aggregateInfo) ? null : new AggregatedStream(window,
                                new ResourceId(parentResourceId, "Aggregate:" + id), aggregateInfo, true);

                window.init(aggregateTransformer, aggregatedStream, null);

                return window;
            }//buildAndInit
        };
    }


    private String buildDeleted(StreamDescriptor descriptor)
            throws Exception {

//        final String fieldName = this.nameGenerator.makeLocalDeletedName();
//
//        TEMPLATE_FOR_CREATE_DELETED.reset();
//        TEMPLATE_FOR_CREATE_DELETED.setAttribute("id", Escaper.toJavaStringSource(fieldName));
//        TEMPLATE_FOR_CREATE_DELETED.setAttribute("valueName", fieldName);
//        TEMPLATE_FOR_CREATE_DELETED.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
//        final String code = TEMPLATE_FOR_CREATE_DELETED.toString();
//        this.continuousBody.append(code).append("\n\n");
//        this.singleShotBody.append(code).append("\n\n");
//
//        descriptor.setVariableName(fieldName);
//        this.updateVariableMaps(descriptor.getContext(), fieldName);
//
//        return fieldName;
        return null;
    }


    /**
     * @param context
     *@param input Stream @return Stream
     * @throws Exception upon problem.
     */
    private Stream buildDistinct(
            QueryExecutionPlanFactoryContext context,
            Stream input)
            throws Exception {

        final DistinctFilteredStreamDescriptor distinctDescriptor = this.qepDescriptor.getDistinctDescriptor();
        if (null == distinctDescriptor) {
            return input;
        }


        final DistinctFilteredStream distinctFilteredStream;
        if (context.isContinuous()) {
            distinctFilteredStream = new DistinctFilteredStream(input, new ResourceId(input.getResourceId(),
                    distinctDescriptor.getName()));

        } else {
            distinctFilteredStream = new DistinctFilteredStream(new ResourceId(distinctDescriptor.getName()),
                    input.getOutputInfo());
        }

        final QueryExecutionPlanFactoryContext.BridgeInfo bridgeInfo = context.getBridgeInfo();
        if (null == bridgeInfo.setupStream) {
            bridgeInfo.setupStream = distinctFilteredStream;
        }

        return distinctFilteredStream;
    }


    private void buildFilter(
            FilterDescriptor descriptor,
            LinkedHashMap<String, Stream> idToStream) {
        final StreamDescriptor inputDescriptor = descriptor.getInputStreamDescriptors()[0];
        final String inputId = inputDescriptor.getName();
        final Stream inputStream = idToStream.remove(inputId);
        final ResourceId resourceId = new ResourceId(inputStream.getResourceId(), descriptor.getName());
        final Object o = this.qepDescriptor.getNameToObject().get(descriptor.getFilterExpressionName());
        final ExpressionEvaluator evaluator;
        if (o instanceof ExtractorDescriptor) {
            evaluator = new ExtractorToEvaluatorAdapter(((ExtractorDescriptor) o).getExtractor(), 0); //todo
        } else if (o instanceof EvaluatorToExtractorAdapter) {
            evaluator = ((EvaluatorToExtractorAdapter) o).getEvaluator();
        } else {
            evaluator = new ExtractorToEvaluatorAdapter((TupleValueExtractor) o, 0);//todo
        }
        idToStream.put(resourceId.getId(), new FilteredStreamImpl(inputStream, resourceId, evaluator));
    }


    private void buildFilters(
            QueryExecutionPlanFactoryContext context) {

        for (FilterDescriptor descriptor : this.qepDescriptor.getFilterDescriptors()) {
            this.buildFilter(descriptor, context.getIdToStream());
        }
    }


    private Stream buildHavingFilters(
            Stream input) {

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        Stream output = input;
        for (FilterDescriptor descriptor : this.qepDescriptor.getHavingFilterDescriptors()) {
            final ResourceId resourceId = new ResourceId(input.getResourceId(), descriptor.getName());
            final Object o = nameToObject.get(descriptor.getFilterExpressionName());
            final ExpressionEvaluator evaluator;
            if (o instanceof ExtractorDescriptor) {
                evaluator = new ExtractorToEvaluatorAdapter(((ExtractorDescriptor) o).getExtractor(), 0); //todo
            } else if (o instanceof EvaluatorToExtractorAdapter) {
                evaluator = ((EvaluatorToExtractorAdapter) o).getEvaluator();
            } else if(o instanceof ExtractorDescriptor){
                evaluator = new ExtractorToEvaluatorAdapter(((ExtractorDescriptor)o).getExtractor(), 0);
            } else {
                evaluator = new ExtractorToEvaluatorAdapter((TupleValueExtractor) o, 0);//todo
            }
            output = new FilteredStreamImpl(input, resourceId, evaluator);
            input = output;
        }

        return output;
    }


    private String buildInsert(
            StreamDescriptor descriptor) {
//
//        final String varName = this.nameGenerator.makeLocalInsertName();
//
//        TEMPLATE_FOR_CREATE_INSERT.reset();
//        TEMPLATE_FOR_CREATE_INSERT.setAttribute("id", Escaper.toJavaStringSource(varName));
//        TEMPLATE_FOR_CREATE_INSERT.setAttribute("valueName", varName);
//        TEMPLATE_FOR_CREATE_INSERT.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
//        final String code = TEMPLATE_FOR_CREATE_INSERT.toString();
//        this.continuousBody.append(code).append("\n\n");
//        this.singleShotBody.append(code).append("\n\n");
//
//        descriptor.setVariableName(varName);
//        this.updateVariableMaps(descriptor.getContext(), varName);
//
//        return varName;
        return null;
    }


    /**
     * @return Stream.
     * @throws Exception upon problem.
     * @param context
     */
    private Stream buildJoin(QueryExecutionPlanFactoryContext context)
            throws Exception {

        final JoinDescriptor joinDescriptor = this.qepDescriptor.getJoinDescriptor();

        if (null == joinDescriptor) {
            return context.getIdToStream().values().iterator().next();
        }

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final ExpressionDescriptor ed = (ExpressionDescriptor)
                nameToObject.get(joinDescriptor.getExpressionFieldName());

        final Expression expr = ed.getExpression();

        final JoinedTupleInfo outputTupleInfo = (JoinedTupleInfo)
                nameToObject.get(joinDescriptor.getTupleInfoFieldName());

        return new JoinedStreamImpl(this.qepDescriptor.getRegionName(), context.getName(),
                new ResourceId(joinDescriptor.getName()),
                context.getIdToStream(), null, expr, outputTupleInfo);
    }


    /**
     * @param context
     *@param input Stream @return Stream
     * @throws Exception upon problem.
     */
    private Stream buildLimit(
            QueryExecutionPlanFactoryContext context,
            Stream input)
            throws Exception {

        final TruncatedStreamDescriptor limitDescriptor = this.qepDescriptor.getLimitDescriptor();

        if (null == limitDescriptor) {
            return input;
        }

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
        final EvaluatorDescriptor limitFirst = (EvaluatorDescriptor)
                nameToObject.get(limitDescriptor.getFirstFieldName());
        final EvaluatorDescriptor limitOffset = (EvaluatorDescriptor)
                nameToObject.get(limitDescriptor.getOffsetFieldName());

        final Stream truncatedStream;
        if (context.isContinuous() || (input instanceof DistinctFilteredStream)) {
            truncatedStream = new TruncatedStream(input,
                    new ResourceId(limitDescriptor.getName()),
                    limitFirst.getEvaluator(),
                    limitOffset.getEvaluator());

        } else {
            truncatedStream = new TruncatedStream(
                    new ResourceId(limitDescriptor.getName()),
                    input.getOutputInfo(),
                    limitFirst.getEvaluator(),
                    limitOffset.getEvaluator());
        }

        final QueryExecutionPlanFactoryContext.BridgeInfo bridgeInfo = context.getBridgeInfo();
        if (null == bridgeInfo.setupStream) {
            bridgeInfo.setupStream = truncatedStream;
        }

        return truncatedStream;
    }


    private void buildPartition(
            PartitionDescriptor partitionDescriptor,
            LinkedHashMap<String, Stream> idToStream)
            throws Exception {

        final Stream inputStream = idToStream.remove(partitionDescriptor.getInputStreamDescriptors()[0].getName());

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final ResourceId resourceId = new ResourceId(inputStream.getResourceId(), partitionDescriptor.getName());
        final TupleInfo tupleInfo = (TupleInfo) nameToObject.get(partitionDescriptor.getTupleInfoFieldName());

        final GroupAggregateInfo groupAggregateInfo = (GroupAggregateInfo)
                nameToObject.get(partitionDescriptor.getGroupAggregateInfoName());
        final WindowBuilderDescriptor windowBuilderDescriptor = partitionDescriptor.getWindowBuilderDescriptor();
        final WindowInfo windowInfo = windowBuilderDescriptor.getWindowInfo();
        final WindowBuilder windowBuilder;
        switch (windowBuilderDescriptor.getType()) {
            case SLIDING:
                windowBuilder = this.buildSlidingWindowBuilder(windowBuilderDescriptor, tupleInfo, tupleInfo);
                break;
            case TIME:
                windowBuilder = this.buildTimeWindowBuilder(windowBuilderDescriptor, tupleInfo, tupleInfo);
                break;
            case TUMBLING:
                windowBuilder = this.buildTumblingWindowBuilder(windowBuilderDescriptor, tupleInfo, tupleInfo);
                break;
            default:
                windowBuilder = this.buildDefaultWindowBuilder(tupleInfo, tupleInfo);
        }

        idToStream.put(resourceId.getId(),
                new PartitionedStream(inputStream, resourceId, tupleInfo,
                        groupAggregateInfo, windowInfo, windowBuilder));
    }


    private void buildPartitions(
            QueryExecutionPlanFactoryContext context)
            throws Exception {

        final LinkedHashMap<String, Stream> idToStream = context.getIdToStream();
        for (PartitionDescriptor descriptor : this.qepDescriptor.getPartitionDescriptors()) {
            this.buildPartition(descriptor, idToStream);
        }
    }


    private void buildProxies(
            QueryExecutionPlanFactoryContext context) {

        final LinkedHashMap<String, Source> nameToSource = context.getNameToSource();
        final LinkedHashMap<String, Stream> idToStream = context.getIdToStream();
        for (Source source : nameToSource.values()) {
            final Stream internalStream = source.getInternalStream();
            idToStream.put(internalStream.getResourceId().getId(), internalStream);
        }
        for (ProxyDescriptor descriptor : this.qepDescriptor.getProxyDescriptors()) {
            final Source source = nameToSource.get(descriptor.getInputName());
            final ResourceId resourceId = new ResourceId(source.getResourceId(), descriptor.getName());
            idToStream.put(resourceId.getId(), new ProxyStream(resourceId, source.getInternalStream()));
        }
    }


    /**
     * @param context
     *@param input Stream @throws Exception upon problem.
     */
    private void buildSink(
            QueryExecutionPlanFactoryContext context,
            Stream input)
            throws Exception {

        final SinkDescriptor sinkDescriptor = this.qepDescriptor.getSinkDescriptor();
        final String id = sinkDescriptor.getName();
        final String outputTupleInfoName = sinkDescriptor.getTupleInfoFieldName();
        final TupleInfo outputTupleInfo = (TupleInfo) this.qepDescriptor.getNameToObject().get(outputTupleInfoName);
        final StreamDescriptor previousStreamDescriptor = sinkDescriptor.getInputStreamDescriptors()[0];
        final Stream stream;
        if ((previousStreamDescriptor instanceof DistinctFilteredStreamDescriptor)
                || (previousStreamDescriptor instanceof TruncatedStreamDescriptor)) {
            stream = input;
        } else {
            stream = null;
        }

        if (context.isContinuous()) {
            context.setSink(new StreamedSink(input, new ResourceId(input.getResourceId(), id)));

        } else if (sinkDescriptor.isStreamed()) {
            if (null == stream) {
                context.setSink(new StreamedSink(new ResourceId(id), outputTupleInfo));
            } else {
                context.setSink(new StreamedSink(stream, new ResourceId(stream.getResourceId(), id)));
            }
        } else {
            if (null == stream) {
                context.setSink(new StaticSink(new ResourceId(id), outputTupleInfo));
            } else {
                context.setSink(new StaticSink(stream, new ResourceId(stream.getResourceId(), id)));
            }
        }

        final QueryExecutionPlanFactoryContext.BridgeInfo bridgeInfo = context.getBridgeInfo();
        if (null == bridgeInfo.setupStream) {
            bridgeInfo.setupStream = context.getSink().getInternalStream();
        }
    }


    private WindowBuilder buildSlidingWindowBuilder(
            final WindowBuilderDescriptor windowBuilderDescriptor,
            final TupleInfo inputTupleInfo,
            final TupleInfo outputTupleInfo) {

        return new WindowBuilder() {

            public Window buildAndInit(
                    ResourceId parentResourceId,
                    String id,
                    GroupKey groupKey,
                    AggregateInfo aggregateInfo,
                    WindowInfo windowInfo,
                    WindowOwner windowOwner,
                    GroupAggregateTransformer aggregateTransformer) {

                final Window window = new SlidingWindow(new ResourceId(parentResourceId, "SlidingWindow:" + id),
                        inputTupleInfo, groupKey, windowOwner, (SlidingWindowInfo) windowInfo);

                SubStream stream = window;

                switch (windowBuilderDescriptor.getEmitType()) {
                    case NEW: {
                        stream = new ISubStream(window, new ResourceId(window.getResourceId(), "Insert:" + id));
                    }
                    break;
                    case DEAD: {
                        stream = new DSubStream(window, new ResourceId(window.getResourceId(), "Delete:" + id));
                    }
                    break;
                }

                if (windowBuilderDescriptor.getIncludesAggregates() && (null != aggregateInfo)) {
                    stream = new AggregatedStream(stream, new ResourceId(parentResourceId, "Aggregate:" + id),
                            aggregateInfo, true);
                }

                WindowPurgeManager purgeManager =
                        plugInWindowPurgeManager(window, windowOwner, windowBuilderDescriptor, inputTupleInfo);

                window.init(aggregateTransformer, stream, purgeManager);

                return window;
            }//buildAndInit
        };
    }


    /**
     * @param context
     *@param input Stream @return Stream
     * @throws Exception upon problem.
     */
    private Stream buildSort(
            QueryExecutionPlanFactoryContext context,
            Stream input)
            throws Exception {

        final SortedStreamDescriptor sortDescriptor = this.qepDescriptor.getSortDescriptor();
        if ((null == sortDescriptor) || !context.isContinuous()) {
            return input;
        }

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final Stream sortedStream;
        if (context.isContinuous()) {
            sortedStream = new SortedStream(
                    input,
                    new ResourceId(sortDescriptor.getName()),
                    (SortInfo) nameToObject.get(sortDescriptor.getSortInfoName()),
                    (TupleValueExtractor[]) nameToObject.get(sortDescriptor.getExtractorsName()),
                    (List<Comparator<Object>>) nameToObject.get(sortDescriptor.getComparatorsName()));
        } else {
            sortedStream = new SortedStream(
                new ResourceId(sortDescriptor.getName()),
                input.getOutputInfo(),
                (SortInfo) nameToObject.get(sortDescriptor.getSortInfoName()),
                (TupleValueExtractor[]) nameToObject.get(sortDescriptor.getExtractorsName()),
                (List<Comparator<Object>>) nameToObject.get(sortDescriptor.getComparatorsName()));

            final QueryExecutionPlanFactoryContext.BridgeInfo bridgeInfo = context.getBridgeInfo();
            if (null == bridgeInfo.setupStream) {
                bridgeInfo.setupStream = sortedStream;
            }
        }
        return sortedStream;
    }


    private void buildSource(
            boolean isContinuous,
            SourceDescriptor descriptor,
            LinkedHashMap<String, Source> nameToSource) {

        final ReteEntityInfo tupleInfo = (ReteEntityInfo)
                this.qepDescriptor.getNameToObject().get(descriptor.getTupleInfoFieldName());
        final ReteEntityFilter filter = descriptor.getFilter();
        final Source source;
        if (isContinuous && (AcceptType.ALL == descriptor.getAcceptType())) {
            if (null == filter) {
                source = new ReteEntityWithdrawableSourceImpl(
                        new ResourceId(descriptor.getName()), tupleInfo, tupleInfo.getColumnTypes()[0]);
            } else {
                source = new ReteEntityWithdrawableSourceImpl(
                        new ResourceId(descriptor.getName()), tupleInfo, tupleInfo.getColumnTypes()[0], filter);
            }
        } else {
            if (null == filter) {
                source = new ReteEntitySourceImpl(
                        new ResourceId(descriptor.getName()), tupleInfo, tupleInfo.getColumnTypes()[0]);
            } else {
                source = new ReteEntitySourceImpl(
                        new ResourceId(descriptor.getName()), tupleInfo, tupleInfo.getColumnTypes()[0], filter);
            }
        }
        nameToSource.put(descriptor.getVariableName(), source);
    }


    private void buildSources(
            QueryExecutionPlanFactoryContext context) {

        for (SourceDescriptor descriptor : this.qepDescriptor.getSourceDescriptors()) {
            this.buildSource(context.isContinuous(), descriptor, context.getNameToSource());
        }
    }


    private WindowBuilder buildTimeWindowBuilder(
            final WindowBuilderDescriptor windowBuilderDescriptor,
            final TupleInfo inputTupleInfo,
            final TupleInfo outputTupleInfo)
            throws Exception {

        return new WindowBuilder() {
            public Window buildAndInit(
                    ResourceId parentResourceId,
                    String id,
                    GroupKey groupKey,
                    AggregateInfo aggregateInfo,
                    WindowInfo windowInfo,
                    WindowOwner windowOwner,
                    GroupAggregateTransformer aggregateTransformer) {

                final Window window = new TimeWindow(new ResourceId(parentResourceId, "TimeWindow:" + id),
                        inputTupleInfo, groupKey, windowOwner, (TimeWindowInfo) windowInfo);

                SubStream stream = window;

                switch (windowBuilderDescriptor.getEmitType()) {
                    case NEW: {
                        stream = new ISubStream(window, new ResourceId(window.getResourceId(), "Insert:" + id));
                    }
                    break;
                    case DEAD: {
                        stream = new DSubStream(window, new ResourceId(window.getResourceId(), "Delete:" + id));
                    }
                    break;
                }

                if (windowBuilderDescriptor.getIncludesAggregates() && (null != aggregateInfo)) {
                    stream = new AggregatedStream(stream, new ResourceId(parentResourceId, "Aggregate:" + id),
                            aggregateInfo, true);
                }

                WindowPurgeManager purgeManager =
                        plugInWindowPurgeManager(window, windowOwner, windowBuilderDescriptor, inputTupleInfo);

                window.init(aggregateTransformer, stream, purgeManager);

                return window;
            }//buildAndInit
        };

    }

    protected WindowPurgeManager plugInWindowPurgeManager(Window window, WindowOwner windowOwner,
                                                          WindowBuilderDescriptor windowBuilderDescriptor,
                                                          TupleInfo inputTupleInfo) {
        return null;
    }

    private WindowBuilder buildTumblingWindowBuilder(
            final WindowBuilderDescriptor windowBuilderDescriptor,
            final TupleInfo inputTupleInfo,
            final TupleInfo outputTupleInfo)
            throws Exception {

        return new WindowBuilder() {
            public Window buildAndInit(
                    ResourceId parentResourceId,
                    String id,
                    GroupKey groupKey,
                    AggregateInfo aggregateInfo,
                    WindowInfo windowInfo,
                    WindowOwner windowOwner,
                    GroupAggregateTransformer aggregateTransformer) {

                final Window window = new TumblingWindow(new ResourceId(parentResourceId, "TumblingWindow:" + id),
                        inputTupleInfo, groupKey, windowOwner, (TumblingWindowInfo) windowInfo);

                SubStream stream = window;

                switch (windowBuilderDescriptor.getEmitType()) {
                    case NEW: {
                        stream = new ISubStream(window, new ResourceId(window.getResourceId(), "Insert:" + id));
                    }
                    break;
                    case DEAD: {
                        stream = new DSubStream(window, new ResourceId(window.getResourceId(), "Delete:" + id));
                    }
                    break;
                }

                if (windowBuilderDescriptor.getIncludesAggregates() && (null != aggregateInfo)) {
                    stream = new AggregatedStream(stream, new ResourceId(parentResourceId, "Aggregate:" + id),
                            aggregateInfo, true);
                }

                WindowPurgeManager purgeManager =
                        plugInWindowPurgeManager(window, windowOwner, windowBuilderDescriptor, inputTupleInfo);

                window.init(aggregateTransformer, stream, purgeManager);

                return window;
            }//buildAndInit
        };
    }


    /**
     * @param context
     *@param stream Stream @return Stream.
     * @throws Exception upon problem.
     */
    private Stream buildTransform(
            QueryExecutionPlanFactoryContext context,
            Stream stream)
            throws Exception {

        final TransformedStreamDescriptor transformDescriptor = this.qepDescriptor.getTransformDescriptor();
        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final String outputTupleInfoName = transformDescriptor.getTupleInfoFieldName();
        final TupleInfo outputTupleInfo = (TupleInfo) nameToObject.get(outputTupleInfoName);

        final String transformInfoName = transformDescriptor.getTransformInfoName();
        final TransformInfo transformInfo = (TransformInfo) nameToObject.get(transformInfoName);

        Stream transformedStream = new TransformedStream(stream,
                new ResourceId(stream.getResourceId(), transformDescriptor.getName()),
                outputTupleInfo,
                transformInfo);

        context.getBridgeInfo().inputStream = transformedStream;

        return transformedStream;
    }


    /**
     * Gets the names of the columns returned by this query.
     *
     * @return List String names of the columns returned by this query.
     */
    public List<String> getColumnNames() {
        return new ArrayList<String>(this.qepDescriptor.getColumnNameToType().keySet());
    }


    /**
     * Gets the names of the types of the columns returned by this query.
     *
     * @return List String names of the types of the columns returned by this query.
     */
    public List<String> getColumnTypeNames() {
        return new ArrayList<String>(this.qepDescriptor.getColumnNameToType().values());
    }


    /**
     * Gets the names of the sources used by this query.
     *
     * @return List String names of the sources used by this query.
     */
    public List<String> getSourceNames() {
        final LinkedList<String> nameList = new LinkedList<String>();
        for (SourceDescriptor descriptor : this.qepDescriptor.getSourceDescriptors()) {
            nameList.add(descriptor.getAlias());
        }
        return nameList;
    }


    /**
     * Creates an execution plan for a query.
     *
     * @param name         String name of the plan.
     * @param isContinuous boolean true is the plan is for a continuous execution.
     * @return QueryExecutionPlan
     * @throws Exception upon error.
     */
    public QueryExecutionPlan newQueryExecutionPlan(
            String name,
            boolean isContinuous)
            throws Exception {

        final QueryExecutionPlanFactoryContext context = new QueryExecutionPlanFactoryContext(name, isContinuous);

        this.buildSources(context);
        this.buildProxies(context);
        this.buildPartitions(context);
        this.buildFilters(context);
        Stream stream;
        stream = this.buildJoin(context);
        stream = this.buildAggregation(context, stream);
        stream = this.buildTransform(context, stream);
        stream = this.buildSort(context, stream);
        stream = this.buildDistinct(context, stream);
        stream = this.buildLimit(context, stream);
        this.buildSink(context, stream);
        this.buildBridge(context);

        return new QueryExecutionPlanImpl(this.qepDescriptor.getRegionName(),
                name,
                this.qepDescriptor.getQuery().getSourceText(),
                context.getNameToSource(),
                context.getBridgeInfo().bridge,
                context.getSink(),
                isContinuous);
                
    }

}//class
