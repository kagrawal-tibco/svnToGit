package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Dec 7, 2007
 * Time: 3:14:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ArrayAccessProxy extends ProxyContext {

    /**
     * @return Expression that determines the array index.
     */
    Expression getArrayIndex();

}
