package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 1:43:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NumberLiteral extends Literal {
    public static final int RADIX_OCTAL = 8;
    public static final int RADIX_DECIMAL = 10;
    public static final int RADIX_HEXADECIMAL = 16;

    public static final int LT_BYTE = 1;
    public static final int LT_SHORT = 2;
    public static final int LT_INTEGER = 3;
    public static final int LT_LONG = 4;
    public static final int LT_FLOAT = 5;
    public static final int LT_DOUBLE = 6;

    /**
     * Returns the Literal value casted to RDF supported Types
     *
     * @return Object
     */
    public Number getRDFTypesValue();

    /**
     * Returns the NumberLiteral Type
     * @return int
     */
    public int getNumberLiteralType();
     /**
     * Returns the primitive value of the literal
     * @return byte
     */
    public byte byteValue();

    /**
     * Returns the primitive value of the literal
     * @return short
     */
    public short shortValue();

    /**
     * Returns the primitive value of the literal
     * @return int
     */
    public int intValue();

    /**
     * Returns the Boxed Type value of the literal
     * @return long
     */
    public long longValue();

    /**
     * Returns the Boxed Type value of the literal
     * @return float
     */
    public float floatValue();


    /**
     * Returns the Boxed Type value of the literal
     * @return Integer
     */
    public double doubleValue();

    /**
     * Returns true if the  number is an integer otherwise false
     * @return true or false
     */
    boolean isByte() ;

    /**
     * Returns true if the  number is an integer otherwise false
     * @return true or false
     */
    boolean isShort() ;

    /**
     * Returns true if the  number is an integer otherwise false
     * @return true or false
     */
    boolean isInteger() ;

    /**
     * Returns true if the  number is a Long integer otherwise false
     * @return true or false
     */
    boolean isLong();

    /**
     * Returns true if the  number is a Long integer otherwise false
     * @return true or false
     */
    boolean isFloat();

    /**
     * Returns true if the  number is a Long integer otherwise false
     * @return true or false
     */
    boolean isDouble();
}
