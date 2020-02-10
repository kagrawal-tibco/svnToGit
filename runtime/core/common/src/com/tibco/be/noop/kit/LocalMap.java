package com.tibco.be.noop.kit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.Invocable.Result;
import com.tibco.cep.runtime.service.om.api.MapChangeListener;

public class LocalMap< K, V> implements ControlDao<K, V> {
	
	private ControlDaoType type;
	private String name;


	public LocalMap(String name, ControlDaoType type) {
		this.name = name;
		this.type = type;
	}
	
	Map map = new ConcurrentHashMap<K, V>();

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return (V) map.get(key);
	}

	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return (V) map.remove(key);
	}

	@Override
	public void putAll(Map m) {
		map.putAll(m);
		
	}

	@Override
	public void clear() {
		map.clear();
		
	}

	@Override
	public Set keySet() {
		return map.keySet();
	}

	@Override
	public Collection values() {
		return map.values();
	}

	@Override
	public Set entrySet() {
		return map.entrySet();
	}

	@Override
	public Set entrySet(Filter filter, int limit) {
		return map.entrySet();
	}

	@Override
	public Set keySet(Filter filter, int limit) {
		return map.keySet();
	}

	@Override
	public Map<String, Result> invoke(Invocable invocable, Set<? extends GroupMember> members) {
		return new HashMap();
	}

	@Override
	public void init(Cluster cluster) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ControlDaoType getType() {
		return type;
	}

	@Override
	public void start() {
		
	}

	@Override
	public boolean lock(Object key, long timeoutMillis) {
		return true;
	}

	@Override
	public boolean lockAll(long timeoutMillis) {
		return true;
	}

	@Override
	public boolean unlock(Object key) {
		return true;
	}

	@Override
	public void unlockAll() {
		
	}

	@Override
	public void discard() {
		
	}

	@Override
	public Map getInternal() {
		return map;
	}

	@Override
	public void registerListener(MapChangeListener changeListener) {
		
	}

	@Override
	public void unregisterListener(MapChangeListener changeListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection getAll(Collection keys) {
		return map.values();
	}

	@Override
	public void removeAll(Collection keys) {
		keys.forEach((k) -> {
			remove(k);
		});
	}
}
