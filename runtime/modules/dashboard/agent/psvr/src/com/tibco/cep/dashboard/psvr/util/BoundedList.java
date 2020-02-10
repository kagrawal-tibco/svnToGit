package com.tibco.cep.dashboard.psvr.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class BoundedList<E> implements List<E> {

	private int capacity;

	private LinkedList<E> backingList;

	public BoundedList(int capacity) {
		this.capacity = capacity;
		this.backingList = new LinkedList<E>();
	}

	@Override
	public boolean add(E e) {
		if (backingList.size() == capacity) {
			backingList.removeFirst();
		}
		return backingList.add(e);
	}

	@Override
	public void add(int index, E element) {
		if (index >= capacity){
			throw new IndexOutOfBoundsException(index +" >= "+capacity);
		}
		if (backingList.size() == capacity) {
			backingList.removeFirst();
		}
		backingList.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index >= capacity){
			throw new IndexOutOfBoundsException(index +" >= "+capacity);
		}		
		for (E e : c) {
			add(index, e);
			index = index + 1 >= capacity ? index : index + 1;   
		}
		return true;
	}

	@Override
	public void clear() {
		backingList.clear();
	}

	@Override
	public boolean contains(Object o) {
		return backingList.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return backingList.containsAll(c);
	}

	@Override
	public E get(int index) {
		if (index >= capacity){
			throw new IndexOutOfBoundsException(index +" >= "+capacity);
		}		
		return backingList.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return backingList.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return backingList.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return backingList.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return backingList.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return backingList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return backingList.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return backingList.remove(o);
	}

	@Override
	public E remove(int index) {
		return backingList.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return backingList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return backingList.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		if (index >= capacity){
			throw new IndexOutOfBoundsException(index +" >= "+capacity);
		}
		return backingList.set(index, element);
	}

	@Override
	public int size() {
		return backingList.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return backingList.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return backingList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return backingList.toArray(a);
	}

	@Override
	public int hashCode() {
		return backingList.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return backingList.equals(obj);
	}
	
	@Override
	public String toString() {
		return backingList.toString();
	}
}
