package com.tibco.be.model.types.xsd;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsByte;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 4:18:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ByteConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public ByteConverter(Class xsdClass,
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
       return new Byte(value);
    }
     /**
      * Converts the given typedValue object to a Byte object
      * @param typedValue should be convertable to a Byte, e.g. an XsByte
      * @return the Byte object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * Byte object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a Byte object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         Byte retVal = null;
         if(!typedValue.isEmpty()) {
             try {
                 retVal = new Byte(typedValue.getAtom(0).castAsByte());
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.  What to do?
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("ByteConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a Byte.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return retVal;
     }

     /**
      * From the input value, creates and returns an XsByte
      * @param value must be either a Byte or a String
      * @return an XsByte equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsByte or if
      * value is neither a Byte object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         if(value instanceof Byte) {
             retVal = new XsByte((Byte)value);
         }
         else if(value instanceof String) {
             try {
                 retVal = XsByte.compile((String)value);
             } catch (XmlAtomicValueParseException e) {
                 throw new ConversionException(
                         "ByteConverter.convertToTypedValue: " + value
                         + " cannot be converted to an XsByte.", e);
             }
         }
         else {
             String className = value != null ? value.getClass().getName() : "NULL";
             throw new ConversionException("ByteConverter.convertToTypedValue: cannot convert a "
                                           + className + " to a XsByte.", null);
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
       TypeConverter tc = new ByteConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = Byte.class;
    public static final Class DEFAULT_TYPE = XsByte.class;
}

