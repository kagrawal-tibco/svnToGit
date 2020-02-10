package com.tibco.jxpath.objects;

import java.math.BigDecimal;
import java.util.TimeZone;

import com.tibco.xml.data.primitive.ExpandedName;

public class XTime extends XGregorian {
    public static final ExpandedName NAME = ExpandedName.makeName(CONSTRUCTORS_URI, "time");

    private final int m_hours;
    private final int m_minutes;
    private final BigDecimal m_seconds;

    public XTime(int hourOfDay, int minutes, BigDecimal seconds)
    {
        // Make it explicit that this is just a convenience method.
        this(hourOfDay, minutes, seconds, null);
    }

//    public XTime(int hourOfDay, int minutes, long millis, int nanos, BigDecimal seconds, TimeZone offset)
//    {
//        // Make it explicit that this is just a convenience method.
//        this(hourOfDay, minutes, TimeSpan.create(millis, nanos, seconds), offset);
//    }

    public XTime(int hourOfDay, int minutes, BigDecimal seconds, TimeZone offset)
    {
        super(offset);

        m_hours = hourOfDay;
        m_minutes = minutes;
        m_seconds = seconds;
    }

    public static XTime create(int hourOfDay, int minutes, BigDecimal seconds, TimeZone offset) throws XPathValueParseException
    {
        checkHourOfDay(hourOfDay);
        checkMinutes(minutes);
        checkSeconds(seconds);

        if (null != offset)
        {
            checkOffset(offset);
        }

        return new XTime(hourOfDay, minutes, seconds, offset);
    }

    protected XGregorian newGregorian(int year, int month, int dayOfMonth, int hours, int minutes, BigDecimal seconds, TimeZone offset)
    {
        return new XTime(hours, minutes, seconds, offset);
    }

    /**
     * Compiles a time with the format HH:MM:SS.SSS[Z|(+|-)HH:MM]
     *
     * @param strval The time string.
     * @return The compiled time.
     * @throws XPathValueParseException If the format is invalid.
     */
    public static XTime compile(String strval) throws XPathValueParseException
    {
        if (null == strval)
        {
            throw new IllegalArgumentException();
        }
        else if (0 == strval.length())
        {
            throw new XPathValueParseException(strval, NAME, new Exception("length is zero"));//, TextRange.EMPTY);
        }

        // A full collapse isn't going to help so just trim.
        String collapsed = strval.trim();

        int i = 0;
        int hourOfDay;
        int minutes;

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
                throw new XPathValueParseException(collapsed, NAME);//, new TextRange(i));
            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
            throw new XPathValueParseException(collapsed, NAME);//, new TextRange(i));
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
                throw new XPathValueParseException(collapsed, NAME);//, new TextRange(i));
            }
        }
        catch (StringIndexOutOfBoundsException e)
        {
            throw new XPathValueParseException(collapsed, NAME);//, new TextRange(i));
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
                throw new XPathValueParseException(collapsed, NAME, e);//, new TextRange(beginIndex, i - trailingZeros));
            }
        }

        if (collapsed.length() > i)
        {
            TimeZone offset = parseTimeZoneToDayTimeDuration(collapsed, i, NAME);

            try
            {
                return create(hourOfDay, minutes, seconds, offset);
            }
            catch (XPathValueParseException e)
            {
                throw new XPathValueParseException(collapsed, NAME, e);
            }
        }
        else
        {
            try
            {
                return create(hourOfDay, minutes, seconds, null);
            }
            catch (XPathValueParseException e)
            {
                throw new XPathValueParseException(collapsed, NAME, e);
            }
        }
    }

    public int getHourOfDay()
    {
        return m_hours;
    }

    public int getMinutes()
    {
        return m_minutes;
    }

    public BigDecimal getSeconds()
    {
        return m_seconds;
    }

    public String castAsString()
    {
        return XGregorianFormat.time.format(this, m_offset);
    }

}
