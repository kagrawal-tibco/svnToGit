package com.tibco.cep.query.stream.util;

import java.util.Collection;
import java.util.NoSuchElementException;

/*
 * Author: Ashwin Jayaprakash Date: Jan 28, 2008 Time: 2:27:50 PM
 */

/**
 * Does/Cannot not store <code>null</code> as the element.
 */
public final class SingleElementCollection<E> implements CustomCollection<E> {
    protected E element;

    public SingleElementCollection(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E e) {
        this.element = e;
    }

    public void clear() {
        element = null;
    }

    /**
     * @throws UnsupportedOperationException
     */
    public boolean add(E o) {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException
     */
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        return (element == o) || (element != null && o != null && element.equals(o));
    }

    /**
     * @throws UnsupportedOperationException
     */
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * @return <code>true</code> if the {@link #getElement()} is <code>null</code>.
     */
    public boolean isEmpty() {
        return element == null;
    }

    public ReusableIterator<E> iterator() {
        return new CollectionIterator();
    }

    public boolean remove(Object o) {
        if (contains(o)) {
            element = null;
            return true;
        }

        return false;
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

    public int size() {
        return isEmpty() ? 0 : 1;
    }

    public Object[] toArray() {
        Object[] array = new Object[size()];

        int x = 0;
        for (E e : this) {
            array[x++] = e;
        }

        return array;
    }

    public <T> T[] toArray(T[] a) {
        T[] newA = a;
        if (a.length < size()) {
            newA = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(),
                    size());
        }

        Object[] array = newA;
        int x = 0;
        for (E e : this) {
            array[x++] = e;
        }

        return (T[]) array;
    }

    // -----------

    public class CollectionIterator implements ReusableIterator<E> {
        protected int counter;

        public CollectionIterator() {
            reset();
        }

        public void reset() {
            counter = SingleElementCollection.this.size();
        }

        public boolean hasNext() {
            return counter > 0;
        }

        public E next() {
            if (counter == 0) {
                throw new NoSuchElementException();
            }

            counter--;

            return SingleElementCollection.this.getElement();
        }

        public void remove() {
            SingleElementCollection.this.setElement(null);
        }
    }
}
