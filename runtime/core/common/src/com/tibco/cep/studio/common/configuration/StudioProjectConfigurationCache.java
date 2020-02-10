package com.tibco.cep.studio.common.configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class StudioProjectConfigurationCache implements Map<String,StudioProjectConfiguration> {

	private static StudioProjectConfigurationCache INSTANCE = null;
	
	private StudioProjectConfigurationCache() {
		map = Collections.synchronizedMap(new HashMap<String, StudioProjectConfiguration>());
		
	}
	
	final Map<String, StudioProjectConfiguration> map;

	
	public static StudioProjectConfigurationCache getInstance() {
		if(INSTANCE == null) {
			synchronized(StudioProjectConfigurationCache.class){
				INSTANCE = new StudioProjectConfigurationCache();
			}
		}
		return INSTANCE;
	}
	
	public Map<String,StudioProjectConfiguration> getConfigMap() {
		return map;
	}
	
	public void clear() {
		synchronized(map) {
			map.clear();
		}
	}

	@Override
	public int size() {
		synchronized (map) {
			return map.size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (map) {
			return map.isEmpty();
		}
	}

	@Override
	public boolean containsKey(Object key) {
		synchronized (map) {
			return map.containsKey(key);
		}
	}

	@Override
	public boolean containsValue(Object value) {
		synchronized (map) {
			return map.containsValue(value);
		}
	}

	@Override
	public StudioProjectConfiguration get(Object key) {
		synchronized (map) {
			return map.get(key);
		}
	}

	@Override
	public StudioProjectConfiguration put(String key, StudioProjectConfiguration value) {
		synchronized (map) {
			return map.put(key,value);
		}
	}

	@Override
	public StudioProjectConfiguration remove(Object key) {
		synchronized (map) {
			return map.remove(key);
		}
	}

	@Override
	public void putAll(Map<? extends String, ? extends StudioProjectConfiguration> m) {
		synchronized (map) {
			map.putAll(m);
		}
		
	}

	@Override
	public Set<String> keySet() {
		synchronized (map) {
			return map.keySet();
		}
	}

	@Override
	public Collection<StudioProjectConfiguration> values() {
		synchronized (map) {
			return map.values();
		}
	}

	@Override
	public Set<java.util.Map.Entry<String, StudioProjectConfiguration>> entrySet() {
		synchronized (map) {
			return map.entrySet();
		}
	}
	
}
