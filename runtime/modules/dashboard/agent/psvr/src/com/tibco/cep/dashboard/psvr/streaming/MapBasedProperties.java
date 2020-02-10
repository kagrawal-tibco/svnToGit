package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

final class MapBasedProperties extends Properties {

	private static final long serialVersionUID = 209701058954214790L;

	private Properties properties;
	private Map<String, String> map;

	MapBasedProperties(Properties properties, Map<String, String> map) {
		this.properties = properties;
		this.map = map;
	}

	@Override
	public String getProperty(String key) {
		String value = map != null ? map.get(key) : null;
		if (value == null) {
			return properties.getProperty(key);
		}
		return value;
	}

	@Override
	public synchronized boolean containsKey(Object key) {
		boolean containsKey = map.containsKey(key);
		if (containsKey == false) {
			containsKey = properties.containsKey(key);
		}
		return containsKey;
	}

	@Override
	public synchronized Enumeration<Object> keys() {
		return new Enumeration<Object>() {

			private Iterator<Object> keyIterator = keySet().iterator();

			@Override
			public boolean hasMoreElements() {
				return keyIterator.hasNext();
			}

			@Override
			public Object nextElement() {
				return keyIterator.next();
			}

		};

	}

	@Override
	public Set<Object> keySet() {
		return new Set<Object>() {

			@Override
			public boolean add(Object e) {
				throw new UnsupportedOperationException("add");
			}

			@Override
			public boolean addAll(Collection<? extends Object> c) {
				throw new UnsupportedOperationException("addAll");
			}

			@Override
			public void clear() {
				throw new UnsupportedOperationException("clear");
			}

			@Override
			public boolean contains(Object o) {
				boolean containsKey = map.containsKey(o);
				if (containsKey == false) {
					containsKey = properties.containsKey(o);
				}
				return containsKey;
			}

			@Override
			public boolean containsAll(Collection<?> c) {
				for (Object object : c) {
					if (contains(object) == false) {
						return false;
					}
				}
				return true;
			}

			@Override
			public boolean isEmpty() {
				return map.isEmpty() && properties.isEmpty();
			}

			@Override
			public Iterator<Object> iterator() {
				return new Iterator<Object>() {

					private Iterator<String> mapKeyIterator = map.keySet().iterator();
					private Enumeration<Object> propsKeyEnumeration = properties.elements();

					private boolean useEnum = false;

					@Override
					public boolean hasNext() {
						if (useEnum == true) {
							return propsKeyEnumeration.hasMoreElements();
						}
						boolean hasNext = mapKeyIterator.hasNext();
						useEnum = !hasNext;
						return hasNext;
					}

					@Override
					public Object next() {
						if (useEnum == true) {
							return propsKeyEnumeration.nextElement();
						}
						return mapKeyIterator.next();
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("remove");
					}

				};
			}

			@Override
			public boolean remove(Object o) {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public boolean removeAll(Collection<?> c) {
				throw new UnsupportedOperationException("removeAll");
			}

			@Override
			public boolean retainAll(Collection<?> c) {
				throw new UnsupportedOperationException("retainAll");
			}

			@Override
			public int size() {
				return Math.max(map.size(), properties.size());
			}

			@Override
			public Object[] toArray() {
				Object[] array = new Object[size()];
				int i = 0;
				for (String key : map.keySet()) {
					array[i] = key;
					i++;
				}
				for (Object key : properties.keySet()) {
					if (map.containsKey(key) == false) {
						array[i] = key;
						i++;
					}
				}
				return array;
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T[] toArray(T[] a) {
				int size = size();
				if (a.length < size) {
					a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
				}
				int i = 0;
				for (String key : map.keySet()) {
					a[i] = (T) key;
					i++;
				}
				for (Object key : properties.keySet()) {
					if (map.containsKey(key) == false) {
						a[i] = (T) key;
						i++;
					}
				}
				if (a.length >= size) {
					a[size] = null;
				}
				return a;

			}

		};
	}

}