package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 14, 2007
 * Time: 10:48:59 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Limited {

    /**
     * returns the first number of tuples from a given offset
     * @return Object the number of tuples, either as Integer or as BindVariable
     */
    Object getLimitFirst();

    /**
     * Returns the offset number of tuple from where the first count of tuples are selected,
     * the default value is 0
     * @return int the offset number of tuples
     */
    Object getLimitOffset();

}
