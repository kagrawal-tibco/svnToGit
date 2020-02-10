package com.tibco.cep.query.stream.sort;

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.CustomCollection;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.ReusableIterator;

/*
* Author: Ashwin Jayaprakash Date: Apr 16, 2008 Time: 6:45:41 PM
*/

/**
 * All the leaf level maps are connected via {@link #getNext()} and {@link #getPrevious()}.
 */
public class HierarchicalSortedLeafMap extends GenericHierarchicalSortedMap {
    protected HierarchicalSortedLeafMap previous;

    protected HierarchicalSortedLeafMap next;

    protected int size;

    public HierarchicalSortedLeafMap(Comparator c) {
        super(c);
    }

    public HierarchicalSortedLeafMap getPrevious() {
        return previous;
    }

    public void setPrevious(HierarchicalSortedLeafMap previous) {
        this.previous = previous;
    }

    public HierarchicalSortedLeafMap getNext() {
        return next;
    }

    public void setNext(HierarchicalSortedLeafMap next) {
        this.next = next;
    }

    /**
     * @return A complete collection/iterator that starts from this map and automatically chains all
     *         the other leaves using the references stored in {@link #getNext()}.
     */
    public CustomCollection<Tuple> getCompleteValueChain() {
        return new ReadonlyCollection(this);
    }

    /**
     * Connects both ways.
     *
     * @param other
     */
    public void linkAsPrevious(HierarchicalSortedLeafMap other) {
        if (previous != null) {
            previous.setNext(other);
            other.setPrevious(previous);
        }

        other.setNext(this);
        previous = other;
    }

    /**
     * Connects both ways.
     *
     * @param other
     */
    public void linkAsNext(HierarchicalSortedLeafMap other) {
        if (next != null) {
            next.setPrevious(other);
            other.setNext(next);
        }

        other.setPrevious(this);
        next = other;
    }

    /**
     * Automatically connects the previous and next.
     */
    @Override
    public void discard() {
        for (Object o : delegateMap.keySet()) {
            if (o instanceof CustomHashSet) {
                ((CustomHashSet) o).clear();
            }
        }
        delegateMap.clear();

        super.discard();

        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }

        next = null;
        previous = null;
    }

    //------------

    @Override
    public int size() {
        return size;
    }

    /**
     * Can store more than one value under the same key. Therefore the {@link #get(Object)} method
     * can return either a {@link com.tibco.cep.query.stream.tuple.Tuple} or a {@link
     * com.tibco.cep.query.stream.util.CustomCollection}.
     *
     * @param key
     * @param o
     */
    @Override
    public void put(Object key, Object o) {
        final Tuple tuple = (Tuple) o;
        final Object existingValue = get(key);

        if (existingValue == null) {
            super.put(key, tuple);

            size++;

            return;
        }
        else if (existingValue instanceof CustomHashSet) {
            CustomHashSet<Tuple> existingSet = (CustomHashSet<Tuple>) existingValue;
            existingSet.add(tuple);

            size++;

            return;
        }

        CustomHashSet<Tuple> newSet = new CustomHashSet<Tuple>();
        newSet.add((Tuple) existingValue);
        newSet.add(tuple);

        size++;

        super.put(key, newSet);
    }

    /**
     * @param key
     */
    @Override
    public void remove(Object key) {
        Object removedObject = delegateMap.remove(key);

        if (removedObject == null) {
            return;
        }

        if (removedObject instanceof CustomHashSet) {
            CustomHashSet set = (CustomHashSet) removedObject;

            size = size - set.size();
        }
        else {
            size--;
        }
    }

    /**
     * @param key
     * @param tuple
     */
    public void removeTuple(Object key, Tuple tuple) {
        Object removedObject = delegateMap.remove(key);

        if (removedObject == null) {
            return;
        }

        if (removedObject instanceof CustomHashSet) {
            CustomHashSet set = (CustomHashSet) removedObject;

            set.remove(tuple);

            //Still has some elements. So, put it back.
            if (set.size() > 0) {
                super.put(key, set);
            }
        }

        size--;
    }

    //------------

    protected static class ReadonlyCollection extends AbstractCollection<Tuple> implements
            CustomCollection<Tuple> {
        protected HierarchicalSortedLeafMap start;

        public ReadonlyCollection(HierarchicalSortedLeafMap start) {
            this.start = start;
        }

        public ReadOnlyIterator iterator() {
            return new ReadOnlyIterator(start);
        }

        /**
         * Expensive operation.
         *
         * @return
         */
        @Override
        public boolean isEmpty() {
            return iterator().hasNext() == false;
        }

        /**
         * Expensive operation.
         *
         * @return Total size of all leaf-maps.
         */
        public int size() {
            int totalSize = 0;

            HierarchicalSortedLeafMap tmpLeaf = start;
            while (tmpLeaf != null) {
                totalSize += tmpLeaf.size();

                tmpLeaf = tmpLeaf.next;
            }

            return totalSize;
        }
    }

    public static class ReadOnlyIterator implements ReusableIterator<Tuple> {
        protected HierarchicalSortedLeafMap currentLeafMap;

        protected Iterator currentLeafMapValueIter;

        protected Iterator<Tuple> innerSetIterator;

        protected HierarchicalSortedLeafMap initialLeafMap;

        public ReadOnlyIterator(HierarchicalSortedLeafMap start) {
            this.initialLeafMap = start;
            doReset();
        }

        public void reset(HierarchicalSortedLeafMap start) {
            this.initialLeafMap = start;
            doReset();
        }

        public void reset() {
            doReset();
        }

        private void doReset() {
            this.currentLeafMap = this.initialLeafMap;
            this.currentLeafMapValueIter = this.currentLeafMap.delegateMap.values().iterator();
            innerSetIterator = null;
        }

        public boolean hasNext() {
            if (innerSetIterator != null && innerSetIterator.hasNext()) {
                return true;
            }

            //---------

            if (currentLeafMapValueIter.hasNext()) {
                return true;
            }

            //---------

            HierarchicalSortedLeafMap tmpLeaf = currentLeafMap.next;
            while (tmpLeaf != null && tmpLeaf.size() == 0) {
                tmpLeaf = tmpLeaf.next;
            }

            return tmpLeaf != null;
        }

        public Tuple next() {
            for (; ;) {
                if (innerSetIterator != null) {
                    if (innerSetIterator.hasNext()) {
                        return innerSetIterator.next();
                    }

                    innerSetIterator = null;
                }

                //---------

                if (currentLeafMapValueIter != null) {
                    if (currentLeafMapValueIter.hasNext()) {
                        Object element = currentLeafMapValueIter.next();

                        if (element instanceof CustomHashSet) {
                            innerSetIterator = ((CustomHashSet<Tuple>) element).iterator();

                            continue;
                        }

                        return (Tuple) element;
                    }

                    currentLeafMapValueIter = null;
                }

                //---------

                if (currentLeafMap != null) {
                    currentLeafMap = currentLeafMap.next;

                    if (currentLeafMap != null) {
                        currentLeafMapValueIter = currentLeafMap.delegateMap.values().iterator();

                        continue;
                    }
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
