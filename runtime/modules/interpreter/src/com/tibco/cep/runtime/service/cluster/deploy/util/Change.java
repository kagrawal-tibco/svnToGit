package com.tibco.cep.runtime.service.cluster.deploy.util;

/**
 * User: nprade
 * Date: 3/16/12
 * Time: 1:50 PM
 */
public class Change<T> {


    public final T before;
    public final T after;


    public Change(
            T before,
            T after) {
        this.before = before;
        this.after = after;
    }

}
