package com.tibco.cep.query.exec.codegen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;

import com.tibco.be.util.idgenerators.serial.PrefixedLeftPaddedNumericGenerator;
import com.tibco.cep.query.exec.ExecutionClassInfo;
import com.tibco.cep.query.exec.ModifierMask;
import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.AliasMapDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.BridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.DistinctFilteredStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SinkDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SortedBridgeDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SortedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.SourceDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TransformedStreamDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.TruncatedStreamDescriptor;
import com.tibco.cep.query.exec.impl.NameGenerator;
import com.tibco.cep.query.exec.prebuilt.HeavyJoinedTuples;
import com.tibco.cep.query.exec.prebuilt.HeavyReteEntities;
import com.tibco.cep.query.exec.prebuilt.HeavyTuples;
import com.tibco.cep.query.exec.util.Escaper;
import com.tibco.cep.query.model.AcceptType;
import com.tibco.cep.query.model.BindVariable;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.ProxyContext;
import com.tibco.cep.query.service.Query;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.AbstractExpression;
import com.tibco.cep.query.stream.expression.ComplexAndExpression;
import com.tibco.cep.query.stream.expression.ComplexOrExpression;
import com.tibco.cep.query.stream.expression.EvaluatableExpression;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.NotExpression;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.group.GroupAggregateInfo;
import com.tibco.cep.query.stream.impl.rete.ReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.impl.rete.join.EqualsExpression;
import com.tibco.cep.query.stream.join.JoinedTuple;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.partition.SlidingWindowInfo;
import com.tibco.cep.query.stream.partition.TimeWindowInfo;
import com.tibco.cep.query.stream.partition.TumblingWindowInfo;
import com.tibco.cep.query.stream.partition.WindowBuilder;
import com.tibco.cep.query.stream.sort.SortInfo;
import com.tibco.cep.query.stream.transform.TransformInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Dec 5, 2007
 * Time: 2:32:41 PM
 */

/**
 * <p>Builder for the generated source code.</p>
 * <p>Known directors: {@link com.tibco.cep.query.exec.codegen.CodeGenerationQueryExecutionPlanFactory},
 * {@link ExtractionCodeHelper}.</p>
 *
 * @see com.tibco.cep.query.exec.codegen.CodeGenerationQueryExecutionPlanFactory
 * @see ExtractionCodeHelper
 */
public class QueryExecutionPlanClassBuilder {

    public static final String NAME_OF_PACKAGE_FOR_QEP = "com.tibco.cep.query.gen";


    private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
    protected static final Class[] ARG_CLASSES_FOR_EXPRESSION_CSTR = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_EXPRESSION_EVALUATE = new Class[]{GlobalContext.class, QueryContext.class, FixedKeyHashMap.class};
    protected static final Class[] ARG_CLASSES_FOR_INITIALIZE_AS_CONTINUOUS = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_INITIALIZE_AS_SINGLE_SHOT = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_JOINEDTUPLE_CSTR1 = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_JOINEDTUPLE_CSTR2 = new Class[]{Long.class, Object[].class,};
    protected static final Class[] ARG_CLASSES_FOR_QEP_CSTR = new Class[]{String.class, String.class};
    protected static final Class[] ARG_CLASSES_FOR_RETEENTITYINFO_CREATERETEENTITY = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_RETEENTITY_CSTR1 = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_RETEENTITYINFO_CSTR = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_TUPLE_CSTR1 = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_TUPLE_CSTR2 = new Class[]{Long.class, Object[].class,};
    protected static final Class[] ARG_CLASSES_FOR_TUPLEINFO_CSTR = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_TUPLEINFO_CREATEJOINEDTUPLE = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_TUPLEINFO_CREATETUPLE = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_TVE_CSTR = EMPTY_CLASS_ARRAY;
    protected static final Class[] ARG_CLASSES_FOR_TVE_EXTRACT = new Class[]{GlobalContext.class, QueryContext.class, Tuple.class};

    protected static final String BODY_FOR_RETEENTITY_CSTR1 = "super();";
    protected static final String BODY_FOR_TUPLE_CSTR1 = "super();";
    protected static final String BODY_FOR_TUPLE_CSTR2 = "super($1, $2);";

    protected static final Class[] EXCEPTION_CLASSES_FOR_EXPRESSION_EVALUATE = EMPTY_CLASS_ARRAY;
    protected static final Class[] EXCEPTION_CLASSES_FOR_INITIALIZE_AS_CONTINUOUS = new Class[]{Exception.class};
    protected static final Class[] EXCEPTION_CLASSES_FOR_INITIALIZE_AS_SINGLE_SHOT = EXCEPTION_CLASSES_FOR_INITIALIZE_AS_CONTINUOUS;
    protected static final Class[] EXCEPTION_CLASSES_FOR_RETEENTITYINFO_CREATERETEENTITY = EMPTY_CLASS_ARRAY;
    protected static final Class[] EXCEPTION_CLASSES_FOR_TUPLEINFO_CREATEJOINEDTUPLE = EMPTY_CLASS_ARRAY;
    protected static final Class[] EXCEPTION_CLASSES_FOR_TUPLEINFO_CREATETUPLE = EMPTY_CLASS_ARRAY;
    protected static final Class[] EXCEPTION_CLASSES_FOR_TVE_EXTRACT = EMPTY_CLASS_ARRAY;

    protected static final String[] NAME_OF_METHOD_EXPRESSION_EVALUATE = new String[]{
            "evaluate", "evaluateBoolean", "evaluateDouble", "evaluateFloat", "evaluateInteger", "evaluateLong"};
    protected static final String NAME_OF_METHOD_INITIALIZE_AS_CONTINUOUS = "initializeAsContinuous";
    protected static final String NAME_OF_METHOD_INITIALIZE_AS_SNAPSHOT = "initializeAsSnapshot";
    protected static final String NAME_OF_METHOD_RETEENTITYINFO_CREATERETEENTITY = "createTuple";
    protected static final String NAME_OF_METHOD_TUPLEINFO_CREATEJOINEDTUPLE = "createTuple";
    protected static final String NAME_OF_METHOD_TUPLEINFO_CREATETUPLE = "createTuple";
    protected static final String[] NAME_OF_METHOD_TVE_EXTRACT = new String[]{
            "extract", "extractInteger", "extractLong", "extractFloat", "extractDouble"};

    protected static final String PREFIX_OF_BIND_VARIABLE = "BV$";

    protected static final Class[] RETURN_TYPE_OF_EXPRESSION_EVALUATE = new Class[]{
            Object.class, boolean.class, double.class, float.class, int.class, long.class };
    protected static final Class RETURN_TYPE_OF_INITIALIZE_AS_CONTINUOUS = void.class;
    protected static final Class RETURN_TYPE_OF_INITIALIZE_AS_SINGLE_SHOT = void.class;
    protected static final Class RETURN_TYPE_OF_RETEENTITYINFO_CREATERETEENTITY = ReteEntity.class;
    protected static final Class RETURN_TYPE_OF_TUPLEINFO_CREATEJOINEDTUPLE = JoinedTuple.class;
    protected static final Class RETURN_TYPE_OF_TUPLEINFO_CREATETUPLE = Tuple.class;
    protected static final Class[] RETURN_TYPE_OF_TVE_EXTRACT = new Class[]{
            Object.class, int.class, long.class, float.class, double.class};

    protected static final String STATIC_FIELD_SEPARATOR = "#";

    protected static final String TEMPLATE_GROUP_NAME = "ExecutionPlan";
    protected static final QueryTemplateRegistry TEMPLATE_REGISTRY = QueryTemplateRegistry.getInstance();

    protected final StringTemplate TEMPLATE_FOR_BODY_OF_RETEENTITYINFO_CSTR;
    protected final StringTemplate TEMPLATE_FOR_BODY_OF_TUPLEINFO_CREATETUPLE;
    protected final StringTemplate TEMPLATE_FOR_BODY_OF_TUPLEINFO_CSTR;
    protected final StringTemplate TEMPLATE_FOR_CREATE_AGGREGATION;
    protected final StringTemplate TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH;
    protected final StringTemplate TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE;
    protected final StringTemplate TEMPLATE_FOR_CREATE_BRIDGE_SORTED;
    protected final StringTemplate TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED;
    protected final StringTemplate TEMPLATE_FOR_CREATE_DELETED;
    protected final StringTemplate TEMPLATE_FOR_CREATE_DISTINCT;
    protected final StringTemplate TEMPLATE_FOR_CREATE_FILTER;
    protected final StringTemplate TEMPLATE_FOR_CREATE_INSERT;
    protected final StringTemplate TEMPLATE_FOR_CREATE_JOIN;
    protected final StringTemplate TEMPLATE_FOR_CREATE_LIMIT;
    protected final StringTemplate TEMPLATE_FOR_CREATE_PARTITION;
    protected final StringTemplate TEMPLATE_FOR_CREATE_PROXY;
    protected final StringTemplate TEMPLATE_FOR_CREATE_SINK;
    protected final StringTemplate TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER;
    protected final StringTemplate TEMPLATE_FOR_CREATE_SORT;
    protected final StringTemplate TEMPLATE_FOR_CREATE_SOURCE;
    protected final StringTemplate TEMPLATE_FOR_CREATE_STATIC_SINK;
    protected final StringTemplate TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER;
    protected final StringTemplate TEMPLATE_FOR_CREATE_TRANSFORMATION;
    protected final StringTemplate TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER;
    protected final StringTemplate TEMPLATE_FOR_CREATE_WITHDRAWABLE_SOURCE;
    protected final StringTemplate TEMPLATE_FOR_INIT_AGGREGATEITEMINFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE;
    protected final StringTemplate TEMPLATE_FOR_INIT_ALIAS_MAP;
    protected final StringTemplate TEMPLATE_FOR_INIT_AND_EXPRESSION;
    protected final StringTemplate TEMPLATE_FOR_INIT_BIND_VARIABLE_EVALUATOR;
    protected final StringTemplate TEMPLATE_FOR_INIT_COMPARATORS_LIST;
    protected final StringTemplate TEMPLATE_FOR_INIT_CONSTANT_EVALUATOR;
    protected final StringTemplate TEMPLATE_FOR_INIT_EMPTY_AND_EXPRESSION;
    protected final StringTemplate TEMPLATE_FOR_INIT_EQUALS_EXPRESSION;
    protected final StringTemplate TEMPLATE_FOR_INIT_EXPRESSION;
    protected final StringTemplate TEMPLATE_FOR_INIT_EXTRACTORS_ARRAY;
    protected final StringTemplate TEMPLATE_FOR_INIT_GROUPAGGREGATEINFOMAP;
    protected final StringTemplate TEMPLATE_FOR_INIT_GROUPAGGREGATEINFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_GROUPITEMINFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_JOINEDTUPLEINFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_BOOLEAN;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_BYTE;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_CHARACTER;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_DATETIME;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_DOUBLE;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_FLOAT;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_INTEGER;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_LONG;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_SHORT;
    protected final StringTemplate TEMPLATE_FOR_INIT_LITERAL_STRING;
    protected final StringTemplate TEMPLATE_FOR_INIT_NOT_EXPRESSION;
    protected final StringTemplate TEMPLATE_FOR_INIT_OR_EXPRESSION;
    protected final StringTemplate TEMPLATE_FOR_INIT_RETEENTITYINFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_RF_WRAPPER;
    protected final StringTemplate TEMPLATE_FOR_INIT_SLIDING_WINDOW_INFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_SORT_INFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_SORT_INFO_WITH_LIMIT;
    protected final StringTemplate TEMPLATE_FOR_INIT_SORT_ITEM_INFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_SORT_ITEM_INFO_WITH_LIMIT;
    protected final StringTemplate TEMPLATE_FOR_INIT_TIME_WINDOW_INFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_TRANSFORM_MAP;
    protected final StringTemplate TEMPLATE_FOR_INIT_TRANSFORM_INFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_TUMBLING_WINDOW_INFO;
    protected final StringTemplate TEMPLATE_FOR_INIT_TUPLE_VALUE_EXTR;
    protected final StringTemplate TEMPLATE_FOR_INIT_TUPLEINFO;


    private final LinkedHashMap<String, ModelContext> variableNameToCtx = new LinkedHashMap<String, ModelContext>();
    private final LinkedHashMap<ModelContext, String> ctxToTupleInfoFieldName = new LinkedHashMap<ModelContext, String>();
    private final LinkedHashMap<String, String> tupleInfoFieldNameToTupleClassName = new LinkedHashMap<String, String>();
    private final LinkedHashMap<String, TupleInfoDescriptor> tupleInfoFieldNameToTupleInfoDescriptor = new LinkedHashMap<String, TupleInfoDescriptor>();

    private final Query query;
    private final ExtractionCodeHelper extractionHelper;
    private final String qepClassName;
    private final StringBuilder initializerBody;
    private final StringBuilder continuousBody;
    private final StringBuilder singleShotBody;
    private final PrefixedLeftPaddedNumericGenerator idGenerator =
            new PrefixedLeftPaddedNumericGenerator("", false, 5);
    private final NameGenerator nameGenerator = new NameGenerator(this.idGenerator);
    private final QueryExecutionClassHelper mainHelper;

    private String sinkName;
    private Class qepClass;
    private int reteEntityInfoCounter = 0;
    private int joinedTupleInfoCounter = 0;
    private int tupleInfoCounter = 0;


    public QueryExecutionPlanClassBuilder(Query query) throws Exception {

        TEMPLATE_FOR_BODY_OF_RETEENTITYINFO_CSTR = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "methodBodyForTupleInfoCstr");
        TEMPLATE_FOR_BODY_OF_TUPLEINFO_CREATETUPLE = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "methodBodyForTupleInfoCreateTuple");
        TEMPLATE_FOR_BODY_OF_TUPLEINFO_CSTR = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "methodBodyForTupleInfoCstr");

        TEMPLATE_FOR_CREATE_AGGREGATION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildAggregation");
        TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildBridgePassThrough");
        TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildBridgeSimple");
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildBridgeSorted");
        TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildBridgeTruncated");
        TEMPLATE_FOR_CREATE_DELETED = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildDeleted");
        TEMPLATE_FOR_CREATE_DISTINCT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildDistinct");
        TEMPLATE_FOR_CREATE_FILTER = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildFilter");
        TEMPLATE_FOR_CREATE_INSERT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildInsert");
        TEMPLATE_FOR_CREATE_JOIN = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildJoin");
        TEMPLATE_FOR_CREATE_LIMIT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildLimit");
        TEMPLATE_FOR_CREATE_PARTITION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildPartition");
        TEMPLATE_FOR_CREATE_PROXY = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildProxy");
        TEMPLATE_FOR_CREATE_SINK = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildSink");
        TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildSlidingWindowBuilder");
        TEMPLATE_FOR_CREATE_SORT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildSort");
        TEMPLATE_FOR_CREATE_SOURCE = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildSource");
        TEMPLATE_FOR_CREATE_STATIC_SINK = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildStaticSink");
        TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildTimeWindowBuilder");
        TEMPLATE_FOR_CREATE_TRANSFORMATION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildTransform");
        TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildTumblingWindowBuilder");
        TEMPLATE_FOR_CREATE_WITHDRAWABLE_SOURCE = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "buildWithdrawableSource");

        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "addAggregateItemInfo");
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "addAggregateItemInfoWithTve");
        TEMPLATE_FOR_INIT_ALIAS_MAP = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticAliasMap");
        TEMPLATE_FOR_INIT_AND_EXPRESSION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticAndExpression");
        TEMPLATE_FOR_INIT_BIND_VARIABLE_EVALUATOR = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticBindVariableEvaluator");
        TEMPLATE_FOR_INIT_COMPARATORS_LIST = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticComparatorsList");
        TEMPLATE_FOR_INIT_CONSTANT_EVALUATOR = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticConstantEvaluator");
        TEMPLATE_FOR_INIT_EMPTY_AND_EXPRESSION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticEmptyAndExpression");
        TEMPLATE_FOR_INIT_EQUALS_EXPRESSION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticEqualsExpression");
        TEMPLATE_FOR_INIT_EXPRESSION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticExpression");
        TEMPLATE_FOR_INIT_EXTRACTORS_ARRAY = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticExtractorsArray");
        TEMPLATE_FOR_INIT_GROUPAGGREGATEINFOMAP = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newGroupAggregateInfoMap");
        TEMPLATE_FOR_INIT_GROUPAGGREGATEINFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticGroupAggregateInfo");
        TEMPLATE_FOR_INIT_GROUPITEMINFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "addGroupItemInfo");
        TEMPLATE_FOR_INIT_JOINEDTUPLEINFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTupleInfo");
        TEMPLATE_FOR_INIT_NOT_EXPRESSION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticNotExpression");
        TEMPLATE_FOR_INIT_OR_EXPRESSION = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticOrExpression");
        TEMPLATE_FOR_INIT_RETEENTITYINFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTupleInfo");
        TEMPLATE_FOR_INIT_RF_WRAPPER = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticRFWrapper");
        TEMPLATE_FOR_INIT_SLIDING_WINDOW_INFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticSlidingWindowInfo");
        TEMPLATE_FOR_INIT_SORT_INFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticSortInfo");
        TEMPLATE_FOR_INIT_SORT_INFO_WITH_LIMIT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticSortInfoWithLimit");
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newSortItemInfo");
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO_WITH_LIMIT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newSortItemInfoWithLimit");
        TEMPLATE_FOR_INIT_TIME_WINDOW_INFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTimeWindowInfo");
        TEMPLATE_FOR_INIT_TRANSFORM_INFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTransformationInfo");
        TEMPLATE_FOR_INIT_TRANSFORM_MAP = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTransformationMap");
        TEMPLATE_FOR_INIT_TUMBLING_WINDOW_INFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTumblingWindowInfo");
        TEMPLATE_FOR_INIT_TUPLE_VALUE_EXTR = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTupleValueExtractor");
        TEMPLATE_FOR_INIT_TUPLEINFO = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticTupleInfo");
        TEMPLATE_FOR_INIT_LITERAL_BOOLEAN = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralBoolean");
        TEMPLATE_FOR_INIT_LITERAL_BYTE = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralByte");
        TEMPLATE_FOR_INIT_LITERAL_CHARACTER = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralCharacter");
        TEMPLATE_FOR_INIT_LITERAL_DATETIME = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralDateTime");
        TEMPLATE_FOR_INIT_LITERAL_DOUBLE = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralDouble");
        TEMPLATE_FOR_INIT_LITERAL_FLOAT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralFloat");
        TEMPLATE_FOR_INIT_LITERAL_INTEGER = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralInteger");
        TEMPLATE_FOR_INIT_LITERAL_LONG = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralLong");
        TEMPLATE_FOR_INIT_LITERAL_SHORT = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralShort");
        TEMPLATE_FOR_INIT_LITERAL_STRING = TEMPLATE_REGISTRY.lookupTemplate(TEMPLATE_GROUP_NAME, "newStaticLiteralString");

        this.query = query;
        this.extractionHelper = new ExtractionCodeHelper(this);

        this.qepClassName = this.nameGenerator.makeQEPClassName(NAME_OF_PACKAGE_FOR_QEP);

        this.continuousBody = new StringBuilder();
        this.singleShotBody = new StringBuilder();
        this.initializerBody = new StringBuilder();

        this.mainHelper = new QueryExecutionClassHelper(query);
        this.mainHelper.createClass(this.qepClassName, AbstractQueryExecutionPlan.class.getCanonicalName());
    }


    public void addAggregateItemInfo(String mapName, String columnName, String aggregatorClassName)
            throws Exception {

        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO.reset();
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO.setAttribute("mapName", mapName);
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO.setAttribute("name", Escaper.toJavaStringSource(columnName));
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO.setAttribute("aggregatorClassName", aggregatorClassName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_AGGREGATEITEMINFO.toString()).append("\n\n");
    }


    public void addAggregateItemInfo(String mapName, String columnName, String aggregatorClassName, String tveName)
            throws Exception {

        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE.reset();
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE.setAttribute("mapName", mapName);
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE.setAttribute("name", Escaper.toJavaStringSource(columnName));
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE.setAttribute("aggregatorClassName", aggregatorClassName);
        TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE.setAttribute("tveName", tveName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_AGGREGATEITEMINFO_WITH_TVE.toString()).append("\n\n");
    }


    public String addAggregationCreation(StreamDescriptor descriptor, boolean emitFull,
                                         String groupAggregateInfoName, String windowBuilderName)
            throws Exception {

        final String localFieldName = this.nameGenerator.makeLocalAggregationName();

        TEMPLATE_FOR_CREATE_AGGREGATION.reset();
        TEMPLATE_FOR_CREATE_AGGREGATION.setAttribute("id", Escaper.toJavaStringSource(localFieldName));
        TEMPLATE_FOR_CREATE_AGGREGATION.setAttribute("valueName", localFieldName);
        TEMPLATE_FOR_CREATE_AGGREGATION.setAttribute("tupleInfoName", descriptor.getTupleInfoFieldName());
        TEMPLATE_FOR_CREATE_AGGREGATION.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_AGGREGATION.setAttribute("emitFull", emitFull);
        TEMPLATE_FOR_CREATE_AGGREGATION.setAttribute("groupAggregateInfoName", groupAggregateInfoName);
        TEMPLATE_FOR_CREATE_AGGREGATION.setAttribute("windowBuilderName", windowBuilderName);
        final String code = TEMPLATE_FOR_CREATE_AGGREGATION.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        descriptor.setVariableName(localFieldName);
        this.updateVariableMaps(descriptor.getContext(), localFieldName);
        this.updateTupleInfoMaps(descriptor.getContext(), descriptor.getTupleInfoFieldName());

        return localFieldName;
    }


    /**
     * Adds an initialized ComplexAndExpression to the generated class.
     *
     * @param ctx          ModelContext associated to the ComplexAndExpression.
     * @param operandNames String name of the operands to the AND.
     * @return String name of the generated variable.
     * @throws Exception upon problem.
     */
    public String addAndExpression(ModelContext ctx, String[] operandNames) throws Exception {
        final String expressionName = this.nameGenerator.makeExpressionName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, ComplexAndExpression.class, expressionName,
                null);

        if ((null == operandNames) || (operandNames.length < 1)) {
            TEMPLATE_FOR_INIT_EMPTY_AND_EXPRESSION.reset();
            TEMPLATE_FOR_INIT_EMPTY_AND_EXPRESSION.setAttribute("exprName", expressionName);
            this.initializerBody.append(TEMPLATE_FOR_INIT_EMPTY_AND_EXPRESSION.toString()).append("\n\n");
        } else {
            TEMPLATE_FOR_INIT_AND_EXPRESSION.reset();
            TEMPLATE_FOR_INIT_AND_EXPRESSION.setAttribute("exprName", expressionName);
            TEMPLATE_FOR_INIT_AND_EXPRESSION.setAttribute("operandNames", operandNames);
            this.initializerBody.append(TEMPLATE_FOR_INIT_AND_EXPRESSION.toString()).append("\n\n");
        }

        this.updateVariableMaps(ctx, expressionName);

        return expressionName;
    }


    public String addBridge(BridgeDescriptor descriptor) {
        final String fieldName = this.nameGenerator.makeLocalBridgeName();
        final StreamDescriptor setupStreamDescriptor = descriptor.getSetupDescriptor();

        TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE.reset();
        TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE.setAttribute("id", Escaper.toJavaStringSource(fieldName));
        TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE.setAttribute("setupStreamName", setupStreamDescriptor.getVariableName());
        this.singleShotBody.append(TEMPLATE_FOR_CREATE_BRIDGE_SIMPLE.toString()).append("\n\n");

        descriptor.setVariableName(fieldName);
        this.updateVariableMaps(descriptor.getContext(), fieldName);

        return fieldName;
    }


    public String addDeletedCreation(StreamDescriptor descriptor) throws Exception {
        final String fieldName = this.nameGenerator.makeLocalDeletedName();

        TEMPLATE_FOR_CREATE_DELETED.reset();
        TEMPLATE_FOR_CREATE_DELETED.setAttribute("id", Escaper.toJavaStringSource(fieldName));
        TEMPLATE_FOR_CREATE_DELETED.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_DELETED.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        final String code = TEMPLATE_FOR_CREATE_DELETED.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        descriptor.setVariableName(fieldName);
        this.updateVariableMaps(descriptor.getContext(), fieldName);

        return fieldName;
    }


    public String addDistinctCreation(StreamDescriptor descriptor) throws Exception {
        final String fieldName = this.nameGenerator.makeLocalDistinctName();
        final StreamDescriptor previousStreamDescriptor = descriptor.getInputStreamDescriptors()[0];
        final String id = Escaper.toJavaStringSource(fieldName);

        TEMPLATE_FOR_CREATE_DISTINCT.reset();
        TEMPLATE_FOR_CREATE_DISTINCT.setAttribute("id", id);
        TEMPLATE_FOR_CREATE_DISTINCT.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_DISTINCT.setAttribute("streamName", previousStreamDescriptor.getVariableName());
        this.continuousBody.append(TEMPLATE_FOR_CREATE_DISTINCT.toString()).append("\n\n");

        TEMPLATE_FOR_CREATE_DISTINCT.reset();
        TEMPLATE_FOR_CREATE_DISTINCT.setAttribute("id", id);
        TEMPLATE_FOR_CREATE_DISTINCT.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_DISTINCT.setAttribute("streamName", descriptor.getTupleInfoFieldName());
        this.singleShotBody.append(TEMPLATE_FOR_CREATE_DISTINCT.toString()).append("\n\n");

        descriptor.setVariableName(fieldName);
        this.updateVariableMaps(descriptor.getContext(), fieldName);

        return fieldName;
    }


    public String addEqualsExpression(ModelContext ctx, String leftName, String rightName) throws Exception {
        final String expressionName = this.nameGenerator.makeExpressionName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, EqualsExpression.class, expressionName,
                null);

        TEMPLATE_FOR_INIT_EQUALS_EXPRESSION.reset();
        TEMPLATE_FOR_INIT_EQUALS_EXPRESSION.setAttribute("exprName", expressionName);
        TEMPLATE_FOR_INIT_EQUALS_EXPRESSION.setAttribute("leftName", leftName);
        TEMPLATE_FOR_INIT_EQUALS_EXPRESSION.setAttribute("rightName", rightName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_EQUALS_EXPRESSION.toString()).append("\n\n");

        this.updateVariableMaps(ctx, expressionName);

        return expressionName;
    }


    public String addExpressionEvaluator(ModelContext ctx, BindVariable bv)
            throws Exception {
        final String localName = this.nameGenerator.makeEvaluatorName();

        this.mainHelper.addField((ModifierMask.STATIC | ModifierMask.FINAL), ExpressionEvaluator.class,
                localName, null);

        TEMPLATE_FOR_INIT_BIND_VARIABLE_EVALUATOR.reset();
        TEMPLATE_FOR_INIT_BIND_VARIABLE_EVALUATOR.setAttribute("fieldName", localName);
        TEMPLATE_FOR_INIT_BIND_VARIABLE_EVALUATOR.setAttribute("bindVarNameAsString",
                Escaper.toJavaStringSource(bv.getLabel()));
        this.initializerBody.append(TEMPLATE_FOR_INIT_BIND_VARIABLE_EVALUATOR.toString()).append("\n\n");

        this.updateVariableMaps(ctx, localName);

        return localName;
    }


    public String addExpressionEvaluator(ModelContext ctx, Integer i)
            throws Exception {
        final String localName = this.nameGenerator.makeEvaluatorName();

        this.mainHelper.addField((ModifierMask.STATIC | ModifierMask.FINAL), ExpressionEvaluator.class,
                localName, null);

        TEMPLATE_FOR_INIT_CONSTANT_EVALUATOR.reset();
        TEMPLATE_FOR_INIT_CONSTANT_EVALUATOR.setAttribute("fieldName", localName);
        TEMPLATE_FOR_INIT_CONSTANT_EVALUATOR.setAttribute("valueAsObject", "new Integer(" + i + ")");
        this.initializerBody.append(TEMPLATE_FOR_INIT_CONSTANT_EVALUATOR.toString()).append("\n\n");

        this.updateVariableMaps(ctx, localName);

        return localName;
    }


    /**
     * Adds an initialized Map, for use in an Expression constructor, to the generated class.
     *
     * @param streamNameToTupleClassName LinkedHashMap
     * @return String
     * @throws Exception upon problem
     */
    public String addExpressionAliasMap(LinkedHashMap<String, String> streamNameToTupleClassName) throws Exception {
        final String aliasMapFieldName = this.nameGenerator.makeAliasMapName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL, Map.class,
                aliasMapFieldName, null);

        TEMPLATE_FOR_INIT_ALIAS_MAP.reset();
        TEMPLATE_FOR_INIT_ALIAS_MAP.setAttribute("mapName", aliasMapFieldName);
        for (Map.Entry<String, String> entry : streamNameToTupleClassName.entrySet()) {
            TEMPLATE_FOR_INIT_ALIAS_MAP.setAttribute("aliasToTypeNames.{alias,typeName}",
                    Escaper.toJavaStringSource(entry.getKey()), entry.getValue());
        }
        this.initializerBody.append(TEMPLATE_FOR_INIT_ALIAS_MAP.toString()).append("\n");

        return aliasMapFieldName;
    }


    public String addFilterCreation(StreamDescriptor descriptor,
                                    String expressionName) throws Exception {

        final String localFieldName = this.nameGenerator.makeLocalFilterName();

        TEMPLATE_FOR_CREATE_FILTER.reset();
        TEMPLATE_FOR_CREATE_FILTER.setAttribute("id", Escaper.toJavaStringSource(localFieldName));
        TEMPLATE_FOR_CREATE_FILTER.setAttribute("valueName", localFieldName);
        TEMPLATE_FOR_CREATE_FILTER.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_FILTER.setAttribute("exprName", expressionName);
        final String code = TEMPLATE_FOR_CREATE_FILTER.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        descriptor.setVariableName(localFieldName);
        this.updateVariableMaps(descriptor.getContext(), localFieldName);
        this.updateTupleInfoMaps(descriptor.getContext(), descriptor.getTupleInfoFieldName());

        return localFieldName;
    }


    public String addGroupAggregateInfo(ModelContext ctx, String mapName) throws Exception {
        final String fieldName = this.nameGenerator.makeGroupAggregateInfoName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                GroupAggregateInfo.class, fieldName, null);

        TEMPLATE_FOR_INIT_GROUPAGGREGATEINFO.reset();
        TEMPLATE_FOR_INIT_GROUPAGGREGATEINFO.setAttribute("name", fieldName);
        TEMPLATE_FOR_INIT_GROUPAGGREGATEINFO.setAttribute("mapName", mapName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_GROUPAGGREGATEINFO.toString()).append("\n");

        return fieldName;
    }


    public String addGroupAggregateItemInfoMap(ModelContext ctx) throws Exception {
        final String fieldName = this.nameGenerator.makeLocalGroupAggregateItemInfoMapName();

        TEMPLATE_FOR_INIT_GROUPAGGREGATEINFOMAP.reset();
        TEMPLATE_FOR_INIT_GROUPAGGREGATEINFOMAP.setAttribute("name", fieldName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_GROUPAGGREGATEINFOMAP.toString()).append("\n");

        return fieldName;
    }


    public void addGroupItemInfo(String mapName, String columnName, String tveName) throws Exception {
        TEMPLATE_FOR_INIT_GROUPITEMINFO.reset();
        TEMPLATE_FOR_INIT_GROUPITEMINFO.setAttribute("mapName", mapName);
        TEMPLATE_FOR_INIT_GROUPITEMINFO.setAttribute("name", Escaper.toJavaStringSource(columnName));
        TEMPLATE_FOR_INIT_GROUPITEMINFO.setAttribute("tveName", tveName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_GROUPITEMINFO.toString()).append("\n\n");
    }


    public String addInsertCreation(StreamDescriptor descriptor) {

        final String varName = this.nameGenerator.makeLocalInsertName();

        TEMPLATE_FOR_CREATE_INSERT.reset();
        TEMPLATE_FOR_CREATE_INSERT.setAttribute("id", Escaper.toJavaStringSource(varName));
        TEMPLATE_FOR_CREATE_INSERT.setAttribute("valueName", varName);
        TEMPLATE_FOR_CREATE_INSERT.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        final String code = TEMPLATE_FOR_CREATE_INSERT.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        descriptor.setVariableName(varName);
        this.updateVariableMaps(descriptor.getContext(), varName);

        return varName;
    }


    /**
     * Adds the creation of a join.
     *
     * @param descriptor          StreamDescriptor describing the join
     * @param expressionFieldName String name of the join expression variable.
     * @return String name of the generated variable.
     * @throws Exception upon problem.
     */
    public String addJoinCreation(StreamDescriptor descriptor, String expressionFieldName) throws Exception {
        final String localJoinName = this.nameGenerator.makeLocalJoinName();
        if (null == expressionFieldName) {
            expressionFieldName = "";
        }
        final List<String> inputStreamNames = new ArrayList<String>();
        for (StreamDescriptor inputDescriptor : descriptor.getInputStreamDescriptors()) {
            inputStreamNames.add(inputDescriptor.getVariableName());
        }

        TEMPLATE_FOR_CREATE_JOIN.reset();
        TEMPLATE_FOR_CREATE_JOIN.setAttribute("joinId", Escaper.toJavaStringSource(localJoinName));
        TEMPLATE_FOR_CREATE_JOIN.setAttribute("valueName", localJoinName);
        TEMPLATE_FOR_CREATE_JOIN.setAttribute("tupleInfoName", descriptor.getTupleInfoFieldName());
        TEMPLATE_FOR_CREATE_JOIN.setAttribute("streamNames", inputStreamNames);
        TEMPLATE_FOR_CREATE_JOIN.setAttribute("cplxExprName", expressionFieldName);
        final String code = TEMPLATE_FOR_CREATE_JOIN.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        descriptor.setVariableName(localJoinName);
        this.updateVariableMaps(descriptor.getContext(), localJoinName);
        this.updateTupleInfoMaps(descriptor.getContext(), descriptor.getTupleInfoFieldName());

        return localJoinName;
    }


    /**
     * Adds an initialized JoinedTupleInfo field to the generated code.
     *
     * @param descriptor  TupleInfoDescriptorImpl describing the TupleInfo to create.
     *                    This will be updated with the tupleClassName generated in this method.
     * @param useNewClass boolean if true a new class will be used for the tuple (useful for joins).
     * @return String name of the TupleInfo field created.
     * @throws Exception upon problem.
     */
    public String addJoinedTupleInfo(TupleInfoDescriptor descriptor, boolean useNewClass)
            throws Exception {
        final String tupleInfoFieldName = this.nameGenerator.makeTupleInfoName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, JoinedTupleInfo.class,
                tupleInfoFieldName, null);

        final List<String> columnNames = new LinkedList<String>();
        for (String columnName : descriptor.getColumnNames()) {
            columnNames.add(Escaper.toJavaStringSource(columnName));
        }

        final int typeIndex = useNewClass ? (++joinedTupleInfoCounter) : 0;
        descriptor.setTypeIndex(typeIndex);
        descriptor.setTupleInfoClassName(HeavyJoinedTuples.HeavyJoinedTupleInfo.class.getCanonicalName());
        descriptor.setTupleClassName(
                HeavyJoinedTuples.HeavyJoinedTupleInfo.fetchContainerClass(typeIndex).getCanonicalName());

        TEMPLATE_FOR_INIT_JOINEDTUPLEINFO.reset();
        TEMPLATE_FOR_INIT_JOINEDTUPLEINFO.setAttribute("valueName", tupleInfoFieldName);
        TEMPLATE_FOR_INIT_JOINEDTUPLEINFO.setAttribute("className", descriptor.getTupleInfoClassName());
        TEMPLATE_FOR_INIT_JOINEDTUPLEINFO.setAttribute("typeIndex", descriptor.getTypeIndex());
        TEMPLATE_FOR_INIT_JOINEDTUPLEINFO.setAttribute("columnNames", columnNames);
        TEMPLATE_FOR_INIT_JOINEDTUPLEINFO.setAttribute("columnClasses", descriptor.getColumnClassNames());
        this.initializerBody.append(TEMPLATE_FOR_INIT_JOINEDTUPLEINFO.toString()).append("\n\n");

        this.updateVariableMaps(descriptor.getModelContext(), tupleInfoFieldName);
        this.updateTupleInfoMaps(descriptor.getModelContext(), tupleInfoFieldName, descriptor.getTupleClassName());

        this.tupleInfoFieldNameToTupleInfoDescriptor.put(tupleInfoFieldName, descriptor);

        return tupleInfoFieldName;
    }


    public String addLimitCreation(TruncatedStreamDescriptor descriptor)
            throws Exception {

        final String fieldName = this.nameGenerator.makeLocalLimitName();
        final StreamDescriptor previousStreamDescriptor = descriptor.getInputStreamDescriptors()[0];
        final String id = Escaper.toJavaStringSource(fieldName);

        final String firstFieldName = descriptor.getFirstFieldName();
        final String offsetFieldName = descriptor.getOffsetFieldName();

        TEMPLATE_FOR_CREATE_LIMIT.reset();
        TEMPLATE_FOR_CREATE_LIMIT.setAttribute("id", id);
        TEMPLATE_FOR_CREATE_LIMIT.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_LIMIT.setAttribute("streamName", previousStreamDescriptor.getVariableName());
        TEMPLATE_FOR_CREATE_LIMIT.setAttribute("limitFirst", firstFieldName);
        TEMPLATE_FOR_CREATE_LIMIT.setAttribute("limitOffset", offsetFieldName);
        this.continuousBody.append(TEMPLATE_FOR_CREATE_LIMIT.toString()).append("\n\n");

        if (!(previousStreamDescriptor instanceof DistinctFilteredStreamDescriptor)) {
            TEMPLATE_FOR_CREATE_LIMIT.reset();
            TEMPLATE_FOR_CREATE_LIMIT.setAttribute("id", id);
            TEMPLATE_FOR_CREATE_LIMIT.setAttribute("valueName", fieldName);
            TEMPLATE_FOR_CREATE_LIMIT.setAttribute("streamName", previousStreamDescriptor.getTupleInfoFieldName());
            TEMPLATE_FOR_CREATE_LIMIT.setAttribute("limitFirst", firstFieldName);
            TEMPLATE_FOR_CREATE_LIMIT.setAttribute("limitOffset", offsetFieldName);
        }
        this.singleShotBody.append(TEMPLATE_FOR_CREATE_LIMIT.toString()).append("\n\n");

        descriptor.setVariableName(fieldName);
        this.updateVariableMaps(descriptor.getContext(), fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Boolean value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, boolean.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_BOOLEAN.reset();
        TEMPLATE_FOR_INIT_LITERAL_BOOLEAN.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_BOOLEAN.setAttribute("value", value);
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_BOOLEAN.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Byte value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, byte.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_BYTE.reset();
        TEMPLATE_FOR_INIT_LITERAL_BYTE.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_BYTE.setAttribute("value", value);
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_BYTE.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Calendar value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, Calendar.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_DATETIME.reset();
        TEMPLATE_FOR_INIT_LITERAL_DATETIME.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_DATETIME.setAttribute("timeZoneIdAsString",
                Escaper.toJavaStringSource(value.getTimeZone().getID()));
        TEMPLATE_FOR_INIT_LITERAL_DATETIME.setAttribute("timeInMillis", value.getTimeInMillis());
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_DATETIME.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, char value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, char.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_CHARACTER.reset();
        TEMPLATE_FOR_INIT_LITERAL_CHARACTER.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_CHARACTER.setAttribute("valueAsChar", Escaper.toJavaCharSource(value));
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_CHARACTER.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Double value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, double.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_DOUBLE.reset();
        TEMPLATE_FOR_INIT_LITERAL_DOUBLE.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_DOUBLE.setAttribute("value", value);
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_DOUBLE.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Float value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, float.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_FLOAT.reset();
        TEMPLATE_FOR_INIT_LITERAL_FLOAT.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_FLOAT.setAttribute("value", value);
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_FLOAT.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Integer value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, int.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_INTEGER.reset();
        TEMPLATE_FOR_INIT_LITERAL_INTEGER.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_INTEGER.setAttribute("value", value);
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_INTEGER.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Long value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, long.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_LONG.reset();
        TEMPLATE_FOR_INIT_LITERAL_LONG.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_LONG.setAttribute("value", value);
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_LONG.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, Short value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, short.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_SHORT.reset();
        TEMPLATE_FOR_INIT_LITERAL_SHORT.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_SHORT.setAttribute("value", value);
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_SHORT.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addLiteral(ModelContext ctx, String value) throws Exception {
        String fieldName = this.nameGenerator.makeLiteralName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, String.class, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_LITERAL_STRING.reset();
        TEMPLATE_FOR_INIT_LITERAL_STRING.setAttribute("fieldName", fieldName);
        TEMPLATE_FOR_INIT_LITERAL_STRING.setAttribute("valueAsString", Escaper.toJavaStringSource(value));
        this.initializerBody.append(TEMPLATE_FOR_INIT_LITERAL_STRING.toString()).append("\n\n");

        this.updateVariableMaps(ctx, fieldName);

        return fieldName;
    }


    public String addNotExpression(ModelContext ctx, String operandName) throws Exception {
        final String localExpressionName = this.nameGenerator.makeExpressionName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, NotExpression.class, localExpressionName, null);

        TEMPLATE_FOR_INIT_NOT_EXPRESSION.reset();
        TEMPLATE_FOR_INIT_NOT_EXPRESSION.setAttribute("exprName", localExpressionName);
        TEMPLATE_FOR_INIT_NOT_EXPRESSION.setAttribute("operandName", operandName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_NOT_EXPRESSION.toString()).append("\n\n");

        this.updateVariableMaps(ctx, localExpressionName);

        return localExpressionName;
    }


    public String addOrExpression(ModelContext ctx, String[] operandNames) throws Exception {
        final String lcoalExpressionName = this.nameGenerator.makeExpressionName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, ComplexOrExpression.class, lcoalExpressionName,
                null);

        TEMPLATE_FOR_INIT_OR_EXPRESSION.reset();
        TEMPLATE_FOR_INIT_OR_EXPRESSION.setAttribute("exprName", lcoalExpressionName);
        TEMPLATE_FOR_INIT_OR_EXPRESSION.setAttribute("operandNames", operandNames);
        this.initializerBody.append(TEMPLATE_FOR_INIT_OR_EXPRESSION.toString()).append("\n\n");

        this.updateVariableMaps(ctx, lcoalExpressionName);

        return lcoalExpressionName;
    }


    public String addPartitionCreation(StreamDescriptor descriptor, String groupAggregateInfoName,
                                       String windowInfoName, String windowBuilderName) {
        final String localFieldName = this.nameGenerator.makeLocalPartitionName();

        if (null == groupAggregateInfoName) {
            groupAggregateInfoName = "null";
        }

        TEMPLATE_FOR_CREATE_PARTITION.reset();
        TEMPLATE_FOR_CREATE_PARTITION.setAttribute("id", Escaper.toJavaStringSource(localFieldName));
        TEMPLATE_FOR_CREATE_PARTITION.setAttribute("valueName", localFieldName);
        TEMPLATE_FOR_CREATE_PARTITION.setAttribute("tupleInfoName", descriptor.getTupleInfoFieldName());
        TEMPLATE_FOR_CREATE_PARTITION.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_PARTITION.setAttribute("groupAggregateInfoName", groupAggregateInfoName);
        TEMPLATE_FOR_CREATE_PARTITION.setAttribute("windowInfoName", windowInfoName);
        TEMPLATE_FOR_CREATE_PARTITION.setAttribute("windowBuilderName", windowBuilderName);
        final String code = TEMPLATE_FOR_CREATE_PARTITION.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        descriptor.setVariableName(localFieldName);
        this.updateVariableMaps(descriptor.getContext(), localFieldName);
        this.updateTupleInfoMaps(descriptor.getContext(), descriptor.getTupleInfoFieldName());

        return localFieldName;
    }


    public String addPassThroughBridge(BridgeDescriptor descriptor) {
        final String fieldName = this.nameGenerator.makeLocalBridgeName();
        final StreamDescriptor setupStreamDescriptor = descriptor.getSetupDescriptor();

        TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH.reset();
        TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH.setAttribute("id", Escaper.toJavaStringSource(fieldName));
        TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH.setAttribute("setupStreamName", setupStreamDescriptor.getVariableName());
        this.singleShotBody.append(TEMPLATE_FOR_CREATE_BRIDGE_PASSTHROUGH.toString()).append("\n\n");
                                                       
        descriptor.setVariableName(fieldName);
        this.updateVariableMaps(descriptor.getContext(), fieldName);

        return fieldName;
    }

    public String addProxyCreation(ModelContext ctx, String id, String streamVarName) {
        final String varName = this.nameGenerator.makeLocalSourceName();

        TEMPLATE_FOR_CREATE_PROXY.reset();
        TEMPLATE_FOR_CREATE_PROXY.setAttribute("id", Escaper.toJavaStringSource(id));
        TEMPLATE_FOR_CREATE_PROXY.setAttribute("valueName", varName);
        TEMPLATE_FOR_CREATE_PROXY.setAttribute("streamName", streamVarName);
        final String code = TEMPLATE_FOR_CREATE_PROXY.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        final String ghostTupleInfoFieldName = this.nameGenerator.makeTupleInfoName();
        final String ghostTupleClassName = this.getTupleClassName(streamVarName);
        this.updateVariableMaps(ctx, ghostTupleInfoFieldName);

        this.updateVariableMaps(ctx, varName);
        this.updateTupleInfoMaps(ctx, ghostTupleInfoFieldName, ghostTupleClassName);
        this.tupleInfoFieldNameToTupleInfoDescriptor.put(ghostTupleInfoFieldName,
                this.getTupleInfoDescriptor(streamVarName));

        return varName;
    }


    /**
     * Adds an initialized ReteEntityInfo field (and dependencies) to the generated code.
     *
     * @param descriptor  TupleInfoDescriptor describing the TupleInfo to create.
     *                    This will be updated with the tupleClassName generated in this method.
     * @param useNewClass boolean if true a new class will be used for the tuple (useful for joins).
     * @return String name of the ReteEntityInfo field created.
     * @throws Exception upon problem.
     */
    public String addReteEntityInfo(TupleInfoDescriptor descriptor, boolean useNewClass)
            throws Exception {
        final String fieldName = this.nameGenerator.makeTupleInfoName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, ReteEntityInfo.class, fieldName, null);

        final List<String> columnNames = new LinkedList<String>();
        for (String columnName : descriptor.getColumnNames()) {
            columnNames.add(Escaper.toJavaStringSource(columnName));
        }

        final int typeIndex = useNewClass ? (++reteEntityInfoCounter) : 0;
        descriptor.setTypeIndex(typeIndex);
        descriptor.setTupleInfoClassName(HeavyReteEntities.HeavyReteEntityInfo.class.getCanonicalName());
        descriptor.setTupleClassName(
                HeavyReteEntities.HeavyReteEntityInfo.fetchContainerClass(typeIndex).getCanonicalName());

        TEMPLATE_FOR_INIT_RETEENTITYINFO.reset();
        TEMPLATE_FOR_INIT_RETEENTITYINFO.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_INIT_RETEENTITYINFO.setAttribute("className", descriptor.getTupleInfoClassName());
        TEMPLATE_FOR_INIT_RETEENTITYINFO.setAttribute("typeIndex", typeIndex);
        TEMPLATE_FOR_INIT_RETEENTITYINFO.setAttribute("columnNames", columnNames);
        TEMPLATE_FOR_INIT_RETEENTITYINFO.setAttribute("columnClasses", descriptor.getColumnClassNames());
        this.initializerBody.append(TEMPLATE_FOR_INIT_RETEENTITYINFO.toString()).append("\n\n");

        this.updateVariableMaps(descriptor.getModelContext(), fieldName);
        this.updateTupleInfoMaps(descriptor.getModelContext(), fieldName, descriptor.getTupleClassName());

        this.tupleInfoFieldNameToTupleInfoDescriptor.put(fieldName, descriptor);

        return fieldName;
    }


    public String addRuleFunctionWrapper(Class ruleFunctionClass) throws Exception {

        String fieldName = this.nameGenerator.makeRuleFunctionInstanceName();
        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, ruleFunctionClass, fieldName, null);

        fieldName = this.mainHelper.getName() + STATIC_FIELD_SEPARATOR + fieldName;

        TEMPLATE_FOR_INIT_RF_WRAPPER.reset();
        TEMPLATE_FOR_INIT_RF_WRAPPER.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_INIT_RF_WRAPPER.setAttribute("className", ruleFunctionClass.getCanonicalName());
        this.initializerBody.append(TEMPLATE_FOR_INIT_RF_WRAPPER.toString()).append("\n");

        return fieldName;
    }


    public String addSimpleExpression(ModelContext ctx, String expressionClassName)
            throws Exception {
        final String localExpressionName = this.nameGenerator.makeExpressionName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, EvaluatableExpression.class,
                localExpressionName, null);

        TEMPLATE_FOR_INIT_EXPRESSION.reset();
        TEMPLATE_FOR_INIT_EXPRESSION.setAttribute("exprName", localExpressionName);
        TEMPLATE_FOR_INIT_EXPRESSION.setAttribute("exprClassName", expressionClassName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_EXPRESSION.toString()).append("\n\n");

        this.updateVariableMaps(ctx, localExpressionName); //SimpleExpression.class.getName());

        return localExpressionName;
    }


    public String addSimpleExpressionClass(String aliasMapName, String[] evaluatorBodies, String expressionQuerySrc)
            throws Exception {
        final String expressionClassName = this.nameGenerator.makeExpressionClassName();

        final QueryExecutionClassHelper expressionClassHelper = this.mainHelper.createdNestedClass(
                expressionClassName, AbstractExpression.class.getName());

        expressionClassHelper.implementInterface(EvaluatableExpression.class.getName());

        expressionClassHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL, String.class,
                "SOURCE", Escaper.toJavaStringSource(expressionQuerySrc));

        expressionClassHelper.addConstructor(ModifierMask.PUBLIC, ARG_CLASSES_FOR_EXPRESSION_CSTR,
                "super(" + this.qepClassName + STATIC_FIELD_SEPARATOR + aliasMapName + ");");

        for (int i=0; i<NAME_OF_METHOD_EXPRESSION_EVALUATE.length; i++) {
            expressionClassHelper.addMethod(ModifierMask.PUBLIC,
                    RETURN_TYPE_OF_EXPRESSION_EVALUATE[i],
                    NAME_OF_METHOD_EXPRESSION_EVALUATE[i],
                    ARG_CLASSES_FOR_EXPRESSION_EVALUATE,
                    EXCEPTION_CLASSES_FOR_EXPRESSION_EVALUATE,
                    evaluatorBodies[i]);
        }

        return expressionClassHelper.getName();
    }


    /**
     * @param descriptor SinkDescriptor describing the Sink.
     * @return String name of the local variable that holds the Sink.
     */
    public String addSinkCreation(SinkDescriptor descriptor) {
        this.sinkName = this.nameGenerator.makeLocalSinkName();

        final StreamDescriptor previousStreamDescriptor = descriptor.getInputStreamDescriptors()[0];
        final String id = Escaper.toJavaStringSource(this.sinkName);
        final String streamName = previousStreamDescriptor.getVariableName();
        final String tupleInfoName = descriptor.getTupleInfoFieldName();

        TEMPLATE_FOR_CREATE_SINK.reset();
        TEMPLATE_FOR_CREATE_SINK.setAttribute("id", id);
        TEMPLATE_FOR_CREATE_SINK.setAttribute("sinkName", this.sinkName);
        TEMPLATE_FOR_CREATE_SINK.setAttribute("streamName", streamName);
        TEMPLATE_FOR_CREATE_SINK.setAttribute("tupleInfoName", tupleInfoName);
        this.continuousBody.append(TEMPLATE_FOR_CREATE_SINK.toString()).append("\n\n");

        final StringTemplate template = descriptor.isStreamed() ? TEMPLATE_FOR_CREATE_SINK : TEMPLATE_FOR_CREATE_STATIC_SINK;
        template.reset();
        template.setAttribute("id", id);
        template.setAttribute("sinkName", this.sinkName);
        if ((previousStreamDescriptor instanceof DistinctFilteredStreamDescriptor)
                || (previousStreamDescriptor instanceof TruncatedStreamDescriptor)) {
            template.setAttribute("streamName", streamName);
        } else {
            template.setAttribute("streamName", "null");
        }
        template.setAttribute("tupleInfoName", tupleInfoName);
        this.singleShotBody.append(template.toString()).append("\n\n");

        descriptor.setVariableName(this.sinkName);
        this.updateVariableMaps(descriptor.getContext(), this.sinkName);

        return this.sinkName;
    }


    public String addSlidingWindowBuilderCreation(ModelContext ctx,
                                                  String inputTupleInfoFieldName,
                                                  String outputTupleInfoFieldName,
                                                  String insertDeleteBothName,
                                                  boolean includesAggregates)
            throws Exception {
        final String varName = this.nameGenerator.makeSlidingWindowBuilderName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                WindowBuilder.class, varName, null);
        TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER.reset();
        TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER.setAttribute("varName", varName);
        TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER.setAttribute("inputTupleInfoName", inputTupleInfoFieldName);
        TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER.setAttribute("outputTupleInfoName", outputTupleInfoFieldName);
        TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER.setAttribute("insertDeleteBoth", insertDeleteBothName);
        TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER.setAttribute("includesAggregates", includesAggregates);
        final String code = TEMPLATE_FOR_CREATE_SLIDING_WINDOW_BUILDER.toString();
        this.continuousBody.append(code).append("\n");
        this.singleShotBody.append(code).append("\n");

        return varName;
    }


    public String addSlidingWindowInfo(ModelContext ctx, int size) throws Exception {
        final String varName = this.nameGenerator.makeSlidingWindowInfoName();


        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                SlidingWindowInfo.class, varName, null);
        TEMPLATE_FOR_INIT_SLIDING_WINDOW_INFO.reset();
        TEMPLATE_FOR_INIT_SLIDING_WINDOW_INFO.setAttribute("varName", varName);
        TEMPLATE_FOR_INIT_SLIDING_WINDOW_INFO.setAttribute("size", size);
        this.initializerBody.append(TEMPLATE_FOR_INIT_SLIDING_WINDOW_INFO.toString()).append("\n");

        return varName;
    }


    public String addSortedBridge(SortedBridgeDescriptor descriptor) {
        final String fieldName = this.nameGenerator.makeLocalBridgeName();
        final SortedStreamDescriptor sortedStreamDescriptor = descriptor.getSortedStreamDescriptor();

        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.reset();
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.setAttribute("id", Escaper.toJavaStringSource(fieldName));
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.setAttribute("setupStreamName", descriptor.getSetupDescriptor().getVariableName());
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.setAttribute("sortInfoName", sortedStreamDescriptor.getSortInfoName());
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.setAttribute("extractorsName", sortedStreamDescriptor.getExtractorsName());
        TEMPLATE_FOR_CREATE_BRIDGE_SORTED.setAttribute("comparatorsName", sortedStreamDescriptor.getComparatorsName());
        this.singleShotBody.append(TEMPLATE_FOR_CREATE_BRIDGE_SORTED.toString()).append("\n\n");

        descriptor.setVariableName(fieldName);
        this.updateVariableMaps(descriptor.getContext(), fieldName);

        return fieldName;
    }


    public String addSortInfo(ModelContext ctx, List<String> sortItemInfos) throws Exception {
        final String sortInfoName = this.nameGenerator.makeSortInfoName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL, SortInfo.class,
                sortInfoName, null);

        TEMPLATE_FOR_INIT_SORT_INFO.reset();
        TEMPLATE_FOR_INIT_SORT_INFO.setAttribute("sortInfoName", sortInfoName);
        TEMPLATE_FOR_INIT_SORT_INFO.setAttribute("sortItemInfoNames", sortItemInfos);
        this.initializerBody.append(TEMPLATE_FOR_INIT_SORT_INFO.toString()).append("\n");

        this.updateVariableMaps(ctx, sortInfoName);

        return sortInfoName;
    }


    public String addSortInfoExtractors(List<String> names) throws Exception {
        final String arrayName = this.nameGenerator.makeExtractorsName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                TupleValueExtractor[].class, arrayName, null);

        TEMPLATE_FOR_INIT_EXTRACTORS_ARRAY.reset();
        TEMPLATE_FOR_INIT_EXTRACTORS_ARRAY.setAttribute("arrayName", arrayName);
        TEMPLATE_FOR_INIT_EXTRACTORS_ARRAY.setAttribute("extractorNames", names);
        this.initializerBody.append(TEMPLATE_FOR_INIT_EXTRACTORS_ARRAY.toString()).append("\n");

        return arrayName;
    }


    public String addSortInfoComparators(List<String> comparatorNames) throws Exception {
        final String listName = this.nameGenerator.makeComparatorsName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL, List.class,
                listName, null);

        TEMPLATE_FOR_INIT_COMPARATORS_LIST.reset();
        TEMPLATE_FOR_INIT_COMPARATORS_LIST.setAttribute("listName", listName);
        TEMPLATE_FOR_INIT_COMPARATORS_LIST.setAttribute("listLength", comparatorNames.size());
        TEMPLATE_FOR_INIT_COMPARATORS_LIST.setAttribute("comparatorNames", comparatorNames);
        this.initializerBody.append(TEMPLATE_FOR_INIT_COMPARATORS_LIST.toString()).append("\n");

        return listName;
    }


    public String addSortItemInfo(ModelContext ctx, boolean ascending)
            throws Exception {
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO.reset();
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO.setAttribute("direction", ascending);
        return TEMPLATE_FOR_INIT_SORT_ITEM_INFO.toString();
    }


    public String addSortItemInfo(ModelContext ctx, boolean ascending, String  limitFirst, String limitOffset)
            throws Exception {
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO_WITH_LIMIT.reset();
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO_WITH_LIMIT.setAttribute("direction", ascending);
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO_WITH_LIMIT.setAttribute("limitFirst", limitFirst);
        TEMPLATE_FOR_INIT_SORT_ITEM_INFO_WITH_LIMIT.setAttribute("limitOffset", limitOffset);
        return TEMPLATE_FOR_INIT_SORT_ITEM_INFO_WITH_LIMIT.toString();
    }


    /**
     * Adds a sort creation to the generated code.
     *
     * @param descriptor SortedStreamDescriptor describing the sort.
     * @return String name of the variable generated.
     * @throws Exception upon problem.
     */
    public String addSortCreation(SortedStreamDescriptor descriptor) throws Exception {

        final String localSortName = this.nameGenerator.makeLocalSortName();
        final String id = Escaper.toJavaStringSource(localSortName);
        final String previousStreamName = descriptor.getInputStreamDescriptors()[0].getVariableName();

        TEMPLATE_FOR_CREATE_SORT.reset();
        TEMPLATE_FOR_CREATE_SORT.setAttribute("id", id);
        TEMPLATE_FOR_CREATE_SORT.setAttribute("valueName", localSortName);
        TEMPLATE_FOR_CREATE_SORT.setAttribute("streamName", previousStreamName);
        TEMPLATE_FOR_CREATE_SORT.setAttribute("sortInfoName", descriptor.getSortInfoName());
        TEMPLATE_FOR_CREATE_SORT.setAttribute("extractorsName", descriptor.getExtractorsName());
        TEMPLATE_FOR_CREATE_SORT.setAttribute("comparatorsName", descriptor.getComparatorsName());
        this.continuousBody.append(TEMPLATE_FOR_CREATE_SORT.toString()).append("\n\n");

        descriptor.setVariableName(localSortName);
        this.updateVariableMaps(descriptor.getContext(), localSortName);

        return localSortName;
    }


    /**
     * Adds a Source creation in the generated code.
     *
     * @param descriptor SourceDescriptor describing the source.
     * @return String name of the local variable that holds the Source created.
     * @throws Exception upon problem.
     */
    public String addSourceCreation(SourceDescriptor descriptor)
            throws Exception {
        final String localSourceName = this.nameGenerator.makeLocalSourceName();
        final String alias = Escaper.toJavaStringSource(descriptor.getAlias());
        final String id = Escaper.toJavaStringSource(descriptor.getName());
        final String tupleInfoFieldName = descriptor.getTupleInfoFieldName();

        final StringTemplate template = (AcceptType.ALL == descriptor.getAcceptType())
                ? TEMPLATE_FOR_CREATE_WITHDRAWABLE_SOURCE
                : TEMPLATE_FOR_CREATE_SOURCE;
        template.reset();
        template.setAttribute("alias", alias);
        template.setAttribute("sourceId", id);
        template.setAttribute("valueName", localSourceName);
        template.setAttribute("tupleInfoName", tupleInfoFieldName);
        this.continuousBody.append(template.toString()).append("\n\n");

        TEMPLATE_FOR_CREATE_SOURCE.reset();
        TEMPLATE_FOR_CREATE_SOURCE.setAttribute("alias", alias);
        TEMPLATE_FOR_CREATE_SOURCE.setAttribute("sourceId", id);
        TEMPLATE_FOR_CREATE_SOURCE.setAttribute("valueName", localSourceName);
        TEMPLATE_FOR_CREATE_SOURCE.setAttribute("tupleInfoName", tupleInfoFieldName);
        this.singleShotBody.append(TEMPLATE_FOR_CREATE_SOURCE.toString()).append("\n\n");

        this.updateVariableMaps(descriptor.getContext(), localSourceName);
        this.updateTupleInfoMaps(descriptor.getContext(), tupleInfoFieldName);

        return localSourceName;
    }


    /**
     * Adds to the generated code the TransformationInfo that represents the projection.
     *
     * @param transformationMapName String name of the Map variable used by the TransformationInfo.
     * @return String name of the generated variable.
     * @throws Exception upon problem.
     */
    public String addTransformInfo(String transformationMapName) throws Exception {
        final String transfInfoName = this.nameGenerator.makeTransformationInfoName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, TransformInfo.class, transfInfoName, null);

        TEMPLATE_FOR_INIT_TRANSFORM_INFO.reset();
        TEMPLATE_FOR_INIT_TRANSFORM_INFO.setAttribute("name", transfInfoName);
        TEMPLATE_FOR_INIT_TRANSFORM_INFO.setAttribute("transformationMapName", transformationMapName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_TRANSFORM_INFO.toString()).append("\n\n");

        return transfInfoName;
    }


    public String addTimeWindowBuilderCreation(ModelContext ctx,
                                               String inputTupleInfoFieldName,
                                               String outputTupleInfoFieldName,
                                               String insertDeleteBothName,
                                               boolean includesAggregates)
            throws Exception {
        final String varName = this.nameGenerator.makeTimeWindowBuilderName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                WindowBuilder.class, varName, null);
        TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER.reset();
        TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER.setAttribute("varName", varName);
        TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER.setAttribute("inputTupleInfoName", inputTupleInfoFieldName);
        TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER.setAttribute("outputTupleInfoName", outputTupleInfoFieldName);
        TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER.setAttribute("insertDeleteBoth", insertDeleteBothName);
        TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER.setAttribute("includesAggregates", includesAggregates);
        final String code = TEMPLATE_FOR_CREATE_TIME_WINDOW_BUILDER.toString();
        this.continuousBody.append(code).append("\n");
        this.singleShotBody.append(code).append("\n");

        return varName;
    }


    public String addTimeWindowInfo(ModelContext ctx, int size, long time) throws Exception {
        final String varName = this.nameGenerator.makeTimeWindowInfoName();


        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                TimeWindowInfo.class, varName, null);
        TEMPLATE_FOR_INIT_TIME_WINDOW_INFO.reset();
        TEMPLATE_FOR_INIT_TIME_WINDOW_INFO.setAttribute("varName", varName);
        TEMPLATE_FOR_INIT_TIME_WINDOW_INFO.setAttribute("size", size);
        TEMPLATE_FOR_INIT_TIME_WINDOW_INFO.setAttribute("time", "(long) " + time);
        this.initializerBody.append(TEMPLATE_FOR_INIT_TIME_WINDOW_INFO.toString()).append("\n");

        return varName;
    }


    public String addTumblingWindowBuilderCreation(ModelContext ctx,
                                                   String inputTupleInfoFieldName,
                                                   String outputTupleInfoFieldName,
                                                   String insertDeleteBothName,
                                                   boolean includesAggregates)
            throws Exception {
        final String varName = this.nameGenerator.makeTumblingWindowBuilderName();

        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                WindowBuilder.class, varName, null);
        TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER.reset();
        TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER.setAttribute("varName", varName);
        TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER.setAttribute("inputTupleInfoName", inputTupleInfoFieldName);
        TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER.setAttribute("outputTupleInfoName", outputTupleInfoFieldName);
        TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER.setAttribute("insertDeleteBoth", insertDeleteBothName);
        TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER.setAttribute("includesAggregates", includesAggregates);
        final String code = TEMPLATE_FOR_CREATE_TUMBLING_WINDOW_BUILDER.toString();
        this.continuousBody.append(code).append("\n");
        this.singleShotBody.append(code).append("\n");

        return varName;
    }


    public String addTumblingWindowInfo(ModelContext ctx, int size) throws Exception {
        final String varName = this.nameGenerator.makeTumblingWindowInfoName();


        this.mainHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL,
                TumblingWindowInfo.class, varName, null);
        TEMPLATE_FOR_INIT_TUMBLING_WINDOW_INFO.reset();
        TEMPLATE_FOR_INIT_TUMBLING_WINDOW_INFO.setAttribute("varName", varName);
        TEMPLATE_FOR_INIT_TUMBLING_WINDOW_INFO.setAttribute("size", size);
        this.initializerBody.append(TEMPLATE_FOR_INIT_TUMBLING_WINDOW_INFO.toString()).append("\n");

        return varName;
    }


    /**
     * Adds a transform creation to the generated code.
     *
     * @param descriptor TransformedStreamDescriptor describing the transformation.
     * @return String name of the variable generated.
     * @throws Exception upon problem.
     */
    public String addTransformCreation(TransformedStreamDescriptor descriptor)
            throws Exception {
        final String localTransformName = this.nameGenerator.makeLocalTransformName();

        TEMPLATE_FOR_CREATE_TRANSFORMATION.reset();
        TEMPLATE_FOR_CREATE_TRANSFORMATION.setAttribute("id", Escaper.toJavaStringSource(localTransformName));
        TEMPLATE_FOR_CREATE_TRANSFORMATION.setAttribute("valueName", localTransformName);
        TEMPLATE_FOR_CREATE_TRANSFORMATION.setAttribute("tupleInfoName", descriptor.getTupleInfoFieldName());
        TEMPLATE_FOR_CREATE_TRANSFORMATION.setAttribute("streamName",
                descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_TRANSFORMATION.setAttribute("transfInfoName", descriptor.getTransformInfoName());
        final String code = TEMPLATE_FOR_CREATE_TRANSFORMATION.toString();
        this.continuousBody.append(code).append("\n\n");
        this.singleShotBody.append(code).append("\n\n");

        descriptor.setVariableName(localTransformName);
        this.updateVariableMaps(descriptor.getContext(), localTransformName);
        this.updateTupleInfoMaps(descriptor.getContext(), descriptor.getTupleInfoFieldName());

        return localTransformName;
    }


    /**
     * Adds to the generated code the TransformationMap that will be used in the TransformationInfo.
     *
     * @param aliasToValueExtractorName LinkedHashMap of String projection alias to String name of TupleValueExtractor.
     * @return String name of the tranformation map created.
     * @throws Exception upon problem
     */
    public String addTransformMap(LinkedHashMap<String, String> aliasToValueExtractorName) throws Exception {
        final String transfMapName = this.nameGenerator.makeTransformationMapName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, LinkedHashMap.class, transfMapName, null);

        TEMPLATE_FOR_INIT_TRANSFORM_MAP.reset();
        TEMPLATE_FOR_INIT_TRANSFORM_MAP.setAttribute("name", transfMapName);
        for (Map.Entry<String, String> entry : aliasToValueExtractorName.entrySet()) {
            TEMPLATE_FOR_INIT_TRANSFORM_MAP.setAttribute("aliasToTupleValueExtractors.{alias,tve}",
                    Escaper.toJavaStringSource(entry.getKey()), entry.getValue());
        }
        this.initializerBody.append(TEMPLATE_FOR_INIT_TRANSFORM_MAP.toString()).append("\n\n");

        return transfMapName;
    }


    public String addTruncatedBridge(BridgeDescriptor descriptor) {
        final String fieldName = this.nameGenerator.makeLocalBridgeName();
        final StreamDescriptor setupStreamDescriptor = descriptor.getSetupDescriptor();

        TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED.reset();
        TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED.setAttribute("id", Escaper.toJavaStringSource(fieldName));
        TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED.setAttribute("valueName", fieldName);
        TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED.setAttribute("streamName", descriptor.getInputStreamDescriptors()[0].getVariableName());
        TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED.setAttribute("setupStreamName", setupStreamDescriptor.getVariableName());
        this.singleShotBody.append(TEMPLATE_FOR_CREATE_BRIDGE_TRUNCATED.toString()).append("\n\n");

        descriptor.setVariableName(fieldName);
        this.updateVariableMaps(descriptor.getContext(), fieldName);

        return fieldName;
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
    public String addTupleInfo(TupleInfoDescriptor descriptor, boolean useNewClass)
            throws Exception {
        final String tupleInfoFieldName = this.nameGenerator.makeTupleInfoName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, TupleInfo.class,
                tupleInfoFieldName, null);

        final List<String> columnNames = new LinkedList<String>();
        for (String columnName : descriptor.getColumnNames()) {
            columnNames.add(Escaper.toJavaStringSource(columnName));
        }

        final int typeIndex = useNewClass ? (++tupleInfoCounter) : 0;
        descriptor.setTypeIndex(typeIndex);
        descriptor.setTupleInfoClassName(HeavyTuples.HeavyTupleInfo.class.getCanonicalName());
        descriptor.setTupleClassName(HeavyTuples.HeavyTupleInfo.fetchContainerClass(typeIndex).getCanonicalName());

        TEMPLATE_FOR_INIT_TUPLEINFO.reset();
        TEMPLATE_FOR_INIT_TUPLEINFO.setAttribute("valueName", tupleInfoFieldName);
        TEMPLATE_FOR_INIT_TUPLEINFO.setAttribute("className", descriptor.getTupleInfoClassName());
        TEMPLATE_FOR_INIT_TUPLEINFO.setAttribute("typeIndex", typeIndex);
        TEMPLATE_FOR_INIT_TUPLEINFO.setAttribute("columnNames", columnNames);
        TEMPLATE_FOR_INIT_TUPLEINFO.setAttribute("columnClasses", descriptor.getColumnClassNames());
        this.initializerBody.append(TEMPLATE_FOR_INIT_TUPLEINFO.toString()).append("\n");

        this.updateVariableMaps(descriptor.getModelContext(), tupleInfoFieldName);
        this.updateTupleInfoMaps(descriptor.getModelContext(), tupleInfoFieldName, descriptor.getTupleClassName());

        this.tupleInfoFieldNameToTupleInfoDescriptor.put(tupleInfoFieldName, descriptor);

        return tupleInfoFieldName;
    }


    public String addTupleValueExtractor(ModelContext ctx, String tveClassName) throws Exception {
        final String tveName = this.nameGenerator.makeTVEName();

        this.mainHelper.addField(ModifierMask.STATIC | ModifierMask.FINAL, TupleValueExtractor.class, tveName, null);

        TEMPLATE_FOR_INIT_TUPLE_VALUE_EXTR.reset();
        TEMPLATE_FOR_INIT_TUPLE_VALUE_EXTR.setAttribute("name", tveName);
        TEMPLATE_FOR_INIT_TUPLE_VALUE_EXTR.setAttribute("className", tveClassName);
        this.initializerBody.append(TEMPLATE_FOR_INIT_TUPLE_VALUE_EXTR.toString()).append("\n");

        this.updateVariableMaps(ctx, tveName);

        return tveName;
    }


    /**
     * @param methodSources String
     * @param tveQuerySrc   String
     * @return String
     * @throws Exception upon problem
     */
    public String addTupleValueExtractorClass(String[] methodSources, String tveQuerySrc) throws Exception {
        final String tveClassName = this.nameGenerator.makeTVEClassName();

        final QueryExecutionClassHelper tveClassHelper = this.mainHelper.createdNestedClass(
                tveClassName, null);
        tveClassHelper.implementInterface(TupleValueExtractor.class.getName());
        tveClassHelper.addConstructor(ModifierMask.PUBLIC, ARG_CLASSES_FOR_TVE_CSTR, null);

        tveClassHelper.addField(ModifierMask.PUBLIC | ModifierMask.STATIC | ModifierMask.FINAL, String.class,
                "SOURCE", Escaper.toJavaStringSource(tveQuerySrc));

        for (int i=0; i<NAME_OF_METHOD_TVE_EXTRACT.length; i++) {
            tveClassHelper.addMethod(ModifierMask.PUBLIC, RETURN_TYPE_OF_TVE_EXTRACT[i],
                    NAME_OF_METHOD_TVE_EXTRACT[i],
                    ARG_CLASSES_FOR_TVE_EXTRACT,
                    EXCEPTION_CLASSES_FOR_TVE_EXTRACT,
                    methodSources[i]);
        }

        return tveClassHelper.getName();
    }


    public String createSimpleExpression(Expression expr, AliasMapDescriptor inputAliasMapDescriptor)
            throws Exception {

        return this.extractionHelper.addSimpleExpression(expr, inputAliasMapDescriptor);
    }


    public String createTupleValueExtractor(Expression expr, TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        return this.extractionHelper.addTupleValueExtractor(expr, inputTupleInfoDescriptor);
    }


    public String createTupleValueExtractor(ProxyContext proxy, TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        return this.extractionHelper.addTupleValueExtractor(proxy, inputTupleInfoDescriptor);
    }


    public com.tibco.be.util.idgenerators.IdentifierGenerator getIdGenerator() {
        return this.idGenerator;
    }


    public Class getQepClass() throws Exception {
        if (null == this.qepClass) {
            this.qepClass = this.makeQueryExecutionPlanClass();
        }
        return this.qepClass;
    }


    public Query getQuery() {
        return this.query;
    }


    public String getTupleInfoFieldName(String name) {
        final ModelContext ctx = this.variableNameToCtx.get(name);
        if (null == ctx) {
            return null;
        }
        return this.ctxToTupleInfoFieldName.get(ctx);
    }


    private Class makeQueryExecutionPlanClass() throws Exception {
        this.mainHelper.setStaticInitializer("{" + this.initializerBody + "}");

        this.mainHelper.addConstructor(ModifierMask.PUBLIC, ARG_CLASSES_FOR_QEP_CSTR,
                "{\n    super($1, $2, " + Escaper.toJavaStringSource(this.query.getSourceText()) + ");\n}"
        );

        this.mainHelper.addMethod(ModifierMask.PUBLIC, RETURN_TYPE_OF_INITIALIZE_AS_CONTINUOUS,
                NAME_OF_METHOD_INITIALIZE_AS_CONTINUOUS,
                ARG_CLASSES_FOR_INITIALIZE_AS_CONTINUOUS,
                EXCEPTION_CLASSES_FOR_INITIALIZE_AS_CONTINUOUS,
                "{\n" + this.continuousBody + "\n    this.sink = " + this.sinkName + ";\n}");

        this.mainHelper.addMethod(ModifierMask.PUBLIC, RETURN_TYPE_OF_INITIALIZE_AS_SINGLE_SHOT,
                NAME_OF_METHOD_INITIALIZE_AS_SNAPSHOT,
                ARG_CLASSES_FOR_INITIALIZE_AS_SINGLE_SHOT,
                EXCEPTION_CLASSES_FOR_INITIALIZE_AS_SINGLE_SHOT,
                "{\n" + this.singleShotBody + "\n    this.sink = " + this.sinkName + ";\n}");

        final ExecutionClassInfo info = this.mainHelper.getExecutionClassInfo();
        return info.getClazz();
    }


    private void updateTupleInfoMaps(ModelContext ctx, String tupleInfoFieldName) {
        this.ctxToTupleInfoFieldName.put(ctx, tupleInfoFieldName);
    }


    private void updateTupleInfoMaps(ModelContext ctx, String tupleInfoFieldName, String tupleClassName) {
        this.updateTupleInfoMaps(ctx, tupleInfoFieldName);
        this.tupleInfoFieldNameToTupleClassName.put(tupleInfoFieldName, tupleClassName);
    }


    private void updateVariableMaps(ModelContext ctx, String variableName) {
        this.variableNameToCtx.put(variableName, ctx);
    }


    public String getTupleClassName(String name) {
        return this.tupleInfoFieldNameToTupleClassName.get(this.getTupleInfoFieldName(name));
    }


    public TupleInfoDescriptor getTupleInfoDescriptor(String varName) {
        return this.tupleInfoFieldNameToTupleInfoDescriptor.get(this.getTupleInfoFieldName(varName));
    }

}//class
