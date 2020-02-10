package com.tibco.cep.query.rest.common;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 3/28/14
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FilterBuilder<T> {

    public void buildFilter();
    public T getFilter();
}
