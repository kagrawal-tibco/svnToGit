package com.tibco.be.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDualBidiMap<K, V> implements BidiMap<K, V> {
	protected transient Map<K,V> normalMap;
	protected transient Map<V,K> reverseMap;
	protected transient BidiMap<V,K> inverseBidiMap = null;
	protected transient Set<K> keyset = null;
	protected transient Collection<V> values = null; 
	protected transient Set<Map.Entry<K, V>> entrySet = null; 
	
	protected AbstractDualBidiMap() {
		super();
	}
	
	protected AbstractDualBidiMap(Map<K,V> normalMap,Map<V,K> reverseMap) {
		super();
		this.normalMap = normalMap;
		this.reverseMap = reverseMap;
	}
	
	protected AbstractDualBidiMap(Map<K,V> normalMap,Map<V,K> reverseMap,BidiMap<V,K> inverseBidiMap) {
		super();
		this.normalMap = normalMap;
		this.reverseMap = reverseMap;
		this.inverseBidiMap = inverseBidiMap;
	}
	
	protected abstract BidiMap<V, K> createBidiMap(Map<V, K> normalMap, Map<K, V> reverseMap, BidiMap<K, V> inverseMap); 
	
	@Override
	public K getKey(Object value) {
		return reverseMap.get(value);
	}

	@Override
	public BidiMap<V, K> inverseMap() {
		if(inverseBidiMap == null) {
			inverseBidiMap = createBidiMap(reverseMap, normalMap, this);
		}
		return inverseBidiMap;
	}

	@Override
	public V put(K key, V value) {
		if(normalMap.containsKey(key)) {
			reverseMap.remove(normalMap.get(key));
		}
		if(reverseMap.containsKey(value)) {
			normalMap.remove(reverseMap.get(value));
		}
		final V obj = normalMap.put(key, value);
		reverseMap.put(value, key);
		return obj;
	}

	@Override
	public K removeValue(Object value) {
		K key = null;
		if(reverseMap.containsKey(value)) {
			key = reverseMap.remove(value);
			normalMap.remove(key);
		}
		return key;
	}


	@Override
	public void clear() {
		normalMap.clear();
		reverseMap.clear();

	}

	@Override
	public boolean containsKey(Object key) {
		return normalMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return reverseMap.containsKey(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return normalMap.entrySet();
	}
	
	

	@Override
	public V get(Object key) {
		return normalMap.get(key);
	}

	@Override
	public boolean isEmpty() {
		return normalMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return normalMap.keySet();
	}
	
	

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) { 
			put(entry.getKey(), entry.getValue()); 
		}

	}

	@Override
	public V remove(Object key) {
		V value = null;
		if(normalMap.containsKey(key)) {
			value = normalMap.remove(key);
			reverseMap.remove(value);
		}
		return value;
	}

	@Override
	public int size() {
		return normalMap.size();
	}

	@Override
	public Collection<V> values() {
		return reverseMap.keySet();
	}
	
	
	
	public boolean equals(Object obj) {
		return normalMap.equals(obj);
	}
	
	public int hashCode() {
		return normalMap.hashCode();
	}

}
