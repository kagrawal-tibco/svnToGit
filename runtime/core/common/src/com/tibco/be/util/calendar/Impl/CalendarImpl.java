package com.tibco.be.util.calendar.Impl;

import java.io.Serializable;

import com.tibco.be.util.calendar.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: Nov 27, 2003
 * Time: 12:23:16 PM
 * To change this template use Options | File Templates.
 */
public abstract class CalendarImpl implements Calendar, Serializable {
    public static final long serialVersionUID=2413616562511050725L;

    boolean m_isRecurring;

    public boolean isRecurring() {
        return m_isRecurring;
    }

    public CalendarImpl(boolean recurring) {
        m_isRecurring = recurring;
    }

}
