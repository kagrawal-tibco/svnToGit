package com.tibco.be.rms.util;

import org.xml.sax.SAXException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 1, 2008
 * Time: 1:01:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParsingCompletedException extends SAXException {

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ParsingCompletedException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParsingCompletedException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this <tt>Throwable</tt> instance
     *         (which may be <tt>null</tt>).
     */
    @Override
    public String getMessage() {
        return super.getMessage();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
