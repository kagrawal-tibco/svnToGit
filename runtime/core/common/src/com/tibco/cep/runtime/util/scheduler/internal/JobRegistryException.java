/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.cep.runtime.util.scheduler.internal;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class JobRegistryException extends Exception {

    /**
     * Creates a new instance of <code>JobRegistryException</code> without detail message.
     */
    public JobRegistryException() {
    }


    /**
     * Constructs an instance of <code>JobRegistryException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public JobRegistryException(String msg) {
        super(msg);
    }
}
