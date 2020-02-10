package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsBoolean;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 4:10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class BooleanConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public BooleanConverter(Class xsdClass,
                         Class javaClass) {
       super(xsdClass, javaClass);
    }

    //-------------------------------------------------------------------------
    // TypeConverter implementation methods
    //-------------------------------------------------------------------------
    /**
     * Converts a simple XML value from a String to a Java object
     *
     * @param value the conversion input, a well-formed XML String
     * @return the converted Object, i.e. the conversion output
     **/
    public Object convertSimpleType(String value) {
        return valueOf(value) ? Boolean.TRUE: Boolean.FALSE;
    }

    public static boolean valueOf(String value) {
        if(value == null) return false;
        String v = value.trim();
        if(v.equalsIgnoreCase("TRUE")) return true;
        if(v.equalsIgnoreCase("1")) return true;
        if(v.equalsIgnoreCase("T")) return true;
        return false;
//
//        return (value.length() == 4)
//                && (value.charAt(0) == 't' || value.charAt(0) == 'T')
//                && (value.charAt(1) == 'r' || value.charAt(1) == 'R')
//                && (value.charAt(2) == 'u' || value.charAt(2) == 'U')
//                && (value.charAt(3) == 'e' || value.charAt(3) == 'E') ? true: false;
    }
     /**
      * Converts the given typedValue object to a Boolean object
      * @param typedValue should be convertable to a Boolean, e.g. an XsBoolean
      * @return the Boolean object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * Boolean object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a Boolean object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         Boolean retVal = null;
         if(!typedValue.isEmpty()) {
             try {
                 retVal = typedValue.getAtom(0).castAsBoolean() ?
                         Boolean.TRUE : Boolean.FALSE;
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.  What to do?
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("BooleanConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a Boolean.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return retVal;
     }

     /**
      * From the input value, creates and returns an XsBoolean
      * @param value must be either a Boolean or a String
      * @return an XsBoolean equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsBoolean or if
      * value is neither a Boolean object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             retVal = XmlTypedValueConversionSupport.convertToXsBoolean(value);
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "BooleanConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsBoolean.", e);
         }
         return retVal;
     }

    public Class getDefaultType() {
       return DEFAULT_TYPE;
    }
    /**
     * Will register, with the given TypeConverterProvider, all converters created
     * from this converter class as well as all default lookup keys (elem/type name,
     * Java Class)
     *
     * @param handler the provider with which to register default converters
     */
    public static void register(TypeConverterHandler handler) {
       TypeConverter tc = new BooleanConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = Boolean.class;
    public static final Class DEFAULT_TYPE = XsBoolean.class;
    public static final String NUMERICAL_TRUE = "1";
}

