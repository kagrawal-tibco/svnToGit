/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class DateTimeZonePair
{
    protected final Date mDate;
    protected final TimeZone mTimeZone;

    public DateTimeZonePair()
    {
        GregorianCalendar cal = new GregorianCalendar();
        mDate = cal.getTime();
        mTimeZone = cal.getTimeZone();
    }

    public DateTimeZonePair(TimeZone tz)
    {
        Calendar cal = new GregorianCalendar(tz);
        mDate = cal.getTime();
        mTimeZone = tz;
    }

    public DateTimeZonePair(Date date, TimeZone timeZone)
    {
        mDate = date;
        mTimeZone = timeZone;
    }

    public final Date getDate()
    {
        return mDate;
    }

    public final TimeZone getTimeZone()
    {
        return mTimeZone;
    }

    public final String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("Date: ");
        buff.append(mDate);
        buff.append('\n');
        buff.append("TimeZone: ");
        buff.append(mTimeZone);
        return buff.toString();
    }
}
