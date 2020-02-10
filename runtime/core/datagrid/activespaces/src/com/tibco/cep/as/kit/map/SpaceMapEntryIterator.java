package com.tibco.cep.as.kit.map;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.cep.as.kit.collection.DiscardableIterator;

/*
 * Author: Ashwin Jayaprakash Date: Apr 25, 2009 Time: 2:36:55 PM
 */
public class SpaceMapEntryIterator<K, V> implements DiscardableIterator<Entry<K, V>> {
    protected Tuple currentEntry;

    protected Tuple currentEntryForRemoval;

    protected Browser browser;

    protected Space space;

    protected KeyValueTupleAdaptor<K, V> kvTupleAdaptor;

    /**
     * Sync's on this instance.
     */
    protected final AtomicBoolean discarded;

    public SpaceMapEntryIterator(Space space, KeyValueTupleAdaptor<K, V> kvTupleAdaptor, Browser browser) {
        this.space = space;
        this.kvTupleAdaptor = kvTupleAdaptor;
        this.browser = browser;

        this.discarded = new AtomicBoolean();
    }

    public Space getSpace() {
        return space;
    }

    public KeyValueTupleAdaptor<K, V> getTupleAdaptor() {
        return kvTupleAdaptor;
    }

    public Browser getBrowser() {
        return browser;
    }

    public final void discard() {
        if (discarded.get()||(browser==null)) {
            return;
        }

        synchronized (discarded) {
            if (discarded.get()||(browser==null)) {
                return;
            }

            try {
                browser.stop();
            } catch (ASException e) {
                System.out.println("WARNING: Failed to stop browser for space "+space.getName());
            }

            currentEntry = null;
            browser = null;
            space = null;

            discarded.set(true);
        }
    }

    //--------------

    public boolean hasNext() {
        if (currentEntry == null && browser != null /*Because we have to call discard when hasNext() return false.*/) {
            try {
                currentEntry = browser.next();
            } catch (ASException e) {
                throw new RuntimeException(e);
            }
        }

        return (currentEntry != null);
    }

    public Map.Entry<K, V> next() {
        currentEntryForRemoval = null;

        if (currentEntry == null) {
            try {
                currentEntry = browser.next();
            } catch (ASException e) {
                throw new RuntimeException(e);
            }
        }

        // Still null.
        if (currentEntry == null) {
            throw new NoSuchElementException();
        }

        MapEntryAdapter entryAdapter = new MapEntryAdapter(currentEntry);
        currentEntryForRemoval = currentEntry;
        currentEntry = null;

        return entryAdapter;
    }

    public void remove() {
        if (currentEntryForRemoval == null) {
            throw new IllegalStateException();
        }

        try {
            space.take(currentEntryForRemoval);
        } catch (ASException e) {
            throw new RuntimeException(e);
        }

        currentEntryForRemoval = null;
    }

    //--------------

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        discard();
    }

    //--------------

    protected class MapEntryAdapter implements Map.Entry<K, V> {
        protected Tuple spaceTuple;

        public MapEntryAdapter(Tuple tuple) {
            this.spaceTuple = tuple;
        }

        public K getKey() {
            return kvTupleAdaptor.extractKey(spaceTuple);
        }

        public V getValue() {
            return kvTupleAdaptor.extractValue(spaceTuple);
        }

        /**
         * @param value
         * @return <b>Always</b> <code>null</code>.
         */
        public V setValue(V value) {

            //make a value tuple
            Tuple valueTuple = kvTupleAdaptor.makeTuple(getKey());
            kvTupleAdaptor.setValue(valueTuple, value);

            try {
                Tuple resultTuple = space.compareAndPut(spaceTuple, valueTuple);
                return kvTupleAdaptor.extractValue(resultTuple);
            } catch (ASException e) {
                 throw new RuntimeException(e);
            }

        }
    }
}
