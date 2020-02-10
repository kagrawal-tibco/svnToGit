package com.tibco.be.util.calendar;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 24, 2004
 * Time: 5:27:34 PM
 * To change this template use Options | File Templates.
 */
public class CalendarException extends Exception{
    private Throwable cause;

    public CalendarException () {
        super();
    }
    public CalendarException(String message) {
        super(message);
    }

    public CalendarException (String message, Throwable cause) {
        super(message);
        this.cause=cause;
    }
}
