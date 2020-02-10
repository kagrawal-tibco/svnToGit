/**
 * Created by IntelliJ IDEA.
 * User: hkarmark
 * Date: Jun 4, 2004
 * Time: 10:04:19 AM
 *
 */

package com.tibco.cep.designtime.model.service.calendar.mutable.impl;


import com.tibco.be.util.calendar.Impl.NonRecurringCalendarImpl;
import com.tibco.be.util.calendar.Impl.RecurringCalendarImpl;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.service.calendar.Calendar;
import com.tibco.cep.designtime.model.service.calendar.mutable.MutableCalendar;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;


public class DefaultMutableCalendar extends AbstractMutableEntity implements MutableCalendar {


    protected boolean m_recurring;
    protected int m_recurrencePattern;
    protected com.tibco.be.util.calendar.NonRecurringCalendar m_calendar;
    protected com.tibco.be.util.calendar.RecurringCalendar m_rcalendar;
    public static final int RECURRENCE_PATTERN_DAILY = 0;
    public static final int RECURRENCE_PATTERN_WEEKLY = 1;
    public static final int RECURRENCE_PATTERN_MONTHLY = 2;
    public static final int RECURRENCE_PATTERN_YEARLY = 3;

    //todo replace with bundle calls
    protected static final String[] recurrencePatterns = {
            "daily",
            "weekly",
            "monthly",
            "yearly"
    };

    protected com.tibco.be.util.calendar.Calendar calendar;


    //constructor
    public DefaultMutableCalendar(DefaultMutableOntology ontology, String name, DefaultMutableFolder folder) {
        super(ontology, folder, name);
    }


    //todo check
    public int getType() {
        int type;
        if (m_recurring) {
            type = Calendar.RECURRING_TYPE;

        } else {
            type = Calendar.NON_RECURRING_TYPE;
        }
        return type;
    }


    public void setType(int type) {
        if (type != getType()) {
            if (type == Calendar.RECURRING_TYPE) {
                m_recurring = true;
            } else if (type == Calendar.NON_RECURRING_TYPE) {
                m_recurring = false;
            }
        }
    }


    public void setRecurrencePattern(int pattern) {
        m_recurrencePattern = pattern;
    }


    public int getRecurrencePattern() {
        return m_recurrencePattern;
    }


    public XiNode toXiNode(XiFactory factory) {
        XiNode root = super.toXiNode(factory, "calendar");
        return root;
    }


    /**
     * This method is called to return a calendar object
     *
     * @param recurring :whether the calendar is recurring or not
     * @return calendar: returns a calendar object
     */
    public com.tibco.be.util.calendar.Calendar getCalendar(boolean recurring) {
        if (recurring) {
            calendar = new RecurringCalendarImpl();

        } else {
            calendar = new NonRecurringCalendarImpl();

        }
        return calendar;
    }


    //todo
    public boolean isValid(boolean recurse) {
        return true;
    }

}
