package com.tibco.cep.query.model.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.query.model.DateFormatProvider;
import com.tibco.cep.query.model.DateTimeLiteral;
import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 6:41:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeLiteralImpl extends AbstractExpression implements DateTimeLiteral {

    private GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

    public DateTimeLiteralImpl(ModelContext parentContext, CommonTree tree, String dateTime) throws Exception {
        super(parentContext, tree);
        this.calendar.setTime(DateFormatProvider.getInstance().getDateFormat().parse(dateTime));
        this.setTypeInfo(Calendar.class.getName());
    }

    /**
     * Returns the DateTime value of the literal
     *
     * @return GregorianCalendar
     */
    public GregorianCalendar getValue() {
        return this.calendar;
    }

    /**
     * @return the context type
     */
    public int getContextType() {
        return CTX_TYPE_DATETIME_LITERAL;
    }

    /**
     * Returns true if the context has been resolved or false
     *
     * @return boolean
     */
    public boolean isResolved() {
        return this.calendar != null;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof DateTimeLiteralImpl)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final DateTimeLiteralImpl that = (DateTimeLiteralImpl) o;
        return this.calendar.equals(that.calendar);
    }


    public int hashCode() {
        return this.calendar.hashCode();
    }

}
