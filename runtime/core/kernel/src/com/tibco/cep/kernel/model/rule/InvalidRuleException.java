package com.tibco.cep.kernel.model.rule;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 2:14:03 PM
 * To change this template use File | Settings | File Templates.
 */
/**
 * @version 2.0.0
 * @since 2.0.0
 */
public class InvalidRuleException extends Exception {
    /**
     * Creates a new InvalidRuleException, without a detailed message.
     * @.category public-api
     */
    public InvalidRuleException() {
        super();
    }
    /**
     * Creates a new InvalidRuleException with a given detail message.
     *
     * @param s the detail message.
     * @.category public-api
     */
    public InvalidRuleException(String s) {
        super(s);
    }
}
