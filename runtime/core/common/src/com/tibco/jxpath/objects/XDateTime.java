package com.tibco.jxpath.objects;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;

public class XDateTime extends XGregorian {

    static public final ExpandedName NAME = ExpandedName.makeName(CONSTRUCTORS_URI, "dateTime");

    private final int m_year;
    private final int m_month;
    private final int m_dayOfMonth;
    private final int m_hour;
    private final int m_minute;
    private final BigDecimal m_second;
    private int m_hashCode = 0;
    private TimeZone m_offset;

    public XDateTime(int year, int month, int dayOfMonth, int hour, int minute, BigDecimal second)
    {
        // Make it explicit that this is just a convenience method.
        this(year, month, dayOfMonth, hour, minute, second, null);
    }

    public XDateTime(int year, int month, int dayOfMonth, int hour, int minute, BigDecimal second, TimeZone offset)
    {
        super(offset);

        m_year = year;
        m_month = month;
        m_dayOfMonth = dayOfMonth;
        m_hour = hour;
        m_minute = minute;
        m_second = (second == null ? BigDecimal.ZERO : second);//.intValue();//TimeSpan.create(0L, 0, second);
    }

    /**
     * Constructs a dateTime instant from a millisecond duration since the 1970-01-01 GMT epoch
     * and a timezone offset. The offset allows fractional milliseconds of arbitrary precision.
     *
     * @param timeInMillis Duration in milliseconds since 1970-01-01 GMT epoch.
     * @param offset       A timezone offset.
     */
    public XDateTime(BigDecimal timeInMillis, TimeZone offset)
    {
        super(offset);

        GregorianCalendar cl = new GregorianCalendar(offset);

        long integralMillisSinceEpoch = timeInMillis.longValue();

        cl.setTime(new Date(integralMillisSinceEpoch));

        m_year = cl.get(Calendar.YEAR);
        m_month = cl.get(Calendar.MONTH) + 1;
        m_dayOfMonth = cl.get(Calendar.DAY_OF_MONTH);
        m_hour = cl.get(Calendar.HOUR_OF_DAY);
        m_minute = cl.get(Calendar.MINUTE);

        m_second = new BigDecimal((1000L * cl.get(Calendar.SECOND)) + cl.get(Calendar.MILLISECOND)).divide(new BigDecimal(1000));
    }

    /**
     * Constructs a dateTime instant from a millisecond duration (since the 1970-01-01 GMT epoch)
     * and a timezone offset. This method is useful for converting from {@link Date} in which case
     * the {@link Date#getTime} method supplied the millisecond duration since the epoch.
     *
     * @param timeInMillis Duration in milliseconds since 1970-01-01 GMT epoch.
     * @param offset       A timezone offset.
     */
    public XDateTime(long timeInMillis, TimeZone offset)
    {
        // Make it clear that this is just a convenience method.
        this(new Date(timeInMillis), offset);
    }

    public XDateTime(Date dateTime, TimeZone offset)
    {
        super(offset);

        GregorianCalendar cl = new GregorianCalendar(offset);

        cl.setTime(dateTime);

        m_year = cl.get(Calendar.YEAR);
        m_month = cl.get(Calendar.MONTH) + 1;
        m_dayOfMonth = cl.get(Calendar.DAY_OF_MONTH);
        m_hour = cl.get(Calendar.HOUR_OF_DAY);
        m_minute = cl.get(Calendar.MINUTE);

        m_second = new BigDecimal((1000L * cl.get(Calendar.SECOND)) + cl.get(Calendar.MILLISECOND)).divide(new BigDecimal(1000));
        m_offset = offset;
    }

    @Override
	public Object object() {
        GregorianCalendar cl = new GregorianCalendar(m_offset);
        cl.set(m_year, m_month-1, m_dayOfMonth, m_hour, m_minute, m_second.intValue());
		return cl;
	}

	public int getYear()
    {
        return m_year;
    }

    public int getMonth()
    {
        return m_month;
    }

    public int getDayOfMonth()
    {
        return m_dayOfMonth;
    }

    public int getHourOfDay()
    {
        return m_hour;
    }

    public int getMinutes()
    {
        return m_minute;
    }

    public BigDecimal getSeconds()
    {
        return m_second;//BigDecimal.valueOf(m_second);
    }

    /**
     * Compiles a dateTime with the format [-]YYYY-MM-DDTHH:MM:SS.SSS...[Z|(+|-)HH:MM]
     *
     * @param strval The dateTime string.
     * @return The compiled dateTime.
     * @throws XmlAtomicValueParseException If the format is invalid.
     */
    public static XDateTime compile(String strval) throws XPathValueParseException
    {
        if (null == strval)
        {
            throw new IllegalArgumentException();
        }
        else if (strval.equals(""))
        {
        	throw new XPathValueParseException("length is zero");
        }

        // A full collapse isn't going to help so just trim.
        String collapsed = strval.trim();

        int i = 0;
        int year;
        int month;
        int dayOfMonth;
        int hourOfDay;
        int minutes;

        boolean negative = false;

        if (collapsed.startsWith("-"))
        {
            negative = true;
            i += 1;
        }

        int separator = collapsed.indexOf("-", i);

        if (separator == -1)
        {
        	throw new XPathValueParseException(collapsed);
        }
        else
        {
            year = parseIntField(collapsed, i, separator, NAME);
            if ((separator - i < 4) //ensure four (or more) digit years
                    || (collapsed.charAt(i) == '0' && year > 999)//no unnecessary leading '0'
                    || year == 0)
            {
                //The year 0000 is prohibited http://www.w3.org/TR/xmlschema-2/#dateTime
            	throw new XPathValueParseException(collapsed, NAME);
            }
            i = separator + 1;
            separator = collapsed.indexOf("-", i);
            if (separator == -1)
            {
            	throw new XPathValueParseException(collapsed, NAME);
            }
            else
            {
                // YYYY-MM-DD
                month = parseIntField(collapsed, i, i + 2, NAME);
                if (separator != i + 2)
                {
                    throw new XPathValueParseException(collapsed, NAME);
                }
                i += 3;
                dayOfMonth = parseIntField(collapsed, i, i + 2, NAME);
                i += 2;
            }
        }

        if (negative)
        {
            year = -year;
        }

        try
        {
            if (collapsed.charAt(i) == 'T')
            {
                i += 1;
            }
            else
            {
            	throw new XPathValueParseException(collapsed, NAME);
            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
        	throw new XPathValueParseException(null, null, e);
        }

        hourOfDay = parseIntField(collapsed, i, i + 2, NAME);
        i += 2;

        try
        {
            if (collapsed.charAt(i) == ':')
            {
                i += 1;
            }
            else
            {
            	throw new XPathValueParseException(collapsed, NAME);
            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
        	throw new XPathValueParseException(collapsed, NAME);
        }

        minutes = parseIntField(collapsed, i, i + 2, NAME);
        i += 2;

        try
        {
            if (collapsed.charAt(i) == ':')
            {
                i += 1;
            }
            else
            {
            	throw new XPathValueParseException(collapsed, NAME);
            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
        	throw new XPathValueParseException(collapsed, NAME);
        }

        BigDecimal seconds;
        {
            int beginIndex = i;
            i += 2;

            int trailingZeros = 0;

            if (collapsed.startsWith(".", i))
            {
                i += 1;

                int digit;
                while (i < collapsed.length() && (-1 != (digit = decDigit(collapsed.charAt(i)))))
                {
                    if (0 == digit)
                    {
                        trailingZeros++;
                    }
                    else
                    {
                        trailingZeros = 0;
                    }
                    i += 1;
                }
            }

            try
            {
                seconds = new BigDecimal(collapsed.substring(beginIndex, i - trailingZeros));
            }
            catch (RuntimeException e)
            {
            	throw new XPathValueParseException(null,null,e);
            }
        }

        if (collapsed.length() > i)
        {
            TimeZone offset = parseTimeZoneToDayTimeDuration(collapsed, i, NAME);

//            try
//            {
                return create(year, month, dayOfMonth, hourOfDay, minutes, seconds, offset);
//            }
//            catch (XPathFunctionException e)
//            {
//            	throw new XPathValueParseException(null,null,e);
//            }
        }
        else
        {
//            try
//            {
                return create(year, month, dayOfMonth, hourOfDay, minutes, seconds, null);
//            }
//            catch (XPathFunctionException e)
//            {
//            	throw new XPathValueParseException(null,null,e);
//            }
        }
    }

    public static XDateTime create(int year, int month, int dayOfMonth, int hourOfDay, int minutes, BigDecimal seconds, TimeZone offset) throws XPathValueParseException
    {
        checkYear(year);
        checkMonth(month);
        checkDayOfMonth(dayOfMonth);
        checkHourOfDay(hourOfDay);
        checkMinutes(minutes);
        checkSeconds(seconds);

        if (null != offset)
        {
            checkOffset(offset);
        }

        checkCalendar(year, month, dayOfMonth);

        return new XDateTime(year, month, dayOfMonth, hourOfDay, minutes, seconds, offset);
    }

    public static XDateTime currentDateTime()
    {
        Date date = new Date();

        TimeZone timezone = TimeZone.getDefault();

//        TimeZone offset = TimeZoneSupport.getDayTimeDuration(timezone, date);

        return new XDateTime(date, timezone);
    }

    public String castAsString()
    {
        return XGregorianFormat.dateTime.format(this, m_offset);
    }

    public XDateTime castAsDateTime() throws XPathValueParseException
    {
        return this;
    }

    public boolean equals(Object object)
    {
        if (object == this)
        {
            return true;
        }
        else if (object instanceof XDateTime)
        {
            XDateTime dateTime = (XDateTime)object;

            final boolean hasOffset = (null != m_offset);

            if (hasOffset == (null != dateTime.m_offset)) //both have (or not) offset
            {
                if (hasOffset)
                {
                    if (m_offset.equals(dateTime.m_offset))
                    {
                        return (m_year == dateTime.m_year &&
                                m_month == dateTime.m_month &&
                                m_dayOfMonth == dateTime.m_dayOfMonth &&
                                m_hour == dateTime.m_hour &&
                                m_minute == dateTime.m_minute &&
                                m_second == dateTime.m_second);
                    }
                    else
                    {
//                        TimeSpan thisTime = getTimeSpanSinceEpoch();
//                        TimeSpan thatTime = dateTime.getTimeSpanSinceEpoch();
//
//                        return thisTime.equals(thatTime);
                    }
                }
                else
                {
                    return (m_year == dateTime.m_year &&
                            m_month == dateTime.m_month &&
                            m_dayOfMonth == dateTime.m_dayOfMonth &&
                            m_hour == dateTime.m_hour &&
                            m_minute == dateTime.m_minute &&
                            m_second == dateTime.m_second);
                }
            }
        }
        return false;
    }

    /**
     * Compares two xs:dateTime values for equality.
     *
     * @param dateTime The other xs:dateTime to compare to this one. Cannot be null.
     * @param context  The Gregorian context that provides the implicit-timezone.
     * @return <code>true</code> if the two xs:dateTime are equal according
     *         XQuery 1.0 and XPath 2.0 rules.
     */
//    public boolean equals(XDateTime dateTime, XmlAtomicValueCompareContext context)
//    {
//        if (dateTime == null)
//        {
//            throw new IllegalArgumentException("dateTime : " + XDateTime.class.getName() + " is null");
//        }
//
//        if (context == null)
//        {
//            throw new IllegalArgumentException("context : " + XmlAtomicValueCompareContext.class.getName() + " is null");
//        }
//
//        return equals(this, dateTime, context);
//    }

    /**
     * Calculates the xs:dayTimeDuration between this xs:dateTime and the one specified.
     * If either xs:dateTime does not have an offset, the implicit-timezone from the
     * context is used to uniquely define the xs:dateTime instant.
     *
     * @param dateTime The other xs:dateTime value.
     * @param context  The Gregorian context that provides the implicit timezone.
     * @return The difference between the two xs:dateTime values as an xs:dayTimeDuration.
     */
//    public TimeZone dayTimeDuration(XDateTime dateTime, XmlAtomicValueCompareContext context)
//    {
//        TimeSpan lhs = (null != m_offset) ? getTimeSpanSinceEpoch() : normalize(context.implicitTimezoneFunction()).getTimeSpanSinceEpoch();
//        TimeSpan rhs = (null != dateTime.m_offset) ? dateTime.getTimeSpanSinceEpoch() : dateTime.normalize(context.implicitTimezoneFunction()).getTimeSpanSinceEpoch();
//
//        return TimeZone.makeDayTimeDuration(lhs.subtract(rhs));
//    }

    /**
     * Calculates the xs:yearMonthDuration between this xs:dateTime and the one specified.
     * If either xs:dateTime does not have an offset, the implicit-timezone from the
     * context is used to uniquely define the xs:dateTime instant.
     *
     * @param dateTime The other xs:dateTime value.
     * @param context  The Gregorian context that provides the implicit timezone.
     * @return The difference between the two xs:dateTime values as an xs:yearMonthDuration.
     */
//    public XsYearMonthDuration yearMonthDuration(XDateTime dateTime, XmlAtomicValueCompareContext context)
//    {
//        TimeZone duration = dayTimeDuration(dateTime, context);
//
//        int sign = duration.signum();
//
//        XDateTime large;
//        XDateTime small;
//
//        if (sign > 0)
//        {
//            large = this;
//            small = dateTime;
//        }
//        else if (0 == sign)
//        {
//            return new XsYearMonthDuration(0);
//        }
//        else
//        {
//            large = dateTime;
//            small = this;
//        }
//
//        TimeZone implicitTimezone = context.implicitTimezoneFunction();
//
//        int months = 0;
//
//        XsYearMonthDuration result = new XsYearMonthDuration(months);
//
//        XsYearMonthDuration test = result;
//
//        while (small.add(test).compareTo(large, implicitTimezone) <= 0)
//        {
//            result = test;
//
//            test = new XsYearMonthDuration(++months);
//        }
//
//        return result;
//    }

//    public XDateTime add(XsDuration duration)
//    {
//        try
//        {
//            return add(this, duration).castAsDateTime();
//        }
//        catch (XmlAtomicValueCastException e)
//        {
//            throw new RuntimeWrapException(e);
//        }
//    }
//
//    public XDateTime subtract(XsDuration duration)
//    {
//        try
//        {
//            return add(this, duration.negate()).castAsDateTime();
//        }
//        catch (XmlAtomicValueCastException e)
//        {
//            throw new RuntimeWrapException(e);
//        }
//    }
//
//    public XDateTime adjustGmtOffset(TimeZone offset)
//    {
//        try
//        {
//            return normalize(offset).castAsDateTime();
//        }
//        catch (XmlAtomicValueCastException e)
//        {
//            throw new RuntimeWrapException(e);
//        }
//    }

    public ExpandedName getTypeName()
    {
        return XDateTime.NAME;
    }

    public boolean isEqualOrDerived(ExpandedName name)
    {
        if (name.equals(XDateTime.NAME))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        if (m_hashCode == 0)
        {
            int hash = (m_year * 977616000
                    + m_month * 2678400
                    + m_dayOfMonth * 86400
                    + m_hour * 3600
                    + m_minute * 60)
                    ^ m_second.intValue();

            if (m_offset != null)
            {
                hash ^= m_offset.hashCode();
            }

            m_hashCode = hash;
        }
        return m_hashCode;
    }
    
}
