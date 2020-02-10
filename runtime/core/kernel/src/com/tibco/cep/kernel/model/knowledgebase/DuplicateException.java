package com.tibco.cep.kernel.model.knowledgebase;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 25, 2006
 * Time: 5:13:00 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
//TODO document the meaning of this exception
public class DuplicateException extends Exception {


    /**
     * Builds a <code>DuplicateException</code> with no message string or cause.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateException() {
        super();
    }


    /**
     * Builds a <code>DuplicateException</code> with a message.
     *
     * @param message the exception details.
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateException(String message) {
        super(message);
    }


    /**
     * Builds a <code>DuplicateException</code> with a cause.
     *
     * @param cause the <code>Throwable</code> which caused this exception.
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateException(Throwable cause) {
        super(cause);
    }


    /**
     * Builds a <code>DuplicateException</code> with a message and a cause.
     *
     * @param message the exception details.
     * @param cause   the <code>Throwable</code> which caused this exception.
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
