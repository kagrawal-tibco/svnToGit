package com.tibco.store.persistence.model.invm.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.store.persistence.model.MemoryIndex;
import com.tibco.store.persistence.model.MemoryKey;

public class MemoryIndexImpl<T> implements MemoryIndex<T> {

	private Class<?> dataClass;

	private TreeMap<T, HashSet<MemoryKey>> indexStore = new TreeMap<T, HashSet<MemoryKey>>();

	private Set<MemoryKey> nullables = new HashSet<MemoryKey>();

	private final ReentrantLock mainLock = new ReentrantLock();

	public MemoryIndexImpl(Class<?> dataClass) {
		this.dataClass = dataClass;
	}

	@Override
	public Set<MemoryKey> get(T key) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			if (key != null) {
				if (indexStore.get(key) != null) {
					return indexStore.get(key);
				}
			}
			return nullables;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<MemoryKey> getValues() {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Set<MemoryKey> result = new HashSet<MemoryKey>();
			result.addAll(getIndexValues());
			result.addAll(nullables);
			return result;
		} finally {
			mainLock.unlock();
		}
	}

	private Collection<MemoryKey> getIndexValues() {
		Set<MemoryKey> result = new HashSet<MemoryKey>();
		Collection<HashSet<MemoryKey>> sets = indexStore.values();
		for (Set<MemoryKey> set : sets) {
			result.addAll(set);
		}
		return result;
	}

	private void put(T key, HashSet<MemoryKey> value) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			if (key != null) {
				if (!dataClass.getName().equals(key.getClass().getName())) {
					throw new IllegalArgumentException("Data Type mismatch");
				}
				indexStore.put(key, value);
			} else {
				nullables.addAll(value);
			}
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public void putValueToKey(T key, MemoryKey value) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {

			HashSet<MemoryKey> set;
			if (key != null) {
				if (!dataClass.getName().equals(key.getClass().getName())) {
					throw new IllegalArgumentException("Data Type mismatch");
				}
				set = indexStore.get(key);
				if (set != null) {
					set.add(value);
					return;
				}
			}
			set = new HashSet<MemoryKey>();
			set.add(value);
			put(key, set);
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public void removeValueReference(MemoryKey value) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Collection<T> c = new LinkedList<T>(indexStore.keySet());
			for (T key : c) {
				HashSet<MemoryKey> set = indexStore.get(key);
				if (set != null) {
					set.remove(value);
					if (set.isEmpty()) {
						indexStore.remove(key);
					}
				}
			}
			nullables.remove(value);
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Iterator<MemoryKey> removeFromStore(T key) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			if (key != null) {
				HashSet<MemoryKey> set = indexStore.remove(key);
				return set.iterator();
			} else {
				nullables.clear();
				return nullables.iterator();
			}
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public void removeValueForKey(T key, MemoryKey value) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			if (key != null) {
				HashSet<MemoryKey> set = indexStore.get(key);
				if (set != null) {
					set.remove(value);
					if (set.isEmpty()) {
						indexStore.remove(key);
					}
				}
			} else {
				nullables.remove(value);
			}
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<HashSet<MemoryKey>> greaterThan(T fromKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Map<T, HashSet<MemoryKey>> map = indexStore.tailMap(fromKey, false);

			if (map != null) {
				return map.values();
			}
			return null;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<HashSet<MemoryKey>> greaterThanEqualTo(T fromKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Map<T, HashSet<MemoryKey>> map = indexStore.tailMap(fromKey, true);
			if (map != null) {
				return map.values();
			}
			return null;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<HashSet<MemoryKey>> lessThan(T toKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Map<T, HashSet<MemoryKey>> map = indexStore.headMap(toKey);
			return map.values();
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<HashSet<MemoryKey>> lessThanEqualTo(T toKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Map<T, HashSet<MemoryKey>> map = indexStore.headMap(toKey, true);

			if (map != null) {
				return map.values();
			}
			return null;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<HashSet<MemoryKey>> greaterThanAndLessThan(T fromKey, T toKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Map<T, HashSet<MemoryKey>> map = indexStore.subMap(fromKey, false, toKey, false);

			if (map != null) {
				return map.values();
			}
			return null;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<HashSet<MemoryKey>> greaterThanEqualAndLThan(T fromKey, T toKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Map<T, HashSet<MemoryKey>> map = indexStore.subMap(fromKey, true, toKey, false);

			if (map != null) {
				return map.values();
			}
			return null;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<HashSet<MemoryKey>> greaterThanEqualAndLThanEqual(T fromKey, T toKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			Map<T, HashSet<MemoryKey>> map = indexStore.subMap(fromKey, true, toKey, true);

			if (map != null) {
				return map.values();
			}
			return null;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public Collection<MemoryKey> getNot(T key) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {
			if (key == null) {
				return getIndexValues();
			}
			if (indexStore.get(key) != null) {
				Collection<HashSet<MemoryKey>> s1 = lessThan(key);
				s1.addAll(greaterThan(key));
				Set<MemoryKey> result = new HashSet<MemoryKey>();
				for (Set<MemoryKey> s2 : s1) {
					result.addAll(s2);
				}
				return result;
			}
			return null;
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public int getTotalKeys() {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {

			return indexStore.size() + nullables.size();
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public int equalsCount(T fromKey) {
		return get(fromKey).size();
	}

	@Override
	public int greaterThanCount(T fromKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {

			Map<T, HashSet<MemoryKey>> map = indexStore.tailMap(fromKey, false);
			return getTotalCount(map);
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public int greaterThanEqualToCount(T fromKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {

			Map<T, HashSet<MemoryKey>> map = indexStore.tailMap(fromKey, true);
			return getTotalCount(map);
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public int lessThanCount(T toKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {

			Map<T, HashSet<MemoryKey>> map = indexStore.headMap(toKey);
			return getTotalCount(map);
		} finally {
			mainLock.unlock();
		}
	}

	@Override
	public int lessThanEqualToCount(T toKey) {
		ReentrantLock mainLock = this.mainLock;
		mainLock.lock();

		try {

			Map<T, HashSet<MemoryKey>> map = indexStore.headMap(toKey, true);
			return getTotalCount(map);
		} finally {
			mainLock.unlock();
		}
	}

	private int getTotalCount(Map<T, HashSet<MemoryKey>> map) {
		int count = 0;

		if (map != null) {
			Collection<HashSet<MemoryKey>> values = map.values();
			for (HashSet<MemoryKey> value : values) {
				count += value.size();
			}
		}
		return count;
	}
}
