package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsString;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 4:05:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public StringConverter( Class xsdClass,
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
       return value;
    }
     /**
      * Converts the given typedValue object to a String object
      * @param typedValue should be convertable to a String, e.g. an XsString
      * @return the String object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * String object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a String object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         String retVal = null;
         if(false == typedValue.isEmpty()) {
             try {
                 retVal = typedValue.getAtom(0).castAsString();
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return retVal;
     }

     /**
      * From the input value, creates and returns an XsString
      * @param value must be either a String or a String
      * @return an XsString equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsString or if
      * value is neither a String object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             retVal = XmlTypedValueConversionSupport.convertToXsString(value);
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "StringConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsString.", e);
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
       TypeConverter tc = new StringConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
       handler.addConverter(DEFAULT_TYPE, StringBuffer.class,tc);
       handler.addConverter(DEFAULT_TYPE, Integer.class,tc);
       handler.addConverter(DEFAULT_TYPE, Float.class,tc);
       handler.addConverter(DEFAULT_TYPE, Double.class,tc);
       handler.addConverter(DEFAULT_TYPE, Boolean.class,tc);
       handler.addConverter(DEFAULT_TYPE, Byte.class,tc);
       handler.addConverter(DEFAULT_TYPE, java.util.Date.class,tc);
     }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS    = String.class;
    public static final Class DEFAULT_TYPE  = XsString.class;
}

