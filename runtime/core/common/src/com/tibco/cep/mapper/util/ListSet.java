/*******************************************************************************
 * Copyright 2000,2001 by TIBCO, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

/**
 * Implements the set interface.  Keeps ordering of elements,
 * so that its preserved on isertion.
 */
public class ListSet implements SortedSet
{
	private List mList = new LinkedList();

    public ListSet() {
        super();
    }

    public ListSet(Collection coll) {
        mList.addAll(coll);
    }

    private ListSet(LinkedList list) {
        mList = list;
    }

	public int size()
	{
		return mList.size();
	}
	
	public boolean isEmpty()
	{
		return mList.isEmpty();
	}
	
	public boolean contains(Object o)
	{
		return mList.contains(o);
	}
	
	public Iterator iterator()
	{
		return mList.iterator();
	}
	
	public Object[] toArray()
	{
		return mList.toArray();
	}
	
	public Object[] toArray(Object a[])
	{
		return mList.toArray(a);
	}
	
	public boolean add(Object o)
	{
		if (mList.contains(o) == false) {
			return mList.add(o);
		}
		return false;
	}
	
	public boolean remove(Object o)
	{
		return mList.remove(o);
	}
	
	public boolean containsAll(Collection c)
	{
		return mList.containsAll(c);
	}
	
	public boolean addAll(Collection c)
	{
		boolean changed = false;
		Iterator iter = c.iterator();
		while(iter.hasNext())
		{
			Object obj = iter.next();
			if (mList.contains(obj) == false) {
				changed = mList.add(obj);
			}
		}
		return changed;
	}
	
	public boolean retainAll(Collection c)
	{
		return mList.retainAll(c);
	}
	
	public boolean removeAll(Collection c)
	{
		return mList.removeAll(c);
	}
	
	public void clear()
	{
		mList.clear();
	}
	
	public boolean equals(Object o)
	{
		return mList.equals(o);
	}
	
	public int hashCode()
	{
		return mList.hashCode();
	}

    public Comparator comparator()
    {
        return null;
    }

    public SortedSet subSet(Object fromElement, Object toElement)
    {
        int from = mList.indexOf(fromElement);
        int to = mList.lastIndexOf(toElement);
        return new ListSet(mList.subList(from, to));
    }

    public SortedSet headSet(Object toElement)
    {
        int to = mList.lastIndexOf(toElement);
        return new ListSet(mList.subList(0, to));
    }

    public SortedSet tailSet(Object fromElement)
    {
        int from = mList.indexOf(fromElement);
        return new ListSet(mList.subList(from, mList.size()-1));
    }

    public Object first()
    {
        if (mList.size() == 0) {
            return null;
        }
        return mList.get(0);
    }

    public Object last()
    {
        if (mList.size() == 0) {
            return null;
        }
        return mList.get(mList.size()-1);
    }
}
