package com.tibco.cep.studio.ui.forms.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map implementation with a fixed hashcode, usable as key in another Map.
 */
public class ExtendedPropertiesMap implements Map {


    private Map delegate; // Provides the map implementation.
    private Object hashObject; // Provides the fixed hashcode.

    public ExtendedPropertiesMap() {
        this.delegate = new LinkedHashMap();
        this.hashObject = new Object();
    }
    
    public void clear() {
        this.delegate.clear();
    }

    public Map getDelegate() {
		return delegate;
	}

	public void setDelegate(Map delegate) {
		this.delegate = delegate;
	}

	public boolean containsKey(Object key) {
        return this.delegate.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.delegate.containsValue(value);
    }

    public Set entrySet() {
        return this.delegate.entrySet();
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public Object get(Object key) {
        return this.delegate.get(key);
    }

    public int hashCode() {
        return this.hashObject.hashCode();
    }

    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public Set keySet() {
        return this.delegate.keySet();
    }

    public Object put(Object key, Object value) {
        return this.delegate.put(key, value);
    }

    public void putAll(Map t) {
        this.delegate.putAll(t);
    }

    public Object remove(Object key) {
        return this.delegate.remove(key);
    }

    public int size() {
        return this.delegate.size();
    }

    /**
     * Translates a Map of Map's and values to an ExtendedPropertiesMap of ExtendedPropertiesMap's and values.
     *
     * @param extPropsMap Map to translate.
     * @return ExtendedPropertiesMap that results from the translation.
     */
    public static ExtendedPropertiesMap translate(Map extPropsMap) {
        final ExtendedPropertiesMap m = new ExtendedPropertiesMap();
        for (Iterator it = extPropsMap.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Entry) it.next();
            final Object value = entry.getValue();
            if (value instanceof Map) {
                m.put(entry.getKey(), translate((Map) value));
            } else {
                m.put(entry.getKey(), value);
            }
        }
        return m;
    }

    public Collection values() {
        return this.delegate.values();
    }

}
