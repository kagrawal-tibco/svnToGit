package com.tibco.rta.queues;

/**
 * Created by aathalye
 * Date : 18/12/14
 * Time : 12:17 PM
 */
public class QueueException extends Exception {

    public QueueException() {
    }

    public QueueException(String message) {
        super(message);
    }

    public QueueException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueueException(Throwable cause) {
        super(cause);
    }
}
