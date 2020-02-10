package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsLong;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 3:28:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public LongConverter(Class xsdClass,
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
       return new Long(value);
    }
     /**
      * Converts the given typedValue object to a Long object
      * @param typedValue should be convertable to a Long, e.g. an XsLong
      * @return the Long object equivalent of the input typedValue object
      * @throws com.tibco.be.model.types.ConversionException if the input typed value cannot be converted to a
      * Long object
      * @exception com.tibco.be.model.types if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a Long object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         Long retVal = null;
         if(!typedValue.isEmpty()) {
             try {
                 retVal = new Long(typedValue.getAtom(0).castAsLong());
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.  What to do?
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("LongConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a Long.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return retVal;
     }

     /**
      * From the input value, creates and returns an XsLong
      * @param value must be either a Long or a String
      * @return an XsLong equivalent to the input value
      * @throws com.tibco.be.model.types.ConversionException if value cannot be converted to XsLong or if
      * value is neither a Long object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             retVal = XmlTypedValueConversionSupport.convertToXsLong(value);
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "LongConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsLong.", e);
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
       TypeConverter tc = new LongConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = Long.class;
    public static final Class DEFAULT_TYPE = XsLong.class;
}
