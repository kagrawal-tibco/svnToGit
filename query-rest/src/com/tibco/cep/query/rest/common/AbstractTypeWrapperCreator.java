package com.tibco.cep.query.rest.common;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/17/14
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTypeWrapperCreator<T> {

    abstract TypeWrapper<T> createWrapper();

    TypeWrapper<T> getWrapper()
    {
        TypeWrapper wrapper = createWrapper();
        return wrapper;
    }
}
