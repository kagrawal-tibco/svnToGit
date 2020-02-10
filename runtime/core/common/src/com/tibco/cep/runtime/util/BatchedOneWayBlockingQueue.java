package com.tibco.cep.runtime.util;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash Date: May 12, 2009 Time: 3:17:59 PM
*/

/**
 * One way queue - only dequeue operations are supported. Enqueue operations may be performed on the
 * internal queue {@link #getInternalRawQ()}. <p></p> {@link #poll()}, {@link #poll(long,
 * java.util.concurrent.TimeUnit)} and {@link #take()} retrieve multiple elements at once. The
 * maximum number of elements retrieved will be {@link #getBatchMaxSize()}.
 */
public class BatchedOneWayBlockingQueue<E> extends AbstractQueue<Collection<E>>
        implements BlockingQueue<Collection<E>> {
    protected BlockingQueue<E> internalRawQ;

    protected int batchMaxSize;

    /**
     * @param internalRawQ The actual queue that is wrapped by this class.
     * @param batchMaxSize
     */
    public BatchedOneWayBlockingQueue(BlockingQueue<E> internalRawQ, int batchMaxSize) {
        this.internalRawQ = internalRawQ;
        this.batchMaxSize = batchMaxSize;
    }

    public BlockingQueue<E> getInternalRawQ() {
        return internalRawQ;
    }

    public int getBatchMaxSize() {
        return batchMaxSize;
    }

    //-------------

    /**
     * @param timeout
     * @param unit    If <code>null</code>, then does a {@link java.util.concurrent.BlockingQueue#take()}.
     *                Otherwise does a {@link java.util.concurrent.BlockingQueue#poll(long,
     *                java.util.concurrent.TimeUnit)}.
     * @return
     * @throws InterruptedException
     */
    protected Collection<E> pollOrTake(long timeout, TimeUnit unit) throws InterruptedException {
        LinkedList<E> drainToQ = new LinkedList<E>();
        int c = internalRawQ.drainTo(drainToQ, batchMaxSize);
        if (c > 0) {
            return drainToQ;
        }

        E singleElement = null;
        if (unit != null) {
            singleElement = internalRawQ.poll(timeout, unit);
        }
        else {
            singleElement = internalRawQ.take();
        }

        if (singleElement != null) {
            drainToQ.add(singleElement);

            internalRawQ.drainTo(drainToQ, batchMaxSize - 1);

            return drainToQ;
        }

        return null;
    }

    public Collection<E> poll(long timeout, TimeUnit unit) throws InterruptedException {
        return pollOrTake(timeout, unit);
    }

    public Collection<E> poll() {
        LinkedList<E> drainToQ = new LinkedList<E>();

        int c = internalRawQ.drainTo(drainToQ, batchMaxSize);
        if (c > 0) {
            return drainToQ;
        }

        return null;
    }

    public Collection<E> peek() {
        E singleElement = internalRawQ.peek();
        if (singleElement != null) {
            LinkedList<E> list = new LinkedList<E>();
            list.add(singleElement);

            return list;
        }

        return null;
    }

    public Collection<E> take() throws InterruptedException {
        return pollOrTake(Integer.MIN_VALUE, null);
    }

    //-------------

    public Iterator<Collection<E>> iterator() {
        return new InternalIterator();
    }

    /**
     * Cummulative size of all the individual items
     *
     * @return
     */
    public int size() {
        return internalRawQ.size();
    }

    public int remainingCapacity() {
        return internalRawQ.remainingCapacity();
    }

    /**
     * @param o
     * @return Always <code>false</code>. Does not add anything.
     */
    public boolean offer(Collection<E> o) {
        return false;
    }

    /**
     * @param o
     * @param timeout
     * @param unit
     * @return Always <code>false</code>. Does not add anything.
     */
    public boolean offer(Collection<E> o, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /**
     * @param o
     * @throws UnsupportedOperationException
     */
    public void put(Collection<E> o) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param c
     * @return Always 0. Does not add anything.
     */
    public int drainTo(Collection<? super Collection<E>> c) {
        return 0;
    }

    /**
     * @param c
     * @param maxElements
     * @return Always 0. Does not add anything.
     */
    public int drainTo(Collection<? super Collection<E>> c, int maxElements) {
        return 0;
    }

    //-------------

    protected class InternalIterator implements Iterator<Collection<E>> {
        protected Iterator<E> internalRawIter;

        public InternalIterator() {
            this.internalRawIter = BatchedOneWayBlockingQueue.this.internalRawQ.iterator();
        }

        public boolean hasNext() {
            return internalRawIter.hasNext();
        }

        public Collection<E> next() {
            E e = internalRawIter.next();

            LinkedList<E> list = new LinkedList<E>();
            list.add(e);

            return list;
        }

        public void remove() {
            internalRawIter.remove();
        }
    }
}
