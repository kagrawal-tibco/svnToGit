package com.tibco.cep.runtime.util.scheduler;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class SchedulerException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of <code>SchedulerException</code> without detail message.
     */
    public SchedulerException() {
    }


    /**
     * Constructs an instance of <code>SchedulerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SchedulerException(String msg) {
        super(msg);
    }

    public SchedulerException(String msg, Exception ex) {
        super(msg, ex);
    }
}
