package com.tibco.cep.query.stream._retired_;

import com.tibco.cep.query.stream.tuple.Tuple;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 10:57:28 AM
*/
public class MultiLinkedTupleSet<M extends Tuple> extends AbstractCollection<M> {
    protected LinkedTupleSet<M> firstTupleSet;

    private Boolean empty;

    private Integer size;

    public MultiLinkedTupleSet(LinkedTupleSet<M> firstTupleSet) {
        this.firstTupleSet = firstTupleSet;
    }

    public Iterator<M> iterator() {
        return new MLTSIterator();
    }

    /**
     * <b>Expensive operation when invoked for the first time.</b>
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        if (empty != null) {
            return empty;
        }

        //--------

        LinkedTupleSet<M> ts = firstTupleSet;

        while (ts != null) {
            Collection<M> collection = ts.getTuples();
            if (collection.isEmpty() == false) {
                empty = false;

                return empty;
            }

            ts = ts.getNext();
        }

        empty = true;

        return empty;
    }

    /**
     * <b>Expensive operation when invoked for the first time.</b>
     *
     * @return
     */
    @Override
    public int size() {
        if (size != null) {
            return size;
        }

        //----------

        int s = 0;
        LinkedTupleSet<M> ts = firstTupleSet;

        while (ts != null) {
            Collection<M> collection = ts.getTuples();
            s = s + collection.size();

            ts = ts.getNext();
        }

        size = s;

        return size;
    }

    //---------

    protected class MLTSIterator implements Iterator<M> {
        LinkedTupleSet<M> currentSet;

        Iterator<M> currentSetIterator;

        LinkedTupleSet<M> nextSet;

        Iterator<M> nextSetIterator;

        State state;

        protected MLTSIterator() {
            this.currentSet = MultiLinkedTupleSet.this.firstTupleSet;
            this.currentSetIterator = this.currentSet.getTuples().iterator();
            this.state = State.DONT_KNOW;
        }

        public boolean hasNext() {
            if (state != State.DONT_KNOW) {
                //Return cached value.
                return state.getValue();
            }

            boolean hasNext = currentSetIterator.hasNext();
            if (hasNext) {
                state = State.TRUE;
            }
            else {
                //Search the next one.
                nextSet = currentSet.getNext();

                do {
                    if (nextSet != null) {
                        nextSetIterator = nextSet.getTuples().iterator();

                        if (nextSetIterator.hasNext()) {
                            state = State.TRUE_BUT_MOVE_NEXT;
                        }
                        else {
                            state = State.FALSE_BUT_MOVE_NEXT;
                            nextSet = nextSet.getNext();
                        }
                    }
                    else {
                        state = State.THE_END;
                    }
                }
                while (state == State.FALSE_BUT_MOVE_NEXT);
            }

            return state.getValue();
        }

        public M next() {
            boolean keepRunning = true;

            while (keepRunning) {
                switch (state) {
                    case TRUE_BUT_MOVE_NEXT:
                        currentSet = nextSet;
                        currentSetIterator = nextSetIterator;
                        nextSet = null;
                        nextSetIterator = null;
                    case TRUE:
                        //Clear the cached value.
                        state = State.DONT_KNOW;

                        return currentSetIterator.next();

                    case FALSE_BUT_MOVE_NEXT:
                        currentSet = nextSet;
                        nextSet = null;
                        nextSetIterator = null;
                    case DONT_KNOW:
                        //Clear the cached value.
                        state = State.DONT_KNOW;

                        /*
                        Caller did not call hasNext() before invoking next(). Only option is to call it
                        ourselves and search recursively/loop.
                        */
                        hasNext();
                        continue;

                    case THE_END:
                        keepRunning = false;
                }
            }

            throw new NoSuchElementException();
        }

        /**
         * @throws UnsupportedOperationException
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected static enum State {
        TRUE(Boolean.TRUE),
        TRUE_BUT_MOVE_NEXT(Boolean.TRUE),
        FALSE_BUT_MOVE_NEXT(null),
        THE_END(Boolean.FALSE),
        DONT_KNOW(null);

        private Boolean value;

        private State(Boolean value) {
            this.value = value;
        }

        public Boolean getValue() {
            return value;
        }
    }
}
