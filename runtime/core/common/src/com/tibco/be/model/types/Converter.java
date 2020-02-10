package com.tibco.be.model.types;

import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 29, 2006
 * Time: 11:06:54 PM
 * To change this template use File | Settings | File Templates.
 */
public  class Converter {

    static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    static TypeConverter xsd2java_dt_conv= registry.nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
    static TypeRenderer java2xsd_dt_conv= registry.foreignToNative(XsDateTime.class, GregorianCalendar.class);

    /**
     *
     * @param calendar
     * @return
     */
    public static  String writeCalendar(Calendar calendar) {
        try {
            return ((XsDateTime)java2xsd_dt_conv.convertToTypedValue(calendar)).castAsString();
        } catch (Exception e) {
            e.printStackTrace(); //Should never happen
            return null;
        }
    }

    /**
     *
     * @param calendarString
     * @return
     */
    public static  Calendar readCalendar(String calendarString) {
        try {
            return (Calendar) xsd2java_dt_conv.convertSimpleType(calendarString);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  XmlTypedValue getStringProperty(String propertyValue) throws Exception {
        return new XsString(propertyValue);
    }


    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  XmlTypedValue getIntegerProperty(int propertyValue) throws Exception {
        return new XsInt(propertyValue);
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  XmlTypedValue getLongProperty(long propertyValue) throws Exception {
        return new XsLong(propertyValue);
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  XmlTypedValue getDoubleProperty(double propertyValue) throws Exception {
        return new XsDouble(propertyValue);
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  XmlTypedValue getBooleanProperty(boolean propertyValue) throws Exception {
        return propertyValue ? XsBoolean.TRUE: XsBoolean.FALSE;
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  XmlTypedValue getDateTimeProperty(Calendar propertyValue) throws Exception {
        return ((XsDateTime)java2xsd_dt_conv.convertToTypedValue(propertyValue));
    }

    public static XmlTypedValue getDateTimeProperty(Object propertyValue) throws Exception {
        return ((XsDateTime)java2xsd_dt_conv.convertToTypedValue(propertyValue));        
    }

    /**
     *
     * @param propertyValue
     * @throws Exception
     */
    public static  String convertStringProperty(XmlTypedValue propertyValue) throws Exception {
        if(propertyValue == null) return null;
        if (propertyValue.isEmpty()) {
            return "";
        }
        return propertyValue.getAtom(0).castAsString();
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  String convertStringProperty(String propertyValue) throws Exception {
        if(propertyValue == null) return null;
        return propertyValue;
    }

    /**
     *
     * @param propertyValue
     * @throws Exception
     */
    public static  boolean convertBooleanProperty(XmlTypedValue propertyValue) throws Exception {
        return propertyValue.getAtom(0).castAsBoolean();
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  boolean convertBooleanProperty(String propertyValue) throws Exception {
        return com.tibco.be.model.types.xsd.BooleanConverter.valueOf(propertyValue);
    }

    /**
     *
     * @param propertyValue
     * @throws Exception
     */
    public static  int convertIntegerProperty(XmlTypedValue propertyValue) throws Exception {
        return propertyValue.getAtom(0).castAsInt();
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  int convertIntegerProperty(String propertyValue) throws Exception {
        return Integer.valueOf(propertyValue).intValue();
    }

    public static int convertIntegerProperty(Integer propertyValue) throws Exception
    {
        return propertyValue;
    }

    public static int convertIntegerProperty(Long propertyValue) throws Exception {
        return propertyValue.intValue();
    }

    public static int convertIntegerProperty(Short propertyValue) throws Exception {
        return propertyValue.intValue();
    }

    public static int convertIntegerProperty(Double propertyValue) throws Exception {
        return propertyValue.intValue();
    }

    public static int convertIntegerProperty(Float propertyValue) throws Exception {
        return propertyValue.intValue();
    }



    /**
     *
     * @param propertyValue
     * @throws Exception
     */
    public static  long convertLongProperty(XmlTypedValue propertyValue) throws Exception {
        return propertyValue.getAtom(0).castAsLong();
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  long convertLongProperty(String propertyValue) throws Exception {
        return Long.valueOf(propertyValue).longValue();
    }

    /**
     *
     * @param propertyValue
     * @throws Exception
     */
    public static  double convertDoubleProperty(XmlTypedValue propertyValue) throws Exception {
        return propertyValue.getAtom(0).castAsDouble();
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  double convertDoubleProperty(String propertyValue) throws Exception {
        return Double.valueOf(propertyValue).doubleValue();
    }

    /**
     *
     * @param propertyValue
     * @throws Exception
     */
    public static  Calendar convertDateTimeProperty(XmlTypedValue propertyValue) throws Exception {
        if ((propertyValue == null) || propertyValue.isEmpty()) {
            return null;
        }
        return (Calendar) xsd2java_dt_conv.convertSimpleType(propertyValue);
    }

    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  Calendar convertDateTimeProperty(String propertyValue) throws Exception {
        if(propertyValue == null) return null;
        return (Calendar) xsd2java_dt_conv.convertSimpleType(propertyValue);
    }
    /**
     *
     * @param propertyValue
     * @return
     * @throws Exception
     */
    public static  Calendar convertDateTimeProperty(Object propertyValue) throws Exception {

        if (propertyValue == null) return null;

        if (propertyValue instanceof Calendar) {
           return (Calendar)propertyValue;
        }
        if (propertyValue instanceof Long) {
            Calendar cal = Calendar.getInstance();  //Use the default Locale, and TimeZone
            cal.setTimeInMillis(((Long)propertyValue).longValue());
            return cal;
        }
        else if (propertyValue instanceof java.util.Date) {
            Calendar cal = Calendar.getInstance();  //Use the default Locale, and TimeZone
            cal.setTime((Date)propertyValue);
            return cal;
        }
        else if (propertyValue instanceof java.lang.String) {
            //return convertDateTimeProperty((String) propertyValue);
            return readCalendar(propertyValue.toString());
        }
        else if (propertyValue instanceof XmlTypedValue) {
            return convertDateTimeProperty((XmlTypedValue) propertyValue);            
        }
        //TODO with NL - we should support basic
        else {
            throw new Exception("Unsupported type [" + propertyValue.getClass() + "] for Calendar conversion");
        }

    }

}
