package com.tibco.cep.query.stream.impl.rete.integ.filter.coherence;

import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.filter.*;
import com.tibco.be.functions.coherence.extractor.CoherenceExtractorFunctions;
import com.tibco.be.model.rdf.primitives.*;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.EvaluatorToExtractorAdapter;
import com.tibco.cep.query.stream.impl.expression.bindvar.BindVariableEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.AndEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.NotEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.OrEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.*;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.PropertyEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.SimpleEventPropertyValueEvaluator;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilterImpl;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterStrategy;
import com.tibco.cep.query.stream.impl.rete.integ.filter.InterpretingFilter;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CoherenceNonIndexedFilter;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

import java.util.*;

/*
* Author: Karthikeyan Subramanian / Date: Apr 2, 2010 / Time: 12:43:31 PM
*/
public class FilterStrategyImpl implements FilterStrategy<Filter> {
    private GlobalContext globalContext;
    private QueryContext queryContext;
    private Map<Filter, IndexInfo> indexMap =
            new HashMap<Filter, IndexInfo>();
    private IndexManager indexManager;
    private Map<String, PropertyDefinition> conceptPropertyMap = new HashMap<String, PropertyDefinition>();
    private Map<String, EventPropertyDefinition> eventPropertyMap = new HashMap<String, EventPropertyDefinition>();
    private Map<CoherenceFilterHelper.IndexAwareWrapperFilter, List<String>> bindVariableValueProxyMap =
            new HashMap<CoherenceFilterHelper.IndexAwareWrapperFilter, List<String>>();
    private Map<String, Integer> bindVariableTypeMap = new HashMap<String, Integer>();
    private Logger logger;

    public FilterStrategyImpl(Class klass, GlobalContext globalContext, QueryContext queryContext, NamedCache cache) {
        this.globalContext = globalContext;
        this.queryContext = queryContext;
        FilterHelperCache filterHelperCache = Registry.getInstance().getComponent(FilterHelperCache.class);
        this.indexManager = filterHelperCache.getIndexManager();
        if (indexManager == null) {
            this.indexManager = filterHelperCache.setIndexManager(new IndexManagerImpl(cache));
        }
        Manager manager = Registry.getInstance().getComponent(Manager.class);
        RegionManager regionManager = manager.getRegionManagers().get(queryContext.getRegionName());
        TypeManager typeManager = regionManager.getAgentService().getEntityClassLoader();
        RuleServiceProvider ruleServiceProvider = RuleServiceProviderManager.getInstance().getDefaultProvider();
        logger = ruleServiceProvider.getLogger(this.getClass());
        Ontology ontology = ruleServiceProvider.getProject().getOntology();
        String path = typeManager.getTypeDescriptor(klass).getURI();
        Concept concept = ontology.getConcept(path);
        conceptPropertyMap = filterHelperCache.addConceptProperties(concept);
        Event event = ontology.getEvent(path);
        eventPropertyMap = filterHelperCache.addEventProperties(event);
    }

    @Override
    public Filter buildComparisonFilter(ExpressionEvaluator evaluator) {
        Filter filter = createDefaultFilter(evaluator);
        if (evaluator instanceof EqualityEvaluator) {
            EqualityEvaluator eqEval = (EqualityEvaluator) evaluator;
            ExpressionEvaluator[] operands = eqEval.getOperands();
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator) == true) {
                ValueExtractor extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                IndexInfo indexInfo = getIndexInfo(leftEvaluator);
                if (indexInfo == null) {
                    indexInfo = getIndexInfo(rightEvaluator);
                }
                filter = new EqualsFilter(extractor, comparable);
                if (indexInfo != null) {
                    indexMap.put(filter, indexInfo);
                }
                filter = checkForBindVariables(filter, leftEvaluator, rightEvaluator);
            }
        } else if (evaluator instanceof InequalityEvaluator) {
            InequalityEvaluator inEqEval = (InequalityEvaluator) evaluator;
            filter = buildNotEqualsFilter(inEqEval);
        } else if (evaluator instanceof GreaterThanEvaluator) {
            GreaterThanEvaluator greaterThanEvaluator = (GreaterThanEvaluator) evaluator;
            ExpressionEvaluator[] operands = greaterThanEvaluator.getOperands();
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator) == true) {
                ValueExtractor extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                IndexInfo indexInfo = getIndexInfo(leftEvaluator);
                if (indexInfo == null) {
                    indexInfo = getIndexInfo(rightEvaluator);
                }
                if (leftEvaluator instanceof ConstantValueEvaluator || leftEvaluator instanceof BindVariableEvaluator) {
                    filter = new LessFilter(extractor, comparable);
                } else {
                    filter = new GreaterFilter(extractor, comparable);
                }
                addIndexToMap(filter, indexInfo);
                filter = checkForBindVariables(filter, leftEvaluator, rightEvaluator);
            }
        } else if (evaluator instanceof GreaterThanOrEqualToEvaluator) {
            GreaterThanOrEqualToEvaluator greaterThanOrEqualToEvaluator = (GreaterThanOrEqualToEvaluator) evaluator;
            ExpressionEvaluator[] operands = greaterThanOrEqualToEvaluator.getOperands();
            ExpressionEvaluator leftEvaluator = operands[0];
            ExpressionEvaluator rightEvaluator = operands[1];
            if (isIndexable(leftEvaluator, rightEvaluator) == true) {
                ValueExtractor extractor = getExtractor(leftEvaluator, rightEvaluator);
                Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
                IndexInfo indexInfo = getIndexInfo(leftEvaluator);
                if (indexInfo == null) {
                    indexInfo = getIndexInfo(rightEvaluator);
                }
                if (leftEvaluator instanceof ConstantValueEvaluator || leftEvaluator instanceof BindVariableEvaluator) {
                    filter = new LessEqualsFilter(extractor, comparable);
                } else {
                    filter = new GreaterEqualsFilter(extractor, comparable);
                }
                if (indexInfo != null) {
                    indexMap.put(filter, indexInfo);
                }
                filter = checkForBindVariables(filter, leftEvaluator, rightEvaluator);
            }
        } else if (evaluator instanceof InEvaluator) {
            InEvaluator inEvaluator = (InEvaluator) evaluator;
            filter = buildInFilter(inEvaluator);
        } else if (evaluator instanceof BetweenEvaluator) {
            BetweenEvaluator betweenEvaluator = (BetweenEvaluator) evaluator;
            filter = buildBetweenFilter(betweenEvaluator);
        }
        return filter;
    }

    @Override
    public Filter buildMiscellaneousFilter(ExpressionEvaluator evaluator) {
        Filter filter = createDefaultFilter(evaluator);
        // Miscellaneous Filters like Like, etc
        return filter;
    }

    @Override
    public Filter buildLogicalFilter(ExpressionEvaluator evaluator) {
        if (evaluator instanceof AndEvaluator) {
            AndEvaluator andEval = (AndEvaluator) evaluator;
            ExpressionEvaluator[] operands = andEval.getOperands();
            if (operands.length > 2) {
                return buildComplexAndFilter(operands);
            } else {
                Filter leftFilter = buildTangosolFilter(operands[0]);
                Filter rightFilter = buildTangosolFilter(operands[1]);
                return buildAndFilter(leftFilter, rightFilter);
            }
        } else if (evaluator instanceof OrEvaluator) {
            OrEvaluator orEval = (OrEvaluator) evaluator;
            ExpressionEvaluator[] operands = orEval.getOperands();
            Filter leftFilter = buildTangosolFilter(operands[0]);
            Filter rightFilter = buildTangosolFilter(operands[1]);
            return buildOrFilter(leftFilter, rightFilter);
        } else if (evaluator instanceof NotEvaluator) {
            NotEvaluator notEval = (NotEvaluator) evaluator;
            ExpressionEvaluator expressionEvaluator = notEval.getEvaluator();
            Filter filter = buildTangosolFilter(expressionEvaluator);
            if (filter instanceof InterpretingFilter) {
                return createDefaultFilter(evaluator);
            }
            return buildNotFilter(filter);
        }
        return null;
    }

    @Override
    public void resolveBindVariables(QueryContext queryContext) throws Exception {
        Map<String, Object> genericStore = queryContext.getGenericStore();
        for (CoherenceFilterHelper.IndexAwareWrapperFilter wrapperFilter : bindVariableValueProxyMap.keySet()) {
            List<String> bindVariables = bindVariableValueProxyMap.get(wrapperFilter);
            List<Object> boundValues = new LinkedList<Object>();
            for (String bindVariable : bindVariables) {
                int type = bindVariableTypeMap.get(bindVariable);
                boundValues.add(castBoundValue(genericStore.get(bindVariable), type));
            }
            if (boundValues.size() == 1) {
                wrapperFilter.setValue(boundValues.get(0));
            } else {
                throw new IllegalArgumentException(
                        "Indexed filters can support at most 1 bind variable. Cannot support " + bindVariables + " using " + boundValues);
            }
        }
    }

    @Override
    public Filter bundleFilters(Collection<Filter> filters) {
        Filter filter = null;
        if (filters.size() == 1) {
            filter =  filters.iterator().next();
        } else if (filters.size() == 0) {
            filter =  new AnyFilter();
        } else {
            List<Filter> reorderedFilters = reorderFilters(filters);
            filter =  new AllFilter(reorderedFilters.toArray(new Filter[0]));
        }

        logger.log(Level.INFO, String.format("Filter is %s", filter.toString()));
        return filter;
    }

    private Object castBoundValue(Object boundValue, int type) {
        switch (type) {
            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                return Boolean.parseBoolean(String.valueOf(boundValue));
            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                return boundValue;
            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                return Integer.parseInt(String.valueOf(boundValue));
            case PropertyDefinition.PROPERTY_TYPE_LONG:
                return Long.parseLong(String.valueOf(boundValue));
            case PropertyDefinition.PROPERTY_TYPE_REAL:
                return Double.parseDouble(String.valueOf(boundValue));
            case PropertyDefinition.PROPERTY_TYPE_STRING:
                return String.valueOf(boundValue);
        }
        return null;
    }

    private Filter buildAndFilter(Filter leftFilter, Filter rightFilter) {
        Filter andFilter = new AndFilter(leftFilter, rightFilter);
        IndexInfo leftIndex = indexMap.get(leftFilter);
        IndexInfo rightIndex = indexMap.get(rightFilter);
        addIndexToMap(andFilter, leftIndex, rightIndex);
        return andFilter;
    }

    private Filter buildOrFilter(Filter leftFilter, Filter rightFilter) {
        Filter orFilter = new OrFilter(leftFilter, rightFilter);
        IndexInfo leftIndex = indexMap.get(leftFilter);
        IndexInfo rightIndex = indexMap.get(rightFilter);
        addIndexToMap(orFilter, leftIndex, rightIndex);
        return orFilter;
    }

    private Filter buildNotFilter(Filter innerFilter) {
        Filter filter = new NotFilter(innerFilter);
        IndexInfo indexInfo = indexMap.get(innerFilter);
        addIndexToMap(filter, indexInfo);
        return filter;
    }

    private Filter buildNotEqualsFilter(InequalityEvaluator inequalityEvaluator) {
        Filter filter = createDefaultFilter(inequalityEvaluator);
        ExpressionEvaluator[] operands = inequalityEvaluator.getOperands();
        if (operands.length != 1 || !(operands[0] instanceof EqualityEvaluator)) {
            return filter;
        }
        EqualityEvaluator equalityEvaluator = (EqualityEvaluator) operands[0];
        ExpressionEvaluator leftEvaluator = equalityEvaluator.getOperands()[0];
        ExpressionEvaluator rightEvaluator = equalityEvaluator.getOperands()[1];
        if (isIndexable(leftEvaluator, rightEvaluator) == true) {
            ValueExtractor extractor = getExtractor(leftEvaluator, rightEvaluator);
            Comparable comparable = getComparable(leftEvaluator, rightEvaluator);
            IndexInfo indexInfo = getIndexInfo(leftEvaluator);
            if (indexInfo == null) {
                indexInfo = getIndexInfo(rightEvaluator);
            }
            if (indexInfo != null) {
                filter = new NotEqualsFilter(extractor, comparable);
                addIndexToMap(filter, indexInfo);
                filter = checkForBindVariables(filter, leftEvaluator, rightEvaluator);
            }
        }
        return filter;
    }

    private Filter buildInFilter(InEvaluator inEvaluator) {
        Filter filter = createDefaultFilter(inEvaluator);
        ExpressionEvaluator exprEvaluator = inEvaluator.getEvaluator();
        ExpressionEvaluator[] setMembers = inEvaluator.getSetValues();
        Set<Comparable> values = new HashSet<Comparable>();
        boolean isIndexed = exprEvaluator instanceof PropertyEvaluator;
        if (!isIndexed) {
            return filter;
        }
        List<String> bindVariables = new LinkedList<String>();
        PropertyEvaluator propEval = (PropertyEvaluator) exprEvaluator;
        for (ExpressionEvaluator setMember : setMembers) {
            if ((setMember instanceof ConstantValueEvaluator) == false &&
                    (setMember instanceof BindVariableEvaluator) == false) {
                isIndexed = false;
                break;
            }
            if (setMember instanceof ConstantValueEvaluator) {
                values.add(getComparable(propEval, ((ConstantValueEvaluator) setMember).getConstant()));
            }
            String bindVariable = getBindVariable(setMember);
            if (bindVariable != null) {
                bindVariables.add(bindVariable);
            }
        }
        if (isIndexed == true) {
            IndexInfo indexInfo = getIndexInfo(exprEvaluator);

            filter = new InFilter(getExtractor(propEval), values);
            addIndexToMap(filter, indexInfo);
        }
        return filter;
    }

    private Filter buildBetweenFilter(BetweenEvaluator betweenEvaluator) {
        Filter filter = createDefaultFilter(betweenEvaluator);
        ExpressionEvaluator exprEvaluator = betweenEvaluator.getExpressionEvaluator();
        ExpressionEvaluator[] boundsEvaluator = betweenEvaluator.getBoundsEvaluator();
        if (exprEvaluator instanceof PropertyEvaluator &&
                (boundsEvaluator[0] instanceof ConstantValueEvaluator ||
                        boundsEvaluator[0] instanceof BindVariableEvaluator) &&
                (boundsEvaluator[1] instanceof ConstantValueEvaluator ||
                        boundsEvaluator[1] instanceof BindVariableEvaluator)) {
            PropertyEvaluator propEval = (PropertyEvaluator) exprEvaluator;
            IndexInfo indexInfo = getIndexInfo(exprEvaluator);
            ValueExtractor extractor = getExtractor((PropertyEvaluator) exprEvaluator);
            Comparable bounds_1 = null, bounds_2 = null;
            if (boundsEvaluator[0] instanceof ConstantValueEvaluator) {
                bounds_1 = getComparable(propEval,
                        ((ConstantValueEvaluator) boundsEvaluator[0]).getConstant());
            }
            if (boundsEvaluator[1] instanceof ConstantValueEvaluator) {
                bounds_2 = getComparable((PropertyEvaluator) exprEvaluator,
                        ((ConstantValueEvaluator) boundsEvaluator[1]).getConstant());
            }
            filter = new BetweenFilter(extractor, bounds_1, bounds_2);
            addIndexToMap(filter, indexInfo);
            filter = checkForBindVariables(filter, boundsEvaluator[0], boundsEvaluator[1]);
        }
        return filter;
    }

    private void addIndexToMap(Filter filter, IndexInfo indexInfo) {
        if (indexInfo != null) {
            indexMap.put(filter, indexInfo);
        }
    }

    private void addIndexToMap(Filter filter, IndexInfo leftIndex, IndexInfo rightIndex) {
        if (leftIndex != null && rightIndex != null && leftIndex.equals(rightIndex)) {
            indexMap.put(filter, leftIndex);
        }
    }

    private Filter checkForBindVariables(Filter filter, ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        if (isBindVariable(leftEvaluator, rightEvaluator) == true) {
            filter = new CoherenceFilterHelper.IndexAwareWrapperFilter(filter);
            String bindVariable = getBindVariable(leftEvaluator);
            if (bindVariable != null) {
                List<String> variableNames = bindVariableValueProxyMap.get(filter);
                int type = getType((PropertyEvaluator) rightEvaluator);
                bindVariableTypeMap.put(bindVariable, type);
                if (variableNames == null) {
                    variableNames = new LinkedList<String>();
                    bindVariableValueProxyMap.put((CoherenceFilterHelper.IndexAwareWrapperFilter) filter, variableNames);
                }
                variableNames.add(bindVariable);
            }
            bindVariable = getBindVariable(rightEvaluator);
            if (bindVariable != null) {
                List<String> variableNames = bindVariableValueProxyMap.get(filter);
                int type = getType((PropertyEvaluator) leftEvaluator);
                bindVariableTypeMap.put(bindVariable, type);
                if (variableNames == null) {
                    variableNames = new LinkedList<String>();
                    bindVariableValueProxyMap.put((CoherenceFilterHelper.IndexAwareWrapperFilter) filter, variableNames);
                }
                variableNames.add(bindVariable);
            }
        }
        return filter;
    }

    private String getBindVariable(ExpressionEvaluator evaluator) {
        if (evaluator instanceof BindVariableEvaluator) {
            return ((BindVariableEvaluator) evaluator).getName();
        }
        return null;
    }

    private Filter createDefaultFilter(ExpressionEvaluator expressionEvaluator) {
        ReteEntityFilter reteEntityFilter = new ReteEntityFilterImpl(new EvaluatorToExtractorAdapter(expressionEvaluator));

        InterpretingFilter interpretingFilter = new InterpretingFilter(reteEntityFilter, globalContext, queryContext);

        return new CoherenceNonIndexedFilter(interpretingFilter);
    }

    private ValueExtractor getExtractor(PropertyEvaluator evaluator) {
        String propertyName = evaluator.getPropertyName();
        if (evaluator instanceof SimpleEventPropertyValueEvaluator) {
            return (ValueExtractor) CoherenceExtractorFunctions.C_EventPropertyGetter(
                    propertyName);
        } else if (evaluator instanceof ConceptPropertyAtomValueEvaluator) {
            PropertyDefinition propertyDefn = conceptPropertyMap.get(evaluator.getPropertyName());
            if (propertyDefn.getName().equals(evaluator.getPropertyName())) {
                switch (propertyDefn.getType()) {
                    case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                        return (ValueExtractor) CoherenceExtractorFunctions.C_BooleanAtomGetter(
                                propertyName);
                    case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                        return (ValueExtractor) CoherenceExtractorFunctions.C_DateTimeAtomGetter(
                                propertyName);
                    case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                        return (ValueExtractor) CoherenceExtractorFunctions.C_IntAtomGetter(
                                propertyName);
                    case PropertyDefinition.PROPERTY_TYPE_LONG:
                        return (ValueExtractor) CoherenceExtractorFunctions.C_LongAtomGetter(
                                propertyName);
                    case PropertyDefinition.PROPERTY_TYPE_REAL:
                        return (ValueExtractor) CoherenceExtractorFunctions.C_DoubleAtomGetter(
                                propertyName);
                    case PropertyDefinition.PROPERTY_TYPE_STRING:
                        return (ValueExtractor) CoherenceExtractorFunctions.C_StringAtomGetter(
                                propertyName);
                }
            }
        }
        return null;
    }

    private int getType(PropertyEvaluator evaluator) {
        int type = -1;
        String propertyName = evaluator.getPropertyName();
        if (evaluator instanceof SimpleEventPropertyValueEvaluator) {
            EventPropertyDefinition propertyDefn = eventPropertyMap.get(propertyName);
            RDFPrimitiveTerm term = propertyDefn.getType();
            if (term instanceof RDFDoubleWrapTerm || term instanceof RDFDoubleTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_REAL;
            } else if (term instanceof RDFBooleanTerm || term instanceof RDFBooleanWrapTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_BOOLEAN;
            } else if (term instanceof RDFDateTimeTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_DATETIME;
            } else if (term instanceof RDFIntegerTerm || term instanceof RDFIntegerWrapTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_INTEGER;
            } else if (term instanceof RDFLongTerm || term instanceof RDFLongWrapTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_LONG;
            } else if (term instanceof RDFStringTerm) {
                type = PropertyDefinition.PROPERTY_TYPE_STRING;
            }
        } else if (evaluator instanceof ConceptPropertyAtomValueEvaluator) {
            PropertyDefinition propertyDefn = conceptPropertyMap.get(evaluator.getPropertyName());
            type = propertyDefn.getType();
        }
        return type;
    }

    private Comparable getComparable(PropertyEvaluator evaluator, Object value) {
        if (value == null) {
            return null;
        } else {
            int type = getType(evaluator);
            switch (type) {
                case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                    return Boolean.parseBoolean(String.valueOf(value));
                case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(String.valueOf(value)));
                    return calendar;
                case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                    return Integer.parseInt(String.valueOf(value));
                case PropertyDefinition.PROPERTY_TYPE_LONG:
                    return Long.parseLong(String.valueOf(value));
                case PropertyDefinition.PROPERTY_TYPE_REAL:
                    return Double.parseDouble(String.valueOf(value));
                case PropertyDefinition.PROPERTY_TYPE_STRING:
                    return String.valueOf(value);
            }
            return null;
        }
    }

    private Filter buildComplexAndFilter(ExpressionEvaluator[] operands) {
        Filter f = null;

        Deque<ExpressionEvaluator> operandQueue = new ArrayDeque<ExpressionEvaluator>();
        for (ExpressionEvaluator exprEvaluator : operands) {
            operandQueue.addLast(exprEvaluator);//.push(exprEvaluator);
        }
        Filter leftFilter;
        while (operandQueue.size() > 0) {
            if (f == null) {
                leftFilter = buildTangosolFilter(operandQueue.poll());
            } else {
                leftFilter = f;
            }
            Filter rightFilter = buildTangosolFilter(operandQueue.poll());
            f = buildAndFilter(leftFilter, rightFilter);
        }
        return f;
    }

    private Filter buildTangosolFilter(ExpressionEvaluator evaluator) {
        Filter filter = buildLogicalFilter(evaluator);
        if (filter == null) {
            filter = buildComparisonFilter(evaluator);
        }
        return filter == null ? createDefaultFilter(evaluator) : filter;
    }

    private ValueExtractor getExtractor(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        if (leftEvaluator instanceof PropertyEvaluator) {
            return getExtractor((PropertyEvaluator) leftEvaluator);
        }
        return getExtractor((PropertyEvaluator) rightEvaluator);
    }

    private Comparable getComparable(ExpressionEvaluator leftEvaluator, ExpressionEvaluator rightEvaluator) {
        if (rightEvaluator instanceof ConstantValueEvaluator) {
            return getComparable((PropertyEvaluator) leftEvaluator, ((ConstantValueEvaluator) rightEvaluator).getConstant());
        } else if (leftEvaluator instanceof ConstantValueEvaluator) {
            return getComparable((PropertyEvaluator) rightEvaluator, ((ConstantValueEvaluator) leftEvaluator).getConstant());
        } else if (rightEvaluator instanceof BindVariableEvaluator) {
            // Dummy value which will be replaced later
            return ((BindVariableEvaluator) rightEvaluator).getName();
        } else if (leftEvaluator instanceof BindVariableEvaluator) {
            // Dummy value which will be replaced later
            return ((BindVariableEvaluator) leftEvaluator).getName();
        }
        return null;
    }

    private IndexInfo getIndexInfo(ExpressionEvaluator evaluator) {
        if (evaluator instanceof SimpleEventPropertyValueEvaluator) {
            SimpleEventPropertyValueEvaluator simpleEventPropertyValueEvaluator = (SimpleEventPropertyValueEvaluator) evaluator;
            return indexManager.getIndexInfo(ModelNameUtil.generatedMemberName(simpleEventPropertyValueEvaluator.getPropertyName()));
        } else if (evaluator instanceof ConceptPropertyAtomValueEvaluator) {
            ConceptPropertyAtomValueEvaluator conceptPropertyAtomValueEvaluator = (ConceptPropertyAtomValueEvaluator) evaluator;
            return indexManager.getIndexInfo(ModelNameUtil.generatedMemberName(conceptPropertyAtomValueEvaluator.getPropertyName()));
        }
        return null;
    }

    private boolean isIndexable(ExpressionEvaluator leftEvaluator,
                                ExpressionEvaluator rightEvaluator) {
        // one side should be a property evaluator and other side should be a constant value evaluator.
        boolean isShallowReferenced = ((isShallowReferenced(leftEvaluator) &&
                rightEvaluator instanceof ConstantValueEvaluator) ||
                (isShallowReferenced(rightEvaluator) &&
                        leftEvaluator instanceof ConstantValueEvaluator));
        boolean isBindVariable = isBindVariable(leftEvaluator, rightEvaluator);
        return isShallowReferenced || isBindVariable;
    }

    private boolean isBindVariable(ExpressionEvaluator leftEvaluator,
                                   ExpressionEvaluator rightEvaluator) {
        // check if bind variable is used.
        return ((isShallowReferenced(leftEvaluator) &&
                rightEvaluator instanceof BindVariableEvaluator) ||
                (isShallowReferenced(rightEvaluator) &&
                        leftEvaluator instanceof BindVariableEvaluator));
    }

    private boolean isShallowReferenced(ExpressionEvaluator evaluator) {
        // This method excludes contained/reference concept property comparisons from
        // filter building
        return (evaluator instanceof SimpleEventPropertyValueEvaluator ||
                evaluator instanceof ConceptPropertyAtomValueEvaluator);
    }

    private List<Filter> reorderFilters(Collection<Filter> filters) {
        List<Filter> unindexedFilters = new LinkedList<Filter>();
        Map<IndexInfo, List<Filter>> indexToFilterMap = new HashMap<IndexInfo, List<Filter>>();
        List<Filter> uncompressedFilters = uncompressFilters(filters);
        // Uncompress all the AND filters into same level.
        boolean shouldContinue = (filters.size() != uncompressedFilters.size());
        while (shouldContinue) {
            int size = uncompressedFilters.size();
            uncompressedFilters = uncompressFilters(uncompressedFilters);
            shouldContinue = (size != uncompressedFilters.size());
        }
        // Apply index based filter ordering.
        for (Filter filter : uncompressedFilters) {
            IndexInfo index = indexMap.get(filter);
            if (index != null) {
                List<Filter> indexedFilters = indexToFilterMap.get(index);
                if (indexedFilters == null) {
                    indexedFilters = new LinkedList<Filter>();
                    indexToFilterMap.put(index, indexedFilters);
                }
                indexedFilters.add(filter);
            } else {
                unindexedFilters.add(filter);
            }
        }
        IndexInfo[] indices = indexToFilterMap.keySet().toArray(new IndexInfo[0]);
        Arrays.sort(indices, new IndexInfoComparator());
        List<Filter> reorderedFilters = new LinkedList<Filter>();
        for (IndexInfo index : indices) {
            reorderedFilters.addAll(indexToFilterMap.get(index));
        }
        reorderedFilters.addAll(unindexedFilters);
        // Check index effectiveness
        return reorderedFilters;
    }

    private List<Filter> uncompressFilters(Collection<Filter> filters) {
        List<Filter> uncompressedFilters = new LinkedList<Filter>();
        for (Filter filter : filters) {
            if (filter instanceof AndFilter) {
                AndFilter andFilter = (AndFilter) filter;
                uncompressedFilters.addAll(Arrays.asList(andFilter.getFilters()));
            } else {
                uncompressedFilters.add(filter);
            }
        }
        return uncompressedFilters;
    }
}
