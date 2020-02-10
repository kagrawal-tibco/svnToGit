package com.tibco.cep.query.utils;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.exception.ClassCoercionException;
import com.tibco.cep.query.model.DateFormatProvider;
import com.tibco.cep.runtime.model.element.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author pdhar
 * Helper class for Java primitives, types and BE classes
 */
public class TypeHelper {

    private static final Set<String> JAVA_PRIMITIVE_TYPE_NAMES;
    private static final Set<String> JAVA_PRIMITIVE_ARRAY_TYPE_NAMES;

    static {
        JAVA_PRIMITIVE_TYPE_NAMES = new HashSet<String>();
        JAVA_PRIMITIVE_TYPE_NAMES.add(boolean.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(byte.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(char.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(double.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(float.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(int.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(long.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(short.class.getName());
        JAVA_PRIMITIVE_TYPE_NAMES.add(void.class.getName());

        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES = new HashSet<String>();
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(boolean[].class.getName());
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(byte[].class.getName());
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(char[].class.getName());
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(double[].class.getName());
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(float[].class.getName());
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(int[].class.getName());
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(long[].class.getName());
        JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.add(short[].class.getName());
    }


    /**
     * Returns true if the given class corresponds to a boxed type
     * @param clz Class to test
     * @return boolean
     */
    public static boolean isBoxedType(Class clz) {
        return Boolean.class.equals(clz)
            || Byte.class.equals(clz)
            || Character.class.equals(clz)
            || Double.class.equals(clz)
            || Float.class.equals(clz)
            || Integer.class.equals(clz)
            || Long.class.equals(clz)
            || Short.class.equals(clz);
    }


    public static Boolean getBoxed(boolean value) {
        return new Boolean(value);
    }


    public static Boolean[] getBoxed(boolean[] value) {
        if (null == value) {
            return null;
        }
        final Boolean[] result = new Boolean[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Boolean(value[i]);
        }
        return result;
    }


    public static Byte getBoxed(byte value) {
        return new Byte(value);
    }


    public static Byte[] getBoxed(byte[] value) {
        if (null == value) {
            return null;
        }
        final Byte[] result = new Byte[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Byte(value[i]);
        }
        return result;
    }


    public static Character getBoxed(char value) {
        return new Character(value);
    }


    public static Character[] getBoxed(char[] value) {
        if (null == value) {
            return null;
        }
        final Character[] result = new Character[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Character(value[i]);
        }
        return result;
    }


    public static Double getBoxed(double value) {
        return new Double(value);
    }


    public static Double[] getBoxed(double[] value) {
        if (null == value) {
            return null;
        }
        final Double[] result = new Double[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Double(value[i]);
        }
        return result;
    }


    public static Float getBoxed(float value) {
        return new Float(value);
    }


    public static Float[] getBoxed(float[] value) {
        if (null == value) {
            return null;
        }
        final Float[] result = new Float[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Float(value[i]);
        }
        return result;
    }


    public static Integer getBoxed(int value) {
        return new Integer(value);
    }


    public static Integer[] getBoxed(int[] value) {
        if (null == value) {
            return null;
        }
        final Integer[] result = new Integer[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Integer(value[i]);
        }
        return result;
    }


    public static Long getBoxed(long value) {
        return new Long(value);
    }


    public static Long[] getBoxed(long[] value) {
        if (null == value) {
            return null;
        }
        final Long[] result = new Long[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Long(value[i]);
        }
        return result;
    }


    public static Short getBoxed(short value) {
        return new Short(value);
    }


    public static Short[] getBoxed(short[] value) {
        if (null == value) {
            return null;
        }
        final Short[] result = new Short[value.length];
        for (int i=value.length-1; i>=0; i--) {
            result[i] = new Short(value[i]);
        }
        return result;
    }


    /**
     * Returns the boxed type of a given primitive class , if the class itself is
     * already boxed then return the same class
     * @param clz
     * @return Class
     */
    public static Class getBoxedClass(Class clz) {
        if(clz.equals(char.class)) {
            return Character.class;
        }
        if(clz.equals(boolean.class)) {
            return Boolean.class;
        }
        if(clz.equals(byte.class)) {
            return Byte.class;
        }
        if(clz.equals(short.class)){
            return Short.class;
        }
        if(clz.equals(int.class)) {
            return Integer.class;
        }
        if(clz.equals(long.class)){
            return Long.class;
        }
        if(clz.equals(float.class)){
            return Float.class;
        }
        if(clz.equals(double.class)){
            return Double.class;
        }
        return clz;
    }


    /**
     * Return the unboxed class for a given class , if the class itself is already unboxed then return the class itself
     * @param clz
     * @return Class
     */
    public static Class getUnboxedClass(Class clz) {
        if(clz.equals(Character.class)) {
            return char.class;
        }
        if(clz.equals(Boolean.class)) {
            return boolean.class;
        }
        if(clz.equals(Byte.class)) {
            return byte.class;
        }
        if(clz.equals(Short.class)){
            return short.class;
        }
        if(clz.equals(Integer.class)) {
            return int.class;
        }
        if(clz.equals(Long.class)){
            return long.class;
        }
        if(clz.equals(Float.class)){
            return float.class;
        }
        if(clz.equals(Double.class)){
            return double.class;
        }
        return clz;
    }


    public static boolean getUnboxed(Boolean boxed) {
        return getUnboxed(boxed, false);
    }


    public static boolean getUnboxed(Boolean boxed, boolean defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.booleanValue();
    }


    public static boolean[] getUnboxed(Boolean[] boxed) {
        return getUnboxed(boxed, false);
    }


    public static boolean[] getUnboxed(Boolean[] boxed, boolean defaultValue) {
        if (null == boxed) {
            return new boolean[0];
        }
        final boolean[] result = new boolean[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i]);
        }
        return result;
    }


    public static byte getUnboxed(Byte boxed) {
        return getUnboxed(boxed, (byte)0);
    }


    public static byte getUnboxed(Byte boxed, byte defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.byteValue();
    }


    public static byte[] getUnboxed(Byte[] boxed) {
        return getUnboxed(boxed, (byte)0);
    }


    public static byte[] getUnboxed(Byte[] boxed, byte defaultValue) {
        if (null == boxed) {
            return new byte[0];
        }
        final byte[] result = new byte[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i], defaultValue);
        }
        return result;
    }


    public static char getUnboxed(Character boxed) {
        return getUnboxed(boxed, '?');
    }


    public static char getUnboxed(Character boxed, char defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.charValue();
    }


    public static char[] getUnboxed(Character[] boxed) {
        return getUnboxed(boxed, '?');
    }


    public static char[] getUnboxed(Character[] boxed, char defaultValue) {
        if (null == boxed) {
            return new char[0];
        }
        final char[] result = new char[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i], defaultValue);
        }
        return result;
    }


    public static double getUnboxed(Double boxed) {
        return getUnboxed(boxed, 0);
    }


    public static double getUnboxed(Double boxed, double defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.doubleValue();
    }


    public static double[] getUnboxed(Double[] boxed) {
        return getUnboxed(boxed, 0d);
    }


    public static double[] getUnboxed(Double[] boxed, double defaultValue) {
        if (null == boxed) {
            return new double[0];
        }
        final double[] result = new double[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i], defaultValue);
        }
        return result;
    }


    public static float getUnboxed(Float boxed) {
        return getUnboxed(boxed, 0);
    }


    public static float getUnboxed(Float boxed, float defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.floatValue();
    }


    public static float[] getUnboxed(Float[] boxed) {
        return getUnboxed(boxed, 0f);
    }


    public static float[] getUnboxed(Float[] boxed, float defaultValue) {
        if (null == boxed) {
            return new float[0];
        }
        final float[] result = new float[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i], defaultValue);
        }
        return result;
    }


    public static int getUnboxed(Integer boxed) {
        return getUnboxed(boxed, 0);
    }


    public static int getUnboxed(Integer boxed, int defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.intValue();
    }


    public static int[] getUnboxed(Integer[] boxed) {
        return getUnboxed(boxed, 0);
    }


    public static int[] getUnboxed(Integer[] boxed, int defaultValue) {
        if (null == boxed) {
            return new int[0];
        }
        final int[] result = new int[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i], defaultValue);
        }
        return result;
    }


    public static long getUnboxed(Long boxed) {
        return getUnboxed(boxed, 0l);
    }


    public static long getUnboxed(Long boxed, long defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.longValue();
    }


    public static long[] getUnboxed(Long[] boxed) {
        return getUnboxed(boxed, 0l);
    }


    public static long[] getUnboxed(Long[] boxed, long defaultValue) {
        if (null == boxed) {
            return new long[0];
        }
        final long[] result = new long[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i], defaultValue);
        }
        return result;
    }


    public static short getUnboxed(Short boxed) {
        return getUnboxed(boxed, (short)0);
    }


    public static short getUnboxed(Short boxed, short defaultValue) {
        if (null == boxed) {
            return defaultValue;
        }
        return boxed.shortValue();
    }


    public static short[] getUnboxed(Short[] boxed) {
        return getUnboxed(boxed, (short)0);
    }


    public static short[] getUnboxed(Short[] boxed, short defaultValue) {
        if (null == boxed) {
            return new short[0];
        }
        final short[] result = new short[boxed.length];
        for (int i=boxed.length-1; i>=0; i--) {
            result[i] = getUnboxed(boxed[i], defaultValue);
        }
        return result;
    }


    /**
     * Gets the root of the component path of the argument, i.e. the argument itself if is not an array,
     * else recursively the first component that is not an array.
     * @param clazz a Class
     * @return Class clazz if it is not an array, else recursively the first component that is not an array.
     */
    public static Class getRootComponentType(Class clazz) {
        if (clazz.isArray()) {
            return getRootComponentType(clazz.getComponentType());
        }
        return clazz;
    }


    /**
     * Gets the array depth of the argument.
     * @param clazz a Class
     * @return int array depth of the argument.
     */
    public static int getArrayDepth(Class clazz) {
        if (clazz.isArray()) {
            return 1 + getArrayDepth(clazz.getComponentType());
        }
        return 0;
    }


    /**
     * Determine if the class passed in is a numeric class
     * @param clz
     * @return boolean
     */
    public static boolean isNumericClass(Class clz) {
        if (
                (clz == Number.class) ||
                (clz == double.class) ||
                (clz == Double.class) ||
                (clz == float.class) ||
                (clz == Float.class) ||
                (clz == short.class) ||
                (clz == Short.class) ||
                (clz == int.class) ||
                (clz == Integer.class) ||
                (clz == long.class) ||
                (clz == Long.class) ||
                (clz == byte.class) ||
                (clz == Byte.class))
            {
                return true;
            }
            return false;
    }

    /**
     * Tests if a class is assignable to another class
     * @param assignFrom
     * @param assignTo
     * @return boolean true if it assignable
     */
    public boolean isAssignable(Class assignFrom,Class assignTo) {
        if(assignFrom.isAssignableFrom(assignTo)) {
            return true;
        }
        if(assignFrom.isPrimitive()) {
            final Class boxedClass = getBoxedClass(assignFrom);
            if(boxedClass != null) {
                return assignTo.equals(boxedClass);
            }
        }
        return false;
    }


    /**
     * Tests if a class name is the name of a Java primitive type
     * @param className String name to test
     * @return boolean
     */
    public static boolean isJavaPrimitiveType(String className) {
        return JAVA_PRIMITIVE_TYPE_NAMES.contains(className);
    }


    /**
     * Tests if a class name is the name of a Java primitive type
     * @param className String name to test
     * @return boolean
     */
    public static boolean isJavaPrimitiveTypeOrPrimitiveArrayType(String className) {
        return JAVA_PRIMITIVE_TYPE_NAMES.contains(className)
                || JAVA_PRIMITIVE_ARRAY_TYPE_NAMES.contains(className);
    }


    /**
     * Test if the passed class is a boxed or unboxed boolean
     * @param clz
     * @return true if it is a boolean or Boolean
     */
    public static boolean isBooleanClass(Class clz) {
        if(clz.equals(boolean.class)|| clz.equals(Boolean.class)){
            return true;
        }
        return false;
    }

    /**
     * Returns the coercion type for the 2 numeric types to a common type for use in numeric expressions.
     * Note: byte and short types always result in integer.
     * @param typeOne is the first type
     * @param typeTwo is the second type
     * @return coerced type
     * @throws ClassCoercionException if types don't allow coercion
     */
    public static Class getNumericCoercionClass(Class typeOne, Class typeTwo)
            throws ClassCoercionException
    {
        Class boxOne = getBoxedClass(typeOne);
        Class boxTwo = getBoxedClass(typeTwo);
        if (!isNumericClass(boxOne) || !isNumericClass(boxTwo))
        {
            throw new ClassCoercionException("Cannot coerce class " + typeOne.getName() + " and " + typeTwo.getName());
        }

        if ((boxOne == Double.class) || (boxTwo == Double.class))
        {
            return Double.class;
        }
        if ((boxOne == Float.class) || (boxTwo == Float.class))
        {
            return Double.class;
        }
        if ((boxOne == Long.class) || (boxTwo == Long.class))
        {
            return Long.class;
        }
        return Integer.class;
    }

    /**
     * Coerce the given number to a specified type, assuming the type is a Boxed type.
     * This function allows coercion to lower result number and does't coerce to primitive types.
     * @param num
     * @param resultBoxedClass
     * @return Number
     */
    public static Number coerceBoxedClass(Number num, Class resultBoxedClass)
    {
        if (num.getClass() == resultBoxedClass)
        {
            return num;
        }
        if (resultBoxedClass == Double.class)
        {
            return num.doubleValue();
        }
        if (resultBoxedClass == Long.class)
        {
            return num.longValue();
        }
        if (resultBoxedClass == Float.class)
        {
            return num.floatValue();
        }
        if (resultBoxedClass == Integer.class)
        {
            return num.intValue();
        }
        if (resultBoxedClass == Short.class)
        {
            return num.shortValue();
        }
        if (resultBoxedClass == Byte.class)
        {
            return num.byteValue();
        }
        throw new IllegalArgumentException("Cannot coerce to number class " + resultBoxedClass.getName());
    }
    /**
     * Returns true if the Number instance is a floating point number.
     * @param number to check
     * @return true if number is Float or Double type
     */
    public static boolean isFloatNumber(Number number)
    {
        if ((number instanceof Float) ||
            (number instanceof Double))
        {
            return true;
        }
        return false;
    }

    public static boolean isDateTimeClass(Class clazz) {
        return Calendar.class.isAssignableFrom(clazz);
    }

    /**
     * Returns true if the supplied type is a floating point number.
     * @param clazz to check
     * @return true if primitive or boxed float or double
     */
    public static boolean isFloatClass(Class clazz)
    {
        if ((clazz == Float.class) ||
            (clazz == Double.class) ||
            (clazz == float.class) ||
            (clazz == double.class))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns for 2 classes to be compared via boolean operator the Class type of
     * common comparison.
     * @param typeOne is the first type
     * @param typeTwo is the second type
     * @return Class
     * @throws IllegalArgumentException if the types cannot be compared
     */
    public static Class getCompareToCoercionClass(Class typeOne, Class typeTwo)
    {
        if ((typeOne == String.class) && (typeTwo == String.class))
        {
            return String.class;
        }
        if (  ((typeOne == boolean.class) || ((typeOne == Boolean.class))) &&
              ((typeTwo == boolean.class) || ((typeTwo == Boolean.class))) )
        {
            return Boolean.class;
        }
        if (!isNumericClass(typeOne) || !isNumericClass(typeTwo))
        {
            throw new IllegalArgumentException("Classes cannot be compared: " +
                    typeOne.getName() + " and " + typeTwo.getName());
        }
        if (isFloatClass(typeOne) || isFloatClass(typeTwo))
        {
            return Double.class;
        }
        return Long.class;
    }

    /**
     * Determines if a number can be coerced upwards to another number class without loss.     *
     * @param coercedFrom the number class to be coerced
     * @param coercedTo the number class to coerce to
     * @return true if numbers can be coerced without loss, false if not
     */
    public static boolean canCoerceClass(Class coercedFrom, Class coercedTo)
    {
        Class boxedFrom = getBoxedClass(coercedFrom);
        Class boxedTo = getBoxedClass(coercedTo);

        if (!isNumericClass(coercedFrom))
        {
            throw new IllegalArgumentException("Class '" + coercedFrom + "' is not a numeric type'");
        }

        if (boxedTo == Float.class)
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class) ||
                    (boxedFrom == Long.class) ||
                    (boxedFrom == Float.class));
        }
        else if (boxedTo == Double.class)
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class) ||
                    (boxedFrom == Long.class) ||
                    (boxedFrom == Float.class) ||
                    (boxedFrom == Double.class));
        }
        else if (boxedTo == Long.class)
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class) ||
                    (boxedFrom == Long.class));
        }
        else if ((boxedTo == Integer.class) ||
                 (boxedTo == Short.class) ||
                 (boxedTo == Byte.class))
        {
            return ((boxedFrom == Byte.class) ||
                    (boxedFrom == Short.class) ||
                    (boxedFrom == Integer.class));
        }
        else
        {
            throw new IllegalArgumentException("Class '" + coercedTo + "' is not a numeric type'");
        }
    }

    /**
     * Returns for the class name given the class name of the boxed type if
     * the class name is one of the Java primitive types.
     * @param className is a class name, a Java primitive type or other class
     * @return boxed class name if Java primitive type, or just same class name passed in if not a primitive type
     */
    public static String getBoxedName(String className)
    {
        if (className.equals(char.class.getName()))
        {
            return Character.class.getName();
        }
        if (className.equals(byte.class.getName()))
        {
            return Byte.class.getName();
        }
        if (className.equals(short.class.getName()))
        {
            return Short.class.getName();
        }
        if (className.equals(int.class.getName()))
        {
            return Integer.class.getName();
        }
        if (className.equals(long.class.getName()))
        {
            return Long.class.getName();
        }
        if (className.equals(float.class.getName()))
        {
            return Float.class.getName();
        }
        if (className.equals(double.class.getName()))
        {
            return Double.class.getName();
        }
        if (className.equals(boolean.class.getName()))
        {
            return Boolean.class.getName();
        }
        return className;
    }

    /**
     * Returns true if the class passed in is a Java built-in data type (primitive or wrapper) including String.
     * @param clazz to check
     * @return true if built-in data type, or false if not
     */
    public static boolean isBuiltinJavaDataType(Class clazz)
    {
        final Class boxedClass = getBoxedClass(clazz);
        return isNumericClass(boxedClass)
                || isBooleanClass(boxedClass)
                || boxedClass.equals(String.class)
                || boxedClass.equals(Object.class)
                || boxedClass.equals(Calendar.class)
                || boxedClass.equals(char.class)
                || boxedClass.equals(Character.class);
    }


     /**
     * Returns true if the class passed in is a Java built-in data type (primitive or wrapper) including String.
     * @param clazz to check
     * @return true if built-in data type, or false if not
     */
    public static boolean isJavaBuiltinDataTypeArray(Class clazz) {
        if (!clazz.isArray()) {
            return false;
        }
        final Class componentType = clazz.getComponentType();
        return isJavaPrimitiveTypeOrPrimitiveArrayType(componentType.getName())
                || isJavaBuiltinDataTypeArray(componentType);
    }


    public static Object[] toArray(Class targetComponentClass, Object o) {
        if (null == o) {
            return null;
        }

        Class arrayClass = o.getClass();
        List arrayAsList;

        if (o instanceof PropertyArray) {
            final PropertyArray propertyArray = (PropertyArray) o;
            final PropertyAtom[] atoms = propertyArray.toArray();
            arrayAsList = new ArrayList(atoms.length);
            for (PropertyAtom atom : atoms) {
                arrayAsList.add(atom.getValue());
            }
            if (propertyArray instanceof PropertyArrayBoolean) {
                arrayClass = Boolean[].class;
            } else if (propertyArray instanceof PropertyArrayConcept) {
                arrayClass = Concept[].class;
            } else if (propertyArray instanceof PropertyArrayDateTime) {
                arrayClass = Calendar[].class;
            } else if (propertyArray instanceof PropertyArrayDouble) {
                arrayClass = Double[].class;
            } else if (propertyArray instanceof PropertyArrayInt) {
                arrayClass = Integer[].class;
            } else if (propertyArray instanceof PropertyArrayLong) {
                arrayClass = Long[].class;
            } else if (propertyArray instanceof PropertyArrayString) {
                arrayClass = String[].class;
            } else {
                arrayClass = Object[].class;
            }
        } else if (arrayClass.isArray()) {
            arrayAsList = Arrays.asList(o);
        } else {
            throw new IllegalArgumentException("Cannot convert to array from " + arrayClass.getName());
        }

        final Class componentClass = arrayClass.getComponentType();
        if (targetComponentClass.isAssignableFrom(componentClass)
                && (componentClass.isPrimitive() || componentClass.isArray()) ) {            
            return (Object[]) o;
        }

        final int listSize = arrayAsList.size();
        final Object[] result = (Object[]) Array.newInstance(componentClass, listSize); 
        final Class boxedTargetComponentClass = getBoxedClass(targetComponentClass);
        if (boxedTargetComponentClass.isArray()) {
            final Class innerComponentType = componentClass.getComponentType();
            for (int i=0; i<listSize; i++) {
                result[i] = toArray(innerComponentType, arrayAsList.get(i));
            }
        } else if (Boolean.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toBoolean(arrayAsList.get(i));
            }
        } else if (Byte.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toByte(arrayAsList.get(i));
            }
        } else if (Calendar.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toDateTime(arrayAsList.get(i));
            }
        } else if (Concept.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toConcept(arrayAsList.get(i));
            }
        } else if (Double.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toDouble(arrayAsList.get(i));
            }
        } else if (Event.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toEvent(arrayAsList.get(i));
            }
        } else if (Integer.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toInteger(arrayAsList.get(i));
            }
        } else if (Long.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toLong(arrayAsList.get(i));
            }
        } else if (Short.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toShort(arrayAsList.get(i));
            }
        } else if (String.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toString(arrayAsList.get(i));
            }
        } else if (Entity.class.isAssignableFrom(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toEntity(arrayAsList.get(i));
            }
        } else if (Object.class.equals(boxedTargetComponentClass)) {
            for (int i=0; i<listSize; i++) {
                result[i] = toObject(arrayAsList.get(i));
            }
        } else {
            throw new IllegalArgumentException("Unsupported conversion to array from " + arrayClass.getCanonicalName());            
        }

        return result;
    }


    public static Boolean toBoolean(Object o) {
        if (null == o) {
            return Boolean.FALSE;
        } else if (o instanceof Boolean) {
            return (Boolean) o;
        } else if (o instanceof Number) {
            return ((Number) o).doubleValue() != 0;
        } else if (o instanceof String) {
            return Boolean.valueOf((String) o);
        } else {
            throw new IllegalArgumentException("Cannot convert to Boolean from " + o.getClass().getName());
        }
    }


    public static Byte toByte(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Byte) {
            return (Byte) o;
        } else if (o instanceof Number) {
            return ((Number) o).byteValue();
        } else if (o instanceof Calendar) {
            return (byte) ((Calendar) o).getTimeInMillis();
        } else if (o instanceof String) {
            return Byte.valueOf((String) o);
        } else {
            throw new IllegalArgumentException("Cannot convert to byte from " + o.getClass().getName());
        }
    }


    public static Concept toConcept(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Concept) {
            return (Concept) o;
        } else {
            throw new IllegalArgumentException("Cannot convert to concept from " + o.getClass().getName());
        }
    }


    public static Calendar toDateTime(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Calendar) {
            return (Calendar) o;
        } else if (o instanceof Number) {
            final Calendar c = Calendar.getInstance();
            c.setTimeInMillis(((Number) o).longValue());
            return c;
        } else if (o instanceof String) {
            final Calendar c = Calendar.getInstance();
            try {
                c.setTime(DateFormatProvider.getInstance().getDateFormat().parse((String) o));
                return c;
            } catch (Exception ignored) {
            }
        }

        throw new IllegalArgumentException("Cannot convert to DateTime from " + o.getClass().getName());
    }


    public static Double toDouble(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof Number) {
            return ((Number) o).doubleValue();
        } else if (o instanceof Calendar) {
            return (double) ((Calendar) o).getTimeInMillis();
        } else if (o instanceof String) {
            return Double.valueOf((String) o);
        } else {
            throw new IllegalArgumentException("Cannot convert to double from " + o.getClass().getName());
        }
    }


    public static Entity toEntity(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Entity) {
            return (Entity) o;
        } else {
            throw new IllegalArgumentException("Cannot convert to entity from " + o.getClass().getName());
        }
    }


    public static Event toEvent(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Event) {
            return (Event) o;
        } else {
            throw new IllegalArgumentException("Cannot convert to event from " + o.getClass().getName());
        }
    }


    public static Float toFloat(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Float) {
            return (Float) o;
        } else if (o instanceof Number) {
            return ((Number) o).floatValue();
        } else if (o instanceof Calendar) {
            return (float ) ((Calendar) o).getTimeInMillis();
        } else if (o instanceof String) {
            return Float.valueOf((String) o);
        } else {
            throw new IllegalArgumentException("Cannot convert to float from " + o.getClass().getName());
        }
    }


    public static Integer toInteger(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Number) {
            return ((Number) o).intValue();
        } else if (o instanceof Calendar) {
            return (int) ((Calendar) o).getTimeInMillis();
        } else if (o instanceof String) {
            return Integer.valueOf((String) o);
        } else {
            throw new IllegalArgumentException("Cannot convert to int from " + o.getClass().getName());
        }
    }


    public static Long toLong(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof Number) {
            return ((Number) o).longValue();
        } else if (o instanceof Calendar) {
            return ((Calendar) o).getTimeInMillis();
        } else if (o instanceof String) {
            return Long.valueOf((String) o);
        } else {
            throw new IllegalArgumentException("Cannot convert to long from " + o.getClass().getName());
        }
    }


    public static Object toObject(Object o) {
        return o;
    }
    

    public static Short toShort(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof Short) {
            return (Short) o;
        } else if (o instanceof Number) {
            return ((Number) o).shortValue();
        } else if (o instanceof Calendar) {
            return (short) ((Calendar) o).getTimeInMillis();
        } else if (o instanceof String) {
            return Short.valueOf((String) o);
        } else {
            throw new IllegalArgumentException("Cannot convert to short from " + o.getClass().getName());
        }
    }


    public static String toString(Object o) {
        if (null == o) {
            return null;
        } else if (o instanceof String) {
                return (String) o;
        } else if (o instanceof Calendar) {
            return DateFormatProvider.getInstance().getDateFormat().format(((Calendar) o).getTime());
        } else {
            return o.toString();
        }
    }




    // null values are allowed and represent and unknown type

    /**
     * Determines a common denominator numeric class to which one or more numeric classes can be casted or coerced.
     * @param types is an array of one or more types, which can be Java built-in (primitive or wrapper)
     * or user types
     * @return common denominator type if any can be found, for use in comparison
     * @throws ClassCoercionException when no coercion type could be determined
     */
    public static Class getCommonCoercionClass(Class[] types)
            throws ClassCoercionException
    {
        if (types.length < 1)
        {
            throw new IllegalArgumentException("Unexpected zero length array");
        }
        if (types.length == 1)
        {
            return getBoxedClass(types[0]);
        }

        // Reduce to non-null types
        List<Class> nonNullTypes = new LinkedList<Class>();
        for (int i = 0; i < types.length; i++)
        {
            if (types[i] != null)
            {
                nonNullTypes.add(types[i]);
            }
        }
        types = nonNullTypes.toArray(new Class[0]);

        if (types.length == 0)
        {
            return null;    // only null types, result is null
        }
        if (types.length == 1)
        {
            return getBoxedClass(types[0]);
        }

        // Check if all String
        if (types[0] == String.class)
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != String.class)
                {
                    throw new ClassCoercionException("Cannot coerce to String class " + types[i].getName());
                }
            }
            return String.class;
        }
        // Check if all Calendar
        if (types[0] == Calendar.class)
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != Calendar.class)
                {
                    throw new ClassCoercionException("Cannot coerce to Calendar class " + types[i].getName());
                }
            }
            return Calendar.class;
        }

        // Convert to boxed types
        for (int i = 0; i < types.length; i++)
        {
            types[i] = getBoxedClass(types[i]);
        }

        // Check if all boolean
        if (types[0] == Boolean.class)
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != Boolean.class)
                {
                    throw new ClassCoercionException("Cannot coerce to Boolean class " + types[i].getName());
                }
            }
            return Boolean.class;
        }

        // Check if all char
        if (types[0] == Character.class)
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != Character.class)
                {
                    throw new ClassCoercionException("Cannot coerce to Character class " + types[i].getName());
                }
            }
            return Character.class;
        }

        // Check if all the same non-Java builtin type, i.e. Java beans etc.
        if (!isBuiltinJavaDataType(types[0]))
        {
            for (int i = 0; i < types.length; i++)
            {
                if (types[i] != types[0])
                {
                    throw new ClassCoercionException("Cannot coerce to class " + types[0].getName());
                }
            }
            return types[0];
        }

        // Test for numeric
        if (!isNumericClass(types[0]))
        {
            throw new ClassCoercionException("Cannot coerce to numeric class " + types[0].getName());
        }

        // Use arithmatic coercion type as the final authority, considering all types
        Class result = getNumericCoercionClass(types[0], types[1]);
        int count = 2;
        while(count < types.length)
        {
            result = getNumericCoercionClass(result, types[count]);
            count++;
        }
        return result;
    }

    /**
     * Returns the class given a fully-qualified class name.
     * @param className is the fully-qualified class name, java primitive types included.
     * @return class for name
     * @throws ClassNotFoundException if the class cannot be found
     */
    public static Class getClassForName(String className) throws ClassNotFoundException
    {
        if (className.equals(boolean.class.getName()))
        {
            return boolean.class;
        }
        if (className.equals(char.class.getName()))
        {
            return char.class;
        }
        if (className.equals(double.class.getName()))
        {
            return double.class;
        }
        if (className.equals(float.class.getName()))
        {
            return float.class;
        }
        if (className.equals(byte.class.getName()))
        {
            return byte.class;
        }
        if (className.equals(short.class.getName()))
        {
            return short.class;
        }
        if (className.equals(int.class.getName()))
        {
            return int.class;
        }
        if (className.equals(long.class.getName()))
        {
            return long.class;
        }
        return Class.forName(className);
    }
}
