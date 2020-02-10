package com.tibco.rta.model;

/**
 * This exception is thrown when validation of an scehma element fails.
 */
public class ValidationException extends Exception {

    private static final long serialVersionUID = -9097461109760118678L;

    ValidationException(String exception) {
        super(exception);
    }
}
