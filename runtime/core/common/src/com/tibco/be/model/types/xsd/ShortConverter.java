package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsShort;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 4:03:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShortConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public ShortConverter(Class xsdClass,
                         Class javaClass) {
       super(xsdClass, javaClass);
    }

    /**
     * Converts a simple XML value from a String to a Java object
     *
     * @param value the conversion input, a well-formed XML String
     * @return the converted Object, i.e. the conversion output
     **/
    public Object convertSimpleType(String value) {
       return new Short(value);
    }
     /**
      * Converts the given typedValue object to a Short object
      * @param typedValue should be convertable to a Short, e.g. an XsShort
      * @return the Short object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * Short object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a Short object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         Short retVal = null;
         if(!typedValue.isEmpty()) {
             try {
                 retVal = new Short(typedValue.getAtom(0).castAsShort());
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.  What to do?
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("ShortConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a Short.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return retVal;
     }

     /**
      * From the input value, creates and returns an XsShort
      * @param value must be either a Short or a String
      * @return an XsShort equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsShort or if
      * value is neither a Short object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             retVal = XmlTypedValueConversionSupport.convertToXsShort(value);
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "ShortConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsShort.", e);
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
       TypeConverter tc = new ShortConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = Short.class;
    public static final Class DEFAULT_TYPE = XsShort.class;
}

