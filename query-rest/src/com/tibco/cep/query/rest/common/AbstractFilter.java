package com.tibco.cep.query.rest.common;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/21/14
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFilter<T> implements Filter<T> {
    private T filter;

    public AbstractFilter(T filter) {
        this.filter = filter;
    }

    public T getFilter() {
        return filter;
    }
}
