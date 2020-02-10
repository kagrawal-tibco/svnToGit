package com.tibco.rta.common.service;

import java.util.Map;
import java.util.Set;

public interface L1Cache<K, V> {

	V get(K key);

	void put (K key, V value);

	Set<Map.Entry<K,V>> entrySet();

	V remove(K key);

	void clear();

}
