package com.tibco.cep.runtime.model.element.impl;

import com.tibco.cep.runtime.model.element.DateTimeTuple;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 20, 2006
 * Time: 11:03:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeTupleImpl implements DateTimeTuple {
    long time;
    String timeZone;

    public DateTimeTupleImpl (long time, String timeZone) {
        this.time=time;
        this.timeZone=timeZone;
    }

    /**
     *
     * @return
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     *
     * @return
     */
    public long getTime() {
        return time;
    }
}
