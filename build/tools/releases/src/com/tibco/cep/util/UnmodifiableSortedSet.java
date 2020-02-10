package com.tibco.cep.util;

import java.io.Serializable;
import java.util.*;

/**
 * User: nprade
 * Date: 6/29/11
 * Time: 4:25 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class UnmodifiableSortedSet<E>
        implements SortedSet<E>, NavigableSet<E>, Cloneable, Serializable {


    private final TreeSet<E> delegate;


    public UnmodifiableSortedSet(
            Collection<E> c) {

        this.delegate = new TreeSet<E>(c);
    }


    public UnmodifiableSortedSet(
            SortedSet<E> s) {

        this.delegate = new TreeSet<E>(s);
    }


    public Object[] toArray() {

        return this.delegate.toArray();
    }


    public <T> T[] toArray(
            T[] a) {

        //noinspection SuspiciousToArrayCall
        return this.delegate.toArray(a);
    }


    public boolean containsAll(
            Collection<?> c) {

        return this.delegate.containsAll(c);
    }


    public boolean retainAll(
            Collection<?> c) {

        return this.delegate.retainAll(c);
    }


    public String toString() {

        return "(unmodifiable) " + this.delegate.toString();
    }


    @SuppressWarnings({"EqualsWhichDoesntCheckParameterClass"})
    @Override
    public boolean equals(
            Object o) {

        return this.delegate.equals(o);
//        return (null != o)
//                && this.getClass().equals(o.getClass())
//                && this.delegate.equals(((UnmodifiableSortedSet) o).delegate);
    }


    public int hashCode() {
        return this.delegate.hashCode() + 1;
    }


    public boolean removeAll(
            Collection<?> c) {

        return this.delegate.removeAll(c);
    }


    public Iterator<E> iterator() {

        return this.delegate.iterator();
    }


    public Iterator<E> descendingIterator() {

        return this.delegate.descendingIterator();
    }


    public NavigableSet<E> descendingSet() {

        return this.delegate.descendingSet();
    }


    public int size() {

        return this.delegate.size();
    }


    public boolean isEmpty() {

        return this.delegate.isEmpty();
    }


    public boolean contains(
            Object o) {

        return this.delegate.contains(o);
    }


    public boolean add(
            E e) {

        return this.delegate.add(e);
    }


    public boolean remove(
            Object o) {

        return this.delegate.remove(o);
    }


    public void clear() {

        this.delegate.clear();
    }


    public boolean addAll(
            Collection<? extends E> c) {

        return this.delegate.addAll(c);
    }


    public NavigableSet<E> subSet(
            E fromElement,
            boolean fromInclusive,
            E toElement,
            boolean toInclusive) {

        return this.delegate.subSet(fromElement, fromInclusive, toElement, toInclusive);
    }


    public NavigableSet<E> headSet(
            E toElement,
            boolean inclusive) {

        return this.delegate.headSet(toElement, inclusive);
    }


    public NavigableSet<E> tailSet(
            E fromElement,
            boolean inclusive) {

        return this.delegate.tailSet(fromElement, inclusive);
    }


    public SortedSet<E> subSet(
            E fromElement,
            E toElement) {

        return this.delegate.subSet(fromElement, toElement);
    }


    public SortedSet<E> headSet(
            E toElement) {

        return this.delegate.headSet(toElement);
    }


    public SortedSet<E> tailSet(
            E fromElement) {

        return this.delegate.tailSet(fromElement);
    }


    public Comparator<? super E> comparator() {

        return this.delegate.comparator();
    }


    public E first() {

        return this.delegate.first();
    }


    public E last() {

        return this.delegate.last();
    }


    public E lower(
            E e) {

        return this.delegate.lower(e);
    }


    public E floor(
            E e) {

        return this.delegate.floor(e);
    }


    public E ceiling(
            E e) {

        return this.delegate.ceiling(e);
    }


    public E higher(
            E e) {

        return this.delegate.higher(e);
    }


    public E pollFirst() {

        return this.delegate.pollFirst();
    }


    public E pollLast() {
        return this.delegate.pollLast();
    }

}
