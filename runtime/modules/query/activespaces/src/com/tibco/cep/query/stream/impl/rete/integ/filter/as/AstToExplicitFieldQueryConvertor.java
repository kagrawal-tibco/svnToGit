package com.tibco.cep.query.stream.impl.rete.integ.filter.as;

import com.tibco.be.model.rdf.primitives.*;
import com.tibco.cep.as.kit.map.KeyMultiValueTupleAdaptor;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.EvaluatorToExtractorAdapter;
import com.tibco.cep.query.stream.impl.expression.attribute.ExtIdAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.IdAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.bindvar.BindVariableEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.AndEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.NotEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.OrEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.*;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.PropertyEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.SimpleEventPropertyValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.LikeEvaluator;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilterImpl;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASEntityDao;
import com.tibco.cep.runtime.service.om.api.query.SqlFilter;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;

/*
* Author: Ashwin Jayaprakash / Date: 6/13/11 / Time: 4:32 PM
*/
public class AstToExplicitFieldQueryConvertor {
    private static final ConcurrentMap<Concept, ConcurrentMap<String, PropertyDefinition>> conceptProperties =
            new ConcurrentHashMap<Concept, ConcurrentMap<String, PropertyDefinition>>();

    private static final ConcurrentMap<Event, ConcurrentMap<String, EventPropertyDefinition>> eventProperties =
            new ConcurrentHashMap<Event, ConcurrentMap<String, EventPropertyDefinition>>();

    private static final RuntimeException abortLiteException =
            new RuntimeException("Expression tree could not be converted to simple string format") {
                @Override
                public Throwable fillInStackTrace() {
                    return this;
                }
            };

    private static final String OPERATOR_EQUAL = "=";

    private static final String OPERATOR_NOT_EQUAL = "!=";

    private static final String OPERATOR_LIKE = "like";

    private static final ThreadLocal<SimpleDateFormat> GMT_DATE_FORMATTER = new ThreadLocal<SimpleDateFormat>();

    private static Logger logger;

    private Map<String, List<String>> bindVariableValueProxyMap = new HashMap<String, List<String>>(2);

    private Map<String, Integer> bindVariableTypeMap = new HashMap<String, Integer>(2);

    private Map<String, PropertyDefinition> conceptPropertyMap = new HashMap<String, PropertyDefinition>();

    private Map<String, EventPropertyDefinition> eventPropertyMap = new HashMap<String, EventPropertyDefinition>();

    private KeyMultiValueTupleAdaptor tupleAdaptor;

    private Map<String, Object> bindValues;

    protected AstToExplicitFieldQueryConvertor(Class klass, GlobalContext globalContext, QueryContext queryContext) {
        Manager manager = Registry.getInstance().getComponent(Manager.class);
        RegionManager regionManager = manager.getRegionManagers().get(queryContext.getRegionName());
        AgentService as = regionManager.getAgentService();
        TypeManager typeManager = as.getEntityClassLoader();

        KeyValueTupleAdaptor ta = ((ASEntityDao) as.getEntityCache(klass)).getTupleAdaptor();
        if (ta instanceof KeyMultiValueTupleAdaptor) {
            this.tupleAdaptor = (KeyMultiValueTupleAdaptor) ta;
        }

        logger = getLogger();

        RuleServiceProvider ruleServiceProvider = RuleServiceProviderManager.getInstance().getDefaultProvider();
        Ontology ontology = ruleServiceProvider.getProject().getOntology();
        String path = typeManager.getTypeDescriptor(klass).getURI();

        Concept concept = ontology.getConcept(path);
        conceptPropertyMap = addConceptProperties(concept);
        Event event = ontology.getEvent(path);
        eventPropertyMap = addEventProperties(event);

        this.bindValues = queryContext.getGenericStore();
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param klass
     * @param entityFilter
     * @return null if coversion failed.
     */
    public static synchronized SqlFilter tryConvert(GlobalContext globalContext, QueryContext queryContext,
                                                    Class klass, ReteEntityFilter entityFilter) {
        if (entityFilter instanceof ReteEntityFilterImpl) {
            ReteEntityFilterImpl reteEntityFilter = (ReteEntityFilterImpl) entityFilter;
            TupleValueExtractor extractor = reteEntityFilter.getExtractor();

            if (extractor instanceof EvaluatorToExtractorAdapter) {
                EvaluatorToExtractorAdapter evalAdapter = (EvaluatorToExtractorAdapter) extractor;
                ExpressionEvaluator evaluator = evalAdapter.getEvaluator();

                AstToExplicitFieldQueryConvertor queryConvertor =
                        new AstToExplicitFieldQueryConvertor(klass, globalContext, queryContext);

                try {
                    String sql = queryConvertor.build(evaluator);

                    //todo Replace the bind vals with actuals here
                    //queryConvertor.resolveBindVariables(queryContext);

                    if (logger.isEnabledFor(Level.DEBUG)) {
                        logger.log(Level.DEBUG, "Using filter string [" + sql + "] on [" + klass.getName() + "]");
                    }

                    return new SqlFilter(sql);
                }
                catch (Exception e) {
                    if (e != abortLiteException) {
                        logger.log(Level.WARN, "Error occurred while resolving bind variables in ["
                                + queryConvertor.getClass().getName() + "]", e);
                    }
                }
            }
        }

        return null;
    }

    static Logger getLogger() {
        if (logger != null) {
            return logger;
        }

        logger = LogManagerFactory.getLogManager().getLogger("com.tibco.cep.query.as.filter");

        return logger;
    }

    String build(ExpressionEvaluator evaluator) {
        String filter = buildLogicalFilter(evaluator);

        if (filter == null) {
            filter = buildComparisonFilter(evaluator);
        }

        if (filter != null) {
            return filter;
        }

        throw abortLiteException;
    }

    String buildLogicalFilter(ExpressionEvaluator evaluator) {
        if (evaluator instanceof AndEvaluator) {
            AndEvaluator andEval = (AndEvaluator) evaluator;
            ExpressionEvaluator[] operands = andEval.getOperands();
            //--------------
            if (operands.length > 2) {
                return buildComplexAndFilter(operands);
            } else {
                String leftFilter = build(operands[0]);
                String rightFilter = build(operands[1]);
                return convert(leftFilter, " and ", rightFilter);
            }
        }
        else if (evaluator instanceof OrEvaluator) {
            OrEvaluator orEval = (OrEvaluator) evaluator;
            ExpressionEvaluator[] operands = orEval.getOperands();
            String leftFilter = build(operands[0]);
            String rightFilter = build(operands[1]);

            return convert(leftFilter, " or ", rightFilter);
        }
        else if (evaluator instanceof NotEvaluator) {
            NotEvaluator notEval = (NotEvaluator) evaluator;
            ExpressionEvaluator expressionEvaluator = notEval.getEvaluator();
            String filter = build(expressionEvaluator);

            return convert(filter, " ! ");
        }

        return null;
    }

    String buildComparisonFilter(ExpressionEvaluator evaluator) {
        String filter = null;

        if (evaluator instanceof EqualityEvaluator) {
            EqualityEvaluator eqEval = (EqualityEvaluator) evaluator;
            ExpressionEvaluator[] operands = eqEval.getOperands();
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator)) {
                String extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                int extractorType = getExtractorType(leftEvaluator, rightEvaluator);
                filter = convert(extractor, comparable, OPERATOR_EQUAL, extractorType);
                checkForBindVariables(filter, leftEvaluator, rightEvaluator);

                return filter;
            }
        }
        else if (evaluator instanceof InequalityEvaluator) {
            InequalityEvaluator eqEval = (InequalityEvaluator) evaluator;
            ExpressionEvaluator[] operands = eqEval.getOperands();
            if (operands.length == 1 && operands[0] instanceof EqualityEvaluator) {
                operands = ((EqualityEvaluator)operands[0]).getOperands();
            }
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator)) {
                String extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                int extractorType = getExtractorType(leftEvaluator, rightEvaluator);
                filter = convert(extractor, comparable, OPERATOR_NOT_EQUAL, extractorType);
                checkForBindVariables(filter, leftEvaluator, rightEvaluator);

                return filter;
            }
        }
        else if (evaluator instanceof GreaterThanEvaluator) {
            GreaterThanEvaluator greaterThanEvaluator = (GreaterThanEvaluator) evaluator;
            ExpressionEvaluator[] operands = greaterThanEvaluator.getOperands();
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator)) {
                String extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                int extractorType = getExtractorType(leftEvaluator, rightEvaluator);
                if (leftEvaluator instanceof ConstantValueEvaluator || leftEvaluator instanceof BindVariableEvaluator) {
                    filter = convert(extractor, comparable, "<", extractorType);
                }
                else {
                    filter = convert(extractor, comparable, ">", extractorType);
                }
                checkForBindVariables(filter, leftEvaluator, rightEvaluator);

                return filter;
            }
        }
        else if (evaluator instanceof GreaterThanOrEqualToEvaluator) {
            GreaterThanOrEqualToEvaluator greaterThanOrEqualToEvaluator = (GreaterThanOrEqualToEvaluator) evaluator;
            ExpressionEvaluator[] operands = greaterThanOrEqualToEvaluator.getOperands();
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator)) {
                String extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                int extractorType = getExtractorType(leftEvaluator, rightEvaluator);
                if (leftEvaluator instanceof ConstantValueEvaluator || leftEvaluator instanceof BindVariableEvaluator) {
                    filter = convert(extractor, comparable, "<=", extractorType);
                }
                else {
                    filter = convert(extractor, comparable, ">=", extractorType);
                }
                checkForBindVariables(filter, leftEvaluator, rightEvaluator);

                return filter;
            }
        }
        else if (evaluator instanceof LikeEvaluator) {
            LikeEvaluator likeEval = (LikeEvaluator) evaluator;
            ExpressionEvaluator[] operands = likeEval.getOperands();
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator)) {
                String extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                int extractorType = getExtractorType(leftEvaluator, rightEvaluator);
                filter = convert(extractor, comparable, OPERATOR_LIKE, extractorType);
                checkForBindVariables(filter, leftEvaluator, rightEvaluator);

                return filter;
            }
        }
        else if (evaluator instanceof InEvaluator) {
            InEvaluator inEval = (InEvaluator) evaluator;
            ExpressionEvaluator leftEvaluator = inEval.getEvaluator();
            String result = "";
            ExpressionEvaluator[] right = inEval.getSetValues();
            for (ExpressionEvaluator rightEvaluator : right) {
                String extractor = getExtractor(leftEvaluator, rightEvaluator);
                int extractorType = getExtractorType(leftEvaluator, rightEvaluator);
                if(filter == null){
                    filter = extractor + " in (";
                }

                if(rightEvaluator instanceof BindVariableEvaluator) {
                    String bindName = ((BindVariableEvaluator) rightEvaluator).getName();
                    Object bindVal = bindValues.get(bindName);

                    if (bindVal instanceof Object[]) {
                        Object[] array = (Object[]) bindVal;
                        for (Object o : array) {
                            if (result .length() > 0) {
                                result += ",";
                            }

                            result += convertIn((Comparable) o, extractorType);
                        }
                    }
                    else {
                        if (result .length() > 0) {
                            result += ",";
                        }

                        result += convertIn((Comparable) bindVal, extractorType);
                    }
                }else{
                    if (result .length() > 0) {
                        result += ",";
                    }
                    Comparable o = getComparable(leftEvaluator, rightEvaluator);
                    result += convertIn(o, extractorType);
                }
            }

            filter = filter + result + ")";

            return filter;
        }

        //todo Handle in and between clauses?

        return null;
    }

    //-------------------
    private String buildComplexAndFilter(ExpressionEvaluator[] operands) {
        String f = null;

        Deque<ExpressionEvaluator> operandQueue = new ArrayDeque<ExpressionEvaluator>();
        for (ExpressionEvaluator exprEvaluator : operands) {
            operandQueue.addLast(exprEvaluator);//.push(exprEvaluator);
        }
        String leftFilter;
        while (operandQueue.size() > 0) {
            if (f == null) {
                leftFilter = build(operandQueue.poll());
            } else {
                leftFilter = f;
            }
            String rightFilter = build(operandQueue.poll());
            f = convert(leftFilter," and " ,rightFilter);
        }
        return f;
    }
    //-------------------

    String convert(String leftFilter, String operator, String rightFilter) {
        return "(" + leftFilter + " " + operator + " " + rightFilter + ")";
    }

    String convert(String filter, String operator) {
        return operator + " (" + filter + ")";
    }

    static SimpleDateFormat getSdf() {
        SimpleDateFormat sdf = GMT_DATE_FORMATTER.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            GMT_DATE_FORMATTER.set(sdf);
        }

        return sdf;
    }

    String convert(String extractor, Comparable comparable, String operator, int type) {
        if(comparable == null){
            if (operator.equals(OPERATOR_EQUAL)) {
                return extractor + " is null";
            }
            else if (operator.equals(OPERATOR_NOT_EQUAL)) {
                return extractor + " is not null";
            }
        }

        switch (type) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return extractor + " " + operator + " " + comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return extractor + " " + operator + " " + comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_LONG:
                return extractor + " " + operator + " " + comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return extractor + " " + operator + " " + comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                String value = operator.equals(OPERATOR_LIKE) == true ? convertPattern((String) comparable) : convert((String) comparable);
                return extractor + " " + operator + value;
            case PropertyDefinition.PROPERTY_TYPE_DATETIME: {
                Calendar cal = (Calendar) comparable;
                SimpleDateFormat sdf = getSdf();
                comparable = sdf.format(cal.getTime());

                return extractor + " " + operator + " '" + comparable + "' ";
            }

            default:
                throw abortLiteException;
        }
    }

    String convertIn(Comparable comparable, int type) {
        switch (type) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_LONG:
                return comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return comparable + "";
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return convert((String) comparable);
            case PropertyDefinition.PROPERTY_TYPE_DATETIME: {
                Calendar cal = (Calendar) comparable;
                SimpleDateFormat sdf = getSdf();
                comparable = sdf.format(cal.getTime());

                return " '" + comparable + "' ";
            }

            default:
                throw abortLiteException;
        }
    }

    private String convert(String value) {
//        value = value.replace("\\", "\\\\");
//        value = value.replace("\"", "\\\"");
        value = value.replaceAll(Matcher.quoteReplacement("\\\\"), Matcher.quoteReplacement("\\"));
        if (value.contains("\"") == true) {
            return " $$"+value+"$$";
        }
        return " \"" + value + "\"";
    }

    private String convertPattern(String pattern) {
        int startIdx = pattern.indexOf("\\Q");
        if (startIdx != -1) {
            int endIdx = pattern.indexOf("\\E");
            String mainPattern = pattern.substring(startIdx+2, endIdx);
            if (endIdx != -1) {
                StringBuilder sb = new StringBuilder();
                if (startIdx > 0) {
                    sb.append(pattern.substring(0,startIdx));
                }
                mainPattern = mainPattern.replaceAll(Matcher.quoteReplacement("\\\\"), Matcher.quoteReplacement("\\"));
                char[] chars = mainPattern.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (Character.isLetterOrDigit(mainPattern.codePointAt(i)) == false){
                        sb.append("\\");
                    }
//                    if (chars[i] == '\\' || chars[i] == '"') {
//                    	sb.append("\\");
//                    }
                    sb.append(chars[i]);
                }
                if (endIdx + 2 < pattern.length()) {
                    sb.append(pattern.substring(endIdx+2,pattern.length()));
                }
                pattern = sb.toString();
            }
        }
        if (pattern.contains("\"") == true) {
            return " $$"+pattern+"$$";
        }
        return " \"" + pattern + "\"";
    }

    void resolveBindVariables(QueryContext queryContext) throws Exception {
        Map<String, Object> genericStore = queryContext.getGenericStore();
        for (String wrapperFilter : bindVariableValueProxyMap.keySet()) {
            List<String> bindVariables = bindVariableValueProxyMap.get(wrapperFilter);
            List<Object> boundValues = new LinkedList<Object>();
            for (String bindVariable : bindVariables) {
                int type = bindVariableTypeMap.get(bindVariable);
                boundValues.add(castBoundValue(genericStore.get(bindVariable), type));
            }
            if (boundValues.size() == 1) {
                //todo wrapperFilter.setValue(boundValues.get(0));
            }
            else {
                throw new IllegalArgumentException(
                        "Indexed filters can support at most 1 bind variable. Cannot support " + bindVariables +
                                " using " + boundValues);
            }
        }
    }

    Object castBoundValue(Object boundValue, int type) {
        switch (type) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return (Boolean.getBoolean(String.valueOf(boundValue)));
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return Integer.parseInt(String.valueOf(boundValue));
            case PropertyDefinition.PROPERTY_TYPE_LONG:
                return Long.parseLong(String.valueOf(boundValue));
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return Double.parseDouble(String.valueOf(boundValue));
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return String.valueOf(boundValue);
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                return boundValue;

            default:
                throw abortLiteException;
        }
    }

    void checkForBindVariables(String filter,
                               ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        if (isBindVariable(leftEvaluator, rightEvaluator)) {
            /*
            if (true) {
                //todo Handle this later.
                throw abortLiteException;
            }
            */


            String bindVariable = getBindVariable(leftEvaluator);
            if (bindVariable != null) {
                List<String> variableNames = bindVariableValueProxyMap.get(filter);
                int type = getType(rightEvaluator);
                bindVariableTypeMap.put(bindVariable, type);
                if (variableNames == null) {
                    variableNames = new LinkedList<String>();
                    bindVariableValueProxyMap.put(filter, variableNames);
                }
                variableNames.add(bindVariable);
            }
            bindVariable = getBindVariable(rightEvaluator);
            if (bindVariable != null) {
                List<String> variableNames = bindVariableValueProxyMap.get(filter);
                int type = getType(leftEvaluator);
                bindVariableTypeMap.put(bindVariable, type);
                if (variableNames == null) {
                    variableNames = new LinkedList<String>();
                    bindVariableValueProxyMap.put(filter, variableNames);
                }
                variableNames.add(bindVariable);
            }
        }
    }

    String getBindVariable(ExpressionEvaluator evaluator) {
        if (evaluator instanceof BindVariableEvaluator) {
            return ((BindVariableEvaluator) evaluator).getName();
        }
        return null;
    }

    String getExtractor(ExpressionEvaluator evaluator) {
        if (evaluator instanceof SimpleEventPropertyValueEvaluator) {
            return ((SimpleEventPropertyValueEvaluator) evaluator).getPropertyName();
        }
        else if (evaluator instanceof ConceptPropertyAtomValueEvaluator) {
            return ((ConceptPropertyAtomValueEvaluator) evaluator).getPropertyName();
        }
        else if (evaluator instanceof IdAttributeEvaluator) {
            return PortablePojoConstants.PROPERTY_NAME_ID;
        }
        else if (evaluator instanceof ExtIdAttributeEvaluator) {
            return PortablePojoConstants.PROPERTY_NAME_EXT_ID;
        }

        return null;
    }

    int getType(ExpressionEvaluator evaluator) {
        int type = -1;
        if (evaluator instanceof SimpleEventPropertyValueEvaluator) {
            String propertyName = ((SimpleEventPropertyValueEvaluator) evaluator).getPropertyName();
            EventPropertyDefinition propertyDefn = eventPropertyMap.get(propertyName);
            RDFPrimitiveTerm term = propertyDefn.getType();
            if (term instanceof RDFDoubleWrapTerm || term instanceof RDFDoubleTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_REAL;
            }
            else if (term instanceof RDFBooleanTerm || term instanceof RDFBooleanWrapTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_BOOLEAN;
            }
            else if (term instanceof RDFDateTimeTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_DATETIME;
            }
            else if (term instanceof RDFIntegerTerm || term instanceof RDFIntegerWrapTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_INTEGER;
            }
            else if (term instanceof RDFLongTerm || term instanceof RDFLongWrapTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_LONG;
            }
            else if (term instanceof RDFStringTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_STRING;
            }
        }
        else if (evaluator instanceof ConceptPropertyAtomValueEvaluator) {
            String propertyName = ((ConceptPropertyAtomValueEvaluator) evaluator).getPropertyName();
            PropertyDefinition propertyDefn = conceptPropertyMap.get(propertyName);
            type = propertyDefn.getType();
        }
        else if (evaluator instanceof IdAttributeEvaluator) {
            type = PropertyDefinition.PROPERTY_TYPE_LONG;
        }
        else if (evaluator instanceof ExtIdAttributeEvaluator) {
            type = PropertyDefinition.PROPERTY_TYPE_STRING;
        }
        return type;
    }

    Comparable getComparable(ExpressionEvaluator evaluator, Object value) {
        if(value == null){
            return null;
        }

        int type = getType(evaluator);
        switch (type) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return (value instanceof Boolean) ? (Comparable) value : (Boolean.getBoolean(String.valueOf(value)));
            case PropertyDefinition.PROPERTY_TYPE_DATETIME: {
                if (value instanceof Calendar) {
                    return (Comparable) value;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(String.valueOf(value)));
                return calendar;
            }
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return (value instanceof Integer) ? (Comparable) value : Integer.parseInt(String.valueOf(value));
            case PropertyDefinition.PROPERTY_TYPE_LONG:
                return (value instanceof Long) ? (Comparable) value : Long.parseLong(String.valueOf(value));
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return (value instanceof Double) ? (Comparable) value : Double.parseDouble(String.valueOf(value));
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return String.valueOf(value);

            default:
        }
        return null;
    }

    String getExtractor(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        String s = getExtractor(leftEvaluator);
        if (s != null) {
            return s;
        }

        return getExtractor(rightEvaluator);
    }

    int getExtractorType(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        int x = getType(leftEvaluator);
        if (x != -1) {
            return x;
        }

        return getType(rightEvaluator);
    }

    Comparable getComparable(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        if (rightEvaluator instanceof ConstantValueEvaluator) {
            return getComparable(leftEvaluator, ((ConstantValueEvaluator) rightEvaluator).getConstant());
        }
        else if (leftEvaluator instanceof ConstantValueEvaluator) {
            return getComparable(rightEvaluator, ((ConstantValueEvaluator) leftEvaluator).getConstant());
        }
        else if (rightEvaluator instanceof BindVariableEvaluator) {
            // Dummy value which will be replaced later
            //return ((BindVariableEvaluator) rightEvaluator).getName();
            String name = ((BindVariableEvaluator) rightEvaluator).getName();
            return getComparable(leftEvaluator, new ConstantValueEvaluator(this.bindValues.get(name)));
        }
        else if (leftEvaluator instanceof BindVariableEvaluator) {
            // Dummy value which will be replaced later
            //return ((BindVariableEvaluator) leftEvaluator).getName();
            String name = ((BindVariableEvaluator) leftEvaluator).getName();
            return getComparable(new ConstantValueEvaluator(this.bindValues.get(name)), rightEvaluator);
        }
        return null;
    }

    boolean isIndexable(ExpressionEvaluator leftEvaluator,
                        ExpressionEvaluator rightEvaluator) {
        //todo Why only constants like a = 10? Why not handle a = b + 10?

        // one side should be a property evaluator and other side should be a constant value evaluator.
        boolean isShallowReferenced = ((isShallowReferenced(leftEvaluator) &&
                rightEvaluator instanceof ConstantValueEvaluator) ||
                (isShallowReferenced(rightEvaluator) &&
                        leftEvaluator instanceof ConstantValueEvaluator));
        boolean isBindVariable = isBindVariable(leftEvaluator, rightEvaluator);
        return isShallowReferenced || isBindVariable;
    }

    boolean isBindVariable(ExpressionEvaluator leftEvaluator,
                           ExpressionEvaluator rightEvaluator) {
        // check if bind variable is used.
        return ((isShallowReferenced(leftEvaluator) &&
                rightEvaluator instanceof BindVariableEvaluator) ||
                (isShallowReferenced(rightEvaluator) &&
                        leftEvaluator instanceof BindVariableEvaluator));
    }

    boolean isShallowReferenced(ExpressionEvaluator evaluator) {
        // This method excludes contained/reference concept property comparisons from
        // filter building
        if (evaluator instanceof SimpleEventPropertyValueEvaluator ||
                evaluator instanceof ConceptPropertyAtomValueEvaluator) {
            PropertyEvaluator pe = (PropertyEvaluator) evaluator;
            String propertyName = pe.getPropertyName();

            if (tupleAdaptor != null) {
                String[] fieldNames = tupleAdaptor.getFieldNames();
                DataType[] dataTypes = tupleAdaptor.getDataTypes();

                //Can't do binary search? Not sorted.
                for (int i = 0; i < fieldNames.length; i++) {
                    if (propertyName.equals(fieldNames[i])) {
                        return ASEntityDao.ensureIndexable(dataTypes[i].getFieldType());
                    }
                }
            }

            return false;
        }

        return evaluator instanceof IdAttributeEvaluator || evaluator instanceof ExtIdAttributeEvaluator;
    }

    static Map<String, EventPropertyDefinition> addEventProperties(Event event) {
        if (event != null) {
            ConcurrentMap<String, EventPropertyDefinition> properties =
                    new ConcurrentHashMap<String, EventPropertyDefinition>();
            ConcurrentMap<String, EventPropertyDefinition> oldProps = eventProperties.putIfAbsent(event, properties);
            if (oldProps != null) {
                properties = oldProps;
            }
            for (final Object p : event.getAllUserProperties()) {
                final EventPropertyDefinition propertyDefinition = (EventPropertyDefinition) p;
                properties.putIfAbsent(propertyDefinition.getPropertyName(), propertyDefinition);
            }
            return Collections.unmodifiableMap(properties);
        }
        return Collections.emptyMap();
    }

    static Map<String, PropertyDefinition> addConceptProperties(Concept concept) {
        if (concept != null) {
            ConcurrentMap<String, PropertyDefinition> properties = new ConcurrentHashMap<String, PropertyDefinition>();
            ConcurrentMap<String, PropertyDefinition> oldProps = conceptProperties.putIfAbsent(concept, properties);
            if (oldProps != null) {
                properties = oldProps;
            }
            final List propertyDefinitions = concept.getAllPropertyDefinitions();
            for (Object propertyDefinition : propertyDefinitions) {
                PropertyDefinition propertyDefn = (PropertyDefinition) propertyDefinition;
                properties.putIfAbsent(propertyDefn.getName(), propertyDefn);
            }
            return Collections.unmodifiableMap(properties);
        }
        return Collections.emptyMap();
    }

    static Map<String, PropertyDefinition> getConceptProperties(Concept concept) {
        return conceptProperties.get(concept);
    }

    static Map<String, EventPropertyDefinition> getEventProperties(Event event) {
        return eventProperties.get(event);
    }

    public static void main(String[] args) {
        Calendar cal = GregorianCalendar.getInstance();

        SimpleDateFormat sdf = getSdf();
        String s = sdf.format(cal.getTime());
        System.out.println(s);

        s = cal.getTime().toString();
        System.out.println(s);
    }
}
