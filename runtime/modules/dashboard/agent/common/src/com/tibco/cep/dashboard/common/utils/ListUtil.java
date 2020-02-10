package com.tibco.cep.dashboard.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

public class ListUtil {
	
	public static final <T> boolean addUnique(List<T> list, T object) {
		boolean added = false;

		if (!list.contains(object)) {
			list.add(object);
			added = true;
		}
		return added;
	}

	public static final <T> boolean addUnique(List<T> list, List<T> addlist) {
		boolean added = false;

		for (Iterator<T> itr = addlist.iterator(); itr.hasNext();) {
			if (addUnique(list, itr.next())) {
				added = true;
			}
		}
		return added;
	}

	public static final <T> boolean addUnique(Vector<T> vector, T object) {
		boolean added = false;

		if (!vector.contains(object)) {
			vector.add(object);
			added = true;
		}
		return added;
	}

	public static final <T> boolean addUnique(Vector<T> vector, Vector<T> addvector) {
		boolean added = false;

		for (Iterator<T> itr = addvector.iterator(); itr.hasNext();) {
			if (addUnique(vector, itr.next())) {
				added = true;
			}
		}
		return added;
	}

	public static final <T> boolean addUnique(TreeSet<T> set, T object) {
		boolean added = false;

		if (!set.contains(object)) {
			set.add(object);
			added = true;
		}
		return added;
	}

	public static final boolean contains(Object[] array, Object item) {
		if (array == null)
			return false;

		for (int i = 0; i < array.length; i++) {
			if (array[i] == null)
				continue;

			if (array[i].equals(item))
				return true;
		}

		return false;
	}

	public static final boolean containsIgnoreCase(String[] array, String item) {
		if (array == null)
			return false;

		item = item.toLowerCase();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null)
				continue;

			if (array[i].equals(item))
				return true;
		}

		return false;
	}

	public static final <T> ArrayList<T> makeList(T item) {
		ArrayList<T> list = new ArrayList<T>();
		list.add(item);
		return list;
	}

	public static final <T> ArrayList<T> makeList(T item1, T item2) {
		ArrayList<T> list = new ArrayList<T>();
		list.add(item1);
		list.add(item2);
		return list;
	}

	public static final <T> ArrayList<T> makeList(T item1, T item2, T item3) {
		ArrayList<T> list = new ArrayList<T>();
		list.add(item1);
		list.add(item2);
		list.add(item3);
		return list;
	}

	public static final Object[] arrayCopy(Object[] items1, Object[] items2) {
		Object[] result = new Object[items1.length + items2.length];
		System.arraycopy(items1, 0, result, 0, items1.length);
		System.arraycopy(items2, 0, result, items1.length, items2.length);
		return result;
	}
	
	public static final <T> String joinList(List<T> list, String separator){
		StringBuilder sb = new StringBuilder();
		Iterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			T object = iterator.next();
			sb.append(object.toString());
			if (iterator.hasNext()){
				sb.append(separator);
			}
		}
		return sb.toString();
	}
	
	public static final String joinArray(Object[] array,String separator){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
			if (i + 1 < array.length){
				sb.append(separator);
			}
		}
		return sb.toString();
	}

}
