package com.tibco.cep.query.exec.impl;

import com.tibco.be.util.idgenerators.IdentifierGenerator;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.exec.QueryExecutionPlanDescriptorFactory;
import com.tibco.cep.query.exec.descriptors.*;
import com.tibco.cep.query.exec.descriptors.impl.*;
import com.tibco.cep.query.exec.util.Comparisons;
import com.tibco.cep.query.model.*;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.stream.filter.FilteredStream;
import com.tibco.cep.query.stream.impl.aggregate.*;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.EvaluatorToExtractorAdapter;
import com.tibco.cep.query.stream.impl.expression.bindvar.BindVariableEvaluator;
import com.tibco.cep.query.stream.partition.TimeWindowInfo;
import com.tibco.cep.query.stream.partition.TupleValueExtractorToTupleTimestampExtractorAdapter;
import com.tibco.cep.query.stream.partition.WindowInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.runtime.model.TypeManager;

import java.util.*;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 8, 2007
 * Time: 4:30:19 PM
 */


/**
 * Builds execution plan descriptors.
 */
public class QueryExecutionPlanDescriptorFactoryImpl
        implements QueryExecutionPlanDescriptorFactory {


    protected static final TypeInfo TYPE_INFO_FOR_TUPLE;


    static {
        try {
            TYPE_INFO_FOR_TUPLE = new TypeInfoImpl(Tuple.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public QueryExecutionPlanDescriptorFactoryImpl() {
    }


    protected int composeAggregateItemInfos(
            QueryExecutionPlanDescriptorFactoryContext context,
            HavingClause having,
            TupleInfoDescriptor inputTupleInfoDescriptor,
            TupleInfoDescriptor aggrTupleInfoDescriptor,
            String mapName)
            throws Exception {

        final Set<AggregateFunctionIdentifier> aggregateFns = new HashSet<AggregateFunctionIdentifier>();

        final NamedSelectContext model = ((NamedSelectContext) context.getQuery().getModel());
        for (ModelContext c : model.getProjectionAttributes()
                .getDescendantContextsByType(ModelContext.CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER)) {
            aggregateFns.add((AggregateFunctionIdentifier) c);
        }
        if (null != having) {
            for (ModelContext c : having
                    .getDescendantContextsByType(ModelContext.CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER)) {
                aggregateFns.add((AggregateFunctionIdentifier) c);
            }
        }

        final TypeManager typeManager = context.getTypeManager();
        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final EvaluatorDescriptorFactory extractionFactory = builder.getEvaluatorDescriptorFactory();

        int colIndex = 0;
        for (AggregateFunctionIdentifier f : aggregateFns) {
            final String columnName = "a" + (colIndex++);
            final TypeInfo typeInfo = f.getTypeInfo().getBoxedTypeInfo(typeManager);
            final boolean distinct =
                    0 < f.getDirectDescendantContextsByType(ModelContext.CTX_TYPE_DISTINCT_CLAUSE).size();

            if (Function.AGGREGATE_FUNCTION_COUNT.equalsIgnoreCase(f.getName()) && !distinct) {
                // COUNT withouth DISTINCT does not use a TVE.
                builder.buildAggregateItemInfo(mapName, columnName, CountAggregator.CREATOR, null);

            } else {
                final EvaluatorDescriptor evaluatorDescriptor =
                        extractionFactory.newEvaluatorDescriptor(f.getArgument(0), inputTupleInfoDescriptor);
                final ExtractorDescriptor extractorDescriptor = new ExtractorDescriptor(evaluatorDescriptor);
                final String tveName = builder.buildExtractor(extractorDescriptor);//todo check

                if (Function.AGGREGATE_FUNCTION_COUNT.equalsIgnoreCase(f.getName())) {
                    builder.buildAggregateItemInfo(mapName, columnName, CountDistinctAggregator.CREATOR, tveName);

                } else if (Function.AGGREGATE_FUNCTION_AVG.equalsIgnoreCase(f.getName())) {
                    if (distinct) {
                        builder.buildAggregateItemInfo(mapName, columnName, AverageDistinctAggregator.CREATOR, tveName);
                    } else {
                        builder.buildAggregateItemInfo(mapName, columnName, AverageAggregator.CREATOR, tveName);
                    }

                } else if (Function.AGGREGATE_FUNCTION_MAX.equalsIgnoreCase(f.getName())) {
                    builder.buildAggregateItemInfo(mapName, columnName, NullsSmallestMaxAggregator.CREATOR, tveName);

                } else if (Function.AGGREGATE_FUNCTION_MIN.equalsIgnoreCase(f.getName())) {
                    builder.buildAggregateItemInfo(mapName, columnName, NullsLargestMinAggregator.CREATOR, tveName);

                } else if (Function.AGGREGATE_FUNCTION_SUM.equalsIgnoreCase(f.getName())) {
                    if (distinct) {
                        builder.buildAggregateItemInfo(mapName, columnName, DoubleSumDistinctAggregator.CREATOR,
                                tveName);
                    } else {
                        builder.buildAggregateItemInfo(mapName, columnName, DoubleSumAggregator.CREATOR, tveName);
                    }

                } else {
                    throw new Exception("Unsupported aggregate: " + f);
                }
            }//else

            aggrTupleInfoDescriptor
                    .addColumn(new TupleInfoColumnDescriptorImpl(columnName, typeInfo, f, null, typeManager));
        }//for

        return colIndex;
    }


    protected StreamDescriptor composeAggregation(
            QueryExecutionPlanDescriptorFactoryContext context,
            StreamDescriptor inputStreamDescriptor)
            throws Exception {

        final NamedSelectContext model = ((NamedSelectContext) context.getQuery().getModel());

        final GroupClause groupBy = model.getGroupClause();

        // No group by => no aggregation.
        if ((null == groupBy)
                || (groupBy.getChildCount() == 0)) {
            return inputStreamDescriptor;
        }

        final HavingClause having = groupBy.getHavingClause();
        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final TupleInfoDescriptor aggrTupleInfoDescriptor = new TupleInfoDescriptorImpl(groupBy);

        // Builds the GroupAggregateItemInfoMap.
        final String groupAggregateInfoName = this.composeGroupAggregateItemInfoMap(context, groupBy, having,
                inputTupleInfoDescriptor, aggrTupleInfoDescriptor);

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final IdentifierGenerator idGenerator = context.getIdGenerator();

        // Builds the TupleInfo field that describes the output of the aggregation stream.
        final String tupleInfoName = builder.buildTupleInfo(aggrTupleInfoDescriptor, false);

        // Adds the WindowBuilder used by the stream.
        final String windowBuilderName = "null"; //todo WindowBuilder ?

        final GroupPolicy policy = groupBy.getGroupPolicy();
        if ((null != policy) && (policy.getCaptureType() == CaptureType.NEW)) {
            // Adds an IStream to drop the deletes.
            inputStreamDescriptor = new StreamDescriptorImpl(
                    "cature:new" + idGenerator.nextIdentifier(), inputStreamDescriptor,
                    inputStreamDescriptor.getTupleInfoDescriptor(),
                    inputStreamDescriptor.getTupleInfoFieldName(), policy);
            builder.buildInsertCreation(inputStreamDescriptor);
        }

        // Adds the aggregation stream creation.
        final AggregationDescriptor aggrStreamDescriptor = new AggregationDescriptor(
                "GROUPBY" + idGenerator.nextIdentifier(), inputStreamDescriptor,
                aggrTupleInfoDescriptor, tupleInfoName, groupBy,
                ((null != policy) && (policy.getEmitType() == EmitType.FULL)), groupAggregateInfoName,
                windowBuilderName);
        builder.buildAggregationCreation(aggrStreamDescriptor);

        if (null == having) {
            return aggrStreamDescriptor;
        } else {
            return this.composeHavingFilter(context, groupBy, having,
                    aggrTupleInfoDescriptor, tupleInfoName, aggrStreamDescriptor);
        }
    }


    protected String composeAndExpression(
            QueryExecutionPlanDescriptorFactoryContext context,
            BinaryExpression andExpression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        final List<String> andArgList = new ArrayList<String>();
        this.composeAndExpressionArgumentList(context, andArgList, andExpression.getLeftExpression(),
                aliasMapDescriptor);
        this.composeAndExpressionArgumentList(context, andArgList, andExpression.getRightExpression(),
                aliasMapDescriptor);

        return context.getBuilder().buildAndExpression(
                andExpression, andArgList.toArray(new String[andArgList.size()]));
    }


    protected void composeAndExpressionArgumentList(
            QueryExecutionPlanDescriptorFactoryContext context,
            List<String> argNames,
            Expression expression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        if ((expression instanceof BinaryExpression)
                && ((BinaryExpression) expression).getOperator().getOpType() == Operator.OP_AND) {
            this.composeAndExpressionArgumentList(context, argNames,
                    ((BinaryExpression) expression).getLeftExpression(), aliasMapDescriptor);
            this.composeAndExpressionArgumentList(context, argNames,
                    ((BinaryExpression) expression).getRightExpression(), aliasMapDescriptor);

        } else if ((expression instanceof UnaryExpression)
                && (((UnaryExpression) expression).getOperator().getOpType() == Operator.OP_GROUP)) {
            this.composeAndExpressionArgumentList(context, argNames,
                    ((UnaryExpression) expression).getArgument(), aliasMapDescriptor);

        } else {
            argNames.add(this.composeExpression(context, expression, aliasMapDescriptor));
        }
    }


    /**
     * @param context             QueryExecutionPlanDescriptorFactoryContext containing the current context.
     * @param preBridgeDescriptor StreamDescriptor of the stream just before the bridge.
     * @param lastDescriptor      StreamDescriptor of the last stream after the bridge.
     * @throws Exception upon problem.
     */
    protected void composeBridgeAndSink(
            QueryExecutionPlanDescriptorFactoryContext context,
            StreamDescriptor preBridgeDescriptor,
            StreamDescriptor lastDescriptor)
            throws Exception {

        final StreamDescriptor[] postBridgeDescriptors = preBridgeDescriptor.getOutputStreamDescriptors();
        final StreamDescriptor postBridgeDescriptor;
        if ((null == postBridgeDescriptors)
                || (postBridgeDescriptors.length < 1)) {
            postBridgeDescriptor = null;
        } else {
            postBridgeDescriptor = postBridgeDescriptors[0];
        }

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final IdentifierGenerator idGenerator = context.getIdGenerator();
        final ColumnNameFactory columnNameFactory = context.getColumnNameFactory();
        final NamedSelectContext model = ((NamedSelectContext) context.getQuery().getModel());

        SinkDescriptor sinkDescriptor;
        BridgeDescriptor bridgeDescriptor;

        if ((null == postBridgeDescriptor)
                || (postBridgeDescriptor.equals(preBridgeDescriptor))) {
            final ProjectionAttributes projections = model.getProjectionAttributes();
            final boolean usePassThroughBridge =
                    (null != projections) && (projections.childrenCount() == 1) && (((Projection) (projections
                            .getChildren()[0])).getExpression() instanceof ScopedIdentifier);

            sinkDescriptor = new SinkDescriptor("SINK" + idGenerator.nextIdentifier(),
                    lastDescriptor, lastDescriptor.getTupleInfoDescriptor(), lastDescriptor.getTupleInfoFieldName(),
                    usePassThroughBridge, model);
            builder.buildSinkCreation(sinkDescriptor);

            if (usePassThroughBridge) {
                bridgeDescriptor = new PassThroughBridgeDescriptor(
                        "bridge" + idGenerator.nextIdentifier(), preBridgeDescriptor,
                        preBridgeDescriptor.getTupleInfoDescriptor(), preBridgeDescriptor.getTupleInfoFieldName(), null,
                        sinkDescriptor.getInternalStreamDescriptor());
                builder.buildPassThroughBridge(bridgeDescriptor);
            } else {
                bridgeDescriptor = new SimpleBridgeDescriptor("bridge" + idGenerator.nextIdentifier(),
                        preBridgeDescriptor, preBridgeDescriptor.getTupleInfoDescriptor(),
                        preBridgeDescriptor.getTupleInfoFieldName(), null,
                        sinkDescriptor.getInternalStreamDescriptor());
                builder.buildBridgeCreation(bridgeDescriptor);
            }

        } else if (postBridgeDescriptor instanceof SortedStreamDescriptor) {
            sinkDescriptor = new SinkDescriptor("SINK" + idGenerator.nextIdentifier(),
                    lastDescriptor, lastDescriptor.getTupleInfoDescriptor(), lastDescriptor.getTupleInfoFieldName(),
                    false, model);
            builder.buildSinkCreation(sinkDescriptor);

            bridgeDescriptor = new SortedBridgeDescriptor(
                    "bridge" + idGenerator.nextIdentifier(), preBridgeDescriptor,
                    preBridgeDescriptor.getTupleInfoDescriptor(), preBridgeDescriptor.getTupleInfoFieldName(), null,
                    postBridgeDescriptor.getOutputStreamDescriptors()[0],
                    (SortedStreamDescriptor) postBridgeDescriptor);
            builder.buildSortedBridge((SortedBridgeDescriptor) bridgeDescriptor);

        } else if (postBridgeDescriptor instanceof TruncatedStreamDescriptor) {
            sinkDescriptor = new SinkDescriptor("SINK" + idGenerator.nextIdentifier(),
                    lastDescriptor, lastDescriptor.getTupleInfoDescriptor(), lastDescriptor.getTupleInfoFieldName(),
                    false, model);
            builder.buildSinkCreation(sinkDescriptor);

            bridgeDescriptor = new TruncatedBridgeDescriptor(
                    "bridge" + idGenerator.nextIdentifier(), preBridgeDescriptor,
                    preBridgeDescriptor.getTupleInfoDescriptor(), preBridgeDescriptor.getTupleInfoFieldName(), null,
                    postBridgeDescriptor);
            builder.buildTruncatedBridge(bridgeDescriptor);

        } else {
            sinkDescriptor = new SinkDescriptor("SINK" + idGenerator.nextIdentifier(),
                    lastDescriptor, lastDescriptor.getTupleInfoDescriptor(), lastDescriptor.getTupleInfoFieldName(),
                    false, model);
            builder.buildSinkCreation(sinkDescriptor);

            bridgeDescriptor = new SimpleBridgeDescriptor("bridge" + idGenerator.nextIdentifier(),
                    preBridgeDescriptor, preBridgeDescriptor.getTupleInfoDescriptor(),
                    preBridgeDescriptor.getTupleInfoFieldName(), null, postBridgeDescriptor);
            builder.buildBridgeCreation(bridgeDescriptor);

        }

        final LinkedHashMap<String, String> columnNameToType = new LinkedHashMap<String, String>();
        for (TupleInfoColumnDescriptor column : lastDescriptor.getTupleInfoDescriptor().getColumns()) {
            columnNameToType.put(column.getName(), columnNameFactory.makeColumnClassName(column.getTypeInfo()));
            //this.columnNames.add(column.getName());
            //this.columnTypeNames.add();
            //todo ?
        }
        builder.buildOuputColumns(columnNameToType);
    }


    protected StreamDescriptor composeDeleted(
            QueryExecutionPlanDescriptorFactoryContext context,
            Stream streamDef,
            StreamDescriptor inputStreamDescriptor)
            throws Exception {

        if ((null == streamDef)
                || (streamDef.getEmitType() != EmitType.DEAD)) {
            return inputStreamDescriptor;
        }

        final StreamDescriptor descriptor = new StreamDescriptorImpl(
                "emit:dead" + context.getIdGenerator().nextIdentifier(), inputStreamDescriptor,
                inputStreamDescriptor.getTupleInfoDescriptor(),
                inputStreamDescriptor.getTupleInfoFieldName(), streamDef);

        context.getBuilder().buildDeletedCreation(descriptor);
        return descriptor;
    }


    protected StreamDescriptor composeDistinct(
            QueryExecutionPlanDescriptorFactoryContext context,
            StreamDescriptor inputStreamDescriptor)
            throws Exception {

        final DistinctClause distinct = ((NamedSelectContext) context.getQuery().getModel()).getDistinctClause();
        if (null == distinct) {
            return inputStreamDescriptor; // no DISTINCT => no extra stream
        }

        // Creates the DistinctFilteredStream.
        final DistinctFilteredStreamDescriptor descriptor = new DistinctFilteredStreamDescriptor(
                "DISTINCT" + context.getIdGenerator().nextIdentifier(), inputStreamDescriptor,
                inputStreamDescriptor.getTupleInfoDescriptor(), inputStreamDescriptor.getTupleInfoFieldName(),
                distinct);
        context.getBuilder().buildDistinctCreation(descriptor);
        return descriptor;
    }


    protected String composeEqualsExpression(
            QueryExecutionPlanDescriptorFactoryContext context,
            BinaryExpression equalsExpression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        final String leftExprName = this.composeExpression(context, equalsExpression.getLeftExpression(),
                aliasMapDescriptor);
        final String rightExprName = this.composeExpression(context, equalsExpression.getRightExpression(),
                aliasMapDescriptor);

        return context.getBuilder().buildEqualsExpression(equalsExpression, leftExprName, rightExprName);
    }


    protected String composeExpression(
            QueryExecutionPlanDescriptorFactoryContext context,
            Expression expression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        if (expression instanceof BlockExpression) {
            return this.composeExpression(context, ((BlockExpression) expression).getArgument(), aliasMapDescriptor);
        }

        if (expression instanceof BinaryExpression) {
            switch (((BinaryExpression) expression).getOperator().getOpType()) {
                case Operator.OP_AND:
                    return this.composeAndExpression(context, (BinaryExpression) expression, aliasMapDescriptor);
                case Operator.OP_EQ:
                    return this.composeEqualsExpression(context, (BinaryExpression) expression, aliasMapDescriptor);
                case Operator.OP_OR:
                    return this.composeOrExpression(context, (BinaryExpression) expression, aliasMapDescriptor);
            }

        } else if (expression instanceof UnaryExpression) {
            switch (((UnaryExpression) expression).getOperator().getOpType()) {
                case Operator.OP_NOT:
                    return this.composeNotExpression(context, (UnaryExpression) expression, aliasMapDescriptor);
            }
        }

        final EvaluatorDescriptor evaluatorDescriptor = context.getBuilder().getEvaluatorDescriptorFactory()
                .newEvaluatorDescriptor(expression, aliasMapDescriptor);
        // return this.builder.buildExtractor(evaluatorDescriptor); //todo check


        return context.getBuilder().buildSimpleExpression(aliasMapDescriptor, evaluatorDescriptor);
    }


    protected String composeGroupAggregateItemInfoMap(
            QueryExecutionPlanDescriptorFactoryContext context,
            GroupClause groupBy,
            HavingClause having,
            TupleInfoDescriptor inputTupleInfoDescriptor,
            TupleInfoDescriptor aggrTupleInfoDescriptor)
            throws Exception {

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final EvaluatorDescriptorFactory extractionFactory = builder.getEvaluatorDescriptorFactory();
        final TypeManager typeManager = context.getTypeManager();

        // Builds the LinkedHashMap of column names to GroupAggregateItemInfo.
        final String mapName = builder.buildGroupAggregateItemInfoMap();
        int colIndex = this.composeAggregateItemInfos(context, having, inputTupleInfoDescriptor,
                aggrTupleInfoDescriptor, mapName);

        // Adds all GroupItemInfo's to the map.
        for (Iterator it = groupBy.getFieldList().getChildrenIterator(); it.hasNext();) {
            final Expression e = (Expression) it.next();

            final EvaluatorDescriptor evaluatorDescriptor =
                    extractionFactory.newEvaluatorDescriptor(e, inputTupleInfoDescriptor);
            final String tveName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check
            final String columnName = "g" + (colIndex++);
            final TypeInfo typeInfo = e.getTypeInfo().getBoxedTypeInfo(typeManager);

            builder.buildGroupItemInfo(mapName, columnName, tveName);

            aggrTupleInfoDescriptor.addColumn(
                    new TupleInfoColumnDescriptorImpl(columnName, typeInfo, e, null, typeManager));
        }

        // Builds the GroupAggregateInfo
        return builder.buildGroupAggregateInfo(null, mapName);
    }


    protected StreamDescriptor composeHavingFilter(
            QueryExecutionPlanDescriptorFactoryContext context,
            GroupClause groupBy,
            HavingClause having,
            TupleInfoDescriptor aggrTupleInfoDescriptor,
            String tupleInfoFieldName,
            AggregationDescriptor aggrStreamDescriptor)
            throws Exception {

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final EvaluatorDescriptorFactory extractionFactory = builder.getEvaluatorDescriptorFactory();
        final IdentifierGenerator idGenerator = context.getIdGenerator();

        final Expression havingExpression = having.getExpression();
        final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(groupBy);
        aliasMapDescriptor.addColumn(new AliasMapEntryDescriptor(FilteredStream.DEFAULT_STREAM_ALIAS,
                new TypeInfoImpl(Tuple.class), aggrStreamDescriptor.getTupleInfoDescriptor().getTupleClassName(), null,
                aggrTupleInfoDescriptor));

        // Creates expression for the HAVING filter.
        final EvaluatorDescriptor evaluatorDescriptor =
                extractionFactory.newEvaluatorDescriptor(havingExpression, aliasMapDescriptor);
        final String filterExpressionName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check

        // Adds the Filter that implements HAVING.
        final FilterDescriptor descriptor = new FilterDescriptor(
                "HAVING" + idGenerator.nextIdentifier(), aggrStreamDescriptor,
                aggrTupleInfoDescriptor, tupleInfoFieldName, having,
                filterExpressionName);
        builder.buildHavingFilterCreation(descriptor);
        return descriptor;
    }


    protected StreamDescriptor composeJoin(
            QueryExecutionPlanDescriptorFactoryContext context,
            List<StreamDescriptor> inputStreamDescriptors)
            throws Exception {

        final WhereClause where = context.getQuery().getModel().getContext().getWhereClause();

        final Expression expression;
        if ((null == where)
                || (where.getChildCount() < 1)) {
            expression = null;
        } else {
            expression = (Expression) where.getChildrenIterator().next();
        }

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final EvaluatorDescriptorFactory extractionFactory = builder.getEvaluatorDescriptorFactory();
        final IdentifierGenerator idGenerator = context.getIdGenerator();

        switch (inputStreamDescriptors.size()) {
            case 0:
                throw new IllegalArgumentException();

            case 1: {
                final StreamDescriptor inputStreamDescriptor = inputStreamDescriptors.get(0);
                if (null == expression) {
                    return inputStreamDescriptor; // Single stream without WHERE => done.
                }

                // Else single stream with WHERE => will replace the join with a filter.
                final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
                final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(null);
                aliasMapDescriptor.addColumn(new AliasMapEntryDescriptor(FilteredStream.DEFAULT_STREAM_ALIAS,
                        TYPE_INFO_FOR_TUPLE, inputTupleInfoDescriptor.getTupleClassName(), null,
                        inputTupleInfoDescriptor));

                // Creates the expression for the filter.
                final EvaluatorDescriptor evaluatorDescriptor =
                        extractionFactory.newEvaluatorDescriptor(where.getExpression(), aliasMapDescriptor);
                final String filterExpressionName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));
// final String filterExpressionName = this.builder.createSimpleExpression(where.getExpression(), aliasMapDescriptor);
//todo check

                // Adds the Filter.
                final FilterDescriptor descriptor = new FilterDescriptor(
                        "WHERE" + idGenerator.nextIdentifier(), inputStreamDescriptor,
                        inputTupleInfoDescriptor,
                        inputStreamDescriptor.getTupleInfoFieldName(), where, filterExpressionName);
                builder.buildFilter(descriptor);
                return descriptor;
            }

            default: // Multiple streams => must join => continue.
        }

        final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(where);
        final TupleInfoDescriptor tupleInfoDescriptor = new TupleInfoDescriptorImpl(where);
        for (StreamDescriptor streamDescriptor : inputStreamDescriptors) {
            final String streamName = streamDescriptor.getName();
            final TupleInfoDescriptor tid = streamDescriptor.getTupleInfoDescriptor();
            final String tupleClassName = tid.getTupleClassName();
            final TypeInfo typeInfo = new TypeInfoImpl(tid.getTupleClass()); //TYPE_INFO_FOR_TUPLE;
            aliasMapDescriptor
                    .addColumn(new AliasMapEntryDescriptor(streamName, typeInfo, tupleClassName, null, tid));
            tupleInfoDescriptor.addColumn(
                    new TupleInfoColumnDescriptorImpl(streamName, typeInfo, tupleClassName, null, tid));
        }

        // Adds the TupleInfo field that describes the output of the Join.
        final String tupleInfoFieldName = builder.buildJoinedTupleInfo(tupleInfoDescriptor);

        // Adds the ComplexExpression used by the Join.
        final String expressionFieldName = this.composeJoinExpression(context, expression, aliasMapDescriptor);

        // Adds the join creation.
        final JoinDescriptor descriptor = new JoinDescriptor("WHERE" + idGenerator.nextIdentifier(),
                new ArrayList<StreamDescriptor>(inputStreamDescriptors).toArray(
                        new StreamDescriptor[inputStreamDescriptors.size()]),
                null, tupleInfoDescriptor, tupleInfoFieldName, where, expressionFieldName);

        builder.buildJoinCreation(descriptor);
        return descriptor;
    }


    protected String composeJoinExpression(
            QueryExecutionPlanDescriptorFactoryContext context,
            Expression expression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();

        if (null == expression) {
            return builder.buildAndExpression(null, null);
        }

        final String expressionName = this.composeExpression(context, expression, aliasMapDescriptor);

        if (expression instanceof BinaryExpression) {
            switch (((BinaryExpression) expression).getOperator().getOpType()) {
                case Operator.OP_AND:
                case Operator.OP_EQ:
                case Operator.OP_OR:
                    return expressionName;
            }
        } else if (expression instanceof UnaryExpression) {
            switch (((UnaryExpression) expression).getOperator().getOpType()) {
                case Operator.OP_NOT:
                    return expressionName;
            }
        }

        return builder.buildAndExpression(expression, new String[]{expressionName});
    }


    protected StreamDescriptor composeLimit(
            QueryExecutionPlanDescriptorFactoryContext context,
            StreamDescriptor inputStreamDescriptor)
            throws Exception {

        final NamedSelectContext model = ((NamedSelectContext) context.getQuery().getModel());
        Object first = model.getLimitFirst();
        Object offset = model.getLimitOffset();
        if (null == first) {
            if (null == offset) {
                return inputStreamDescriptor; // No limit set => no need for TruncatedStream.
            }
            first = Integer.MAX_VALUE; // Default first.
        }
        if (null == offset) {
            offset = 0; // Default offset.
        }

        final String firstFieldName;
        if (first instanceof BindVariable) {
            final BindVariable bv = (BindVariable) first;
            firstFieldName = context.getBuilder().buildEvaluator(
                    new EvaluatorDescriptor(new BindVariableEvaluator(bv.getLabel()), bv.getTypeInfo()));
        } else {
            firstFieldName = context.getBuilder().buildEvaluator(
                    new EvaluatorDescriptor(new ConstantValueEvaluator(first), TypeInfoImpl.INTEGER));
        }

        final String offsetFieldName;
        if (offset instanceof BindVariable) {
            final BindVariable bv = (BindVariable) offset;
            offsetFieldName = context.getBuilder().buildEvaluator(
                    new EvaluatorDescriptor(new BindVariableEvaluator(bv.getLabel()), bv.getTypeInfo()));
        } else {
            offsetFieldName = context.getBuilder().buildEvaluator(
                    new EvaluatorDescriptor(new ConstantValueEvaluator(offset), TypeInfoImpl.INTEGER));
        }

        // Creates the TruncatedStream.
        final TruncatedStreamDescriptor descriptor = new TruncatedStreamDescriptor(
                "LIMIT" + context.getIdGenerator().nextIdentifier(), inputStreamDescriptor,
                inputStreamDescriptor.getTupleInfoDescriptor(), inputStreamDescriptor.getTupleInfoFieldName(), model,
                firstFieldName, offsetFieldName);
        context.getBuilder().buildLimitCreation(descriptor);
        return descriptor;
    }


    protected String composeNotExpression(
            QueryExecutionPlanDescriptorFactoryContext context,
            UnaryExpression notExpression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        Expression child = notExpression;
        boolean useNot = true;
        for (;;) {
            child = (Expression) child.getChildrenIterator().next();
            if (child instanceof UnaryExpression) {
                switch (((UnaryExpression) child).getOperator().getOpType()) {
                    case Operator.OP_NOT:
						useNot = !useNot;
                    case Operator.OP_GROUP: 
						continue;
                }
            }
            break;
        }
        final String localChildExpressionName = this.composeExpression(context, child, aliasMapDescriptor);

        if (useNot) {
            return context.getBuilder().buildNotExpression(notExpression, localChildExpressionName);
        } else {
            return localChildExpressionName;
        }
    }


    protected String composeOrExpression(
            QueryExecutionPlanDescriptorFactoryContext context,
            BinaryExpression orExpression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        final List<String> argList = new ArrayList<String>();
        this.composeOrExpressionArgumentList(context, argList, orExpression.getLeftExpression(), aliasMapDescriptor);
        this.composeOrExpressionArgumentList(context, argList, orExpression.getRightExpression(), aliasMapDescriptor);

        return context.getBuilder().buildOrExpression(orExpression, argList.toArray(new String[argList.size()]));
    }


    protected void composeOrExpressionArgumentList(
            QueryExecutionPlanDescriptorFactoryContext context,
            List<String> argNames,
            Expression expression,
            AliasMapDescriptor aliasMapDescriptor)
            throws Exception {

        if ((expression instanceof BinaryExpression) && (((BinaryExpression) expression).getOperator().getOpType()
                == Operator.OP_OR)) {
            this.composeOrExpressionArgumentList(context, argNames,
                    ((BinaryExpression) expression).getLeftExpression(), aliasMapDescriptor);
            this.composeOrExpressionArgumentList(context, argNames,
                    ((BinaryExpression) expression).getRightExpression(), aliasMapDescriptor);

        } else if ((expression instanceof UnaryExpression)
                && (((UnaryExpression) expression).getOperator().getOpType() == Operator.OP_GROUP)) {
            this.composeOrExpressionArgumentList(context, argNames,
                    ((UnaryExpression) expression).getArgument(), aliasMapDescriptor);

        } else {
            argNames.add(this.composeExpression(context, expression, aliasMapDescriptor));
        }
    }


    protected StreamDescriptor composeSort(
            QueryExecutionPlanDescriptorFactoryContext context,
            StreamDescriptor inputStreamDescriptor)
            throws Exception {

        final OrderClause orderBy = ((NamedSelectContext) context.getQuery().getModel()).getOrderClause();

        if ((null == orderBy)
                || (orderBy.getChildCount() == 0)) {
            return inputStreamDescriptor; // = No Sort.
        }

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final EvaluatorDescriptorFactory extractionFactory = builder.getEvaluatorDescriptorFactory();
        final IdentifierGenerator idGenerator = context.getIdGenerator();

        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final List<String> sortItemInfos = new ArrayList<String>();
        final List<String> extractors = new ArrayList<String>();
        final List<Comparator<Object>> comparators = new ArrayList<Comparator<Object>>();
        for (SortCriterion sortCriterion : orderBy.getSortCriteria()) {
            final boolean isAscending = (sortCriterion.getDirection() == SortCriterion.Direction.ASC);
            final Expression expr = sortCriterion.getExpression();
            if (sortCriterion.usesNonDefaultLimit()) {
                final Object first = sortCriterion.getLimitFirst();
                final String firstFieldName;
                if (first instanceof BindVariable) {
                    final BindVariable bv = (BindVariable) first;
                    firstFieldName = context.getBuilder().buildEvaluator(
                            new EvaluatorDescriptor(new BindVariableEvaluator(bv.getLabel()), bv.getTypeInfo()));
                } else {
                    firstFieldName = context.getBuilder().buildEvaluator(
                            new EvaluatorDescriptor(new ConstantValueEvaluator(first), TypeInfoImpl.INTEGER));
                }
                final Object offset = sortCriterion.getLimitOffset();
                final String offsetFieldName;
                if (offset instanceof BindVariable) {
                    final BindVariable bv = (BindVariable) offset;
                    offsetFieldName = context.getBuilder().buildEvaluator(
                            new EvaluatorDescriptor(new BindVariableEvaluator(bv.getLabel()), bv.getTypeInfo()));
                } else {
                    offsetFieldName = context.getBuilder().buildEvaluator(
                            new EvaluatorDescriptor(new ConstantValueEvaluator(offset), TypeInfoImpl.INTEGER));
                }
                sortItemInfos.add(builder.buildSortItemInfo(sortCriterion, isAscending,
                        firstFieldName, offsetFieldName));
            } else {
                sortItemInfos.add(builder.buildSortItemInfo(sortCriterion, isAscending));
            }
            final EvaluatorDescriptor evaluatorDescriptor =
                    extractionFactory.newEvaluatorDescriptor(expr, inputTupleInfoDescriptor);
            final String tveFieldName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check
            extractors.add(tveFieldName);
//            extractors.add(this.builder.createTupleValueExtractor(expr, inputTupleInfoDescriptor));
            comparators.add(this.getComparator(expr.getTypeInfo())); //todo fix
        }

        final String extractorsName = builder.buildSortInfoExtractors(extractors);
        final String comparatorsName = builder.buildSortInfoComparators(comparators);
        final String sortInfoName = builder.buildSortInfo(orderBy, sortItemInfos);

        // Creates the Sort.
        final SortedStreamDescriptor descriptor = new SortedStreamDescriptor(
                "SORT" + idGenerator.nextIdentifier(), inputStreamDescriptor,
                inputTupleInfoDescriptor, inputStreamDescriptor.getTupleInfoFieldName(), orderBy, sortInfoName,
                extractorsName, comparatorsName);
        builder.buildSortCreation(descriptor);
        return descriptor;
    }


    protected StreamDescriptor composeSourceFromEntity(
            QueryExecutionPlanDescriptorFactoryContext context,
            AliasedIdentifier aliasedId,
            AcceptType acceptType)
            throws Exception {

        final Entity entity = (Entity) aliasedId.getIdentifiedContext();

        final TupleInfoDescriptorImpl tupleInfoDescriptor = new TupleInfoDescriptorImpl(aliasedId);
        tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(entity.getEntityPath(), entity.getTypeInfo(),
                aliasedId, null, context.getTypeManager()));

        // Adds the ReteEntityInfo field that describes the output of the Source.
        final String alias = aliasedId.getAlias();
        final String tupleInfoName = context.getBuilder().buildReteEntityInfo(tupleInfoDescriptor);

        final SourceDescriptor descriptor =
                new SourceDescriptor("SRC_" + alias, tupleInfoDescriptor, tupleInfoName, aliasedId, alias, acceptType);

        // Adds a Source creation.
        context.getBuilder().buildSourceCreation(descriptor);
        return descriptor;
    }


    protected StreamDescriptor composeSourceProxyFromEntity(
            QueryExecutionPlanDescriptorFactoryContext context,
            AliasedIdentifier aliasedId,
            StreamDescriptor sourceDescriptor)
            throws Exception {

        final Entity entity = (Entity) aliasedId.getIdentifiedContext();

        final TupleInfoDescriptorImpl tupleInfoDescriptor = new TupleInfoDescriptorImpl(aliasedId);
        tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(entity.getEntityPath(), entity.getTypeInfo(),
                aliasedId, null, context.getTypeManager()));

        // Associates the Tuple class of the source to the descriptor of the TupleInfo of the proxy.
        tupleInfoDescriptor.setTupleClass(sourceDescriptor.getTupleInfoDescriptor().getTupleClass());
        //tupleInfoDescriptor.setTupleClassName(sourceDescriptor.getTupleInfoDescriptor().getTupleClassName());

        // Adds a Proxy creation.
        final String tupleInfoFieldName = sourceDescriptor.getTupleInfoFieldName();
        final ProxyDescriptor descriptor = new ProxyDescriptor(
                "PROXY_" + aliasedId.getAlias() + "_" + context.getIdGenerator().nextIdentifier(), null,
                tupleInfoDescriptor, tupleInfoFieldName, aliasedId,
                sourceDescriptor.getVariableName());
        context.getBuilder().buildProxyCreation(descriptor);
        return descriptor;
    }


    protected List<StreamDescriptor> composeSources(
            QueryExecutionPlanDescriptorFactoryContext context)
            throws Exception {

        final List<StreamDescriptor> streamDescriptors = new LinkedList<StreamDescriptor>();
        final Map<ModelContext, StreamDescriptor> ctxToStreamDescriptor = new HashMap<ModelContext, StreamDescriptor>();

        final NamedSelectContext model = ((NamedSelectContext) context.getQuery().getModel());
        for (Iterator it = model.getFromClause().getChildrenIterator(); it.hasNext();) {
            final AliasedIdentifier aliasedId = (AliasedIdentifier) it.next();
            final ModelContext identifiedCtx = aliasedId.getIdentifiedContext();
            final Stream streamDef = aliasedId.getStream();

            StreamDescriptor descriptor = ctxToStreamDescriptor.get(identifiedCtx);

            if (identifiedCtx instanceof Entity) {
                boolean useProxy = false;

                if (null != descriptor) {
                    StreamDescriptor otherDescriptor = descriptor;
                    while (!(otherDescriptor.getContext() instanceof AliasedIdentifier)) {
                        otherDescriptor = otherDescriptor.getInputStreamDescriptors()[0];
                    }
                    if (aliasedId.equals(otherDescriptor.getContext())) {
                        final Stream otherStreamDef = ((AliasedIdentifier) otherDescriptor.getContext()).getStream();
                        if (null == streamDef) {
                            useProxy = (null == otherStreamDef);
                        } else {
                            useProxy = (streamDef.equals(otherStreamDef));
                        }
                    }
                }

                if (useProxy) {
                    descriptor = this.composeSourceProxyFromEntity(context, aliasedId, descriptor);

                } else {
                    if (null == streamDef) {
                        descriptor = this.composeSourceFromEntity(context, aliasedId, AcceptType.ALL);

                    } else {
                        descriptor = this.composeSourceFromEntity(context, aliasedId, streamDef.getAcceptType());

                        final StreamPolicy policy = streamDef.getPolicy();
                        if (null != policy) {
                            descriptor = this.composeStreamPolicy(context, streamDef, descriptor);

                        } else if (streamDef.getEmitType() == EmitType.DEAD) {
                            descriptor = this.composeDeleted(context, streamDef, descriptor);
                        }
                    }
                    ctxToStreamDescriptor.put(identifiedCtx, descriptor);
                }

            } else {
                // todo: take care of subselects and piped queries/statements
                throw new Exception("Unsupported FROM item type: " + identifiedCtx.getClass().getCanonicalName());
            }

            streamDescriptors.add(descriptor);
        }

        return streamDescriptors;
    }


    protected StreamDescriptor composeStreamPolicy(
            QueryExecutionPlanDescriptorFactoryContext context,
            Stream streamDef,
            StreamDescriptor inputStreamDescriptor)
            throws Exception {

        if (null == streamDef) {
            return inputStreamDescriptor;
        }

        final StreamPolicy policy = streamDef.getPolicy();
        if (null == policy) {
            return inputStreamDescriptor;
        }

        final WhereClause where = policy.getWhereClause();
        if (null != where) {
            final Expression whereExpr = where.getExpression();
            if (null != whereExpr) {
               this.composeStreamPolicyWhere(context, inputStreamDescriptor, where, whereExpr);
            }
        }

        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final String inputTupleInfoName = inputStreamDescriptor.getTupleInfoFieldName();

        final StreamPolicyBy by = policy.getByClause();
        final String groupAggregateInfoName = this.composeStreamPolicyBy(context, inputTupleInfoDescriptor, by);

        final WindowBuilderDescriptor windowBuilderDescriptor =
                this.composeStreamPolicyWindow(context, streamDef, inputStreamDescriptor, policy.getWindow());

        // Adds the partition stream creation.
        final PartitionDescriptor descriptor = new PartitionDescriptor(
                "policy:by" + context.getIdGenerator().nextIdentifier(), inputStreamDescriptor,
                inputTupleInfoDescriptor, inputTupleInfoName, by,
                groupAggregateInfoName, windowBuilderDescriptor);
        context.getBuilder().buildPartitionCreation(descriptor);

        return descriptor;
    }


    protected String composeStreamPolicyBy(
            QueryExecutionPlanDescriptorFactoryContext context,
            TupleInfoDescriptor inputTupleInfoDescriptor,
            StreamPolicyBy by)
            throws Exception {

        if (null == by) {
            return null;

        } else {
            final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
            final EvaluatorDescriptorFactory extractionFactory = builder.getEvaluatorDescriptorFactory();

            // Adds the LinkedHashMap of column names to GroupAggregateItemInfo.
            final String mapName = builder.buildGroupAggregateItemInfoMap();

            // Adds all GroupItemInfo's to the map.
            int colIndex = 0;
            for (Expression e : by.getExpressions()) {
                final EvaluatorDescriptor evaluatorDescriptor =
                        extractionFactory.newEvaluatorDescriptor(e, inputTupleInfoDescriptor);
                final String tveFieldName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));
                final String columnName = "by" + (colIndex++);
                // Column values are always boxed since they are obtained from TVE's.
                builder.buildGroupItemInfo(mapName, columnName, tveFieldName);
            }

            // Adds the GroupAggregateInfo used by the partition stream.
            return builder.buildGroupAggregateInfo(null, mapName);
        }
    }


    protected StreamDescriptor composeStreamPolicyWhere(
            QueryExecutionPlanDescriptorFactoryContext context,
            StreamDescriptor inputStreamDescriptor,
            WhereClause where,
            Expression whereExpr)
            throws Exception {

        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final String inputTupleInfoName = inputStreamDescriptor.getTupleInfoFieldName();
        final String inputTupleClassName = inputTupleInfoDescriptor.getTupleClassName();

        final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(where);
        aliasMapDescriptor.addColumn(new AliasMapEntryDescriptor(FilteredStream.DEFAULT_STREAM_ALIAS,
                new TypeInfoImpl(Tuple.class), inputTupleClassName, null, inputTupleInfoDescriptor));

        final EvaluatorDescriptor evaluatorDescriptor = context.getBuilder().getEvaluatorDescriptorFactory()
                .newEvaluatorDescriptor(whereExpr, aliasMapDescriptor);
        final String filterExpressionName = context.getBuilder().buildExtractor(
                new ExtractorDescriptor(evaluatorDescriptor));//todo check

//                final String filterExpressionName = this.builder.createSimpleExpression(whereExpr, aliasMapDescriptor);

        final FilterDescriptor filterDescriptor = new FilterDescriptor(
                "policy:where" + context.getIdGenerator().nextIdentifier(), inputStreamDescriptor,
                inputTupleInfoDescriptor, inputTupleInfoName, where,
                filterExpressionName);

        context.getBuilder().buildFilter(filterDescriptor);
        return filterDescriptor;
    }


    protected WindowBuilderDescriptor composeStreamPolicyWindow(
            QueryExecutionPlanDescriptorFactoryContext context,
            Stream streamDef,
            StreamDescriptor inputStreamDescriptor,
            Window window)
            throws Exception {

        final String inputTupleInfoName = inputStreamDescriptor.getTupleInfoFieldName();

        WindowBuilderDescriptor windowBuilderDescriptor;
        if (window instanceof SlidingWindow) {
            final WindowInfo windowInfo =
                    context.getBuilder().buildSlidingWindowInfo(null, ((SlidingWindow) window).getMaxSize());
            windowBuilderDescriptor = new WindowBuilderDescriptorImpl(WindowBuilderDescriptor.Type.SLIDING,
                    inputTupleInfoName, inputTupleInfoName, streamDef.getEmitType(), false, windowInfo);
        } else if (window instanceof TimeWindow) {
            final Expression usingExpression = ((TimeWindow) window).getUsingProperty();
            final TimeWindowInfo.TupleTimestampExtractor timestampExtractor;
            if (null == usingExpression) {
                timestampExtractor = null;
            } else {
                final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
                final EvaluatorDescriptor evaluatorDescriptor = builder.getEvaluatorDescriptorFactory()
                        .newEvaluatorDescriptor(usingExpression, inputStreamDescriptor.getTupleInfoDescriptor());
                timestampExtractor = new TupleValueExtractorToTupleTimestampExtractorAdapter(
                        new EvaluatorToExtractorAdapter(evaluatorDescriptor.getEvaluator()));
            }
            final WindowInfo windowInfo =
                    context.getBuilder().buildTimeWindowInfo(null, Integer.MAX_VALUE,
                            ((TimeWindow) window).getMaxTimeInMs(),
                            timestampExtractor);
            windowBuilderDescriptor = new WindowBuilderDescriptorImpl(WindowBuilderDescriptor.Type.TIME,
                    inputTupleInfoName, inputTupleInfoName, streamDef.getEmitType(), false, windowInfo);
        } else if (window instanceof TumblingWindow) {
            final WindowInfo windowInfo =
                    context.getBuilder().buildTumblingWindowInfo(null, ((TumblingWindow) window).getMaxSize());
            windowBuilderDescriptor = new WindowBuilderDescriptorImpl(WindowBuilderDescriptor.Type.TUMBLING,
                    inputTupleInfoName, inputTupleInfoName, streamDef.getEmitType(), false, windowInfo);
        } else {
            throw new Exception("Unsupported window type: " + window);
        }
        return windowBuilderDescriptor;
    }


    protected StreamDescriptor composeTransformation(
            QueryExecutionPlanDescriptorFactoryContext context,
            StreamDescriptor inputStreamDescriptor)
            throws Exception {

        final LinkedHashMap<String, String> projAliasToTveName = new LinkedHashMap<String, String>();

        final NamedSelectContext model = ((NamedSelectContext) context.getQuery().getModel());
        final ProjectionAttributes projAttributes = model.getProjectionAttributes();
        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final TupleInfoDescriptorImpl tupleInfoDescriptor = new TupleInfoDescriptorImpl(projAttributes);

        final QueryExecutionPlanDescriptorBuilder builder = context.getBuilder();
        final EvaluatorDescriptorFactory extractionFactory = builder.getEvaluatorDescriptorFactory();
        final IdentifierGenerator idGenerator = context.getIdGenerator();
        final ColumnNameFactory columnNameFactory = context.getColumnNameFactory();
        final TypeManager typeManager = context.getTypeManager();

        for (Projection projection : projAttributes.getAllProjections()) {
            final Expression expr = projection.getExpression();

            if (expr instanceof ScopedIdentifier) {
                for (ModelContext mc : ((ScopedIdentifier) expr).getIdentifiedContexts()) {
                    String tveFieldName;
                    if (mc instanceof Expression) {
                        final EvaluatorDescriptor evaluatorDescriptor = extractionFactory
                                .newEvaluatorDescriptor((Expression) mc, inputTupleInfoDescriptor);
                        tveFieldName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check
//                        tveFieldName = this.builder.createTupleValueExtractor((Expression) mc, inputTupleInfoDescriptor);
                    } else if (mc instanceof ProxyContext) {
                        final EvaluatorDescriptor evaluatorDescriptor = extractionFactory
                                .newEvaluatorDescriptor((ProxyContext) mc, inputTupleInfoDescriptor);
                        tveFieldName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check
//                        tveFieldName = this.builder.createTupleValueExtractor((ProxyContext) mc, inputTupleInfoDescriptor);
                    } else {
                        throw new Exception("Unexpected context in *: " + mc);
                    }

                    final String columnName = columnNameFactory.makeColumnName(mc, projection, tupleInfoDescriptor);
                    projAliasToTveName.put(columnName, tveFieldName);
                    tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(columnName,
                            ((TypedContext) mc).getTypeInfo(), mc, null, typeManager));
                }

            } else if (expr.getIdentifiedContext() instanceof ScopedIdentifier) {
                for (ModelContext mc : ((ScopedIdentifier) expr.getIdentifiedContext()).getIdentifiedContexts()) {
                    String tveFieldName;
                    if (mc instanceof Expression) {
                        final EvaluatorDescriptor evaluatorDescriptor = extractionFactory
                                .newEvaluatorDescriptor((Expression) mc, inputTupleInfoDescriptor);
                        tveFieldName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check
//                        tveFieldName = this.builder.createTupleValueExtractor((Expression) mc, inputTupleInfoDescriptor);
                    } else if (mc instanceof ProxyContext) {
                        final EvaluatorDescriptor evaluatorDescriptor = extractionFactory
                                .newEvaluatorDescriptor((ProxyContext) mc, inputTupleInfoDescriptor);
                        tveFieldName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check
//                        tveFieldName = this.builder.createTupleValueExtractor((ProxyContext) mc, inputTupleInfoDescriptor);
                    } else {
                        throw new Exception("Unexpected context in *: " + mc);
                    }
                    final String columnName = columnNameFactory.makeColumnName(mc, projection, tupleInfoDescriptor);
                    projAliasToTveName.put(columnName, tveFieldName);
                    tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(columnName,
                            ((TypedContext) mc).getTypeInfo(), mc, null, typeManager));
                }

            } else {
                final EvaluatorDescriptor evaluatorDescriptor =
                        extractionFactory.newEvaluatorDescriptor(expr, inputTupleInfoDescriptor);
                final String tveFieldName = builder.buildExtractor(new ExtractorDescriptor(evaluatorDescriptor));//todo check
//                final String tveFieldName = this.builder.createTupleValueExtractor(expr, inputTupleInfoDescriptor);
                final String columnName = columnNameFactory.makeColumnName(expr, projection, tupleInfoDescriptor);
                projAliasToTveName.put(columnName, tveFieldName);
                tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(columnName, expr.getTypeInfo(), expr,
                        null, typeManager));
            }
        }

        // Adds a TupleInfo field that describes the output of the Transformation.
        final String tupleInfoName = builder.buildTupleInfo(tupleInfoDescriptor, false);

        // Adds a LinkedHashMap field that binds projection aliases to TVEs.
        final String transfMapName = builder.buildTransformMap(projAliasToTveName);

        // Adds a TransformInfo field that represents all the projections.
        final String transfInfoName = builder.buildTransformInfo(transfMapName);

        // Creates the TransformedStream.
        final TransformedStreamDescriptor descriptor = new TransformedStreamDescriptor(
                "TRANSF" + idGenerator.nextIdentifier(), inputStreamDescriptor, tupleInfoDescriptor,
                tupleInfoName, projAttributes, transfInfoName);
        builder.buildTransformCreation(descriptor);
        return descriptor;
    }


    protected Comparator<Object> getComparator(TypeInfo typeInfo) {
        if (typeInfo.isComparable()
                || typeInfo.isJavaPrimitiveType()) {
            return Comparisons.Comparators.DEFAULT;

        } else if (typeInfo.isEntity()) {
            return Comparisons.Comparators.ENTITIES;

        } else {
            throw new IllegalArgumentException("Type is not comparable.");
        }
    }


    /**
     * Creates a descriptor of execution plan for a query.
     *
     * @param query Query for which a plan descriptor will be built.
     * @param regionName String name of the region queried.
     * @return QueryExecutionPlanDescriptor
     * @throws Exception upon error.
     */
    public QueryExecutionPlanDescriptor newQueryExecutionPlanDescriptor(
            Query query,
            String regionName)
            throws Exception {

        final QueryExecutionPlanDescriptorFactoryContext context =
                new QueryExecutionPlanDescriptorFactoryContext(query, regionName);

        final ClassLoader initialClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(query.getExecutionClassLoader());
        try {
            if (!(query.getModel().getContext() instanceof DeleteContext)) {
                final List<StreamDescriptor> sourceDescriptors = this.composeSources(context);

                StreamDescriptor preBridgeDescriptor;
                preBridgeDescriptor = this.composeJoin(context, sourceDescriptors);
                preBridgeDescriptor = this.composeAggregation(context, preBridgeDescriptor);
                preBridgeDescriptor = this.composeTransformation(context, preBridgeDescriptor);

                StreamDescriptor postBridgeDescriptor;
                postBridgeDescriptor = this.composeSort(context, preBridgeDescriptor);
                postBridgeDescriptor = this.composeDistinct(context, postBridgeDescriptor);
                postBridgeDescriptor = this.composeLimit(context, postBridgeDescriptor);

                this.composeBridgeAndSink(context, preBridgeDescriptor, postBridgeDescriptor);

                context.getBuilder().optimize();
            } else {
                DeleteContext deleteContext = ((DeleteContext) query.getModel().getContext());
                AliasedIdentifier aliasedIdentifier;
                if (deleteContext.getFromClause().getChildCount() == 1) {
                    aliasedIdentifier = (AliasedIdentifier) deleteContext.getFromClause().getChildren()[0];
                } else {
                    throw new QueryException("Delete query should have one source.");
                }
                StreamDescriptor streamDescriptor = this.composeSourceFromEntity(context, aliasedIdentifier, AcceptType.ALL);
                List<StreamDescriptor> sourceDescriptors = Arrays.asList(new StreamDescriptor[]{streamDescriptor});
                this.composeJoin(context, sourceDescriptors);
            }
        } finally {
            Thread.currentThread().setContextClassLoader(initialClassLoader);
        }

        return context.getBuilder().getQueryExecutionPlanDescriptor();
    }


}
