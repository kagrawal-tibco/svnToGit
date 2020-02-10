package com.tibco.cep.decision.table.utils;

import java.util.HashSet;
import java.util.Set;

abstract public class AbstractIDGenerator<T> implements IDGenerator<T> {
    protected final Set<T> _IDs = new HashSet<T>();

    protected AbstractIDGenerator() {
    }

    public T getNextID(T oldID) {
        releaseID(oldID);
        return getNextID();
    }

    public void releaseID(T oldID) {
        _IDs.remove(oldID);
    }

    public void releaseAll() {
        _IDs.clear();
    }

    public boolean reserveID(T id) {
        if (!_IDs.contains(id)) {
            _IDs.add(id);
            return true;
        }
        else {
            return false;
        }
    }
}