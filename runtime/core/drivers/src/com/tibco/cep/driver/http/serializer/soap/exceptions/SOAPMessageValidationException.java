package com.tibco.cep.driver.http.serializer.soap.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Feb 23, 2009
 * Time: 2:08:05 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class SOAPMessageValidationException extends Exception {
    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public SOAPMessageValidationException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public SOAPMessageValidationException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public SOAPMessageValidationException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
