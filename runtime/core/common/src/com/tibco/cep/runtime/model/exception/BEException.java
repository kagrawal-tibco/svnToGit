package com.tibco.cep.runtime.model.exception;


import com.tibco.xml.datamodel.XiNode;


/**
 * Runtime exception which wraps any exception caught during rule language execution.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface BEException {


    /**
     * Returns the detail message string of this exception.
     *
     * @return a String.
     * @.category public-api
     * @since 2.0.0
     */
    String getMessage();


    /**
     * Gets the type of error represented by this exception (exception class name in the case of Java exceptions).
     *
     * @return a String.
     * @.category public-api
     * @since 2.0.0
     */
    String getErrorType();


    /**
     * Converts this <code>BEException</code> into a <code>RuntimeException</code>.
     *
     * @return a RuntimeException
     * @.category public-api
     * @since 2.0.0
     */
    RuntimeException toException();


    /**
     * Returns the cause of this exception or null if the cause is nonexistent or unknown.
     * (The cause is the <code>BEException</code> that caused this throwable to get thrown.)
     *
     * @return the cause of this exception or null if the cause is nonexistent or unknown.
     * @.category public-api
     * @since 2.0.0
     */
    BEException get_Cause();


    /**
     * Provides access to the stack trace information.
     *
     * @return a String.
     * @.category public-api
     * @since 2.0.0
     */
    String get_StackTrace();


    void toXiNode(XiNode node);
}
