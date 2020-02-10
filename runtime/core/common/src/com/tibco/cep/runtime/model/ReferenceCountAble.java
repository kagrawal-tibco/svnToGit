package com.tibco.cep.runtime.model;

public interface ReferenceCountAble {
    /**
     *
     * @return int value after incrementing the reference count
     */
    int incrementCount();

    /**
     * Decrement the ref count and return the value
     * @return
     */
    int decrementCount();

    /**
     * get the Count.
     * @return
     */
    int getCount();
}
