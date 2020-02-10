package com.tibco.cep.query.stream.util;

/*
 * Author: Ashwin Jayaprakash Date: Feb 7, 2008 Time: 10:44:59 AM
 */

//todo Make this more dynamic. Auto resize.
public final class SimplePool<E> {
    protected Object[] pool;

    protected int currPosition;

    public SimplePool(int maxSize) {
        this.pool = new Object[maxSize];
        this.currPosition = -1;
    }

    public int getMaxSize() {
        return pool.length;
    }

    public int getNumPooledElements() {
        return currPosition + 1;
    }

    /**
     * @param e Cannot be <code>null</code>.
     * @return <code>false</code> if the element was not added to the pool because it was full.
     */
    public boolean returnOrAdd(E e) {
        int x = currPosition + 1;

        if (x == pool.length) {
            return false;
        }

        currPosition = x;
        pool[x] = e;

        return true;
    }

    /**
     * @return <code>null</code> if the pool was empty.
     */
    @SuppressWarnings("unchecked")
    public E fetch() {
        if (currPosition >= 0) {
            return (E) pool[currPosition--];
        }

        return null;
    }

    public void discard() {
        for (int i = 0; i < pool.length; i++) {
            pool[i] = null;
        }

        pool = null;
    }
}
