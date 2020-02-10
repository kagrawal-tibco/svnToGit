package com.tibco.cep.kernel.model.knowledgebase;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 15, 2006
 * Time: 4:49:51 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Exception thrown upon attempting to set an external ID to a value that is already used as external ID.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public class DuplicateExtIdException extends Exception {


    /**
     * Builds a <code>DuplicateExtIdException</code> with no message string or cause.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateExtIdException() {
        super();
    }


    /**
     * Builds a <code>DuplicateExtIdException</code> with a message string.
     *
     * @param message the exception details.
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateExtIdException(String message) {
        super(message);
    }


    /**
     * Builds a <code>DuplicateExtIdException</code> with a cause.
     *
     * @param cause the <code>Throwable</code> that caused this exception.
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateExtIdException(Throwable cause) {
        super(cause);
    }


    /**
     * Builds a <code>DuplicateExtIdException</code> with a message string and a cause.
     *
     * @param message the exception details.
     * @param cause   the <code>Throwable</code> which caused this exception.
     * @.category public-api
     * @since 2.0.0
     */
    public DuplicateExtIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
