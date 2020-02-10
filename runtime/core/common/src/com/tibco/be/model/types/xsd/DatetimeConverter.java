package com.tibco.be.model.types.xsd;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeConverterHandler;
import com.tibco.be.util.TimeZoneCache;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlTypedValueConversionSupport;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsDayTimeDuration;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 17, 2004
 * Time: 5:10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatetimeConverter extends XSDBuiltInConverter{
    //-------------------------------------------------------------------------
    // constructor
    //-------------------------------------------------------------------------
    public DatetimeConverter(Class xsdClass,
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
       XsDateTime xsDateTime = null;
       try {
          xsDateTime = XsDateTime.compile(value);
       }
       catch (XmlAtomicValueParseException e) {
          throw new ConversionException("DateTimeConverter.convertSimpleType: "
                                        + value + " is not a valid DateTime.", null);
       }
       return convertSimpleType(xsDateTime);
    }
     /**
      * Converts the given typedValue object to a DateTime object
      * @param typedValue should be convertable to a DateTime, e.g. an XsDateTime
      * @return the DateTime object equivalent of the input typedValue object
      * @throws ConversionException if the input typed value cannot be converted to a
      * DateTime object
      * @exception ConversionException if the size of the typedValue sequence is not
      * one or if the typedValue object is not of the proper type to be converted
      * to a DateTime object
      */
     public Object convertSimpleType(XmlTypedValue typedValue) throws ConversionException {
        Object retVal = null;
        if(!typedValue.isEmpty()) {
            try {
                XsDateTime xsDateTime = typedValue.getAtom(0).castAsDateTime();
               if(getForeignClass() == GregorianCalendar.class) {
                   retVal = convertToGregorianCalendar(xsDateTime);
               }
               else if(getForeignClass() == java.util.Date.class) {
                  retVal = new java.util.Date(xsDateTime.getTimeSpanSinceEpoch().getTotalMilliSecondsAsLong());
               }
               else if(getForeignClass() == java.sql.Date.class) {
                  retVal = new java.sql.Date(xsDateTime.getTimeSpanSinceEpoch().getTotalMilliSecondsAsLong());
               }
            } catch (XmlAtomicValueCastException e) {
                // Bad cast.  What to do?
                String valueName = typedValue.getClass().getName();
                throw new ConversionException("DateTimeConverter.convertSimpleType: cannot convert a "
                                              + valueName +
                                              " to a GregorianCalendar.", null);
            } catch (ArrayIndexOutOfBoundsException e) {
                // We checked the size; this shouldn't happen.
            }
        }
        return retVal;
     }
     
     public static GregorianCalendar convertToGregorianCalendar(XsDateTime xsDateTime) {
       BigDecimal orig =  xsDateTime.getSeconds();
  	   int seconds = orig.intValue();
  	   GregorianCalendar gc = new GregorianCalendar(
                 xsDateTime.getYear(),
                 xsDateTime.getMonth() + JAVA_UTIL_GREGORIAN_MONTH_OFFSET,
                 xsDateTime.getDayOfMonth(),
                 xsDateTime.getHourOfDay(),
                 xsDateTime.getMinutes(),
                 seconds);
  	   
  	   BigDecimal msecs = orig.subtract(new BigDecimal(seconds));
  	   msecs = msecs.multiply(new BigDecimal(1000));
  	   int millis = msecs.intValue();
  	   gc.set(Calendar.MILLISECOND, millis);
  	   
         // Let's not forget the timezone!
         TimeZone tz = xsDateTime.getTimeZone();
         if (null != tz) {
             gc.setTimeZone(TimeZoneCache.getCachedTz(tz.getID()));
         }
         return gc;
     }

     /**
      * From the input value, creates and returns an XsDateTime
      * @param value must be either a DateTime or a String
      * @return an XsDateTime equivalent to the input value
      * @throws ConversionException if value cannot be converted to XsDateTime or if
      * value is neither a DateTime object or a String object
      */
     public XmlTypedValue convertToTypedValue(Object value) throws ConversionException {
         XmlTypedValue retVal = null;
         try {
             if (value instanceof GregorianCalendar) {
                 GregorianCalendar gc = (GregorianCalendar) value;
                 retVal = new XsDateTime(gc.getTimeInMillis(), XsDayTimeDuration.makeDayTimeDuration(gc.getTimeZone().getOffset(gc.getTimeInMillis())));
             } else {
                 retVal = XmlTypedValueConversionSupport.convertToXsDateTime(value);
             }
         } catch (XmlAtomicValueParseException e) {
             throw new ConversionException("DateTimeConverter.convertToTypedValue: " + value + " cannot be converted to an XsDateTime.", e);
         }
         return retVal;
     }
    
    /**
     * From the input value, creates and returns an XsDateTime
     * @param value must be either a DateTime or a String
     * @return an XsDateTime equivalent to the input value
     * @throws ConversionException if value cannot be converted to XsDateTime or if
     * value is neither a DateTime object or a String object
     */
    public String convertToString(Object value) throws ConversionException {
       XsDateTime dateTime = (XsDateTime)(convertToTypedValue(value));
       String retVal = null;
       retVal = dateTime.castAsString();
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
        TypeConverter tc = new DatetimeConverter(DEFAULT_TYPE, JAVA_CLASS);
        handler.addConverter(tc);
        tc = new DatetimeConverter(DEFAULT_TYPE, java.sql.Date.class);
        handler.addConverter(tc);
        tc = new DatetimeConverter(DEFAULT_TYPE, GregorianCalendar.class);
        handler.addConverter(tc);
    }

    //-------------------------------------------------------------------------
    // member attributes
    //-------------------------------------------------------------------------
    public static final Class JAVA_CLASS = java.util.Date.class;
    public static final Class DEFAULT_TYPE = XsDateTime.class;
    public static final int JAVA_UTIL_GREGORIAN_MONTH_OFFSET = -1;
}

