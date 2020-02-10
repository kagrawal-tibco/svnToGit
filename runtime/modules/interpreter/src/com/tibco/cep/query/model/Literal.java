package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jul 15, 2007
 * Time: 9:11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Literal extends Expression {

    /**
     * Returns the Literal value
     * @return Object
     */
    public Object getValue();
    
}
