package com.tibco.store.query.exec;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/12/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EvaluationOutputStack<T> {

    public void push(T entry);

    public T pop();

    public Collection<T> popTopEntries(int numOfTopEntries);

    public Collection<T> popAllEntries();

    public int size();
}
