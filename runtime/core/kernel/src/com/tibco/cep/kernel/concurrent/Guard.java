package com.tibco.cep.kernel.concurrent;

/*
* Author: Ashwin Jayaprakash Date: Dec 5, 2008 Time: 4:51:45 PM
*/
public interface Guard {
    /**
     * Blocks until lock can be acquired. Implementors can throw {@link RuntimeException} if
     * something goes wrong.
     */
    void lock();

    /**
     * Unlocks only if the calling Thread has already acquired the lock before using {@link
     * #lock()}. Implementors can throw {@link RuntimeException} if something goes wrong.
     */
    void unlock();
}
