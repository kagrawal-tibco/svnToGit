/**
 * 
 */
package com.tibco.cep.decision.table.model.domainmodel.impl;

import static com.tibco.cep.decision.table.model.domainmodel.util.SecurityManager.checkPermission;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;

/**
 * This is a custom <tt>EList</tt> written for the purpose of BE 3.0
 * <b><i>Decision</i></b> application.
 * <p>
 * All list write operations like <tt>add</tt>, <tt>remove</tt>,
 * <tt>set</tt> etc. have to go through an authorization check.
 * @generated NOT
 * @author aathalye
 *
 */
public class DomainModelEntryList<T extends DomainEntry> extends BasicEList<T> implements EList<T> {
	
	private static final long serialVersionUID = 1L;
	
	protected DomainModelEntryList(final BasicEList<T> list,
			                       final ArgumentProperty argProperty) {
		this.composedList = list;
		this.argProperty = argProperty;
	}
	
	private BasicEList<T> composedList; //The decorated list
	
	private ArgumentProperty argProperty;
	
	
	/**
	 * @throws java.lang.SecurityException
	 * @see org.eclipse.emf.common.util.EList#move(int, java.lang.Object)
	 */
	public void move(int arg0, T entry) {
		if (checkPermission("modify", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				composedList.move(arg0, entry);
			}
		}
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see org.eclipse.emf.common.util.EList#move(int, int)
	 */
	public T move(int arg0, int arg1) {
		if (checkPermission("modify", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				return composedList.move(arg0, arg1);
			}
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.util.BasicEList#isUnique()
	 */
	@Override
	protected boolean isUnique() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.util.BasicEList#useEquals()
	 */
	@Override
	protected boolean useEquals() {
		return super.useEquals() || true;
	}
	
	/**
	 * @param entry
	 * @return
	 */
	public int indexOf(T entry) {
		Iterator<T> elements = composedList.iterator();
		int counter = 0;
		if(useEquals() && entry != null) {
			while (elements.hasNext()) {
				counter++;
			    if(entry.equals(elements.next())) {
				    return counter;
			    }
		    }
        }
	    return -1;
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(T entry) {
		boolean add = false;
		if (checkPermission("create", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				if(indexOf(entry) == -1) {
					composedList.add(entry);
				}
			}
		}
		/*TRACE.logDebug(this.getClass().getName(), 
				       "Entry {0} added to list: {1}", entry.getEntryName(), add);*/
		return add;
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, T entry) {
		if (checkPermission("create", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				if (indexOf(entry) == -1) {
					composedList.add(index, entry);
				}
			}
		}
	}
	
	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#addAll(java.util.Collection)
	 * Do Not use -> Out of Order Temporarily
	 */
	public boolean addAll(Collection<? extends T> c) {
		boolean add = true;
		if (checkPermission("create", argProperty.getPath(), argProperty.getResourceType())) {
			Iterator<? extends T> values = c.iterator();
			synchronized (composedList) {
				while (values.hasNext()) {
					T value = values.next();
					if (composedList.contains(value)) {
						return add = false;
					}
					add = composedList.add(value);
				}
			}
		}
		return add;
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#addAll(int, java.util.Collection)
	 * Do Not use -> Out of Order Temporarily
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean add = true;
		if (checkPermission("create", argProperty.getPath(), argProperty.getResourceType())) {
			Iterator<? extends T> values = c.iterator();
			synchronized (composedList) {
				while (values.hasNext()) {
					T value = values.next();
					if (composedList.contains(value)) {
						return add = false;
					}
					composedList.add(index++, value);
				}
			}
		}
		return add;
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#clear()
	 */
	public void clear() {
		if (checkPermission("delete", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				composedList.clear();
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public synchronized boolean contains(Object entry) {
		return composedList.contains(entry);
	}

	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public synchronized boolean containsAll(Collection<?> c) {
		return composedList.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	public synchronized T get(int index) {
		return composedList.get(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public synchronized int indexOf(Object o) {
		return composedList.indexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	public synchronized boolean isEmpty() {
		return composedList.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.List#iterator()
	 */
	public synchronized Iterator<T> iterator() {
		return composedList.iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public synchronized int lastIndexOf(Object o) {
		return composedList.indexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	public synchronized ListIterator<T> listIterator() {
		return composedList.listIterator();
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	public synchronized ListIterator<T> listIterator(int index) {
		return composedList.listIterator(index);
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object element) {
		if (checkPermission("delete", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				return composedList.remove(element);
			}
		}
		return false;
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#remove(int)
	 */
	public T remove(int index) {
		if (checkPermission("delete", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				return composedList.remove(index);
			}
		}
		return null;
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		if (checkPermission("delete", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				return composedList.removeAll(c);
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public synchronized boolean retainAll(Collection<?> c) {
		return composedList.retainAll(c);
	}

	/**
	 * @throws java.lang.SecurityException
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public T set(int index, T element) {
		if (checkPermission("modify", argProperty.getPath(), argProperty.getResourceType())) {
			synchronized (composedList) {
				return composedList.set(index, element);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#size()
	 */
	public synchronized int size() {
		return composedList.size();
	}

	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	public synchronized List<T> subList(int fromIndex, int toIndex) {
		return composedList.subList(fromIndex, toIndex);
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray()
	 */
	public synchronized Object[] toArray() {
		return composedList.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray(T[])
	 */
	public synchronized <E> E[] toArray(E[] a) {
		return composedList.toArray(a);
	}
}
