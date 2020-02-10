package com.tibco.cep.query.exec.impl;

import com.tibco.be.util.idgenerators.serial.PrefixedLeftPaddedNumericGenerator;
import com.tibco.cep.query.exec.descriptors.*;
import com.tibco.cep.query.exec.descriptors.impl.*;
import com.tibco.cep.query.exec.prebuilt.HeavyJoinedTuples;
import com.tibco.cep.query.exec.prebuilt.HeavyReteEntities;
import com.tibco.cep.query.exec.prebuilt.HeavyTuples;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.stream.aggregate.AggregateCreator;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.expression.*;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.group.GroupAggregateItemInfo;
import com.tibco.cep.query.stream.group.GroupItemInfo;
import com.tibco.cep.query.stream.impl.expression.EvaluatorToExtractorAdapter;
import com.tibco.cep.query.stream.impl.expression.ExtractorToEvaluatorAdapter;
import com.tibco.cep.query.stream.impl.expression.bool.AndEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.NotEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.OrEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.EqualityEvaluator;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilterImpl;
import com.tibco.cep.query.stream.impl.rete.join.EqualsExpression;
import com.tibco.cep.query.stream.partition.SlidingWindowInfo;
import com.tibco.cep.query.stream.partition.TimeWindowInfo;
import com.tibco.cep.query.stream.partition.TumblingWindowInfo;
import com.tibco.cep.query.stream.partition.WindowInfo;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.sort.SortItemInfo;
import com.tibco.cep.query.stream.transform.TransformInfo;
import com.tibco.cep.query.stream.transform.TransformItemInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.runtime.model.TypeManager;

import java.lang.reflect.Constructor;
import java.util.*;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 20, 2008
* Time: 2:01:02 PM
*/
class QueryExecutionPlanDescriptorBuilder {


    protected class QueryExecutionPlanDescriptorImpl
            implements QueryExecutionPlanDescriptor {

        protected final Query query;
        protected final String regionName;

        protected final PrefixedLeftPaddedNumericGenerator idGenerator =
                new PrefixedLeftPaddedNumericGenerator("", false, 5);
        protected final NameGenerator nameGenerator = new NameGenerator(this.idGenerator);

        private final LinkedHashMap<String, Object> nameToObject = new LinkedHashMap<String, Object>();
        private final List<SourceDescriptor> sourceDescriptors = new ArrayList<SourceDescriptor>();
        private final List<ProxyDescriptor> proxyDescriptors = new ArrayList<ProxyDescriptor>();
        private final List<PartitionDescriptor> partitionDescriptors = new LinkedList<PartitionDescriptor>();
        private final List<FilterDescriptor> filterDescriptors = new LinkedList<FilterDescriptor>();
        private final List<FilterDescriptor> havingFilterDescriptors = new ArrayList<FilterDescriptor>();

        private JoinDescriptor joinDescriptor;
        private AggregationDescriptor aggregationDescriptor;
        private BridgeDescriptor bridgeDescriptor;
        private SinkDescriptor sinkDescriptor;
        private TransformedStreamDescriptor transformDescriptor;
        private DistinctFilteredStreamDescriptor distinctDescriptor;
        private SortedStreamDescriptor sortDescriptor;
        private TruncatedStreamDescriptor limitDescriptor;
        private LinkedHashMap<String, String> columnNameToType;
        private boolean selfJoin;



        protected QueryExecutionPlanDescriptorImpl(
                Query query,
                String regionName) {
            this.query = query;
            this.regionName = regionName;
        }

        public boolean isSelfJoin() {
            return selfJoin;
        }

        public void setSelfJoin(boolean selfJoin) {
            this.selfJoin = selfJoin;
        }

        public AggregationDescriptor getAggregationDescriptor() {
            return aggregationDescriptor;
        }


        public BridgeDescriptor getBridgeDescriptor() {
            return bridgeDescriptor;
        }


        public LinkedHashMap<String, String> getColumnNameToType() {
            return columnNameToType;
        }


        public DistinctFilteredStreamDescriptor getDistinctDescriptor() {
            return distinctDescriptor;
        }


        public List<FilterDescriptor> getFilterDescriptors() {
            return filterDescriptors;
        }


        public List<FilterDescriptor> getHavingFilterDescriptors() {
            return havingFilterDescriptors;
        }


        public PrefixedLeftPaddedNumericGenerator getIdGenerator() {
            return idGenerator;
        }


        public JoinDescriptor getJoinDescriptor() {
            return joinDescriptor;
        }


        public TruncatedStreamDescriptor getLimitDescriptor() {
            return limitDescriptor;
        }


        public NameGenerator getNameGenerator() {
            return nameGenerator;
        }


        public LinkedHashMap<String, Object> getNameToObject() {
            return nameToObject;
        }


        public List<PartitionDescriptor> getPartitionDescriptors() {
            return partitionDescriptors;
        }


        public List<ProxyDescriptor> getProxyDescriptors() {
            return proxyDescriptors;
        }


        public Query getQuery() {
            return query;
        }


        public String getRegionName() {
            return regionName;
        }


        public SinkDescriptor getSinkDescriptor() {
            return sinkDescriptor;
        }


        public SortedStreamDescriptor getSortDescriptor() {
            return sortDescriptor;
        }


        public List<SourceDescriptor> getSourceDescriptors() {
            return sourceDescriptors;
        }


        public TransformedStreamDescriptor getTransformDescriptor() {
            return transformDescriptor;
        }


        public void setAggregationDescriptor(AggregationDescriptor aggregationDescriptor) {
            this.aggregationDescriptor = aggregationDescriptor;
            this.nameToObject.put(aggregationDescriptor.getName(), aggregationDescriptor);
        }


        public void setBridgeDescriptor(BridgeDescriptor bridgeDescriptor) {
            this.bridgeDescriptor = bridgeDescriptor;
        }


        public void setColumnNameToType(LinkedHashMap<String, String> columnNameToType) {
            this.columnNameToType = columnNameToType;
        }


        public void setDistinctDescriptor(DistinctFilteredStreamDescriptor distinctDescriptor) {
            this.distinctDescriptor = distinctDescriptor;
        }


        public void setJoinDescriptor(JoinDescriptor joinDescriptor) {
            this.joinDescriptor = joinDescriptor;
        }


        public void setLimitDescriptor(TruncatedStreamDescriptor limitDescriptor) {
            this.limitDescriptor = limitDescriptor;
        }


        public void setSinkDescriptor(SinkDescriptor sinkDescriptor) {
            this.sinkDescriptor = sinkDescriptor;
        }


        public void setSortDescriptor(SortedStreamDescriptor sortDescriptor) {
            this.sortDescriptor = sortDescriptor;
        }


        public void setTransformDescriptor(TransformedStreamDescriptor transformDescriptor) {
            this.transformDescriptor = transformDescriptor;
        }
    }


    protected final TypeManager typeManager;

    protected final QueryExecutionPlanDescriptor qepDescriptor;

    //protected int joinedTupleInfoCounter = 0;
    protected int reteEntityInfoCounter = 0;
    protected int tupleInfoCounter = 0;
    private final EvaluatorDescriptorFactory evaluatorDescriptorFactory;


    /**
     * @param query      Query to build a plan for.
     * @param regionName String name of the region queried.
     * @throws Exception upon error
     */
    public QueryExecutionPlanDescriptorBuilder(
            Query query,
            String regionName)
            throws Exception {

        this.typeManager = query.getQuerySession().getRuleSession().getRuleServiceProvider().getTypeManager();
        this.evaluatorDescriptorFactory = new EvaluatorDescriptorFactory(this.typeManager);
        this.qepDescriptor = new QueryExecutionPlanDescriptorImpl(query, regionName);
    }


    public String buildAggregationCreation(
            AggregationDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalAggregationName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setAggregationDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public void buildAggregateItemInfo(
            String mapName,
            String columnName,
            AggregateCreator aggregateCreator,
            String tveName)
            throws Exception {

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final Map<String, GroupAggregateItemInfo> map =
                (Map<String, GroupAggregateItemInfo>) nameToObject.get(mapName);

        if (null == tveName) {
            map.put(columnName, new GroupAggregateItemInfo(new AggregateItemInfo(aggregateCreator, null)));

        } else {
            final ExtractorDescriptor extractorDescr = (ExtractorDescriptor) nameToObject.get(tveName);
            final TupleValueExtractor tve = extractorDescr.getExtractor();
            map.put(columnName, new GroupAggregateItemInfo(new AggregateItemInfo(aggregateCreator, tve)));
        }

    }


    /**
     * Adds an initialized ComplexAndExpression to the generated class.
     *
     * @param ctx          ModelContext associated to the ComplexAndExpression.
     * @param operandNames String name of the operands to the AND.
     * @return String name of the generated variable.
     * @throws Exception upon problem.
     */
    public String buildAndExpression(
            ModelContext ctx,
            String[] operandNames)
            throws Exception {

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
        final String name = this.qepDescriptor.getNameGenerator().makeExpressionName();

        final LinkedHashMap<String, String> usedColumnNameToType = new LinkedHashMap<String, String>();
        final Expression[] expressions;
        final List<ExpressionDescriptor> expressionDescriptors = new LinkedList<ExpressionDescriptor>();
        final AliasMapDescriptor aliasMapDescr;

        if ((null == operandNames) || (operandNames.length < 1)) {
            expressions = new Expression[0];
            aliasMapDescr = new AliasMapDescriptor(ctx);

        } else {
            expressions = new Expression[operandNames.length];
            final ExpressionDescriptor firstDescr = (ExpressionDescriptor) nameToObject.get(operandNames[0]);
            aliasMapDescr = firstDescr.getAliasMapDescriptor();
            int i = 0;
            for (String opName : operandNames) {
                final ExpressionDescriptor exprDescr = (ExpressionDescriptor) nameToObject.get(opName);
                usedColumnNameToType.putAll(exprDescr.getUsedColumnNames());
                expressions[i++] = exprDescr.getExpression();
                expressionDescriptors.add(exprDescr);
            }
        }

        nameToObject.put(name,
                new ExpressionDescriptor(new ComplexAndExpression(expressions), expressionDescriptors,
                        aliasMapDescr, usedColumnNameToType));

        return name;
    }


    public String buildBridgeCreation(
            BridgeDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalBridgeName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setBridgeDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public void buildOuputColumns(LinkedHashMap<String, String> columnNameToType) {
        this.qepDescriptor.setColumnNameToType(columnNameToType);
    }


    public String buildDeletedCreation(
            StreamDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalDeletedName();

        descriptor.setVariableName(name);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildDistinctCreation(
            DistinctFilteredStreamDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalDistinctName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setDistinctDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildEqualsExpression(
            ModelContext ctx,
            String leftExprName,
            String rightExprName)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeExpressionName();
        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final SimpleExpressionDescriptor leftExprDescr = (SimpleExpressionDescriptor) nameToObject.get(leftExprName);
        final AliasMapDescriptor leftAliasMapDescr = leftExprDescr.getAliasMapDescriptor();
        final EvaluatorDescriptor leftEvalDescr = leftExprDescr.getEvaluatorDescriptor();
        final Map<String, Class<? extends Tuple>> leftAliasMap = new LinkedHashMap<String, Class<? extends Tuple>>();
        for (String colName : leftEvalDescr.getUsedColumnNames().keySet()) {
            final TupleInfoColumnDescriptor columnDescriptor = leftAliasMapDescr.getColumnByName(colName);
            leftAliasMap.put(columnDescriptor.getName(),
                    columnDescriptor.getTypeInfo().getRuntimeClass(this.typeManager));
        }

        final SimpleExpressionDescriptor rightExprDescr = (SimpleExpressionDescriptor) nameToObject.get(rightExprName);
        final EvaluatorDescriptor rightEvalDescr = rightExprDescr.getEvaluatorDescriptor();
        final Map<String, Class<? extends Tuple>> rightAliasMap = new LinkedHashMap<String, Class<? extends Tuple>>();
        for (String colName : rightEvalDescr.getUsedColumnNames().keySet()) {
            final TupleInfoColumnDescriptor columnDescriptor = leftAliasMapDescr.getColumnByName(colName);
            rightAliasMap.put(columnDescriptor.getName(),
                    columnDescriptor.getTypeInfo().getRuntimeClass(this.typeManager));
        }

        final Expression leftExpr = new SimpleExpression(leftEvalDescr.getEvaluator(), leftAliasMap);
        final Expression rightExpr = new SimpleExpression(rightEvalDescr.getEvaluator(), rightAliasMap);
        final Expression eqExpr = new EqualsExpression(leftExpr, rightExpr);
        final LinkedHashMap<String, String> usedColumnNameToType = new LinkedHashMap<String, String>();
        usedColumnNameToType.putAll(leftEvalDescr.getUsedColumnNames());
        usedColumnNameToType.putAll(rightEvalDescr.getUsedColumnNames());
        final List<ExpressionDescriptor> expressionDescriptors = new ArrayList<ExpressionDescriptor>(2);
        expressionDescriptors.add(leftExprDescr);
        expressionDescriptors.add(rightExprDescr);
        nameToObject.put(name,
                new ExpressionDescriptor(eqExpr, expressionDescriptors, leftAliasMapDescr, usedColumnNameToType));

        return name;
    }


    public String buildEvaluator(
            EvaluatorDescriptor evaluatorDescriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeEvaluatorName();

        this.qepDescriptor.getNameToObject().put(name, evaluatorDescriptor);
        return name;
    }


    public String buildExtractor(
            ExtractorDescriptor extractorDescriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeTVEName();

        this.qepDescriptor.getNameToObject().put(name, extractorDescriptor);
        return name;
    }


    public String buildFilter(
            FilterDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalFilterName();

        this.qepDescriptor.getFilterDescriptors().add(descriptor);

        descriptor.setVariableName(name);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildGroupAggregateInfo(
            ModelContext ctx,
            String mapName)
            throws Exception {

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
        final LinkedHashMap<String, GroupAggregateItemInfo> map =
                (LinkedHashMap<String, GroupAggregateItemInfo>) nameToObject.get(mapName);

        final String name = this.qepDescriptor.getNameGenerator().makeGroupAggregateInfoName();
        final GroupAggregateInfo groupAggregateInfo = new GroupAggregateInfo(map);

        nameToObject.put(name, groupAggregateInfo);
        return name;
    }


    public String buildGroupAggregateItemInfoMap()
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalGroupAggregateItemInfoMapName();
        final LinkedHashMap<String, GroupAggregateItemInfo> map = new LinkedHashMap<String, GroupAggregateItemInfo>();

        this.qepDescriptor.getNameToObject().put(name, map);
        return name;
    }


    public void buildGroupItemInfo(
            String mapName,
            String columnName,
            String tveName)
            throws Exception {

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
        final Map map = (Map) nameToObject.get(mapName);
        final ExtractorDescriptor extractorDescriptor = (ExtractorDescriptor) nameToObject.get(tveName);
        map.put(columnName, new GroupAggregateItemInfo(new GroupItemInfo(extractorDescriptor.getExtractor())));
    }


    public String buildHavingFilterCreation(
            FilterDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalFilterName();

        this.qepDescriptor.getHavingFilterDescriptors().add(descriptor);

        descriptor.setVariableName(name);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildInsertCreation(
            StreamDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalInsertName();

        descriptor.setVariableName(name);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    /**
     * Adds the creation of a join.
     *
     * @param descriptor StreamDescriptor describing the join
     * @return String name of the generated variable.
     * @throws Exception upon problem.
     */
    public String buildJoinCreation(
            JoinDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalJoinName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setJoinDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    /**
     * Adds an initialized JoinedTupleInfo field to the generated code.
     *
     * @param descriptor TupleInfoDescriptorImpl describing the TupleInfo to create.
     *                   This will be updated with the tupleClassName generated in this method.
     * @return String name of the TupleInfo field created.
     * @throws Exception upon problem.
     */
    public String buildJoinedTupleInfo(
            TupleInfoDescriptor descriptor)
            throws Exception {

//        final int typeIndex = useNewClass ? (++joinedTupleInfoCounter) : 0;
        final int typeIndex = 0;
        final Class tupleClass = HeavyJoinedTuples.HeavyJoinedTupleInfo.fetchContainerClass(typeIndex);

        return this.buildTupleInfo(descriptor, tupleClass, HeavyJoinedTuples.HeavyJoinedTupleInfo.class, typeIndex);
    }


    public String buildLimitCreation(
            TruncatedStreamDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalLimitName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setLimitDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildLiteral(
            ModelContext ctx,
            Object value)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLiteralName();
        this.qepDescriptor.getNameToObject().put(name, value);

        return name;
    }


    public String buildNotExpression(
            ModelContext ctx,
            String operandName)
            throws Exception {

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
        final String name = this.qepDescriptor.getNameGenerator().makeExpressionName();

        final LinkedHashMap<String, String> usedColumnNameToType = new LinkedHashMap<String, String>();
        final Expression expression;
        final List<ExpressionDescriptor> expressionDescriptorList = new LinkedList<ExpressionDescriptor>();
        final AliasMapDescriptor aliasMapDescr;

        if (null == operandName) {
            expression = null;
            aliasMapDescr = new AliasMapDescriptor(ctx);

        } else {
            final ExpressionDescriptor exprDescr = (ExpressionDescriptor) nameToObject.get(operandName);
            expressionDescriptorList.add(exprDescr);
            aliasMapDescr = exprDescr.getAliasMapDescriptor();
            usedColumnNameToType.putAll(exprDescr.getUsedColumnNames());
            expression = exprDescr.getExpression();
        }

        nameToObject.put(name,
                new ExpressionDescriptor(new NotExpression(expression), expressionDescriptorList,
                        aliasMapDescr, usedColumnNameToType));

        return name;
    }


    public String buildOrExpression(
            ModelContext ctx,
            String[] operandNames)
            throws Exception {

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
        final String name = this.qepDescriptor.getNameGenerator().makeExpressionName();

        final LinkedHashMap<String, String> usedColumnNameToType = new LinkedHashMap<String, String>();
        final Expression[] expressions;
        final List<ExpressionDescriptor> expressionDescriptors = new LinkedList<ExpressionDescriptor>();
        final AliasMapDescriptor aliasMapDescr;

        if ((null == operandNames) || (operandNames.length < 1)) {
            expressions = new Expression[0];
            aliasMapDescr = new AliasMapDescriptor(ctx);

        } else {
            expressions = new Expression[operandNames.length];
            final ExpressionDescriptor firstDescr = (ExpressionDescriptor) nameToObject.get(operandNames[0]);
            aliasMapDescr = firstDescr.getAliasMapDescriptor();
            int i = 0;
            for (String opName : operandNames) {
                final ExpressionDescriptor exprDescr = (ExpressionDescriptor) nameToObject.get(opName);
                usedColumnNameToType.putAll(exprDescr.getUsedColumnNames());
                expressions[i++] = exprDescr.getExpression();
                expressionDescriptors.add(exprDescr);
            }
        }

        nameToObject.put(name,
                new ExpressionDescriptor(new ComplexOrExpression(expressions), expressionDescriptors,
                        aliasMapDescr, usedColumnNameToType));

        return name;
    }


    public String buildPartitionCreation(
            PartitionDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalPartitionName();

        descriptor.setVariableName(name);
        this.qepDescriptor.getPartitionDescriptors().add(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildPassThroughBridge(
            BridgeDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalBridgeName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setBridgeDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildProxyCreation(
            ProxyDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalSourceName();

        descriptor.setVariableName(name);
        this.qepDescriptor.getProxyDescriptors().add(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    /**
     * Adds an initialized ReteEntityInfo field (and dependencies) to the generated code.
     *
     * @param descriptor TupleInfoDescriptor describing the TupleInfo to create.
     *                   This will be updated with the tupleClassName generated in this method.
     * @return String name of the ReteEntityInfo field created.
     * @throws Exception upon problem.
     */
    public String buildReteEntityInfo(
            TupleInfoDescriptor descriptor)
            throws Exception {

        final int typeIndex = ++reteEntityInfoCounter;
        final Class tupleClass = HeavyReteEntities.HeavyReteEntityInfo.fetchContainerClass(typeIndex);

        return this.buildTupleInfo(descriptor, tupleClass, HeavyReteEntities.HeavyReteEntityInfo.class, typeIndex);
    }


    public String buildRuleFunctionWrapper(
            Class ruleFunctionClass)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeRuleFunctionInstanceName();

        this.qepDescriptor.getNameToObject().put(name, ruleFunctionClass.newInstance());
        return name;
    }


    public String buildSimpleExpression(
            AliasMapDescriptor aliasMapDescriptor,
            EvaluatorDescriptor evaluatorDescriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeExpressionName();

        final Map<String, Class<? extends Tuple>> aliasMap = new LinkedHashMap<String, Class<? extends Tuple>>();
        for (String colName : evaluatorDescriptor.getUsedColumnNames().keySet()) {
            final TupleInfoColumnDescriptor columnDescriptor = aliasMapDescriptor.getColumnByName(colName);
            aliasMap.put(columnDescriptor.getName(), columnDescriptor.getTypeInfo().getRuntimeClass(this.typeManager));
        }

        final SimpleExpressionDescriptor expressionDescriptor = new SimpleExpressionDescriptor(
                new SimpleExpression(evaluatorDescriptor.getEvaluator(), aliasMap),
                aliasMapDescriptor,
                evaluatorDescriptor);

        this.qepDescriptor.getNameToObject().put(name, expressionDescriptor);

        return name;
    }


    /**
     * @param descriptor SinkDescriptor describing the Sink.
     * @return String name of the local variable that holds the Sink.
     */
    public String buildSinkCreation(
            SinkDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalSinkName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setSinkDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildSlidingWindowBuilderCreation(
            WindowBuilderDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeSlidingWindowBuilderName();

        descriptor.setVariableName(name);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public WindowInfo buildSlidingWindowInfo(
            ModelContext ctx,
            int size)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeSlidingWindowInfoName();

        final WindowInfo windowInfo = new SlidingWindowInfo(size);

        this.qepDescriptor.getNameToObject().put(name, windowInfo);
        return windowInfo;
    }


    public String buildSortedBridge(
            SortedBridgeDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalBridgeName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setBridgeDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildSortInfo(
            ModelContext ctx,
            List<String> sortItemInfoNames)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeSortInfoName();
        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final SortItemInfo[] sortItemInfos = new SortItemInfo[sortItemInfoNames.size()];
        int i = 0;
        for (String sortItemInfoName : sortItemInfoNames) {
            sortItemInfos[i++] = (SortItemInfo) nameToObject.get(sortItemInfoName);
        }

        nameToObject.put(name, new SortInfo(sortItemInfos));
        return name;
    }


    public String buildSortInfoExtractors(
            List<String> extractorNames)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeExtractorsName();
        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final TupleValueExtractor[] extractors = new TupleValueExtractor[extractorNames.size()];
        for (int i = 0; i < extractors.length; i++) {
            final ExtractorDescriptor extractorDescr = (ExtractorDescriptor) nameToObject.get(extractorNames.get(i));
            extractors[i] = extractorDescr.getExtractor();
        }

        nameToObject.put(name, extractors);
        return name;
    }


    public String buildSortInfoComparators(
            List<Comparator<Object>> comparators)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeComparatorsName();

        this.qepDescriptor.getNameToObject().put(name, comparators);
        return name;
    }


    public String buildSortItemInfo(
            ModelContext ctx,
            boolean ascending)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeSortItemInfoName();

        this.qepDescriptor.getNameToObject().put(name, new SortItemInfo(ascending));
        return name;
    }


    public String buildSortItemInfo(
            ModelContext ctx,
            boolean ascending,
            String limitFirstName,
            String limitOffsetName)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeSortItemInfoName();

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final EvaluatorDescriptor limitFirst = (EvaluatorDescriptor) nameToObject.get(limitFirstName);
        final EvaluatorDescriptor limitOffset = (EvaluatorDescriptor) nameToObject.get(limitOffsetName);

        this.qepDescriptor.getNameToObject().put(name,
                new SortItemInfo(ascending, limitFirst.getEvaluator(), limitOffset.getEvaluator()));
        return name;
    }


    /**
     * Adds a sort creation to the generated code.
     *
     * @param descriptor SortedStreamDescriptor describing the sort.
     * @return String name of the variable generated.
     * @throws Exception upon problem.
     */
    public String buildSortCreation(
            SortedStreamDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalSortName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setSortDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    /**
     * Adds a Source creation in the generated code.
     *
     * @param descriptor SourceDescriptor describing the source.
     * @return String name of the local variable that holds the Source created.
     * @throws Exception upon problem.
     */
    public String buildSourceCreation(
            SourceDescriptor descriptor)
            throws Exception {

        String name = descriptor.getAlias();
        if (name == null) {
            name = this.qepDescriptor.getNameGenerator().makeLocalSourceName();
        }

        descriptor.setVariableName(name);
        this.qepDescriptor.getSourceDescriptors().add(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    /**
     * Adds to the generated code the TransformationInfo that represents the projection.
     *
     * @param transformationMapName String name of the Map variable used by the TransformationInfo.
     * @return String name of the generated variable.
     * @throws Exception upon problem.
     */
    public String buildTransformInfo(
            String transformationMapName)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeTransformationInfoName();
        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final LinkedHashMap<String, TransformItemInfo> map =
                (LinkedHashMap<String, TransformItemInfo>) nameToObject.get(transformationMapName);
        nameToObject.put(name, new TransformInfo(map));

        return name;
    }


    public String buildTimeWindowBuilderCreation(
            ModelContext ctx,
            WindowBuilderDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeTimeWindowBuilderName();

        descriptor.setVariableName(name);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public WindowInfo buildTimeWindowInfo(
            ModelContext ctx,
            int size,
            long time,
            TimeWindowInfo.TupleTimestampExtractor timestampExtractor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeTimeWindowInfoName();

        final WindowInfo windowInfo = new TimeWindowInfo(size, time, timestampExtractor);
        this.qepDescriptor.getNameToObject().put(name, windowInfo);
        return windowInfo;
    }


    /**
     * Adds a transform creation to the generated code.
     *
     * @param descriptor TransformedStreamDescriptor describing the transformation.
     * @return String name of the variable generated.
     * @throws Exception upon problem.
     */
    public String buildTransformCreation(
            TransformedStreamDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalTransformName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setTransformDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    /**
     * Adds to the generated code the TransformationMap that will be used in the TransformationInfo.
     *
     * @param aliasToValueExtractorName LinkedHashMap of String projection alias to String name of TupleValueExtractor.
     * @return String name of the tranformation map created.
     * @throws Exception upon problem
     */
    public String buildTransformMap(
            LinkedHashMap<String, String> aliasToValueExtractorName)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeTransformationMapName();
        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        final LinkedHashMap<String, TransformItemInfo> map = new LinkedHashMap<String, TransformItemInfo>();

        for (Map.Entry<String, String> entry : aliasToValueExtractorName.entrySet()) {
            final ExtractorDescriptor extractorDescriptor = (ExtractorDescriptor) nameToObject.get(entry.getValue());
            map.put(entry.getKey(), new TransformItemInfo(extractorDescriptor.getExtractor()));
        }

        nameToObject.put(name, map);
        return name;
    }


    public String buildTruncatedBridge(
            BridgeDescriptor descriptor) {

        final String name = this.qepDescriptor.getNameGenerator().makeLocalBridgeName();

        descriptor.setVariableName(name);
        this.qepDescriptor.setBridgeDescriptor(descriptor);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public String buildTumblingWindowBuilderCreation(
            WindowBuilderDescriptor descriptor)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeTumblingWindowBuilderName();

        descriptor.setVariableName(name);
        this.qepDescriptor.getNameToObject().put(name, descriptor);
        return name;
    }


    public WindowInfo buildTumblingWindowInfo(
            ModelContext ctx,
            int size)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeTumblingWindowInfoName();

        final WindowInfo windowInfo = new TumblingWindowInfo(size);
        this.qepDescriptor.getNameToObject().put(name, windowInfo);
        return windowInfo;
    }


    /**
     * Adds an initialized TupleInfo field to the generated code.
     *
     * @param descriptor  TupleInfoDescriptorImpl describing the TupleInfo to create.
     *                    This will be updated with the tupleClassName generated in this method.
     * @param useNewClass boolean if true a new class will be used for the tuple (useful for joins).
     * @return String name of the TupleInfo field created.
     * @throws Exception upon problem
     */
    public String buildTupleInfo(
            TupleInfoDescriptor descriptor,
            boolean useNewClass)
            throws Exception {

        final int typeIndex = useNewClass ? (++tupleInfoCounter) : 0;
        final Class tupleClass = HeavyTuples.HeavyTupleInfo.fetchContainerClass(typeIndex);

        return this.buildTupleInfo(descriptor, tupleClass, HeavyTuples.HeavyTupleInfo.class, typeIndex);
    }


    protected String buildTupleInfo(
            TupleInfoDescriptor descriptor,
            Class tupleClass,
            Class tupleInfoClass,
            int typeIndex)
            throws Exception {

        final String name = this.qepDescriptor.getNameGenerator().makeTupleInfoName();

        descriptor.setTypeIndex(typeIndex);
        descriptor.setTupleInfoClassName(tupleInfoClass.getCanonicalName());
        descriptor.setTupleClass(tupleClass);
        //descriptor.setTupleClassName(tupleClass.getCanonicalName());

        final Constructor constructor = tupleInfoClass.getConstructor(String[].class, Class[].class, int.class);

        final Collection<? extends TupleInfoColumnDescriptor> columns = descriptor.getColumns();
        final String[] columnNames = new String[columns.size()];
        final Class[] columnClasses = new Class[columns.size()];
        int i = 0;
        for (TupleInfoColumnDescriptor col : columns) {
            columnNames[i] = col.getName();
            columnClasses[i] = col.getTypeInfo().getRuntimeClass(this.typeManager);
            i++;
        }

        this.qepDescriptor.getNameToObject().put(name, constructor.newInstance(columnNames, columnClasses, typeIndex));
        return name;
    }


    public EvaluatorDescriptorFactory getEvaluatorDescriptorFactory() {
        return this.evaluatorDescriptorFactory;
    }


    /**
     * Returns the Query for which this object builds a plan.
     *
     * @return Query
     */
    public QueryExecutionPlanDescriptor getQueryExecutionPlanDescriptor() {
        return this.qepDescriptor;
    }


    public void optimize() {
        this.optimizeJoin();
        this.optimizeFilters();
    }


    protected void optimizeFilters() {
        final Map<SourceDescriptor, List<FilterDescriptor>> srcDescToFilterDescs =
                new HashMap<SourceDescriptor, List<FilterDescriptor>>();

        final List<FilterDescriptor> filterDescriptors = this.qepDescriptor.getFilterDescriptors();
        if ((null == filterDescriptors) || (filterDescriptors.size() < 1)) {
            return;
        }

        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();

        // Finds FilterDescriptor's that can be moved into SourceDescriptor's.
        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            StreamDescriptor inputDescriptor = filterDescriptor.getInputStreamDescriptors()[0];
            if (inputDescriptor instanceof ProxyDescriptor) {
                inputDescriptor = inputDescriptor.getInputStreamDescriptors()[0];
            }
            if (inputDescriptor instanceof SourceDescriptor) {
                boolean canMoveFilterToWithinSource = true;
                for (StreamDescriptor outputDescriptor : inputDescriptor.getOutputStreamDescriptors()) {
                    if (outputDescriptor instanceof ProxyDescriptor) {
                        canMoveFilterToWithinSource = false;
                        break;
                    }//if
                }//for

                if (canMoveFilterToWithinSource) {
                    List<FilterDescriptor> filterDescs = srcDescToFilterDescs.get(inputDescriptor);
                    if (null == filterDescs) {
                        filterDescs = new LinkedList<FilterDescriptor>();
                        srcDescToFilterDescs.put((SourceDescriptor) inputDescriptor, filterDescs);
                    }
                    filterDescs.add(filterDescriptor);
                }//if

            }//if
        }//for

        // Moves the movable FilterDescriptor's
        for (Map.Entry<SourceDescriptor, List<FilterDescriptor>> entry : srcDescToFilterDescs.entrySet()) {
            final SourceDescriptor sourceDescriptor = entry.getKey();
            final List<FilterDescriptor> filterDescs = entry.getValue();
            if (filterDescs.size() == 1) {
                final FilterDescriptor filterDescriptor = filterDescs.get(0);
                filterDescriptors.remove(filterDescriptor);
                final ExtractorDescriptor ed = (ExtractorDescriptor)
                        nameToObject.get(filterDescriptor.getFilterExpressionName());
                final ReteEntityFilter filter = new ReteEntityFilterImpl(ed.getExtractor());
                sourceDescriptor.setFilter(filter);

            } else {
                final List<ExpressionEvaluator> evaluators = new ArrayList<ExpressionEvaluator>();
                for (FilterDescriptor filterDescriptor : filterDescs) {
                    final Object exprObj = nameToObject.get(filterDescriptor.getFilterExpressionName());
                    final ExtractorDescriptor ed = (ExtractorDescriptor) exprObj;
                    final TupleValueExtractor e = ed.getExtractor();
                    if (e instanceof EvaluatorToExtractorAdapter) {
                        evaluators.add(((EvaluatorToExtractorAdapter) e).getEvaluator());
                    } else {
                        evaluators.add(new ExtractorToEvaluatorAdapter(e, 0));
                    }
                    this.qepDescriptor.getFilterDescriptors().remove(filterDescriptor);
                }
                if (evaluators.size() > 0) {
                    final AndEvaluator andEvaluator = new AndEvaluator(
                            evaluators.toArray(new ExpressionEvaluator[evaluators.size()]));
                    final ReteEntityFilter filter = new ReteEntityFilterImpl(
                            new EvaluatorToExtractorAdapter(andEvaluator));
                    sourceDescriptor.setFilter(filter);
                }
            }

        }
    }


    protected void optimizeJoin() {
        final JoinDescriptor joinDescriptor = this.qepDescriptor.getJoinDescriptor();
        if (null == joinDescriptor) {
            return;
        }

        // Looking in the join expression, replaces each top AND child that uses a single Source with a Filter.
        // todo
        final LinkedHashMap<String, Object> nameToObject = this.qepDescriptor.getNameToObject();
        final ExpressionDescriptor joinExprDescr = (ExpressionDescriptor)
                nameToObject.get(joinDescriptor.getExpressionFieldName());

        this.optimizeJoin(joinDescriptor, joinExprDescr, false);
    }


    private boolean optimizeJoin(
            JoinDescriptor joinDescriptor,
            ExpressionDescriptor exprDescriptor,
            boolean useNot) {

        final Expression expr = exprDescriptor.getExpression();

        //*** Check whether its a self Join ***
        // This optimization is for self-joins only
        // Pass the filter conditions to the RETE JOIN to get the correct resultset
        // Without passing the filters to the RETE , incorrect results are seen.
       	//------------------
        boolean selfJoin = false;
        HashMap<String, Integer> tempClassNameStore = new HashMap<String, Integer>();

        for (StreamDescriptor streamDescriptor : joinDescriptor.getInputStreamDescriptors()) {
            Map<String, TupleInfoColumnDescriptor> classNameToDescriptor = ((TupleInfoDescriptorImpl) streamDescriptor.getTupleInfoDescriptor()).getClassNameToDescriptor();

            for (Map.Entry mEntry : classNameToDescriptor.entrySet()) {
                String className = (String) mEntry.getKey();

                if (tempClassNameStore.containsKey(className)) {
                    selfJoin = true;
                    break;
                } else {
                    tempClassNameStore.put(className, 1);
                }
            }
        }

        this.qepDescriptor.setSelfJoin(selfJoin);
        tempClassNameStore.clear();
        //------------------

        if ((expr instanceof SimpleExpression)
                || (expr instanceof NotExpression)
                || (expr instanceof EqualsExpression)) {
            final LinkedHashMap<String, String> usedColumnNames = exprDescriptor.getUsedColumnNames();
            if (usedColumnNames.size() < 2) {
                this.optimizeExpressionIntoFilter(joinDescriptor, exprDescriptor, useNot,
                        usedColumnNames);
                return true;
            }
        }

//        todo handle the not case.
//        else if (expr instanceof NotExpression) {
//            final LinkedHashMap<String, String> usedColumnNames = exprDescriptor.getUsedColumnNames();
//            if (usedColumnNames.size() < 2) {
//                return this.optimizeJoin(joinDescriptor, exprDescriptor.getChildExpressionDescriptors().get(0),
//                        nameToObject, !useNot);
//            }
//        }
//
        else if (expr instanceof ComplexAndExpression) {
            final LinkedHashMap<String, String> usedColumnNames = exprDescriptor.getUsedColumnNames();
            if (usedColumnNames.size() < 2) {
                this.optimizeExpressionIntoFilter(joinDescriptor, exprDescriptor, useNot,
                        usedColumnNames);
                return true;

            } else {
                final LinkedHashMap<String, String> newUsedColumnNames = exprDescriptor.getUsedColumnNames();
                final List<ExpressionDescriptor> childDescriptors =
                        exprDescriptor.getChildExpressionDescriptors();
                final List<ExpressionDescriptor> remainingDescriptors =
                        new LinkedList<ExpressionDescriptor>();
                for (ExpressionDescriptor child : childDescriptors) {
                    if (!this.optimizeJoin(joinDescriptor, child, useNot)) {
                        remainingDescriptors.add(child);
                    }
                }
                childDescriptors.clear();
                newUsedColumnNames.clear();
                final List<Expression> remainingExpressions = new ArrayList<Expression>(remainingDescriptors.size());
                for (ExpressionDescriptor remainingDescriptor : remainingDescriptors) {
                    childDescriptors.add(remainingDescriptor);
                    remainingExpressions.add(remainingDescriptor.getExpression());
                    newUsedColumnNames.putAll(remainingDescriptor.getUsedColumnNames());
                }

                //*** If  Self Join  let the expression contain filters also along with the Rete Join ***
                if (selfJoin == false) {
                    exprDescriptor.setExpression(new ComplexAndExpression(
                            remainingExpressions.toArray(new Expression[remainingDescriptors.size()])));
                }
            }
        }

        return false;
    }


    private void optimizeExpressionIntoFilter(
            JoinDescriptor joinDescriptor,
            ExpressionDescriptor exprDescriptor,
            boolean useNot,
            LinkedHashMap<String, String> usedColumnNames) {

        if (usedColumnNames.size() == 0) {
            return;
        }

        for (StreamDescriptor inputStreamDescriptor : joinDescriptor.getInputStreamDescriptors()) {
            if (usedColumnNames.keySet().contains(inputStreamDescriptor.getName())) {

                EvaluatorDescriptor ed = this.makeEvaluatorDescriptor(exprDescriptor);
                if (useNot) {
                    ed = new EvaluatorDescriptor(new NotEvaluator(ed.getEvaluator()), TypeInfoImpl.PRIMITIVE_BOOLEAN);
                }
                final String extrName = this.buildExtractor(new ExtractorDescriptor(ed));

                this.buildFilter(new FilterDescriptor("optmzFilter_" + exprDescriptor.hashCode(),
                        inputStreamDescriptor, inputStreamDescriptor.getTupleInfoDescriptor(),
                        inputStreamDescriptor.getTupleInfoFieldName(), null, extrName));
                return;
            }
        }

        throw new IllegalArgumentException("Cannot find input descriptor in used column names.");
    }


    private EvaluatorDescriptor makeEvaluatorDescriptor(ExpressionDescriptor exprDescriptor) {
        if (exprDescriptor instanceof SimpleExpressionDescriptor) {
            final SimpleExpressionDescriptor sed = (SimpleExpressionDescriptor) exprDescriptor;
            return sed.getEvaluatorDescriptor();
        }

        final Expression expr = exprDescriptor.getExpression();
        final List<ExpressionEvaluator> argEvaluators = new LinkedList<ExpressionEvaluator>();
        final List<EvaluatorDescriptor> argDescriptors = new LinkedList<EvaluatorDescriptor>();
        for (ExpressionDescriptor argExprDescriptor : exprDescriptor.getChildExpressionDescriptors()) {
            final EvaluatorDescriptor ed = this.makeEvaluatorDescriptor(argExprDescriptor);
            argDescriptors.add(ed);
            argEvaluators.add(ed.getEvaluator());
        }

        if (expr instanceof ComplexAndExpression) {
            final int numArgs = argDescriptors.size();
            if (1 == numArgs) {
                return argDescriptors.get(0);
            }
            return new EvaluatorDescriptor(
                    new AndEvaluator(argEvaluators.toArray(new ExpressionEvaluator[numArgs])),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
        }
        if (expr instanceof ComplexOrExpression) {
            final int numArgs = argDescriptors.size();
            if (1 == numArgs) {
                return argDescriptors.get(0);
            }
            return new EvaluatorDescriptor(
                    new OrEvaluator(argEvaluators.toArray(new ExpressionEvaluator[numArgs])),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
        }

        if (expr instanceof EqualsExpression) {
            return new EvaluatorDescriptor(
                    new EqualityEvaluator(argEvaluators.get(0), argEvaluators.get(1)),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
        }

        if (expr instanceof NotExpression) {
            return new EvaluatorDescriptor(
                    new NotEvaluator(argEvaluators.get(0)),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
        }

        throw new UnsupportedOperationException("Cannot optimize Expression of type " + expr.getClass().getName());
    }


}
