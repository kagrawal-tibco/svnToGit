/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.report;

/**
 *
 * @author ksubrama
 */
public class TableException extends Exception {

    /**
     * Creates a new instance of <code>TableException</code> without detail message.
     */
    public TableException() {
    }


    /**
     * Constructs an instance of <code>TableException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TableException(String msg) {
        super(msg);
    }
}
