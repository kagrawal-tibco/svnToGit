package com.tibco.cep.query.stream.util;

import java.util.Collection;
import java.util.NoSuchElementException;

/*
 * Author: Ashwin Jayaprakash Date: Jan 22, 2008 Time: 1:30:44 PM
 */

/**
 * <p>Highly optimized "Dynamic Array" that supports appends only, no random seeks and deletes.</p>
 * <p>Unlike the {@link java.util.ArrayList}, this implementation expands smoothly and does not copy
 * elements to the resized array. It grows in segments, thereby drastically reducing the load on the
 * GC by not asking for larger and larger arrays.</p>
 */
public final class AppendOnlyQueue<E> implements CustomCollection<E> {
    /**
     * {@value}.
     */
    public static final int DEFAULT_START_FRAGMENT_SIZE = ArrayPool.DEFAULT_FRAGMENT_START_SIZE;

    /**
     * {@value}.
     */
    public static final int MAX_FRAGMENT_SIZE = ArrayPool.DEFAULT_FRAGMENT_MAX_SIZE;

    private Chunk head;

    private int size;

    private Chunk currChunk;

    private int fragmentNumInCurrChunk;

    private int emptySlotNumInCurrFragment;

    /**
     * Can be <code>null</code>.
     */
    private ArrayPool arrayPool;

    /**
     * @param startFragmentSize Has to be a power of 2.
     * @param arrayPool         Can be <code>null</code>.
     */
    public AppendOnlyQueue(int startFragmentSize, ArrayPool arrayPool) {
        this.arrayPool = arrayPool;

        this.head = new Chunk(startFragmentSize);
        this.head.fragment0 = createArray(startFragmentSize);

        this.currChunk = this.head;
    }

    /**
     * @param arrayPool
     */
    public AppendOnlyQueue(ArrayPool arrayPool) {
        this(DEFAULT_START_FRAGMENT_SIZE, arrayPool);
    }

    /**
     * Uses {@link #DEFAULT_START_FRAGMENT_SIZE} and no {@link com.tibco.cep.query.stream.util.ArrayPool}.
     */
    public AppendOnlyQueue() {
        this(DEFAULT_START_FRAGMENT_SIZE, null);
    }

    /**
     * @param startFragmentSize Has to be a power of 2.
     */
    public AppendOnlyQueue(int startFragmentSize) {
        this(startFragmentSize, null);
    }

    private static int calcNextFragmentSize(int fragmentSizeInCurrChunk) {
        int i = (fragmentSizeInCurrChunk << 1);

        return (i > MAX_FRAGMENT_SIZE) ? MAX_FRAGMENT_SIZE : i;
    }

    private Object[] createArray(int arraySize) {
        return (arrayPool == null) ? new Object[arraySize] : arrayPool.createArray(arraySize);
    }

    private static void clearArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                break;
            }

            array[i] = null;
        }
    }

    private void discardArray(Object[] array, boolean clearElements) {
        if (clearElements) {
            clearArray(array);
        }

        if (arrayPool != null) {
            arrayPool.returnArray(array, false);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public ReusableIterator<E> iterator() {
        return new QueueIterator();
    }

    public Object[] toArray() {
        Object[] array = new Object[size];

        int x = 0;
        for (E e : this) {
            array[x++] = e;
        }

        return array;
    }

    public <T> T[] toArray(T[] a) {
        T[] newA = a;
        if (a.length < size) {
            newA = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        Object[] array = newA;
        int x = 0;
        for (E e : this) {
            array[x++] = e;
        }

        return (T[]) array;
    }

    public void discard() {
        boolean clearArray = (size > 0);

        for (Chunk c = head; c != null;) {
            if (c.fragment0 != null) {
                discardArray(c.fragment0, clearArray);
                c.fragment0 = null;
            }
            else {
                break;
            }

            if (c.fragment1 != null) {
                discardArray(c.fragment1, clearArray);
                c.fragment1 = null;
            }
            else {
                break;
            }

            if (c.fragment2 != null) {
                discardArray(c.fragment2, clearArray);
                c.fragment2 = null;
            }
            else {
                break;
            }

            if (c.fragment3 != null) {
                discardArray(c.fragment3, clearArray);
                c.fragment3 = null;
            }
            else {
                break;
            }

            Chunk temp = c.next;
            c.next = null;
            c = temp;
        }

        head = null;
        currChunk = null;
        fragmentNumInCurrChunk = 0;
        emptySlotNumInCurrFragment = 0;
        size = 0;
    }

    public void clear() {
        if (size == 0) {
            return;
        }

        for (Chunk c = head; c != null; c = c.next) {
            if (c.fragment0 != null) {
                clearArray(c.fragment0);
            }
            else {
                break;
            }

            if (c.fragment1 != null) {
                clearArray(c.fragment1);
            }
            else {
                break;
            }

            if (c.fragment2 != null) {
                clearArray(c.fragment2);
            }
            else {
                break;
            }

            if (c.fragment3 != null) {
                clearArray(c.fragment3);
            }
            else {
                break;
            }
        }

        currChunk = head;
        fragmentNumInCurrChunk = 0;
        emptySlotNumInCurrFragment = 0;
        size = 0;
    }

    /**
     * @param o
     * @return
     * @throw UnsupportedOperationException if parameter is <code>null</code>.
     */
    public boolean add(E o) {
        if (o == null) {
            throw new UnsupportedOperationException("Cannot add nulls.");
        }

        //Reached end of fragment.
        if (emptySlotNumInCurrFragment == currChunk.fragmentSize) {
            expandOrMove();
        }

        switch (fragmentNumInCurrChunk) {
            case 0:
                currChunk.fragment0[emptySlotNumInCurrFragment] = o;
                break;

            case 1:
                currChunk.fragment1[emptySlotNumInCurrFragment] = o;
                break;

            case 2:
                currChunk.fragment2[emptySlotNumInCurrFragment] = o;
                break;

            case 3:
                currChunk.fragment3[emptySlotNumInCurrFragment] = o;
                break;

            default:
                /*
                Should not reach here. If it has, then the number of fragments have been
                modified without changing this method correctly!!!
                */
        }

        emptySlotNumInCurrFragment++;

        size++;

        return true;
    }

    public boolean addAll(Collection<? extends E> elements) {
        for (E e : elements) {
            add(e);
        }

        return true;
    }


    private void expandOrMove() {
        //Need a new Chunk.
        if (fragmentNumInCurrChunk == Chunk.LAST_FRAGMENT_NUM_IN_CHUNK) {
            if (currChunk.next == null) {
                int newFragmentSize = calcNextFragmentSize(currChunk.fragmentSize);

                Chunk newChunk = new Chunk(newFragmentSize);
                newChunk.fragment0 = createArray(newFragmentSize);

                currChunk.next = newChunk;
                currChunk = newChunk;
            }
            else {
                currChunk = currChunk.next;
            }

            fragmentNumInCurrChunk = 0;
        }
        //Use the next fragment in the same Chunk.
        else {
            switch (fragmentNumInCurrChunk) {
                case 0:
                    if (currChunk.fragment1 == null) {
                        currChunk.fragment1 = createArray(currChunk.fragmentSize);
                    }
                    break;

                case 1:
                    if (currChunk.fragment2 == null) {
                        currChunk.fragment2 = createArray(currChunk.fragmentSize);
                    }
                    break;

                case 2:
                    if (currChunk.fragment3 == null) {
                        currChunk.fragment3 = createArray(currChunk.fragmentSize);
                    }
                    break;

                default:
                    /*
                    Should not reach here. If it has, then the number of fragments have been
                    modified without changing this method correctly!!!
                    */
            }

            fragmentNumInCurrChunk++;
        }

        emptySlotNumInCurrFragment = 0;
    }

    public boolean contains(Object o) {
        boolean retVal = false;

        for (E e : this) {
            if ((e == o) || (e != null && o != null && e.equals(o))) {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    public boolean containsAll(Collection<?> c) {
        boolean retVal = false;

        ReusableIterator<E> iter = iterator();

        for (Object o : c) {
            for (; iter.hasNext();) {
                E e = iter.next();

                if ((e == o) || (e != null && o != null && e.equals(o))) {
                    retVal = true;
                    break;
                }
            }

            if (retVal == false) {
                break;
            }

            // Re-use.
            iter.reset();
        }

        return retVal;
    }

    //-----------

    /**
     * @throws UnsupportedOperationException
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    // -----------

    protected static class Chunk {
        /**
         * Total number of fragments in a chunk = {@value} + 1.
         */
        public static final int LAST_FRAGMENT_NUM_IN_CHUNK = 3;

        final int fragmentSize;

        /*
        Chunks are accessed from 0 to 3. They can be null, or have values starting from fragment0[0]
        all the way up to fragment3[fragmentSize-1]. If fragment0 and fragment2 are non-null,
        fragment1 cannot be null.
        */
        Object[] fragment0;

        Object[] fragment1;

        Object[] fragment2;

        Object[] fragment3;

        Chunk next;

        public Chunk(int fragmentSize) {
            this.fragmentSize = fragmentSize;
        }
    }

    // -----------

    protected class QueueIterator implements ReusableIterator<E> {
        private Chunk chunk;

        private int fragmentNum;

        private int slotNum;

        public QueueIterator() {
            doReset();
        }

        public void reset() {
            doReset();
        }

        private void doReset() {
            this.chunk = AppendOnlyQueue.this.head;
            this.fragmentNum = 0;
            this.slotNum = 0;
        }

        public boolean hasNext() {
            if (chunk == null) {
                return false;
            }

            //End of fragment.
            if (slotNum == chunk.fragmentSize) {
                //End of chunk.
                if (fragmentNum == Chunk.LAST_FRAGMENT_NUM_IN_CHUNK) {
                    return chunk.next != null && chunk.next.fragment0[0] != null;
                }

                switch (fragmentNum) {
                    case 0:
                        return (chunk.fragment1 != null && chunk.fragment1[0] != null);

                    case 1:
                        return (chunk.fragment2 != null && chunk.fragment2[0] != null);

                    case 2:
                        return (chunk.fragment3 != null && chunk.fragment3[0] != null);

                    default:
                        /*
                        Should not reach here. If it has, then the number of fragments have been
                        modified without changing this method correctly!!!
                        */
                        return false;
                }
            }

            switch (fragmentNum) {
                case 0:
                    return (chunk.fragment0[slotNum] != null);

                case 1:
                    return (chunk.fragment1[slotNum] != null);

                case 2:
                    return (chunk.fragment2[slotNum] != null);

                case 3:
                    return (chunk.fragment3[slotNum] != null);

                default:
                    /*
                    Should not reach here. If it has, then the number of fragments have been
                    modified without changing this method correctly!!!
                    */
            }

            return false;
        }

        /**
         * @return
         * @throws java.util.NoSuchElementException
         *
         */
        public E next() {
            for (; ;) {
                if (chunk == null) {
                    throw new NoSuchElementException();
                }

                //End of fragment.
                if (slotNum == chunk.fragmentSize) {
                    //End of chunk.
                    if (fragmentNum == Chunk.LAST_FRAGMENT_NUM_IN_CHUNK) {
                        chunk = chunk.next;
                        fragmentNum = 0;
                        slotNum = 0;

                        continue;
                    }

                    fragmentNum++;
                    slotNum = 0;

                    continue;
                }

                switch (fragmentNum) {
                    case 0:
                        if (chunk.fragment0[slotNum] == null) {
                            throw new NoSuchElementException();
                        }

                        return (E) chunk.fragment0[slotNum++];

                    case 1:
                        if (chunk.fragment1[slotNum] == null) {
                            throw new NoSuchElementException();
                        }

                        return (E) chunk.fragment1[slotNum++];

                    case 2:
                        if (chunk.fragment2[slotNum] == null) {
                            throw new NoSuchElementException();
                        }

                        return (E) chunk.fragment2[slotNum++];

                    case 3:
                        if (chunk.fragment3[slotNum] == null) {
                            throw new NoSuchElementException();
                        }

                        return (E) chunk.fragment3[slotNum++];

                    default:
                        /*
                        Should not reach here. If it has, then the number of fragments have been
                        modified without changing this method correctly!!!
                        */
                }

                throw new NoSuchElementException();
            }
        }

        /**
         * @throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}