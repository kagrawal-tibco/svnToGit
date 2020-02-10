package com.tibco.cep.runtime.model.element;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: Apr 7, 2004
* Time: 3:04:47 PM
*
* Copyright (c) 2004  TIBCO Software Inc.
* Contact: Nick Leong (nleong@tibco.com)
*
*/


/**
 * Exception thrown when attempting to access a property value that is currently unknown or unset.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */

public class PropertyException extends Exception {


    /**
     * Creates a new <code>PropertyException</code>, without a detailed message.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public PropertyException() {
        super();
    }


    /**
     * Creates a new <code>PropertyException</code> with the given detail message.
     *
     * @param s the detail message.
     * @.category public-api
     * @since 2.0.0
     */
    public PropertyException(String s) {
        super(s);
    }


}
