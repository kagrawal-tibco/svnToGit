package com.tibco.be.parser.codegen;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Aug 25, 2004
 * Time: 10:35:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class CompilationFailedException extends Exception {

    public CompilationFailedException() {
        super();
    }

    public CompilationFailedException(Throwable cause) {
        super(cause);
    }

    public CompilationFailedException(String message) {
        super(message);
    }

    public CompilationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
