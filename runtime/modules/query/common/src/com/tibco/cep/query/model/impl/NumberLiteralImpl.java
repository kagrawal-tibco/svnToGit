package com.tibco.cep.query.model.impl;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NumberLiteral;
import com.tibco.cep.query.utils.TypeHelper;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 2:03:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class NumberLiteralImpl extends AbstractExpression implements NumberLiteral {

    private Number numValue;
    private int numType;


    public NumberLiteralImpl(ModelContext parentContext, CommonTree tree, String inputValue) throws Exception {
        super(parentContext, tree);
        Number val = null;
        int radix = RADIX_DECIMAL;
        String value = inputValue;

        if(inputValue.startsWith("0")) {
            radix = RADIX_OCTAL; // Octal
            value = inputValue.trim();
        }
        if(inputValue.startsWith("0x") || inputValue.startsWith("0X")) {
            value = inputValue.substring("0x".length()).trim();
            if(value.length() > 8)  {
               // value = value +"L";
            }
            radix = RADIX_HEXADECIMAL; // Hexadecimal
        }

        try {
            val = Byte.parseByte(value,radix);
            numType = LT_BYTE;
            this.typeInfo = new TypeInfoImpl(byte.class);
        } catch ( NumberFormatException be) {
            try {
                val = Short.parseShort(value,radix);
                numType = LT_SHORT;
                this.typeInfo = new TypeInfoImpl(short.class);
            } catch (NumberFormatException se) {
                 try {
                     val = Integer.parseInt(value,radix);
                     numType = LT_INTEGER;
                     this.typeInfo = new TypeInfoImpl(int.class);
                 } catch (NumberFormatException ie) {
                     try {
                         if(radix == RADIX_DECIMAL) {
                            val = Long.parseLong(value,radix);
                         } else if( radix == RADIX_HEXADECIMAL ) {
                             val = Long.parseLong(value,radix);
                         } else {
                            val = Long.parseLong(value,radix);
                         }
                         numType = LT_LONG;
                         this.typeInfo = new TypeInfoImpl(long.class);
                     } catch(NumberFormatException le) {
                         if (radix != RADIX_DECIMAL) {
                            throw new Exception("Unsupported Number Literal :"+inputValue);
                         } else {
                             try {
                                 val = new Double(Double.parseDouble(value));
                                 numType = LT_DOUBLE;
                                 this.typeInfo = new TypeInfoImpl(double.class);
                             } catch (NumberFormatException fe) {
                                 try {
                                     val = Float.parseFloat(value);
                                     numType = LT_FLOAT;
                                     this.typeInfo = new TypeInfoImpl(float.class);
                                 } catch (NumberFormatException de) {
                                     throw de;
                                 }
                             }
                         }
                     }
                 }
            }
        }
        this.numValue = val;
    }

    
    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_NUMBER_LITERAL;
    }

    /**
     * Returns the primitive numValue of the literal
     *
     * @return byte
     */
    public byte byteValue() {
        return this.numValue.byteValue();
    }

    /**
     * Returns the primitive numValue of the literal
     *
     * @return short
     */
    public short shortValue() {
        return this.numValue.shortValue();
    }

    /**
     * Returns the primitive numValue of the literal
     *
     * @return int
     */
    public int intValue() {
        return this.numValue.intValue();
    }

    /**
     * Returns the Boxed Type numValue of the literal
     *
     * @return long
     */
    public long longValue() {
        return this.numValue.longValue();
    }

    /**
     * Returns the Boxed Type numValue of the literal
     *
     * @return Integer
     */
    public double doubleValue() {
        return this.numValue.doubleValue();
    }

    /**
     * Returns the Boxed Type numValue of the literal
     *
     * @return float
     */
    public float floatValue() {
        return this.numValue.floatValue();
    }

    /**
     * Returns true if the  number is an integer otherwise false
     *
     * @return true or false
     */
    public boolean isByte() {
        return this.numType == LT_BYTE;
    }

    /**
     * Returns true if the  number is a Long integer otherwise false
     *
     * @return true or false
     */
    public boolean isDouble() {
        return this.numType == LT_DOUBLE;
    }

    /**
     * Returns true if the  number is a Long integer otherwise false
     *
     * @return true or false
     */
    public boolean isFloat() {
        return this.numType == LT_FLOAT;
    }

    /**
     * Returns true if the  number is an integer otherwise false
     *
     * @return true or false
     */
    public boolean isInteger() {
        return this.numType == LT_INTEGER;
    }

    /**
     * Returns true if the  number is a Long integer otherwise false
     *
     * @return true or false
     */
    public boolean isLong() {
        return this.numType == LT_LONG;
    }

    /**
     * Returns true if the  number is an integer otherwise false
     *
     * @return true or false
     */
    public boolean isShort() {
        return this.numType == LT_SHORT;
    }



    /**
     * Returns the Literal numValue casted to RDF supported Types
     *
     * @return Object
     */
    public Number getRDFTypesValue() {
        // reduce to BE supported Datatypes
        if(typeInfo.isChar() && TypeHelper.canCoerceClass(this.numValue.getClass(),Integer.class)) {
            return TypeHelper.coerceBoxedClass(this.numValue,Integer.class);
        } else if(typeInfo.isInt() && TypeHelper.canCoerceClass(this.numValue.getClass(),Integer.class)) {
            return TypeHelper.coerceBoxedClass(this.numValue,Integer.class);
        } else if(typeInfo.isLong() && TypeHelper.canCoerceClass(this.numValue.getClass(),Long.class)) {
            return TypeHelper.coerceBoxedClass(this.numValue,Long.class);
        } else if(typeInfo.isDouble() && TypeHelper.canCoerceClass(this.numValue.getClass(),Double.class)) {
            return TypeHelper.coerceBoxedClass(this.numValue,Double.class);
        }
        return this.numValue;
    }

    /**
     * Returns the Literal numValue
     *
     * @return Object
     */
    public Number getValue() {
        return this.numValue;
    }

    /**
     * Returns the NumberLiteral Type
     *
     * @return int
     */
    public int getNumberLiteralType() {
        return this.numType;
    }


    /**
     * Returns true if the context has been resolved or false
     *
     * @return boolean
     */
    public boolean isResolved() {
        return this.numValue != null;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberLiteralImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final NumberLiteralImpl that = (NumberLiteralImpl) o;
        return ((this.numValue == null) && (that.numValue == null))
                || ((this.numValue != null) && this.numValue.equals(that.numValue));
    }

    public int hashCode() {
        if (null == this.numValue) {
            return 0;
        }
        long longHash = Double.doubleToLongBits(this.numValue.doubleValue());
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


}
