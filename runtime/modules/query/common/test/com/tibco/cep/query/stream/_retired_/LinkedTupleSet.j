package com.tibco.cep.query.stream._retired_;

import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.SingleElementCollection;

import java.util.Collection;
import java.util.LinkedHashMap;

/*
 * Author: Ashwin Jayaprakash Date: Oct 4, 2007 Time: 5:41:51 PM
 */

public class LinkedTupleSet<T extends Tuple> {
    protected /*LinkedHashMap<Long, T>*/ Object tupleOrIdAndTupleMap;

    protected LinkedTupleSet<T> next;

    protected LinkedTupleSet<T> previous;

    public LinkedTupleSet() {
    }

    public LinkedTupleSet(Collection<? extends T> c) {
        LinkedHashMap<Long, T> map = new LinkedHashMap<Long, T>();
        this.tupleOrIdAndTupleMap = map;

        for (T tuple : c) {
            map.put(tuple.getId(), tuple);
        }
    }

    public void add(T tuple) {
        if (tupleOrIdAndTupleMap == null) {
            tupleOrIdAndTupleMap = tuple;

            return;
        }
        else if (tupleOrIdAndTupleMap instanceof LinkedHashMap) {
            LinkedHashMap<Long, T> map = (LinkedHashMap<Long, T>) tupleOrIdAndTupleMap;
            map.put(tuple.getId(), tuple);

            return;
        }

        LinkedHashMap<Long, T> map = new LinkedHashMap<Long, T>();
        T oldTuple = (T) tupleOrIdAndTupleMap;
        map.put(oldTuple.getId(), oldTuple);
        map.put(tuple.getId(), tuple);

        tupleOrIdAndTupleMap = map;
    }

    public void remove(T tuple) {
        if (tupleOrIdAndTupleMap instanceof LinkedHashMap) {
            LinkedHashMap<Long, T> map = (LinkedHashMap<Long, T>) tupleOrIdAndTupleMap;
            map.remove(tuple);

            //Cleanup.
            if (map.size() == 0) {
                tupleOrIdAndTupleMap = null;
            }

            return;
        }

        tupleOrIdAndTupleMap = null;
    }

    /**
     * @return <code>null</code> if there are no tuples inside.
     */
    public Collection<T> getTuples() {
        if (tupleOrIdAndTupleMap == null) {
            return null;
        }
        else if (tupleOrIdAndTupleMap instanceof LinkedHashMap) {
            LinkedHashMap<Long, T> map = (LinkedHashMap<Long, T>) tupleOrIdAndTupleMap;

            return map.values();
        }

        return new SingleElementCollection<T>((T) tupleOrIdAndTupleMap);
    }

    public int getSize() {
        if (tupleOrIdAndTupleMap == null) {
            return 0;
        }
        else if (tupleOrIdAndTupleMap instanceof LinkedHashMap) {
            LinkedHashMap<Long, T> map = (LinkedHashMap<Long, T>) tupleOrIdAndTupleMap;

            return map.size();
        }

        return 1;
    }

    // ---------

    public LinkedTupleSet<T> getNext() {
        return next;
    }

    public void setNext(LinkedTupleSet<T> next) {
        this.next = next;
    }

    public LinkedTupleSet<T> getPrevious() {
        return previous;
    }

    public void setPrevious(LinkedTupleSet<T> previous) {
        this.previous = previous;
    }

    public void linkAsPrevious(LinkedTupleSet<T> other) {
        if (previous != null) {
            previous.setNext(other);
            other.setPrevious(previous);
        }

        other.setNext(this);
        previous = other;
    }

    public void linkAsNext(LinkedTupleSet<T> other) {
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
    public void clear() {
        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }

        next = null;
        previous = null;
    }
}
