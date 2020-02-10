package com.tibco.cep.runtime.service.basic;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/*
* Author: Ashwin Jayaprakash Date: Feb 11, 2009 Time: 11:18:40 AM
*/
public class DependencyWatcher {
    protected ConcurrentHashMap<Object, ConcurrentHashMap<Dependent, Object>> dependencies;

    public String getId() {
        return DependencyWatcher.class.getName();
    }

    public void init() {
        dependencies = new ConcurrentHashMap<Object, ConcurrentHashMap<Dependent, Object>>();
    }

    public void stop() {
        for (ConcurrentHashMap<Dependent, Object> map : dependencies.values()) {
            map.clear();
        }

        dependencies.clear();
    }

    //-----------

    public void registerDependency(Object key, Dependent dependent) {
        ConcurrentHashMap<Dependent, Object> map = dependencies.get(key);

        if (map == null) {
            map = new ConcurrentHashMap<Dependent, Object>(2);

            ConcurrentHashMap<Dependent, Object> tempMap = dependencies.putIfAbsent(key, map);
            if (tempMap != null) {
                map = tempMap;
            }
        }

        map.put(dependent, dependent);
    }

    public Collection<Object> getDependencyKeys() {
        return dependencies.keySet();
    }

    /**
     * @param key
     * @return Can be <code>null</code>.
     */
    public Collection<Dependent> getDependents(Object key) {
        ConcurrentHashMap<Dependent, Object> map = dependencies.get(key);

        if (map == null) {
            return null;
        }

        return map.keySet();
    }

    public void notifyDependents(Object key, Object... args) {
        ConcurrentHashMap<Dependent, Object> map = dependencies.get(key);

        if (map == null) {
            return;
        }

        for (Dependent dependent : map.keySet()) {
            dependent.onNotify(args);
        }
    }

    public void unregisterDependency(Object key, Dependent dependent) {
        ConcurrentHashMap<Dependent, Object> map = dependencies.get(key);

        if (map == null) {
            return;
        }

        map.remove(dependent);

        /*
        Do not clear from "dependencies". Needs locking to do it correctly. We'll just leave the
        empty map here and clear it in stop().
        */
    }
}
