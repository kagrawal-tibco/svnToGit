package com.tibco.cep.query.exec.impl;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.exception.ResolveException;
import com.tibco.cep.query.exec.descriptors.TupleInfoColumnDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.impl.*;
import com.tibco.cep.query.model.*;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.ConstantValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.TupleContainerExtractor;
import com.tibco.cep.query.stream.impl.expression.TupleExtractor;
import com.tibco.cep.query.stream.impl.expression.TupleExtractorToEvaluatorAdapter;
import com.tibco.cep.query.stream.impl.expression.array.*;
import com.tibco.cep.query.stream.impl.expression.attribute.*;
import com.tibco.cep.query.stream.impl.expression.bindvar.BindVariableEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.AndEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.NotEvaluator;
import com.tibco.cep.query.stream.impl.expression.bool.OrEvaluator;
import com.tibco.cep.query.stream.impl.expression.comparison.*;
import com.tibco.cep.query.stream.impl.expression.conversion.CastEvaluator;
import com.tibco.cep.query.stream.impl.expression.function.RuleFunctionEvaluator;
import com.tibco.cep.query.stream.impl.expression.function.StaticFunctionEvaluator;
import com.tibco.cep.query.stream.impl.expression.numeric.*;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyArrayEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.SimpleEventPropertyValueEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.ConcatenationEvaluator;
import com.tibco.cep.query.stream.impl.expression.string.LikeEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.PropertyAtom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jul 28, 2008
 * Time: 6:17:32 PM
 */


public class EvaluatorDescriptorFactory {


    protected final TypeManager typeManager;
    private ConversionHelper conversionHelper;


    /**
     * @param typeManager TypeManager used to find runtime classes.
     */
    public EvaluatorDescriptorFactory(
            TypeManager typeManager) {

        this.typeManager = typeManager;
        this.conversionHelper = new ConversionHelper(typeManager);
    }


    protected EvaluatorDescriptor composeBetweenExtractionCode(
            BinaryExpression exp,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {
        final EvaluatorDescriptor tested = this.composeEvaluator(exp.getLeftExpression(), inputTupleInfoDescriptor);

        final Iterator it = exp.getRightExpression().getChildrenIterator();
        final EvaluatorDescriptor bound1 = this.composeEvaluator((Expression) it.next(), inputTupleInfoDescriptor);
        final EvaluatorDescriptor bound2 = this.composeEvaluator((Expression) it.next(), inputTupleInfoDescriptor);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new BetweenEvaluator(tested.getEvaluator(), bound1.getEvaluator(), bound2.getEvaluator()),
                TypeInfoImpl.PRIMITIVE_BOOLEAN);
        d.addUsedColumnNames(tested);
        d.addUsedColumnNames(bound1);
        d.addUsedColumnNames(bound2);
        return d;
    }


    protected EvaluatorDescriptor composeCastExtractionCode(
            BinaryExpression exp,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {
        final EvaluatorDescriptor left = this.composeEvaluator(exp.getLeftExpression(), inputTupleInfoDescriptor);
        final TypeExpression typeExp = (TypeExpression) exp.getRightExpression();
        final TypeInfo typeInfo = typeExp.getInstancesTypeInfo();
        final Class targetClass = this.getRuntimeClass(typeInfo);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new CastEvaluator(left.getEvaluator(), targetClass),
                typeInfo);
        d.addUsedColumnNames(left);
        return d;
    }


    protected EvaluatorDescriptor composeEvaluator(
            ArrayAccessProxy proxy,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final ModelContext array = proxy.getParentContext();

        final EvaluatorDescriptor indexDescriptor = this.composeEvaluator(proxy.getArrayIndex(),
                inputTupleInfoDescriptor);

        final EvaluatorDescriptor arrayDescriptor;
        final TypeInfo arrayItemTypeInfo;
        if (array instanceof EntityPropertyProxy) {
            arrayDescriptor = this.composeEvaluator((EntityPropertyProxy) array, inputTupleInfoDescriptor);
            arrayItemTypeInfo = ((EntityPropertyProxy) array).getTypeInfo().getArrayItemType();
        } else if (array instanceof Expression) {
            arrayDescriptor = this.composeEvaluator((Expression) array, inputTupleInfoDescriptor);
            arrayItemTypeInfo = ((Expression) array).getTypeInfo().getArrayItemType();
        } else {
            throw new Exception("Unsuported array type: " + array);
        }

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new PropertyArrayItemEvaluator(arrayDescriptor.getEvaluator(), indexDescriptor.getEvaluator()),
                arrayItemTypeInfo);
        d.addUsedColumnNames(arrayDescriptor);
        d.addUsedColumnNames(indexDescriptor);
        return d;
    }


    protected EvaluatorDescriptor composeEvaluator(
            ArrayLengthAttribute lengthAttr,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final ModelContext array = lengthAttr.getParentContext();

        final EvaluatorDescriptor d;
        if (array instanceof EntityPropertyProxy) {
            final EvaluatorDescriptor arrayDescriptor = this.composeEvaluator((EntityPropertyProxy) array,
                    inputTupleInfoDescriptor);
            final ExpressionEvaluator e = new PropertyArrayLengthAttributeEvaluator(arrayDescriptor.getEvaluator());
            d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_INTEGER);
            d.addUsedColumnNames(arrayDescriptor);

        } else if (array instanceof Expression) {
            final EvaluatorDescriptor arrayDescriptor =
                    this.composeEvaluator((Expression) array, inputTupleInfoDescriptor);
            final ExpressionEvaluator e = new SimpleArrayLengthAttributeEvaluator(arrayDescriptor.getEvaluator());
            d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_INTEGER);
            d.addUsedColumnNames(arrayDescriptor);

        } else {
            throw new Exception("Unsuported array type: " + array);
        }
        return d;
    }


    protected EvaluatorDescriptor composeEvaluator(
            BinaryExpression binExp,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final int opType = binExp.getOperator().getOpType();

        switch (opType) {

            // =
            case Operator.OP_EQ: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final ExpressionEvaluator e;
                if (((null == this.getRuntimeClass(left.getTypeInfo())) && (null == this
                        .getRuntimeClass(right.getTypeInfo())))
                        || (left.getEvaluator().equals(right.getEvaluator()))) {
                    e = ConstantValueEvaluator.TRUE;

                } else {
                    e = new EqualityEvaluator(left.getEvaluator(), right.getEvaluator());
                }

                final EvaluatorDescriptor d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // !=
            case Operator.OP_NE: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final ExpressionEvaluator e;
                if (((null == this.getRuntimeClass(left.getTypeInfo())) && (null == this
                        .getRuntimeClass(right.getTypeInfo())))
                        || (left.getEvaluator().equals(right.getEvaluator()))) {
                    e = ConstantValueEvaluator.FALSE;

                } else {
                    e = new InequalityEvaluator(left.getEvaluator(), right.getEvaluator());
                }

                final EvaluatorDescriptor d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // >=
            case Operator.OP_GE: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final ExpressionEvaluator e;
                if ((null == this.getRuntimeClass(left.getTypeInfo()))
                        || (null == this.getRuntimeClass(right.getTypeInfo()))) {
                    e = ConstantValueEvaluator.FALSE;

                } else if (left.getEvaluator().equals(right.getEvaluator())) {
                    e = ConstantValueEvaluator.TRUE;

                } else {
                    e = new GreaterThanOrEqualToEvaluator(left.getEvaluator(), right.getEvaluator());
                }

                final EvaluatorDescriptor d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // >
            case Operator.OP_GT: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final ExpressionEvaluator e;
                if ((null == this.getRuntimeClass(left.getTypeInfo()))
                        || (null == this.getRuntimeClass(right.getTypeInfo()))
                        || (left.getEvaluator().equals(right.getEvaluator()))) {
                    e = ConstantValueEvaluator.FALSE;

                } else {
                    e = new GreaterThanEvaluator(left.getEvaluator(), right.getEvaluator());
                }

                final EvaluatorDescriptor d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // <=
            case Operator.OP_LE: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final ExpressionEvaluator e;
                if ((null == this.getRuntimeClass(left.getTypeInfo()))
                        || (null == this.getRuntimeClass(right.getTypeInfo()))) {
                    e = ConstantValueEvaluator.FALSE;

                } else if (left.getEvaluator().equals(right.getEvaluator())) {
                    e = ConstantValueEvaluator.TRUE;

                } else {
                    // (a <= b) = (b >= a)
                    e = new GreaterThanOrEqualToEvaluator(right.getEvaluator(), left.getEvaluator());
                }

                final EvaluatorDescriptor d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // <
            case Operator.OP_LT: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final ExpressionEvaluator e;
                if ((null == this.getRuntimeClass(left.getTypeInfo()))
                        || (null == this.getRuntimeClass(right.getTypeInfo()))
                        || (left.getEvaluator().equals(right.getEvaluator()))) {
                    e = ConstantValueEvaluator.FALSE;

                } else {
                    // (a < b) = (b > a)
                    e = new GreaterThanEvaluator(right.getEvaluator(), left.getEvaluator());
                }

                final EvaluatorDescriptor d = new EvaluatorDescriptor(e, TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            case Operator.OP_AND: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new AndEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            case Operator.OP_OR: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new OrEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_BOOLEAN);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // %
            case Operator.OP_MOD: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new ModuloEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_DOUBLE);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // +
            case Operator.OP_PLUS: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new AdditionEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_DOUBLE);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // -
            case Operator.OP_MINUS: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new SubtractionEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_DOUBLE);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // *
            case Operator.OP_MULTIPLY: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new MultiplicationEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_DOUBLE);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // /
            case Operator.OP_DIVIDE: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new DivisionEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_DOUBLE);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // ||
            case Operator.OP_CONCAT: {
                final EvaluatorDescriptor left =
                        this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                final EvaluatorDescriptor right =
                        this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);

                final EvaluatorDescriptor d = new EvaluatorDescriptor(
                        new ConcatenationEvaluator(left.getEvaluator(), right.getEvaluator()),
                        TypeInfoImpl.STRING);
                d.addUsedColumnNames(left);
                d.addUsedColumnNames(right);
                return d;
            }

            // Object
            case Operator.OP_AT:
            case Operator.OP_DOT: {
                final ModelContext ctx = binExp.getIdentifiedContext();
                if (ctx instanceof ProxyContext) {
                    return this.composeEvaluator((ProxyContext) ctx, inputTupleInfoDescriptor);
                } else if (ctx instanceof ArrayLengthAttribute) {
                    return composeEvaluator((ArrayLengthAttribute) ctx, inputTupleInfoDescriptor);
                } else if (ctx instanceof IsSetAttribute) {
                    return composeEvaluator((IsSetAttribute) ctx, inputTupleInfoDescriptor);
                }
                break;
            }

            // ARRAY
            case Operator.OP_ARRAY: {
                final ModelContext ctx = binExp.getIdentifiedContext();
                if (ctx instanceof ArrayAccessProxy) {
                    return this.composeEvaluator((ArrayAccessProxy) ctx, inputTupleInfoDescriptor);
                } else {
                    final EvaluatorDescriptor left =
                            this.composeEvaluator(binExp.getLeftExpression(), inputTupleInfoDescriptor);
                    final EvaluatorDescriptor right =
                            this.composeEvaluator(binExp.getRightExpression(), inputTupleInfoDescriptor);
                    if (ctx instanceof Expression) {
                        final TypeInfo itemTypeInfo = ((Expression) ctx).getTypeInfo();
                        final Class resultType = this.getRuntimeClass(itemTypeInfo);
                        final ExpressionEvaluator e;
                        if (boolean.class.equals(resultType)) {
                            e = new BooleanArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else if (byte.class.equals(resultType)) {
                            e = new ByteArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else if (char.class.equals(resultType)) {
                            e = new CharArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else if (double.class.equals(resultType)) {
                            e = new DoubleArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else if (float.class.equals(resultType)) {
                            e = new FloatArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else if (int.class.equals(resultType)) {
                            e = new IntArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else if (long.class.equals(resultType)) {
                            e = new LongArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else if (short.class.equals(resultType)) {
                            e = new ShortArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        } else {
                            e = new ObjectArrayItemEvaluator(left.getEvaluator(), right.getEvaluator());
                        }
                        final EvaluatorDescriptor d = new EvaluatorDescriptor(e, itemTypeInfo);
                        d.addUsedColumnNames(left);
                        d.addUsedColumnNames(right);
                        return d;
                    }
                }
                break;
            }

            // IN
            case Operator.OP_IN: {
                return this.composeInEvaluator(binExp, inputTupleInfoDescriptor);
            }

            // BETWEEN
            case Operator.OP_BETWEEN: {
                return this.composeBetweenExtractionCode(binExp, inputTupleInfoDescriptor);
            }

            // Cast
            case Operator.OP_CAST: {
                return this.composeCastExtractionCode(binExp, inputTupleInfoDescriptor);
            }

            // Like
            case Operator.OP_LIKE: {
                return this.composeLikeEvaluator(binExp, inputTupleInfoDescriptor);
            }
        }

        throw new Exception("Unsupported operator '" + binExp.getOperator()
                + "' in the binary expression: " + binExp.getSourceString());
    }

    private EvaluatorDescriptor composeLikeEvaluator(
            BinaryExpression binaryExpression, TupleInfoDescriptor tupleInfoDescriptor) throws Exception {
        
        EvaluatorDescriptor left = composeEvaluator(binaryExpression.getLeftExpression(), tupleInfoDescriptor);
        ExpressionEvaluator leftEvaluator = left.getEvaluator();

        ExpressionEvaluator rightEvaluator =
                composeEvaluator(binaryExpression.getRightExpression(), tupleInfoDescriptor).getEvaluator();

        EvaluatorDescriptor d =
                new EvaluatorDescriptor(new LikeEvaluator(leftEvaluator, rightEvaluator), TypeInfoImpl.PRIMITIVE_BOOLEAN);
        d.addUsedColumnNames(left);
        
        return d;
    }

    protected EvaluatorDescriptor composeEvaluator(
            BindVariable bindingVariable)
            throws Exception {

        return new EvaluatorDescriptor(
                new BindVariableEvaluator(bindingVariable.getLabel()),
                bindingVariable.getTypeInfo());
    }


    protected EvaluatorDescriptor composeEvaluator(
            EntityAttributeProxy proxy,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {
        final String attrName = proxy.getAttribute().getName();
        final Class attrClass = this.getRuntimeClass(proxy);

        final EvaluatorDescriptor containerDescriptor = this.composeEvaluator(
                (ProxyContext) (proxy.getParentContext()), inputTupleInfoDescriptor);

        final EvaluatorDescriptor d;
        if ("closure".equals(attrName)) {
            d = new EvaluatorDescriptor(new ClosureAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.OBJECT);
        } else if ("extId".equals(attrName)) {
            d = new EvaluatorDescriptor(new ExtIdAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.STRING);
        } else if ("id".equals(attrName)) {
            d = new EvaluatorDescriptor(new IdAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_LONG);
        } else if ("interval".equals(attrName)) {
            d = new EvaluatorDescriptor(new IntervalAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_LONG);
        } else if ("isSet".equals(attrName)) {
            d = new EvaluatorDescriptor(new IsSetAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
        } else if ("length".equals(attrName)) {
            final TypeInfo typeInfo = containerDescriptor.getTypeInfo();
            if (typeInfo.isPropertyArray()) {
                d = new EvaluatorDescriptor(
                        new PropertyArrayLengthAttributeEvaluator(containerDescriptor.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_INTEGER);
            } else if (typeInfo.isArray()) {
                d = new EvaluatorDescriptor(new SimpleArrayLengthAttributeEvaluator(containerDescriptor.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_INTEGER);
            } else {
                throw new Exception("Attempted to use @length on non-array.");
            }
        } else if ("parent".equals(attrName)) {
            d = new EvaluatorDescriptor(new ParentAttributeEvaluator(containerDescriptor.getEvaluator()),
                    new TypeInfoImpl(attrClass));
        } else if ("payload".equals(attrName)) {
            d = new EvaluatorDescriptor(new PayloadAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.STRING);
        } else if ("scheduledTime".equals(attrName)) {
            d = new EvaluatorDescriptor(new ScheduledTimeAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.DATETIME);
        } else if ("ttl".equals(attrName)) {
            d = new EvaluatorDescriptor(new TtlAttributeEvaluator(containerDescriptor.getEvaluator()),
                    TypeInfoImpl.LONG);
        } else {
            throw new Exception("Unsupported attribute: " + attrName);
        }

        d.addUsedColumnNames(containerDescriptor);
        return d;
    }


    protected EvaluatorDescriptor composeEvaluator(
            EntityPropertyProxy proxy,
            TupleInfoDescriptor inputTupleInfoDescriptor,
            Class propertyClass)
            throws Exception {
        final EntityProperty prop = proxy.getProperty();
        final String propName = prop.getName();
        final TypeInfo typeInfo = prop.getTypeInfo().getBoxedTypeInfo(this.typeManager);

        final EvaluatorDescriptor containerDescriptor = this.composeEvaluator(
                (ProxyContext) proxy.getParentContext(), inputTupleInfoDescriptor);

        final EvaluatorDescriptor d;
        if (prop.isArray()) {
            d = new EvaluatorDescriptor(
                    new ConceptPropertyArrayEvaluator(containerDescriptor.getEvaluator(), propName),
                    typeInfo);

        } else if (Event.class.isAssignableFrom(prop.getEntity().getEntityClass())) {
            d = new EvaluatorDescriptor(
                    new SimpleEventPropertyValueEvaluator(containerDescriptor.getEvaluator(), propName),
                    typeInfo);

        } else if (null == propertyClass) {
            d = new EvaluatorDescriptor(
                    new ConceptPropertyAtomValueEvaluator(containerDescriptor.getEvaluator(), propName),
                    typeInfo);

        } else {
            d = new EvaluatorDescriptor(
                    new ConceptPropertyAtomEvaluator(containerDescriptor.getEvaluator(), propName),
                    typeInfo);
        }

        d.addUsedColumnNames(containerDescriptor);
        return d;
    }


    protected EvaluatorDescriptor composeEvaluator(
            Expression expression,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        if (expression instanceof Literal) {
            return this.composeEvaluator((Literal) expression);
        }

        final EvaluatorDescriptor fromTuple = this.composeEvaluatorFromTuple(expression, inputTupleInfoDescriptor);
        if (null != fromTuple) {
            return fromTuple;
        }

        if (expression instanceof BinaryExpression) {
            return this.composeEvaluator((BinaryExpression) expression, inputTupleInfoDescriptor);

        }

        if (expression instanceof BindVariable) {
            return this.composeEvaluator((BindVariable) expression);
        }

        if (expression instanceof FunctionIdentifier) {
            return this.composeEvaluator((FunctionIdentifier) expression, inputTupleInfoDescriptor);
        }

        if (expression instanceof Identifier) {
            return this.composeEvaluator((Identifier) expression, inputTupleInfoDescriptor);
        }

        if (expression instanceof ProxyContext) {
            return this.composeEvaluator((ProxyContext) expression, inputTupleInfoDescriptor);
        }

        if (expression instanceof UnaryExpression) {
            return this.composeEvaluator((UnaryExpression) expression, inputTupleInfoDescriptor);
        }

//        if (expression instanceof ScopedIdentifier) {
//            return this.composeEvaluator((ScopedIdentifier) expression, inputTupleInfoDescriptor);
//        }

        throw new Exception("Unsupported expression: " + expression.getSourceString());
    }


    protected EvaluatorDescriptor composeEvaluator(
            FunctionIdentifier functionIdentifier,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final Function f = (Function) functionIdentifier.getIdentifiedContext();
        final Class fClass = f.getImplClass();
        final FunctionArg[] argDefs = f.getArguments();
        final Expression[] argExprs = functionIdentifier.getArguments();
        final TypeInfo typeInfo = f.getTypeInfo();

        final Class[] argClasses = new Class[argDefs.length];
        final EvaluatorDescriptor[] argDescriptors = new EvaluatorDescriptor[argExprs.length];
        final ExpressionEvaluator[] argEvaluators = new ExpressionEvaluator[argExprs.length];
        for (int i = 0; i < argExprs.length; i++) {
            final FunctionArg argDef = argDefs[i];
            argClasses[i] = this.getRuntimeClass(argDef);

            if(argExprs[i] instanceof NullLiteral){
                continue;
            }
            EvaluatorDescriptor argDescriptor = this.composeEvaluator(argExprs[i], inputTupleInfoDescriptor);
            argDescriptor = this.conversionHelper.convert(argDescriptor, argDef.getTypeInfo());
            argDescriptors[i] = argDescriptor;
            argEvaluators[i] = argDescriptor.getEvaluator();
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
                e = new RuleFunctionEvaluator(fClass, argEvaluators, f.getPath());
            }
            break;

            default: {
                throw new Exception("Unsupported function type for '" + f.getName() + "': " + f.getFunctionType());
            }
        }

        final EvaluatorDescriptor d = new EvaluatorDescriptor(e, typeInfo);
        for (EvaluatorDescriptor argDescriptor : argDescriptors) {
            d.addUsedColumnNames(argDescriptor);
        }
        return d;
    }


    protected EvaluatorDescriptor composeEvaluator(
            Identifier identifier,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final ModelContext identifiedCtx = identifier.getIdentifiedContext();

        if (identifiedCtx instanceof Expression) {
            return this.composeEvaluator((Expression) identifiedCtx, inputTupleInfoDescriptor);
        }

        if (identifiedCtx instanceof Projection) {
            return this.composeEvaluator(((Projection) identifiedCtx).getExpression(),
                    inputTupleInfoDescriptor);
        }

        if (identifiedCtx instanceof ProxyContext) {
            return this.composeEvaluator((ProxyContext) identifiedCtx, inputTupleInfoDescriptor);
        }

        throw new Exception("Unsupported extraction for: "
                + ((identifiedCtx instanceof QueryContext)
                ? ((QueryContext) identifiedCtx).getSourceString()
                : identifiedCtx.toString()));

    }


    protected EvaluatorDescriptor composeEvaluator(
            IsSetAttribute attr,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        EvaluatorDescriptor propertyEvaluatorDescriptor = null;

        final ModelContext parent = attr.getParentContext();
        if (parent instanceof EntityPropertyProxy) {
            if (!((EntityPropertyProxy) parent).getTypeInfo().isArray()) {
                propertyEvaluatorDescriptor = this.composeEvaluator(
                        (EntityPropertyProxy) parent, inputTupleInfoDescriptor, PropertyAtom.class);
            }
        } else if (parent instanceof Expression) {
            final Expression e = (Expression) parent;
            if (e.getTypeInfo().isProperty()) {
                propertyEvaluatorDescriptor = this.composeEvaluator((Expression) parent, inputTupleInfoDescriptor);
            }
        }

        if (null == propertyEvaluatorDescriptor) {
            throw new Exception("Unsupported use of @isSet with: " + parent);
        }

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new IsSetAttributeEvaluator(propertyEvaluatorDescriptor.getEvaluator()),
                TypeInfoImpl.PRIMITIVE_BOOLEAN);
        d.addUsedColumnNames(propertyEvaluatorDescriptor);
        return d;
    }


    protected EvaluatorDescriptor composeEvaluator(
            Literal literal)
            throws Exception {

        if (literal instanceof NullLiteral) {
            return new EvaluatorDescriptor(ConstantValueEvaluator.NULL, literal.getTypeInfo());
        } else {
            return new EvaluatorDescriptor(new ConstantValueEvaluator(literal.getValue()), literal.getTypeInfo());
        }
    }


    protected EvaluatorDescriptor composeEvaluator(
            ProxyContext proxy,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        return this.composeEvaluator(proxy, inputTupleInfoDescriptor, null);
    }


    protected EvaluatorDescriptor composeEvaluator(
            ProxyContext proxy,
            TupleInfoDescriptor inputTupleInfoDescriptor,
            Class propertyClass)
            throws Exception {

        final EvaluatorDescriptor fromTuple = this.composeEvaluatorFromTuple(proxy, inputTupleInfoDescriptor);
        if (null != fromTuple) {
            return fromTuple;
        }

        if (proxy instanceof ArrayAccessProxy) {
            return composeEvaluator((ArrayAccessProxy) proxy, inputTupleInfoDescriptor);

        } else if (proxy instanceof EntityAttributeProxy) {
            return composeEvaluator((EntityAttributeProxy) proxy, inputTupleInfoDescriptor);

        } else if (proxy instanceof EntityPropertyProxy) {
            return composeEvaluator((EntityPropertyProxy) proxy, inputTupleInfoDescriptor, propertyClass);

        } else if (proxy instanceof AliasedIdentifier) {
            throw new Exception("Identifier out of scope: " + ((QueryContext) proxy).getSourceString());

        } else {
            throw new Exception("Item out of scope: " + proxy);
        }
    }


    protected EvaluatorDescriptor composeEvaluator(
            UnaryExpression exp,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final Expression arg = (Expression) exp.getChildrenIterator().next();

        switch (exp.getOperator().getOpType()) {

            case Operator.OP_ABS: {
                final EvaluatorDescriptor argDescriptor = this.composeEvaluator(arg, inputTupleInfoDescriptor);
                final Class argClass = TypeHelper.getUnboxedClass(this.getRuntimeClass(exp));
                return new EvaluatorDescriptor(
                        new AbsoluteValueEvaluator(argDescriptor.getEvaluator()),
                        new TypeInfoImpl(argClass));
            }

            case Operator.OP_GROUP: {
                return this.composeEvaluator(arg, inputTupleInfoDescriptor);
            }

            case Operator.OP_NOT: {
                final EvaluatorDescriptor argDescriptor = this.composeEvaluator(arg, inputTupleInfoDescriptor);
                return new EvaluatorDescriptor(
                        new NotEvaluator(argDescriptor.getEvaluator()),
                        TypeInfoImpl.PRIMITIVE_BOOLEAN);
            }

            case Operator.OP_UMINUS: {
                final EvaluatorDescriptor argDescriptor = this.composeEvaluator(arg, inputTupleInfoDescriptor);
                final Class argClass = TypeHelper.getUnboxedClass(this.getRuntimeClass(exp));
                return new EvaluatorDescriptor(
                        new SignInversionEvaluator(argDescriptor.getEvaluator()),
                        new TypeInfoImpl(argClass));
            }

        }//switch

        throw new Exception("Unsupported unary expression: " + exp.getSourceString());
    }


    protected EvaluatorDescriptor composeEvaluatorFromTuple(
            ModelContext context,
            TupleInfoDescriptor tupleInfoDescriptor) {

        // Looks in every column that contains a Tuple
        int columnIndex = 0;
        for (final TupleInfoColumnDescriptor columnDescriptor : tupleInfoDescriptor.getColumns()) {

            final TupleInfoDescriptor containedTupleDescriptor = columnDescriptor.getTupleInfoDescriptor();
            if (null != containedTupleDescriptor) {
                // This column contains a tuple.

                final TupleExtractorDescriptor found =
                        this.composeExtractorFromTuple(context, containedTupleDescriptor);
                if (null != found) {
                    // The data can be extracted from the column tuple.

                    final ExpressionEvaluator adapter = new TupleExtractorToEvaluatorAdapter(found.getTupleExtractor(),columnDescriptor.getName());
                    final EvaluatorDescriptor result = new EvaluatorDescriptor(adapter, found.getTypeInfo());
                    result.addUsedColumnName(columnDescriptor.getName(), columnDescriptor.getClassName());
                    return result;

                }
            }
            columnIndex++;
        }

        return null;
    }


    protected TupleExtractorDescriptor composeExtractorFromTuple(
            ModelContext context,
            TupleInfoDescriptor descriptor) {

        // Tries to find the context directly in a column
        final TupleInfoColumnDescriptor directColumnDescriptor = descriptor.getColumnByModelContext(context);
        if (null != directColumnDescriptor) {
            return descriptor.getColumnExtractor(directColumnDescriptor);
        }

        // Looks in every column that contains a Projection, an Expression, or a Tuple
        int columnIndex = 0;
        for (final TupleInfoColumnDescriptor columnDescriptor : descriptor.getColumns()) {

            ModelContext ctx = columnDescriptor.getModelContext();
            if (ctx instanceof Projection) {
                ctx = ((Projection) ctx).getExpression();
            }
            if (ctx instanceof Expression) {
                try {
                    if (context.equals(((Expression) ctx).getIdentifiedContext())) {
                        return descriptor.getColumnExtractor(columnDescriptor);
                    }
                } catch (ResolveException shouldNotHappen) {
                    shouldNotHappen.printStackTrace();
                }
            }

            final TupleInfoDescriptor containedTupleDescriptor = columnDescriptor.getTupleInfoDescriptor();
            if (null != containedTupleDescriptor) {
                // This column contains a tuple.

                final TupleExtractorDescriptor found =
                        this.composeExtractorFromTuple(context, containedTupleDescriptor);
                if (null != found) {
                    // The data can be extracted from the column tuple.

                    final TupleExtractor container = new TupleContainerExtractor(
                            found.getTupleExtractor(), columnIndex);

                    final TupleExtractorDescriptor result = new TupleExtractorDescriptor(
                            container, found.getTypeInfo());
                    result.addUsedColumnName(columnDescriptor.getName(), columnDescriptor.getClassName());
                    return result;
                }
            }
            columnIndex++;
        }

        return null;
    }


    protected EvaluatorDescriptor composeInEvaluator(
            BinaryExpression exp,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        final EvaluatorDescriptor tested = this.composeEvaluator(exp.getLeftExpression(), inputTupleInfoDescriptor);

        final List<EvaluatorDescriptor> memberDescriptors = new ArrayList<EvaluatorDescriptor>();
        final List<ExpressionEvaluator> memberEvaluators = new ArrayList<ExpressionEvaluator>();
        for (Iterator it = exp.getRightExpression().getChildrenIterator(); it.hasNext();) {
            final Expression memberExpr = (Expression) it.next();
            final EvaluatorDescriptor memberDescriptor = this.composeEvaluator(memberExpr, inputTupleInfoDescriptor);
            memberDescriptors.add(memberDescriptor);
            memberEvaluators.add(memberDescriptor.getEvaluator());
        }
        final ExpressionEvaluator[] membersArray = memberEvaluators.toArray(
                new ExpressionEvaluator[memberEvaluators.size()]);

        final EvaluatorDescriptor d = new EvaluatorDescriptor(
                new InEvaluator(tested.getEvaluator(), membersArray),
                TypeInfoImpl.PRIMITIVE_BOOLEAN);
        d.addUsedColumnNames(tested);
        for (EvaluatorDescriptor member : memberDescriptors) {
            d.addUsedColumnNames(member);
        }
        return d;
    }


    protected EvaluatorDescriptor composePropertyExtractionCode(
            Expression expr,
            Class argRuntimeClass,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        if ((null != argRuntimeClass) && PropertyAtom.class.isAssignableFrom(argRuntimeClass)) {
            if (expr instanceof ProxyContext) {
                return this.composeEvaluator(
                        (ProxyContext) expr, inputTupleInfoDescriptor, argRuntimeClass);
            } else if (expr.getIdentifiedContext() instanceof ProxyContext) {
                return this.composeEvaluator(
                        (ProxyContext) expr.getIdentifiedContext(), inputTupleInfoDescriptor, argRuntimeClass);
            }
        }
        return this.composeEvaluator(expr, inputTupleInfoDescriptor);
    }


    private Class getRuntimeClass(TypedContext typedCtx)
            throws Exception {
        return this.getRuntimeClass(typedCtx.getTypeInfo());
    }


    private Class getRuntimeClass(TypeInfo typeInfo)
            throws Exception {
        return typeInfo.getRuntimeClass(this.typeManager);
    }


    /**
     * Creates an ExpressionEvaluator for a given Expression, and returns its descriptor.
     *
     * @param expression               Expression to get an evaluator for.
     * @param inputTupleInfoDescriptor TupleInfoDescriptorImpl of the tuples available to the TVE.
     * @return EvaluatorDescriptor
     * @throws Exception upon problem.
     */
    public EvaluatorDescriptor newEvaluatorDescriptor(
            Expression expression,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        if (inputTupleInfoDescriptor instanceof AliasMapDescriptor) {
            return this.composeEvaluator(expression, inputTupleInfoDescriptor);
        }

        final TupleInfoDescriptor wrapper = new TupleInfoDescriptorImpl(null);
        wrapper.addColumn(new TupleInfoColumnDescriptorImpl("0",
                new TypeInfoImpl(Tuple.class),
                inputTupleInfoDescriptor.getTupleClassName(),
                null,
                inputTupleInfoDescriptor));
        return this.composeEvaluator(expression, wrapper);
    }


    public EvaluatorDescriptor newEvaluatorDescriptor(
            ProxyContext proxyContext,
            TupleInfoDescriptor inputTupleInfoDescriptor)
            throws Exception {

        if (inputTupleInfoDescriptor instanceof AliasMapDescriptor) {
            return this.composeEvaluator(proxyContext, inputTupleInfoDescriptor);
        }

        final TupleInfoDescriptor wrapper = new TupleInfoDescriptorImpl(null);
        wrapper.addColumn(new TupleInfoColumnDescriptorImpl("0",
                new TypeInfoImpl(Tuple.class),
                inputTupleInfoDescriptor.getTupleClassName(),
                null,
                inputTupleInfoDescriptor));
        return this.composeEvaluator(proxyContext, wrapper);
    }

}

//    /**
//     * Creates a TVE for a given Expression in a QueryExecutionPlanClassBuilder.
//     *
//     * @param expression               Expression to get a TVE for.
//     * @param inputTupleInfoDescriptor TupleInfoDescriptorImpl of the tuples available to the extractor.
//     * @return TupleValueExtractor
//     * @throws Exception upon problem.
//     */
//    public TupleValueExtractor newTupleValueExtractor(
//            Expression expression,
//            TupleInfoDescriptor inputTupleInfoDescriptor)
//            throws Exception {
//
//        final TupleInfoDescriptor wrapper = new TupleInfoDescriptorImpl(null);
//        wrapper.addColumn(new TupleInfoColumnDescriptorImpl("0",
//                new TypeInfoImpl(Tuple.class),
//                inputTupleInfoDescriptor.getTupleClassName(),
//                null, inputTupleInfoDescriptor));
//
//        final EvaluatorDescriptor descriptor = this.composeEvaluator(expression, wrapper);
//
//        return new EvaluatorToExtractorAdapter(descriptor.getEvaluator());
//    }
//
//
//    /**
//     * Creates a TVE for a given ProxyContext in a QueryExecutionPlanClassBuilder.
//     *
//     * @param proxy                    ProxyContext to get a TVE for.
//     * @param inputTupleInfoDescriptor TupleInfoDescriptorImpl of the tuples available to the extractor.
//     * @return TupleValueExtractor
//     * @throws Exception upon problem.
//     */
//    public TupleValueExtractor newTupleValueExtractor(
//            ProxyContext proxy,
//            TupleInfoDescriptor inputTupleInfoDescriptor)
//            throws Exception {
//
//        final TupleInfoDescriptor wrapper = new TupleInfoDescriptorImpl(null);
//        wrapper.addColumn(new TupleInfoColumnDescriptorImpl("0",
//                new TypeInfoImpl(Tuple.class),
//                inputTupleInfoDescriptor.getTupleClassName(),
//                null, inputTupleInfoDescriptor));
//
//        final EvaluatorDescriptor descriptor = this.composeEvaluator(proxy, wrapper);
//
//        return new EvaluatorToExtractorAdapter(descriptor.getEvaluator());
//    }


