package com.tibco.cep.query.model;

import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2007
 * Time: 6:36:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DateTimeLiteral extends Literal {

    public static String DATETIME_FORMAT ="yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * Returns the DateTime value of the literal
     * @return GregorianCalendar
     */
    GregorianCalendar getValue();
}
