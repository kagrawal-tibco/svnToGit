package com.tibco.cep.query.model.validation;

/*
 * Author: Ashwin Jayaprakash Date: Dec 7, 2007 Time: 6:11:26 PM
 */

public class ValidationException extends Exception {
    private static final long serialVersionUID = 1L;

    protected final int lineNumber;

    protected final int charPosition;

    public ValidationException(int lineNumber, int charPosition) {
        super();

        this.lineNumber = lineNumber;
        this.charPosition = charPosition;
    }

    public ValidationException(String message, Throwable cause, int lineNumber, int charPosition) {
        super(message, cause);

        this.lineNumber = lineNumber;
        this.charPosition = charPosition;
    }

    public ValidationException(String message, int lineNumber, int charPosition) {
        super(message);

        this.lineNumber = lineNumber;
        this.charPosition = charPosition;
    }

    public ValidationException(Throwable cause, int lineNumber, int charPosition) {
        super(cause);

        this.lineNumber = lineNumber;
        this.charPosition = charPosition;
    }

    public int getCharPosition() {
        return charPosition;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ". Line: " + getLineNumber() + ", Char: " + getCharPosition();
    }
}
