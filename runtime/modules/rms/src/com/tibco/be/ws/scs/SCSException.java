package com.tibco.be.ws.scs;

/**
 * Exception to be thrown when interacting with a Source Control Repository.
 * 
 * @.category public-api
 * @version 5.5.0
 * @since 5.5.0
 */
public class SCSException extends Exception {

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     * 
     * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
     */
    public SCSException() {
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
     *              
     * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
     */
    public SCSException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     *                
     * @.category public-api
	 * @version 5.5.0
	 * @since 5.5.0
     */
    public SCSException(String message) {
        super(message);
    }
}
