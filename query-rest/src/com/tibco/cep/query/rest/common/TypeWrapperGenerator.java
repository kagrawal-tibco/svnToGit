package com.tibco.cep.query.rest.common;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/17/14
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TypeWrapperGenerator<T> {

    public TypeWrapper<T> getTypeWrapper(AbstractTypeWrapperCreator wrapperCreator)
    {
        TypeWrapper typeWrapper = wrapperCreator.createWrapper();

        return  typeWrapper;
    }
}
