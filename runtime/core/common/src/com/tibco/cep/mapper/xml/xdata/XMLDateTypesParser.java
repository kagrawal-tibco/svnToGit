/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Date string utility class.  Code copied and modified from adapter SDK source.
 */
public class XMLDateTypesParser
{
    //private static final TimeZone DefaultTimeZone = TimeZone.getDefault();
    private static final TimeZone UTCTimeZone = TimeZone.getTimeZone("GMT");

    public static final String DATE_TIME_FORMAT     =   "yyyy-MM-dd'T'HH:mm:ss";
    public static final String TIME_FORMAT          =   "HH:mm:ss";
    public static final String DATE_FORMAT          =   "yyyy-MM-dd";
    public static final String YEAR_MONTH_FORMAT    =   "yyyy-MM";
    public static final String YEAR_FORMAT          =   "yyyy";
    public static final String MONTH_DAY_FORMAT     =   "--MM-dd";
    public static final String DAY_FORMAT           =   "---dd";
    public static final String MONTH_FORMAT         =   "--MM--";

    public static final DateTimeZonePair parseDateTime(String dateStr)
        throws ParseException
    {
//        return ISODateTimeParser.parseISO8601Date(dateStr);
        return createDateTime(dateStr, DATE_TIME_FORMAT);
    }

    public static final DateTimeZonePair parseTime(String timeStr)
        throws ParseException
    {
        return createTime(timeStr, TIME_FORMAT);
    }

    public static final DateTimeZonePair parseDate(String dateStr)
        throws ParseException
    {
        return createDate(dateStr, DATE_FORMAT);
    }

    public static final DateTimeZonePair parseGYearMonth(String dateStr)
        throws ParseException
    {
        return createDate(dateStr, YEAR_MONTH_FORMAT);
    }

    public static final DateTimeZonePair parseGYear(String dateStr)
        throws ParseException
    {
        return createDate(dateStr, YEAR_FORMAT);
    }

    public static final DateTimeZonePair parseGMonthDay(String dateStr)
        throws ParseException
    {
        return createDate(dateStr, MONTH_DAY_FORMAT);
    }

    public static final DateTimeZonePair parseGDay(String dateStr)
        throws ParseException
    {
        return createDate(dateStr, DAY_FORMAT);
    }

    public static final DateTimeZonePair parseGMonth(String dateStr)
        throws ParseException
    {
        return createDate(dateStr, MONTH_FORMAT);
    }

    /*
     * Helper methods.
     */

    /**
     * Returns a timezone adjusted string if necessary.
     * @return String returns null if there was no adjustment.
     */
    protected static final String adjustTimezone(String dateStr)
    {
        int length = dateStr.length();
        if (dateStr.charAt(length-1) == 'Z')
        {
            dateStr = dateStr.substring(0, length-1) + "+00:00";
        }
//        else if (dateStr.charAt(length-3) == ':')
//        {
//            char ch = dateStr.charAt(length-6);
//            if (ch == '-' || ch == '+')
//            {
//                dateStr = dateStr.substring(0, length-3) + dateStr.substring(length-2);
//                return dateStr;
//            }
//        }
        return dateStr;
    }

    /**
     * Extracts the timezone substring from a XML/ISO 8601 style date.
     */
    public static final String getTimeZoneString(String dateStr)
    {
        if (dateStr == null || !hasTimezone(dateStr))
        {
            return "";  // "if $srcval does not contain a timezone, the result is the empty sequence"
        }
        dateStr = adjustTimezone(dateStr);
        int length = dateStr.length();
        return dateStr.substring(length-6);
    }

    public static final String stripTimeZoneString(String dateStr)
    {
        if (dateStr == null || !hasTimezone(dateStr))
        {
            return dateStr;  // "if $srcval does not contain a timezone, the result is the empty sequence"
        }
        dateStr = adjustTimezone(dateStr);
        int length = dateStr.length();
        return dateStr.substring(0,length-6);
    }

    /**
     * Extracts a timezone from a XML/ISO 8601 style date.
     */
    public static final TimeZone getTimeZone(String dateStr)
    {
        if (dateStr == null || !hasTimezone(dateStr))
        {
            return UTCTimeZone;
        }
        String timeZoneStr = "GMT"+getTimeZoneString(dateStr);
        return TimeZone.getTimeZone(timeZoneStr);
    }

    /**
     * Extracts a timezone from a date formated w/ the given format string from the dateStr.
     */
    public static final TimeZone getTimeZone(String formatStr, String dateStr)
    {
        boolean sawAmp =false;
        boolean sawTimeZone =false;
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        sdf.setLenient(false);
        for (int i=0; i < formatStr.length(); i++)
        {
            char c = formatStr.charAt(i);
            if (c == '\'')
            {
                sawAmp = !sawAmp;
            }
            else if (c == 'z')
            {
                if (!sawAmp)
                {
                    sawTimeZone = true;
                }
            }
        }

        // Hunt for the TimeZone if there is one.
        if (sawTimeZone == false)
        {
            return null;
        }
        return TimeZone.getDefault();   // LAMb: Should parse out the timezone, but that would require
                                        // parsing the entire string. Unfortunatly, SimpleDateFormat parsing
                                        // methods are private. For now documentation will not support timezone.
    }

    /**
     * Constructs a timezone IDentifier from the offset.
     * Format:  GMT[+|-]hh[[:]mm]
     * @param hours hours positive/negative offset from GMT.
     * @param minutes minutes offset from GMT.
     */
    public static final String getTimeZoneID(int hours, int minutes)
    {
        StringBuffer id = new StringBuffer();
        id.append("GMT");

        if (hours == 0 && minutes == 0)
        {
            return id.toString();
        }

        if (hours >= 0)
        {
            id.append('+');
        }
        else
        {
            id.append('-');
        }

        if (Math.abs(hours) < 10)
        {
            id.append('0');
        }
        id.append(Math.abs(hours));

        id.append(':');

        if (minutes < 10)
        {
            id.append('0');
        }
        id.append(minutes);

        return id.toString();
    }

    /**
     * Ges a timezone from an hour/minute offset.
     */
    public static TimeZone getTimeZone(int hours, int minutes)
    {
        int tzOffset = ((hours * 60 * 60) + (minutes * 60)) * 1000;
        String tzID = XMLDateTypesParser.getTimeZoneID(hours, minutes);
        TimeZone tz = new SimpleTimeZone(tzOffset, tzID);
        return tz;
    }

    /**
     * Returns true if this dateStr has a timezone associated with it.
     */
    public static final boolean hasTimezone(String dateStr)
    {
        int length = dateStr.length();
        if (dateStr.charAt(length-1) == 'Z')
        {
            return true;
        }
        if (dateStr.charAt(length-3) == ':')
        {
            char ch = dateStr.charAt(length-6);
            if (ch == '-' || ch == '+')
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this dateStr has a timezone associated with it.
     */
    protected static final boolean hasMilliSeconds(String dateStr)
    {
        boolean hasMilliSeconds =false;
        if (dateStr.indexOf(".") != -1)
        {
            hasMilliSeconds = true;
        }
        return hasMilliSeconds;
    }

    /**
     * Parses out a dateStr w/ the given format. The format is picked based
     * on whether the dateStr has the optional timezone associated with it.
     * @param dateStr String to convert to a java Date.
     * @return Date Returns a java Date created from the dateStr.
     */
    protected static DateTimeZonePair createDate(String dateStr, String baseFormat)
        throws ParseException
    {
        // Assumes formats has two entries.
        if (hasTimezone(dateStr))
        {
            //baseFormat += "z";
            dateStr = adjustTimezone(dateStr);
        }

        // Create the date from the date format.
        DateFormat sdf = new SimpleDateFormat(baseFormat);
        sdf.setLenient(false);
        TimeZone tz = getTimeZone(dateStr);
        sdf.setTimeZone(tz);
        dateStr = stripTimeZoneString(dateStr);
        Date date = sdf.parse(dateStr);
        return new DateTimeZonePair(date, tz);
    }

    /**
     * Parses out a dateStr w/ the given format. The format is picked based
     * on whether the dateStr has the optional timezone associated with it.
     * @param dateStr String to convert to a java Date.
     * @param baseFormat represents the base format for a date/time string.
     * @return Date Returns a java Date created from the dateStr.
     */
    protected static DateTimeZonePair createDateTime(String dateStr, String baseFormat)
        throws ParseException
    {
        if (hasMilliSeconds(dateStr))
        {
            baseFormat += ".SSS";
        }
        return createDate(dateStr, baseFormat);
    }

    /**
     * Parses out a time w/ the given format.
     * @param timeStr String to convert to a java Date.
     * @param baseFormat format string for the timeStr
     * @return Date Returns a java Date created from the timeStr.
     */
    protected static DateTimeZonePair createTime(String timeStr, String baseFormat)
        throws ParseException
    {
        return createDateTime(timeStr, baseFormat);
    }

    public static Date createDate(int year, int month, int day, int hour, int minute, int second, int milli)
    {
        Calendar cal = new GregorianCalendar();
        return createDate(cal, year, month, day, hour, minute, second, milli);
    }

    public static Date createDateGMT(int year, int month, int day, int hour, int minute, int second, int milli)
    {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        return createDate(cal, year, month, day, hour, minute, second, milli);
    }

    public static Date createDateWithTimeZone(int year, int month, int day, int hour, int minute, int second, int milli, TimeZone tz)
    {
        Calendar cal = new GregorianCalendar(tz);
        return createDate(cal, year, month, day, hour, minute, second, milli);
    }

    protected static Date createDate(Calendar cal, int year, int month, int day, int hour, int minute, int second, int milli)
    {
        cal.setLenient(false);
        cal.set(year, month-1, day, hour, minute, second);
        cal.set(Calendar.MILLISECOND, milli);
        try
        {
            Date date = cal.getTime();
            return date;
        }
        catch (IllegalArgumentException ex)
        {
            throw new IllegalArgumentException("Invalid date: " + year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second + "." + milli);
        }
    }

    public static Date createDate(int year, int month, int day)
    {
        return createDate(year, month, day, 0, 0, 0, 0);
    }

    public static Date createTime(int hour, int minute, int second, int milli)
    {
        return createDate(1970, 1, 1, hour, minute, second, milli);
    }

    /**
     * Gets the timezone string from a TimeZone object.
     */
    public static String getTimeZoneString(Date date, TimeZone tz)
    {
        if (tz == null)
        {
            return "";
        }
        int rawTimeZone = getTimeZoneOffset(tz,date.getTime());
        rawTimeZone /= 1000;
        int hours = Math.abs(rawTimeZone / 3600);
        int minutes = Math.abs(rawTimeZone % 3600) / 60;
        String time;

        StringBuffer tzStr = new StringBuffer();
        tzStr.append(rawTimeZone >= 0 ? "+" : "-");
        time = String.valueOf(hours);
        if (time.length() == 1)
        {
            tzStr.append("0");
        }
        tzStr.append(time);
        tzStr.append(":");
        time = String.valueOf(minutes);
        if (time.length() == 1)
        {
            tzStr.append("0");
        }
        tzStr.append(time);
        return tzStr.toString();
    }

    public static String getTimeZoneString(DateTimeZonePair dtz)
    {
        return getTimeZoneString(dtz.getDate(), dtz.getTimeZone());
    }

    /**
     * Gets the Date as a dateTime string.
     */
    public static String formatAsDateTime(DateTimeZonePair dateTz)
    {
        return formatAsDateTime(dateTz.getDate(), dateTz.getTimeZone());
    }

    public static String formatAsDateTime(Date date, TimeZone timeZone)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT + ".SSS");
        sdf.setLenient(false);
        if (timeZone != null)
        {
            sdf.setTimeZone(timeZone);
        }
        String dateTimeStr = sdf.format(date);
        if (dateTimeStr.endsWith(".000"))
        {
            dateTimeStr = dateTimeStr.substring(0, dateTimeStr.length()-4);
        }
        String result = dateTimeStr + XMLDateTypesParser.getTimeZoneString(date, timeZone);
        return result;
    }

    /**
     * Gets the Date as a dateTime string.
     */
    public static String formatAsDate(DateTimeZonePair dateTz)
    {
        return formatAsDate(dateTz.getDate(), dateTz.getTimeZone());
    }

    public static String formatAsDate(Date date, TimeZone timeZone)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        if (timeZone != null)
        {
            sdf.setTimeZone(timeZone);
        }
        return sdf.format(date) + XMLDateTypesParser.getTimeZoneString(date, timeZone);
    }

    public static String formatAsDateNoTimezone(Date date, TimeZone timeZone)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        if (timeZone != null)
        {
            sdf.setTimeZone(timeZone);
        }
        return sdf.format(date);
    }

    /**
     * Gets the Date as a dateTime string.
     *
     */
//    public static String formatAsTime(Date date)
//    {
//        return formatAsTime(date, UTCTimeZone);
//    }

    public static String formatAsTime(DateTimeZonePair dateTz)
    {
        return formatAsTime(dateTz.getDate(), dateTz.getTimeZone());
    }

    public static String formatAsTime(Date date, TimeZone timeZone)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT + ".SSS");
        sdf.setLenient(false);
        if (timeZone != null)
        {
            sdf.setTimeZone(timeZone);
        }
        String timeStr= sdf.format(date);
        if (timeStr.endsWith(".000"))
        {
            timeStr = timeStr.substring(0, timeStr.length()-4);
        }
        return timeStr;
    }

    public static String formatAsTimeWithTimezone(Date date, TimeZone timeZone)
    {
        return formatAsTime(date,timeZone) + XMLDateTypesParser.getTimeZoneString(date, timeZone);
    }

    public static String formatAsDateTimeNoTimezone(Date date, TimeZone timeZone)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT + ".SSS");
        sdf.setLenient(false);
        if (timeZone != null)
        {
            sdf.setTimeZone(timeZone);
        }
        String dateTimeStr = sdf.format(date);
        if (dateTimeStr.endsWith(".000"))
        {
            dateTimeStr = dateTimeStr.substring(0, dateTimeStr.length()-4);
        }
        return dateTimeStr;
    }


    /**
     * JDK 1.3 or JDK 1.4 independent way of getting time zone offset.
     * @param tz
     * @param date
     * @return
     */
    static int getTimeZoneOffset(TimeZone tz, long date)
    {
        // For JDK1.4, can't use the JDK1.3 hack, but can't use the new method directly, either.  Use reflection.
        if (JDK14_TIME_ZONE_METHOD!=null)
        {
            // This is JDK1.4
            try
            {
                return ((Integer)JDK14_TIME_ZONE_METHOD.invoke(tz,new Object[] {new Long(date)})).intValue();
            }
            catch (Throwable t)
            {
                System.err.println("Internal error with timezone in JDK1.4");
                t.printStackTrace(System.err);
                throw new RuntimeException(t.getMessage());
            }
        }
        else
        {
            // JDK 1.3
            int offset = tz.getRawOffset();
            if (tz.inDaylightTime(new Date(date)))
            {
                if (tz instanceof SimpleTimeZone)
                {
                    {
                        if (tz instanceof SimpleTimeZone) // in JDK1.3 was always this way & this was the only way to get the info.
                        {
                            SimpleTimeZone stz = (SimpleTimeZone)tz;
                            offset += stz.getDSTSavings();
                        }
                    }
                }
            }
            return offset;
        }
    }

    private static Method JDK14_TIME_ZONE_METHOD;

    static
    {
        try
        {
            JDK14_TIME_ZONE_METHOD = TimeZone.class.getMethod("getOffset",new Class[] {Long.TYPE});
        }
        catch (NoSuchMethodException ns)
        {
            JDK14_TIME_ZONE_METHOD = null;
        }
    }
}
