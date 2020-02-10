package com.tibco.jxpath.objects;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TimeZone;

public class XGregorianFormat implements Serializable {
	
    public static final XGregorianFormat gYearMonth = new XGregorianFormat("gYearMonth", true, true, false, false);
    public static final XGregorianFormat gYear = new XGregorianFormat("gYear", true, false, false, false);
    public static final XGregorianFormat gMonthDay = new XGregorianFormat("gMonthDay", false, true, true, false);
    public static final XGregorianFormat gDay = new XGregorianFormat("gDay", false, false, true, false);
    public static final XGregorianFormat gMonth = new XGregorianFormat("gMonth", false, true, false, false);
    public static final XGregorianFormat dateTime = new XGregorianFormat("dateTime", true, true, true, true);
    public static final XGregorianFormat date = new XGregorianFormat("date", true, true, true, false);
    public static final XGregorianFormat time = new XGregorianFormat("time", false, false, false, true);

    private static final String YEAR_MISSING = "--";
    private static final String MONTH_MISSING = "-";
    private static final String DATE_SEPARATOR = "-";
    private static final String TIME_SEPARATOR = ":";
    private static final String DATETIME_SEPARATOR = "T";
    private static final int MIN_YEAR_DIGITS = 4;
    private static final int MONTH_DIGITS = 2;
    private static final int DAYOFMONTH_DIGITS = 2;

    public static final TimeZone TIMEZONE_GMT = TimeZone.getTimeZone("GMT");

    private static final int TIMEZONE_LENGTH = 6;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MINUTES_PER_HOUR = 60;

    private final String m_name;
    private final boolean m_hasYear;
    private final boolean m_hasMonth;
    private final boolean m_hasDayOfMonth;
    private final boolean m_hasTime;

    private XGregorianFormat(String name, boolean hasYear, boolean hasMonth, boolean hasDayOfMonth, boolean hasTime)
    {
        m_name = name;
        m_hasYear = hasYear;
        m_hasMonth = hasMonth;
        m_hasDayOfMonth = hasDayOfMonth;
        m_hasTime = hasTime;
    }

    public String toString()
    {
        return m_name;
    }

    public String format(XGregorian gregorian, TimeZone offset)
    {
        // TODO:
        // Changing the implementation of Gregorian types so that components
        // are no longer stored will make this implementation inefficient.
        // It makes more sense to get all the components at once.
        StringBuffer buffer = new StringBuffer();

        if (m_hasYear)
        {
            // [-]YYYY
            appendDigits(gregorian.getYear(), MIN_YEAR_DIGITS, buffer);

            if (m_hasMonth)
            {
                // [-]YYYY-MM
                buffer.append(DATE_SEPARATOR);
                appendDigits(gregorian.getMonth(), MONTH_DIGITS, buffer);

                if (m_hasDayOfMonth)
                {
                    // [-]YYYY-MM-DD
                    buffer.append(DATE_SEPARATOR);
                    appendDigits(gregorian.getDayOfMonth(), DAYOFMONTH_DIGITS, buffer);

                    if (m_hasTime)
                    {
                        // [-]YYYY-MM-DDTHH:MM:SS.SSS
                        buffer.append(DATETIME_SEPARATOR);
                        appendTimePart(gregorian, buffer);
                    }
                    else
                    {
                        // [-]YYYY-MM-DD
                    }
                }
                else
                {
                    // [-]YYYY-MM
                }
            }
            else
            {
                if (m_hasDayOfMonth)
                {
                    throw new RuntimeException();
                }
                else
                {
                    // [-]YYYY
                }
            }
        }
        else
        {
            if (m_hasMonth)
            {
                // --MM
                buffer.append(YEAR_MISSING);
                appendDigits(gregorian.getMonth(), MONTH_DIGITS, buffer);

                if (m_hasDayOfMonth)
                {
                    // --MM-DD
                    buffer.append(DATE_SEPARATOR);
                    appendDigits(gregorian.getDayOfMonth(), DAYOFMONTH_DIGITS, buffer);
                }
                else
                {
                    // --MM
                }
            }
            else
            {
                if (m_hasDayOfMonth)
                {
                    buffer.append(YEAR_MISSING);

                    buffer.append(MONTH_MISSING);

                    appendDigits(gregorian.getDayOfMonth(), DAYOFMONTH_DIGITS, buffer);
                }
                else
                {
                    if (m_hasTime)
                    {
                        appendTimePart(gregorian, buffer);
                    }
                    else
                    {
                        throw new RuntimeException();
                    }
                }
            }
        }

        if (null != offset)
        {
            buffer.append(toString(offset.getRawOffset(), true));
        }

        return buffer.toString();
    }

    private static StringBuffer appendTimePart(XGregorian gregorian, StringBuffer buffer)
    {
        appendDigits(gregorian.getHourOfDay(), 2, buffer);
        buffer.append(TIME_SEPARATOR);
        appendDigits(gregorian.getMinutes(), 2, buffer);
        buffer.append(TIME_SEPARATOR);

        int integralSec = gregorian.getSeconds().intValue();

        // TODO: Is there any value in replacing BigDecimal with TimeSpan here?
        BigDecimal fractional = gregorian.getSeconds().subtract(BigDecimal.valueOf(integralSec));

        appendDigits(integralSec, 2, buffer);

        if (fractional.signum() > 0)
        {
            String decimalStr = fractional.toString();

            decimalStr = decimalStr.substring(decimalStr.indexOf('.'));

            buffer.append(decimalStr);
        }

        return buffer;
    }

    private static StringBuffer appendDigits(int value, int minDigits, StringBuffer sb)
    {
        String strval;

        if (value < 0)
        {
            sb.append('-');

            strval = Integer.toString(-value);
        }
        else
        {
            strval = Integer.toString(value);
        }

        int padding = minDigits - strval.length();

        while (padding > 0)
        {
            sb.append('0');

            padding--;
        }

        sb.append(strval);

        return sb;
    }

    public final boolean equals(Object other)
    {
        return super.equals(other);
    }

    public final int hashCode()
    {
        return super.hashCode();
    }

    private static int nextOrdinal = 0;
    private final int m_ordinal = nextOrdinal++;
    private static final XGregorianFormat[] VALUES = {gYearMonth, gYear, gMonthDay, gDay, gMonth, dateTime, date, time};

    Object readResolve() throws ObjectStreamException
    {
        return VALUES[m_ordinal];
    }
    
    public static String toString(final int offsetInMillis, final boolean zuluEnabled)
    {
        final int offsetInSeconds = (offsetInMillis) / 1000;

        final int offsetInMinutes = offsetInSeconds / SECONDS_PER_MINUTE;

        final int hours = offsetInMinutes / MINUTES_PER_HOUR;
        final int minutes = offsetInMinutes % MINUTES_PER_HOUR;

        return toString(hours, minutes, zuluEnabled);
    }

    /**
     * Returns a timezone string with the format Z|(+|-)HH:MM with optional Z.
     *
     * @param hours       The hours component of the timezone.
     * @param minutes     The minutes component of the timezone.
     * @param zuluEnabled Determines whether Z is an allowable representation for zero offset.
     */
    public static String toString(final int hours, final int minutes, final boolean zuluEnabled)
    {
        return toString(hours, minutes, zuluEnabled, true);
    }

    /**
     * Returns a timezone string with the format Z|(+|-)HH:MM with optional Z and +.
     *
     * @param hours       The hours component of the timezone.
     * @param minutes     The minutes component of the timezone.
     * @param zuluEnabled Determines whether Z is an allowable representation for zero offset.
     * @param plusEnabled Determines whether + should be displayed for zero and positive offsets.
     */
    public static String toString(int hours, int minutes, final boolean zuluEnabled, boolean plusEnabled)
    {
        StringBuffer buffer;

        if (hours < 0)
        {
            if (minutes < 0)
            {
                buffer = new StringBuffer(TIMEZONE_LENGTH);

                buffer.append('-');

                hours *= (-1);
                minutes *= (-1);
            }
            else if (minutes == 0)
            {
                buffer = new StringBuffer(TIMEZONE_LENGTH);

                buffer.append('-');

                hours *= (-1);
            }
            else
            {
                throw new RuntimeException();
            }
        }
        else if (hours == 0)
        {
            if (minutes < 0)
            {
                buffer = new StringBuffer(TIMEZONE_LENGTH);

                buffer.append('-');

                minutes *= (-1);
            }
            else if (minutes == 0)
            {
                if (zuluEnabled)
                {
                    return "Z";
                }
                else
                {
                    buffer = new StringBuffer(TIMEZONE_LENGTH);

                    if (plusEnabled)
                    {
                        buffer.append('+');
                    }

                    minutes *= (-1);
                }
            }
            else
            {
                buffer = new StringBuffer(TIMEZONE_LENGTH);

                if (plusEnabled)
                {
                    buffer.append('+');
                }
            }
        }
        else
        {
            if (minutes < 0)
            {
                throw new RuntimeException();
            }
            else
            {
                buffer = new StringBuffer(TIMEZONE_LENGTH);

                if (plusEnabled)
                {
                    buffer.append('+');
                }
            }
        }

        addTwoDigits(hours, buffer);
        buffer.append(':');
        addTwoDigits(minutes, buffer);

        return buffer.toString();
    }

    private static void addTwoDigits(final int posValue, final StringBuffer buffer)
    {
        if (posValue < 10)
        {
            buffer.append('0');
            buffer.append(posValue);
        }
        else if (posValue < 100)
        {
            buffer.append(posValue);
        }
        else
        {
            throw new RuntimeException();
        }
    }

}
