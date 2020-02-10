package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsFloat;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 5:05:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class FloatConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public FloatConverter(Class xsdClass,
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
       return new Float(value);
    }

     /**
      * Converts the given typedValue object to a Float object
      * @param typedValue should be convertable to a Float, e.g. an XsFloat
      * @return the Float object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * Float object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a Float object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         Float retVal = null;
         if(!typedValue.isEmpty()) {
             try {
                 retVal = new Float(typedValue.getAtom(0).castAsFloat());
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.  What to do?
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("FloatConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a Float.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return retVal;
     }

     /**
      * From the input value, creates and returns an XsFloat
      * @param value must be either a Float or a String
      * @return an XsFloat equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsFloat or if
      * value is neither a Float object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             retVal = XmlTypedValueConversionSupport.convertToXsFloat(value);
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "FloatConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsFloat.", e);
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
       TypeConverter tc = new FloatConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = Float.class;
    public static final Class DEFAULT_TYPE = XsFloat.class;
}


