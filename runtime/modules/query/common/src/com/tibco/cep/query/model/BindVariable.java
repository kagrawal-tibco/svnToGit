package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 19, 2007
 * Time: 2:52:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BindVariable extends Expression {

    
    /**
     * Returns the bind variable label
     * @return int
     */
    String getLabel();


    boolean isBindingResolved();


    boolean resolveBinding() throws Exception;


}
