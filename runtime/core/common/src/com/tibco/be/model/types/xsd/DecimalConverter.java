package com.tibco.be.model.types.xsd;

import java.math.BigDecimal;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 5:55:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class DecimalConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public DecimalConverter(Class xsdClass,
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
       return new Double(value);
    }
     /**
      * Converts the given typedValue object to a BigDecimal object
      * @param typedValue should be convertable to a BigDecimal, e.g. an XsDecimal
      * @return the BigDecimal object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * BigDecimal object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a BigDecimal object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         if(!typedValue.isEmpty()) {
        	 BigDecimal retVal = null;
             try {
                 retVal = typedValue.getAtom(0).castAsDecimal();
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("DecimalConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a BigDecimal.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
             return new Double(retVal.doubleValue());
         } else {
        	 return null;
         }
     }

     /**
      * From the input value, creates and returns an XsDecimal
      * @param value must be either a BigDecimal or a String
      * @return an XsDecimal equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsDecimal or if
      * value is neither a BigDecimal object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             retVal = XmlTypedValueConversionSupport.convertToXsDecimal(value);
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "DecimalConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsDecimal.", e);
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
       TypeConverter tc = new DecimalConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = Double.class;
    public static final Class DEFAULT_TYPE = XsDecimal.class;
}

