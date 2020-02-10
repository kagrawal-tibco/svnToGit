package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsInteger;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 18, 2004
 * Time: 10:31:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntegerConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public IntegerConverter(Class xsdClass,
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
       return Integer.valueOf(value);
    }
     /**
      * Converts the given typedValue object to a BigInteger object
      * @param typedValue should be convertable to a BigInteger, e.g. an XsInteger
      * @return the BigInteger object equivalent of the input typedValue object
      * @throws com.tibco.be.model.types.ConversionException if the input typed value cannot be converted to a
      * BigInteger object
      * @exception com.tibco.be.model.types.ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a BigInteger object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         int retVal=0;
         if(!typedValue.isEmpty()) {
             try {
                 retVal = typedValue.getAtom(0).castAsInt();
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.  What to do?
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("IntegerConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a BigInteger.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return new Integer(retVal);
     }

     /**
      * From the input value, creates and returns an XsInteger
      * @param value must be either a Integer or a String
      * @return an XsInteger equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsInteger or if
      * value is neither a Integer object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             retVal = XmlTypedValueConversionSupport.convertToXsInteger(value);
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "IntegerConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsInteger.", e);
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
        TypeConverter tc = new IntegerConverter(DEFAULT_TYPE, JAVA_CLASS);
        handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = Integer.class;
    public static final Class DEFAULT_TYPE = XsInteger.class;
}

