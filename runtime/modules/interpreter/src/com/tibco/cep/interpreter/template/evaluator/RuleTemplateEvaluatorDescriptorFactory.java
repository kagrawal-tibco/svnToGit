package com.tibco.cep.interpreter.template.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.rule.ActionType;
import com.tibco.cep.designtime.model.registry.FileExtensionTypes;
import com.tibco.cep.interpreter.ExpressionScope;
import com.tibco.cep.query.exec.descriptors.impl.EvaluatorDescriptor;
import com.tibco.cep.query.exec.impl.ConversionHelper;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityAttribute;
import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.FunctionArg;
import com.tibco.cep.query.model.ProjectContext;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.impl.ProjectContextImpl;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.TupleExtractor;
import com.tibco.cep.query.stream.impl.expression.TupleExtractorToEvaluatorAdapter;
import com.tibco.cep.query.stream.impl.expression.array.ArraySizeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.ClosureAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.ExtIdAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.IdAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.IntervalAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.IsSetAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.ParentAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.ScheduledTimeAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.attribute.TtlAttributeEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.AndEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.NotEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.OrEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.EqualityEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.GreaterThanEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.GreaterThanOrEqualToEvaluator;
import com.tibco.cep.query.stream.impl.expression.creation.NewConceptInstanceEvaluator;
import com.tibco.cep.query.stream.impl.expression.creation.NewEventInstanceEvaluator;
import com.tibco.cep.query.stream.impl.expression.function.RuleFunctionEvaluator;
import com.tibco.cep.query.stream.impl.expression.function.StaticFunctionEvaluator;
import com.tibco.cep.query.stream.impl.expression.modification.AssignEvaluator;
import com.tibco.cep.query.stream.impl.expression.modification.ConceptPropertyAtomAssignEvaluator;
import com.tibco.cep.query.stream.impl.expression.modification.MultiAssignEvaluator;
import com.tibco.cep.query.stream.impl.expression.numeric.AdditionEvaluator;
import com.tibco.cep.query.stream.impl.expression.numeric.SubtractionEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyArrayEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.PropertyArrayLengthEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.SimpleEventPropertyValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.ContainsEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.EndsWithEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.EqualityIgnoringCaseEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.StartsWithEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.StringLengthEvaluator;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.EmptyFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RangeFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.SimpleFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.CommandOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;


/**
 * User: nprade
 * Date: 1/20/12
 * Time: 4:18 PM
 */
public class RuleTemplateEvaluatorDescriptorFactory {


    private static final FilterValue FILTER_VALUE_FALSE = new SimpleFilterValueImpl();
    private static final FilterValue FILTER_VALUE_TRUE = new SimpleFilterValueImpl();
    
    static {
        ((SimpleFilterValue) FILTER_VALUE_FALSE).setValue(Boolean.FALSE.toString());
        ((SimpleFilterValue) FILTER_VALUE_TRUE).setValue(Boolean.TRUE.toString());
    }


    private ConversionHelper conversionHelper;
    private ProjectContext projectContext;


    public RuleTemplateEvaluatorDescriptorFactory(
            ProjectContext projectContext) {

        this.conversionHelper = new ConversionHelper(projectContext.getRuleServiceProvider().getTypeManager());
        this.projectContext = projectContext;
    }


    public RuleTemplateEvaluatorDescriptorFactory(
            RuleServiceProvider rsp)
            throws Exception {

        this.projectContext = new ProjectContextImpl(rsp);
        this.projectContext.getFunctionRegistry().resolveContext(false);
    }


    private static TypeInfo getTypeInfo(
            Entity entity,
            String fieldName)
            throws Exception {

        {
            final EntityProperty property = entity.getEntityProperty(fieldName);
            if (null != property) {
                return property.getTypeInfo();
            }
        }

        {
            final EntityAttribute attribute = entity.getEntityAttribute(fieldName);
            if (null != attribute) {
                return attribute.getTypeInfo();
            }
        }

        throw new IllegalArgumentException(
                "Cannot find property or attribute '" + fieldName + "' in: " + entity.getEntityPath());
    }


    private RtEvaluatorDescriptor make(
            Command command,
            Filter filter,
            ExpressionScope scope) {

        if ((filter instanceof MultiFilter) || (null == filter)) {
            return this.make(command, ((MultiFilter) filter), scope);
        }
        else if (filter instanceof SingleFilter) {
            return this.make(command, ((SingleFilter) filter), scope);
        }
//        else if (filter instanceof Clause) { TODO
//        }
//        else if (filter instanceof Command) { TODO
//        }
        else {
            throw new IllegalArgumentException("Unsupported filter type: " + filter);
        }
    }


    public RtEvaluatorDescriptor make(
            Command command,
            List<Filter> filters,
            ExpressionScope scope) {

        if (null != command) {
            final String actionType = command.getActionType();
            if (ActionType.CALL.getLiteral().equals(actionType)) {
                return this.makeCall(command, filters, scope);
            }
            if (ActionType.MODIFY.getLiteral().equals(actionType)) {
                return this.makeModify(command, filters, scope);
            }
            if (ActionType.CREATE.getLiteral().equals(actionType)) {
                return this.makeCreate(command, filters, scope);
            }
            throw new UnsupportedOperationException("Command not supported: " + actionType);
        }

        return this.make(command, filters.get(0), scope);
    }


    public RtEvaluatorDescriptor make(
            Command command,
            MultiFilter multiFilter,
            ExpressionScope scope) {

        if (null == multiFilter) {
            return this.makeValue(command, (FilterValue) null, scope);
        }

        final String matchType = multiFilter.getMatchType();

        if ("Match Any".equals(matchType)) {
            return this.makeOr(multiFilter.getSubClause().getFilters(), scope);
        }
        else if ("Match All".equals(matchType)) {
            return this.makeAnd(multiFilter.getSubClause().getFilters(), scope);
        }
        else if ("Match None".equals(matchType)) {
            return this.makeNot(this.makeOr(multiFilter.getSubClause().getFilters(), scope), scope);
        }
        else {
            throw new IllegalArgumentException("Unsupported match type: " + matchType);
        }
    }

    /**
     *
     * @param command -> If used in context of action evaluation this parameter will not be null.
     * @param singleFilter
     * @param scope
     * @return
     */
    public RtEvaluatorDescriptor make(
            Command command,
            SingleFilter singleFilter,
            ExpressionScope scope) {

        if (null != command) {
            final String actionType = command.getActionType();
            if (ActionType.CREATE.getLiteral().equals(actionType)) {
                return this.makeCreate(command, singleFilter, scope);
            }
            else if (ActionType.CALL.getLiteral().equals(actionType)) {
                final List<Filter> filters = new ArrayList<Filter>();
                if (null != singleFilter) {
                    filters.add(singleFilter);
                }
                return this.makeCall(command, filters, scope);
            }
        }

        if (null == singleFilter) {
            return this.makeValue(command, null, scope);
        }

        final FilterOperator filterOperator = FilterOperator.fromValue(singleFilter.getOperator());
        if (null != filterOperator) {
            switch (filterOperator) {
                case BETWEEN:
                    return this.makeBetween(singleFilter, scope);
                case BETWEEN_INCLUSIVE:
                    return this.makeBetweenInclusive(singleFilter, scope);
                case CONTAINS:
                    return this.makeContains(singleFilter, scope);
                case DOES_NOT_CONTAIN:
                    return this.makeNot(this.makeContains(singleFilter, scope), scope);
                case ENDS_WITH:
                    return this.makeEndsWith(singleFilter, scope);
                case EQUALS:
                    return this.makeEquals(singleFilter, scope);
                case EQUALS_FALSE:
                    return this.makeEqualsFalse(singleFilter, scope);
                case EQUALS_IGNORE_CASE:
                    return this.makeEqualsIgnoreCase(singleFilter, scope);
                case EQUALS_TRUE:
                    return this.makeEqualsTrue(singleFilter, scope);
                case GREATER_THAN:
                case GREATER_THAN_FIELD:
                    return this.makeGreaterThan(singleFilter, scope);
                case GREATER_THAN_OR_EQUAL_TO:
                case GREATER_THAN_OR_EQUAL_TO_FIELD:
                    return this.makeGreaterThanOrEqualTo(singleFilter, scope);
                case IS_NOT_NULL:
                    return this.makeNot(this.makeIsNull(singleFilter, scope), scope);
                case IS_NULL:
                    return this.makeIsNull(singleFilter, scope);
                case LESS_THAN:
                case LESS_THAN_FIELD:
                    return this.makeLessThan(singleFilter, scope);
                case LESS_THAN_OR_EQUAL_TO:
                case LESS_THAN_OR_EQUAL_TO_FIELD:
                    return this.makeLessThanOrEqualTo(singleFilter, scope);
                case MATCHES_OTHER_FIELD:
                    return this.makeEquals(singleFilter, scope);
                case DIFFERS_FROM_FIELD:
                case NOT_EQUALS:
                    return this.makeNot(this.makeEquals(singleFilter, scope), scope);
                case NOT_EQUALS_IGNORE_CASE:
                    return this.makeNot(this.makeEqualsIgnoreCase(singleFilter, scope), scope);
                case STARTS_WITH:
                    return this.makeStartsWith(singleFilter, scope);
            }

        }


        final CommandOperator commandOperator = CommandOperator.fromValue(singleFilter.getOperator());
        if (null != commandOperator) {
            switch (commandOperator) {
                case DECREMENT_BY:
                case DECREMENT_BY_FIELD:
                    return this.makeDecrementBy(command, singleFilter, scope);
                case INCREMENT_BY:
                case INCREMENT_BY_FIELD:
                    return this.makeIncrementBy(command, singleFilter, scope);
                case SET_TO:
                    return this.makeAssign(command, singleFilter, scope);
                /*case SET_TO_DATE:
                    return this.makeAssign(command, singleFilter, scope);*/
                case SET_TO_FALSE:
                    return this.makeAssign(command, FILTER_VALUE_FALSE, singleFilter.getLinks(), scope);
                case SET_TO_FIELD:
                    return this.makeAssign(command, singleFilter, scope);
                case SET_TO_NULL:
                    return this.makeAssign(command, null, singleFilter.getLinks(), scope);
                case SET_TO_TRUE:
                    return this.makeAssign(command, FILTER_VALUE_TRUE, singleFilter.getLinks(), scope);
            }
        }

        throw new IllegalArgumentException("Unsupported operator: " + singleFilter.getOperator());
    }


    private RtEvaluatorDescriptor makeAnd(
            List<Filter> filterList,
            ExpressionScope scope) {

        final int filterCount = filterList.size();
        final RtEvaluatorDescriptor[] childDescriptors = new RtEvaluatorDescriptor[filterCount];
        final ExpressionEvaluator[] childEvaluators = new ExpressionEvaluator[filterCount];

        int i = 0;
        for (final Filter child : filterList) {
            final RtEvaluatorDescriptor descriptor = this.make(null, child, scope);
            childDescriptors[i] = descriptor;
            childEvaluators[i] = descriptor.getEvaluator();
            i++;
        }

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new AndEvaluator(childEvaluators),
                TypeInfoImpl.BOOLEAN);

        for (final RtEvaluatorDescriptor descriptor : childDescriptors) {
            d.addUsed(descriptor);
        }

        return d;
    }


    private RtEvaluatorDescriptor makeArgument(
            List<Filter> filters,
            ExpressionScope scope,
            String argName) {

        SingleFilter argFilter = null;
        for (final Filter f : filters) {
            if (f instanceof SingleFilter) {
                final List<RelatedLink> links = ((SingleFilter) f).getLinks();
                if ((links.size() > 0) && argName.equals(links.get(0).getLinkText())) {
                    argFilter = (SingleFilter) f;
                    break;
                }
            }
        }
        if (null == argFilter) {
            throw new IllegalArgumentException("Cannot find argument named: " + argName);
        }

        switch (CommandOperator.fromValue(argFilter.getOperator())) {
            case SET_TO:
            /*case SET_TO_DATE:*/
            case SET_TO_FIELD:
                return this.makeValue(null, argFilter.getFilterValue(), scope);
            case SET_TO_FALSE:
                return new RtEvaluatorDescriptor(ConstantValueEvaluator.FALSE, TypeInfoImpl.PRIMITIVE_BOOLEAN);
            case SET_TO_NULL:
                return new RtEvaluatorDescriptor(ConstantValueEvaluator.NULL, TypeInfoImpl.OBJECT);
            case SET_TO_TRUE:
                return new RtEvaluatorDescriptor(ConstantValueEvaluator.TRUE, TypeInfoImpl.PRIMITIVE_BOOLEAN);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + argFilter.getOperator());
        }
    }


    private RtEvaluatorDescriptor makeAssign(Command command,
            SingleFilter filter,
            ExpressionScope scope) {

        return this.makeAssign(command, filter.getFilterValue(), filter.getLinks(), scope);
    }


    private RtEvaluatorDescriptor makeAssign(
            Command command,
            FilterValue filterValue,
            List<RelatedLink> links,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(command, filterValue, scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(command, links, scope);

        return this.makeAssign(valueDescriptor, linksDescriptor);
    }


    private RtEvaluatorDescriptor makeAssign(
            RtEvaluatorDescriptor valueDescriptor,
            RtEvaluatorDescriptor linksDescriptor) {

        ExpressionEvaluator leftEvaluator = linksDescriptor.getEvaluator();
        if (leftEvaluator instanceof ConceptPropertyAtomValueEvaluator) {
            leftEvaluator = ((ConceptPropertyAtomValueEvaluator) leftEvaluator).getContainerEvaluator();
        }

        RtEvaluatorDescriptor d = null;

        if (leftEvaluator instanceof ConceptPropertyAtomEvaluator) {
            if (valueDescriptor != null) {
                //Descriptor can be null if value is empty
                d = new RtEvaluatorDescriptor(
                        new ConceptPropertyAtomAssignEvaluator(
                                (ConceptPropertyAtomEvaluator) leftEvaluator,
                                valueDescriptor.getEvaluator()),
                        linksDescriptor.getTypeInfo());
            }
        }
// TODO?
//        else if (leftEvaluator instanceof ConceptPropertyArrayEvaluator) {
//            d = new RtEvaluatorDescriptor(
//                    new ConceptPropertyArrayAssignEvaluator(
//                            (ConceptPropertyAtomEvaluator) leftEvaluator,
//                            valueDescriptor.getEvaluator()),
//                    linksDescriptor.getTypeInfo());
//        }
//        else if (leftEvaluator instanceof ArrayItemEvaluator) {
//            d = new RtEvaluatorDescriptor(
//                    new ArrayItemAssignEvaluator(
//                            (ConceptPropertyAtomEvaluator) leftEvaluator,
//                            valueDescriptor.getEvaluator()),
//                    linksDescriptor.getTypeInfo());
//        }
        else {
            throw new UnsupportedOperationException("Cannot assign to: " + leftEvaluator.getClass().getName());
        }

        if (d != null) {
            d.addUsed(valueDescriptor);
            d.addUsed(linksDescriptor);
        }

        return d;
    }


    private RtEvaluatorDescriptor makeBetween(
            SingleFilter singleFilter,
            ExpressionScope scope) {

        final RangeFilterValue range = (RangeFilterValue) singleFilter.getFilterValue();
        final ExpressionEvaluator min = this.makeValue(range.getMinValue(), scope).getEvaluator();
        final ExpressionEvaluator max = this.makeValue(range.getMaxValue(), scope).getEvaluator();


        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, singleFilter.getLinks(), scope);

        final RtEvaluatorDescriptor result = new RtEvaluatorDescriptor(
                new AndEvaluator(
                        new GreaterThanEvaluator(max, linksDescriptor.getEvaluator()),
                        new GreaterThanEvaluator(linksDescriptor.getEvaluator(), min)),
                TypeInfoImpl.BOOLEAN);

        result.addUsed(linksDescriptor);

        return result;
    }


    private RtEvaluatorDescriptor makeBetweenInclusive(
            SingleFilter singleFilter,
            ExpressionScope scope) {

        final RangeFilterValue range = (RangeFilterValue) singleFilter.getFilterValue();
        final ExpressionEvaluator min = this.makeValue(range.getMinValue(), scope).getEvaluator();
        final ExpressionEvaluator max = this.makeValue(range.getMaxValue(), scope).getEvaluator();

        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, singleFilter.getLinks(), scope);

        final RtEvaluatorDescriptor result = new RtEvaluatorDescriptor(
                new AndEvaluator(
                        new GreaterThanOrEqualToEvaluator(max, linksDescriptor.getEvaluator()),
                        new GreaterThanOrEqualToEvaluator(linksDescriptor.getEvaluator(), min)),
                TypeInfoImpl.BOOLEAN);

        result.addUsed(linksDescriptor);

        return result;
    }


    private RtEvaluatorDescriptor makeCall(
            Command command,
            List<Filter> filters,
            ExpressionScope scope) {

        String functionUrl = command.getType();
        final String rfExtension = "." + FileExtensionTypes.RULEFUNCTION_EXTENSION.getLiteral();
        if ((null != functionUrl) && functionUrl.endsWith(rfExtension)) {
            functionUrl = functionUrl.substring(0, functionUrl.length() - rfExtension.length());
        }
        if ((null == functionUrl) || functionUrl.isEmpty()) {
            throw new IllegalArgumentException("No function URL provided in call statement.");
        }

        final Function f = this.projectContext.getFunctionRegistry().getFunctionByPath(functionUrl);
        if (null == f) {
            throw new IllegalArgumentException("Cannot find function: " + functionUrl);
        }

        return this.makeFunctionCall(f, filters, scope);
    }


    private RtEvaluatorDescriptor makeClosureAttribute(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {
        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ClosureAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.STRING);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeConceptPropertyArray(
            RtEvaluatorDescriptor baseDescriptor,
            String linkName,
            TypeInfo linkTypeInfo,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ConceptPropertyArrayEvaluator(baseDescriptor.getEvaluator(), linkName),
                linkTypeInfo);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeConceptPropertyAtom(
            RtEvaluatorDescriptor baseDescriptor,
            String linkName,
            TypeInfo linkTypeInfo,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ConceptPropertyAtomEvaluator(baseDescriptor.getEvaluator(), linkName),
                linkTypeInfo);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeConceptPropertyAtomValue(
            RtEvaluatorDescriptor baseDescriptor,
            String linkName,
            TypeInfo linkTypeInfo,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ConceptPropertyAtomValueEvaluator(baseDescriptor.getEvaluator(), linkName),
                linkTypeInfo);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeContains(
            SingleFilter singleFilter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, singleFilter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, singleFilter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new ContainsEvaluator(linksDescriptor.getEvaluator(), valueDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeCreate(
            Command command,
            Filter filter,
            ExpressionScope scope) {

        final List<Filter> filters = new ArrayList<Filter>(1);
        filters.add(filter);
        return this.makeCreate(command, filters, scope);
    }


    private RtEvaluatorDescriptor makeCreate(
            Command command,
            List<Filter> filters,
            ExpressionScope scope) {

        try {
            final String uri = command.getType().substring(0, command.getType().lastIndexOf("."));
            final Entity entity = this.projectContext.getEntityRegistry().getEntityByPath(uri);
            final TypeInfo typeInfo = entity.getTypeInfo();

            if (typeInfo.isConcept()) {
                return makeCreateConcept(command, uri, typeInfo, scope);
            } else {
                return makeCreateEvent(command, uri, entity, scope);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Cannot find class of entity to create, for: " + command.getType(), e);
        }

    }


    private RtEvaluatorDescriptor makeCreateConcept(
            Command command,
            String uri,
            TypeInfo typeInfo,
            ExpressionScope scope)
            throws Exception {

        final Map<String, RtEvaluatorDescriptor> fieldDescriptors = new HashMap<String, RtEvaluatorDescriptor>();
        final Map<String, ExpressionEvaluator> fieldEvaluators = new HashMap<String, ExpressionEvaluator>();

        for (final Filter filter : command.getSubClause().getFilters()) {
            final SingleFilter singleFilter = (SingleFilter) filter;

            final String operator = singleFilter.getOperator();
            final CommandOperator commandOperator = CommandOperator.fromValue(operator);
            final RtEvaluatorDescriptor fieldEvaluatorDescriptor;
            switch (commandOperator) {
                case SET_TO:
               /* case SET_TO_DATE:*/
                case SET_TO_FIELD: {
                    fieldEvaluatorDescriptor = this.makeValue(null, singleFilter.getFilterValue(), scope);
                    break;
                }
                case SET_TO_FALSE: {
                    fieldEvaluatorDescriptor = this.makeValue(Boolean.FALSE.toString(), scope);
                    break;
                }
                case SET_TO_NULL: {
                    fieldEvaluatorDescriptor = this.makeValue(null, null, scope);
                    break;
                }
                case SET_TO_TRUE: {
                    fieldEvaluatorDescriptor = this.makeValue(Boolean.TRUE.toString(), scope);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }

            final String fieldName = singleFilter.getLinks().get(0).getLinkText();
            fieldDescriptors.put(fieldName, fieldEvaluatorDescriptor);
            fieldEvaluators.put(fieldName, fieldEvaluatorDescriptor.getEvaluator());
        }

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new NewConceptInstanceEvaluator(uri, fieldEvaluators),
                typeInfo);

        for (final RtEvaluatorDescriptor descriptor : fieldDescriptors.values()) {
            d.addUsed(descriptor);
        }

        return d;
    }


    private RtEvaluatorDescriptor makeCreateEvent(
            Command command,
            String uri,
            Entity entity,
            ExpressionScope scope)
            throws Exception {

        final Map<String, EvaluatorDescriptor> fieldDescriptors = new HashMap<String, EvaluatorDescriptor>();
        final Map<String, ExpressionEvaluator> fieldEvaluators = new HashMap<String, ExpressionEvaluator>();

        for (final Filter filterInCommand : command.getSubClause().getFilters()) {
            final SingleFilter singleFilter = (SingleFilter) filterInCommand;

            final String operator = singleFilter.getOperator();
            if (null != operator) {
                final CommandOperator commandOperator = CommandOperator.fromValue(operator);
                if (null == commandOperator) {
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
                }

                EvaluatorDescriptor fieldEvaluatorDescriptor;
                switch (commandOperator) {
                    case SET_TO:
                   /* case SET_TO_DATE:*/
                    case SET_TO_FIELD: {
                        fieldEvaluatorDescriptor = this.makeValue(null, singleFilter.getFilterValue(), scope);
                        break;
                    }
                    case SET_TO_FALSE: {
                        fieldEvaluatorDescriptor = this.makeValue(Boolean.FALSE.toString(), scope);
                        break;
                    }
                    case SET_TO_NULL: {
                        fieldEvaluatorDescriptor = this.makeValue(null, null, scope);
                        break;
                    }
                    case SET_TO_TRUE: {
                        fieldEvaluatorDescriptor = this.makeValue(Boolean.TRUE.toString(), scope);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unsupported operator: " + operator);
                }

                final String fieldName = singleFilter.getLinks().get(0).getLinkText();

                fieldEvaluatorDescriptor = this.conversionHelper.convert(
                        fieldEvaluatorDescriptor,
                        getTypeInfo(entity, fieldName));

                fieldDescriptors.put(fieldName, fieldEvaluatorDescriptor);
                fieldEvaluators.put(fieldName, fieldEvaluatorDescriptor.getEvaluator());
            }
        }

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new NewEventInstanceEvaluator(uri, fieldEvaluators),
                entity.getTypeInfo());

        for (final EvaluatorDescriptor descriptor : fieldDescriptors.values()) {
            d.addUsed(descriptor);
        }

        return d;

    }


    private RtEvaluatorDescriptor makeDecrementBy(
            Command command,
            SingleFilter singleFilter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor linksEvaluatorDescriptor = this.makeLinks(command, singleFilter.getLinks(), scope);
        final RtEvaluatorDescriptor valueEvaluatorDescriptor =
                this.makeValue(null, singleFilter.getFilterValue(), scope);

        if (null == valueEvaluatorDescriptor) {
            return null;
        }

        final TypeInfo typeInfo;
        try {
            final TypeManager typeManager = this.projectContext.getRuleServiceProvider().getTypeManager();
            typeInfo = new TypeInfoImpl(TypeHelper.getNumericCoercionClass(
                    linksEvaluatorDescriptor.getTypeInfo().getRuntimeClass(typeManager),
                    valueEvaluatorDescriptor.getTypeInfo().getRuntimeClass(typeManager)));
        }
        catch (Exception e) {
            throw new RuntimeException("Could not find result type.", e);
        }

        final RtEvaluatorDescriptor newValueEvaluatorDescriptor = new RtEvaluatorDescriptor(
                new SubtractionEvaluator(
                        linksEvaluatorDescriptor.getEvaluator(),
                        valueEvaluatorDescriptor.getEvaluator()),
                typeInfo);
        newValueEvaluatorDescriptor.addUsed(linksEvaluatorDescriptor);
        newValueEvaluatorDescriptor.addUsed(valueEvaluatorDescriptor);

        return this.makeAssign(newValueEvaluatorDescriptor, linksEvaluatorDescriptor);
    }


    private RtEvaluatorDescriptor makeEndsWith(
            SingleFilter filter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, filter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new EndsWithEvaluator(linksDescriptor.getEvaluator(), valueDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeEntity(
            String name,
            Entity entity) {
//            RelatedLink relatedLink,
//            ExpressionScope scope) {
//
//        final String name = relatedLink.getLinkText();
//        final Entity entity = this.projectContext.getEntityRegistry().getEntityByPath(scope.get(name));

        if (null == entity) {
            throw new IllegalArgumentException("Could not find entity for name: " + name);
        }

        try {
            final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                    new TupleExtractorToEvaluatorAdapter(new TupleExtractor(name), name), // todo args?
                    entity.getTypeInfo());

            final Class runtimeClass = entity.getEntityClass();
            if (null != runtimeClass) {
                d.addUsedColumnName(name, runtimeClass.getName());
            }

            return d;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private RtEvaluatorDescriptor makeEquals(
            SingleFilter filter,
            ExpressionScope scope) {

        return this.makeEquals(filter.getFilterValue(), filter.getLinks(), scope);
    }


    private RtEvaluatorDescriptor makeEquals(
            FilterValue filterValue,
            List<RelatedLink> links,
            ExpressionScope scope) {


        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, links, scope);

        EvaluatorDescriptor valueDescriptor = this.makeValue(null, filterValue, scope);
        if (null == valueDescriptor) {
            final TypeInfo valueTypeInfo = linksDescriptor.getTypeInfo();
            if (valueTypeInfo.isString() && !valueTypeInfo.isArray()) {
                valueDescriptor = new RtEvaluatorDescriptor(ConstantValueEvaluator.EMPTY_STRING, valueTypeInfo);
            } else {
                valueDescriptor = new RtEvaluatorDescriptor(ConstantValueEvaluator.NULL, valueTypeInfo);
            }
        }
        else {
            try {
                valueDescriptor = this.conversionHelper.convert(valueDescriptor, linksDescriptor.getTypeInfo());
            }
            catch (Exception e) {
                return new RtEvaluatorDescriptor(ConstantValueEvaluator.FALSE, TypeInfoImpl.BOOLEAN);
            }
        }

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new EqualityEvaluator(linksDescriptor.getEvaluator(), valueDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeEqualsFalse(
            SingleFilter filter,
            ExpressionScope scope) {

        return this.makeEquals(FILTER_VALUE_FALSE, filter.getLinks(), scope);
    }


    private RtEvaluatorDescriptor makeEqualsIgnoreCase(
            SingleFilter filter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, filter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new EqualityIgnoringCaseEvaluator(linksDescriptor.getEvaluator(), valueDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeEqualsTrue(
            SingleFilter filter,
            ExpressionScope scope) {

        return this.makeEquals(FILTER_VALUE_TRUE, filter.getLinks(), scope);
    }


    private RtEvaluatorDescriptor makeEventProperty(
            RtEvaluatorDescriptor baseDescriptor,
            String linkName,
            TypeInfo linkTypeInfo,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new SimpleEventPropertyValueEvaluator(baseDescriptor.getEvaluator(), linkName),
                linkTypeInfo);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeExtId(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ExtIdAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.STRING);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeFunctionCall(
            Function f,
            List<Filter> filters,
            ExpressionScope scope) {
        try {
            final Class fClass = f.getImplClass();
            final TypeInfo typeInfo = f.getTypeInfo();
            final FunctionArg[] argDefs = f.getArguments();

            final Class[] argClasses = new Class[argDefs.length];
            final EvaluatorDescriptor[] argDescriptors = new EvaluatorDescriptor[argDefs.length];
            final ExpressionEvaluator[] argEvaluators = new ExpressionEvaluator[argDefs.length];

            final TypeManager typeManager = this.projectContext.getRuleServiceProvider().getTypeManager();
            for (int i = 0; i < argDefs.length; i++) {
                final FunctionArg argDef = argDefs[i];
                final String argName = argDef.getName();
                final RtEvaluatorDescriptor argEvaluatorDescriptor = this.makeArgument(filters, scope, argName);
                final TypeInfo argTypeInfo = argDefs[i].getTypeInfo();
                argDescriptors[i] = this.conversionHelper.convert(argEvaluatorDescriptor, argTypeInfo);
                argClasses[i] = argTypeInfo.getRuntimeClass(typeManager);
                argEvaluators[i] = argDescriptors[i].getEvaluator();
            }

            final ExpressionEvaluator e;
            switch (f.getFunctionType()) {
                case FUNCTION_TYPE_AGGREGATE: {
                    e = new StaticFunctionEvaluator(fClass, "calculateAggregate", null, argEvaluators);
                }
                break;

                case FUNCTION_TYPE_CATALOG: {
                    e = new StaticFunctionEvaluator(fClass, f.getName(), argClasses, argEvaluators);
                }
                break;

                case FUNCTION_TYPE_RULE: {
                    e = new RuleFunctionEvaluator(fClass, argEvaluators,  f.getPath());
                }
                break;

                default: {
                    throw new Exception("Unsupported function type for '" + f.getName() + "': " + f.getFunctionType());
                }
            }

            final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(e, typeInfo);
            for (final EvaluatorDescriptor argDescriptor : argDescriptors) {
                d.addUsed(argDescriptor);
            }

            return d;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Could not build function call.", e);
        }
    }


    private RtEvaluatorDescriptor makeGreaterThan(
            SingleFilter filter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, filter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new GreaterThanEvaluator(linksDescriptor.getEvaluator(), valueDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }

    private RtEvaluatorDescriptor makeGreaterThanOrEqualTo(
            SingleFilter filter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, filter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new GreaterThanOrEqualToEvaluator(linksDescriptor.getEvaluator(), valueDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeIdAttribute(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {
        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new IdAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.LONG);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeIncrementBy(
            Command command,
            SingleFilter singleFilter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor linksEvaluatorDescriptor = this.makeLinks(command, singleFilter.getLinks(), scope);
        final RtEvaluatorDescriptor valueEvaluatorDescriptor =
                this.makeValue(null, singleFilter.getFilterValue(), scope);

        if (null == valueEvaluatorDescriptor) {
            return null;
        }

        final TypeInfo typeInfo;
        try {
            final TypeManager typeManager = this.projectContext.getRuleServiceProvider().getTypeManager();
            typeInfo = new TypeInfoImpl(TypeHelper.getNumericCoercionClass(
                            linksEvaluatorDescriptor.getTypeInfo().getRuntimeClass(typeManager),
                            valueEvaluatorDescriptor.getTypeInfo().getRuntimeClass(typeManager)));
        }
        catch (Exception e) {
            throw new RuntimeException("Could not find result type.", e);
        }

        final RtEvaluatorDescriptor newValueEvaluatorDescriptor = new RtEvaluatorDescriptor(
                new AdditionEvaluator(
                        linksEvaluatorDescriptor.getEvaluator(),
                        valueEvaluatorDescriptor.getEvaluator()),
                typeInfo);
        newValueEvaluatorDescriptor.addUsed(linksEvaluatorDescriptor);
        newValueEvaluatorDescriptor.addUsed(valueEvaluatorDescriptor);

        return this.makeAssign(newValueEvaluatorDescriptor, linksEvaluatorDescriptor);
    }


    private RtEvaluatorDescriptor makeIntervalAttribure(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {
        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new IntervalAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.LONG);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeIsNull(
            SingleFilter filter,
            ExpressionScope scope) {

        EvaluatorDescriptor evaluatorDescriptor = this.makeLinks(null, filter.getLinks(), scope);
        if (null == evaluatorDescriptor) {
            evaluatorDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        }

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new EqualityEvaluator(evaluatorDescriptor.getEvaluator(), ConstantValueEvaluator.NULL),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(evaluatorDescriptor);
        return d;
    }


    private RtEvaluatorDescriptor makeIsSetAttribute(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {
        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new IsSetAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeLessThan(
            SingleFilter filter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, filter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new GreaterThanEvaluator(valueDescriptor.getEvaluator(), linksDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeLessThanOrEqualTo(
            SingleFilter filter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, filter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new GreaterThanOrEqualToEvaluator(valueDescriptor.getEvaluator(), linksDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeLinks(
            Entity entity,
            RtEvaluatorDescriptor baseDescriptor,
            List<RelatedLink> links,
            ExpressionScope scope) {

        final int size = links.size();
        if (size == 0) {
            return baseDescriptor;
        }

        final String linkName = links.get(0).getLinkText();
        final TypeInfo baseTypeInfo = baseDescriptor.getTypeInfo();

        RtEvaluatorDescriptor d = null;
        Entity newEntity = null;

        if ("extId".equals(linkName)) {
            d = this.makeExtId(baseDescriptor, scope);
        }
        else if ("id".equals(linkName)) {
            d = this.makeIdAttribute(baseDescriptor, scope);
        }
        else if ("parent".equals(linkName)) {
            d = this.makeParentAttribute(baseDescriptor, scope);
        }
        else if ("isSet".equals(linkName) && baseTypeInfo.isProperty()) {
            d = this.makeIsSetAttribute(baseDescriptor, scope);
        }
        else if ("length".equals(linkName)) {
            if (baseTypeInfo.isArray()) {
                if (baseTypeInfo.isProperty()) {
                    d = this.makePropertyArrayLength(baseDescriptor, scope);
                }
                else {
                    d = this.makeSimpleArrayLength(baseDescriptor, scope);
                }
            }
            else if (baseTypeInfo.isString()) {
                d = this.makeStringLength(baseDescriptor, scope);
            }
        }
        else if (baseTypeInfo.isConcept()) {
            try {

                final EntityProperty p = entity.getEntityProperty(linkName);
                final TypeInfo typeInfo = p.getTypeInfo();
                if (p.isArray()) {
                    d = this.makeConceptPropertyArray(baseDescriptor, linkName, typeInfo, scope);
                }
                else {
                    d = this.makeConceptPropertyAtomValue(baseDescriptor, linkName, typeInfo, scope);
                }
                if (typeInfo.isEntity()) {
                    final String path = ((com.tibco.cep.designtime.model.element.PropertyDefinition)
                            entity.getEntityProperty(linkName).getOntologyObject())
                            .getConceptTypePath();
                    newEntity = this.projectContext.getEntityRegistry().getEntityByPath(path);
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (baseTypeInfo.isEvent()) {
            if ("closure".equals(linkName)) {
                d = this.makeClosureAttribute(baseDescriptor, scope);
            }
            else if ("interval".equals(linkName)) {
                d = this.makeIntervalAttribure(baseDescriptor, scope);
            }
            else if ("payload".equals(linkName)) {
                d = this.makePayloadAttribute(baseDescriptor, scope);
            }
            else if ("scheduledTime".equals(linkName)) {
                d = this.makeScheduledTimeAttribute(baseDescriptor, scope);
            }
            else if ("ttl".equals(linkName)) {
                d = this.makeTtlAttribute(baseDescriptor, scope);
            }
            else {
                try {
                    d = this.makeEventProperty(baseDescriptor, linkName,
                            entity.getEntityProperty(linkName).getTypeInfo(), scope);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (null == d) {
            throw new IllegalArgumentException("Unsupported link: " + linkName);
        }
        else if (size == 1) {
            return d;
        } else {
            return this.makeLinks(newEntity, d, links.subList(1, size), scope);
        }

    }

    /**
     *
     * @param command  -> Action command parameter which will not be null if used in context of action.
     * @param links
     * @param scope
     * @return
     */
    private RtEvaluatorDescriptor makeLinks(
            Command command,
            List<RelatedLink> links,
            ExpressionScope scope) {

    	boolean bFound = true;
        final int size = links.size();
        if (0 == size) {
            return null;
        }

        final String name = links.get(0).getLinkText();


        try {
            if (scope.containsKey(name)) {
                final RtEvaluatorDescriptor d = this.makeTupleExtractor(scope, name);
                final Class runtimeClass = d.getTypeInfo().getRuntimeClass(
                        this.projectContext.getRuleServiceProvider().getTypeManager());

                if (1 == size) {
                    d.addDeclarationUsage(name, null);
                    return d;

                }
                else {
                    if (null == runtimeClass) {
                        throw new IllegalArgumentException("Cannot find type of: " + name);
                    }
                    d.addDeclarationUsage(name, links.get(1).getLinkText());
                    final String path = ModelNameUtil.generatedClassNameToModelPath(runtimeClass.getName());
                    final Entity entity = this.projectContext.getEntityRegistry().getEntityByPath(path);
                    return this.makeLinks(entity, d, links.subList(1, size), scope);

                }
            }
            else if (command != null) {
                final RtEvaluatorDescriptor d = this.makeTupleExtractor(scope, command.getAlias());
                final Class runtimeClass = d.getTypeInfo().getRuntimeClass(
                        this.projectContext.getRuleServiceProvider().getTypeManager());

                if (null != runtimeClass) {
                    d.addDeclarationUsage(command.getAlias(), name);
                    final String path = ModelNameUtil.generatedClassNameToModelPath(runtimeClass.getName());
                    final Entity entity = this.projectContext.getEntityRegistry().getEntityByPath(path);
                    return this.makeLinks(entity, d, links, scope);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Failed to build links starting with: " + name);
    }


    private RtEvaluatorDescriptor makeModify(
            Command command,
            List<Filter> filters,
            ExpressionScope scope) {

        final List<RtEvaluatorDescriptor> assignDescriptors = new ArrayList<RtEvaluatorDescriptor>(filters.size());
        final AssignEvaluator[] assignEvaluators = new AssignEvaluator[filters.size()];

        int index = 0;
        for (final Filter filter : filters) {
            if (filter instanceof SingleFilter) {
                final SingleFilter singleFilter = (SingleFilter) filter;
                final CommandOperator commandOperator = CommandOperator.fromValue(singleFilter.getOperator());
                if (null == commandOperator) {
                    throw new IllegalArgumentException("Command not found in filter!");
                }
                else {
                    final RtEvaluatorDescriptor assignDescriptor;
                    switch (commandOperator) {
                        case DECREMENT_BY:
                        case DECREMENT_BY_FIELD:
                            assignDescriptor = this.makeDecrementBy(command, singleFilter, scope);
                            break;
                        case INCREMENT_BY:
                        case INCREMENT_BY_FIELD:
                            assignDescriptor = this.makeIncrementBy(command, singleFilter, scope);
                            break;
                        case SET_TO:
                      /*  case SET_TO_DATE:*/
                        case SET_TO_FIELD:
                            assignDescriptor = this.makeAssign(command, singleFilter, scope);
                            break;
                        case SET_TO_FALSE:
                            assignDescriptor = this.makeAssign(
                                    command, FILTER_VALUE_FALSE, singleFilter.getLinks(), scope);
                            break;
                        case SET_TO_NULL:
                            assignDescriptor = this.makeAssign(command, null, singleFilter.getLinks(), scope);
                            break;
                        case SET_TO_TRUE:
                            assignDescriptor = this.makeAssign(
                                    command, FILTER_VALUE_TRUE, singleFilter.getLinks(), scope);
                            break;
                        default:
                            throw new UnsupportedOperationException(
                                    "Unsupported modify operator: " + singleFilter.getOperator());
                    }
                    if (null == assignDescriptor) {
                        assignEvaluators[index++] = null;
                    } else {
                        assignEvaluators[index++] = (AssignEvaluator) assignDescriptor.getEvaluator();
                        assignDescriptors.add(assignDescriptor);
                    }
                }
            }
            else {
                throw new UnsupportedOperationException("Unsupported modify filter type: " + filter.getClass());
            }
        }

        RtEvaluatorDescriptor returnValueDescriptor = null;

        if (null != command) {
            final String name = command.getAlias();
            if (null != name) {
                if (!scope.containsKey(name)) {
                    throw new IllegalArgumentException("Concept not found in scope: " + name);
                }
                try {
                    returnValueDescriptor = this.makeTupleExtractor(scope, name);
                }
                catch (Exception e) {
                    throw new RuntimeException("Failed to build return value descriptor for: " + name, e);
                }
                returnValueDescriptor.addDeclarationUsage(name, null);
            }
        }

        if (null == returnValueDescriptor) {
            returnValueDescriptor = new RtEvaluatorDescriptor(ConstantValueEvaluator.NULL, TypeInfoImpl.OBJECT);
        }

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new MultiAssignEvaluator(returnValueDescriptor.getEvaluator(), assignEvaluators),
                returnValueDescriptor.getTypeInfo());

        for (final RtEvaluatorDescriptor descriptor : assignDescriptors) {
            d.addUsed(descriptor);
        }

        return d;
    }


    private RtEvaluatorDescriptor makeNot(
            RtEvaluatorDescriptor descriptor,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new NotEvaluator(descriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(descriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeOr(
            List<Filter> filterList,
            ExpressionScope scope) {

        final int filterCount = filterList.size();
        final RtEvaluatorDescriptor[] childDescriptors = new RtEvaluatorDescriptor[filterCount];
        final ExpressionEvaluator[] childEvaluators = new ExpressionEvaluator[filterCount];

        int i = 0;
        for (final Filter child : filterList) {
            final RtEvaluatorDescriptor descriptor = this.make(null, child, scope);
            childDescriptors[i] = descriptor;
            childEvaluators[i] = descriptor.getEvaluator();
            i++;
        }

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new OrEvaluator(childEvaluators),
                TypeInfoImpl.BOOLEAN);

        for (final RtEvaluatorDescriptor descriptor : childDescriptors) {
            d.addUsed(descriptor);
        }

        return d;
    }


    private RtEvaluatorDescriptor makeParentAttribute(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {

        final com.tibco.cep.designtime.model.Entity entity = this.projectContext.getOntology().getAlias(
                baseDescriptor.getTypeInfo().getDesigntimeClass().getName());

        final TypeInfo linkTypeInfo;
        try {
            linkTypeInfo = new TypeInfoImpl(((ContainedConcept) entity).getParent().getClass());
        }
        catch (Exception e) {
            throw new RuntimeException(e);//todo explicit error
        }

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ParentAttributeEvaluator(baseDescriptor.getEvaluator()),
                linkTypeInfo);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makePayloadAttribute(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {
        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new TtlAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.OBJECT);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makePropertyArrayLength(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new PropertyArrayLengthEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.INTEGER);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeScheduledTimeAttribute(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {
        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ScheduledTimeAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.OBJECT);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeSimpleArrayLength(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new ArraySizeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.INTEGER);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeStartsWith(
            SingleFilter filter,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor valueDescriptor = this.makeValue(null, filter.getFilterValue(), scope);
        final RtEvaluatorDescriptor linksDescriptor = this.makeLinks(null, filter.getLinks(), scope);

        final RtEvaluatorDescriptor d = new RtEvaluatorDescriptor(
                new StartsWithEvaluator(linksDescriptor.getEvaluator(), valueDescriptor.getEvaluator()),
                TypeInfoImpl.BOOLEAN);

        d.addUsed(valueDescriptor);
        d.addUsed(linksDescriptor);

        return d;
    }


    private RtEvaluatorDescriptor makeStringLength(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {

        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new StringLengthEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.INTEGER);

        descriptor.addUsed(baseDescriptor);

        return descriptor;

    }


    private RtEvaluatorDescriptor makeTtlAttribute(
            RtEvaluatorDescriptor baseDescriptor,
            ExpressionScope scope) {
        final RtEvaluatorDescriptor descriptor = new RtEvaluatorDescriptor(
                new TtlAttributeEvaluator(baseDescriptor.getEvaluator()),
                TypeInfoImpl.LONG);

        descriptor.addUsed(baseDescriptor);

        return descriptor;
    }


    private RtEvaluatorDescriptor makeTupleExtractor(
            ExpressionScope scope,
            String name)
            throws Exception {

        final Class runtimeClass = scope.get(name);
        if (null == runtimeClass) {
            throw new IllegalArgumentException("Cannot find name in scope: " + name);
        }

        final RtEvaluatorDescriptor d =  new RtEvaluatorDescriptor(
                new TupleExtractorToEvaluatorAdapter(new TupleExtractor(name), name),
                new TypeInfoImpl(runtimeClass));

        d.addUsedColumnName(name, runtimeClass.getName());

        return d;
    }


    /**
     *
     * @param command -> Action command parameter which will not be null if used in context of action. For <b>when</b>
     *                                     evaluation command will be obviously null.
     * @param filterValue
     * @param scope
     * @return
     */
    private RtEvaluatorDescriptor makeValue(Command command,
            FilterValue filterValue,
            ExpressionScope scope) {

        if ((null == filterValue) || (filterValue instanceof EmptyFilterValue)) {
            return new RtEvaluatorDescriptor(ConstantValueEvaluator.NULL, TypeInfoImpl.OBJECT);
        }
        else if (filterValue instanceof RelatedFilterValue) {
            final List<RelatedLink> links = ((RelatedFilterValue) filterValue).getLinks();
            if ((null == links) || links.isEmpty()) {
                return new RtEvaluatorDescriptor(ConstantValueEvaluator.NULL, TypeInfoImpl.OBJECT);
            }
            else {
                return this.makeLinks(command, links, scope);
            }
        }
        else if (filterValue instanceof SimpleFilterValue) {
            return this.makeValue(((SimpleFilterValue) filterValue).getValue(), scope);
        }
        else {
            throw new IllegalArgumentException("Cannot make value from: " + filterValue.getClass().getName());
        }

    }


    private RtEvaluatorDescriptor makeValue(
            String value,
            ExpressionScope scope) {

        Object typedValue;
        TypeInfo typeInfo;

        try {
            typedValue = Byte.valueOf(value);
            typeInfo = TypeInfoImpl.BYTE;
        }
        catch (NumberFormatException eb) {
            try {
                typedValue = Short.valueOf(value);
                typeInfo = TypeInfoImpl.SHORT;
            }
            catch (NumberFormatException es) {
                try {
                    typedValue = Integer.valueOf(value);
                    typeInfo = TypeInfoImpl.INTEGER;
                }
                catch (NumberFormatException ei) {
                    try {
                        typedValue = Long.valueOf(value);
                        typeInfo = TypeInfoImpl.LONG;
                    }
                    catch (NumberFormatException el) {
                    	try {
                    		typedValue = Double.valueOf(value);
                    		typeInfo = TypeInfoImpl.DOUBLE;
                    	}
                    	catch (Exception ed) {
                    		if ("true".equals(value) || "false".equals(value)) {
                    			typedValue = Boolean.valueOf(value);
                    			typeInfo = TypeInfoImpl.BOOLEAN;
                    		}
                    		else {
                    			try {
                    				typedValue = TypeHelper.toDateTime(value);
                    				typeInfo = TypeInfoImpl.DATETIME;
                    			} catch(Exception e) {
                    				try {
                    					typedValue = TypeHelper.toDateTime(value + ".000+0000");
                    					typeInfo = TypeInfoImpl.DATETIME;
                    				}
                    				catch (Exception edt) {
                    					typedValue = value;
                    					typeInfo = TypeInfoImpl.STRING;
                    				}
                    			}
                    		}
                    	}
                    }
                }
            }
        }

        return new RtEvaluatorDescriptor(new ConstantValueEvaluator(typedValue), typeInfo);
    }
}

