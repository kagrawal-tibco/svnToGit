package com.tibco.rta.property;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/5/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PropertyAtom<T> {

    /**
     *
     * @return
     */
    public T getValue();
}
