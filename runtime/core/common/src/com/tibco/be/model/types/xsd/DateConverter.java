package com.tibco.be.model.types.xsd;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsDate;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 18, 2004
 * Time: 9:49:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class DateConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public DateConverter(Class xsdClass,
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
    public Object convertSimpleType(String value) throws ConversionException {
       XsDate xsDate = null;
       try {
          xsDate = XsDate.compile(value);
       }
       catch (XmlAtomicValueParseException e) {
          throw new ConversionException("DateConverter.convertSimpleType: "
                                        + value + " is not a valid date.", null);
       }
       return convertSimpleType(xsDate);
    }
     /**
      * Converts the given typedValue object to a Date object
      * @param typedValue should be convertable to a Date, e.g. an XsDate
      * @return the Date object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * Date object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a Date object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
         Object retVal = null;
         if(!typedValue.isEmpty()) {
             try {
                 XsDate xsDate = typedValue.getAtom(0).castAsDate();
                if(getForeignClass() == GregorianCalendar.class) {
                   retVal = new GregorianCalendar(
                           xsDate.getYear(),
                           xsDate.getMonth() + JAVA_UTIL_GREGORIAN_MONTH_OFFSET,
                           xsDate.getDayOfMonth());
                }
                else if(getForeignClass() == java.util.Date.class) {
                   retVal = new java.util.Date(xsDate.getTimeSpanSinceEpoch().getTotalMilliSecondsAsLong());
                }
                else if(getForeignClass() == java.sql.Date.class) {
                   retVal = new java.sql.Date(xsDate.getTimeSpanSinceEpoch().getTotalMilliSecondsAsLong());
                }
             } catch (XmlAtomicValueCastException e) {
                 // Bad cast.  What to do?
                 String valueName = typedValue.getClass().getName();
                 throw new ConversionException("DateConverter.convertSimpleType: cannot convert a "
                                               + valueName +
                                               " to a GregorianCalendar.", null);
             } catch (ArrayIndexOutOfBoundsException e) {
                 // We checked the size; this shouldn't happen.
             }
         }
         return retVal;
     }

     /**
      * From the input value, creates and returns an XsDate
      * @param value must be either a Date or a String
      * @return an XsDate equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsDate or if
      * value is neither a Date object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             if (value instanceof java.util.Date) {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTimeInMillis(((Date) value).getTime());
                gc.setTimeZone(TimeZone.getDefault());
                retVal =XmlTypedValueConversionSupport.convertToXsDate(gc);
             } else {
                retVal = XmlTypedValueConversionSupport.convertToXsDate(value);
             }
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException(
                     "DateConverter.convertToTypedValue: " + value
                     + " cannot be converted to an XsDate.", e);
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
       TypeConverter tc = new DateConverter(DEFAULT_TYPE, JAVA_CLASS);
       handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = java.util.Date.class;
    public static final Class DEFAULT_TYPE = XsDate.class;
    public static final int JAVA_UTIL_GREGORIAN_MONTH_OFFSET = -1;
}


