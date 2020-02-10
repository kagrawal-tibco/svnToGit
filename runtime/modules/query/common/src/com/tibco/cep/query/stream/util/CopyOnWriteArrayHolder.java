package com.tibco.cep.query.stream.util;

import java.util.concurrent.locks.ReentrantLock;

/*
 * Author: Ashwin Jayaprakash Date: Mar 4, 2008 Time: 5:58:27 PM
 */

public final class CopyOnWriteArrayHolder<E> {
    final ReentrantLock lock;

    //Put this in a separate cacheline.
    final VolatileHolder vh;

    public CopyOnWriteArrayHolder() {
        this.lock = new ReentrantLock();
        this.vh = new VolatileHolder();

        this.vh.array = new Object[0];
    }

    /**
     * @param e Cannot be <code>null</code>.
     * @return New array with the element.
     */
    public Object[] add(E e) {
        lock.lock();
        try {
            Object[] oldArray = vh.array;

            Object[] newArray = new Object[oldArray.length + 1];

            if (oldArray.length <= 32) {
                int i = 0;
                for (; i < oldArray.length; i++) {
                    newArray[i] = oldArray[i];
                }
                newArray[i] = e;
            }
            else {
                System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
                newArray[oldArray.length] = e;
            }

            vh.array = newArray;
        }
        finally {
            lock.unlock();
        }

        return vh.array;
    }

    /**
     * @param e Cannot be <code>null</code>.
     * @return New array without the element.
     */
    public Object[] remove(E e) {
        lock.lock();
        try {
            Object[] oldArray = vh.array;

            int size = oldArray.length;
            Object[] newArray = (size == 0) ? oldArray : new Object[size - 1];

            int i = 0;
            int n = 0;
            for (; i < oldArray.length; i++) {
                if (oldArray[i] != e && oldArray[i].equals(e) == false) {
                    newArray[n] = oldArray[i];
                    n++;
                }
            }

            vh.array = newArray;
        }
        finally {
            lock.unlock();
        }

        return vh.array;
    }

    /**
     * Do not modify this array.
     *
     * @return
     */
    public Object[] getInternalArray() {
        return vh.array;
    }

    public int getLength() {
        return vh.array.length;
    }

    protected static class VolatileHolder {
        volatile Object[] array;
    }
}
