package com.tibco.rta.client.taskdefs;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/3/13
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class Memento<T> {

    private T state;

    public Memento(T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }
}
