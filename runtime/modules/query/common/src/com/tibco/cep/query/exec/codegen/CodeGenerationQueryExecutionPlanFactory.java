package com.tibco.cep.query.exec.codegen;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.exec.QueryExecutionPlanFactory;
import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoColumnDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.AliasMapDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.AliasMapEntryDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.BridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.DistinctFilteredStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.PassThroughBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SimpleBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SinkDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SortedBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SortedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SourceDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.StreamDescriptorImpl;
import com.tibco.cep.query.exec.descriptors.impl.TransformedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TruncatedBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TruncatedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TupleInfoColumnDescriptorImpl;
import com.tibco.cep.query.exec.descriptors.impl.TupleInfoDescriptorImpl;
import com.tibco.cep.query.exec.util.Comparisons;
import com.tibco.cep.query.model.AcceptType;
import com.tibco.cep.query.model.AggregateFunctionIdentifier;
import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.BindVariable;
import com.tibco.cep.query.model.CaptureType;
import com.tibco.cep.query.model.DistinctClause;
import com.tibco.cep.query.model.EmitType;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityAttribute;
import com.tibco.cep.query.model.EntityAttributeProxy;
import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.EntityPropertyProxy;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.GroupClause;
import com.tibco.cep.query.model.GroupPolicy;
import com.tibco.cep.query.model.HavingClause;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedSelectContext;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.OrderClause;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProjectionAttributes;
import com.tibco.cep.query.model.ProxyContext;
import com.tibco.cep.query.model.ScopedIdentifier;
import com.tibco.cep.query.model.SlidingWindow;
import com.tibco.cep.query.model.SortCriterion;
import com.tibco.cep.query.model.Stream;
import com.tibco.cep.query.model.StreamPolicy;
import com.tibco.cep.query.model.StreamPolicyBy;
import com.tibco.cep.query.model.TimeWindow;
import com.tibco.cep.query.model.TumblingWindow;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.TypeNames;
import com.tibco.cep.query.model.TypedContext;
import com.tibco.cep.query.model.UnaryExpression;
import com.tibco.cep.query.model.WhereClause;
import com.tibco.cep.query.model.Window;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.stream.filter.FilteredStream;
import com.tibco.cep.query.stream.impl.aggregate.AverageAggregator;
import com.tibco.cep.query.stream.impl.aggregate.AverageDistinctAggregator;
import com.tibco.cep.query.stream.impl.aggregate.CountAggregator;
import com.tibco.cep.query.stream.impl.aggregate.CountDistinctAggregator;
import com.tibco.cep.query.stream.impl.aggregate.DoubleSumAggregator;
import com.tibco.cep.query.stream.impl.aggregate.DoubleSumDistinctAggregator;
import com.tibco.cep.query.stream.impl.aggregate.NullsLargestMinAggregator;
import com.tibco.cep.query.stream.impl.aggregate.NullsSmallestMaxAggregator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.runtime.model.TypeManager;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 8, 2007
 * Time: 4:30:19 PM
 */


/**
 * Builds QueryExecutionPlan instances for a given query.
 */
public class CodeGenerationQueryExecutionPlanFactory
        implements QueryExecutionPlanFactory {


    protected static final TypeInfo TYPE_INFO_FOR_TUPLE;

    static {
        try {
            TYPE_INFO_FOR_TUPLE = new TypeInfoImpl(Tuple.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected final Query query;
    protected final Class queryExecutionPlanClass;
    protected final Constructor queryExecutionPlanConstructor;
    protected final TypeManager typeManager;
    protected String regionName;
    protected List<String> columnNames;
    protected List<String> sourceNames;
    private List<String> columnTypeNames;


    /**
     * Builds a QueryExecutionPlanFactoryImpl for the given Query.
     * @param query Query that will use this object.
     * @param regionName String name of the region queried.
     * @throws Exception upon error
     */
    public CodeGenerationQueryExecutionPlanFactory(Query query, String regionName) throws Exception {
        this.query = query;
        this.regionName = regionName;
        this.typeManager = this.query.getQuerySession().getRuleSession().getRuleServiceProvider().getTypeManager();
        this.queryExecutionPlanClass = this.createExecutionPlanClass();
        this.queryExecutionPlanConstructor = this.queryExecutionPlanClass.getDeclaredConstructors()[0];
    }


    protected StreamDescriptor createAggregation(NamedSelectContext model, QueryExecutionPlanClassBuilder classBuilder,
                                          StreamDescriptor inputStreamDescriptor) throws Exception {

        final GroupClause groupBy = model.getGroupClause();

        // No group by => no aggregation.
        if ((null == groupBy) || (groupBy.getChildCount() == 0)) {
            return inputStreamDescriptor;
        }

        final HavingClause having = groupBy.getHavingClause();

        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final TupleInfoDescriptorImpl aggrTupleInfoDescriptor = new TupleInfoDescriptorImpl(groupBy);

        // Adds the LinkedHashMap of column names to GroupAggregateItemInfo.
        final String gaiiMapFieldName = classBuilder.addGroupAggregateItemInfoMap(null);
        int colIndex;

        // Adds all AggregateItemInfo's to the map.
        final Set<AggregateFunctionIdentifier> aggregateFns = new HashSet<AggregateFunctionIdentifier>();
        for (ModelContext c : model.getProjectionAttributes().getDescendantContextsByType(
                ModelContext.CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER)) {
            aggregateFns.add((AggregateFunctionIdentifier) c);
        }
        if (null != having) {
            for (ModelContext c : having.getDescendantContextsByType(
                    ModelContext.CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER)) {
                aggregateFns.add((AggregateFunctionIdentifier) c);
            }
        }
        colIndex = 0;
        for (AggregateFunctionIdentifier f : aggregateFns) {
            final String columnName = "a" + (colIndex++);
            final TypeInfo typeInfo = f.getTypeInfo().getBoxedTypeInfo(this.typeManager);
            final boolean distinct = f.getDirectDescendantContextsByType(ModelContext.CTX_TYPE_DISTINCT_CLAUSE).size() > 0;
            if (Function.AGGREGATE_FUNCTION_COUNT.equalsIgnoreCase(f.getName()) && !distinct) {
                // COUNT withouth DISTINCT does not use a TVE.
                classBuilder.addAggregateItemInfo(gaiiMapFieldName, columnName,
                        CountAggregator.class.getCanonicalName() + ".CREATOR");
            } else {
                final String tveFieldName = classBuilder.createTupleValueExtractor(f.getArgument(0),
                        inputTupleInfoDescriptor);
                // Column values are always boxed since they are obtained from TVE's.
                if (Function.AGGREGATE_FUNCTION_COUNT.equalsIgnoreCase(f.getName())) {
                    // COUNT(DISTINCT expr)
                    classBuilder.addAggregateItemInfo(gaiiMapFieldName, columnName,
                            CountDistinctAggregator.class.getCanonicalName() + ".CREATOR", tveFieldName);
                } else if (Function.AGGREGATE_FUNCTION_AVG.equalsIgnoreCase(f.getName())) {
                    final Class aggregatorClass = distinct ? AverageDistinctAggregator.class : AverageAggregator.class;
                    classBuilder.addAggregateItemInfo(gaiiMapFieldName, columnName,
                            aggregatorClass.getCanonicalName() + ".CREATOR", tveFieldName);
                } else if (Function.AGGREGATE_FUNCTION_MAX.equalsIgnoreCase(f.getName())) {
                    classBuilder.addAggregateItemInfo(gaiiMapFieldName, columnName,
                            NullsSmallestMaxAggregator.class.getCanonicalName() + ".CREATOR", tveFieldName);
                } else if (Function.AGGREGATE_FUNCTION_MIN.equalsIgnoreCase(f.getName())) {
                    classBuilder.addAggregateItemInfo(gaiiMapFieldName, columnName,
                            NullsLargestMinAggregator.class.getCanonicalName() + ".CREATOR", tveFieldName);
                } else if (Function.AGGREGATE_FUNCTION_SUM.equalsIgnoreCase(f.getName())) {
                    final Class aggregatorClass = distinct ? DoubleSumDistinctAggregator.class : DoubleSumAggregator.class;
                    classBuilder.addAggregateItemInfo(gaiiMapFieldName, columnName,
                            aggregatorClass.getCanonicalName() + ".CREATOR", tveFieldName);
                } else {
                    throw new Exception("Unsupported aggregate: " + f);
                }
            }
            aggrTupleInfoDescriptor.addColumn(
                    new TupleInfoColumnDescriptorImpl(columnName, typeInfo, f, null, this.typeManager));
        }

        // Adds all GroupItemInfo's to the map.
        colIndex = 0;
        for (Iterator it = groupBy.getFieldList().getChildrenIterator(); it.hasNext(); ) {
            final Expression e = (Expression) it.next();
            final String tveFieldName = classBuilder.createTupleValueExtractor(e, inputTupleInfoDescriptor);
            final String columnName = "g" + (colIndex++);
            // Column values are always boxed since they are obtained from TVE's.
            final TypeInfo typeInfo = e.getTypeInfo().getBoxedTypeInfo(this.typeManager);
            classBuilder.addGroupItemInfo(gaiiMapFieldName, columnName, tveFieldName);
            aggrTupleInfoDescriptor.addColumn(
                    new TupleInfoColumnDescriptorImpl(columnName, typeInfo, e, null, this.typeManager));
        }

        // Adds the TupleInfo field that describes the output of the aggregation stream.
        final String tupleInfoFieldName = classBuilder.addTupleInfo(aggrTupleInfoDescriptor, false);

        // Adds the GroupAggregateInfo used by the stream.
        final String groupAggregateInfoName = classBuilder.addGroupAggregateInfo(null, gaiiMapFieldName);

        // Adds the WindowBuilder used by the stream.
        final String windowBuilderName = "null"; //todo WindowBuilder

        final GroupPolicy policy = groupBy.getGroupPolicy();
        if ((null != policy) && (policy.getCaptureType() == CaptureType.NEW)) {
            // Adds an IStream to drop the deletes.
            inputStreamDescriptor = new StreamDescriptorImpl(
                    "cature:new" + classBuilder.getIdGenerator().nextIdentifier(),
                    inputStreamDescriptor,
                    inputStreamDescriptor.getTupleInfoDescriptor(),
                    inputStreamDescriptor.getTupleInfoFieldName(), policy);
            classBuilder.addInsertCreation(inputStreamDescriptor);
        }

        // Adds the aggregation stream creation.
        final StreamDescriptor aggrStreamDescriptor = new StreamDescriptorImpl(
                "GROUPBY" + classBuilder.getIdGenerator().nextIdentifier(),
                inputStreamDescriptor,
                aggrTupleInfoDescriptor, tupleInfoFieldName, groupBy);
        classBuilder.addAggregationCreation(aggrStreamDescriptor,
                ((null != policy) && (policy.getEmitType() == EmitType.FULL)),
                groupAggregateInfoName, windowBuilderName);

        if (null == having) {
            return aggrStreamDescriptor;
        }

        final Expression havingExpression = having.getExpression();
        final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(groupBy);
        aliasMapDescriptor.addColumn(new AliasMapEntryDescriptor(FilteredStream.DEFAULT_STREAM_ALIAS,
                new TypeInfoImpl(Tuple.class),
                aggrStreamDescriptor.getTupleInfoDescriptor().getTupleClassName(),
                null, aggrTupleInfoDescriptor));

        // Creates expression for the HAVING filter.
        final String filterExpressionName = classBuilder.createSimpleExpression(havingExpression,
                aliasMapDescriptor);

        // Adds the Filter that implements HAVING.
        final StreamDescriptor havingStreamDescriptor = new StreamDescriptorImpl(
                "HAVING" + classBuilder.getIdGenerator().nextIdentifier(),
                aggrStreamDescriptor,
                aggrTupleInfoDescriptor, tupleInfoFieldName, having);
        classBuilder.addFilterCreation(havingStreamDescriptor, filterExpressionName);

        return havingStreamDescriptor;
    }


    protected String createAndExpression(BinaryExpression andExpression,
                                         AliasMapDescriptor aliasMapDescriptor,
                                         QueryExecutionPlanClassBuilder classBuilder) throws Exception {

        final List<String> andArgumentList = new ArrayList<String>();
        this.createAndExpressionArgumentList(andArgumentList, andExpression.getLeftExpression(),
                aliasMapDescriptor, classBuilder);
        this.createAndExpressionArgumentList(andArgumentList, andExpression.getRightExpression(),
                aliasMapDescriptor, classBuilder);

        return classBuilder.addAndExpression(andExpression,
                andArgumentList.toArray(new String[andArgumentList.size()]));
    }


    protected void createAndExpressionArgumentList(
            List<String> argNames,
            Expression expression,
            AliasMapDescriptor aliasMapDescriptor,
            QueryExecutionPlanClassBuilder classBuilder) throws Exception {
        if ((expression instanceof BinaryExpression)
                && ((BinaryExpression) expression).getOperator().getOpType() == Operator.OP_AND) {
            this.createAndExpressionArgumentList(argNames, ((BinaryExpression) expression).getLeftExpression(),
                    aliasMapDescriptor, classBuilder);
            this.createAndExpressionArgumentList(argNames, ((BinaryExpression) expression).getRightExpression(),
                    aliasMapDescriptor, classBuilder);
        } else {
            argNames.add(this.createExpression(expression, aliasMapDescriptor, classBuilder));
        }
    }


    /**
     * @param model               NamedSelectContext associated to the query.
     * @param classBuilder        QueryExecutionPlanClassBuilder used to generate the code.
     * @param preBridgeDescriptor StreamDescriptor of the stream just before the bridge.
     * @param lastDescriptor      StreamDescriptor of the last stream after the bridge.
     * @throws Exception upon problem.
     */
    protected void createBridgeAndSink(
            NamedSelectContext model,
            QueryExecutionPlanClassBuilder classBuilder,
            StreamDescriptor preBridgeDescriptor, StreamDescriptor lastDescriptor) throws Exception {

        BridgeDescriptor bridgeDescriptor;

        final StreamDescriptor[] postBridgeDescriptors = preBridgeDescriptor.getOutputStreamDescriptors();
        final StreamDescriptor postBridgeDescriptor;
        if ((null == postBridgeDescriptors) || (postBridgeDescriptors.length < 1)) {
            postBridgeDescriptor = null;
        } else {
            postBridgeDescriptor = postBridgeDescriptors[0];
        }

        if ((null == postBridgeDescriptor) || (postBridgeDescriptor.equals(preBridgeDescriptor))) {
            final ProjectionAttributes projections = model.getProjectionAttributes();
            final boolean usePassThroughBridge = (null != projections)
                    && (projections.childrenCount() == 1)
                    && (((Projection) (projections.getChildren()[0])).getExpression() instanceof ScopedIdentifier);

            final SinkDescriptor sinkDescriptor = new SinkDescriptor(
                    "SINK" + classBuilder.getIdGenerator().nextIdentifier(),
                    lastDescriptor,
                    lastDescriptor.getTupleInfoDescriptor(),
                    lastDescriptor.getTupleInfoFieldName(),
                    usePassThroughBridge,
                    model);
            classBuilder.addSinkCreation(sinkDescriptor);

            if (usePassThroughBridge) {
                bridgeDescriptor = new PassThroughBridgeDescriptor(
                        "bridge" + classBuilder.getIdGenerator().nextIdentifier(),
                        preBridgeDescriptor,
                        preBridgeDescriptor.getTupleInfoDescriptor(),
                        preBridgeDescriptor.getTupleInfoFieldName(),
                        null,
                        sinkDescriptor.getInternalStreamDescriptor());
                classBuilder.addPassThroughBridge(bridgeDescriptor);
            } else {
                bridgeDescriptor = new SimpleBridgeDescriptor(
                        "bridge" + classBuilder.getIdGenerator().nextIdentifier(),
                        preBridgeDescriptor,
                        preBridgeDescriptor.getTupleInfoDescriptor(),
                        preBridgeDescriptor.getTupleInfoFieldName(),
                        null,
                        sinkDescriptor.getInternalStreamDescriptor());
                classBuilder.addBridge(bridgeDescriptor);
            }

        } else if (postBridgeDescriptor instanceof SortedStreamDescriptor) {
            final SinkDescriptor sinkDescriptor = new SinkDescriptor(
                    "SINK" + classBuilder.getIdGenerator().nextIdentifier(),
                    lastDescriptor,
                    lastDescriptor.getTupleInfoDescriptor(),
                    lastDescriptor.getTupleInfoFieldName(),
                    false,
                    model);
            classBuilder.addSinkCreation(sinkDescriptor);

            bridgeDescriptor = new SortedBridgeDescriptor(
                    "bridge" + classBuilder.getIdGenerator().nextIdentifier(),
                    preBridgeDescriptor,
                    preBridgeDescriptor.getTupleInfoDescriptor(),
                    preBridgeDescriptor.getTupleInfoFieldName(),
                    null,
                    postBridgeDescriptor.getOutputStreamDescriptors()[0],
                    (SortedStreamDescriptor) postBridgeDescriptor);
            classBuilder.addSortedBridge((SortedBridgeDescriptor) bridgeDescriptor);

        } else if (postBridgeDescriptor instanceof TruncatedStreamDescriptor) {
            final SinkDescriptor sinkDescriptor = new SinkDescriptor(
                    "SINK" + classBuilder.getIdGenerator().nextIdentifier(),
                    lastDescriptor,
                    lastDescriptor.getTupleInfoDescriptor(),
                    lastDescriptor.getTupleInfoFieldName(),
                    false,
                    model);
            classBuilder.addSinkCreation(sinkDescriptor);

            bridgeDescriptor = new TruncatedBridgeDescriptor(
                    "bridge" + classBuilder.getIdGenerator().nextIdentifier(),
                    preBridgeDescriptor,
                    preBridgeDescriptor.getTupleInfoDescriptor(),
                    preBridgeDescriptor.getTupleInfoFieldName(),
                    null,
                    postBridgeDescriptor);
            classBuilder.addTruncatedBridge(bridgeDescriptor);

        } else {
            final SinkDescriptor sinkDescriptor = new SinkDescriptor(
                    "SINK" + classBuilder.getIdGenerator().nextIdentifier(),
                    lastDescriptor,
                    lastDescriptor.getTupleInfoDescriptor(),
                    lastDescriptor.getTupleInfoFieldName(),
                    false,
                    model);
            classBuilder.addSinkCreation(sinkDescriptor);

            bridgeDescriptor = new SimpleBridgeDescriptor(
                    "bridge" + classBuilder.getIdGenerator().nextIdentifier(),
                    preBridgeDescriptor,
                    preBridgeDescriptor.getTupleInfoDescriptor(),
                    preBridgeDescriptor.getTupleInfoFieldName(),
                    null,
                    postBridgeDescriptor);
            classBuilder.addBridge(bridgeDescriptor);

        }

        this.columnNames = new ArrayList<String>();
        this.columnTypeNames = new ArrayList<String>();
        for (TupleInfoColumnDescriptor column : lastDescriptor.getTupleInfoDescriptor().getColumns()) {
            this.columnNames.add(column.getName());
            this.columnTypeNames.add(this.makeColumnClassName(
                column.getTypeInfo().getRuntimeClass(this.typeManager)));
        }
    }


    protected StreamDescriptor createDeleted(Stream streamDef,
                                             StreamDescriptor inputStreamDescriptor,
                                             QueryExecutionPlanClassBuilder classBuilder)
            throws Exception {

        if ((null == streamDef) || (streamDef.getEmitType() != EmitType.DEAD)) {
            return inputStreamDescriptor;
        }

        final StreamDescriptor streamDescriptor = new StreamDescriptorImpl(
                "emit:dead" + classBuilder.getIdGenerator().nextIdentifier(),
                inputStreamDescriptor,
                inputStreamDescriptor.getTupleInfoDescriptor(),
                inputStreamDescriptor.getTupleInfoFieldName(), streamDef);

        classBuilder.addDeletedCreation(streamDescriptor);

        return streamDescriptor;
    }


    protected StreamDescriptor createDistinct(NamedSelectContext model, QueryExecutionPlanClassBuilder classBuilder,
                                              StreamDescriptor inputStreamDescriptor)
            throws Exception {

        final DistinctClause distinct = model.getDistinctClause();
        if (null == distinct) {
            return inputStreamDescriptor; // no DISTINCT => no extra stream
        }

        // Creates the DistinctFilteredStream.
        final DistinctFilteredStreamDescriptor streamDescriptor = new DistinctFilteredStreamDescriptor(
                "DISTINCT" + classBuilder.getIdGenerator().nextIdentifier(),
                inputStreamDescriptor, inputStreamDescriptor.getTupleInfoDescriptor(),
                inputStreamDescriptor.getTupleInfoFieldName(), distinct);
        classBuilder.addDistinctCreation(streamDescriptor);

        return streamDescriptor;
    }


    protected String createEqualsExpression(BinaryExpression equalsExpression,
                                            AliasMapDescriptor aliasMapDescriptor,
                                            QueryExecutionPlanClassBuilder classBuilder) throws Exception {

        final String leftExpressionName = this.createExpression(equalsExpression.getLeftExpression(), aliasMapDescriptor, classBuilder);
        final String rightExpressionName = this.createExpression(equalsExpression.getRightExpression(), aliasMapDescriptor, classBuilder);

        return classBuilder.addEqualsExpression(equalsExpression, leftExpressionName, rightExpressionName);
    }


    /**
     * Creates an execution plan for a query.
     *
     * @param name       String name of the plan.
     * @param continuous boolean true is the plan is for a continuous execution.
     * @return QueryExecutionPlan
     * @throws Exception upon error.
     */
    public QueryExecutionPlan newQueryExecutionPlan(
            String name,
            boolean continuous)
            throws Exception {

        QueryExecutionPlan plan;
        final ClassLoader cloader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.query.getExecutionClassLoader());
        try {
            plan = (QueryExecutionPlan) this.queryExecutionPlanConstructor.newInstance(this.regionName, name);
            if (continuous) {
                plan.initializeAsContinuous();
            } else {
                plan.initializeAsSnapshot();
            }
        } finally {
            Thread.currentThread().setContextClassLoader(cloader);
        }
        return plan;
    }


    protected Class createExecutionPlanClass() throws Exception {
        ClassLoader cloader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(this.query.getExecutionClassLoader());
        try {

            final NamedSelectContext model = (NamedSelectContext) query.getModel();
            final QueryExecutionPlanClassBuilder classBuilder = new QueryExecutionPlanClassBuilder(query);

            final List<StreamDescriptor> sourceDescriptors = this.createSources(model, classBuilder);

            StreamDescriptor preBridgeDescriptor;
            preBridgeDescriptor = this.createJoin(model, classBuilder, sourceDescriptors);
            preBridgeDescriptor = this.createAggregation(model, classBuilder, preBridgeDescriptor);
            preBridgeDescriptor = this.createTransformation(model, classBuilder, preBridgeDescriptor);

            StreamDescriptor descriptor;
            descriptor = this.createSort(model, classBuilder, preBridgeDescriptor);
            descriptor = this.createDistinct(model, classBuilder, descriptor);
            descriptor = this.createLimit(model, classBuilder, descriptor);

            this.createBridgeAndSink(model, classBuilder, preBridgeDescriptor, descriptor);

            return classBuilder.getQepClass();
        } finally {
            Thread.currentThread().setContextClassLoader(cloader);
        }
    }

    protected String createExpression(Expression expression, AliasMapDescriptor aliasMapDescriptor,
                                      QueryExecutionPlanClassBuilder classBuilder)
            throws Exception {

        if (expression instanceof BinaryExpression) {
            switch (((BinaryExpression) expression).getOperator().getOpType()) {
                case Operator.OP_AND:
                    return this.createAndExpression((BinaryExpression) expression, aliasMapDescriptor, classBuilder);
                case Operator.OP_EQ:
                    return this.createEqualsExpression((BinaryExpression) expression, aliasMapDescriptor, classBuilder);
                case Operator.OP_OR:
                    return this.createOrExpression((BinaryExpression) expression, aliasMapDescriptor, classBuilder);
            }
        } else if (expression instanceof UnaryExpression) {
            switch (((UnaryExpression) expression).getOperator().getOpType()) {
                case Operator.OP_NOT:
                    return this.createNotExpression((UnaryExpression) expression, aliasMapDescriptor, classBuilder);
            }
        }
        return classBuilder.createSimpleExpression(expression, aliasMapDescriptor);
    }


    protected StreamDescriptor createJoin(NamedSelectContext model, QueryExecutionPlanClassBuilder classBuilder,
                                          List<StreamDescriptor> inputStreamDescriptors) throws Exception {
        final WhereClause where = model.getWhereClause();

        Expression expression;
        if ((null == where) || (where.getChildCount() == 0)) {
            expression = null;
        } else {
            expression = (Expression) where.getChildrenIterator().next();
        }

        switch (inputStreamDescriptors.size()) {
            case 0: throw new IllegalArgumentException();
            case 1:
            {
                final StreamDescriptor inputStreamDescriptor = inputStreamDescriptors.get(0);
                if (null == expression) {
                    return inputStreamDescriptor; // Single stream without WHERE => done.
                }
                // Else single stream with WHERE => will replace the join with a filter.
                final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
                final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(null);
                aliasMapDescriptor.addColumn(new AliasMapEntryDescriptor(FilteredStream.DEFAULT_STREAM_ALIAS,
                        new TypeInfoImpl(Tuple.class),
                        inputTupleInfoDescriptor.getTupleClassName(),
                        null, inputTupleInfoDescriptor));

                // Creates expression for the filter.
                final String filterExpressionName = classBuilder.createSimpleExpression(where.getExpression(),
                        aliasMapDescriptor);

                // Adds the Filter.
                final StreamDescriptor descriptor = new StreamDescriptorImpl(
                        "WHERE" + classBuilder.getIdGenerator().nextIdentifier(),
                        inputStreamDescriptor,
                        inputTupleInfoDescriptor,
                        inputStreamDescriptor.getTupleInfoFieldName(), where);
                classBuilder.addFilterCreation(descriptor, filterExpressionName);

                return descriptor;
            }
            default: // Multiple streams => must join.
        }

        final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(where);
        final TupleInfoDescriptorImpl tupleInfoDescriptor = new TupleInfoDescriptorImpl(where);
        for (StreamDescriptor streamDescriptor : inputStreamDescriptors) {
            final String streamName = streamDescriptor.getName();
            final TupleInfoDescriptor tid = streamDescriptor.getTupleInfoDescriptor();
            final String tupleClassName = tid.getTupleClassName();
            aliasMapDescriptor.addColumn(new AliasMapEntryDescriptor(streamName, TYPE_INFO_FOR_TUPLE, tupleClassName,
                    null, tid));
            tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(streamName, TYPE_INFO_FOR_TUPLE, tupleClassName,
                    null, tid));
        }

        // Adds the TupleInfo field that describes the output of the Join.
        final String tupleInfoFieldName = classBuilder.addJoinedTupleInfo(tupleInfoDescriptor, false);

        // Adds the ComplexExpression used by the Join.
        final String expressionFieldName = this.createJoinExpression(expression, aliasMapDescriptor, classBuilder);

        // Adds the join creation.
        final StreamDescriptor streamDescriptor = new StreamDescriptorImpl(
                "WHERE" + classBuilder.getIdGenerator().nextIdentifier(),
                new ArrayList<StreamDescriptor>(inputStreamDescriptors).toArray(
                        new StreamDescriptor[inputStreamDescriptors.size()]),
                null, tupleInfoDescriptor, tupleInfoFieldName, where);
        classBuilder.addJoinCreation(streamDescriptor, expressionFieldName);

        return streamDescriptor;
    }


    protected String createJoinExpression(Expression expression,
                                          AliasMapDescriptor aliasMapDescriptor,
                                          QueryExecutionPlanClassBuilder classBuilder) throws Exception {

        if (null == expression) {
            return classBuilder.addAndExpression(null, null);
        }

        final String expressionName = this.createExpression(expression, aliasMapDescriptor, classBuilder);

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

        return classBuilder.addAndExpression(expression, new String[]{expressionName});
    }


    protected StreamDescriptor createLimit(NamedSelectContext model, QueryExecutionPlanClassBuilder classBuilder,
                                           StreamDescriptor inputStreamDescriptor) throws Exception {

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
            firstFieldName = classBuilder.addExpressionEvaluator(null, (BindVariable) first);
        } else {
            firstFieldName = classBuilder.addExpressionEvaluator(null, (Integer) first);
        }

        final String offsetFieldName;
        if (offset instanceof BindVariable) {
            offsetFieldName = classBuilder.addExpressionEvaluator(null, (BindVariable) offset);
        } else {
            offsetFieldName = classBuilder.addExpressionEvaluator(null, (Integer) offset);
        }

        // Creates the TruncatedStream.
        final TruncatedStreamDescriptor streamDescriptor = new TruncatedStreamDescriptor(
                "LIMIT" + classBuilder.getIdGenerator().nextIdentifier(),
                inputStreamDescriptor, inputStreamDescriptor.getTupleInfoDescriptor(),
                inputStreamDescriptor.getTupleInfoFieldName(), model, firstFieldName, offsetFieldName);
        classBuilder.addLimitCreation(streamDescriptor);

        return streamDescriptor;
    }


    protected String createNotExpression(UnaryExpression notExpression,
                                         AliasMapDescriptor aliasMapDescriptor,
                                         QueryExecutionPlanClassBuilder classBuilder) throws Exception {

        final Expression child = (Expression) notExpression.getChildrenIterator().next();
        final String localChildExpressionName = this.createExpression(child, aliasMapDescriptor, classBuilder);

        return classBuilder.addNotExpression(notExpression, localChildExpressionName);
    }


    protected String createOrExpression(BinaryExpression orExpression,
                                            AliasMapDescriptor aliasMapDescriptor,
                                            QueryExecutionPlanClassBuilder classBuilder) throws Exception {

        final List<String> argumentList = new ArrayList<String>();
        this.createOrExpressionArgumentList(argumentList, orExpression.getLeftExpression(),
                aliasMapDescriptor, classBuilder);
        this.createAndExpressionArgumentList(argumentList, orExpression.getRightExpression(),
                aliasMapDescriptor, classBuilder);

        return classBuilder.addOrExpression(orExpression,
                argumentList.toArray(new String[argumentList.size()]));
    }


    protected void createOrExpressionArgumentList(
            List<String> argNames,
            Expression expression,
            AliasMapDescriptor aliasMapDescriptor,
            QueryExecutionPlanClassBuilder classBuilder) throws Exception {
        if ((expression instanceof BinaryExpression)
                && ((BinaryExpression) expression).getOperator().getOpType() == Operator.OP_OR) {
            this.createOrExpressionArgumentList(argNames, ((BinaryExpression) expression).getLeftExpression(),
                    aliasMapDescriptor, classBuilder);
            this.createOrExpressionArgumentList(argNames, ((BinaryExpression) expression).getRightExpression(),
                    aliasMapDescriptor, classBuilder);
        } else {
            argNames.add(this.createExpression(expression, aliasMapDescriptor, classBuilder));
        }
    }


    protected StreamDescriptor createSort(NamedSelectContext model, QueryExecutionPlanClassBuilder classBuilder,
                                StreamDescriptor inputStreamDescriptor) throws Exception {

        final OrderClause orderBy = model.getOrderClause();

        if ((null == orderBy) || (orderBy.getChildCount() == 0)) {
            return inputStreamDescriptor; // = No Sort.
        }

        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final List<String> sortItemInfos = new ArrayList<String>();
        final List<String> extractors = new ArrayList<String>();
        final List<String> comparators = new ArrayList<String>();
        for (SortCriterion sortCriterion : orderBy.getSortCriteria()) {
            final boolean asc = (sortCriterion.getDirection() == SortCriterion.Direction.ASC);
            final Expression expr = sortCriterion.getExpression();
            if (sortCriterion.usesNonDefaultLimit()) {
                final Object first = sortCriterion.getLimitFirst();
                final Object offset = sortCriterion.getLimitOffset();

                final String firstFieldName;
                if (first instanceof BindVariable) {
                    firstFieldName = classBuilder.addExpressionEvaluator(sortCriterion, (BindVariable) first);
                } else {
                    firstFieldName = classBuilder.addExpressionEvaluator(sortCriterion, (Integer) first);
                }

                final String offsetFieldName;
                if (offset instanceof BindVariable) {
                    offsetFieldName = classBuilder.addExpressionEvaluator(sortCriterion, (BindVariable) offset);
                } else {
                    offsetFieldName = classBuilder.addExpressionEvaluator(sortCriterion, (Integer) offset);
                }

                sortItemInfos.add(classBuilder.addSortItemInfo(sortCriterion, asc,
                        firstFieldName, offsetFieldName));
            }
            else {
                sortItemInfos.add(classBuilder.addSortItemInfo(sortCriterion, asc));
            }
            extractors.add(classBuilder.createTupleValueExtractor(expr, inputTupleInfoDescriptor));
            comparators.add(this.getComparatorFullName(expr.getTypeInfo()));
        }

        final String extractorsName = classBuilder.addSortInfoExtractors(extractors);
        final String comparatorsName = classBuilder.addSortInfoComparators(comparators);
        final String sortInfoName = classBuilder.addSortInfo(orderBy, sortItemInfos);

        // Creates the Sort.
        final SortedStreamDescriptor sortStreamDescriptor = new SortedStreamDescriptor(
                "SORT" + classBuilder.getIdGenerator().nextIdentifier(),
                inputStreamDescriptor,
                inputTupleInfoDescriptor, inputStreamDescriptor.getTupleInfoFieldName(), orderBy,
                sortInfoName, extractorsName, comparatorsName);
        classBuilder.addSortCreation(sortStreamDescriptor);

        return sortStreamDescriptor;
    }


    protected StreamDescriptor createSourceFromEntity(
            AliasedIdentifier aliasedId,
            AcceptType acceptType,
            QueryExecutionPlanClassBuilder classBuilder)
            throws Exception {

        final Entity entity = (Entity) aliasedId.getIdentifiedContext();

        final TupleInfoDescriptorImpl tupleInfoDescriptor = new TupleInfoDescriptorImpl(aliasedId);
        tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(entity.getEntityPath(),
                entity.getTypeInfo(), aliasedId, null, this.typeManager));

        // Adds the ReteEntityInfo field that describes the output of the Source.
        final String alias = aliasedId.getAlias();
        final String tupleInfoFieldName = classBuilder.addReteEntityInfo(tupleInfoDescriptor, true);
        final SourceDescriptor streamDescriptor = new SourceDescriptor("SRC_" + alias,
                tupleInfoDescriptor, tupleInfoFieldName, aliasedId, alias, acceptType);

        // Adds a Source creation.
        final String varName = classBuilder.addSourceCreation(streamDescriptor);
        streamDescriptor.setVariableName(varName);

        return streamDescriptor;
    }


    protected StreamDescriptor createSourceProxyFromEntity(AliasedIdentifier aliasedId,
                                                           StreamDescriptor sourceDescriptor,
                                                           QueryExecutionPlanClassBuilder classBuilder)
            throws Exception {

        final Entity entity = (Entity) aliasedId.getIdentifiedContext();

        final TupleInfoDescriptorImpl tupleInfoDescriptor = new TupleInfoDescriptorImpl(aliasedId);
        tupleInfoDescriptor.addColumn(new TupleInfoColumnDescriptorImpl(entity.getEntityPath(),
                entity.getTypeInfo(), aliasedId, null, this.typeManager));

        // Associates the Tuple class of the source to the descriptor of the TupleInfo of the proxy.
        tupleInfoDescriptor.setTupleClassName(sourceDescriptor.getTupleInfoDescriptor().getTupleClassName());

        // Adds a Proxy creation.
        final String tupleInfoFieldName = sourceDescriptor.getTupleInfoFieldName();
        final StreamDescriptorImpl streamDescriptor = new StreamDescriptorImpl(
                "PROXY_" + aliasedId.getAlias() + "_" + classBuilder.getIdGenerator().nextIdentifier(),
                null,
                tupleInfoDescriptor, tupleInfoFieldName, aliasedId);
        final String varName = classBuilder.addProxyCreation(aliasedId, aliasedId.getAlias(),
                sourceDescriptor.getVariableName());
        streamDescriptor.setVariableName(varName);

        return streamDescriptor;
    }


    protected List<StreamDescriptor> createSources(NamedSelectContext model,
                                                   QueryExecutionPlanClassBuilder classBuilder)
            throws Exception {
        final List<StreamDescriptor> streamDescriptors = new LinkedList<StreamDescriptor>();
        final Map<ModelContext, StreamDescriptor> ctxToStreamDescriptor = new HashMap<ModelContext, StreamDescriptor>();

        this.sourceNames = new ArrayList<String>();

        for (Iterator it = model.getFromClause().getChildrenIterator(); it.hasNext();) {
            final AliasedIdentifier aliasedId = (AliasedIdentifier) it.next();
            final ModelContext identifiedCtx = aliasedId.getIdentifiedContext();
            final Stream streamDef = aliasedId.getStream();

            this.sourceNames.add(aliasedId.getAlias());

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
                    descriptor = this.createSourceProxyFromEntity(aliasedId, descriptor, classBuilder);

                } else {
                    if (null == streamDef) {
                        descriptor = this.createSourceFromEntity(aliasedId, AcceptType.ALL, classBuilder);
                    } else {
                        descriptor = this.createSourceFromEntity(aliasedId, streamDef.getAcceptType(), classBuilder);
                        final StreamPolicy policy = streamDef.getPolicy();
                        if (null != policy) {
                            descriptor = this.createStreamPolicy(streamDef, descriptor, classBuilder);
                        } else if (streamDef.getEmitType() == EmitType.DEAD) {
                            descriptor = this.createDeleted(streamDef, descriptor, classBuilder);
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


    protected StreamDescriptor createStreamPolicy(Stream streamDef,
                                                  StreamDescriptor inputStreamDescriptor,
                                                  QueryExecutionPlanClassBuilder classBuilder)
            throws Exception {

        if (null == streamDef) {
            return inputStreamDescriptor;
        }

        final StreamPolicy policy = streamDef.getPolicy();
        if (null == policy) {
            return inputStreamDescriptor;
        }

        StreamDescriptor descriptor = inputStreamDescriptor;

        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final String inputTupleInfoFieldName = inputStreamDescriptor.getTupleInfoFieldName();
        final String inputTupleClassName = inputTupleInfoDescriptor.getTupleClassName();

        final WhereClause where = policy.getWhereClause();
        if (null != where) {
            final Expression whereExpr = where.getExpression();
            if (null != whereExpr) {
                final AliasMapDescriptor aliasMapDescriptor = new AliasMapDescriptor(where);
                aliasMapDescriptor.addColumn(new AliasMapEntryDescriptor(FilteredStream.DEFAULT_STREAM_ALIAS,
                        new TypeInfoImpl(Tuple.class),
                        inputTupleClassName,
                        null, inputTupleInfoDescriptor));

                final String filterExpressionName = classBuilder.createSimpleExpression(whereExpr, aliasMapDescriptor);

                descriptor = new StreamDescriptorImpl(
                        "policy:where" + classBuilder.getIdGenerator().nextIdentifier(),
                        inputStreamDescriptor,
                        inputTupleInfoDescriptor, inputTupleInfoFieldName, where);

                classBuilder.addFilterCreation(descriptor, filterExpressionName);
            }
        }

        final StreamPolicyBy by = policy.getByClause();
        final String groupAggregateInfoName;
        if (null == by) {
            groupAggregateInfoName = null;
        } else {

            // Adds the LinkedHashMap of column names to GroupAggregateItemInfo.
            final String mapName = classBuilder.addGroupAggregateItemInfoMap(null);

            // Adds all GroupItemInfo's to the map.
            int colIndex = 0;
            for (Expression e: by.getExpressions()) {
                final String tveFieldName = classBuilder.createTupleValueExtractor(e, inputTupleInfoDescriptor);
                final String columnName = "by" + (colIndex++);
                // Column values are always boxed since they are obtained from TVE's.
                classBuilder.addGroupItemInfo(mapName, columnName, tveFieldName);
            }

            // Adds the GroupAggregateInfo used by the partition stream.
            groupAggregateInfoName = classBuilder.addGroupAggregateInfo(null, mapName);
        }


        // Adds the WindowInfo and the WindowBuilder used by the stream.
        final Window window = policy.getWindow();
        String windowInfoName;
        String windowBuilderName;
        final String insertDeleteBothName;
        switch (streamDef.getEmitType()) {
            case DEAD:
                insertDeleteBothName = AbstractQueryExecutionPlan.Emit.class.getCanonicalName()+"." + EmitType.DEAD.name();
                break;
            case NEW:
                insertDeleteBothName = AbstractQueryExecutionPlan.Emit.class.getCanonicalName()+"." + EmitType.NEW.name();
                break;
           default:
               insertDeleteBothName = AbstractQueryExecutionPlan.Emit.class.getCanonicalName()+"." + EmitType.FULL.name();
        }

        if (window instanceof SlidingWindow) {
            windowInfoName = classBuilder.addSlidingWindowInfo(null, ((SlidingWindow) window).getMaxSize());
            windowBuilderName = classBuilder.addSlidingWindowBuilderCreation(window,
                    inputTupleInfoFieldName, inputTupleInfoFieldName, insertDeleteBothName, false);
        } else if (window instanceof TimeWindow) {
            windowInfoName = classBuilder.addTimeWindowInfo(null, Integer.MAX_VALUE,
                    ((TimeWindow) window).getMaxTimeInMs());
            windowBuilderName = classBuilder.addTimeWindowBuilderCreation(window,
                    inputTupleInfoFieldName, inputTupleInfoFieldName, insertDeleteBothName, false);
        } else if (window instanceof TumblingWindow) {
            windowInfoName = classBuilder.addTumblingWindowInfo(null, ((TumblingWindow) window).getMaxSize());
            windowBuilderName = classBuilder.addTumblingWindowBuilderCreation(window,
                    inputTupleInfoFieldName, inputTupleInfoFieldName, insertDeleteBothName, false);
        } else {
            throw new Exception("Unsupported window type: " + window);
        }

        // Adds the partition stream creation.
        descriptor = new StreamDescriptorImpl(
                "policy:by" + classBuilder.getIdGenerator().nextIdentifier(),
                descriptor,
                inputTupleInfoDescriptor, inputTupleInfoFieldName, by);
        classBuilder.addPartitionCreation(descriptor, groupAggregateInfoName, windowInfoName, windowBuilderName);

        return descriptor;
    }


    protected StreamDescriptor createTransformation(NamedSelectContext model,
                                                    QueryExecutionPlanClassBuilder classBuilder,
                                                    StreamDescriptor inputStreamDescriptor) throws Exception {
        final LinkedHashMap<String, String> projAliasToTveName = new LinkedHashMap<String, String>();
        final ProjectionAttributes projAttributes = model.getProjectionAttributes();
        final TupleInfoDescriptor inputTupleInfoDescriptor = inputStreamDescriptor.getTupleInfoDescriptor();
        final TupleInfoDescriptorImpl tupleInfoDescriptor = new TupleInfoDescriptorImpl(projAttributes);

        for (Projection projection : projAttributes.getAllProjections()) {
            final Expression expr = projection.getExpression();

            if (expr instanceof ScopedIdentifier) {
                for (ModelContext mc : ((ScopedIdentifier) expr).getIdentifiedContexts()) {
                    String tveFieldName;
                    if (mc instanceof Expression) {
                        tveFieldName = classBuilder.createTupleValueExtractor((Expression) mc, inputTupleInfoDescriptor);
                    } else if (mc instanceof ProxyContext) {
                        tveFieldName = classBuilder.createTupleValueExtractor((ProxyContext) mc, inputTupleInfoDescriptor);
                    } else {
                        throw new Exception("Unexpected context in *: " + mc);
                    }

                    final String columnName = makeColumnName(mc, projection, tupleInfoDescriptor);
                    projAliasToTveName.put(columnName, tveFieldName);
                    tupleInfoDescriptor.addColumn(
                            new TupleInfoColumnDescriptorImpl(columnName, ((TypedContext) mc).getTypeInfo(), mc, null, this.typeManager));
                }
            } else if (expr.getIdentifiedContext() instanceof ScopedIdentifier) {
                for (ModelContext mc : ((ScopedIdentifier) expr.getIdentifiedContext()).getIdentifiedContexts()) {
                    String tveFieldName;
                    if (mc instanceof Expression) {
                        tveFieldName = classBuilder.createTupleValueExtractor((Expression) mc, inputTupleInfoDescriptor);
                    } else if (mc instanceof ProxyContext) {
                        tveFieldName = classBuilder.createTupleValueExtractor((ProxyContext) mc, inputTupleInfoDescriptor);
                    } else {
                        throw new Exception("Unexpected context in *: " + mc);
                    }
                    final String columnName = makeColumnName(mc, projection, tupleInfoDescriptor);
                    projAliasToTveName.put(columnName, tveFieldName);
                    tupleInfoDescriptor.addColumn(
                            new TupleInfoColumnDescriptorImpl(columnName, ((TypedContext) mc).getTypeInfo(), mc, null, this.typeManager));
                }
            } else {
                final String tveFieldName = classBuilder.createTupleValueExtractor(expr, inputTupleInfoDescriptor);
                final String columnName = makeColumnName(expr, projection, tupleInfoDescriptor);
                projAliasToTveName.put(columnName, tveFieldName);
                tupleInfoDescriptor.addColumn(
                        new TupleInfoColumnDescriptorImpl(columnName, expr.getTypeInfo(), expr, null, this.typeManager));
            }
        }

        // Adds a TupleInfo field that describes the output of the Transformation.
        final String tupleInfoFieldName = classBuilder.addTupleInfo(tupleInfoDescriptor, false);

        // Adds a LinkedHashMap field that binds projection aliases to TVEs.
        final String transfMapName = classBuilder.addTransformMap(projAliasToTveName);

        // Adds a TransformInfo field that represents all the projections.
        final String transfInfoName = classBuilder.addTransformInfo(transfMapName);

        // Creates the TransformedStream.
        final TransformedStreamDescriptor transfStreamDescriptor = new TransformedStreamDescriptor(
                "TRANSF" + classBuilder.getIdGenerator().nextIdentifier(),
                inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, projAttributes, transfInfoName);
        classBuilder.addTransformCreation(transfStreamDescriptor);

        return transfStreamDescriptor;
    }


    protected String getComparatorFullName(TypeInfo typeInfo)  {
        if (typeInfo.isComparable() || typeInfo.isJavaPrimitiveType()) {
            return Comparisons.Comparators.class.getCanonicalName()
                    + QueryExecutionPlanClassBuilder.STATIC_FIELD_SEPARATOR + "COMPARABLES";
        }
        else if (typeInfo.isEntity()) {
            return Comparisons.Comparators.class.getCanonicalName()
                    + QueryExecutionPlanClassBuilder.STATIC_FIELD_SEPARATOR + "ENTITIES";
        }
        else {
            throw new IllegalArgumentException("Type is not comparable.");
        }
    }


    /**
     * Returns the Query that uses this object.
     * @return Query
     */
    public Query getQuery() {
        return query;
    }


    public List<String> getColumnNames() {
        return this.columnNames;
    }


    public List<String> getColumnTypeNames() {
        return this.columnTypeNames;
    }


    public List<String> getSourceNames() {
        return this.sourceNames;
    }


    protected String makeColumnClassName(Class columnClass) {
        if (com.tibco.cep.kernel.model.entity.Entity[].class.isAssignableFrom(columnClass)) {
            final Class componentClass = columnClass.getComponentType();
            final TypeManager.TypeDescriptor descriptor = this.typeManager.getTypeDescriptor(componentClass);
            if (null != descriptor) {
                return descriptor.getURI() + "[]";
            } else {
                return columnClass.getCanonicalName();
            }

        } else if (com.tibco.cep.kernel.model.entity.Entity.class.isAssignableFrom(columnClass)) {
            final TypeManager.TypeDescriptor descriptor = this.typeManager.getTypeDescriptor(columnClass);
            if (null != descriptor) {
                return descriptor.getURI();
            } else {
                return columnClass.getCanonicalName();
            }

        } else  if (Boolean[].class.equals(columnClass)
                || boolean[].class.equals(columnClass)) {
            return TypeNames.BOOLEAN + "[]";

        } else  if (Boolean.class.equals(columnClass)
                || boolean.class.equals(columnClass)) {
            return TypeNames.BOOLEAN;

        } else if (Calendar[].class.isAssignableFrom(columnClass)) {
            return TypeNames.DATETIME + "[]";

        } else if (Calendar.class.isAssignableFrom(columnClass)) {
            return TypeNames.DATETIME;

        } else  if (Double[].class.equals(columnClass)
                || Float[].class.equals(columnClass)
                || double[].class.equals(columnClass)
                || float[].class.equals(columnClass)) {
            return TypeNames.DOUBLE + "[]";

        } else  if (Double.class.equals(columnClass)
                || Float.class.equals(columnClass)
                || double.class.equals(columnClass)
                || float.class.equals(columnClass)) {
            return TypeNames.DOUBLE;

        } else if (Byte[].class.equals(columnClass)
                || Character[].class.equals(columnClass)
                || Integer[].class.equals(columnClass)
                || Short[].class.equals(columnClass)
                || byte[].class.equals(columnClass)
                || char[].class.equals(columnClass)
                || int[].class.equals(columnClass)
                || short[].class.equals(columnClass)) {
            return TypeNames.INT + "[]";

        } else if (Byte.class.equals(columnClass)
                || Character.class.equals(columnClass)
                || Integer.class.equals(columnClass)
                || Short.class.equals(columnClass)
                || byte.class.equals(columnClass)
                || char.class.equals(columnClass)
                || int.class.equals(columnClass)
                || short.class.equals(columnClass)) {
            return TypeNames.INT;

        } else if (Long[].class.equals(columnClass)
                || long[].class.equals(columnClass)) {
            return TypeNames.LONG + "[]";

        } else if (Long.class.equals(columnClass)
                || long.class.equals(columnClass)) {
            return TypeNames.LONG;

        } else if (String[].class.isAssignableFrom(columnClass)) {
            return TypeNames.STRING + "[]";

        } else if (String.class.isAssignableFrom(columnClass)) {
            return TypeNames.STRING;

        } else {
            return columnClass.getCanonicalName();
        }
    }


    protected String makeColumnName(
            EntityAttributeProxy proxy,
            TupleInfoDescriptor descriptor) {
        final ModelContext owner = proxy.getAttributeOwner();
        final EntityAttribute attr = proxy.getAttribute();

        String ownerName;
        if (owner instanceof Aliased) {
            final Aliased aliased = (Aliased) owner;
            if (aliased.isPseudoAliased()) {
                ownerName = attr.getEntity().getEntityName();
            } else {
                ownerName = aliased.getAlias();
            }
        } else {
            ownerName = proxy.getAttribute().getEntity().getEntityName();
        }

        return makeColumnName(ownerName + "@" + attr.getName(), descriptor);
    }


    protected String makeColumnName(
            EntityPropertyProxy proxy,
            TupleInfoDescriptor descriptor) {
        final ModelContext owner = proxy.getPropertyOwner();
        final EntityProperty prop = proxy.getProperty();

        String ownerName;
        if (owner instanceof Aliased) {
            final Aliased aliased = (Aliased) owner;
            if (aliased.isPseudoAliased()) {
                ownerName = prop.getEntity().getEntityName();
            } else {
                ownerName = aliased.getAlias();
            }
        } else {
            ownerName = prop.getEntity().getEntityName();
        }

        return makeColumnName(ownerName + "." + prop.getName(), descriptor);
    }


    protected String makeColumnName(
            ModelContext projectionItem,
            Projection projection,
            TupleInfoDescriptorImpl descriptor) {
        
        if (projection.isPseudoAliased()) {
            if (projectionItem instanceof EntityPropertyProxy) {
                return makeColumnName(((EntityPropertyProxy) projectionItem), descriptor);

            } else if (projectionItem instanceof EntityAttributeProxy) {
                return makeColumnName(((EntityAttributeProxy) projectionItem), descriptor);
            }            
            return makeColumnName(projection.getProjectionText(), descriptor);

        } else {
            return makeColumnName(projection.getAlias(), descriptor);
        }
    }


    protected String makeColumnName(
            String baseName,
            TupleInfoDescriptor descriptor) {        
        String newName = baseName;
        for (int i=1; null != descriptor.getColumnByName(newName); i++) {
            newName = baseName + " (" + i + ")";
        }
        return newName;
    }


}
