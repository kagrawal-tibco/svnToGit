package com.tibco.cep.query.exec.impl;

import com.tibco.cep.query.exec.descriptors.impl.EvaluatorDescriptor;
import com.tibco.cep.query.model.Entity;
import com.tibco.cep.query.model.TypeInfo;
import com.tibco.cep.query.model.impl.TypeInfoImpl;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.impl.expression.conversion.*;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomEvaluator;
import com.tibco.cep.query.stream.impl.expression.property.ConceptPropertyAtomValueEvaluator;
import com.tibco.cep.query.utils.TypeHelper;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.*;

import java.util.Calendar;

/**
 * User: nprade
 * Date: 9/8/11
 * Time: 11:52 AM
 */
public class ConversionHelper {


    private TypeManager typeManager;



    public ConversionHelper(
            TypeManager typeManager
    ) {
        this.typeManager = typeManager;
    }


    public EvaluatorDescriptor convert(
            EvaluatorDescriptor descriptor,
            TypeInfo typeInfo)
            throws Exception {

        if (null == typeInfo) {
            return descriptor;
        }

        final Class targetClass = this.getRuntimeClass(typeInfo);
        final Class boxedTargetClass = TypeHelper.getBoxedClass(targetClass);

        if (Object.class.equals(boxedTargetClass)) {
            return this.convertToObject(descriptor);

        } else if (boxedTargetClass.isArray() || typeInfo.isArray()) {
            return new EvaluatorDescriptor(new CastEvaluator(descriptor.getEvaluator(), targetClass), typeInfo);

        } else if (Boolean.class.equals(boxedTargetClass)) {
            return this.convertToBoolean(descriptor);

        } else if (Byte.class.equals(boxedTargetClass)) {
            return this.convertToByte(descriptor);

        } else if (Double.class.equals(boxedTargetClass)) {
            return this.convertToDouble(descriptor);

        } else if (Calendar.class.equals(boxedTargetClass)) {
            return this.convertToDateTime(descriptor);

        } else if ((Entity.class.isAssignableFrom(boxedTargetClass))
                || (com.tibco.cep.kernel.model.entity.Entity.class.isAssignableFrom(boxedTargetClass))) {
            return this.convertToEntity(descriptor, typeInfo);

        } else if (Integer.class.equals(boxedTargetClass)) {
            return this.convertToInt(descriptor);

        } else if (Long.class.equals(boxedTargetClass)) {
            return this.convertToLong(descriptor);

        } else if (PropertyAtom.class.isAssignableFrom(targetClass)) {
            return this.convertToPropertyAtom(descriptor, boxedTargetClass);

        } else if (Short.class.equals(boxedTargetClass)) {
            return this.convertToShort(descriptor);

        } else if (String.class.equals(boxedTargetClass)) {
            return this.convertToString(descriptor);
        }

        throw new Exception("Unsupported conversion to: " + targetClass);
    }


    public EvaluatorDescriptor convertToBoolean(
            EvaluatorDescriptor descriptor)
            throws Exception {
        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Boolean.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToBooleanEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (String.class.equals(runtimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToBooleanEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_BOOLEAN);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new Exception("Cannot convert to boolean: " + runtimeClass);
    }


    public EvaluatorDescriptor convertToByte(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToByteEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_BYTE);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Byte.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToByteEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_BYTE);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (String.class.equals(runtimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToByteEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_BYTE);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CalendarToByteEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_BYTE);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new IllegalArgumentException("Cannot convert to Byte from " + runtimeClass.getName());
    }


    public EvaluatorDescriptor convertToDateTime(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToCalendarEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.DATETIME);
            d.addUsedColumnNames(d);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToCalendarEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.DATETIME);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (String.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToCalendarEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.DATETIME);
            d.addUsedColumnNames(descriptor);
            return d;

        }

        throw new IllegalArgumentException("Cannot convert to DateTime from " + runtimeClass.getName());
    }


    public EvaluatorDescriptor convertToDouble(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToDoubleEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_DOUBLE);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor; // Any number can be safely used as a double

        } else if (String.class.equals(runtimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToDoubleEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_DOUBLE);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CalendarToDoubleEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_DOUBLE);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new IllegalArgumentException("Cannot convert to Double from " + runtimeClass.getName());
    }


    public EvaluatorDescriptor convertToEntity(
            EvaluatorDescriptor descriptor,
            TypeInfo typeInfo)
            throws Exception {

        final Class entityClass = this.getRuntimeClass(typeInfo);
        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());

        if (entityClass.equals(runtimeClass)) {
            return descriptor;

        } else if (entityClass.isAssignableFrom(runtimeClass)
                || runtimeClass.isAssignableFrom(entityClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CastEvaluator(descriptor.getEvaluator(), entityClass),
                    typeInfo);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new Exception("Cannot convert to " + entityClass.getCanonicalName()
                + " from: " + runtimeClass.getCanonicalName());
    }


    public EvaluatorDescriptor convertToFloat(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToFloatEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_FLOAT);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Float.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToFloatEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_FLOAT);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (String.class.equals(runtimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToFloatEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_FLOAT);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CalendarToFloatEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_FLOAT);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new IllegalArgumentException("Cannot convert to Float from " + runtimeClass.getName());
    }


    public EvaluatorDescriptor convertToInt(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToIntegerEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_INTEGER);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Integer.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToIntegerEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_INTEGER);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (String.class.equals(runtimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToIntegerEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_INTEGER);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CalendarToIntegerEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_INTEGER);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new IllegalArgumentException("Cannot convert to Integer from " + runtimeClass.getName());
    }


    public EvaluatorDescriptor convertToLong(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToLongEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_LONG);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Long.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToLongEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_LONG);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (String.class.equals(runtimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToLongEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_LONG);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CalendarToLongEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_LONG);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new IllegalArgumentException("Cannot convert to Long from " + runtimeClass.getName());
    }


    public EvaluatorDescriptor convertToObject(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (Object.class.equals(runtimeClass)) {
            return descriptor;
        }

        final EvaluatorDescriptor d = new EvaluatorDescriptor(descriptor.getEvaluator(), TypeInfoImpl.OBJECT);
        d.addUsedColumnNames(descriptor);
        return d;
    }


    public EvaluatorDescriptor convertToPropertyAtom(
            EvaluatorDescriptor descriptor,
            Class propertyAtomClass)
            throws Exception {

        final TypeInfo typeInfo = descriptor.getTypeInfo();
        if (!typeInfo.isAtom()) {
            throw new Exception("Cannot convert to PropertyAtom: "
                    + this.getRuntimeClass(typeInfo).getName());
        }

        ExpressionEvaluator evaluator = descriptor.getEvaluator();
        if (evaluator instanceof ConceptPropertyAtomValueEvaluator) {
            descriptor = new EvaluatorDescriptor(
                    ((ConceptPropertyAtomValueEvaluator) evaluator).getContainerEvaluator(),
                    typeInfo);
            evaluator = descriptor.getEvaluator();
        }
        if (!(evaluator instanceof ConceptPropertyAtomEvaluator)) {
            throw new Exception("Cannot convert to PropertyAtom, no viable evaluator path from: "+ evaluator);
        }

        final Class runtimeClass;
        if (typeInfo.isBoolean()) {
            runtimeClass = PropertyAtomBoolean.class;
        } else if (typeInfo.isContainedConceptProperty()) {
            runtimeClass = PropertyAtomContainedConcept.class;
        } else if (typeInfo.isConcept()) {
            runtimeClass = PropertyAtomConceptReference.class;
        } else if (typeInfo.isDateTime()) {
            runtimeClass = PropertyAtomDateTime.class;
        } else if (typeInfo.isInt()) {
            runtimeClass = PropertyAtomInt.class;
        } else if (typeInfo.isLong()) {
            runtimeClass = PropertyAtomLong.class;
        } else if (typeInfo.isDouble()) {
            runtimeClass = PropertyAtomDouble.class;
        } else if (typeInfo.isString()) {
            runtimeClass = PropertyAtomString.class;
        } else {
            runtimeClass = this.getRuntimeClass(typeInfo);
        }

        if (!propertyAtomClass.isAssignableFrom(runtimeClass)) {
            throw new Exception("Cannot convert to '" + propertyAtomClass.getName() + "' from: "
                    + runtimeClass.getName());
        }

        return descriptor;
    }


    public EvaluatorDescriptor convertToShort(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToShortEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_SHORT);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (Short.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToShortEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_SHORT);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (String.class.equals(runtimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new StringToShortEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_SHORT);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CalendarToShortEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.PRIMITIVE_SHORT);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        throw new IllegalArgumentException("Cannot convert to Short from " + runtimeClass.getName());
    }


    public EvaluatorDescriptor convertToString(
            EvaluatorDescriptor descriptor)
            throws Exception {

        final Class runtimeClass = this.getRuntimeClass(descriptor.getTypeInfo());
        if (null == runtimeClass) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToStringEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.STRING);
            d.addUsedColumnNames(descriptor);
            return d;
        }

        final Class boxedRuntimeClass = TypeHelper.getBoxedClass(runtimeClass);

        if (String.class.isAssignableFrom(boxedRuntimeClass)) {
            return descriptor;

        } else if (Number.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new NumberToStringEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.STRING);
            d.addUsedColumnNames(descriptor);
            return d;

        } else if (Calendar.class.isAssignableFrom(boxedRuntimeClass)) {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new CalendarToStringEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.STRING);
            d.addUsedColumnNames(descriptor);
            return d;

        } else {
            final EvaluatorDescriptor d = new EvaluatorDescriptor(
                    new ObjectToStringEvaluator(descriptor.getEvaluator()),
                    TypeInfoImpl.STRING);
            d.addUsedColumnNames(descriptor);
            return d;
        }
    }


    private Class getRuntimeClass(TypeInfo typeInfo)
            throws Exception {
        return typeInfo.getRuntimeClass(this.typeManager);
    }




}
