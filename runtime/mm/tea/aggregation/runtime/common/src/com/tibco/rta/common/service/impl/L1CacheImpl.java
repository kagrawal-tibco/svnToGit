package com.tibco.rta.common.service.impl;

import com.tibco.rta.common.service.L1Cache;
import org.apache.commons.collections4.map.LRUMap;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class L1CacheImpl<K, V> implements L1Cache<K, V>, L1CacheImplMBean {

	protected final Map<K, V> map;

	protected final long cacheSize;

	protected AtomicLong cache_hit = new AtomicLong(0);

	protected AtomicLong cache_miss = new AtomicLong(0);

    @SuppressWarnings("unchecked")
	public L1CacheImpl(long cacheSize) {
		this.cacheSize = cacheSize;
		this.map = Collections.synchronizedMap(new LRUMap((int) cacheSize));
	}

	@Override
	public V get(K key) {
		if (map.containsKey(key)) {
			cache_hit.incrementAndGet();
		} else {
			cache_miss.incrementAndGet();
		}
		return map.get(key);
	}

	@Override
	public void put(K key, V value) {
		map.put(key, value);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public V remove(K key) {
		return map.remove(key);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public int getCurrentSize() {
		return map.size();
	}

	@Override
	public long getCapacity() {
		return cacheSize;
	}

	@Override
	public long getHits() {
		return cache_hit.get();
	}

	@Override
	public long getMisses() {
		return cache_miss.get();
	}

	@Override
	public double getHitPercentage() {
		long cache_event = cache_hit.get() + cache_miss.get();
		if (cache_event != 0) {
			return (cache_hit.get() * 100 / cache_event);
		} else {
			return 0;
		}
	}

	@Override
	public double getMissPercentage() {
		long cache_event = cache_hit.get() + cache_miss.get();
		if (cache_event != 0) {
			return (cache_miss.get() * 100 / cache_event);
		} else {
			return 0;
		}
	}
}
