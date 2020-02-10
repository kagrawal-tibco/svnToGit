package com.tibco.be.util;

import java.util.Map;

public interface BidiMap<K, V> extends Map<K, V> {
	V put(K key,V value);
	K getKey(Object value);
	K removeValue(Object value);
	BidiMap<V,K> inverseMap();
}
