package com.tibco.jxpath.objects;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.TimeSpan;

public class XDate extends XGregorian {
    static public final ExpandedName NAME = ExpandedName.makeName("", "date");

    private final int m_year;
    private final int m_month;
    private final int m_dayOfMonth;

    /**
     * Initializes an {@link XDate} from year, month and dayOfMonth components.
     * The {@link XDate} constructed does not have a timezone.
     * <b>N.B.</b>No check is perfomed on the input data, use {@link #compile(String)} for that.
     *
     * @param year       The year.
     * @param month      The one-based month.
     * @param dayOfMonth The one-based day of the month.
     */
    public XDate(int year, int month, int dayOfMonth)
    {
        super(null);

        m_year = year;
        m_month = month;
        m_dayOfMonth = dayOfMonth;
    }

    /**
     * Initializes an {@link XDate} from year, month and dayOfMonth components and a time zone.
     * <b>N.B.</b>No check is perfomed on the input data, use {@link #compile(String)} for that.
     *
     * @param year       The year.
     * @param month      The one-based month.
     * @param dayOfMonth The one-based day of the month.
     * @param offset     The time zone offset.
     */
    public XDate(int year, int month, int dayOfMonth, TimeZone offset)
    {
        super(offset);

        m_year = year;
        m_month = month;
        m_dayOfMonth = dayOfMonth;
    }

    public XDate(long timeInMillis, TimeZone offset)
    {
        super(offset);

        Calendar calendar = new GregorianCalendar();

        calendar.setTime(new java.util.Date(timeInMillis));

        calendar.setTimeZone(offset);

        m_year = calendar.get(Calendar.YEAR);
        m_month = calendar.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        m_dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    }

    public static XDate create(int year, int month, int dayOfMonth, TimeZone offset) throws XPathValueParseException
    {
        checkYear(year);
        checkMonth(month);
        checkDayOfMonth(dayOfMonth);

        if (null != offset)
        {
            checkOffset(offset);
        }

        checkCalendar(year, month, dayOfMonth);

        return new XDate(year, month, dayOfMonth, offset);
    }

    protected XGregorian newGregorian(int year, int month, int dayOfMonth, int hours, int minutes, TimeSpan seconds, TimeZone offset)
    {
        return new XDate(year, month, dayOfMonth, offset);
    }

    protected XGregorian newGregorian(int year, int month, int dayOfMonth, int hours, int minutes, BigDecimal seconds, TimeZone offset)
    {
        return new XDate(year, month, dayOfMonth, offset);
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

    /**
     * Compiles a date with the format [-]YYYY-MM-DD[Z|(+|-)HH:MM]
     *
     * @param strval The date string.
     * @return The compiled date.
     * @throws XPathValueParseException If the format is invalid.
     */
    public static XDate compile(String strval) throws XPathValueParseException
    {
        if (null == strval)
        {
            throw new IllegalArgumentException();
        }
        else if (0 == strval.length())
        {
            throw new XPathValueParseException(strval, NAME, new Exception("length is zero"));
        }

        // A full collapse isn't going to help so just trim.
        String collapsed = strval.trim();

        int i = 0;
        int year;
        int month;
        int dayOfMonth;

        boolean negative = false;

        if (collapsed.startsWith("-"))
        {
            negative = true;
            i += 1;
        }

        int separator = collapsed.indexOf("-", i);

        if (separator == -1) {
            throw new XPathValueParseException(collapsed, NAME, null);
        }
        year = parseIntField(collapsed, i, separator, NAME);
        if ((separator - i < 4) //ensure four (or more) digit years
                || (collapsed.charAt(i) == '0' && year > 999)//no unnecessary leading '0'
                || year == 0)
        { //The year 0000 is prohibited http://www.w3.org/TR/xmlschema-2/#dateTime
            throw new XPathValueParseException(collapsed, NAME, null);
        }
        i = separator + 1;
        separator = collapsed.indexOf("-", i);
        if (separator == -1)
        {
            throw new XPathValueParseException(collapsed, NAME, null);
        }
        // YYYY-MM-DD
        month = parseIntField(collapsed, i, i + 2, NAME);
        if (separator != i + 2)
        {
            throw new XPathValueParseException(collapsed, NAME, null);
        }
        i += 3;
        dayOfMonth = parseIntField(collapsed, i, i + 2, NAME);
        i += 2;

        if (negative)
        {
            year = -year;
        }

        if (collapsed.length() > i)
        {
            TimeZone offset = parseTimeZoneToDayTimeDuration(collapsed, i, NAME);

//            try
//            {
                return create(year, month, dayOfMonth, offset);
//            }
//            catch (XPathFunctionException e)
//            {
//                throw new XPathValueParseException(collapsed, NAME, e);
//            }
        }
        else
        {
//            try
//            {
                return create(year, month, dayOfMonth, null);
//            }
//            catch (XPathFunctionException e)
//            {
//                throw new XPathValueParseException(collapsed, NAME, e);
//            }
        }
    }

    public String castAsString()
    {
        return XGregorianFormat.date.format(this, m_offset);
    }

}
