package com.tibco.cep.runtime.service.om.api;

public interface MapChangeListener <K, V> {
    void onPut(K key, V value, boolean isLocal);

    void onUpdate(K key, V oldValue, V newValue);

    void onRemove(K key, V oldValue, boolean isLocal);
}
