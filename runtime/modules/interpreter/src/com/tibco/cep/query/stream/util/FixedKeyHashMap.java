package com.tibco.cep.query.stream.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*
 * Author: Ashwin Jayaprakash
 * Date: Mar 18, 2008
 * Time: 11:31:36 AM
 */

public final class FixedKeyHashMap<K, V> extends HashMap<K, V> implements Iterable<K> {
    protected String[] fixedKeys;

    public FixedKeyHashMap(Set<String> fixedKeys) {
        this(fixedKeys.toArray(new String[fixedKeys.size()]));
    }

    public FixedKeyHashMap(String... fixedKeys) {
        super(fixedKeys.length);

        this.fixedKeys = fixedKeys;
    }

    public String[] getFixedKeys() {
        return fixedKeys;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
