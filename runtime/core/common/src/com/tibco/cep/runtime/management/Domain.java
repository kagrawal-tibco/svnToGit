package com.tibco.cep.runtime.management;

import java.util.EnumMap;
import java.util.Map;

/*
* Author: Ashwin Jayaprakash Date: Jan 27, 2009 Time: 4:52:28 PM
*/
public class Domain extends EnumMap<DomainKey, Object> {
    public Domain(Class<DomainKey> keyType) {
        super(keyType);
    }

    public Domain(EnumMap<DomainKey, ? extends Object> m) {
        super(m);
    }

    public Domain(Map<DomainKey, ? extends Object> m) {
        super(m);
    }

    /**
     * @param key
     * @return value such that {@link T} is of type {@link DomainKey#getValueClass()}.
     */
    public <T> T safeGet(DomainKey key) {
        Object value = get(key);

        Class<T> clazz = key.getValueClass();

        return clazz.cast(value);
    }

    /**
     * @param key
     * @param value Must be of type {@link DomainKey#getValueClass()}.
     * @return Existing value.
     */
    public <T> T safePut(DomainKey key, T value) {
        Class<T> clazz = key.getValueClass();
        T t = clazz.cast(value);

        T oldValue = (T) put(key, t);

        return oldValue;
    }
}
