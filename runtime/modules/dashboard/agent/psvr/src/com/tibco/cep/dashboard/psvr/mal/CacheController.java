package com.tibco.cep.dashboard.psvr.mal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 *
 */
public abstract class CacheController<E> extends ServiceDependent {

	protected Map<String, E> objectMap;

	protected CacheController(String name, String descriptiveName) {
		super(name, descriptiveName);
		objectMap = new HashMap<String, E>();
	}

	@Override
	protected synchronized boolean doStop() {
		objectMap.clear();
		return true;
	}

	protected void addObject(String key, E object) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Indexing " + object + " against " + key);
		}
		objectMap.put(key, object);
	}

	protected void removeObject(String key) {
		E remove = objectMap.remove(key);
		if (remove != null) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Deindexing " + remove + " against " + key);
			}
		}
	}

	protected E getObject(String key) {
		return objectMap.get(key);
	}

	public final Iterator<E> getAllObjects() {
		return objectMap.values().iterator();
	}

	public final Iterator<String> getAllKeys() {
		return objectMap.keySet().iterator();
	}

	public final int size() {
		return objectMap.size();
	}
}
