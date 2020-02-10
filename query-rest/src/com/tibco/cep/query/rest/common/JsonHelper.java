package com.tibco.cep.query.rest.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/24/14
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonHelper<K,V> {

    Map<K,Map<K,V>> store;

    JsonHelper()
    {
        store = new HashMap<>();
    }

    public void put(K key, Map<K,V> value)
    {
        store.put(key, value);
    }

    public Map<K,V> get(K key)
    {
        return store.get(key);
    }


}
