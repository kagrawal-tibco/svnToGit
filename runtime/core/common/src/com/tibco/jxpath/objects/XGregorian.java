package com.tibco.jxpath.objects;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;

public abstract class XGregorian extends XObjectWrapper {

    protected static final int SECONDS_PER_MINUTE = 60;
    protected static final int MINUTES_PER_HOUR = 60;
    protected static final int HOURS_PER_DAY = 24;

    protected static final int MIN_CENTURY_DIGITS = 2;
    protected static final int MIN_YEAR_DIGITS = 4;
    protected static final int MONTH_DIGITS = 2;
    protected static final int DAYOFMONTH_DIGITS = 2;

    protected static final int LEAP_YEAR_EXAMPLE = 2000;

    private static final BigDecimal ZERO_DECIMAL = BigDecimal.valueOf(0L);
    private static final BigDecimal SIXTY_ONE_DECIMAL = BigDecimal.valueOf(61L);

    private static final TimeZone TIMEZONE_GMT = new SimpleTimeZone(0, "GMT+00:00");
    protected static final String CONSTRUCTORS_URI = "http://www.w3.org/2001/XMLSchema";
    
    protected final TimeZone m_offset;

	public XGregorian(TimeZone offset) {
		super(offset);
		this.m_offset = offset;
	}

    /**
     * Returns the timezone offset from GMT as an xs:dayTimeDuration.
     * The timezone offset is optional, so this method may return null.
     *
     * @return The timezone offset from GMT, or null.
     */
    public TimeZone getGmtOffset()
    {
        return m_offset;
    }

    /**
     * Returns the local year component, if specified, for this Gregorian type.
     */
    public int getYear()
    {
        return 1970;
    }

    /**
     * Returns the local unity-based month component, if specified, for this Gregorian type.
     * The month will be in the range of 1 to 12, both inclusive.
     */
    public int getMonth()
    {
        return 1;
    }

    /**
     * Returns the local unity-based day-of-month component, if specified, for this Gregorian type.
     * The day-of-month will be in the range of 1 to 31, both inclusive.
     */
    public int getDayOfMonth()
    {
        return 1;
    }

    /**
     * Returns the local hour-of-day component, if specified, for this Gregorian type.
     * The hour-of-day will be in the range of 0 to 23, both inclusive.
     */
    public int getHourOfDay()
    {
        return 0;
    }

    /**
     * Returns the minutes component, if specified, for this Gregorian type.
     * The minute will be in the range of 0 to 59, both inclusive.
     */
    public int getMinutes()
    {
        return 0;
    }

    /**
     * Returns the seconds component, if specified, for this Gregorian type.
     */
    public BigDecimal getSeconds()
    {
        return ZERO_DECIMAL;
    }

    public XDateTime castAsDateTime() throws XPathValueParseException
    {
        return new XDateTime(getYear(), getMonth(), getDayOfMonth(), getHourOfDay(), getMinutes(), getSeconds(), m_offset);
    }

    public XTime castAsTime() throws XPathValueParseException
    {
        return new XTime(getHourOfDay(), getMinutes(), getSeconds(), m_offset);
    }

    public XDate castAsDate() throws XPathValueParseException
    {
        return new XDate(getYear(), getMonth(), getDayOfMonth(), m_offset);
    }

    protected static void checkYear(final int year) throws XPathValueParseException
    {
        if (0 == year)
        {
            throw new XPathValueParseException("year cannot be zero");
        }
    }

    protected static void checkMonth(final int month) throws XPathValueParseException
    {
        if (month < 1 || month > 12)
        {
            throw new XPathValueParseException("month must be in the range of 1 to 12");
        }
    }

    protected static void checkDayOfMonth(final int dayOfMonth) throws XPathValueParseException
    {
        if (dayOfMonth < 1 || dayOfMonth > 31)
        {
            throw new XPathValueParseException("day of month must be in the range of 1 to 31, both inclusive");
        }
    }

    protected static void checkCalendar(final int year, final int month, final int dayOfMonth) throws XPathValueParseException
    {
        try
        {
            final Calendar calendar = new GregorianCalendar();

            calendar.setTimeZone(TIMEZONE_GMT);
            if (year < 0)
            {
                calendar.set(Calendar.ERA, GregorianCalendar.BC);
                calendar.set(Calendar.YEAR, 1 - year);
            }
            else
            {
                calendar.set(Calendar.ERA, GregorianCalendar.AD);
                calendar.set(Calendar.YEAR, year);
            }
            calendar.set(Calendar.MONTH, month - 1 + Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            calendar.setLenient(false);

            calendar.getTime();

            if (year < 0)
            {
                // be lenient
                calendar.set(Calendar.YEAR, year);
                calendar.setLenient(true);
                calendar.getTime();
            }
        }
        catch (final RuntimeException e)
        {
            throw new XPathValueParseException("Cannot exist in Gregorian calendar.");
        }
    }

    protected static void checkHourOfDay(final int hourOfDay) throws XPathValueParseException
    {
        if (hourOfDay < 0 || hourOfDay > 23)
        {
            throw new XPathValueParseException("hour of day must be in the range of 0 to 23, both inclusive");
        }
    }

    protected static void checkMinutes(final int minutes) throws XPathValueParseException
    {
        if (minutes < 0 || minutes > 59)
        {
            throw new XPathValueParseException("minutes must be in the range of 0 to 59, both inclusive");
        }
    }

    protected static void checkSeconds(final BigDecimal seconds) throws XPathValueParseException
    {
        if (seconds.signum() < 0 || seconds.compareTo(SIXTY_ONE_DECIMAL) >= 0)
        {
            throw new XPathValueParseException("seconds must be in the range of 0 to 60.9999..., both inclusive");
        }
    }

    protected static void checkOffset(final TimeZone offset) throws XPathValueParseException
    {
//        if (offset.compareTo(TimeZone.OFFSET_LIMIT_POSITIVE) > 0)
//        {
//            throw new XPathValueParseException("offset('" + offset.castAsString() + "') must be less than " + TimeZone.OFFSET_LIMIT_POSITIVE.castAsString());
//        }
//        else if (offset.compareTo(TimeZone.OFFSET_LIMIT_NEGATIVE) < 0)
//        {
//            throw new XPathValueParseException("offset('" + offset.castAsString() + "') must be greater than " + TimeZone.OFFSET_LIMIT_NEGATIVE.castAsString());
//        }
//        else
//        {
//            // Do nothing
//        }
    }

    protected static void checkOffsetRange(final String srcval, TimeZone offset, ExpandedName target) throws XPathValueParseException
    {
//        try
//        {
            checkOffset(offset);
//        }
//        catch (final XPathValueParseException e)
//        {
//            throw new XmlAtomicValueParseException(srcval, target, e);
//        }
    }

    /**
     * Parse an integer value that is a  substring in a string.
     *
     * @param srcval The string containing the integer field.
     * @param start  The zero-based start index (inclusive).
     * @param end    The zero-based end index (exclusive).
     * @param target The name of the type that is being parsed.
     * @return The integer value.
     * @throws XmlAtomicValueParseException If someting other than a digit is encountered.
     */
    protected static int parseIntField(final String srcval, final int start, final int end, final ExpandedName target) throws XPathValueParseException
    {
        int n = 0;

        if (srcval.length() < end)
        {
            throw new XPathValueParseException(srcval, null, new Exception("length('" + srcval + "') < " + end));
        }

        for (int i = start; i < end; i++)
        {
            char c = srcval.charAt(i);
            if (c <= '9' && c >= '0')
            {
                n = n * 10 + (int)c - (int)'0';
            }
            else
            {
                throw new XPathValueParseException(srcval, null, new Exception("Unexpected character '" + c + "' at position " + (i + 1) + ". Expecting " + (end - start) + " digits"));
            }
        }
        return n;
    }

//    public TimeSpan getTimeSpanSinceEpoch()
//    {
//        final GregorianCalendar calendar = new GregorianCalendar();
//
//        calendar.clear();
//
//        final TimeSpan remainder = setCalendar(calendar, this, getGmtOffset(), TimeZone.getDefault());
//
//        final TimeSpan baseline = new TimeSpan(calendar.getTime().getTime());
//
//        return baseline.add(remainder);
//    }

	public TimeZone getTimeZone() {
		return m_offset;
	}
	
    protected static TimeZone parseTimeZoneToDayTimeDuration(String srcval, int i, ExpandedName target) throws XPathValueParseException
    {
        int hours = 0;
        int minutes = 0;

        boolean isNegative = false;

        if (i >= srcval.length())
        {
            throw new XPathValueParseException(srcval);
        }
        else if (srcval.charAt(i) == 'Z')
        {
            i += 1;
        }
        else
        {
            if (srcval.charAt(i) == '-')
            {
                isNegative = true;
            }
            else if (srcval.charAt(i) == '+')
            {
                isNegative = false;
            }
            else
            {
                throw new XPathValueParseException(srcval);
            }
            i += 1;

            hours = parseIntField(srcval, i, i + 2, target);
            i += 2;

            if (i >= srcval.length())
            {
                throw new XPathValueParseException(srcval);
            }
            else if (srcval.charAt(i) == ':')
            {
                i += 1;
            }
            else
            {
                throw new XPathValueParseException(srcval);
            }

            minutes = parseIntField(srcval, i, i + 2, target);
            i += 2;
        }

        if (i == srcval.length())
        {
            if (isNegative)
            {
                return new SimpleTimeZone(-hours*60*60*1000+ -minutes*60*1000, "");
            }
            else
            {
                return new SimpleTimeZone(hours*60*60*1000+ minutes*60*1000, "");
            }
        }
        else
        {
            throw new XPathValueParseException(srcval);
        }
    }

    protected static int decDigit(char c)
    {
        if (c <= '9' && c >= '0')
            return (int)c - (int)'0';
        else
            return -1;
    }
    
	@Override
	public String str() {
		return castAsString();
	}

	public abstract String castAsString();

}
