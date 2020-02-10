package com.tibco.store.persistence.descriptor;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/1/14
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RangeDescriptor<T> {

    public T getStart();

    public T getEnd();

    /**
     * Does this range enclose the given value.
     * @param value
     * @return
     */
    public boolean encloses(T value);

    public int hashCode();

    public boolean equals(Object other);
}
