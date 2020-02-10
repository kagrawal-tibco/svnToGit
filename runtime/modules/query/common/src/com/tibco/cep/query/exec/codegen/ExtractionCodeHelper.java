package com.tibco.cep.query.exec.codegen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.exec.descriptors.TupleInfoColumnDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.AliasMapDescriptor;
import com.tibco.cep.query.exec.util.Escaper;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.ArrayAccessProxy;
import com.tibco.cep.query.model.ArrayLengthAttribute;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.BindVariable;
import com.tibco.cep.query.model.BooleanLiteral;
import com.tibco.cep.query.model.CharLiteral;
import com.tibco.cep.query.model.DateTimeLiteral;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.EntityAttributeProxy;
import com.tibco.cep.query.model.EntityProperty;
import com.tibco.cep.query.model.EntityPropertyProxy;
import com.tibco.cep.query.model.Expression;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.FunctionArg;
import com.tibco.cep.query.model.FunctionIdentifier;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.IsSetAttribute;
import com.tibco.cep.query.model.Literal;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NullLiteral;
import com.tibco.cep.query.model.NumberLiteral;
import com.tibco.cep.query.model.Operator;
import com.tibco.cep.query.model.Projection;
import com.tibco.cep.query.model.ProxyContext;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.StringLiteral;
import com.tibco.cep.query.model.TypeExpression;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.TypedContext;
import com.tibco.cep.query.model.UnaryExpression;
import com.tibco.cep.query.model.impl.DateTimeLiteralImpl;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.element.PropertyAtom;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jan 15, 2008
 * Time: 5:27:29 PM
 */


/**
 * Provides methods to add <code>TupleValueExtractor</code>'s and <code>AbstractSimpleExpression</code>'s
 * to a QueryExecutionPlanClassBuilder.
 * @see com.tibco.cep.query.stream.expression.EvaluatableExpression EvaluatableExpression
 * @see com.tibco.cep.query.stream.expression.TupleValueExtractor TupleValueExtractor
 *
 */
public class ExtractionCodeHelper {

    protected static final String BODY_FOR_UNSUPPORTED_OP = "throw new UnsupportedOperationException();";
    protected static final String NAME_OF_VARIABLE_QUERYCONTEXT = "queryContext";

    protected static final String NAME_OF_VARIABLE_TUPLE = "tuple";
    protected static final QueryTemplateRegistry TEMPLATE_REGISTRY = QueryTemplateRegistry.getInstance();

    protected final StringTemplate TEMPLATE_FOR_BODY_OF_EXPRESSION_EVALUATE;
    protected final StringTemplate TEMPLATE_FOR_BODY_OF_TVE_EXTRACT;
    protected final StringTemplate TEMPLATE_FOR_FUNCTION_CALL;
    protected final StringTemplate TEMPLATE_FOR_GET_ARRAY_ACCESS;
    protected final StringTemplate TEMPLATE_FOR_GET_ATTRIBUTE_VALUE;
    protected final StringTemplate TEMPLATE_FOR_GET_BETWEEN;
    protected final StringTemplate TEMPLATE_FOR_GET_BINDING_VAR_AS_BOOLEAN;
    protected final StringTemplate TEMPLATE_FOR_GET_BINDING_VAR_AS_DATETIME;
    protected final StringTemplate TEMPLATE_FOR_GET_BINDING_VAR_AS_DOUBLE;
    protected final StringTemplate TEMPLATE_FOR_GET_BINDING_VAR_AS_INTEGER;
    protected final StringTemplate TEMPLATE_FOR_GET_BINDING_VAR_AS_LONG;
    protected final StringTemplate TEMPLATE_FOR_GET_BINDING_VAR_AS_STRING;
    protected final StringTemplate TEMPLATE_FOR_GET_EQUAL_TO;
    protected final StringTemplate TEMPLATE_FOR_GET_GREATER_THAN;
    protected final StringTemplate TEMPLATE_FOR_GET_GREATER_THAN_OR_EQUAL_TO;
    protected final StringTemplate TEMPLATE_FOR_GET_LESS_THAN;
    protected final StringTemplate TEMPLATE_FOR_GET_LESS_THAN_OR_EQUAL_TO;
    protected final StringTemplate TEMPLATE_FOR_GET_NOT_EQUAL_TO;

    protected final StringTemplate TEMPLATE_FOR_GET_PROPERTY;
    protected final StringTemplate TEMPLATE_FOR_GET_PROPERTY_ATOM;
    protected final StringTemplate TEMPLATE_FOR_GET_PROPERTY_ATOM_VALUE;
    protected final StringTemplate TEMPLATE_FOR_GET_PROPERTY_ARRAY;
    protected final StringTemplate TEMPLATE_FOR_RULE_FUNCTION_CALL;


    protected final QueryExecutionPlanClassBuilder qepClassBuilder;


    /**
     * Contructs a ExtractionCodeHelper for a given a QueryExecutionPlanClassBuilder.
     *
     * @param classBuilder QueryExecutionPlanClassBuilder in which the TVEs will be added.
     */
    public ExtractionCodeHelper(QueryExecutionPlanClassBuilder classBuilder) {
        try {
            TEMPLATE_FOR_BODY_OF_EXPRESSION_EVALUATE = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "methodBodyForSimpleExpressionEvaluate");
            TEMPLATE_FOR_BODY_OF_TVE_EXTRACT = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "methodBodyForTveExtract");
            TEMPLATE_FOR_FUNCTION_CALL = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "functionCall");
            TEMPLATE_FOR_GET_ARRAY_ACCESS = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getArrayAccess");
            TEMPLATE_FOR_GET_ATTRIBUTE_VALUE = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getAttributeValue");
            TEMPLATE_FOR_GET_BETWEEN = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getBetween");
            TEMPLATE_FOR_GET_BINDING_VAR_AS_BOOLEAN = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getBindingVariableAsBoolean");
            TEMPLATE_FOR_GET_BINDING_VAR_AS_DATETIME = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getBindingVariableAsDateTime");
            TEMPLATE_FOR_GET_BINDING_VAR_AS_DOUBLE = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getBindingVariableAsDouble");
            TEMPLATE_FOR_GET_BINDING_VAR_AS_INTEGER = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getBindingVariableAsInteger");
            TEMPLATE_FOR_GET_BINDING_VAR_AS_LONG = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getBindingVariableAsLong");
            TEMPLATE_FOR_GET_BINDING_VAR_AS_STRING = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getBindingVariableAsString");
            TEMPLATE_FOR_GET_EQUAL_TO = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getEQ");
            TEMPLATE_FOR_GET_GREATER_THAN = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getGT");
            TEMPLATE_FOR_GET_GREATER_THAN_OR_EQUAL_TO = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getGE");
            TEMPLATE_FOR_GET_LESS_THAN = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getLT");
            TEMPLATE_FOR_GET_LESS_THAN_OR_EQUAL_TO = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getLE");
            TEMPLATE_FOR_GET_NOT_EQUAL_TO = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getNE");
            TEMPLATE_FOR_GET_PROPERTY = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getProperty");
            TEMPLATE_FOR_GET_PROPERTY_ATOM = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getPropertyAtom");
            TEMPLATE_FOR_GET_PROPERTY_ATOM_VALUE = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getPropertyAtomValue");
            TEMPLATE_FOR_GET_PROPERTY_ARRAY = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "getPropertyArray");
            TEMPLATE_FOR_RULE_FUNCTION_CALL = TEMPLATE_REGISTRY.lookupTemplate("ExecutionPlan", "ruleFunctionCall");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.qepClassBuilder = classBuilder;
    }


    /**
     * Adds a SimpleExpression for a given Expression in a QueryExecutionPlanClassBuilder.
     *
     * @param expression              Expression to get a SimpleExpression for.
     * @param inputAliasMapDescriptor AliasMapDescriptor of the tuples available to the extractor.
     * @return String name of the TVE added.
     * @throws Exception upon problem.
     */
    public String addSimpleExpression(Expression expression, AliasMapDescriptor inputAliasMapDescriptor)
            throws Exception {

        // Creates the source code of the Expression.evaluate method.
        final TypedCodeFragment code = this.createExtractionCode(expression, inputAliasMapDescriptor);

        return this.addSimpleExpression(expression, code);
    }


    /**
     * Adds a SimpleExpression for a given Expression and TypedCodeFragment in a QueryExecutionPlanClassBuilder.
     *
     * @param expression              Expression implemented by the SimpleExpression.
     * @param code                    TypedCodeFragment that represents the generated source code for the Expression.
     * @return String name of the TVE added.
     * @throws Exception upon problem.
     */
    protected String addSimpleExpression(Expression expression, TypedCodeFragment code)
            throws Exception {

        // Creates the alias Map used in the Expression constructor.
        final String aliasMapName = this.qepClassBuilder.addExpressionAliasMap(code.getUsedColumnNames());

        // Method bodies
        final int typeFlag = code.getTypeInfo().getTypeFlag();
        final boolean isArray = (typeFlag & TypeInfo.TYPE_ARRAY) == TypeInfo.TYPE_ARRAY;
        final String[] methodSources;
        if (((typeFlag & TypeInfo.TYPE_INTEGER) == TypeInfo.TYPE_INTEGER) && !isArray) {
            methodSources = new String[] {
                    getBodyOfExpressionEvaluator(code.getSourceBoxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    getBodyOfExpressionEvaluator(this.convertToDouble(code).getSourceUnboxed()),
                    getBodyOfExpressionEvaluator(this.convertToFloat(code).getSourceUnboxed()),
                    getBodyOfExpressionEvaluator(code.getSourceUnboxed()),
                    getBodyOfExpressionEvaluator(this.convertToLong(code).getSourceUnboxed())
            };
        } else if (((typeFlag & TypeInfo.TYPE_LONG) == TypeInfo.TYPE_LONG) && !isArray) {
            methodSources = new String[] {
                    getBodyOfExpressionEvaluator(code.getSourceBoxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    getBodyOfExpressionEvaluator(this.convertToDouble(code).getSourceUnboxed()),
                    getBodyOfExpressionEvaluator(this.convertToFloat(code).getSourceUnboxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    getBodyOfExpressionEvaluator(code.getSourceUnboxed()),
            };
        } else if (((typeFlag & TypeInfo.TYPE_DOUBLE) == TypeInfo.TYPE_DOUBLE) && !isArray) {
            methodSources = new String[] {
                    getBodyOfExpressionEvaluator(code.getSourceBoxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    getBodyOfExpressionEvaluator(code.getSourceUnboxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP
            };
        } else if (((typeFlag & TypeInfo.TYPE_BOOLEAN) == TypeInfo.TYPE_BOOLEAN) && !isArray) {
            methodSources = new String[] {
                    getBodyOfExpressionEvaluator(code.getSourceBoxed()),
                    getBodyOfExpressionEvaluator(code.getSourceUnboxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP
            };
        } else {
            methodSources = new String[] {
                    getBodyOfExpressionEvaluator(code.getSourceBoxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP
            };
        }

        // Creates the Expression class.
        final String expressionClassName = this.qepClassBuilder.addSimpleExpressionClass(aliasMapName,
                methodSources, expression.getSourceString());

        // Creates the Expression field.
        return this.qepClassBuilder.addSimpleExpression(expression, expressionClassName);
    }


    /**
     * Adds a TVE for a given Expression in a QueryExecutionPlanClassBuilder.
     *
     * @param expression               Expression to get a TVE for.
     * @param inputTupleInfoDescriptor TupleInfoDescriptorImpl of the tuples available to the extractor.
     * @return String name of the TVE added.
     * @throws Exception upon problem.
     */
    public String addTupleValueExtractor(Expression expression, TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        // Creates the source code of the TupleValueExtractor.extract method.
        final TypedCodeFragment code = this.createExtractionCode(expression, inputTupleInfoDescriptor);

        return this.addTupleValueExtractor(expression, code);
    }


    /**
     * Adds a TVE for a given TypedCodeFragment in a QueryExecutionPlanClassBuilder.
     *
     * @param context ModelContext associated to the extractor.
     * @param code    TypedCodeFragment of the extractor body.
     * @return String name of the TVE added.
     * @throws Exception upon problem.
     */
    protected String addTupleValueExtractor(ModelContext context, TypedCodeFragment code)
            throws Exception {

        final int typeFlag = code.getTypeInfo().getTypeFlag();
        final boolean isArray = (typeFlag & TypeInfo.TYPE_ARRAY) == TypeInfo.TYPE_ARRAY;
        final String[] methodSources;
        if (((typeFlag & TypeInfo.TYPE_INTEGER) == TypeInfo.TYPE_INTEGER) && !isArray)  {
            methodSources = new String[] {
                    getBodyOfTupleValueExtractor(code.getSourceBoxed()),
                    getBodyOfTupleValueExtractor(code.getSourceUnboxed()),
                    getBodyOfTupleValueExtractor(this.convertToLong(code).getSourceUnboxed()),
                    getBodyOfTupleValueExtractor(this.convertToFloat(code).getSourceUnboxed()),
                    getBodyOfTupleValueExtractor(this.convertToDouble(code).getSourceUnboxed())
            };
        } else if (((typeFlag & TypeInfo.TYPE_LONG) == TypeInfo.TYPE_LONG) && !isArray) {
            methodSources = new String[] {
                    getBodyOfTupleValueExtractor(code.getSourceBoxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    getBodyOfTupleValueExtractor(code.getSourceUnboxed()),
                    getBodyOfTupleValueExtractor(this.convertToFloat(code).getSourceUnboxed()),
                    getBodyOfTupleValueExtractor(this.convertToDouble(code).getSourceUnboxed())
            };
        } else if (((typeFlag & TypeInfo.TYPE_DOUBLE) == TypeInfo.TYPE_DOUBLE) && !isArray) {
            methodSources = new String[] {
                    getBodyOfTupleValueExtractor(code.getSourceBoxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    getBodyOfTupleValueExtractor(code.getSourceUnboxed())
            };
        } else {
            methodSources = new String[] {
                    getBodyOfTupleValueExtractor(code.getSourceBoxed()),
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP,
                    BODY_FOR_UNSUPPORTED_OP           
            };
        }

        // Creates the TupleValueExtractor class.
        final String tveClassName = this.qepClassBuilder.addTupleValueExtractorClass(methodSources,
                context.toString());

        // Creates the TupleValueExtractor field.
        return this.qepClassBuilder.addTupleValueExtractor(context, tveClassName);
    }


    /**
     * Adds a TVE for a given ProxyContext in a QueryExecutionPlanClassBuilder.
     *
     * @param proxy               ProxyContext to get a TVE for.
     * @param inputTupleInfoDescriptor TupleInfoDescriptorImpl of the tuples available to the extractor.
     * @return String name of the TVE added.
     * @throws Exception upon problem.
     */
    public String addTupleValueExtractor(ProxyContext proxy, TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        // Creates the source code of the TupleValueExtractor.extract method.
        final TypedCodeFragment code = this.createExtractionCode(proxy, inputTupleInfoDescriptor);

        return this.addTupleValueExtractor(proxy, code);
    }


    protected TypedCodeFragment convert(TypedCodeFragment fragment, TypeInfo typeInfo) throws Exception {
        final Class targetClass = this.getRuntimeClass(typeInfo);
        final Class boxedTargetClass = TypeHelper.getBoxedClass(targetClass);

        if (Object.class.equals(boxedTargetClass)) {
            return this.convertToObject(fragment);

        } else if (boxedTargetClass.isArray()) {
            return new TypedCodeFragment(
                    new StringBuilder("((")
                            .append(boxedTargetClass.getCanonicalName())
                            .append(") ")
                            .append(TypeHelper.class.getCanonicalName())
                            .append(".toArray(")
                            .append(boxedTargetClass.getComponentType().getCanonicalName())
                            .append(".class, ")
                            .append(fragment.getSourceBoxed())
                            .append("))"),
                    typeInfo);

        } else if (Boolean.class.equals(boxedTargetClass)) {
            return this.convertToBoolean(fragment);

        } else if (Byte.class.equals(boxedTargetClass)) {
            return this.convertToByte(fragment);

        } else if (Double.class.equals(boxedTargetClass)) {
            return this.convertToDouble(fragment);

        } else if (Calendar.class.equals(boxedTargetClass)) {
            return this.convertToDateTime(fragment);

        } else if ((Entity.class.isAssignableFrom(boxedTargetClass))
                || (com.tibco.cep.kernel.model.entity.Entity.class.isAssignableFrom(boxedTargetClass))) {        
            return this.convertToEntity(fragment, typeInfo);

        } else if (Integer.class.equals(boxedTargetClass)) {
            return this.convertToInt(fragment);

        } else if (Long.class.equals(boxedTargetClass)) {
            return this.convertToLong(fragment);

        } else if (PropertyAtom.class.isAssignableFrom(targetClass)) {
            return this.convertToPropertyAtom(fragment, boxedTargetClass);

        } else if (Short.class.equals(boxedTargetClass)) {
            return this.convertToShort(fragment);

        } else if (String.class.equals(boxedTargetClass)) {
            return this.convertToString(fragment);
        }

        throw new Exception("Unsupported conversion to: " + targetClass);
    }


    protected TypedCodeFragment convertToBoolean(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Boolean.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else if (Number.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("(")
                            .append(fragment.getSourceBoxed())
                            .append(".doubleValue() != 0)"),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);

        } else if (String.class.equals(fragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("Boolean.parseBoolean(")
                            .append(fragment)
                            .append(")"),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
        }

        throw new Exception("Cannot convert to boolean: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToByte(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Byte.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else if (Number.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".byteValue()"),
                    TypeInfoImpl.PRIMITIVE_BYTE);

        } else if (String.class.equals(fragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("Byte.parseByte(")
                            .append(fragment.getSource())
                            .append(")"),
                    TypeInfoImpl.PRIMITIVE_BYTE);

        } else if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("((byte) ")
                            .append(fragment.getSourceBoxed())
                            .append(".getTimeInMillis())"),
                    TypeInfoImpl.PRIMITIVE_BYTE);
        }

        throw new Exception("Cannot convert to byte: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToDateTime(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else {
            return new TypedCodeFragment(
                    new StringBuilder(TypeHelper.class.getCanonicalName())
                            .append(".toDateTime(")
                            .append(fragment.getSourceBoxed())
                            .append(")"),
                    TypeInfoImpl.DATETIME);
        }
    }


    protected TypedCodeFragment convertToDouble(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Double.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else if (Number.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".doubleValue()"),
                    TypeInfoImpl.PRIMITIVE_DOUBLE);

        } else if (String.class.equals(fragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("Double.parseDouble(")
                            .append(fragment.getSource())
                            .append(")"),
                    TypeInfoImpl.PRIMITIVE_DOUBLE);

        } else if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("((double) ")
                            .append(fragment.getSourceBoxed())
                            .append(".getTimeInMillis())"),
                    TypeInfoImpl.PRIMITIVE_DOUBLE);
        }

        throw new Exception("Cannot convert to double: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToEntity(TypedCodeFragment fragment, TypeInfo typeInfo) throws Exception {
        final Class entityClass = this.getRuntimeClass(typeInfo);
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        if (entityClass.equals(fragmentRuntimeClass)) {
            return fragment;
        }
        else if (entityClass.isAssignableFrom(fragmentRuntimeClass)
                || fragmentRuntimeClass.isAssignableFrom(entityClass)) {
            return new TypedCodeFragment(
                new StringBuilder("((" + entityClass.getCanonicalName() + ") ")
                        .append(fragment.getSource())
                        .append(")"),
                typeInfo);
        }
        throw new Exception("Cannot convert to " + entityClass.getCanonicalName()
                + " from: " + fragmentRuntimeClass.getCanonicalName());
    }


    protected TypedCodeFragment convertToFloat(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Float.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else if (Number.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".floatValue()"),
                    TypeInfoImpl.PRIMITIVE_FLOAT);

        } else if (String.class.equals(fragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("Float.parseFloat(")
                            .append(fragment.getSource())
                            .append(")"),
                    TypeInfoImpl.PRIMITIVE_FLOAT);

        } else if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("((float) ")
                            .append(fragment.getSourceBoxed())
                            .append(".getTimeInMillis())"),
                    TypeInfoImpl.PRIMITIVE_FLOAT);
        }

        throw new Exception("Cannot convert to float: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToInt(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Integer.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else if (Number.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".intValue()"),
                    TypeInfoImpl.PRIMITIVE_INTEGER);

        } else if (String.class.equals(fragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("Integer.parseInt(")
                            .append(fragment.getSource())
                            .append(")"),
                    TypeInfoImpl.PRIMITIVE_INTEGER);

        } else if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("((int) ")
                            .append(fragment.getSourceBoxed())
                            .append(".getTimeInMillis())"),
                    TypeInfoImpl.PRIMITIVE_INTEGER);
        }

        throw new Exception("Cannot convert to integer: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToLong(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Long.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else if (Number.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".longValue()"),
                    TypeInfoImpl.PRIMITIVE_LONG);

        } else if (String.class.equals(fragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("Long.parseLong(")
                            .append(fragment.getSource())
                            .append(")"),
                    TypeInfoImpl.PRIMITIVE_LONG);

        } else if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".getTimeInMillis()"),
                    TypeInfoImpl.PRIMITIVE_LONG);
        }

        throw new Exception("Cannot convert to long: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToObject(TypedCodeFragment fragment) throws Exception {
        return new TypedCodeFragment(
                new StringBuilder("((java.lang.Object) ")
                        .append(fragment.getSourceBoxed())
                        .append(")"),
                TypeInfoImpl.OBJECT);
    }


    protected TypedCodeFragment convertToPropertyAtom(
            TypedCodeFragment fragment,
            Class propertyAtomClass)
            throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());

        if (propertyAtomClass.isAssignableFrom(fragmentRuntimeClass)) {
            return fragment;
        }

        throw new Exception("Cannot convert to PropertyAtom: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToShort(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (Short.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return fragment;

        } else if (Number.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".shortValue()"),
                    TypeInfoImpl.PRIMITIVE_SHORT);

        } else if (String.class.equals(fragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("Short.parseShort(")
                            .append(fragment.getSource())
                            .append(")"),
                    TypeInfoImpl.PRIMITIVE_SHORT);

        } else if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder("((short) ")
                            .append(fragment.getSourceBoxed())
                            .append(".getTimeInMillis())"),
                    TypeInfoImpl.PRIMITIVE_SHORT);
        }

        throw new Exception("Cannot convert to short: " + fragmentRuntimeClass);
    }


    protected TypedCodeFragment convertToString(TypedCodeFragment fragment) throws Exception {
        final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
        final Class boxedFragmentRuntimeClass = TypeHelper.getBoxedClass(fragmentRuntimeClass);

        if (String.class.equals(fragmentRuntimeClass)) {
            return fragment;

        } else if (Calendar.class.isAssignableFrom(boxedFragmentRuntimeClass)) {
            return new TypedCodeFragment(
                    new StringBuilder(DateTimeLiteralImpl.class.getCanonicalName())
                            .append(".FORMAT.format(")
                            .append(fragment.getSourceBoxed())
                            .append(".getTime())"),
                    TypeInfoImpl.STRING);

        } else {
            return new TypedCodeFragment(
                    new StringBuilder(fragment.getSourceBoxed())
                            .append(".toString()"),
                    TypeInfoImpl.STRING);
        }
    }


    protected TypedCodeFragment createBetweenExtractionCode(BinaryExpression exp, TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {
        final TypedCodeFragment fragment = this.createExtractionCode(exp.getLeftExpression(), inputTupleInfoDescriptor);

        final Iterator it = exp.getRightExpression().getChildrenIterator();
        final TypedCodeFragment bound1 = this.createExtractionCode((Expression) it.next(), inputTupleInfoDescriptor);
        final TypedCodeFragment bound2 = this.createExtractionCode((Expression) it.next(), inputTupleInfoDescriptor);

        TEMPLATE_FOR_GET_BETWEEN.reset();
        TEMPLATE_FOR_GET_BETWEEN.setAttribute("expr", fragment.getSourceBoxed());
        TEMPLATE_FOR_GET_BETWEEN.setAttribute("bound1", bound1.getSourceBoxed());
        TEMPLATE_FOR_GET_BETWEEN.setAttribute("bound2", bound2.getSourceBoxed());
        fragment.set(new StringBuilder(TEMPLATE_FOR_GET_BETWEEN.toString()),
                TypeInfoImpl.PRIMITIVE_BOOLEAN);
        fragment.addUsedColumnNames(bound1);
        fragment.addUsedColumnNames(bound2);

        return fragment;
    }


    protected TypedCodeFragment createCastExtractionCode(BinaryExpression exp, TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {
        final TypedCodeFragment fragment = this.createExtractionCode(exp.getLeftExpression(), inputTupleInfoDescriptor);
        final TypeExpression typeExp = (TypeExpression) exp.getRightExpression();
        return this.convert(fragment, typeExp.getInstancesTypeInfo());
    }


    protected TypedCodeFragment createExtractionCode(
            ArrayLengthAttribute lengthAttr,
            TupleInfoDescriptor inputTupleInfoDescriptor
    ) throws Exception {
        final ModelContext array = lengthAttr.getParentContext();
        final TypedCodeFragment fragment;
        if (array instanceof EntityPropertyProxy) {
            fragment = this.createExtractionCode((EntityPropertyProxy) array, inputTupleInfoDescriptor);
            TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.reset();
            TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.setAttribute("container", fragment);
            TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.setAttribute("attributeName", "length");
            fragment.setSource(new StringBuilder(TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.toString()));
        } else if (array instanceof Expression) {
            fragment = this.createExtractionCode((Expression) array, inputTupleInfoDescriptor);
            fragment.setSource(fragment.getSource().append(".length"));
        } else {
            throw new Exception("Unsuported array type: " + array);
        }
        fragment.setTypeInfo(TypeInfoImpl.PRIMITIVE_INTEGER);
        return fragment;
    }


    protected TypedCodeFragment createExtractionCode(BinaryExpression binExp,
                                                     TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final int opType = binExp.getOperator().getOpType();

        switch (opType) {

            // =
            case Operator.OP_EQ: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
                if ((null == fragmentRuntimeClass)
                        && (null == this.getRuntimeClass(otherFragment.getTypeInfo()))) {
                    fragment.set(new StringBuilder("true"), TypeInfoImpl.PRIMITIVE_BOOLEAN);
                } else {
                    TEMPLATE_FOR_GET_EQUAL_TO.reset();
                    TEMPLATE_FOR_GET_EQUAL_TO.setAttribute("arg1", fragment.getSourceBoxed());
                    TEMPLATE_FOR_GET_EQUAL_TO.setAttribute("arg2", otherFragment.getSourceBoxed());
                    fragment.set(
                            new StringBuilder(TEMPLATE_FOR_GET_EQUAL_TO.toString()),
                            TypeInfoImpl.PRIMITIVE_BOOLEAN);
                }
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }

            // !=
            case Operator.OP_NE: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
                if ((null == fragmentRuntimeClass)
                        && (null == this.getRuntimeClass(otherFragment.getTypeInfo()))) {
                    fragment.set(new StringBuilder("true"), TypeInfoImpl.PRIMITIVE_BOOLEAN);
                } else {
                    TEMPLATE_FOR_GET_NOT_EQUAL_TO.reset();
                    TEMPLATE_FOR_GET_NOT_EQUAL_TO.setAttribute("arg1", fragment.getSourceBoxed());
                    TEMPLATE_FOR_GET_NOT_EQUAL_TO.setAttribute("arg2", otherFragment.getSourceBoxed());
                    fragment.set(
                            new StringBuilder(TEMPLATE_FOR_GET_NOT_EQUAL_TO.toString()),
                            TypeInfoImpl.PRIMITIVE_BOOLEAN);
                }
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }


            // >=
            case Operator.OP_GE: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
                if ((null == fragmentRuntimeClass)
                        || (null == this.getRuntimeClass(otherFragment.getTypeInfo()))) {
                    fragment.set(new StringBuilder("false"), TypeInfoImpl.PRIMITIVE_BOOLEAN);
                } else {
                    TEMPLATE_FOR_GET_GREATER_THAN_OR_EQUAL_TO.reset();
                    TEMPLATE_FOR_GET_GREATER_THAN_OR_EQUAL_TO.setAttribute("arg1", fragment.getSourceBoxed());
                    TEMPLATE_FOR_GET_GREATER_THAN_OR_EQUAL_TO.setAttribute("arg2", otherFragment.getSourceBoxed());
                    fragment.set(
                            new StringBuilder(TEMPLATE_FOR_GET_GREATER_THAN_OR_EQUAL_TO.toString()),
                            TypeInfoImpl.PRIMITIVE_BOOLEAN);
                }
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }


            // >
            case Operator.OP_GT: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
                if ((null == fragmentRuntimeClass)
                        || (null == this.getRuntimeClass(otherFragment.getTypeInfo()))) {
                    fragment.set(new StringBuilder("false"), TypeInfoImpl.PRIMITIVE_BOOLEAN);
                } else {
                    TEMPLATE_FOR_GET_GREATER_THAN.reset();
                    TEMPLATE_FOR_GET_GREATER_THAN.setAttribute("arg1", fragment.getSourceBoxed());
                    TEMPLATE_FOR_GET_GREATER_THAN.setAttribute("arg2", otherFragment.getSourceBoxed());
                    fragment.set(
                            new StringBuilder(TEMPLATE_FOR_GET_GREATER_THAN.toString()),
                            TypeInfoImpl.PRIMITIVE_BOOLEAN);
                }
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }
            

            // <=
            case Operator.OP_LE: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
                if ((null == fragmentRuntimeClass)
                        || (null == this.getRuntimeClass(otherFragment.getTypeInfo()))) {
                    fragment.set(new StringBuilder("false"), TypeInfoImpl.PRIMITIVE_BOOLEAN);
                } else {
                    TEMPLATE_FOR_GET_LESS_THAN_OR_EQUAL_TO.reset();
                    TEMPLATE_FOR_GET_LESS_THAN_OR_EQUAL_TO.setAttribute("arg1", fragment.getSourceBoxed());
                    TEMPLATE_FOR_GET_LESS_THAN_OR_EQUAL_TO.setAttribute("arg2", otherFragment.getSourceBoxed());
                    fragment.set(
                            new StringBuilder(TEMPLATE_FOR_GET_LESS_THAN_OR_EQUAL_TO.toString()),
                            TypeInfoImpl.PRIMITIVE_BOOLEAN);
                }
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }


            // <
            case Operator.OP_LT: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                final Class fragmentRuntimeClass = this.getRuntimeClass(fragment.getTypeInfo());
                if ((null == fragmentRuntimeClass)
                        || (null == this.getRuntimeClass(otherFragment.getTypeInfo()))) {
                    fragment.set(new StringBuilder("false"), TypeInfoImpl.PRIMITIVE_BOOLEAN);
                } else {
                    TEMPLATE_FOR_GET_LESS_THAN.reset();
                    TEMPLATE_FOR_GET_LESS_THAN.setAttribute("arg1", fragment.getSourceBoxed());
                    TEMPLATE_FOR_GET_LESS_THAN.setAttribute("arg2", otherFragment.getSourceBoxed());
                    fragment.set(
                            new StringBuilder(TEMPLATE_FOR_GET_LESS_THAN.toString()),
                            TypeInfoImpl.PRIMITIVE_BOOLEAN);
                }
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }


            // Boolean
            case Operator.OP_AND:
            case Operator.OP_OR: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                fragment.set(
                        new StringBuilder("(")
                                .append(fragment.getSourceUnboxed())
                                .append(binExp.getOperator().getJavaOperator())
                                .append(otherFragment.getSourceUnboxed())
                                .append(")"),
                        TypeInfoImpl.PRIMITIVE_BOOLEAN);
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }

            //Numeric
            case Operator.OP_MOD:
            case Operator.OP_PLUS:
            case Operator.OP_MINUS:
            case Operator.OP_MULTIPLY:
            case Operator.OP_DIVIDE: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                final Class resultType = TypeHelper.getUnboxedClass(this.getRuntimeClass(binExp));
                fragment.set(
                        new StringBuilder("((")
                                .append(resultType.getCanonicalName())
                                .append(") (")
                                .append(fragment.getSourceUnboxed())
                                .append(binExp.getOperator().getJavaOperator())
                                .append(otherFragment.getSourceUnboxed())
                                .append("))"),
                        new TypeInfoImpl(resultType));
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }

            // CONCAT
            case Operator.OP_CONCAT: {
                final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                fragment.set(
                        new StringBuilder("(")
                                .append(fragment.getSource())
                                .append(" + ")
                                .append(otherFragment.getSource())
                                .append(")"),
                        TypeInfoImpl.STRING);
                fragment.addUsedColumnNames(otherFragment);
                return fragment;
            }

            // Object
            case Operator.OP_AT:
            case Operator.OP_DOT: {
                final ModelContext ctx = binExp.getIdentifiedContext();
                if (ctx instanceof ProxyContext) {
                    return this.createExtractionCode((ProxyContext) ctx, inputTupleInfoDescriptor);
                } else if (ctx instanceof ArrayLengthAttribute) {
                    return createExtractionCode((ArrayLengthAttribute) ctx, inputTupleInfoDescriptor);
                } else if (ctx instanceof IsSetAttribute) {
                    return createExtractionCode((IsSetAttribute) ctx, inputTupleInfoDescriptor);
                }
                break;
            }

            // ARRAY
            case Operator.OP_ARRAY: {
                final ModelContext ctx = binExp.getIdentifiedContext();
                if (ctx instanceof ArrayAccessProxy) {
                    return this.createExtractionCode((ArrayAccessProxy) ctx, inputTupleInfoDescriptor);
                } else {
                    final TypedCodeFragment fragment = this.createExtractionCode(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                    final TypedCodeFragment otherFragment = this.createExtractionCode(binExp.getRightExpression(), inputTupleInfoDescriptor);
                    if (ctx instanceof Expression) {
                        final TypeInfo itemTypeInfo = ((Expression) ctx).getTypeInfo();
                        final Class resultType = this.getRuntimeClass(itemTypeInfo);
                        fragment.set(
                                new StringBuilder("((")
                                        .append(resultType.getCanonicalName())
                                        .append(") (")
                                        .append(fragment.getSource())
                                        .append("[")
                                        .append(otherFragment.getSourceUnboxed())
                                        .append("]))"),
                                itemTypeInfo);
                        fragment.addUsedColumnNames(otherFragment);
                        return fragment;
                    }
                }
                break;
            }

            // IN
            case Operator.OP_IN: {
                    return this.createInExtractionCode(binExp, inputTupleInfoDescriptor);
            }

            // BETWEEN
            case Operator.OP_BETWEEN: {
                return this.createBetweenExtractionCode(binExp, inputTupleInfoDescriptor);
            }

            // Cast
            case Operator.OP_CAST: {
                return this.createCastExtractionCode(binExp, inputTupleInfoDescriptor);
            }
        }

        throw new Exception("Unsupported operator '" +  binExp.getOperator()
                + "' in the binary expression: " + binExp.getSourceString());
    }


    protected TypedCodeFragment createExtractionCode(BindVariable bindingVariable)
            throws Exception {

        final Class runtimeClass = TypeHelper.getBoxedClass(this.getRuntimeClass(bindingVariable));
        final String escapedPrefixedLabel = Escaper.toJavaStringSource(
                QueryExecutionPlanClassBuilder.PREFIX_OF_BIND_VARIABLE + bindingVariable.getLabel());

        final StringTemplate template;
        if (Boolean.class == runtimeClass) {
            template = TEMPLATE_FOR_GET_BINDING_VAR_AS_BOOLEAN;
        } else if (Calendar.class.isAssignableFrom(runtimeClass)) {
            template = TEMPLATE_FOR_GET_BINDING_VAR_AS_DATETIME;
        } else if (Double.class == runtimeClass) {
            template = TEMPLATE_FOR_GET_BINDING_VAR_AS_DOUBLE;
        } else if (Integer.class == runtimeClass) {
            template = TEMPLATE_FOR_GET_BINDING_VAR_AS_INTEGER;
        } else if (Long.class == runtimeClass) {
            template = TEMPLATE_FOR_GET_BINDING_VAR_AS_LONG;
        } else if (String.class == runtimeClass) {
            template = TEMPLATE_FOR_GET_BINDING_VAR_AS_STRING;
        } else {
            throw new IllegalArgumentException("Unsupported variable type: " + runtimeClass);
        }
        template.reset();
        template.setAttribute("queryContextName", NAME_OF_VARIABLE_QUERYCONTEXT);
        template.setAttribute("prefixedLabel", escapedPrefixedLabel);


        return new TypedCodeFragment(
                new StringBuilder(template.toString()),
                new TypeInfoImpl(runtimeClass));
    }


    protected TypedCodeFragment createExtractionCode(Expression expression,
                                                     TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        if (expression instanceof Literal) {
            return this.createExtractionCode((Literal) expression);
        }

        final TypedCodeFragment fromTuple = this.createExtractionCodeFromTuple(expression, inputTupleInfoDescriptor);
        if (null != fromTuple) {
            return fromTuple;
        }

        if (expression instanceof BinaryExpression) {
            return this.createExtractionCode((BinaryExpression) expression, inputTupleInfoDescriptor);
        }

        if (expression instanceof BindVariable) {
            return this.createExtractionCode((BindVariable) expression);
        }

        if (expression instanceof FunctionIdentifier) {
            return this.createExtractionCode((FunctionIdentifier) expression, inputTupleInfoDescriptor);
        }

        if (expression instanceof Identifier) {
            return this.createExtractionCode((Identifier) expression, inputTupleInfoDescriptor);
        }

        if (expression instanceof ProxyContext) {
            return this.createExtractionCode((ProxyContext) expression,
                    inputTupleInfoDescriptor);
        }

        if (expression instanceof UnaryExpression) {
            return this.createExtractionCode((UnaryExpression) expression, inputTupleInfoDescriptor);
        }

//        if (expression instanceof ScopedIdentifier) {
//            return this.createExtractionCode((ScopedIdentifier) expression, inputTupleInfoDescriptor);
//        }

        throw new Exception("Unsupported expression: " + expression.getSourceString());
    }


    protected TypedCodeFragment createExtractionCode(FunctionIdentifier functionIdentifier,
                                                     TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final Function f = (Function) functionIdentifier.getIdentifiedContext();
        final FunctionArg[] argDefs = f.getArguments();
        final Expression[] argExprs = functionIdentifier.getArguments();
        final TypeInfo typeInfo = f.getTypeInfo();
        final TypedCodeFragment fragment = new TypedCodeFragment(null, typeInfo);

        switch (f.getFunctionType()) {

            case FUNCTION_TYPE_AGGREGATE:
            case FUNCTION_TYPE_CATALOG: {
                final List<StringBuilder> args = new ArrayList<StringBuilder>(argExprs.length);
                for (int i = 0; i < argExprs.length; i++) {
                    final TypeInfo argTypeInfo = argDefs[i].getTypeInfo();
                    final Class argRuntimeClass = this.getRuntimeClass(argTypeInfo);
                    final TypedCodeFragment argFragment = this.createPropertyExtractionCode(argExprs[i], argRuntimeClass, inputTupleInfoDescriptor);
                    if (null == argRuntimeClass) {
                        args.add(argFragment.getSource());
                    } else if (argRuntimeClass.isPrimitive()) {
                        args.add(this.convert(argFragment, argTypeInfo).getSourceUnboxed());
                    } else {
                        args.add(this.convert(argFragment, argTypeInfo).getSourceBoxed());
                    }
                    fragment.addUsedColumnNames(argFragment);
                }
                TEMPLATE_FOR_FUNCTION_CALL.reset();
                TEMPLATE_FOR_FUNCTION_CALL.setAttribute("fnClassName", f.getImplClassName());
                TEMPLATE_FOR_FUNCTION_CALL.setAttribute("fnName", f.getName());
                TEMPLATE_FOR_FUNCTION_CALL.setAttribute("args", args);
                fragment.setSource(new StringBuilder(TEMPLATE_FOR_FUNCTION_CALL.toString()));
            }
            break;

            case FUNCTION_TYPE_RULE: {
                final Class rfClass = f.getImplClass();
                final String wrapperName = this.qepClassBuilder.addRuleFunctionWrapper(rfClass);
                final List<StringBuilder> args = new ArrayList<StringBuilder>(argExprs.length);
                for (int i = 0; i < argExprs.length; i++) {
                    final TypedCodeFragment argFragment = this.createExtractionCode(argExprs[i], inputTupleInfoDescriptor);
                    final TypeInfo argTypeInfo = argDefs[i].getTypeInfo();
                    final Class argRuntimeClass = this.getRuntimeClass(argTypeInfo);
                    if (null == argRuntimeClass) {
                        args.add(argFragment.getSource());
                    } else {
                        args.add(this.convert(argFragment, argTypeInfo).getSourceBoxed());
                    }
                    fragment.addUsedColumnNames(argFragment);
                }
                TEMPLATE_FOR_RULE_FUNCTION_CALL.reset();
                TEMPLATE_FOR_RULE_FUNCTION_CALL.setAttribute("wrapperName", wrapperName);
                TEMPLATE_FOR_RULE_FUNCTION_CALL.setAttribute("boxedReturnType", TypeHelper.getBoxedClass(this.getRuntimeClass(typeInfo)).getCanonicalName());
                TEMPLATE_FOR_RULE_FUNCTION_CALL.setAttribute("boxedArgs", args);
                fragment.setSource(new StringBuilder(TEMPLATE_FOR_RULE_FUNCTION_CALL.toString()));
                fragment.setTypeInfo(typeInfo.getBoxedTypeInfo(
                        this.qepClassBuilder.getQuery().getQuerySession().getRuleSession().getRuleServiceProvider()
                                .getTypeManager()));
            }
            break;
            default: {
                throw new Exception("Unsupported function type for '" + f.getName() + "': " + f.getFunctionType());
            }
        }

        return fragment;
    }


    protected TypedCodeFragment createExtractionCode(Identifier identifier,
                                                     TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final ModelContext identifiedCtx = identifier.getIdentifiedContext();

        if (identifiedCtx instanceof Expression) {
            return this.createExtractionCode((Expression) identifiedCtx, inputTupleInfoDescriptor);
        }

        if (identifiedCtx instanceof Projection) {
            return this.createExtractionCode(((Projection) identifiedCtx).getExpression(),
                    inputTupleInfoDescriptor);
        }

        if (identifiedCtx instanceof ProxyContext) {
            return this.createExtractionCode((ProxyContext) identifiedCtx, inputTupleInfoDescriptor);
        }

        throw new Exception("Unsupported extraction for: "
                + ((identifiedCtx instanceof QueryContext)
                ? ((QueryContext) identifiedCtx).getSourceString()
                : identifiedCtx.toString()));

    }


    protected TypedCodeFragment createExtractionCode(
            IsSetAttribute attr,
            TupleInfoDescriptor inputTupleInfoDescriptor
    ) throws Exception {
        TypedCodeFragment fragment = null;

        final ModelContext parent = attr.getParentContext();
        if (parent instanceof EntityPropertyProxy) {
            if (!((EntityPropertyProxy) parent).getTypeInfo().isArray()) {
                fragment = this.createExtractionCode(
                        (EntityPropertyProxy) parent, inputTupleInfoDescriptor, PropertyAtom.class);
            }
        } else if (parent instanceof Expression) {
            final Expression e = (Expression) parent;
            if (e.getTypeInfo().isProperty()) {
                fragment = this.createExtractionCode((Expression) parent, inputTupleInfoDescriptor);
            }
        }

        if (null == fragment) {
            throw new Exception("Unsupported use of @isSet: " + parent);
        }

        TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.reset();
        TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.setAttribute("container", fragment);
        TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.setAttribute("attributeName", "isSet");
        fragment.setSource(new StringBuilder(TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.toString()));
        fragment.setTypeInfo(TypeInfoImpl.PRIMITIVE_BOOLEAN);
        return fragment;
    }


    protected TypedCodeFragment createExtractionCode(Literal literal)
            throws Exception {

        if (literal instanceof BooleanLiteral) {
            return new TypedCodeFragment(
                    new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Boolean) (literal.getValue()))),
                    literal.getTypeInfo());
        } else if (literal instanceof CharLiteral) {
            return new TypedCodeFragment(
                    new StringBuilder(this.qepClassBuilder.addLiteral(literal, ((String) (literal.getValue())).charAt(0))),
                    literal.getTypeInfo());
        } else if (literal instanceof DateTimeLiteral) {
            return new TypedCodeFragment(
                    new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Calendar) (literal.getValue()))),
                    literal.getTypeInfo());
        } else if (literal instanceof NullLiteral) {
            return new TypedCodeFragment(
                    new StringBuilder("null"),
                    literal.getTypeInfo());
        } else if (literal instanceof NumberLiteral) {
            switch (((NumberLiteral) literal).getNumberLiteralType()) {
                case NumberLiteral.LT_BYTE:
                    return new TypedCodeFragment(
                            new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Byte) (literal.getValue()))),
                            literal.getTypeInfo());
                case NumberLiteral.LT_DOUBLE:
                    return new TypedCodeFragment(
                            new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Double) (literal.getValue()))),
                            literal.getTypeInfo());
                case NumberLiteral.LT_FLOAT:
                    return new TypedCodeFragment(
                            new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Float) (literal.getValue()))),
                            literal.getTypeInfo());
                case NumberLiteral.LT_INTEGER:
                    return new TypedCodeFragment(
                            new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Integer) (literal.getValue()))),
                            literal.getTypeInfo());
                case NumberLiteral.LT_LONG:
                    return new TypedCodeFragment(
                            new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Long) (literal.getValue()))),
                            literal.getTypeInfo());
                case NumberLiteral.LT_SHORT:
                    return new TypedCodeFragment(
                            new StringBuilder(this.qepClassBuilder.addLiteral(literal, (Short) (literal.getValue()))),
                            literal.getTypeInfo());
            }
        } else if (literal instanceof StringLiteral) {
            return new TypedCodeFragment(
                    new StringBuilder(this.qepClassBuilder.addLiteral(literal, (String) (literal.getValue()))),
                    literal.getTypeInfo());
        }

        throw new Exception("Unsupported literal: " + literal.getSourceString());
    }


    protected TypedCodeFragment createExtractionCode(
            ProxyContext proxy,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {
        return this.createExtractionCode(proxy, inputTupleInfoDescriptor, null);
    }


    protected TypedCodeFragment createExtractionCode(
            ProxyContext proxy,
            TupleInfoDescriptor inputTupleInfoDescriptor,
            Class propertyClass
            ) throws Exception {

        final TypedCodeFragment fromTuple = this.createExtractionCodeFromTuple(proxy, inputTupleInfoDescriptor);
        if (null != fromTuple) {
            return fromTuple;
        }

        if (proxy instanceof ArrayAccessProxy) {
            final ModelContext array = proxy.getParentContext();

            final TypedCodeFragment indexFragment = this.createExtractionCode(
                    ((ArrayAccessProxy) proxy).getArrayIndex(), inputTupleInfoDescriptor);

            final TypedCodeFragment fragment;
            final TypeInfo arrayItemTypeInfo;
            if (array instanceof EntityPropertyProxy) {
                fragment = this.createExtractionCode((EntityPropertyProxy) array, inputTupleInfoDescriptor);
                arrayItemTypeInfo = ((EntityPropertyProxy) array).getTypeInfo().getArrayItemType();
            } else if (array instanceof Expression) {
                fragment = this.createExtractionCode((Expression) array, inputTupleInfoDescriptor);
                arrayItemTypeInfo = ((Expression) array).getTypeInfo().getArrayItemType();
            } else {
                throw new Exception("Unsuported array type: " + array);
            }

            TEMPLATE_FOR_GET_ARRAY_ACCESS.reset();
            TEMPLATE_FOR_GET_ARRAY_ACCESS.setAttribute("valueClassName",
                    this.getRuntimeClass(fragment.getTypeInfo()).getCanonicalName());
            TEMPLATE_FOR_GET_ARRAY_ACCESS.setAttribute("array", fragment);
            TEMPLATE_FOR_GET_ARRAY_ACCESS.setAttribute("index", indexFragment.getSourceUnboxed());
            fragment.setSource(new StringBuilder(TEMPLATE_FOR_GET_ARRAY_ACCESS.toString()));
            fragment.setTypeInfo(arrayItemTypeInfo);
            return fragment;

        } else if (proxy instanceof EntityAttributeProxy) {
            final String attrName = ((EntityAttributeProxy) proxy).getAttribute().getName();

            final TypedCodeFragment fragment = this.createExtractionCode(
                    (ProxyContext) (proxy.getParentContext()), inputTupleInfoDescriptor);

            TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.reset();
            TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.setAttribute("container", fragment);
            TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.setAttribute("attributeName", attrName);
            fragment.set(
                    new StringBuilder(TEMPLATE_FOR_GET_ATTRIBUTE_VALUE.toString()),
                    new TypeInfoImpl(this.getRuntimeClass(proxy)));
            return fragment;

        } else if (proxy instanceof EntityPropertyProxy) {
            final EntityProperty prop = ((EntityPropertyProxy) proxy).getProperty();
            final String propName = Escaper.toJavaStringSource(prop.getName());
            final Class runtimeClass = TypeHelper.getBoxedClass(this.getRuntimeClass(proxy));
            final String className = runtimeClass.getCanonicalName();

            final TypedCodeFragment fragment = this.createExtractionCode((ProxyContext) proxy.getParentContext(),
                    inputTupleInfoDescriptor);

            if (prop.isArray()) {
                TEMPLATE_FOR_GET_PROPERTY_ARRAY.reset();
                TEMPLATE_FOR_GET_PROPERTY_ARRAY.setAttribute("container", fragment);
                TEMPLATE_FOR_GET_PROPERTY_ARRAY.setAttribute("escapedPropertyName", propName);
                fragment.setSource(new StringBuilder(TEMPLATE_FOR_GET_PROPERTY_ARRAY.toString()));

            } else if (Event.class.isAssignableFrom(prop.getEntity().getEntityClass())) {
                TEMPLATE_FOR_GET_PROPERTY.reset();
                TEMPLATE_FOR_GET_PROPERTY.setAttribute("valueClassName", className);
                TEMPLATE_FOR_GET_PROPERTY.setAttribute("container", fragment);
                TEMPLATE_FOR_GET_PROPERTY.setAttribute("escapedPropertyName", propName);
                fragment.setSource(new StringBuilder(TEMPLATE_FOR_GET_PROPERTY.toString()));
            } else if (null == propertyClass) {
                TEMPLATE_FOR_GET_PROPERTY_ATOM_VALUE.reset();
                TEMPLATE_FOR_GET_PROPERTY_ATOM_VALUE.setAttribute("valueClassName", className);
                TEMPLATE_FOR_GET_PROPERTY_ATOM_VALUE.setAttribute("container", fragment);
                TEMPLATE_FOR_GET_PROPERTY_ATOM_VALUE.setAttribute("escapedPropertyName", propName);
                fragment.setSource(new StringBuilder(TEMPLATE_FOR_GET_PROPERTY_ATOM_VALUE.toString()));
            } else {
                TEMPLATE_FOR_GET_PROPERTY_ATOM.reset();
                TEMPLATE_FOR_GET_PROPERTY_ATOM.setAttribute("valueClassName", propertyClass.getCanonicalName());
                TEMPLATE_FOR_GET_PROPERTY_ATOM.setAttribute("container", fragment);
                TEMPLATE_FOR_GET_PROPERTY_ATOM.setAttribute("escapedPropertyName", propName);
                fragment.setSource(new StringBuilder(TEMPLATE_FOR_GET_PROPERTY_ATOM.toString()));
            }
            fragment.setTypeInfo(prop.getTypeInfo().getBoxedTypeInfo(
                    this.qepClassBuilder.getQuery().getQuerySession().getRuleSession()
                            .getRuleServiceProvider().getTypeManager()));
            return fragment;

        } else if (proxy instanceof AliasedIdentifier) {
            throw new Exception("Identifier out of scope: " + ((QueryContext) proxy).getSourceString());
        } else {
            throw new Exception("Item out of scope: " + proxy);
        }
    }


    protected TypedCodeFragment createExtractionCode(UnaryExpression exp,
                                                     TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final Expression arg = (Expression) exp.getChildrenIterator().next();

        switch (exp.getOperator().getOpType()) {

            case Operator.OP_ABS: {
                final TypedCodeFragment fragment = this.createExtractionCode(arg, inputTupleInfoDescriptor);
                fragment.set(
                        new StringBuilder("java.lang.Math.abs(")
                                .append(fragment.getSourceUnboxed())
                                .append(")"),
                        new TypeInfoImpl(TypeHelper.getUnboxedClass(this.getRuntimeClass(exp))));
                return fragment;
            }

            case Operator.OP_GROUP: {
                return this.createExtractionCode(arg, inputTupleInfoDescriptor);
            }

            case Operator.OP_NOT: {
                final TypedCodeFragment fragment = this.createExtractionCode(arg, inputTupleInfoDescriptor);
                fragment.set(
                        new StringBuilder("(!")
                                .append(fragment.getSourceUnboxed())
                                .append(")"),
                        TypeInfoImpl.PRIMITIVE_BOOLEAN);
                return fragment;
            }

            case Operator.OP_UMINUS: {
                final TypedCodeFragment fragment = this.createExtractionCode(arg, inputTupleInfoDescriptor);
                fragment.set(
                        new StringBuilder("(-1*")
                                .append(fragment.getSourceUnboxed())
                                .append(")"),
                        new TypeInfoImpl(TypeHelper.getUnboxedClass(this.getRuntimeClass(exp))));
                return fragment;
            }

        }//switch

        throw new Exception("Unsupported unary expression: " + exp.getSourceString());
    }


    protected TypedCodeFragment createExtractionCodeFromTuple(ModelContext context, TupleInfoDescriptor descriptor) {
        return this.createExtractionCodeFromTuple(context, descriptor, NAME_OF_VARIABLE_TUPLE);
    }


    private TypedCodeFragment createExtractionCodeFromTuple(ModelContext context, TupleInfoDescriptor descriptor,
                                                            String tupleGetterCode) {

        // Tries to find the context directly in a column
        final TupleInfoColumnDescriptor directColumnDescriptor = descriptor.getColumnByModelContext(context);
        if (null != directColumnDescriptor) {
            return new TypedCodeFragment(
                    new StringBuilder(descriptor.getColumnGetterCode(tupleGetterCode, directColumnDescriptor)),
                    directColumnDescriptor.getTypeInfo(),
                    directColumnDescriptor.getName(),
                    directColumnDescriptor.getClassName());
        }

        // Else looks in every column that contains a Tuple
        for (TupleInfoColumnDescriptor columnDescriptor : descriptor.getColumns()) {
            {
                ModelContext ctx = columnDescriptor.getModelContext();
                if (ctx instanceof Projection) {
                    ctx = ((Projection) ctx).getExpression();
                }
                if (ctx instanceof Expression) {
                    try {
                        if (context.equals(((Expression) ctx).getIdentifiedContext())) {
                            return new TypedCodeFragment(
                                    new StringBuilder(
                                            descriptor.getColumnGetterCode(tupleGetterCode, columnDescriptor)),
                                    columnDescriptor.getTypeInfo(),
                                    columnDescriptor.getName(),
                                    columnDescriptor.getClassName());
                        }
                    } catch (ResolveException shouldNotHappen) {
                        shouldNotHappen.printStackTrace();
                    }
                }
            }
            final TupleInfoDescriptor containedTupleDescriptor = columnDescriptor.getTupleInfoDescriptor();
            if (null != containedTupleDescriptor) {
                final TypedCodeFragment fragment = this.createExtractionCodeFromTuple(context,
                        containedTupleDescriptor,
                        descriptor.getColumnGetterCode(tupleGetterCode, columnDescriptor));
                if (null != fragment) {
                    fragment.getUsedColumnNames().clear();
                    fragment.addUsedColumnName(columnDescriptor.getName(), columnDescriptor.getClassName());
                    return fragment;
                }
            }
        }

        return null;
    }


    protected TypedCodeFragment createInExtractionCode(BinaryExpression exp, TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {
        final TypedCodeFragment fragment = this.createExtractionCode(exp.getLeftExpression(), inputTupleInfoDescriptor);

        final StringBuilder s = new StringBuilder("(");
        boolean isFirst = true;
        for (Iterator it = exp.getRightExpression().getChildrenIterator(); it.hasNext();) {
            if (isFirst) {
                isFirst = false;
            } else {
                s.append(" || ");
            }
            final Expression member = (Expression) it.next();
            final TypedCodeFragment memberFragment = this.createExtractionCode(member, inputTupleInfoDescriptor);
            fragment.addUsedColumnNames(memberFragment);
            TEMPLATE_FOR_GET_EQUAL_TO.reset();
            // Todo: optimize to avoid using the whole left arg extraction source each time.
            TEMPLATE_FOR_GET_EQUAL_TO.setAttribute("arg1", fragment.getSourceBoxed());
            TEMPLATE_FOR_GET_EQUAL_TO.setAttribute("arg2", memberFragment.getSourceBoxed());
            s.append(TEMPLATE_FOR_GET_EQUAL_TO.toString());
        }
        s.append(")");
        fragment.setSource(s);
        fragment.setTypeInfo(TypeInfoImpl.PRIMITIVE_BOOLEAN);

        return fragment;
    }


    protected TypedCodeFragment createPropertyExtractionCode(
            Expression expr,
            Class argRuntimeClass,
            TupleInfoDescriptor inputTupleInfoDescriptor) throws Exception {
        if ((null != argRuntimeClass) && PropertyAtom.class.isAssignableFrom(argRuntimeClass)) {
            if (expr instanceof ProxyContext) {
                return this.createExtractionCode(
                        (ProxyContext) expr, inputTupleInfoDescriptor, argRuntimeClass);
            } else if (expr.getIdentifiedContext() instanceof ProxyContext) {
                return this.createExtractionCode(
                        (ProxyContext) expr.getIdentifiedContext(), inputTupleInfoDescriptor, argRuntimeClass);
            }
        }
        return this.createExtractionCode(expr, inputTupleInfoDescriptor);
    }



    private String getBodyOfExpressionEvaluator(StringBuilder extractionSource) {
        TEMPLATE_FOR_BODY_OF_EXPRESSION_EVALUATE.reset();
        TEMPLATE_FOR_BODY_OF_EXPRESSION_EVALUATE.setAttribute("mapName", NAME_OF_VARIABLE_TUPLE);
        TEMPLATE_FOR_BODY_OF_EXPRESSION_EVALUATE.setAttribute("queryContextName", NAME_OF_VARIABLE_QUERYCONTEXT);
        TEMPLATE_FOR_BODY_OF_EXPRESSION_EVALUATE.setAttribute("expressionCode", extractionSource);
        return TEMPLATE_FOR_BODY_OF_EXPRESSION_EVALUATE.toString();
    }


    private String getBodyOfTupleValueExtractor(StringBuilder extractionSource) {
        TEMPLATE_FOR_BODY_OF_TVE_EXTRACT.reset();
        TEMPLATE_FOR_BODY_OF_TVE_EXTRACT.setAttribute("tupleName", NAME_OF_VARIABLE_TUPLE);
        TEMPLATE_FOR_BODY_OF_TVE_EXTRACT.setAttribute("queryContextName", NAME_OF_VARIABLE_QUERYCONTEXT);
        TEMPLATE_FOR_BODY_OF_TVE_EXTRACT.setAttribute("expressionCode", extractionSource);
        return TEMPLATE_FOR_BODY_OF_TVE_EXTRACT.toString();
    }


    private Class getRuntimeClass(TypedContext typedCtx) throws Exception {
        return this.getRuntimeClass(typedCtx.getTypeInfo());
    }


    private Class getRuntimeClass(TypeInfo typeInfo) throws Exception {
        return typeInfo.getRuntimeClass(
                this.qepClassBuilder.getQuery().getQuerySession().getRuleSession().getRuleServiceProvider()
                        .getTypeManager()
        );
    }

}
