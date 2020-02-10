package com.tibco.cep.runtime.model.element;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 18, 2006
 * Time: 12:36:40 AM
 * To change this template use File | Settings | File Templates.
 */
/**
 * @version 2.0.0
 * @since 2.0.0
 */
public class ContainedConceptException extends RuntimeException {
    /**
     * Creates a new ContainedConceptException, without a detailed message.
     * @.category public-api
     */
    public ContainedConceptException() {
        super();
    }
    /**
     * Creates a new ContainedConceptException with a given detail message.
     *
     * @param s the detail message.
     * @.category public-api
     */
    public ContainedConceptException(String s) {
        super(s);
    }

}
