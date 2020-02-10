package com.tibco.cep.mapper.xml.xdata.xpath.func;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.DateTimeZonePair;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.xpath.expr.AbstractStringTypeExpr;
import com.tibco.xml.xquery.Expr;
import com.tibco.xml.xquery.ExprContext;
import com.tibco.xml.xquery.ExprException;
import com.tibco.xml.xquery.ExprFocus;

/**
 * TEMPORARILY HERE!!
 */
abstract class AbstractTibcoFormatDateFunction extends AbstractStringTypeExpr /*Function*/
{
    private final Expr m_format;
    private final Expr m_dateTime;

    public AbstractTibcoFormatDateFunction(ExpandedName name, Expr[] args)
    {
        m_format = args[0];
        m_dateTime = args[1];
    }

    public String stringValue(ExprFocus focus, ExprContext host) throws ExprException
    {
        String formatStr = m_format.stringValue(focus,host);
        String dateTimeStr = m_dateTime.stringValue(focus,host);

        try
        {
            DateTimeZonePair dateTz = parseToDate(dateTimeStr);
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            sdf.setLenient(false);
            sdf.setTimeZone(dateTz.getTimeZone());

            return sdf.format(dateTz.getDate());
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public abstract DateTimeZonePair parseToDate(String date) throws ParseException;

    /**
     * WCETODO remove if were AbstractStringTypeFunction.
     */
    public void serialize(XmlContentHandler handler) throws SAXException
    {
    }

    /**
     * WCETODO remove if were AbstractStringTypeFunction.
     * @return
     */
    public Expr optimize()
    {
        return this;
    }

    // WCETODO CUT'n'PASTED from TibcoParseDateTimeFunction (was non-public)

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
}
