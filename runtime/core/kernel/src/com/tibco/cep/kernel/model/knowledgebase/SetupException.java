package com.tibco.cep.kernel.model.knowledgebase;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 17, 2006
 * Time: 6:23:48 PM
 * To change this template use File | Settings | File Templates.
 */
/**
 * @version 2.0.0
 * @since 2.0.0
 */
public class SetupException extends Exception {
    /**
     * Creates a new SetupException, without a detailed message.
     * @.category public-api
     */
    public SetupException() {
        super();
    }

    /**
     * Creates a new SetupException with a given detail message.
     * @param s the detail message.
     * @.category public-api
     */
    public SetupException(String s) {
        super(s);
    }
}