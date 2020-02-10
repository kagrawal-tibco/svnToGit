package com.tibco.cep.query.stream._join2_.util;

import com.tibco.cep.query.stream.util.SingleElementCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
* Author: Ashwin Jayaprakash Date: Jun 3, 2009 Time: 8:36:37 PM
*/
public class MultiValueBag<K, V> {
    protected HashMap<K, Collection<V>> delegate;

    protected SingleElementCollection<V> reusableTempCollection;

    public MultiValueBag() {
        this.delegate = new HashMap<K, Collection<V>>();
        this.reusableTempCollection = new SingleElementCollection<V>(null);
    }

    protected Collection<V> createValueContainer() {
        return new HashSet<V>();
    }

    public void put(K k, V v) {
        reusableTempCollection.setElement(v);

        //Just put it in. Optimization for the fast path. 
        Collection<V> existing = delegate.put(k, reusableTempCollection);

        /*
        New addition. So, let's leave the reusableTempCollection there and create a new
        one for later.
        */
        if (existing == null) {
            reusableTempCollection = new SingleElementCollection<V>(null);

            return;
        }

        if (existing instanceof SingleElementCollection) {
            Collection<V> multiValueCollection = createValueContainer();

            V existingElement = ((SingleElementCollection<V>) existing).getElement();
            multiValueCollection.add(existingElement);

            multiValueCollection.add(v);

            //Put the new one in.
            delegate.put(k, multiValueCollection);
        }
        //Oh, the multi-value-collection is already there.
        else {
            existing.add(v);

            //Put it back in.
            delegate.put(k, existing);
        }

        //We did not use this. So, let's keep it clean for later.
        reusableTempCollection.clear();
    }

    public Set<K> keySet() {
        return delegate.keySet();
    }

    public Collection<V> get(K k) {
        return delegate.get(k);
    }

    public Collection<V> removeAll(K k) {
        return delegate.remove(k);
    }

    public void remove(K k, V v) {
        Collection<V> existing = delegate.get(k);
        if (existing == null) {
            return;
        }

        existing.remove(v);

        //Clean up.
        if (existing.isEmpty()) {
            delegate.remove(k);
        }
    }

    public void clear() {
        delegate.clear();
    }
}
