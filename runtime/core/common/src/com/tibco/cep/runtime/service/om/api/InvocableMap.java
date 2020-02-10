package com.tibco.cep.runtime.service.om.api;

public interface InvocableMap<K> {
    Object invoke(Invocable def);

    Object invoke(K k, Invocable def);

    Object invokeAll(K k, Invocable def);
}
